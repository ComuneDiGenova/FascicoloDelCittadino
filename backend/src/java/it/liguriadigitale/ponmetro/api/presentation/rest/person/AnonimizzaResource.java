package it.liguriadigitale.ponmetro.api.presentation.rest.person;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.anonim.apiclient.PortaleApi;
import it.liguriadigitale.ponmetro.anonim.model.IdCPVPerson;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.dto.UserAnonymous;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.demografico.model.Problem;
import java.math.BigDecimal;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;

public class AnonimizzaResource implements PortaleApi {

  private static Log log = LogFactory.getLog(AnonimizzaResource.class);

  @Override
  public IdCPVPerson cpvPersonCodiceFiscaleGet(String codiceFiscale) {
    // https://www.javacodegeeks.com/2012/06/resteasy-tutorial-part-3-exception.html
    Problem entity = getProblemResponse();
    try {
      @SuppressWarnings("deprecation")
      UserAnonymous user =
          ServiceLocator.getInstance().getAnonimizza().getIdUtenteAnonimo(codiceFiscale);

      if (user == null) {
        log.error("Utente non genovese");
        throw new BadRequestException(
            "Utente non genovese",
            Response.serverError().status(HttpStatus.SC_BAD_REQUEST).entity(entity).build());
      }
      if (user.getIdAnonimo() != 0) {
        IdCPVPerson person = new IdCPVPerson();
        person.setPersonId(BigDecimal.valueOf(user.getIdAnonimo()));
        person.setIsCittadinoComuneGe(true);
        return person;
      } else {
        log.error("Utente non trovato");
        entity.setTitle("Utente non trovato");
        entity.setDetail("Il cf inserito non corrisponde ad un record nel database comunale");
        throw new BadRequestException(
            "Utente non trovato",
            Response.serverError().status(HttpStatus.SC_BAD_REQUEST).entity(entity).build());
      }

    } catch (BusinessException e) {
      log.error("CF non corretto");
      throw new BadRequestException(
          "CF non corretto",
          Response.serverError().status(HttpStatus.SC_BAD_REQUEST).entity(entity).build());
    }
  }

  private Problem getProblemResponse() {
    Problem entity = new Problem();
    entity.status(400);
    // entity.setType("https://tools.ietf.org/html/rfc7231#section-6.5.1");
    return entity;
  }
}
