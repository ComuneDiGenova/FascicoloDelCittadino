package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.model.CompoundPropertyModel;

public class EsitoModificaServiziCortesiaPanel extends BasePanel {

  private int index;

  DatiCompletiRegistrazioneUtenteAllerteCortesia datiCompletiRegistrazioneUtenteAllerteCortesia;

  public EsitoModificaServiziCortesiaPanel(
      String id,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiCompleti,
      int index) {
    super(id);
    this.datiCompletiRegistrazioneUtenteAllerteCortesia = datiCompleti.getObject();
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(datiCompleti);
    this.index = index;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub

  }
}
