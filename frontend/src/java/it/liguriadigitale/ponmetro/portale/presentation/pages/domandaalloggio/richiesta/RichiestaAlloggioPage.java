package it.liguriadigitale.ponmetro.portale.presentation.pages.domandaalloggio.richiesta;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.integrazioneclassic2.panel.IntegrazioneFdCClassicFdC2Panel;

public class RichiestaAlloggioPage extends LayoutBasePage {

  private static final long serialVersionUID = -3714483080490407163L;

  public RichiestaAlloggioPage() {
    super();

    addOrReplace(
        new IntegrazioneFdCClassicFdC2Panel(
            "integrazionePanel", BaseServiceImpl.FDC2_RICHIESTA_DOMANDA_ALLOGGIO));
  }
}
