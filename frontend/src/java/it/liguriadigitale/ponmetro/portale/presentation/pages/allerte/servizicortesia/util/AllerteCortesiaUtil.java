package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.util;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServiziResponse;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class AllerteCortesiaUtil {

  private static Log log = LogFactory.getLog(AllerteCortesiaUtil.class);

  public static String decodificaBooleanoComeString(String valore) {
    String decodifica = "";
    if (PageUtil.isStringValid(valore)) {
      if (valore.equalsIgnoreCase("S")) {
        decodifica = "Sì";
      } else {
        decodifica = "No";
      }
    }
    return decodifica;
  }

  public static VerificaServiziResponse popolaDettagliServizio(String servizio, String email) {
    VerificaServiziResponse verificaServizi = null;
    try {
      verificaServizi =
          ServiceLocator.getInstance()
              .getServiziAllerteCortesia()
              .getWsGetServizioById(email, servizio);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte Servizi Cortesia"));
    }

    return verificaServizi;
  }
}
