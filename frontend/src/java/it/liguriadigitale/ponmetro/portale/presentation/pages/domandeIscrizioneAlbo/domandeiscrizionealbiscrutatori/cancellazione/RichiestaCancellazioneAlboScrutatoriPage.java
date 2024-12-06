package it.liguriadigitale.ponmetro.portale.presentation.pages.domandeIscrizioneAlbo.domandeiscrizionealbiscrutatori.cancellazione;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.integrazioneclassic2.panel.IntegrazioneFdCClassicFdC2Panel;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class RichiestaCancellazioneAlboScrutatoriPage extends LayoutBasePage {

  private static final long serialVersionUID = 2109899450175136279L;

  public RichiestaCancellazioneAlboScrutatoriPage() {
    super();

    if (Constants.DEPLOY == Constants.TIPO_DEPLOY.ESERCIZIO) {
      throw new RestartResponseAtInterceptPageException(
          new RedirectPage(BaseServiceImpl.FDC2_RICHIESTA_DOMANDA_CANCELLAZIONE_ALBO_SCRUTATORI));
    }

    addOrReplace(
        new IntegrazioneFdCClassicFdC2Panel(
            "cancellazionePanel",
            BaseServiceImpl.FDC2_RICHIESTA_DOMANDA_CANCELLAZIONE_ALBO_SCRUTATORI));
  }
}
