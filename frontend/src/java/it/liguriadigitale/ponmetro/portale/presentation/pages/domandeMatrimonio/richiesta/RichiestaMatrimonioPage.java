package it.liguriadigitale.ponmetro.portale.presentation.pages.domandeMatrimonio.richiesta;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.integrazioneclassic2.panel.IntegrazioneFdCClassicFdC2Panel;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class RichiestaMatrimonioPage extends LayoutBasePage {

  private static final long serialVersionUID = -7392940202149596966L;

  public RichiestaMatrimonioPage() {
    super();

    if (Constants.DEPLOY == Constants.TIPO_DEPLOY.ESERCIZIO) {
      throw new RestartResponseAtInterceptPageException(
          new RedirectPage(BaseServiceImpl.FDC2_RICHIESTA_DOMANDA_MATRIMONIO));
    }

    addOrReplace(
        new IntegrazioneFdCClassicFdC2Panel(
            "integrazionePanel", BaseServiceImpl.FDC2_RICHIESTA_DOMANDA_MATRIMONIO));
  }
}
