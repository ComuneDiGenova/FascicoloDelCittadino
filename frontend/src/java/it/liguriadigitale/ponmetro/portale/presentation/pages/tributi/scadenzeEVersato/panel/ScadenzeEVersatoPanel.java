package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.panel;

import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;

public class ScadenzeEVersatoPanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 5968129763983369013L;

  public ScadenzeEVersatoPanel(String id) {
    super(id);
    setOutputMarkupId(true);
    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {

    LinkDinamicoLaddaFunzione<Object> btnPaginaVersato =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnPaginaVersato",
            new LinkDinamicoFunzioneData(
                "ScadenzeEVersatoPanel.versato",
                "TributiVersatoPage",
                "ScadenzeEVersatoPanel.versato.tooltip"),
            null,
            ScadenzeEVersatoPanel.this,
            "color-green col-auto " + BasePanelGenericContent.CSS_ICON_IOCON_VERSATO);
    addOrReplace(btnPaginaVersato);

    LinkDinamicoLaddaFunzione<Object> btnPaginaScadenze =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnPaginaScadenze",
            new LinkDinamicoFunzioneData(
                "ScadenzeEVersatoPanel.scadenze",
                "TributiScadenzePage",
                "ScadenzeEVersatoPanel.scadenze.tooltip"),
            null,
            ScadenzeEVersatoPanel.this,
            "color-green col-auto " + BasePanelGenericContent.CSS_ICON_IOCON_SCADENZE);
    addOrReplace(btnPaginaScadenze);
  }
}
