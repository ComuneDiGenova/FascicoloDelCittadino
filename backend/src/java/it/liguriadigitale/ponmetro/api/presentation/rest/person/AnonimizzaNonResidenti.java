package it.liguriadigitale.ponmetro.api.presentation.rest.person;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.anonim.apiclient.BackendApi;
import it.liguriadigitale.ponmetro.anonim.model.AnonimoData;
import it.liguriadigitale.ponmetro.anonim.model.IdCPVPerson;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.dto.UserAnonymous;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.demografico.model.Problem;
import java.math.BigDecimal;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;

@Path("/anonym")
public class AnonimizzaNonResidenti implements BackendApi {

  private static Log log = LogFactory.getLog(AnonimizzaNonResidenti.class);

  @Override
  public IdCPVPerson anonimizzaPost(AnonimoData anonimo) {
    log.debug("anonimizzaPost");
    try {
      UserAnonymous risultato =
          ServiceLocator.getInstance()
              .getAnonimizza()
              .gestioneNonResidentiConCambioResidenza(anonimo);
      IdCPVPerson person = converti(risultato);
      return person;
    } catch (BusinessException e) {
      log.error("CF non corretto");
      throw new BadRequestException(
          "CF non corretto",
          Response.serverError()
              .status(HttpStatus.SC_BAD_REQUEST)
              .entity(getProblemResponse())
              .build());
    }
  }

  private Problem getProblemResponse() {
    Problem entity = new Problem();
    entity.status(400);
    // entity.setType("https://tools.ietf.org/html/rfc7231#section-6.5.1");
    return entity;
  }

  private IdCPVPerson converti(UserAnonymous risultato) {
    IdCPVPerson person = new IdCPVPerson();
    person.setIsCittadinoComuneGe(risultato.getIsResidente());
    person.setPersonId(BigDecimal.valueOf(risultato.getIdAnonimo()));
    return person;
  }
}
