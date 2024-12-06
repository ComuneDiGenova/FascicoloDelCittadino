package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.domandaagevolazionemensa;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.integrazioneclassic2.panel.IntegrazioneFdCClassicFdC2Panel;
import it.liguriadigitale.ponmetro.portale.presentation.util.EncrypterUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class DomandaAgevolazioneTariffariaMensaPage extends LayoutBasePage {

  private static final long serialVersionUID = -1568641581240446243L;

  public DomandaAgevolazioneTariffariaMensaPage(UtenteServiziRistorazione iscritto) {
    super();

    if (Constants.DEPLOY == Constants.TIPO_DEPLOY.ESERCIZIO) {

      throw new RestartResponseAtInterceptPageException(
          new RedirectPage(urlRichiestaAgevolazione(iscritto)));
    }

    addOrReplace(
        new IntegrazioneFdCClassicFdC2Panel(
            "integrazionePanel", urlRichiestaAgevolazione(iscritto)));
  }

  private String urlRichiestaAgevolazione(UtenteServiziRistorazione iscritto) {
    try {
      return String.format(
          "%s/%s",
          BaseServiceImpl.FDC2_RICHIESTA_DOMANDA_AGEVOLAZIONE_TARIFFARIA_MENSA,
          EncrypterUtil.encrypt(iscritto.getCodiceFiscale()));
    } catch (InvalidKeyException
        | NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidAlgorithmParameterException
        | BadPaddingException
        | IllegalBlockSizeException
        | UnsupportedEncodingException e) {

      log.debug("[urlRichiestaAgevolazione] Impossibile criptare il CF del figlio");
      return "";
    }
  }
}
