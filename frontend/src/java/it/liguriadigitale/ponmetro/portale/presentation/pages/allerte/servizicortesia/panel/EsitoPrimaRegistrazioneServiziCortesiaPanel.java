package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.model.CompoundPropertyModel;

public class EsitoPrimaRegistrazioneServiziCortesiaPanel extends BasePanel {

  private static final long serialVersionUID = 8271975975470846734L;

  private int index;

  DatiCompletiRegistrazioneUtenteAllerteCortesia datiCompletiRegistrazioneUtenteAllerteCortesia;

  public EsitoPrimaRegistrazioneServiziCortesiaPanel(
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
  public void fillDati(Object dati) {}
}
