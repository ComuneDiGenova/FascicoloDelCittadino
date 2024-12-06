package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.form;

import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;

public final class UtilIstanze {

  private static final String CODICE_RATEIZZAZIONE = "43";

  public static boolean isIstanzaRateizzazione(Istanza istanza) {
    return istanza.getMotivazioni().stream()
        .anyMatch(x -> x.getCodice().equals(CODICE_RATEIZZAZIONE));
  }
}
