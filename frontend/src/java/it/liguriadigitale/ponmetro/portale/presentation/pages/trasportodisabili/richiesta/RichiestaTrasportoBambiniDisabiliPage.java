package it.liguriadigitale.ponmetro.portale.presentation.pages.trasportodisabili.richiesta;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.integrazioneclassic2.panel.IntegrazioneFdCClassicFdC2Panel;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class RichiestaTrasportoBambiniDisabiliPage extends LayoutBasePage {

  private static final long serialVersionUID = -7392940202149596966L;

  public RichiestaTrasportoBambiniDisabiliPage() {
    super();

    if (Constants.DEPLOY == Constants.TIPO_DEPLOY.ESERCIZIO) {
      throw new RestartResponseAtInterceptPageException(
          new RedirectPage(
              BaseServiceImpl.FDC2_RICHIESTA_DOMANDA_TRASPORTO_BAMBINI_DISABILI_A_SCUOLA));
    }

    addOrReplace(
        new IntegrazioneFdCClassicFdC2Panel(
            "integrazionePanel",
            BaseServiceImpl.FDC2_RICHIESTA_DOMANDA_TRASPORTO_BAMBINI_DISABILI_A_SCUOLA));
  }
}
