package it.liguriadigitale.ponmetro.api.business.helpczrm.impl;

import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.helpczrm.service.IHelpCzRMService;
import it.liguriadigitale.ponmetro.api.integration.dao.CzrmServiziDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CzrmSottoFascicoliDAO;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmServizi;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmSottoFascicoli;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HelpCzRMService implements IHelpCzRMService {

  private static Log log = LogFactory.getLog(HelpCzRMService.class);

  @SuppressWarnings("unchecked")
  @Override
  public List<CzrmServizi> getCzRmServizi() {
    // TODO Auto-generated method stub
    try {

      CzrmServizi cat = new CzrmServizi();
      CzrmServiziDAO dao = new CzrmServiziDAO(cat);
      PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
      return (List<CzrmServizi>) helper.cercaOggetti();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      log.debug("[getCategorieCatastali - Service] Nessuna categoria catastale trovata");
      return new ArrayList<CzrmServizi>();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<CzrmSottoFascicoli> getCzRmSottoFascicoli() {
    // TODO Auto-generated method stub
    try {

      CzrmSottoFascicoli cat = new CzrmSottoFascicoli();
      CzrmSottoFascicoliDAO dao = new CzrmSottoFascicoliDAO(cat);
      PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
      return (List<CzrmSottoFascicoli>) helper.cercaOggetti();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      log.debug("[getCategorieCatastali - Service] Nessuna categoria catastale trovata");
      return new ArrayList<CzrmSottoFascicoli>();
    }
  }
}
