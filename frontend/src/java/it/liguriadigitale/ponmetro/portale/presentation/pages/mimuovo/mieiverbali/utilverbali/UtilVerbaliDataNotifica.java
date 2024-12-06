package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaNotifica;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Notifica;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilVerbaliDataNotifica {

  protected static Log log = LogFactory.getLog(UtilVerbaliDataNotifica.class);

  public static AnagraficaNotifica getAnagraficaUtente(
      DettaglioVerbale dettaglioVerbale, Utente utente) {
    AnagraficaNotifica anagraficaNotificaUtenteLoggato = null;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale)
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getAnagraficheNotifiche())
        && !dettaglioVerbale.getAnagraficheNotifiche().isEmpty()) {
      List<AnagraficaNotifica> listaAnagrafiche = dettaglioVerbale.getAnagraficheNotifiche();

      anagraficaNotificaUtenteLoggato =
          listaAnagrafiche.stream()
              .filter(
                  elem ->
                      UtilVerbali.checkIfNotNull(elem.getDatiAnagrafici())
                          && ((UtilVerbali.checkIfNotNull(elem.getDatiAnagrafici().getCpvTaxCode())
                                  && elem.getDatiAnagrafici()
                                      .getCpvTaxCode()
                                      .equalsIgnoreCase(utente.getCodiceFiscaleOperatore()))
                              || (UtilVerbali.checkIfNotNull(
                                      elem.getDatiAnagrafici().getCpvGivenName())
                                  && UtilVerbali.checkIfNotNull(
                                      elem.getDatiAnagrafici().getCpvFamilyName())
                                  && elem.getDatiAnagrafici()
                                      .getCpvGivenName()
                                      .equalsIgnoreCase(utente.getNome())
                                  && elem.getDatiAnagrafici()
                                      .getCpvFamilyName()
                                      .equalsIgnoreCase(utente.getCognome()))))
              .findAny()
              .orElse(null);
    }

    log.debug("CP anagraficaNotificaUtenteLoggato = " + anagraficaNotificaUtenteLoggato);

    return anagraficaNotificaUtenteLoggato;
  }

  public static boolean checkDataNotificaPresente(
      DettaglioVerbale dettaglioVerbale, Utente utente) {
    boolean dataNotificaPresente = false;

    if (UtilVerbali.checkIfNotNull(
        UtilVerbaliDataNotifica.getAnagraficaUtente(dettaglioVerbale, utente))) {
      AnagraficaNotifica anagraficaNotifica =
          UtilVerbaliDataNotifica.getAnagraficaUtente(dettaglioVerbale, utente);

      log.debug("CP anagraficaNotifica = " + anagraficaNotifica);

      if (UtilVerbali.checkIfNotNull(anagraficaNotifica.getDatiNotifica())
          && UtilVerbali.checkIfNotNull(anagraficaNotifica.getDatiNotifica().getDataNotifica())) {
        dataNotificaPresente = true;
      }
    }

    log.debug("CP dataNotificaPresente = " + dataNotificaPresente);

    return dataNotificaPresente;
  }

  public static Notifica getDataNotificaSeProprietarioVeicoloNonInAnagrafiche(
      DettaglioVerbale dettaglioVerbale) {
    Notifica notificaPiuRecente = null;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale)
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getAnagraficheNotifiche())
        && !dettaglioVerbale.getAnagraficheNotifiche().isEmpty()) {
      List<AnagraficaNotifica> listaAnagrafiche = dettaglioVerbale.getAnagraficheNotifiche();

      Comparator<Notifica> dataNotificaComparator =
          Comparator.nullsLast(Comparator.comparing(Notifica::getDataNotifica));
      notificaPiuRecente =
          listaAnagrafiche.stream()
              .filter(
                  elem ->
                      elem.getDatiNotifica() != null
                          && elem.getDatiNotifica().getDataNotifica() != null)
              .map(x -> x.getDatiNotifica())
              .max(dataNotificaComparator)
              .get();
      log.debug("CP notificaPiuRecente = " + notificaPiuRecente);
    }

    return notificaPiuRecente;
  }
}
