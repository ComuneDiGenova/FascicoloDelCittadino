package it.liguriadigitale.ponmetro.portale.business.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.liguriadigitale.ponmetro.allertecortesia.model.ErroreProblem;
import java.io.IOException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.ext.Provider;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Provider
public class ErrorResponseCortesiaFilter implements ClientResponseFilter {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
      throws IOException {
    // for non-200 response, deal with the custom error messages
    log.debug("[ErrorResponseCortesiaFilter] Status=" + responseContext.getStatus());
    log.debug(
        "[ErrorResponseCortesiaFilter] invocazione di:"
            + requestContext.getUri()
            + requestContext.getMethod());

    if (responseContext.getStatus() >= Response.Status.BAD_REQUEST.getStatusCode()) {

      if (responseContext.hasEntity()) {
        StatusType statusInfo = responseContext.getStatusInfo();
        log.debug("[ErrorResponseCortesiaFilter] statusInfo=" + statusInfo);
        String message = "Error";
        String messaggioJSON = "";
        if (statusInfo != null) message = statusInfo.getReasonPhrase();
        if (responseContext.getEntityStream() != null) {
          messaggioJSON = new String(IOUtils.toByteArray(responseContext.getEntityStream()));
          log.debug("[ErrorResponseCortesiaFilter] messaggioJSON=" + messaggioJSON);
        }
        Response.Status status = Response.Status.fromStatusCode(responseContext.getStatus());
        switch (status) {
          case BAD_REQUEST:
            gestioneErrore400(messaggioJSON);
            break;
          case UNAUTHORIZED:
            throw new NotAuthorizedException(message);
          case FORBIDDEN:
            throw new ForbiddenException(message);
          case NOT_FOUND:
            oggettoNonTrovato(responseContext);
            break;
          case METHOD_NOT_ALLOWED:
            throw new NotAllowedException(message);
          case NOT_ACCEPTABLE:
            throw new NotAcceptableException(message);
          case UNSUPPORTED_MEDIA_TYPE:
            throw new NotSupportedException(message);
          case INTERNAL_SERVER_ERROR:
            throw new InternalServerErrorException(message);
          case SERVICE_UNAVAILABLE:
            throw new ServiceUnavailableException(message);
          default:
            throw new WebApplicationException(message);
        }
      }
    }
  }

  private void oggettoNonTrovato(ClientResponseContext response) {

    log.debug("From404to204Filter -- INIZIO");

    if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
      log.debug("[From404to204Filter] getAllowedMethods=" + response.getAllowedMethods());
      log.debug("[From404to204Filter] getHeaders=" + response.getHeaders());
      log.debug("[From404to204Filter] hasEntity=" + response.hasEntity());
      log.debug("[From404to204Filter] getEntityTag=" + response.getEntityTag());

      response.setStatus(Status.NO_CONTENT.getStatusCode());
      response.setStatusInfo(Status.NO_CONTENT);

      log.debug("[From404to204Filter] getStatus=" + response.getStatus());
    }
  }

  private void gestioneErrore400(String messaggioJSON) {
    String message = "Errore generico";
    ErroreProblem problem = null;

    try {
      if (messaggioJSON != null) {
        log.debug("[ErrorResponseCortesiaFilter] messaggioJSON=" + messaggioJSON);
        ObjectMapper mapper = new ObjectMapper();
        problem = mapper.readValue(messaggioJSON, ErroreProblem.class);
        if (problem != null && problem.getErrore() != null) {
          message = problem.getErrore().getDetail();
        }

        log.debug("MP messaggio " + message);
      }
    } catch (Exception e) {
      log.debug("[ErrorResponseCortesiaFilter] Errore generico WSO", e);
      message = message + "=" + e.getMessage();
      throw new ServiceUnavailableException(message);
    }
    log.debug("[ErrorResponseCortesiaFilter] Errore 400, parsing oggetto Problem:" + message);
    throw new WebApplicationException(message, 400);
  }
}
