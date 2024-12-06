package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.DatiCatastali;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.LoadData;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class MieiDatiFamigliariPanel extends BasePanel {

  private static final long serialVersionUID = -1642481424336076636L;

  public MieiDatiFamigliariPanel(String id) {
    super(id);
    Residente residente = LoadData.caricaMieiDatiResidente(getSession());
    fillDati(residente);
  }

  @SuppressWarnings("unused")
  @Override
  public void fillDati(Object dati) {

    DatiCatastali datiCatastali = new DatiCatastali();
    try {
      datiCatastali =
          ServiceLocator.getInstance().getServizioDemografico().getDatiCatastali(getUtente());

    } catch (BusinessException | ApiException e) {
      log.error("Errore API:", e);
    }
    /*
    add(new NotEmptyLabel("foglio", datiCatastali.getFoglio() + " / " + datiCatastali.getSezioneUrbana()));
    add(new NotEmptyLabel("particella", datiCatastali.getParticella() + " / " + datiCatastali.getSubalterno()));
    add(new NotEmptyLabel("zona", datiCatastali.getZonaCensuaria()));
    add(new NotEmptyLabel("categoria", datiCatastali.getCategoria() + " / " + datiCatastali.getClasse()));
    add(new NotEmptyLabel("consistenza", datiCatastali.getConsistenza()));
    add(new NotEmptyLabel("superficie", datiCatastali.getSuperficieCatastale()));
    add(new NotEmptyLabel("rendita", datiCatastali.getRendita()));
    */
  }
}
