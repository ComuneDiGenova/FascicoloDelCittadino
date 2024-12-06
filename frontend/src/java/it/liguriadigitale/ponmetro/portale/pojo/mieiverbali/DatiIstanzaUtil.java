package it.liguriadigitale.ponmetro.portale.pojo.mieiverbali;

import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaNotifica;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DatiIstanzaUtil {

  private static Log log = LogFactory.getLog(DatiIstanzaUtil.class);

  public static BigDecimal getCodiceHermesAnagrafica(
      DettaglioVerbale dettaglioVerbale, String codiceFiscale) {

    log.error("DatiIstanzaUtil -- getCodiceHermesAnagrafica: ");

    try {
      List<AnagraficaNotifica> listAnagraficaNotifica = dettaglioVerbale.getAnagraficheNotifiche();
      log.error(
          "7777 3 ServiziMieiVerbaliImpl -- getCodiceHermesAnagrafica getAnagraficheNotifiche: "
              + dettaglioVerbale.getAnagraficheNotifiche());
      AnagraficaNotifica anagraficaNotifica =
          listAnagraficaNotifica.stream()
              .filter(
                  elemento ->
                      elemento.getDatiAnagrafici().getCpvTaxCode().equalsIgnoreCase(codiceFiscale))
              .findFirst()
              .orElse(null);

      return anagraficaNotifica != null
          ? anagraficaNotifica.getCodiceAnagraficaHermes()
          : new BigDecimal(0);
    } catch (Exception e) {
      log.error(
          "7777 9999  ServiziMieiVerbaliImpl -- getCodiceHermesAnagrafica " + e.getStackTrace());
      return new BigDecimal(0);
    }
  }
}
