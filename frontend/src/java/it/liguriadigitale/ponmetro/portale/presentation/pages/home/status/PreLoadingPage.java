package it.liguriadigitale.ponmetro.portale.presentation.pages.home.status;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Tessera;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Tickets;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.home.HomePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreLoadingPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1454597643088947240L;

  private static final String ERRORE_NEL_CARICAMENTO = "Errore nel caricamento di: ";

  private Utente utente;

  public PreLoadingPage() {
    super();
    utente = getUtente();

    getListaVeicoli("ioMiMuovo");
    getBiblioteche("ioLeggo");

    // TODO PER DOPO VERSIONE 1.8.X
    // getListaAbbonamentiAmtNucleoFamiliare("abbonamentiAmt");

    // scadenza amt solo del loggato
    getListaAbbonamentiAmtDelLoggato("abbonamentiAmt");
    // getListaAbbonamentiAmtDelLoggato();

    getListaPermessiAreaBlu("permessiGenovaParcheggi");

    setResponsePage(HomePage.class);
  }

  @SuppressWarnings("unused")
  private void getResidenza(String id) {

    Residente datiResidenza;
    try {
      datiResidenza =
          ServiceLocator.getInstance().getServizioDemografico().getDatiResidente(utente);
      utente.setDatiCittadinoResidente(datiResidenza);
      Utente.inizializzaDatiAnagrafici(datiResidenza, utente);
    } catch (BusinessException | ApiException e) {
      log.error(ERRORE_NEL_CARICAMENTO + " getResidenza()");
    }
  }

  private void getBiblioteche(String id) {

    try {
      utente.setListaUtenteBiblioteche(
          ServiceLocator.getInstance()
              .getServiziBiblioteche()
              .getUtenteByDocIdentita(utente.getCodiceFiscaleOperatore()));
      if (utente.getListaUtenteBiblioteche() != null
          && utente.getListaUtenteBiblioteche().size() == 1) {
        utente.setListaMovimentiBiblioteche(
            ServiceLocator.getInstance()
                .getServiziBiblioteche()
                .getMovimentiCorrentiUtenteById(utente.getCodiceFiscaleOperatore()));
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error(ERRORE_NEL_CARICAMENTO + " getBiblioteche()");
    }
  }

  private void getListaVeicoli(String id) {
    log.debug("getListaVeicoli");
    try {
      if (getUtente().isRichiestoRefreshVeicoliAttivi()) {
        getUtente()
            .setListaVeicoliAttivi(
                ServiceLocator.getInstance().getServiziMieiMezzi().getListaVeicoli(getUtente()));
        getUtente().setRichiestoRefreshVeicoliAttivi(false);
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error(ERRORE_NEL_CARICAMENTO + " getListaVeicoli()");
    }
  }

  private void getListaPermessiAreaBlu(String id) {
    log.debug("CP getListaPermessiAreaBlu");
    try {
      getUtente()
          .setListaPermessiAreaBlu(
              ServiceLocator.getInstance()
                  .getServiziGenovaParcheggi()
                  .getPermessiGeParkPerPreloadingPage(getUtente().getCodiceFiscaleOperatore()));
    } catch (BusinessException | ApiException | IOException e) {
      log.error(ERRORE_NEL_CARICAMENTO + " getListaPermessiAreaBlu()");
    }
  }

  private void getListaAbbonamentiAmtDelLoggato(String id) {
    log.debug("CP getListaAbbonamentiAmtDelLoggato");
    List<Tessera> listaAbbonamenti = new ArrayList<Tessera>();
    try {
      Tickets ticket =
          ServiceLocator.getInstance()
              .getServiziAbbonamentiAmt()
              .getAbbonamentiAmtPerPreloadingPage(getUtente().getCodiceFiscaleOperatore());
      if (LabelFdCUtil.checkIfNotNull(ticket)) {
        listaAbbonamenti = ticket.getTessere();
        getUtente().setListaAbbonamentiAmtDelLoggato(listaAbbonamenti);
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error(ERRORE_NEL_CARICAMENTO + " getListaAbbonamentiAmtNucleoFamiliare()");
    }
  }
}
