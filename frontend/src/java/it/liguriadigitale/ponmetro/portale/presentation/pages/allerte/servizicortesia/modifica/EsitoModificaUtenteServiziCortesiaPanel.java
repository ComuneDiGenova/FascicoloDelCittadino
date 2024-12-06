package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.modifica;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.model.CompoundPropertyModel;

public class EsitoModificaUtenteServiziCortesiaPanel extends BasePanel {

  private static final long serialVersionUID = 8271975975470846734L;

  private int index;

  DatiCompletiRegistrazioneUtenteAllerteCortesia datiCompletiRegistrazioneUtenteAllerteCortesia;

  public EsitoModificaUtenteServiziCortesiaPanel(
      String id,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiCompleti,
      int index) {
    super(id);
    this.datiCompletiRegistrazioneUtenteAllerteCortesia = datiCompleti.getObject();
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(datiCompleti);
    this.setIndex(index);
  }

  @Override
  public void fillDati(Object dati) {}

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
