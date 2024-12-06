package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmServizi;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmSottoFascicoli;
import it.liguriadigitale.ponmetro.api.presentation.rest.helpczrm.service.HelpCzRMApi;
import java.util.List;

public class ApiHelpCzRMImp implements HelpCzRMApi {

  private HelpCzRMApi instance;

  public ApiHelpCzRMImp(HelpCzRMApi api) {
    instance = api;
  }

  @Override
  public List<CzrmServizi> getCzRmServizi() {
    // TODO Auto-generated method stub
    return instance.getCzRmServizi();
  }

  @Override
  public List<CzrmSottoFascicoli> getCzRmSottoFascicoli() {
    // TODO Auto-generated method stub
    return instance.getCzRmSottoFascicoli();
  }
}
