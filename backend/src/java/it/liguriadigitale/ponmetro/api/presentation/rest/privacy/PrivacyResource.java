package it.liguriadigitale.ponmetro.api.presentation.rest.privacy;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.common.EsitoResponse;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.PrivacyRequest;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.PrivacyVersioneRequest;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.builder.PrivacyRequestBuilder;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.builder.PrivacyVersioneRequestBuilder;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyDocResponse;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyResponse;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.BadRequestException;
import it.liguriadigitale.ponmetro.api.presentation.rest.privacy.service.PrivacyRestInterface;
import javax.sql.rowset.serial.SerialException;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrivacyResource implements PrivacyRestInterface {

  private static Log log = LogFactory.getLog(PrivacyResource.class);

  @Override
  public PrivacyResponse verificaLetturaPrivacyCorrente(
      @PathParam("cod_applicazione") String codApplicazione,
      @PathParam("id_anagrafica") Long idAnonimo) {
    log.debug("cod_applicazione=" + codApplicazione);
    log.debug("id_anagrafica=" + idAnonimo);
    try {
      PrivacyRequest request =
          new PrivacyRequestBuilder()
              .setCodApplicazione(codApplicazione)
              .setIdAnonimo(idAnonimo)
              .build();
      PrivacyResponse response =
          ServiceLocator.getInstance().getPrivacy().verificaLetturaPrivacyCorrente(request);

      return response;
    } catch (BusinessException e) {
      log.error("Errore: ", e);
      throw new BadRequestException("Informativa non trovata");
    }
  }

  @Override
  public PrivacyResponse presaVisionePrivacy(
      @FormParam("cod_applicazione") String codApplicazione,
      @FormParam("id_anagrafica") Long idAnonimo,
      @FormParam("id_versione_privacy") Long idVersionePrivacy) {
    log.debug("cod_applicazione=" + codApplicazione);
    log.debug("id_versione_privacy=" + idVersionePrivacy);
    log.debug("id_anagrafica=" + idAnonimo);
    PrivacyVersioneRequest request =
        new PrivacyVersioneRequestBuilder()
            .setCodApplicazione(codApplicazione)
            .setIdAnonimo(idAnonimo)
            .setIdVersionePrivacy(idVersionePrivacy)
            .build();

    try {
      EsitoResponse response =
          ServiceLocator.getInstance().getPrivacy().presaVisionePrivacy(request);
      PrivacyResponse privacy = new PrivacyResponse();
      privacy.setEsito(response);
      privacy.setIdVersionePrivacy(idVersionePrivacy);
      privacy.setStatoPresaVisione(response.isEsito());
      return privacy;
    } catch (BusinessException e) {
      log.error("Errore: ", e);
      throw new BadRequestException("Informativa non trovata");
    }
  }

  @Override
  public Response getDocumentoPrivacy(
      @PathParam("cod_applicazione") String codApplicazione,
      @PathParam("id_anagrafica") Long idAnonimo,
      @PathParam("id_versione_privacy") Long idVersionePrivacy) {

    log.debug("cod_applicazione=" + codApplicazione);
    log.debug("id_versione_privacy=" + idVersionePrivacy);
    log.debug("id_anagrafica=" + idAnonimo);

    PrivacyVersioneRequest request =
        new PrivacyVersioneRequestBuilder()
            .setCodApplicazione(codApplicazione)
            .setIdAnonimo(idAnonimo)
            .setIdVersionePrivacy(idVersionePrivacy)
            .build();

    try {
      PrivacyDocResponse response =
          ServiceLocator.getInstance().getPrivacy().getDocumentoPrivacy(request);
      if (response.getEsito().isEsito()) {
        log.debug("Streaming risposta: " + response.getIdVersionePrivacy());
        ResponseBuilder responseBuilder = Response.ok(response.getPdfFile().getBinaryStream());
        responseBuilder.header("Content-Disposition", "attachment; Informativa-privacy.pdf");
        return responseBuilder.build();
      } else {
        log.debug("Streaming risposta: " + response.getEsito().getDescrizione());
        return Response.serverError().status(204).tag(response.getEsito().getDescrizione()).build();
      }

    } catch (BusinessException | RuntimeException e) {
      log.error("Errore: ", e);
      return Response.serverError().status(418).build();
    } catch (SerialException e) {
      log.error("Errore: ", e);
      return Response.serverError().status(500).build();
    }
  }
}
