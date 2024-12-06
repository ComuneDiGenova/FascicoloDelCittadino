package it.liguriadigitale.ponmetro.portale.business.certificati.impl.anagrafe;

import it.liguriadigitale.ponmetro.servizianagrafici.model.CertificatoPersona;
import java.util.Comparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CertificatoPersonaComparator implements Comparator<CertificatoPersona> {

  private static final String RICH = "RICH;";
  private static Log log = LogFactory.getLog(CertificatoPersonaComparator.class);

  @Override
  public int compare(CertificatoPersona o1, CertificatoPersona o2) {
    return getIdentificativoNumerico(o2).compareTo(getIdentificativoNumerico(o1));
  }

  private Long getIdentificativoNumerico(CertificatoPersona o1) {

    String identificativoStringa = o1.getIdentificativoRichiesta();
    if (identificativoStringa.contains(RICH)) {
      identificativoStringa = identificativoStringa.replace(RICH, "");
    }
    try {
      return Long.parseLong(identificativoStringa);
    } catch (NumberFormatException e) {
      log.error("Errore di conversione: " + identificativoStringa);
      return 0L;
    }
  }
}
