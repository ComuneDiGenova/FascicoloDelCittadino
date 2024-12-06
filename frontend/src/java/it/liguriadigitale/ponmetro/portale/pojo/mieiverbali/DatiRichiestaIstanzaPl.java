package it.liguriadigitale.ponmetro.portale.pojo.mieiverbali;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateTimeUtil;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiDocumento;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivo;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivoSummary;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaNotifica;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiISEE;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.EsitoOperazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.IdDocumentiIstanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Rimborsi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DatiRichiestaIstanzaPl implements Serializable {
  private static Log log = LogFactory.getLog(DatiRichiestaIstanzaPl.class);
  private static final long serialVersionUID = -7258746232684512568L;

  private Utente utente;
  private DatiCompletiVerbale datiCompletiVerbale;
  private BigDecimal codiceHermesAnagraficaUtenteCollegato;

  /* Step 1 */
  private String richiedenteCognome;
  private String richiedenteNome;
  private String richiedenteCf;
  private String richiedenteTelefono;
  private String richiedenteEmail;

  private LocalDate nascitaData;
  private String nascitaComune;

  private String residenzaComune;
  private String residenzaVia;
  private String residenzaNumeroCivico;
  private String residenzaNumeroCivicoEsponente;
  private Integer residenzaNumeroCivicoSoloNumero;
  private Integer residenzaInterno;
  private String residenzaInternoEsponente;
  private String residenzaColore;

  private DatiCompletiVerbale datiCompletiVerbaleDiPartenza;
  private DatiMotivoSummary datiMotivoSummarySelezionato;
  private List<DatiMotivoSummary> listDatiMotivoSummarySelezionabili;

  private List<String> listDatiMarcheVeicoloSelezionabili;
  /* Step 1-2 */
  private DatiMotivo datiMotivo;

  /* Step 2 */
  private DatiISEE datiISEE;
  private Rimborsi rimborso;
  private double iSEETotale;
  public boolean presentabile;

  private Boolean autodichiarazioneFurto;
  private String autodichiarazioneTarga;
  private String autodichiarazioneMarca;
  private String autodichiarazioneModello;
  // private String autodichiarazioneTarga2;

  private LocalDate furtoData;
  private Boolean autodichiarazioneFurtoRitrovamento;
  private LocalDate ritrovamentoData;
  private String riconsegnatoPolizia;
  private String riconsegnatoPoliziaComune;
  private LocalDate riconsegnatoData;

  private Boolean autodichiarazioneProprietarioETitolare;

  /* Step 3 */
  private List<DatiCompletiVerbale> verbaliUlterioriCompatibili;

  private Istanza istanza;

  /* Step 4 */
  private List<DatiDocumento> documentiDaInviare;
  private List<DatiRichiestaIstanzaPlDocumentoCaricato> documentiCaricatiCompleti;
  private List<IdDocumentiIstanza> documentiCaricatiHeader;

  /* Step 5 */
  private Boolean confermaDati;

  private boolean checkInformativaRateizzazione;

  private String s4documentoUpload;
  private String s4documentoUpload1;
  private String s4documentoUpload2;
  private String s4documentoUpload3;

  private List<Verbale> listTuttiVerbali;
  private List<String> tutteLeMarche;
  private Map<Pair<String, Utente>, DettaglioVerbale> mapDettaglioVerbale;

  private EsitoOperazione esitoInvioIstanza;

  // targa errata
  private String miaMacchina = "Sì";
  private String marca;
  private String modello;
  private String noteTargaErrata;

  // non proprietario
  private String maiMia = "Sì";
  private LocalDate dataInizioProprieta;
  private LocalDate dataFineProprieta;

  public boolean isPresentabile() {
    return presentabile;
  }

  public void setPresentabile(boolean presentabile) {
    this.presentabile = presentabile;
  }

  public double getiSEETotale() {
    return iSEETotale;
  }

  public void setiSEETotale(double iSEETotale) {
    this.iSEETotale = iSEETotale;
  }

  public boolean isCheckInformativaRateizzazione() {
    return checkInformativaRateizzazione;
  }

  public void setCheckInformativaRateizzazione(boolean checkInformativaRateizzazione) {
    this.checkInformativaRateizzazione = checkInformativaRateizzazione;
  }

  public Rimborsi getRimborso() {
    return rimborso;
  }

  public void setRimborso(Rimborsi rimborso) {
    this.rimborso = rimborso;
  }

  public String getS4documentoUpload1() {
    return s4documentoUpload1;
  }

  public void setS4documentoUpload1(String s4documentoUpload1) {
    this.s4documentoUpload1 = s4documentoUpload1;
  }

  public String getS4documentoUpload2() {
    return s4documentoUpload2;
  }

  public void setS4documentoUpload2(String s4documentoUpload2) {
    this.s4documentoUpload2 = s4documentoUpload2;
  }

  public String getS4documentoUpload3() {
    return s4documentoUpload3;
  }

  public void setS4documentoUpload3(String s4documentoUpload3) {
    this.s4documentoUpload3 = s4documentoUpload3;
  }

  public String getS4documentoUpload4() {
    return s4documentoUpload4;
  }

  public void setS4documentoUpload4(String s4documentoUpload4) {
    this.s4documentoUpload4 = s4documentoUpload4;
  }

  public String getS4documentoUpload5() {
    return s4documentoUpload5;
  }

  public void setS4documentoUpload5(String s4documentoUpload5) {
    this.s4documentoUpload5 = s4documentoUpload5;
  }

  public String getS4documentoUpload6() {
    return s4documentoUpload6;
  }

  public void setS4documentoUpload6(String s4documentoUpload6) {
    this.s4documentoUpload6 = s4documentoUpload6;
  }

  public String getS4documentoUpload7() {
    return s4documentoUpload7;
  }

  public void setS4documentoUpload7(String s4documentoUpload7) {
    this.s4documentoUpload7 = s4documentoUpload7;
  }

  public String getS4documentoUpload8() {
    return s4documentoUpload8;
  }

  public void setS4documentoUpload8(String s4documentoUpload8) {
    this.s4documentoUpload8 = s4documentoUpload8;
  }

  public String getS4documentoUpload9() {
    return s4documentoUpload9;
  }

  public void setS4documentoUpload9(String s4documentoUpload9) {
    this.s4documentoUpload9 = s4documentoUpload9;
  }

  public String getS4documentoUpload10() {
    return s4documentoUpload10;
  }

  public void setS4documentoUpload10(String s4documentoUpload10) {
    this.s4documentoUpload10 = s4documentoUpload10;
  }

  public Boolean getObbligatoriCaricatiTutti() {
    return obbligatoriCaricatiTutti;
  }

  public void setObbligatoriCaricatiTutti(Boolean obbligatoriCaricatiTutti) {
    this.obbligatoriCaricatiTutti = obbligatoriCaricatiTutti;
  }

  private String s4documentoUpload4;
  private String s4documentoUpload5;
  private String s4documentoUpload6;
  private String s4documentoUpload7;
  private String s4documentoUpload8;
  private String s4documentoUpload9;
  private String s4documentoUpload10;

  private Boolean isIntegrazioneOrDaConcludere;
  private Boolean isIntegrazione;
  private Boolean obbligatoriCaricatiTutti;

  public DatiRichiestaIstanzaPl(Boolean isIntegrazioneOrDaConcludere, Boolean isIntegrazione) {
    datiCompletiVerbaleDiPartenza = new DatiCompletiVerbale();
    datiMotivoSummarySelezionato = new DatiMotivoSummary();
    datiMotivo = new DatiMotivo();
    verbaliUlterioriCompatibili = new ArrayList<DatiCompletiVerbale>();

    listDatiMarcheVeicoloSelezionabili = new ArrayList<String>();
    documentiDaInviare = new ArrayList<DatiDocumento>();
    documentiCaricatiCompleti = new ArrayList<DatiRichiestaIstanzaPlDocumentoCaricato>();
    documentiCaricatiHeader = new ArrayList<IdDocumentiIstanza>();
    this.isIntegrazioneOrDaConcludere = isIntegrazioneOrDaConcludere;
    this.setIsIntegrazione(isIntegrazione);
    obbligatoriCaricatiTutti = false;
    resetListTuttiVerbali();
    tutteLeMarche = null;
    resetMapDettaglioVerbale();
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  public String getRichiedenteCognome() {
    return richiedenteCognome;
  }

  public void setRichiedenteCognome(String richiedenteCognome) {
    this.richiedenteCognome = richiedenteCognome;
  }

  public String getRichiedenteNome() {
    return richiedenteNome;
  }

  public void setRichiedenteNome(String richiedenteNome) {
    this.richiedenteNome = richiedenteNome;
  }

  public String getRichiedenteCf() {
    return richiedenteCf;
  }

  public void setRichiedenteCf(String richiedenteCf) {
    this.richiedenteCf = richiedenteCf;
  }

  public String getRichiedenteTelefono() {
    return richiedenteTelefono;
  }

  public void setRichiedenteTelefono(String richiedenteTelefono) {
    this.richiedenteTelefono = richiedenteTelefono;
  }

  public String getRichiedenteEmail() {
    return richiedenteEmail;
  }

  public void setRichiedenteEmail(String richiedenteEmail) {
    this.richiedenteEmail = richiedenteEmail;
  }

  public LocalDate getNascitaData() {
    return nascitaData;
  }

  public void setNascitaData(LocalDate nascitaData) {
    this.nascitaData = nascitaData;
  }

  public String getNascitaComune() {
    return nascitaComune;
  }

  public void setNascitaComune(String nascitaComune) {
    this.nascitaComune = nascitaComune;
  }

  public String getResidenzaComune() {
    return residenzaComune;
  }

  public void setResidenzaComune(String residenzaComune) {
    this.residenzaComune = residenzaComune;
  }

  public String getResidenzaVia() {
    return residenzaVia;
  }

  public void setResidenzaVia(String residenzaVia) {
    this.residenzaVia = residenzaVia;
  }

  public String getResidenzaNumeroCivico() {
    return residenzaNumeroCivico;
  }

  public void setResidenzaNumeroCivico(String residenzaNumeroCivico) {
    this.residenzaNumeroCivico = residenzaNumeroCivico;
  }

  public String getResidenzaNumeroCivicoEsponente() {
    return residenzaNumeroCivicoEsponente;
  }

  public void setResidenzaNumeroCivicoEsponente(String residenzaNumeroCivicoEsponente) {
    this.residenzaNumeroCivicoEsponente = residenzaNumeroCivicoEsponente;
  }

  public Integer getResidenzaNumeroCivicoSoloNumero() {
    return residenzaNumeroCivicoSoloNumero;
  }

  public void setResidenzaNumeroCivicoSoloNumero(Integer residenzaNumeroCivicoSoloNumero) {
    this.residenzaNumeroCivicoSoloNumero = residenzaNumeroCivicoSoloNumero;
  }

  public DettaglioVerbale getDettaglioVerbaleDiPartenza() {
    return datiCompletiVerbaleDiPartenza.getDettaglioVerbale();
  }

  public void setDettaglioVerbaleDiPartenza(DettaglioVerbale dettaglioVerbaleDiPartenza) {
    log.debug("setDettaglioVerbaleDiPartenza -- ");
    this.datiCompletiVerbaleDiPartenza.setDettaglioVerbale(dettaglioVerbaleDiPartenza);
  }

  public DatiRichiestaIstanzaPl setDettaglioVerbaleDiPartenzaAndGet(
      DettaglioVerbale dettaglioVerbaleDiPartenza) {
    log.debug("setDettaglioVerbaleDiPartenzaAndGet -- ");
    setDettaglioVerbaleDiPartenza(dettaglioVerbaleDiPartenza);
    return this;
  }

  public DatiMotivoSummary getDatiMotivoSummary() {
    return datiMotivoSummarySelezionato;
  }

  public void setDatiMotivoSummary(
      DatiMotivoSummary datiMotivoSummary, boolean boResetAltroCheDipendeDalMotivo) {
    if (this.datiMotivoSummarySelezionato != datiMotivoSummary && boResetAltroCheDipendeDalMotivo) {
      resetListTuttiVerbali();
      resetMapDettaglioVerbale();
    }
    this.datiMotivoSummarySelezionato = datiMotivoSummary;
  }

  public DatiISEE getDatiISEE() {
    return datiISEE;
  }

  public void setDatiISEE(DatiISEE datiISEE) {
    this.datiISEE = datiISEE;
  }

  public DatiMotivo getDatiMotivo() {
    return datiMotivo;
  }

  public void setDatiMotivo(DatiMotivo datiMotivo) {
    this.datiMotivo = datiMotivo;
  }

  public Boolean isAutodichiarazioneFurto() {
    return autodichiarazioneFurto;
  }

  public Boolean getAutodichiarazioneFurto() {
    return autodichiarazioneFurto;
  }

  public void setAutodichiarazioneFurto(Boolean autodichiarazioneFurto) {
    this.autodichiarazioneFurto = autodichiarazioneFurto;
  }

  public LocalDate getFurtoData() {
    return furtoData;
  }

  public void setFurtoData(LocalDate furtoData) {
    this.furtoData = furtoData;
  }

  public Boolean getAutodichiarazioneFurtoRitrovamento() {
    if (istanza != null
        && istanza.getRequestAutodichiarazioneFurto() != null
        && istanza.getRequestAutodichiarazioneFurto().getDatiAutodichiarazione() != null) {
      return istanza.getRequestAutodichiarazioneFurto().getAutodichiarazione()
          && istanza.getRequestAutodichiarazioneFurto().getDatiAutodichiarazione().getRitrovato();
    }
    return autodichiarazioneFurtoRitrovamento;
  }

  public void setAutodichiarazioneFurtoRitrovamento(Boolean autodichiarazioneFurtoRitrovamento) {
    this.autodichiarazioneFurtoRitrovamento = autodichiarazioneFurtoRitrovamento;
  }

  public LocalDate getRitrovamentoData() {
    return ritrovamentoData;
  }

  public void setRitrovamentoData(LocalDate ritrovamentoData) {
    this.ritrovamentoData = ritrovamentoData;
  }

  public LocalDate getRiconsegnatoData() {
    return riconsegnatoData;
  }

  public void setRiconsegnatoData(LocalDate riconsegnatoData) {
    this.riconsegnatoData = riconsegnatoData;
  }

  public Boolean getAutodichiarazioneProprietarioETitolare() {
    if (istanza != null && autodichiarazioneProprietarioETitolare == null) {
      log.debug(
          "istanza.getRequestAutodichiarazioneProprietarioETitolarePermessoDisabile()="
              + istanza.getRequestAutodichiarazioneProprietarioETitolarePermessoDisabile());
      autodichiarazioneProprietarioETitolare =
          istanza.getRequestAutodichiarazioneProprietarioETitolarePermessoDisabile();
      return istanza.getRequestAutodichiarazioneProprietarioETitolarePermessoDisabile();
    }
    log.debug("autodichiarazioneProprietarioETitolare=" + autodichiarazioneProprietarioETitolare);
    return autodichiarazioneProprietarioETitolare;
  }

  public void setAutodichiarazioneProprietarioETitolare(
      Boolean autodichiarazioneProprietarioETitolare) {
    this.autodichiarazioneProprietarioETitolare = autodichiarazioneProprietarioETitolare;
  }

  public List<DatiCompletiVerbale> getVerbaliUlterioriCompatibili() {
    return verbaliUlterioriCompatibili;
  }

  public void setVerbaliUlterioriCompatibili(
      List<DatiCompletiVerbale> verbaliUlterioriCompatibili) {
    this.verbaliUlterioriCompatibili = verbaliUlterioriCompatibili;
  }

  public int getNumeroVerbaliUlterioriCompatibiliSelezionati() {
    int numeroSelezionati = 0;
    for (DatiCompletiVerbale datiCompletiVerbale : this.verbaliUlterioriCompatibili) {
      // log.error("getNumeroVerbaliUlterioriCompatibiliSelezionati:
      // DatiCompletiVerbale " + DatiCompletiVerbale);
      if (datiCompletiVerbale.isSelezionato()) numeroSelezionati++;
    }
    return numeroSelezionati;
  }

  public String getNumeriAltriVerbali() {
    String toRet = "";
    for (DatiCompletiVerbale datiCompletiVerbale : this.verbaliUlterioriCompatibili) {
      if (datiCompletiVerbale.isSelezionato()) {
        toRet += datiCompletiVerbale.getVerbale().getNumeroProtocollo() + ", ";
      }
    }
    // if (StringUtils.endsWith(toRet, ",")) {
    // toRet = StringUtils.chop(toRet);
    // }
    return toRet;
  }

  public void setSelezionatoByNumeroVerbale(Boolean selezionato, String numeroVerbale) {
    for (DatiCompletiVerbale datiCompletiVerbale : this.verbaliUlterioriCompatibili) {
      if (datiCompletiVerbale.getVerbale() != null
          && numeroVerbale.equalsIgnoreCase(
              datiCompletiVerbale.getVerbale().getNumeroProtocollo())) {
        datiCompletiVerbale.setSelezionato(selezionato);
        break;
      }
    }
  }

  public Boolean isSelezionatoByNumeroVerbale(String numeroVerbale) {
    for (DatiCompletiVerbale datiCompletiVerbale : this.verbaliUlterioriCompatibili) {
      if (numeroVerbale.equalsIgnoreCase(datiCompletiVerbale.getVerbale().getNumeroProtocollo())) {
        return datiCompletiVerbale.isSelezionato();
      }
    }
    return false;
  }

  public List<DatiDocumento> getDocumentiDaInviare() {
    return documentiDaInviare;
  }

  public void setDocumentiDaInviare(List<DatiDocumento> documentiDaInviare) {
    this.documentiDaInviare = documentiDaInviare;
  }

  public List<DatiRichiestaIstanzaPlDocumentoCaricato> getDocumentiCaricatiCompleti() {
    return documentiCaricatiCompleti;
  }

  public void setDocumentiCaricatiCompleti(
      List<DatiRichiestaIstanzaPlDocumentoCaricato> documentiCaricatiCompleti) {
    this.documentiCaricatiCompleti = documentiCaricatiCompleti;
  }

  public Boolean getConfermaDati() {
    return confermaDati;
  }

  public void setConfermaDati(Boolean confermaDati) {
    this.confermaDati = confermaDati;
  }

  public DatiCompletiVerbale getDatiCompletiVerbaleDiPartenza() {
    return datiCompletiVerbaleDiPartenza;
  }

  public void setDatiCompletiVerbaleDiPartenza(DatiCompletiVerbale datiCompletiVerbaleDiPartenza) {
    this.datiCompletiVerbaleDiPartenza = datiCompletiVerbaleDiPartenza;
  }

  public String getCodiceHermesMotivoSelezionato() {
    return datiMotivoSummarySelezionato != null && datiMotivoSummarySelezionato.getCodice() != null
        ? datiMotivoSummarySelezionato.getCodice()
        : "00";
  }

  public String getDescrizioneCodiceHermesMotivoSelezionato() {
    return datiMotivoSummarySelezionato != null
            && datiMotivoSummarySelezionato.getDescrizione() != null
        ? datiMotivoSummarySelezionato.getDescrizione()
        : "-";
  }

  public String getRiferimentoLeggeCodiceHermesMotivoSelezionato() {
    return datiMotivoSummarySelezionato != null
            && datiMotivoSummarySelezionato.getRiferimentoLegge() != null
        ? datiMotivoSummarySelezionato.getRiferimentoLegge()
        : null;
  }

  public BigDecimal getCodiceHermesAnagrafica() {
    // cf richiedente uguale all'elemento iesimo
    // cerca il cf dentro array
    // getDettaglioVerbaleDiPartenza().getAnagraficheNotifiche()
    // getDettaglioVerbaleDiPartenza().getAnagraficheNotifiche();
    log.debug("7777 1 ServiziMieiVerbaliImpl -- getCodiceHermesAnagrafica: ");
    log.debug(
        "7777 2 ServiziMieiVerbaliImpl -- getCodiceHermesAnagrafica getDettaglioVerbaleDiPartenza: "
            + getDettaglioVerbaleDiPartenza());
    if (getCodiceHermesAnagraficaUtenteCollegato() == null
        || getCodiceHermesAnagraficaUtenteCollegato().equals(BigDecimal.ZERO)) {
      try {
        List<AnagraficaNotifica> listAnagraficaNotifica =
            getDettaglioVerbaleDiPartenza().getAnagraficheNotifiche();
        log.error(
            "7777 3 ServiziMieiVerbaliImpl -- getCodiceHermesAnagrafica getAnagraficheNotifiche: "
                + getDettaglioVerbaleDiPartenza().getAnagraficheNotifiche());
        AnagraficaNotifica anagraficaNotifica =
            listAnagraficaNotifica.stream()
                .filter(
                    elemento ->
                        elemento
                            .getDatiAnagrafici()
                            .getCpvTaxCode()
                            .equalsIgnoreCase(getRichiedenteCf()))
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
    } else {
      return getCodiceHermesAnagraficaUtenteCollegato();
    }
  }

  public String getSerieVerbalePartenza() {
    return getDettaglioVerbaleDiPartenza() != null
            && getDettaglioVerbaleDiPartenza().getSerieVerbale() != null
            && getDettaglioVerbaleDiPartenza().getSerieVerbale().getCodiceSerie() != null
        ? getDettaglioVerbaleDiPartenza().getSerieVerbale().getCodiceSerie()
        : "-";
  }

  public List<Istanza> getIstanzeVerbalePartenza() {
    if (getDettaglioVerbaleDiPartenza() == null) {
      return new ArrayList<Istanza>();
    }
    return getDettaglioVerbaleDiPartenza().getIstanze();
  }

  public List<String> getArticoliVerbalePartenzaTutti() {
    if (getDettaglioVerbaleDiPartenza() == null
        || getDettaglioVerbaleDiPartenza().getArticoliViolati().isEmpty()) {
      return new ArrayList<String>();
    }

    List<String> datiToRet =
        getDettaglioVerbaleDiPartenza().getArticoliViolati().stream()
            .filter(ithitem -> ithitem != null)
            .map(ithitem -> ithitem.getNumero().toString())
            .collect(Collectors.toList());
    return datiToRet;
  }

  public List<String> getArticoliVerbalePartenzaNonArt180() {
    if (getDettaglioVerbaleDiPartenza() == null
        || getDettaglioVerbaleDiPartenza().getArticoliViolati().isEmpty()) {
      return new ArrayList<String>();
    }

    List<String> datiToRet =
        getArticoliVerbalePartenzaTutti().stream()
            .filter(ithitem -> !("180".equals(ithitem)))
            .collect(Collectors.toList());
    return datiToRet;
  }

  public List<String> getArticoliVerbalePartenzaArt180() {
    if (getDettaglioVerbaleDiPartenza() == null
        || getDettaglioVerbaleDiPartenza().getArticoliViolati().isEmpty()) {
      return new ArrayList<String>();
    }

    List<String> datiToRet =
        getArticoliVerbalePartenzaTutti().stream()
            .filter(ithitem -> ("180".equals(ithitem)))
            .collect(Collectors.toList());
    return datiToRet;
  }

  public String getNumeroVerbalePartenza() {
    if (getDettaglioVerbaleDiPartenza() == null) return null;
    return getDettaglioVerbaleDiPartenza().getNumeroAvviso(); // Protocollo();
  }

  public DatiRichiestaIstanzaPl setDatiCompletiVerbaleDiPartenzaAndGet(
      DatiCompletiVerbale datiCompletiVerbale2) {
    setDatiCompletiVerbaleDiPartenza(datiCompletiVerbale2);
    return this;
  }

  public String getAutodichiarazioneTarga() {
    return autodichiarazioneTarga;
  }

  public void setAutodichiarazioneTarga(String autodichiarazioneTarga) {
    this.autodichiarazioneTarga = autodichiarazioneTarga;
  }

  public String getAutodichiarazioneMarca() {
    return autodichiarazioneMarca;
  }

  public void setAutodichiarazioneMarca(String autodichiarazioneMarca) {
    this.autodichiarazioneMarca = autodichiarazioneMarca;
  }

  public String getAutodichiarazioneModello() {
    return autodichiarazioneModello;
  }

  public void setAutodichiarazioneModello(String autodichiarazioneModello) {
    this.autodichiarazioneModello = autodichiarazioneModello;
  }

  // public String getAutodichiarazioneTarga2() {
  // return autodichiarazioneTarga2;
  // }

  // public void setAutodichiarazioneTarga2(String autodichiarazioneTarga2) {
  // this.autodichiarazioneTarga2 = autodichiarazioneTarga2;
  // }

  public String getRiconsegnatoPolizia() {
    return riconsegnatoPolizia;
  }

  public void setRiconsegnatoPolizia(String riconsegnatoPolizia) {
    this.riconsegnatoPolizia = riconsegnatoPolizia;
  }

  public String getRiconsegnatoPoliziaComune() {
    return riconsegnatoPoliziaComune;
  }

  public void setRiconsegnatoPoliziaComune(String riconsegnatoPoliziaComune) {
    this.riconsegnatoPoliziaComune = riconsegnatoPoliziaComune;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getCodeVia1VerbalePartenza() {
    if (datiCompletiVerbaleDiPartenza == null) {
      return null;
    }
    if (datiCompletiVerbaleDiPartenza.getVerbale() == null) {
      return null;
    }
    return datiCompletiVerbaleDiPartenza.getVerbale().getOfficialStreetNameCode1();
  }

  public String getCodeVia2VerbalePartenza() {
    if (datiCompletiVerbaleDiPartenza == null) {
      return null;
    }
    if (datiCompletiVerbaleDiPartenza.getVerbale() == null) {
      return null;
    }
    return datiCompletiVerbaleDiPartenza.getVerbale().getOfficialStreetNameCode2();
  }

  public String getCodeVia3VerbalePartenza() {
    if (datiCompletiVerbaleDiPartenza == null) {
      return null;
    }
    if (datiCompletiVerbaleDiPartenza.getVerbale() == null) {
      return null;
    }
    return datiCompletiVerbaleDiPartenza.getVerbale().getOfficialStreetNameCode3();
  }

  public String getTargaVerbalePartenza() {
    if (datiCompletiVerbaleDiPartenza == null) {
      return null;
    }
    if (datiCompletiVerbaleDiPartenza.getVerbale() == null) {
      return null;
    }
    return datiCompletiVerbaleDiPartenza.getVerbale().getTarga();
  }

  public LocalDate getDataAccertamentoVerbalePartenza() {
    if (datiCompletiVerbaleDiPartenza == null) {
      return null;
    }
    if (datiCompletiVerbaleDiPartenza.getVerbale() == null) {
      return null;
    }
    return datiCompletiVerbaleDiPartenza.getVerbale().getDataAccertamento();
  }

  public String getStringTimeAccertamentoVerbalePartenza() {
    if (datiCompletiVerbaleDiPartenza == null) {
      return null;
    }
    if (datiCompletiVerbaleDiPartenza.getDettaglioVerbale() == null) {
      return null;
    }
    return datiCompletiVerbaleDiPartenza.getDettaglioVerbale().getOraAccertamento();
  }

  public LocalTime getTimeAccertamentoVerbalePartenza() {
    // oraAccertamento: 11:52
    if (datiCompletiVerbaleDiPartenza == null) {
      return null;
    }
    if (datiCompletiVerbaleDiPartenza.getDettaglioVerbale() == null) {
      return null;
    }
    String oraAccertamentoHHmm =
        datiCompletiVerbaleDiPartenza.getDettaglioVerbale().getOraAccertamento();
    if (oraAccertamentoHHmm == null) {
      return null;
    }
    return LocalDateTimeUtil.getLocalTimeFromStringHHmm(oraAccertamentoHHmm);
  }

  public LocalDateTime getDataEOraAccertamentoVerbalePartenza() {
    if (getDataAccertamentoVerbalePartenza() == null
        || getTimeAccertamentoVerbalePartenza() == null) {
      return null;
    }
    LocalDate ld = getDataAccertamentoVerbalePartenza();
    LocalTime lt = getTimeAccertamentoVerbalePartenza();
    return LocalDateTimeUtil.getLocalDateTimeFromLocalDateAndLocalTime(ld, lt);
  }

  public void setIsIntegrazioneOrDaConcludere(Boolean isIntegrazioneOrDaConcludere) {
    this.isIntegrazioneOrDaConcludere = isIntegrazioneOrDaConcludere;
  }

  public Boolean getIsIntegrazioneOrDaConcludere() {
    return this.isIntegrazioneOrDaConcludere;
  }

  public void setIstanza(Istanza istanza) {
    this.istanza = istanza;
  }

  public Istanza getIstanza() {
    return this.istanza;
  }

  public String getS4documentoUpload() {
    return s4documentoUpload;
  }

  public void setS4documentoUpload(String s4documentoUpload) {
    this.s4documentoUpload = s4documentoUpload;
  }

  public List<IdDocumentiIstanza> getDocumentiCaricatiHeader() {
    return documentiCaricatiHeader;
  }

  public void setDocumentiCaricatiHeader(List<IdDocumentiIstanza> documentiCaricatiHeader) {
    this.documentiCaricatiHeader = documentiCaricatiHeader;
  }

  public List<DatiMotivoSummary> getListDatiMotivoSummarySelezionabili() {
    return listDatiMotivoSummarySelezionabili;
  }

  public void setListDatiMotivoSummarySelezionabili(
      List<DatiMotivoSummary> listDatiMotivoSummarySelezionabili) {
    this.listDatiMotivoSummarySelezionabili = listDatiMotivoSummarySelezionabili;
  }

  public List<String> getListDatiMarcheVeicoloSelezionabili() {
    return listDatiMarcheVeicoloSelezionabili;
  }

  public void setListDatiMarcheVeicoloSelezionabili(
      List<String> listDatiMarcheVeicoloSelezionabili) {
    this.listDatiMarcheVeicoloSelezionabili = listDatiMarcheVeicoloSelezionabili;
  }

  public DatiRichiestaIstanzaPl setDatiIstanzaAndGet(Istanza dettagliIstanza) {
    setIstanza(dettagliIstanza);
    return this;
  }

  public boolean isMotivoSelezionatoEqualToCode(String code) {
    return StringUtils.equalsIgnoreCase(getCodiceHermesMotivoSelezionato(), code);
  }

  public Boolean getIsIntegrazione() {
    return isIntegrazione;
  }

  public void setIsIntegrazione(Boolean isIntegrazione) {
    this.isIntegrazione = isIntegrazione;
  }

  public void setTuttiDocumentiObbligatoriCaricati(Boolean obbligatoriCaricatiTutti) {
    this.obbligatoriCaricatiTutti = obbligatoriCaricatiTutti;
  }

  public Boolean getTuttiDocumentiObbligatoriCaricati(Boolean obbligatoriCaricatiTutti) {
    return obbligatoriCaricatiTutti;
  }

  @Override
  public String toString() {
    return "DatiRichiestaIstanzaPl [log="
        + log
        + ", utente="
        + utente
        + ", richiedenteCognome="
        + richiedenteCognome
        + ", richiedenteNome="
        + richiedenteNome
        + ", richiedenteCf="
        + richiedenteCf
        + ", richiedenteTelefono="
        + richiedenteTelefono
        + ", richiedenteEmail="
        + richiedenteEmail
        + ", nascitaData="
        + nascitaData
        + ", nascitaComune="
        + nascitaComune
        + ", residenzaComune="
        + residenzaComune
        + ", residenzaVia="
        + residenzaVia
        + ", residenzaNumeroCivico="
        + residenzaNumeroCivico
        + ", datiCompletiVerbaleDiPartenza="
        + datiCompletiVerbaleDiPartenza
        + ", datiMotivoSummarySelezionato="
        + datiMotivoSummarySelezionato
        + ", listDatiMotivoSummarySelezionabili="
        + listDatiMotivoSummarySelezionabili
        + ", listDatiMarcheVeicoloSelezionabili="
        + listDatiMarcheVeicoloSelezionabili
        + ", datiMotivo="
        + datiMotivo
        + ", autodichiarazioneFurto="
        + autodichiarazioneFurto
        + ", autodichiarazioneTarga="
        + autodichiarazioneTarga
        + ", autodichiarazioneMarca="
        + autodichiarazioneMarca
        + ", autodichiarazioneModello="
        + autodichiarazioneModello
        + ", furtoData="
        + furtoData
        + ", autodichiarazioneFurtoRitrovamento="
        + autodichiarazioneFurtoRitrovamento
        + ", ritrovamentoData="
        + ritrovamentoData
        + ", riconsegnatoPolizia="
        + riconsegnatoPolizia
        + ", riconsegnatoPoliziaComune="
        + riconsegnatoPoliziaComune
        + ", riconsegnatoData="
        + riconsegnatoData
        + ", autodichiarazioneProprietarioETitolare="
        + autodichiarazioneProprietarioETitolare
        + ", verbaliUlterioriCompatibili="
        + verbaliUlterioriCompatibili
        + ", istanza="
        + istanza
        + ", documentiDaInviare="
        + documentiDaInviare
        + ", documentiCaricatiCompleti="
        + documentiCaricatiCompleti
        + ", documentiCaricatiHeader="
        + documentiCaricatiHeader
        + ", confermaDati="
        + confermaDati
        + ", s4documentoUpload="
        + s4documentoUpload
        + ", s4documentoUpload1="
        + s4documentoUpload1
        + ", s4documentoUpload2="
        + s4documentoUpload2
        + ", s4documentoUpload3="
        + s4documentoUpload3
        + ", s4documentoUpload4="
        + s4documentoUpload4
        + ", s4documentoUpload5="
        + s4documentoUpload5
        + ", s4documentoUpload6="
        + s4documentoUpload6
        + ", s4documentoUpload7="
        + s4documentoUpload7
        + ", s4documentoUpload8="
        + s4documentoUpload8
        + ", s4documentoUpload9="
        + s4documentoUpload9
        + ", s4documentoUpload10="
        + s4documentoUpload10
        + ", isIntegrazioneOrDaConcludere="
        + isIntegrazioneOrDaConcludere
        + ", isIntegrazione="
        + isIntegrazione
        + ", obbligatoriCaricatiTutti="
        + obbligatoriCaricatiTutti
        + ", getS4documentoUpload1()="
        + getS4documentoUpload1()
        + ", getS4documentoUpload2()="
        + getS4documentoUpload2()
        + ", getS4documentoUpload3()="
        + getS4documentoUpload3()
        + ", getS4documentoUpload4()="
        + getS4documentoUpload4()
        + ", getS4documentoUpload5()="
        + getS4documentoUpload5()
        + ", getS4documentoUpload6()="
        + getS4documentoUpload6()
        + ", getS4documentoUpload7()="
        + getS4documentoUpload7()
        + ", getS4documentoUpload8()="
        + getS4documentoUpload8()
        + ", getS4documentoUpload9()="
        + getS4documentoUpload9()
        + ", getS4documentoUpload10()="
        + getS4documentoUpload10()
        + ", getObbligatoriCaricatiTutti()="
        + getObbligatoriCaricatiTutti()
        + ", getUtente()="
        + getUtente()
        + ", getRichiedenteCognome()="
        + getRichiedenteCognome()
        + ", getRichiedenteNome()="
        + getRichiedenteNome()
        + ", getRichiedenteCf()="
        + getRichiedenteCf()
        + ", getRichiedenteTelefono()="
        + getRichiedenteTelefono()
        + ", getRichiedenteEmail()="
        + getRichiedenteEmail()
        + ", getNascitaData()="
        + getNascitaData()
        + ", getNascitaComune()="
        + getNascitaComune()
        + ", getResidenzaComune()="
        + getResidenzaComune()
        + ", getResidenzaVia()="
        + getResidenzaVia()
        + ", getResidenzaNumeroCivico()="
        + getResidenzaNumeroCivico()
        + ", getDettaglioVerbaleDiPartenza()="
        + getDettaglioVerbaleDiPartenza()
        + ", getDatiMotivoSummary()="
        + getDatiMotivoSummary()
        + ", getDatiMotivo()="
        + getDatiMotivo()
        + ", isAutodichiarazioneFurto()="
        + isAutodichiarazioneFurto()
        + ", getAutodichiarazioneFurto()="
        + getAutodichiarazioneFurto()
        + ", getFurtoData()="
        + getFurtoData()
        + ", getAutodichiarazioneFurtoRitrovamento()="
        + getAutodichiarazioneFurtoRitrovamento()
        + ", getRitrovamentoData()="
        + getRitrovamentoData()
        + ", getRiconsegnatoData()="
        + getRiconsegnatoData()
        + ", getAutodichiarazioneProprietarioETitolare()="
        + getAutodichiarazioneProprietarioETitolare()
        + ", getVerbaliUlterioriCompatibili()="
        + getVerbaliUlterioriCompatibili()
        + ", getNumeroVerbaliUlterioriCompatibiliSelezionati()="
        + getNumeroVerbaliUlterioriCompatibiliSelezionati()
        + ", getNumeriAltriVerbali()="
        + getNumeriAltriVerbali()
        + ", getDocumentiDaInviare()="
        + getDocumentiDaInviare()
        + ", getDocumentiCaricatiCompleti()="
        + getDocumentiCaricatiCompleti()
        + ", getConfermaDati()="
        + getConfermaDati()
        + ", getDatiCompletiVerbaleDiPartenza()="
        + getDatiCompletiVerbaleDiPartenza()
        + ", getCodiceHermesMotivoSelezionato()="
        + getCodiceHermesMotivoSelezionato()
        + ", getDescrizioneCodiceHermesMotivoSelezionato()="
        + getDescrizioneCodiceHermesMotivoSelezionato()
        + ", getCodiceHermesAnagrafica()="
        + getCodiceHermesAnagrafica()
        + ", getSerieVerbalePartenza()="
        + getSerieVerbalePartenza()
        + ", getArticoliVerbalePartenzaTutti()="
        + getArticoliVerbalePartenzaTutti()
        + ", getArticoliVerbalePartenzaNonArt180()="
        + getArticoliVerbalePartenzaNonArt180()
        + ", getArticoliVerbalePartenzaArt180()="
        + getArticoliVerbalePartenzaArt180()
        + ", getNumeroVerbalePartenza()="
        + getNumeroVerbalePartenza()
        + ", getAutodichiarazioneTarga()="
        + getAutodichiarazioneTarga()
        + ", getAutodichiarazioneMarca()="
        + getAutodichiarazioneMarca()
        + ", getAutodichiarazioneModello()="
        + getAutodichiarazioneModello()
        + ", getRiconsegnatoPolizia()="
        + getRiconsegnatoPolizia()
        + ", getRiconsegnatoPoliziaComune()="
        + getRiconsegnatoPoliziaComune()
        + ", getCodeVia1VerbalePartenza()="
        + getCodeVia1VerbalePartenza()
        + ", getCodeVia2VerbalePartenza()="
        + getCodeVia2VerbalePartenza()
        + ", getCodeVia3VerbalePartenza()="
        + getCodeVia3VerbalePartenza()
        + ", getTargaVerbalePartenza()="
        + getTargaVerbalePartenza()
        + ", getDataAccertamentoVerbalePartenza()="
        + getDataAccertamentoVerbalePartenza()
        + ", getStringTimeAccertamentoVerbalePartenza()="
        + getStringTimeAccertamentoVerbalePartenza()
        + ", getTimeAccertamentoVerbalePartenza()="
        + getTimeAccertamentoVerbalePartenza()
        + ", getDataEOraAccertamentoVerbalePartenza()="
        + getDataEOraAccertamentoVerbalePartenza()
        + ", getIsIntegrazioneOrDaConcludere()="
        + getIsIntegrazioneOrDaConcludere()
        + ", getIstanza()="
        + getIstanza()
        + ", getS4documentoUpload()="
        + getS4documentoUpload()
        + ", getDocumentiCaricatiHeader()="
        + getDocumentiCaricatiHeader()
        + ", getListDatiMotivoSummarySelezionabili()="
        + getListDatiMotivoSummarySelezionabili()
        + ", getListDatiMarcheVeicoloSelezionabili()="
        + getListDatiMarcheVeicoloSelezionabili()
        + ", getIsIntegrazione()="
        + getIsIntegrazione()
        + "]";
  }

  public String getAutodichiarazioneFurtoStringa() {
    // TODO Auto-generated method stub
    if (getAutodichiarazioneFurto() == null) return "-";
    if (getAutodichiarazioneFurto()) return "SI";
    return "NO";
  }

  public String getAutodichiarazioneFurtoRitrovamentoStringa() {
    // TODO Auto-generated method stub
    if (getAutodichiarazioneFurtoRitrovamento() == null) return "-";
    if (getAutodichiarazioneFurtoRitrovamento()) return "SI";
    return "NO";
  }

  public String getAutodichiarazioneProprietarioETitolareStringa() {

    if (getAutodichiarazioneProprietarioETitolare() == null) return "-";
    if (getAutodichiarazioneProprietarioETitolare()) return "SI";
    return "NO";
  }

  public LocalDate getVerbaleData() {
    if (datiCompletiVerbaleDiPartenza != null || datiCompletiVerbaleDiPartenza.getVerbale() != null)
      return datiCompletiVerbaleDiPartenza.getVerbale().getDataAccertamento();
    return null;
  }

  public BigDecimal getCodiceHermesAnagraficaUtenteCollegato() {
    return codiceHermesAnagraficaUtenteCollegato;
  }

  public void setCodiceHermesAnagraficaUtenteCollegato(
      BigDecimal codiceHermesAnagraficaUtenteCollegato) {
    this.codiceHermesAnagraficaUtenteCollegato = codiceHermesAnagraficaUtenteCollegato;
  }

  public DatiCompletiVerbale getDatiCompletiVerbale() {
    return datiCompletiVerbale;
  }

  public void setDatiCompletiVerbale(DatiCompletiVerbale datiCompletiVerbale) {
    this.datiCompletiVerbale = datiCompletiVerbale;
  }

  public DatiMotivoSummary getDatiMotivoSummarySelezionato() {
    return datiMotivoSummarySelezionato;
  }

  public void setDatiMotivoSummarySelezionato(DatiMotivoSummary datiMotivoSummarySelezionato) {
    this.datiMotivoSummarySelezionato = datiMotivoSummarySelezionato;
  }

  public List<Verbale> getListTuttiVerbali() {
    return listTuttiVerbali;
  }

  public void setListTuttiVerbali(List<Verbale> listTuttiVerbali) {
    this.listTuttiVerbali = listTuttiVerbali;
  }

  public List<String> getTutteLeMarche() {
    return tutteLeMarche;
  }

  public void setTutteLeMarche(List<String> tutteLeMarche) {
    this.tutteLeMarche = tutteLeMarche;
  }

  public DettaglioVerbale getMapDettaglioVerbale(String numeroProtocollo, Utente utente) {
    DettaglioVerbale dettaglioVerbale = null;
    if (mapDettaglioVerbale != null) {
      dettaglioVerbale = mapDettaglioVerbale.get(Pair.of(numeroProtocollo, utente));
    }
    return dettaglioVerbale;
  }

  public void addMapDettaglioVerbale(
      String numeroProtocollo, Utente utente, DettaglioVerbale dettaglioVerbale) {
    if (mapDettaglioVerbale == null) {
      mapDettaglioVerbale = new HashMap<Pair<String, Utente>, DettaglioVerbale>();
    }
    mapDettaglioVerbale.put(Pair.of(numeroProtocollo, utente), dettaglioVerbale);
  }

  public void resetMapDettaglioVerbale() {
    mapDettaglioVerbale = null;
  }

  public void resetListTuttiVerbali() {
    listTuttiVerbali = null;
  }

  public EsitoOperazione getEsitoInvioIstanza() {
    return esitoInvioIstanza;
  }

  public void setEsitoInvioIstanza(EsitoOperazione esitoInvioIstanza) {
    this.esitoInvioIstanza = esitoInvioIstanza;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public String getModello() {
    return modello;
  }

  public void setModello(String modello) {
    this.modello = modello;
  }

  public String getMiaMacchina() {
    return miaMacchina;
  }

  public void setMiaMacchina(String miaMacchina) {
    this.miaMacchina = miaMacchina;
  }

  public String getMaiMia() {
    return maiMia;
  }

  public void setMaiMia(String maiMia) {
    this.maiMia = maiMia;
  }

  public LocalDate getDataInizioProprieta() {
    return dataInizioProprieta;
  }

  public void setDataInizioProprieta(LocalDate dataInizioProprieta) {
    this.dataInizioProprieta = dataInizioProprieta;
  }

  public LocalDate getDataFineProprieta() {
    return dataFineProprieta;
  }

  public void setDataFineProprieta(LocalDate dataFineProprieta) {
    this.dataFineProprieta = dataFineProprieta;
  }

  public String getNoteTargaErrata() {
    return noteTargaErrata;
  }

  public void setNoteTargaErrata(String noteTargaErrata) {
    this.noteTargaErrata = noteTargaErrata;
  }

  public Integer getResidenzaInterno() {
    return residenzaInterno;
  }

  public void setResidenzaInterno(Integer residenzaInterno) {
    this.residenzaInterno = residenzaInterno;
  }

  public String getResidenzaInternoEsponente() {
    return residenzaInternoEsponente;
  }

  public void setResidenzaInternoEsponente(String residenzaInternoEsponente) {
    this.residenzaInternoEsponente = residenzaInternoEsponente;
  }

  public String getResidenzaColore() {
    return residenzaColore;
  }

  public void setResidenzaColore(String residenzaColore) {
    this.residenzaColore = residenzaColore;
  }
}
