package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.panel;

import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class AllerteEServiziCortesiaPanel extends BasePanel {

  private static final long serialVersionUID = -1303574188580429975L;

  public AllerteEServiziCortesiaPanel(String id) {
    super(id);

    // TODO per ora
    fillDati("");

    setOutputMarkupId(true);

    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {

    LinkDinamicoLaddaFunzione<Object> btnZonaRossa =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnZonaRossa",
            new LinkDinamicoFunzioneData(
                "AllerteEServiziCortesiaPanel.btnZonaRossa",
                "AllertePage",
                "AllerteEServiziCortesiaPanel.btnZonaRossa"),
            null,
            AllerteEServiziCortesiaPanel.this,
            "color-fc-pink col-auto icon-servizi-allerta");
    addOrReplace(btnZonaRossa);

    LinkDinamicoLaddaFunzione<Object> btnCortesia =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnCortesia",
            new LinkDinamicoFunzioneData(
                "AllerteEServiziCortesiaPanel.btnCortesia",
                "ServiziCortesiaPage",
                "AllerteEServiziCortesiaPanel.btnCortesia"),
            null,
            AllerteEServiziCortesiaPanel.this,
            "color-fc-pink col-auto icon-servizi-allerta");
    addOrReplace(btnCortesia);

    LinkDinamicoLaddaFunzione<Object> btnAllerteEServizioCortesia =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnAllerteEServCortesia",
            new LinkDinamicoFunzioneData(
                "AllerteEServiziCortesiaPanel.btnVerificaPericolosita",
                "VerificaPericolositaStradaPage",
                "AllerteEServiziCortesiaPanel.btnVerificaPericolosita"),
            null,
            AllerteEServiziCortesiaPanel.this,
            "color-fc-pink col-auto icon-servizi-allerta");
    addOrReplace(btnAllerteEServizioCortesia);
  }
}
