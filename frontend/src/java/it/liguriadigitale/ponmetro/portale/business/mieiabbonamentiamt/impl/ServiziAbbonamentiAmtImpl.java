package it.liguriadigitale.ponmetro.portale.business.mieiabbonamentiamt.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Informazioni;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Messaggio;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Problem;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Sanctions;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Sanzione;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Scadenza;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Scadenze;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Tessera;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Tickets;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.mieiabbonamentiamt.service.ServiziAbbonamentiAmtService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.amt.SanzioniAmtEsteso;
import it.liguriadigitale.ponmetro.portale.pojo.amt.ScadenzaAmtEsteso;
import it.liguriadigitale.ponmetro.portale.pojo.amt.TesseraAmtEsteso;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.util.PojoUtils;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziConStatusPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto.NucleoFamiglia;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziAbbonamentiAmtImpl implements ServiziAbbonamentiAmtService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_AMT = "Errore di connessione alle API Abbonamenti AMT";

  @Override
  public Tickets getAbbonamentiAmt(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getAbbonamentiAmt: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiAbbonamentiAmt().getTicket(codiceFiscale);
    } catch (BusinessException e) {
      log.error("ServiziAbbonamentiAmtImpl -- getAbbonamentiAmt: errore API abbonamenti AMT:", e);
      throw new BusinessException(ERRORE_API_AMT);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAbbonamentiAmtImpl -- getAbbonamentiAmt: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAbbonamentiAmtImpl -- getAbbonamentiAmt: errore durante la chiamata delle API abbonamenti AMT ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("abbonamenti AMT"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<TesseraAmtEsteso> listaTuttiAbbonamentiAmt(NucleoFamiglia nucleoFamiglia)
      throws BusinessException, ApiException, IOException {
    log.debug("CP listaTuttiAbbonamentiAmt");

    List<TesseraAmtEsteso> listaTessereEsteso = new ArrayList<TesseraAmtEsteso>();
    List<TesseraAmtEsteso> listaTutteTessere = new ArrayList<TesseraAmtEsteso>();

    if (LabelFdCUtil.checkIfNotNull(nucleoFamiglia)) {
      for (ComponenteNucleo componenteNucleo : nucleoFamiglia.getListaCfNucleo()) {

        if (LabelFdCUtil.checkIfNotNull(componenteNucleo)
            && LabelFdCUtil.checkIfNotNull(componenteNucleo.getDatiCittadino())) {
          String taxCode = componenteNucleo.getDatiCittadino().getCpvTaxCode();
          String nominativo = getNominativoCapoFamiglia(componenteNucleo.getDatiCittadino());
          String nome = componenteNucleo.getDatiCittadino().getCpvGivenName();
          String cognome = componenteNucleo.getDatiCittadino().getCpvFamilyName();
          LocalDate dataNascita = componenteNucleo.getDatiCittadino().getCpvDateOfBirth();
          Tickets ticket = getAbbonamentiAmtPerPreloadingPage(taxCode);

          if (LabelFdCUtil.checkIfNotNull(ticket)) {

            List<Tessera> tessere = ticket.getTessere();
            String messaggioNuovaTessera = ticket.getMessaggioNuovaTessera();
            String urlNuovaTessera = ticket.getUrlNuovaTessera();

            List<Tessera> listaTessere = new ArrayList<Tessera>();
            if (LabelFdCUtil.checkIfNotNull(tessere) && !LabelFdCUtil.checkEmptyList(tessere)) {
              listaTessere = ticket.getTessere();

              listaTessereEsteso =
                  listaTessere.stream()
                      .map(
                          elem ->
                              PojoUtils.copyAndReturn(
                                  new TesseraAmtEsteso(taxCode, nominativo), elem))
                      .collect(Collectors.toList());

              listaTutteTessere.addAll(listaTessereEsteso);

            } else {
              TesseraAmtEsteso utenteSenzaAbbonamento =
                  new TesseraAmtEsteso(
                      taxCode,
                      nominativo,
                      nome,
                      cognome,
                      dataNascita,
                      messaggioNuovaTessera,
                      urlNuovaTessera);
              listaTutteTessere.add(utenteSenzaAbbonamento);
            }
          }
        }
      }
    }

    log.debug("CP lista tutte tessere = " + listaTutteTessere);

    return listaTutteTessere;
  }

  @Override
  public List<ScadenzaAmtEsteso> listaTuttiAbbonamentiAmtScadenze(NucleoFamiglia nucleoFamiglia)
      throws BusinessException, ApiException, IOException {
    log.debug("CP listaTuttiAbbonamentiAmt");

    List<ScadenzaAmtEsteso> listaTessereEsteso = new ArrayList<ScadenzaAmtEsteso>();
    List<ScadenzaAmtEsteso> listaTutteTessere = new ArrayList<ScadenzaAmtEsteso>();

    if (LabelFdCUtil.checkIfNotNull(nucleoFamiglia)) {
      for (ComponenteNucleo componenteNucleo : nucleoFamiglia.getListaCfNucleo()) {

        if (LabelFdCUtil.checkIfNotNull(componenteNucleo)
            && LabelFdCUtil.checkIfNotNull(componenteNucleo.getDatiCittadino())) {
          String taxCode = componenteNucleo.getDatiCittadino().getCpvTaxCode();
          String nominativo = getNominativoCapoFamiglia(componenteNucleo.getDatiCittadino());

          Scadenze scadenza = getAbbonamentiAmtPerPreloadingPageScadenze(taxCode);

          if (LabelFdCUtil.checkIfNotNull(scadenza)) {

            List<Scadenza> scadenze = scadenza.getScadenzaTessere();

            List<Scadenza> listaScadenze = new ArrayList<Scadenza>();
            if (LabelFdCUtil.checkIfNotNull(scadenze) && !LabelFdCUtil.checkEmptyList(scadenze)) {
              listaScadenze = scadenza.getScadenzaTessere();

              listaTessereEsteso =
                  listaScadenze.stream()
                      .map(
                          elem ->
                              PojoUtils.copyAndReturn(
                                  new ScadenzaAmtEsteso(nominativo, taxCode), elem))
                      .collect(Collectors.toList());

              listaTutteTessere.addAll(listaTessereEsteso);

            } else {
              ScadenzaAmtEsteso utenteSenzaAbbonamento = new ScadenzaAmtEsteso(nominativo, taxCode);
              listaTutteTessere.add(utenteSenzaAbbonamento);
            }
          }
        }
      }
    }

    log.debug("CP lista tutte tessere = " + listaTutteTessere);

    return listaTutteTessere;
  }

  private String getNominativoCapoFamiglia(Residente residente) {
    StringBuilder builder = new StringBuilder();
    return builder
        .append(residente.getCpvFamilyName())
        .append(" ")
        .append(residente.getCpvGivenName())
        .toString();
  }

  @Override
  public List<TesseraAmtEsteso> listaTuttiAbbonamentiAmtOrdinatiPerDataDiscendente(
      NucleoFamiglia nucleoFamiglia) throws BusinessException, ApiException, IOException {
    log.debug("CP listaTuttiAbbonamentiAmtOrdinatiPerDataDiscendente");

    List<TesseraAmtEsteso> listaTuttiAbbonamentiOrdinati = new ArrayList<TesseraAmtEsteso>();

    List<TesseraAmtEsteso> listaTuttiAbbonamenti = listaTuttiAbbonamentiAmt(nucleoFamiglia);

    List<TesseraAmtEsteso> listaTuttiAbbonamentiConData = new ArrayList<TesseraAmtEsteso>();
    List<TesseraAmtEsteso> listaTuttiAbbonamentiSenzaData = new ArrayList<TesseraAmtEsteso>();

    listaTuttiAbbonamentiConData =
        listaTuttiAbbonamenti.stream()
            .filter(elem -> elem.getFineValidita() != null)
            .collect(Collectors.toList());

    listaTuttiAbbonamentiSenzaData =
        listaTuttiAbbonamenti.stream()
            .filter(elem -> elem.getFineValidita() == null)
            .collect(Collectors.toList());

    listaTuttiAbbonamentiOrdinati =
        listaTuttiAbbonamentiConData.stream()
            .sorted(
                Comparator.nullsLast(Comparator.comparing(TesseraAmtEsteso::getFineValidita))
                    .reversed())
            .collect(Collectors.toList());

    listaTuttiAbbonamentiOrdinati.addAll(listaTuttiAbbonamentiSenzaData);

    return listaTuttiAbbonamentiOrdinati;
  }

  @Override
  public Informazioni getMessaggi(String codiceFiscale, String tipo)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getMessaggi");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiAbbonamentiAmt().getMessage(codiceFiscale, tipo);
    } catch (BusinessException e) {
      log.error("ServiziAbbonamentiAmtImpl -- getMessaggi: errore API abbonamenti AMT:", e);
      throw new BusinessException(ERRORE_API_AMT);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAbbonamentiAmtImpl -- getMessaggi: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAbbonamentiAmtImpl -- getMessaggi: errore durante la chiamata delle API abbonamenti AMT ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("abbonamenti AMT"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Messaggio> getTuttiMessaggi(NucleoFamiglia nucleoFamiglia, String tipo)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getTuttiMessaggi " + tipo);

    List<Messaggio> listaTuttiMessaggi = new ArrayList<Messaggio>();

    if (LabelFdCUtil.checkIfNotNull(nucleoFamiglia)) {
      for (ComponenteNucleo componenteNucleo : nucleoFamiglia.getListaCfNucleo()) {

        if (LabelFdCUtil.checkIfNotNull(componenteNucleo)
            && LabelFdCUtil.checkIfNotNull(componenteNucleo.getDatiCittadino())) {
          String taxCode = componenteNucleo.getDatiCittadino().getCpvTaxCode();

          Informazioni info = getMessaggi(taxCode, tipo);

          if (LabelFdCUtil.checkIfNotNull(info)) {

            List<Messaggio> listaMessaggi = info.getMessaggi();
            if (LabelFdCUtil.checkIfNotNull(listaMessaggi)
                && !LabelFdCUtil.checkEmptyList(listaMessaggi)) {
              listaTuttiMessaggi.addAll(listaMessaggi);
            }
          }
        }
      }
    }

    return listaTuttiMessaggi;
  }

  @Override
  public Problem getStatus() throws BusinessException, ApiException, IOException {
    log.debug("CP getStatus");
    Problem problemResult = new Problem();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      problemResult = instance.getApiAbbonamentiAmt().status();
      return problemResult;
    } catch (BusinessException e) {
      log.error("ServiziAbbonamentiAmtImpl -- getStatus: errore API abbonamenti AMT:", e);
      throw new BusinessException(ERRORE_API_AMT);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAbbonamentiAmtImpl -- getStatus: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAbbonamentiAmtImpl -- getStatus: errore durante la chiamata delle API abbonamenti AMT ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziConStatusPage(
              "abbonamenti AMT", problemResult.getTitle(), problemResult.getDetail()));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioMiMuovo", "io Mi Muovo"));
    listaBreadcrumb.add(new BreadcrumbFdC("iMieiAbbonamentiAMT", "i Miei Abbonamenti AMT"));

    return listaBreadcrumb;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggi(NucleoFamiglia nucleoFamiglia, String tipo)
      throws BusinessException, ApiException, IOException {
    log.debug("CP popolaListaMessaggi = " + nucleoFamiglia.getListaCfNucleo());

    List<Messaggio> listaTuttiMessaggi = getTuttiMessaggi(nucleoFamiglia, tipo);
    List<MessaggiInformativi> listaMessaggiDaMostrare = new ArrayList<MessaggiInformativi>();
    List<MessaggiInformativi> listaMessaggiSenzaDoppioni = new ArrayList<MessaggiInformativi>();

    if (LabelFdCUtil.checkIfNotNull(listaTuttiMessaggi)
        && !LabelFdCUtil.checkEmptyList(listaTuttiMessaggi)) {
      for (Messaggio messaggio : listaTuttiMessaggi) {
        MessaggiInformativi elem = new MessaggiInformativi();
        elem.setType(messaggio.getType());
        elem.setMessaggio(messaggio.getTitle());
        listaMessaggiDaMostrare.add(elem);
      }
    }

    listaMessaggiSenzaDoppioni =
        listaMessaggiDaMostrare.stream()
            .filter(distinctByKey(elem -> elem.getMessaggio()))
            .collect(Collectors.toList());

    return listaMessaggiSenzaDoppioni;
  }

  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  @Override
  public Tickets getAbbonamentiAmtPerPreloadingPage(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getAbbonamentiAmtPerPreloadingPage");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiAbbonamentiAmt().getTicket(codiceFiscale);
    } catch (BusinessException e) {
      log.error("ServiziAbbonamentiAmtImpl -- getAbbonamentiAmt: errore API abbonamenti AMT:", e);
      throw new BusinessException(ERRORE_API_AMT);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAbbonamentiAmtImpl -- getAbbonamentiAmt: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAbbonamentiAmtImpl -- getAbbonamentiAmt: errore durante la chiamata delle API abbonamenti AMT ",
          e);
      return null;
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Scadenze getAbbonamentiAmtPerPreloadingPageScadenze(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getAbbonamentiAmtPerPreloadingPage");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiAbbonamentiAmt().getScadenze(codiceFiscale);
    } catch (BusinessException e) {
      log.error("ServiziAbbonamentiAmtImpl -- getAbbonamentiAmt: errore API abbonamenti AMT:", e);
      throw new BusinessException(ERRORE_API_AMT);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAbbonamentiAmtImpl -- getAbbonamentiAmt: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAbbonamentiAmtImpl -- getAbbonamentiAmt: errore durante la chiamata delle API abbonamenti AMT ",
          e);
      return null;
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiInfoSanzioni() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio(
        "Informativa privacy ai sensi degli artt. 13 e 14 del regolamento (UE) 2016/679 del GDPR (General Data Protection Regulation) e della normativa nazionale.<br>Le basi giuridiche correlate alle finalità del trattamento sono:<br><ul><li>l’esecuzione di un pubblico interesse ai sensi dell’art. 6 par. 1 lett. e) GDPR;</li><li>il perseguimento del legittimo interesse ai sensi dell’art. 6 par. 1 lett. f) GDPR;</li></ul><br>Modalità del trattamento: il trattamento è effettuato con strumenti informatici e cartacei nel rispetto del principio di minimizzazione dei dati. <br>Periodo di conservazione dei dati:<br>I dati saranno conservati per 10 anni come da normativa vigente. In presenza di contenzioso i suoi dati saranno conservati per i tempi necessari alla risoluzione.<br>Il TITOLARE del trattamento dei dati è AMT S.p.A. Azienda Mobilità e Trasporti, Via Montaldo, 2 – 16137 Genova.<br>AMT Azienda Mobilità e Trasporti di Genova S.p.A. ha individuato un Responsabile per la Protezione dei dati personali (RPD / DPO come definito dal GDPR all’art. 37) a cui è possibile fare in qualsiasi momento personali (RPD / DPO come definito dal GDPR all’art. 37) a cui è possibile fare in qualsiasi momento riferimento per richieste relative ai propri dati personali ed il rispetto della propria privacy (GDPR Capo III - Diritti dell’Interessato), scrivendo all’indirizzo <a href=\"mailto: rpd@amt.genova.it\">rpd@amt.genova.it</a>.<br>Diritto di reclamo: qualora non avesse ricevuto una risposta oppure la risposta non fosse soddisfacente, lei hai il diritto di proporre reclamo a <a href=\"https:/www.garanteprivacy.it/\" target=\"_blank\">Autorità di controllo italiana</a> o adire le sedi giudiziarie ai sensi dell'art. 79 del GDPR.<br>Per leggere e comprendere l’informativa cliccare sul seguente <a href=\"https://www.amt.genova.it/amt/amt-istituzionale/privacy/\" target=\"_blank\">link</a> che riporterà al sito del titolare");
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public List<ComponenteNucleo> inizializzaListaFamigliaAMT(Utente utente)
      throws BusinessException, ApiException {
    List<ComponenteNucleo> listaComponenteNucleoFiltratoLoggatoConMinorenni =
        new ArrayList<ComponenteNucleo>();

    List<ComponenteNucleo> listaFigliMinoriConviventi =
        utente.listaFigliInNucleoAllargatoCoresidentiUnder18();

    listaComponenteNucleoFiltratoLoggatoConMinorenni = listaFigliMinoriConviventi;
    listaComponenteNucleoFiltratoLoggatoConMinorenni.add(
        utente.getUtenteLoggatoComeComponenteNucleo());

    return listaComponenteNucleoFiltratoLoggatoConMinorenni;
  }

  @Override
  public NucleoFamiglia inizializzaNucleoFamigliaAMT(Utente utente)
      throws BusinessException, ApiException {
    NucleoFamiglia nucleoFamiglia = new NucleoFamiglia();

    List<ComponenteNucleo> listaComponenteNucleoFiltratoLoggatoConMinorenni =
        inizializzaListaFamigliaAMT(utente);

    nucleoFamiglia.addListaCfNucleo(listaComponenteNucleoFiltratoLoggatoConMinorenni);
    return nucleoFamiglia;
  }

  @Override
  public Sanctions getSanzioni(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getSanzioni: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiAbbonamentiAmt().getSanctions(codiceFiscale);
    } catch (BusinessException e) {
      log.error("ServiziAbbonamentiAmtImpl -- getSanzioni: errore API abbonamenti AMT:", e);
      throw new BusinessException(ERRORE_API_AMT);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziAbbonamentiAmtImpl -- getSanzioni: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziAbbonamentiAmtImpl -- getSanzioni: errore durante la chiamata delle API abbonamenti AMT ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("sanzioni AMT"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<SanzioniAmtEsteso> listaTutteSanzioniAmt(NucleoFamiglia nucleoFamiglia)
      throws BusinessException, ApiException, IOException {
    log.debug("CP listaTutteSanzioniAmt");

    List<SanzioniAmtEsteso> listaSanzioniEsteso = new ArrayList<SanzioniAmtEsteso>();
    List<SanzioniAmtEsteso> listaTutteSanzioni = new ArrayList<SanzioniAmtEsteso>();

    if (LabelFdCUtil.checkIfNotNull(nucleoFamiglia)) {
      for (ComponenteNucleo componenteNucleo : nucleoFamiglia.getListaCfNucleo()) {

        if (LabelFdCUtil.checkIfNotNull(componenteNucleo)
            && LabelFdCUtil.checkIfNotNull(componenteNucleo.getDatiCittadino())) {

          String taxCode = componenteNucleo.getDatiCittadino().getCpvTaxCode();
          String nominativo = getNominativoCapoFamiglia(componenteNucleo.getDatiCittadino());

          Sanctions sanzioni = getSanzioni(taxCode);

          List<Sanzione> listaSanzioni = new ArrayList<Sanzione>();
          if (LabelFdCUtil.checkIfNotNull(sanzioni)
              && !LabelFdCUtil.checkEmptyList(sanzioni.getSanzioni())) {
            listaSanzioni = sanzioni.getSanzioni();

            listaSanzioniEsteso =
                listaSanzioni.stream()
                    .map(
                        elem ->
                            PojoUtils.copyAndReturn(
                                new SanzioniAmtEsteso(taxCode, nominativo), elem))
                    .collect(Collectors.toList());

            listaTutteSanzioni.addAll(listaSanzioniEsteso);
          }
        }
      }
    }

    log.debug("CP lista tutte sanzioni = " + listaTutteSanzioni);

    return listaTutteSanzioni;
  }

  @Override
  public String getValoreDaDb(String chiave) {
    String data = "";
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);
      if (LabelFdCUtil.checkIfNotNull(valore)) {
        data = valore.getValore();
      }
    } catch (BusinessException e) {
      log.error("Errore durante get in AMT da DB = " + e.getMessage(), e);
    } finally {
      instance.closeConnection();
    }

    return data;
  }
}
