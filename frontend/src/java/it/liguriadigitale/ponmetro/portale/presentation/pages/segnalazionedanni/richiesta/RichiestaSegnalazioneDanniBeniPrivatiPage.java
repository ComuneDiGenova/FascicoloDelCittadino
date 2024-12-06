package it.liguriadigitale.ponmetro.portale.presentation.pages.segnalazionedanni.richiesta;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.integrazioneclassic2.panel.IntegrazioneFdCClassicFdC2Panel;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class RichiestaSegnalazioneDanniBeniPrivatiPage extends LayoutBasePage {

  private static final long serialVersionUID = 1L;

  public RichiestaSegnalazioneDanniBeniPrivatiPage() {

    super();

    if (Constants.DEPLOY == Constants.TIPO_DEPLOY.ESERCIZIO) {
      throw new RestartResponseAtInterceptPageException(
          new RedirectPage(BaseServiceImpl.FDC2_RICHIESTA_SEGNALAZIONE_DANNI_BENI_PRIVATI));
    }

    addOrReplace(
        new IntegrazioneFdCClassicFdC2Panel(
            "integrazionePanel", BaseServiceImpl.FDC2_RICHIESTA_SEGNALAZIONE_DANNI_BENI_PRIVATI));
  }
}
