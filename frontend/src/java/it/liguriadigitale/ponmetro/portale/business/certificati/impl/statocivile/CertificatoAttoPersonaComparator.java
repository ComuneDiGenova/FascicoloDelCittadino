package it.liguriadigitale.ponmetro.portale.business.certificati.impl.statocivile;

import it.liguriadigitale.ponmetro.servizianagrafici.model.CertificatoAttoPersona;
import java.util.Comparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CertificatoAttoPersonaComparator implements Comparator<CertificatoAttoPersona> {

  private static Log log = LogFactory.getLog(CertificatoAttoPersonaComparator.class);

  private static final String RICH = "RICH;";

  @Override
  public int compare(CertificatoAttoPersona o1, CertificatoAttoPersona o2) {

    return getIdentificativoNumerico(o2).compareTo(getIdentificativoNumerico(o1));
  }

  private Long getIdentificativoNumerico(CertificatoAttoPersona o1) {

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
