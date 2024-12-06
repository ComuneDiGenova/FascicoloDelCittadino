package it.liguriadigitale.ponmetro.portale.presentation.pages.domandalocazione.richiesta;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.integrazioneclassic2.panel.IntegrazioneFdCClassicFdC2Panel;

public class RichiestaLocazionePage extends LayoutBasePage {

  private static final long serialVersionUID = -1218249602467820026L;

  public RichiestaLocazionePage() {
    super();

    addOrReplace(
        new IntegrazioneFdCClassicFdC2Panel(
            "integrazionePanel", BaseServiceImpl.FDC2_RICHIESTA_DOMANDA_LOCAZIONE));
  }
}
