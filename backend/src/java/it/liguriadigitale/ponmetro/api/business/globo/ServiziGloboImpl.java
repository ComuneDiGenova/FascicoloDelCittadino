package it.liguriadigitale.ponmetro.api.business.globo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.integration.dao.view.VCfgTCatGloboDAO;
import it.liguriadigitale.ponmetro.api.pojo.globo.VCfgTCatGlobo;
import it.liguriadigitale.ponmetro.api.pojo.globo.builder.VCfgTCatGloboBuilder;
import java.util.List;

public class ServiziGloboImpl implements ServiziGloboInterface {

  @Override
  public List ricercaFunzioniGlobo() throws BusinessException {

    VCfgTCatGlobo parametriRicerca =
        VCfgTCatGloboBuilder.getInstance()
            .addIdStatoRec(true)
            // .addIdStatoRecGlobo(true)
            .addFlagAbilitazioneGlobo(true)
            .addFlagAbilitazione(true)
            .build();
    VCfgTCatGloboDAO dao = new VCfgTCatGloboDAO(parametriRicerca);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    return helper.cercaOggetti();
  }
}
