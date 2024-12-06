package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.pagamentomultapl;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.integrazioneclassic2.panel.IntegrazioneFdCClassicFdC2Panel;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class PagamentoMultaPLPage extends LayoutBasePage {

  private static final long serialVersionUID = 719088531637351699L;

  public PagamentoMultaPLPage() {
    super();

    if (Constants.DEPLOY == Constants.TIPO_DEPLOY.ESERCIZIO) {
      throw new RestartResponseAtInterceptPageException(
          new RedirectPage(BaseServiceImpl.FDC2_PAGAMENTO_VERBALE_PL));
    }

    addOrReplace(
        new IntegrazioneFdCClassicFdC2Panel(
            "integrazionePanel", BaseServiceImpl.FDC2_PAGAMENTO_VERBALE_PL));
  }
}
