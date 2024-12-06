package it.liguriadigitale.ponmetro.portale.pojo.portale;

import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffaria;
import java.io.Serializable;
import java.time.LocalDate;

public class AgevolazioneTariffariaRistorazione implements Serializable {

  private static final long serialVersionUID = -2069465961582399266L;

  private boolean selezionato;
  private String codiceFiscale;
  private String nome;
  private String cognome;
  private LocalDate dataNascita;
  private String annoScolastico;
  private LocalDate dataFineAgevolazione;
  private LocalDate dataInizioAgevolazione;
  private LocalDate dataPresentazioneAgevolazione;
  private LocalDate dataRichiestaAgevolazione;
  private LocalDate dataValiditaAgevolazione;
  private String numeroProtocolloDsu;
  private Boolean omissioneDifformita;
  private DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum statoRichiestaAgevolazione;
  private String importoIndicatoreIseeBambino;
  private Boolean iseeNonCalcolabile;

  public String getImportoIndicatoreIseeBambino() {
    return importoIndicatoreIseeBambino;
  }

  public void setImportoIndicatoreIseeBambino(String importoIndicatoreIseeBambino) {
    this.importoIndicatoreIseeBambino = importoIndicatoreIseeBambino;
  }

  public String getAnnoScolastico() {
    return annoScolastico;
  }

  public void setAnnoScolastico(String annoScolastico) {
    this.annoScolastico = annoScolastico;
  }

  public LocalDate getDataFineAgevolazione() {
    return dataFineAgevolazione;
  }

  public void setDataFineAgevolazione(LocalDate dataFineAgevolazione) {
    this.dataFineAgevolazione = dataFineAgevolazione;
  }

  public LocalDate getDataInizioAgevolazione() {
    return dataInizioAgevolazione;
  }

  public void setDataInizioAgevolazione(LocalDate dataInizioAgevolazione) {
    this.dataInizioAgevolazione = dataInizioAgevolazione;
  }

  public LocalDate getDataPresentazioneAgevolazione() {
    return dataPresentazioneAgevolazione;
  }

  public void setDataPresentazioneAgevolazione(LocalDate dataPresentazioneAgevolazione) {
    this.dataPresentazioneAgevolazione = dataPresentazioneAgevolazione;
  }

  public LocalDate getDataRichiestaAgevolazione() {
    return dataRichiestaAgevolazione;
  }

  public void setDataRichiestaAgevolazione(LocalDate dataRichiestaAgevolazione) {
    this.dataRichiestaAgevolazione = dataRichiestaAgevolazione;
  }

  public LocalDate getDataValiditaAgevolazione() {
    return dataValiditaAgevolazione;
  }

  public void setDataValiditaAgevolazione(LocalDate dataValiditaAgevolazione) {
    this.dataValiditaAgevolazione = dataValiditaAgevolazione;
  }

  public String getNumeroProtocolloDsu() {
    return numeroProtocolloDsu;
  }

  public void setNumeroProtocolloDsu(String numeroProtocolloDsu) {
    this.numeroProtocolloDsu = numeroProtocolloDsu;
  }

  public Boolean getOmissioneDifformita() {
    return omissioneDifformita;
  }

  public void setOmissioneDifformita(Boolean omissioneDifformita) {
    this.omissioneDifformita = omissioneDifformita;
  }

  public DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum getStatoRichiestaAgevolazione() {
    return statoRichiestaAgevolazione;
  }

  public void setStatoRichiestaAgevolazione(
      DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum statoRichiestaAgevolazione) {
    this.statoRichiestaAgevolazione = statoRichiestaAgevolazione;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  public boolean isSelezionato() {
    return selezionato;
  }

  public void setSelezionato(boolean selezionato) {
    this.selezionato = selezionato;
  }

  public Boolean getIseeNonCalcolabile() {
    return iseeNonCalcolabile;
  }

  public void setIseeNonCalcolabile(Boolean iseeNonCalcolabile) {
    this.iseeNonCalcolabile = iseeNonCalcolabile;
  }

  @Override
  public String toString() {
    return "AgevolazioneTariffariaRistorazione [selezionato="
        + selezionato
        + ", codiceFiscale="
        + codiceFiscale
        + ", nome="
        + nome
        + ", cognome="
        + cognome
        + ", dataNascita="
        + dataNascita
        + ", annoScolastico="
        + annoScolastico
        + ", dataFineAgevolazione="
        + dataFineAgevolazione
        + ", dataInizioAgevolazione="
        + dataInizioAgevolazione
        + ", dataPresentazioneAgevolazione="
        + dataPresentazioneAgevolazione
        + ", dataRichiestaAgevolazione="
        + dataRichiestaAgevolazione
        + ", dataValiditaAgevolazione="
        + dataValiditaAgevolazione
        + ", numeroProtocolloDsu="
        + numeroProtocolloDsu
        + ", omissioneDifformita="
        + omissioneDifformita
        + ", statoRichiestaAgevolazione="
        + statoRichiestaAgevolazione
        + ", importoIndicatoreIseeBambino="
        + importoIndicatoreIseeBambino
        + ", iseeNonCalcolabile="
        + iseeNonCalcolabile
        + "]";
  }
}
