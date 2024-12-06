package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.ricorsialprefetto.richiesta;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.integrazioneclassic2.panel.IntegrazioneFdCClassicFdC2Panel;
import it.liguriadigitale.ponmetro.portale.presentation.util.EncrypterUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class RichiestaRicorsoAlPrefettoPage extends LayoutBasePage {

  private static final long serialVersionUID = -2581441254580210198L;

  public RichiestaRicorsoAlPrefettoPage(DettaglioVerbale dettaglioVerbale) {
    super();

    if (Constants.DEPLOY == Constants.TIPO_DEPLOY.ESERCIZIO) {

      throw new RestartResponseAtInterceptPageException(
          new RedirectPage(urlRicorsoAlPrefetto(dettaglioVerbale)));
    }

    addOrReplace(
        new IntegrazioneFdCClassicFdC2Panel(
            "integrazionePanel", urlRicorsoAlPrefetto(dettaglioVerbale)));
  }

  public RichiestaRicorsoAlPrefettoPage() {
    super();

    if (Constants.DEPLOY == Constants.TIPO_DEPLOY.ESERCIZIO) {

      throw new RestartResponseAtInterceptPageException(
          new RedirectPage(BaseServiceImpl.FDC2_RICHIESTA_RICORSO_AL_PREFETTO));
    }

    addOrReplace(
        new IntegrazioneFdCClassicFdC2Panel(
            "integrazionePanel", BaseServiceImpl.FDC2_RICHIESTA_RICORSO_AL_PREFETTO));
  }

  private String urlRicorsoAlPrefetto(DettaglioVerbale dettaglioVerbale) {
    try {
      return String.format(
          "%s/%s",
          BaseServiceImpl.FDC2_RICHIESTA_RICORSO_AL_PREFETTO,
          EncrypterUtil.encrypt(dettaglioVerbale.getNumeroAvviso()));
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
