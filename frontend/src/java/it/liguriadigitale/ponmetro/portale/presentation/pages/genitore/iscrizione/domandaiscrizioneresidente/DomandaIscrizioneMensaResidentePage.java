package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.domandaiscrizioneresidente;

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

public class DomandaIscrizioneMensaResidentePage extends LayoutBasePage {

  private static final long serialVersionUID = 7730163632682503476L;

  public DomandaIscrizioneMensaResidentePage(UtenteServiziRistorazione iscritto) {
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
          BaseServiceImpl.FDC2_RICHIESTA_DOMANDA_ISCRIZIONE_MENSA_RESIDENTE,
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
