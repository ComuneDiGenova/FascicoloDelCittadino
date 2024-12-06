package it.liguriadigitale.ponmetro.api.business.helpczrm.service;

import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmServizi;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmSottoFascicoli;
import java.util.List;

public interface IHelpCzRMService {

  List<CzrmServizi> getCzRmServizi();

  List<CzrmSottoFascicoli> getCzRmSottoFascicoli();
}
