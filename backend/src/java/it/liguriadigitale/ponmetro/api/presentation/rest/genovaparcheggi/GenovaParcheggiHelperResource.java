package it.liguriadigitale.ponmetro.api.presentation.rest.genovaparcheggi;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.BadRequestException;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.apiclient.GenovaParcheggiHelperApi;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravPermessi;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GenovaParcheggiHelperResource implements GenovaParcheggiHelperApi {

  private static Log log = LogFactory.getLog(GenovaParcheggiHelperResource.class);

  @Override
  public BravFunzioni getBravFunzione(String tipoFunz) {
    try {
      return ServiceLocator.getInstance().getGenovaParcheggiHelper().getBravFunzione(tipoFunz);
    } catch (BusinessException e) {
      log.error("Errore durante getBravFunzione: " + e.getMessage());
      throw new BadRequestException("Impossibile recuperare funzione brav");
    }
  }

  @Override
  public List<BravFunzioni> getBravFunzioni() {
    try {
      return ServiceLocator.getInstance().getGenovaParcheggiHelper().getBravFunzioni();
    } catch (BusinessException e) {
      log.error("Errore durante getBravFunzioni: " + e.getMessage());
      throw new BadRequestException("Impossibile recuperare funzioni brav");
    }
  }

  @Override
  public List<BravPermessi> getBravPermessi(String tipoFunz) {
    try {
      return ServiceLocator.getInstance().getGenovaParcheggiHelper().getBravPermessi(tipoFunz);
    } catch (BusinessException e) {
      log.error("Errore durante getBravPermessi: " + e.getMessage());
      throw new BadRequestException("Impossibile recuperare permessi brav");
    }
  }
}
