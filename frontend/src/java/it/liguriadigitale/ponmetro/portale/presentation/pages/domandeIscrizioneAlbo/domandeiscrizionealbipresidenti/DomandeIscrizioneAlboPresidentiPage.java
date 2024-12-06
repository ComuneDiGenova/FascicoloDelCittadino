package it.liguriadigitale.ponmetro.portale.presentation.pages.domandeIscrizioneAlbo.domandeiscrizionealbipresidenti;

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

public class DomandeIscrizioneAlboPresidentiPage extends LayoutNoFeedBackPage {

  private List<DomandeInviateRecordsInner> rinnovi;
  private List<DomandeInviateRecordsInner> iscrizioni;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public DomandeIscrizioneAlboPresidentiPage() {

    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    TipologiaRichiestaEnum tipologia = TipologiaRichiestaEnum.PRESIDENTI;

    List<DomandeInviateRecordsInner> pratiche =
        listaCompletaPratichePresidenti(getUtente().getCodiceFiscaleOperatore(), tipologia);

    rinnovi = estraiRinnovi(pratiche);
    iscrizioni = estraiIscrizioni(pratiche);

    boolean isRichiestaVisibile = mostraBottone(getUtente());

    LinkDinamicoLaddaFunzione<Object> btnDomandaIscrizione =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaAlboIscrizionePresidenti",
            new LinkDinamicoFunzioneData(
                "DomandeIscrizioneAlboPresidentiPage.btnDomandaAlboIscrizionePresidenti",
                "RichiestaIscrizioneAlboPresidentiPage",
                "DomandeIscrizioneAlboPresidentiPage.btnDomandaAlboIscrizionePresidenti"),
            null,
            DomandeIscrizioneAlboPresidentiPage.this,
            "color-yellow col-auto icon-foglio",
            isRichiestaVisibile);

    boolean bottoneRinnovo = mostraBottoneRinnovo(isRichiestaVisibile);

    LinkDinamicoLaddaFunzione<Object> btnDomandaRinnovo =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaAlboRinnovoPresidenti",
            new LinkDinamicoFunzioneData(
                "DomandeIscrizioneAlboPresidentiPage.btnDomandaAlboRinnovoPresidenti",
                "RichiestaRinnovoAlboPresidentiPage",
                "DomandeIscrizioneAlboPresidentiPage.btnDomandaAlboRinnovoPresidenti"),
            null,
            DomandeIscrizioneAlboPresidentiPage.this,
            "color-yellow col-auto icon-foglio",
            bottoneRinnovo);

    addOrReplace(btnDomandaIscrizione);
    addOrReplace(btnDomandaRinnovo);

    AlertBoxPanel<ErroreGenericoPage> boxControlli = fillBoxControlli(pratiche);
    addOrReplace(boxControlli);

    List<DomandeInviateRecordsInner> listPanel = new ArrayList<DomandeInviateRecordsInner>();
    boolean isPresidente = isPresidente(getUtente().getCodiceFiscaleOperatore());
    if (isPresidente) {
      listPanel = rinnovi;
    } else {
      listPanel = iscrizioni;
    }

    DomandeIscrizioneAlboPanel domandeUnione =
        new DomandeIscrizioneAlboPanel(
            "domandeIscrizioneAlboPresidentiPanel", listPanel, tipologia);
    addOrReplace(domandeUnione);
  }

  private boolean mostraBottoneRinnovo(boolean isRichiestaVisibile) {
    return !isRichiestaVisibile
        && !hasRinnoviViaCzrm()
        && isPresidente(getUtente().getCodiceFiscaleOperatore())
        && isElettore()
        && isEtaCorretta()
        && isPresenteTornataPerTipo();
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

  private boolean isRichiestaAttiva(DomandeInviateRecordsInner elem) {
    return elem != null
        && elem.getStatoFrontendC() != null
        && (elem.getStatoFrontendC().equalsIgnoreCase("Presentata")
            || elem.getStatoFrontendC().equalsIgnoreCase("Richiesta accolta")
            || elem.getStatoFrontendC().equalsIgnoreCase("Presa in carico"));
  }

  private List<DomandeInviateRecordsInner> estraiIscrizioni(
      List<DomandeInviateRecordsInner> pratiche) {
    return pratiche.stream()
        .filter(p -> isIscrizione(p.getTipoProcedimentoC()))
        .collect(Collectors.toList());
  }

  private List<DomandeInviateRecordsInner> estraiRinnovi(
      List<DomandeInviateRecordsInner> pratiche) {
    return pratiche.stream()
        .filter(p -> isRinnovo(p.getTipoProcedimentoC()))
        .collect(Collectors.toList());
  }

  private AlertBoxPanel<ErroreGenericoPage> fillBoxControlli(
      List<DomandeInviateRecordsInner> pratiche) {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<MessaggiInformativi>();
    if (!isPresidente(getUtente().getCodiceFiscaleOperatore()) && !hasIscrizioniViaCzrm()) {
      listaMessaggi.add(
          fillMessaggioError(getString("DomandeIscrizioneAlboPresidentiPage.isPresidente")));
      if (!isPeriodoPerIscrizione()) {
        listaMessaggi.add(
            fillMessaggioError(
                getString("DomandeIscrizioneAlboPresidentiPage.isPeriodoPerIscrizione")));
      }
    }
    if (!isElettore()) {
      listaMessaggi.add(
          fillMessaggioError(getString("DomandeIscrizioneAlboPresidentiPage.isElettore")));
    }
    if (!isPresenteTornataPerTipo() && isPresidente(getUtente().getCodiceFiscaleOperatore())) {
      listaMessaggi.add(
          fillMessaggioError(
              getString("DomandeIscrizioneAlboPresidentiPage.isPresenteTornataPerTipo")));
    }
    if (hasRinnoviViaCzrm()) {
      listaMessaggi.add(
          fillMessaggioError(
              getString("DomandeIscrizioneAlboPresidentiPage.noRichiesteGiaPresenti")));
    }
    if (!isEtaCorretta()) {
      listaMessaggi.add(
          fillMessaggioError(getString("DomandeIscrizioneAlboPresidentiPage.isEtaCorretta")));
    }
    AlertBoxPanel<ErroreGenericoPage> boxControlli =
        new AlertBoxPanel<ErroreGenericoPage>("boxControlli", listaMessaggi);
    boxControlli.setVisible(listaMessaggi != null && listaMessaggi.size() > 0);
    return boxControlli;
  }

  private boolean isPeriodoPerIscrizione() {
    LocalDate ora = LocalDate.now();
    String dataInizio = getValoreDaDbByChiave("PRESIDENTI_INIZIO");
    String dataFine = getValoreDaDbByChiave("PRESIDENTI_FINE");
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
    boolean isEtaCompresaTra18e70 =
        getUtente().getDataDiNascita() != null
            ? LocalDateUtil.isMaggiorenne(getUtente().getDataDiNascita())
                && !LocalDateUtil.isMaggiore70anni(getUtente().getDataDiNascita())
            : false;
    return isEtaCompresaTra18e70;
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

  private List<DomandeInviateRecordsInner> listaCompletaPratichePresidenti(
      String codiceFiscale, TipologiaRichiestaEnum tipologia) {
    log.debug("DomandeIscrizioneAlboPresidentiPage popola");

    List<DomandeInviateRecordsInner> listaRecords = new ArrayList<DomandeInviateRecordsInner>();
    try {

      DomandeInviate dati =
          ServiceLocator.getInstance()
              .getIscrizioniAlbi()
              .getListaDomandeIscrizioniAlbi(codiceFiscale, TipologiaRichiestaEnum.PRESIDENTI);

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

  private boolean isIscrizione(String tipo) {
    return tipo.equalsIgnoreCase("Iscrizione Albo Presidente di seggio");
  }

  private boolean isRinnovo(String tipo) {
    return tipo.equalsIgnoreCase("Rinnovo Disponibilità Presidente");
  }

  private boolean mostraBottone(Utente utente) {
    boolean bottoneRichiestaVisibile = false;

    boolean isEtaCompresaTra18e70 =
        utente.getDataDiNascita() != null
            ? LocalDateUtil.isMaggiorenne(utente.getDataDiNascita())
                && !LocalDateUtil.isMaggiore70anni(utente.getDataDiNascita())
            : false;

    try {
      Schedario elettore =
          ServiceLocator.getInstance()
              .getControlloAlbi()
              .getSchedario(utente.getCodiceFiscaleOperatore());

      log.debug("CP elettore = " + elettore);

      boolean isPresidente = isPresidente(utente.getCodiceFiscaleOperatore());
      boolean hasIscrizioneViaCzrm = hasIscrizioniViaCzrm();

      boolean isElettore = Optional.ofNullable(elettore).isPresent();
      boolean isElettoreAbilitato = isElettore && !isPresidente;

      bottoneRichiestaVisibile =
          !hasIscrizioneViaCzrm
              && isEtaCompresaTra18e70
              && isElettoreAbilitato
              && isPeriodoPerIscrizione();

    } catch (BusinessException | IOException | ApiException e) {
      log.error("Errore durante mostraBottone in controllo albi = " + e.getMessage());
    }

    return bottoneRichiestaVisibile;
  }

  private boolean isPresidente(String codiceFiscale) {
    try {
      Elettorali presidente =
          ServiceLocator.getInstance().getControlloAlbi().getPresidenti(codiceFiscale);
      boolean isPresidente = Optional.ofNullable(presidente).isPresent();
      log.debug("CP uffa presidente = " + presidente);
      return isPresidente;
    } catch (BusinessException | IOException | ApiException e) {
      log.error("Errore durante mostraBottone in controllo albi = " + e.getMessage());
    }
    return false;
  }

  private boolean isPresenteTornataPerTipo() {
    return ServiceLocator.getInstance().getIscrizioniAlbi().isPresentTornataByTypo("presidenti");
  }
}
