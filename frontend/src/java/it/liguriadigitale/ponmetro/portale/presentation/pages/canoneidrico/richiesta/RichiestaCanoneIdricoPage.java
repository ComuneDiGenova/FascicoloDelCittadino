package it.liguriadigitale.ponmetro.portale.presentation.pages.canoneidrico.richiesta;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.pages.canoneidrico.privacy.CanoneIdricoPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.integrazioneclassic2.panel.IntegrazioneFdCClassicFdC2Panel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.privacy.pojo.PrivacyServizio;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class RichiestaCanoneIdricoPage extends LayoutBasePage {

  private static final long serialVersionUID = -5085762162851571779L;

  public RichiestaCanoneIdricoPage() {
    super();

    Utente.inizializzaPrivacyServiziCanoneIdrico(getUtente());

    if (!getUtente().isServiziCanoneIdricoPrivacyNonAccettata()) {

      PrivacyServizio privacyServizio = new PrivacyServizio();

      privacyServizio.setUtente(getUtente());
      privacyServizio.setCodicePrivacy(BaseServiceImpl.COD_CANONEIDRICO);
      privacyServizio.setPaginaSuCuiAtterrare(getPage());
      privacyServizio.setMessaggioErrore("privacy servizi Canone Idrico");
      privacyServizio.setNomePartecipata("Iren Acqua");

      throw new RestartResponseAtInterceptPageException(
          new CanoneIdricoPrivacyPage(privacyServizio));

    } else {
      if (Constants.DEPLOY == Constants.TIPO_DEPLOY.ESERCIZIO) {

        throw new RestartResponseAtInterceptPageException(
            new RedirectPage(BaseServiceImpl.FDC2_RICHIESTA_PAGAMENTO_CANONE_IDRICO));
      }

      addOrReplace(
          new IntegrazioneFdCClassicFdC2Panel(
              "integrazionePanel", BaseServiceImpl.FDC2_RICHIESTA_PAGAMENTO_CANONE_IDRICO));
    }

    setOutputMarkupId(true);
  }
}
