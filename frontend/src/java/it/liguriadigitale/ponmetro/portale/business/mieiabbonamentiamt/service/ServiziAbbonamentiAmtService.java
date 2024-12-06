package it.liguriadigitale.ponmetro.portale.business.mieiabbonamentiamt.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Informazioni;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Messaggio;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Problem;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Sanctions;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Scadenze;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Tickets;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.amt.SanzioniAmtEsteso;
import it.liguriadigitale.ponmetro.portale.pojo.amt.ScadenzaAmtEsteso;
import it.liguriadigitale.ponmetro.portale.pojo.amt.TesseraAmtEsteso;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto.NucleoFamiglia;
import java.io.IOException;
import java.util.List;

public interface ServiziAbbonamentiAmtService {

  Tickets getAbbonamentiAmt(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  Tickets getAbbonamentiAmtPerPreloadingPage(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  List<TesseraAmtEsteso> listaTuttiAbbonamentiAmt(NucleoFamiglia nucleoFamiglia)
      throws BusinessException, ApiException, IOException;

  List<TesseraAmtEsteso> listaTuttiAbbonamentiAmtOrdinatiPerDataDiscendente(
      NucleoFamiglia nucleoFamiglia) throws BusinessException, ApiException, IOException;

  Informazioni getMessaggi(String codiceFiscale, String tipo)
      throws BusinessException, ApiException, IOException;

  List<Messaggio> getTuttiMessaggi(NucleoFamiglia abbonamnucleoFamigliaentiAmt, String tipo)
      throws BusinessException, ApiException, IOException;

  Problem getStatus() throws BusinessException, ApiException, IOException;

  List<BreadcrumbFdC> getListaBreadcrumb();

  List<MessaggiInformativi> popolaListaMessaggi(NucleoFamiglia nucleoFamiglia, String tipo)
      throws BusinessException, ApiException, IOException;

  List<MessaggiInformativi> popolaListaMessaggiInfoSanzioni();

  public List<ScadenzaAmtEsteso> listaTuttiAbbonamentiAmtScadenze(NucleoFamiglia nucleoFamiglia)
      throws BusinessException, ApiException, IOException;

  public Scadenze getAbbonamentiAmtPerPreloadingPageScadenze(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  NucleoFamiglia inizializzaNucleoFamigliaAMT(Utente utente) throws BusinessException, ApiException;

  List<ComponenteNucleo> inizializzaListaFamigliaAMT(Utente utente)
      throws BusinessException, ApiException;

  Sanctions getSanzioni(String codiceFiscale) throws BusinessException, ApiException, IOException;

  List<SanzioniAmtEsteso> listaTutteSanzioniAmt(NucleoFamiglia nucleoFamiglia)
      throws BusinessException, ApiException, IOException;

  String getValoreDaDb(String chiave);
}
