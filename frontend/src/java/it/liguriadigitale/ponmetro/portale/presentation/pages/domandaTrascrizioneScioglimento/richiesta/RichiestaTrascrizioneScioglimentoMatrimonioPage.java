package it.liguriadigitale.ponmetro.portale.presentation.pages.domandaTrascrizioneScioglimento.richiesta;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.integrazioneclassic2.panel.IntegrazioneFdCClassicFdC2Panel;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class RichiestaTrascrizioneScioglimentoMatrimonioPage extends LayoutBasePage {

  private static final long serialVersionUID = 4515209685581399534L;

  public RichiestaTrascrizioneScioglimentoMatrimonioPage() {

    super();

    if (Constants.DEPLOY == Constants.TIPO_DEPLOY.ESERCIZIO) {
      throw new RestartResponseAtInterceptPageException(
          new RedirectPage(
              BaseServiceImpl.FDC2_RICHIESTA_DOMANDA_TRASCRIZIONI_SCIOGLIMENTO_MATRIMONIO));
    }

    addOrReplace(
        new IntegrazioneFdCClassicFdC2Panel(
            "integrazionePanel",
            BaseServiceImpl.FDC2_RICHIESTA_DOMANDA_TRASCRIZIONI_SCIOGLIMENTO_MATRIMONIO));
  }
}
