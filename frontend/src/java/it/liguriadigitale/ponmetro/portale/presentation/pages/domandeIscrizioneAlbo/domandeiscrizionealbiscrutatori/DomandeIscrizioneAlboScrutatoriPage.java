package it.liguriadigitale.ponmetro.portale.presentation.pages.domandeIscrizioneAlbo.domandeiscrizionealbiscrutatori;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.controlli.albi.model.Elettorali;
import it.liguriadigitale.ponmetro.controlli.albi.model.Schedario;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DomandeInviate;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DomandeInviateRecordsInner;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.TipologiaRichiestaEnum;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.domandeIscrizioneAlbo.panel.DomandeIscrizioneAlboPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DomandeIscrizioneAlboScrutatoriPage extends LayoutNoFeedBackPage {

  private List<DomandeInviateRecordsInner> rinnovi;
  private List<DomandeInviateRecordsInner> iscrizioni;
  private List<DomandeInviateRecordsInner> cancellazioni;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public DomandeIscrizioneAlboScrutatoriPage() {

    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    TipologiaRichiestaEnum tipologia = TipologiaRichiestaEnum.SCRUTATORI;

    List<DomandeInviateRecordsInner> pratiche =
        listaCompletaPraticheScrutatori(getUtente().getCodiceFiscaleOperatore(), tipologia);

    boolean isRichiestaVisibile = mostraBottone(pratiche, getUtente());
    rinnovi = estraiRinnovi(pratiche);
    iscrizioni = estraiIscrizioni(pratiche);
    cancellazioni = estraiCancellazioni(pratiche);

    log.debug("RINNOVI = " + rinnovi);
    log.debug("CANCELLAZIONI = " + cancellazioni);
    log.debug("ISCRIZIONI = " + iscrizioni);

    LinkDinamicoLaddaFunzione<Object> btnDomandaIscrizione =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaAlboIscrizioneScrutatori",
            new LinkDinamicoFunzioneData(
                "DomandeIscrizioneAlboScrutatoriPage.btnDomandaAlboIscrizioneScrutatori",
                "RichiestaIscrizioneAlboScrutatoriPage",
                "DomandeIscrizioneAlboScrutatoriPage.btnDomandaAlboIscrizioneScrutatori"),
            null,
            DomandeIscrizioneAlboScrutatoriPage.this,
            "color-yellow col-auto icon-foglio",
            isRichiestaVisibile);

    boolean bottoneRinnovo = mostraBottoneRinnovo(isRichiestaVisibile);

    LinkDinamicoLaddaFunzione<Object> btnDomandaRinnovo =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaAlboRinnovoScrutatori",
            new LinkDinamicoFunzioneData(
                "DomandeIscrizioneAlboScrutatoriPage.btnDomandaAlboRinnovoScrutatori",
                "RichiestaRinnovoAlboScrutatoriPage",
                "DomandeIscrizioneAlboScrutatoriPage.btnDomandaAlboRinnovoScrutatori"),
            null,
            DomandeIscrizioneAlboScrutatoriPage.this,
            "color-yellow col-auto icon-foglio",
            bottoneRinnovo);

    boolean bottoneCancellazione = mostraBottoneCancellazione();

    LinkDinamicoLaddaFunzione<Object> btnDomandaCancellazione =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaAlboCancellazioniScrutatori",
            new LinkDinamicoFunzioneData(
                "DomandeIscrizioneAlboScrutatoriPage.btnDomandaAlboCancellazioniScrutatori",
                "RichiestaCancellazioneAlboScrutatoriPage",
                "DomandeIscrizioneAlboScrutatoriPage.btnDomandaAlboCancellazioniScrutatori"),
            null,
            DomandeIscrizioneAlboScrutatoriPage.this,
            "color-yellow col-auto icon-foglio",
            bottoneCancellazione);

    addOrReplace(btnDomandaIscrizione);
    addOrReplace(btnDomandaRinnovo);
    addOrReplace(btnDomandaCancellazione);

    AlertBoxPanel<ErroreGenericoPage> boxControlli = fillBoxControlli(pratiche);
    addOrReplace(boxControlli);

    List<DomandeInviateRecordsInner> listPanel = new ArrayList<DomandeInviateRecordsInner>();
    boolean isScrutatore = isScrutatore(getUtente().getCodiceFiscaleOperatore());
    if (isScrutatore) {
      listPanel = rinnovi;
      listPanel.addAll(cancellazioni);
    } else {
      listPanel = iscrizioni;
    }

    DomandeIscrizioneAlboPanel domandeUnione =
        new DomandeIscrizioneAlboPanel(
            "domandeIscrizioneAlboScrutatoriPanel", listPanel, tipologia);
    addOrReplace(domandeUnione);
  }

  private boolean mostraBottoneCancellazione() {
    return isScrutatore(getUtente().getCodiceFiscaleOperatore()) && !hasCancellazioniViaCzrm();
  }

  private List<DomandeInviateRecordsInner> estraiIscrizioni(
      List<DomandeInviateRecordsInner> pratiche) {
    log.debug("estraiIscrizioni start, list [" + pratiche.size() + "]");
    return pratiche.stream()
        .filter(p -> isIscrizione(p.getTipoProcedimentoC()))
        .collect(Collectors.toList());
  }

  private List<DomandeInviateRecordsInner> estraiRinnovi(
      List<DomandeInviateRecordsInner> pratiche) {
    log.debug("estraiRinnovi start, list [" + pratiche.size() + "]");
    return pratiche.stream()
        .filter(p -> isRinnovo(p.getTipoProcedimentoC()))
        .collect(Collectors.toList());
  }

  private List<DomandeInviateRecordsInner> estraiCancellazioni(
      List<DomandeInviateRecordsInner> pratiche) {
    log.debug("estraiCancellazioni start, list [" + pratiche.size() + "]");
    return pratiche.stream()
        .filter(p -> isCancellazione(p.getTipoProcedimentoC()))
        .collect(Collectors.toList());
  }

  private boolean isPeriodoPerIscrizione() {
    LocalDate ora = LocalDate.now();
    String dataInizio = getValoreDaDbByChiave("SCRUTATORI_INIZIO");
    String dataFine = getValoreDaDbByChiave("SCRUTATORI_FINE");
    LocalDate ini = LocalDate.parse(dataInizio);
    LocalDate fin = LocalDate.parse(dataFine);
    return ora.isAfter(ini) && ora.isBefore(fin);
  }

  public String getValoreDaDbByChiave(String chiave) {
    log.debug("CP getValoreDaDbByChiave");

    String massimo = "";
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);
      if (LabelFdCUtil.checkIfNotNull(valore)) {
        massimo = valore.getValore();
      }
    } catch (BusinessException e) {
      log.error("Errore durante getValoreDaDbByChiave = " + e.getMessage(), e);
    } finally {
      instance.closeConnection();
    }

    return massimo;
  }

  private MessaggiInformativi fillMessaggioError(String testo) {
    MessaggiInformativi messaggio = new MessaggiInformativi();
    messaggio.setMessaggio(testo);
    messaggio.setType("info");
    return messaggio;
  }

  private boolean isEtaCorretta() {
    return LocalDateUtil.isMaggiorenne(getUtente().getDataDiNascita());
  }

  private boolean isElettore() {
    try {
      Schedario elettore =
          ServiceLocator.getInstance()
              .getControlloAlbi()
              .getSchedario(getUtente().getCodiceFiscaleOperatore());
      boolean isElettore = Optional.ofNullable(elettore).isPresent();
      return isElettore;
    } catch (BusinessException | IOException | ApiException e) {
      log.error("Errore durante mostraBottone in controllo albi = " + e.getMessage());
    }
    return false;
  }

  private boolean isRinnovo(String tipo) {
    log.debug(
        "isRinnovo [ Rinnovo Disponibilità Scrutatore != \""
            + tipo
            + "\" ] = "
            + tipo.equalsIgnoreCase("Rinnovo Disponibilità Scrutatore"));
    return tipo.equalsIgnoreCase("Rinnovo Disponibilità Scrutatore");
  }

  private boolean isIscrizione(String tipo) {
    log.debug(
        "isIscrizione [ Iscrizione Albo Scrutatore di seggio != \""
            + tipo
            + "\" ] = "
            + tipo.equalsIgnoreCase("Iscrizione Albo Scrutatore di seggio"));
    return tipo.equalsIgnoreCase("Iscrizione Albo Scrutatore di seggio");
  }

  private boolean isCancellazione(String tipo) {
    log.debug(
        "isCancellazione [ Disiscrizione Albo Scrutatore di seggio != \""
            + tipo
            + "\" ] = "
            + tipo.equalsIgnoreCase("Disiscrizione Albo Scrutatore di seggio"));
    return tipo.equalsIgnoreCase("Disiscrizione Albo Scrutatore di seggio");
  }

  private boolean hasRinnoviViaCzrm() {
    if (rinnovi != null && !rinnovi.isEmpty()) {
      return rinnovi.stream().anyMatch(elem -> isRichiestaAttiva(elem));
    }
    return false;
  }

  private boolean hasIscrizioniViaCzrm() {
    if (iscrizioni != null && !iscrizioni.isEmpty()) {
      return iscrizioni.stream().anyMatch(elem -> isRichiestaAttiva(elem));
    }
    return false;
  }

  private boolean hasCancellazioniViaCzrm() {
    if (cancellazioni != null && !cancellazioni.isEmpty()) {
      return cancellazioni.stream().anyMatch(elem -> isRichiestaAttiva(elem));
    }
    return false;
  }

  private boolean isRichiestaAttiva(DomandeInviateRecordsInner elem) {
    return elem != null
        && elem.getStatoFrontendC() != null
        && (elem.getStatoFrontendC().equalsIgnoreCase("Presentata")
            || elem.getStatoFrontendC().equalsIgnoreCase("Richiesta accolta")
            || elem.getStatoFrontendC().equalsIgnoreCase("Presa in carico"));
  }

  private boolean mostraBottoneRinnovo(boolean isRichiestaVisibile) {
    return !isRichiestaVisibile
        && !hasRinnoviViaCzrm()
        && isScrutatore(getUtente().getCodiceFiscaleOperatore())
        && isElettore()
        && isEtaCorretta()
        && isPresenteTornataPerTipo();
  }

  private boolean mostraBottone(List<DomandeInviateRecordsInner> iscrizioni, Utente utente) {
    boolean bottoneRichiestaVisibile = false;

    try {
      Schedario elettore =
          ServiceLocator.getInstance()
              .getControlloAlbi()
              .getSchedario(utente.getCodiceFiscaleOperatore());

      boolean isElettore = Optional.ofNullable(elettore).isPresent();
      boolean isScrutatore = isScrutatore(utente.getCodiceFiscaleOperatore());

      boolean isElettoreAbilitato = isElettore && !isScrutatore;

      boolean hasIscrizioneViaCzrm = hasIscrizioniViaCzrm();

      bottoneRichiestaVisibile =
          !hasIscrizioneViaCzrm
              && LocalDateUtil.isMaggiorenne(utente.getDataDiNascita())
              && isElettoreAbilitato
              && isPeriodoPerIscrizione();

    } catch (BusinessException | IOException | ApiException e) {
      log.error("Errore durante mostraBottone in controllo albi = " + e.getMessage());
    }

    return bottoneRichiestaVisibile;
  }

  private boolean isScrutatore(String codiceFiscale) {
    try {
      Elettorali scrutatore =
          ServiceLocator.getInstance().getControlloAlbi().getScrutatori(codiceFiscale);
      boolean isScrutatore = Optional.ofNullable(scrutatore).isPresent();
      log.debug("CP uffa scrutatore = " + scrutatore);
      return isScrutatore;
    } catch (BusinessException | IOException | ApiException e) {
      log.error("Errore durante mostraBottone in controllo albi = " + e.getMessage());
    }
    return false;
  }

  private boolean isPresenteTornataPerTipo() {
    return ServiceLocator.getInstance().getIscrizioniAlbi().isPresentTornataByTypo("scrutatori");
  }

  private AlertBoxPanel<ErroreGenericoPage> fillBoxControlli(
      List<DomandeInviateRecordsInner> pratiche) {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<MessaggiInformativi>();
    if (!isScrutatore(getUtente().getCodiceFiscaleOperatore()) && !hasIscrizioniViaCzrm()) {
      listaMessaggi.add(
          fillMessaggioError(getString("DomandeIscrizioneAlboScrutatoriPage.isScrutatore")));
      if (!isPeriodoPerIscrizione()) {
        listaMessaggi.add(
            fillMessaggioError(
                getString("DomandeIscrizioneAlboScrutatoriPage.isPeriodoPerIscrizione")));
      }
    }
    if (!isElettore()) {
      listaMessaggi.add(
          fillMessaggioError(getString("DomandeIscrizioneAlboScrutatoriPage.isElettore")));
    }
    if (!isPresenteTornataPerTipo() && isScrutatore(getUtente().getCodiceFiscaleOperatore())) {
      listaMessaggi.add(
          fillMessaggioError(
              getString("DomandeIscrizioneAlboScrutatoriPage.isPresenteTornataPerTipo")));
    }
    if (hasRinnoviViaCzrm()) {
      listaMessaggi.add(
          fillMessaggioError(
              getString("DomandeIscrizioneAlboScrutatoriPage.noRichiesteGiaPresenti")));
    }
    if (!isEtaCorretta()) {
      listaMessaggi.add(
          fillMessaggioError(getString("DomandeIscrizioneAlboScrutatoriPage.isEtaCorretta")));
    }
    AlertBoxPanel<ErroreGenericoPage> boxControlli =
        new AlertBoxPanel<ErroreGenericoPage>("boxControlli", listaMessaggi);
    boxControlli.setVisible(listaMessaggi != null && listaMessaggi.size() > 0);
    return boxControlli;
  }

  private List<DomandeInviateRecordsInner> listaCompletaPraticheScrutatori(
      String codiceFiscale, TipologiaRichiestaEnum tipologia) {
    log.debug("DomandeIscrizioneAlboScrutatoriPage popola");

    List<DomandeInviateRecordsInner> listaRecords = new ArrayList<DomandeInviateRecordsInner>();
    try {
      DomandeInviate dati =
          ServiceLocator.getInstance()
              .getIscrizioniAlbi()
              .getListaDomandeIscrizioniAlbi(codiceFiscale, TipologiaRichiestaEnum.SCRUTATORI);

      if (dati != null && dati.getRecords() != null) {
        listaRecords =
            dati.getRecords().stream()
                .sorted(Comparator.comparing(DomandeInviateRecordsInner::getCreatedDate).reversed())
                .collect(Collectors.toList());
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popola richieste iscrizioni albi: " + e.getMessage(), e);
    }
    return listaRecords;
  }
}
