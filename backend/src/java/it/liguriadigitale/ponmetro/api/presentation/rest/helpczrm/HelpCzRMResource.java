package it.liguriadigitale.ponmetro.api.presentation.rest.helpczrm;

import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmServizi;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmSottoFascicoli;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.helpczrm.service.HelpCzRMApi;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HelpCzRMResource implements HelpCzRMApi {

  private static Log log = LogFactory.getLog(HelpCzRMResource.class);

  @Override
  public List<CzrmServizi> getCzRmServizi() {
    // TODO Auto-generated method stub
    return ServiceLocator.getInstance().getHelpCzRMService().getCzRmServizi();
  }

  @Override
  public List<CzrmSottoFascicoli> getCzRmSottoFascicoli() {
    // TODO Auto-generated method stub
    return ServiceLocator.getInstance().getHelpCzRMService().getCzRmSottoFascicoli();
  }
}
