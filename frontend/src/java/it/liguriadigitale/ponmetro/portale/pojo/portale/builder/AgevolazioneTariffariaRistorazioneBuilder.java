package it.liguriadigitale.ponmetro.portale.pojo.portale.builder;

import it.liguriadigitale.ponmetro.portale.pojo.portale.AgevolazioneTariffariaRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffaria;
import java.time.LocalDate;

public class AgevolazioneTariffariaRistorazioneBuilder {
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

  public AgevolazioneTariffariaRistorazioneBuilder setAnnoScolastico(String annoScolastico) {
    this.annoScolastico = annoScolastico;

    return this;
  }

  public AgevolazioneTariffariaRistorazioneBuilder setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;

    return this;
  }

  public AgevolazioneTariffariaRistorazioneBuilder setDataFineAgevolazione(
      LocalDate dataFineAgevolazione) {
    this.dataFineAgevolazione = dataFineAgevolazione;

    return this;
  }

  public AgevolazioneTariffariaRistorazioneBuilder setDataInizioAgevolazione(
      LocalDate dataInizioAgevolazione) {
    this.dataInizioAgevolazione = dataInizioAgevolazione;

    return this;
  }

  public AgevolazioneTariffariaRistorazioneBuilder setDataPresentazioneAgevolazione(
      LocalDate dataPresentazioneAgevolazione) {
    this.dataPresentazioneAgevolazione = dataPresentazioneAgevolazione;

    return this;
  }

  public AgevolazioneTariffariaRistorazioneBuilder setDataRichiestaAgevolazione(
      LocalDate dataRichiestaAgevolazione) {
    this.dataRichiestaAgevolazione = dataRichiestaAgevolazione;

    return this;
  }

  public AgevolazioneTariffariaRistorazioneBuilder setDataValiditaAgevolazione(
      LocalDate dataValiditaAgevolazione) {
    this.dataValiditaAgevolazione = dataValiditaAgevolazione;

    return this;
  }

  public AgevolazioneTariffariaRistorazioneBuilder setNumeroProtocolloDsu(
      String numeroProtocolloDsu) {
    this.numeroProtocolloDsu = numeroProtocolloDsu;

    return this;
  }

  public AgevolazioneTariffariaRistorazioneBuilder setOmissioneDifformita(
      Boolean omissioneDifformita) {
    this.omissioneDifformita = omissioneDifformita;

    return this;
  }

  public AgevolazioneTariffariaRistorazioneBuilder setStatoRichiestaAgevolazione(
      DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum statoRichiestaAgevolazione) {
    this.statoRichiestaAgevolazione = statoRichiestaAgevolazione;

    return this;
  }

  public AgevolazioneTariffariaRistorazioneBuilder setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;

    return this;
  }

  public AgevolazioneTariffariaRistorazioneBuilder setNome(String nome) {
    this.nome = nome;

    return this;
  }

  public AgevolazioneTariffariaRistorazioneBuilder setCognome(String cognome) {
    this.cognome = cognome;

    return this;
  }

  public AgevolazioneTariffariaRistorazione build() {
    AgevolazioneTariffariaRistorazione atr = new AgevolazioneTariffariaRistorazione();
    atr.setAnnoScolastico(annoScolastico);
    atr.setCodiceFiscale(codiceFiscale);
    atr.setCognome(cognome);
    atr.setDataNascita(dataNascita);
    atr.setDataFineAgevolazione(dataFineAgevolazione);
    atr.setDataInizioAgevolazione(dataInizioAgevolazione);
    atr.setDataPresentazioneAgevolazione(dataPresentazioneAgevolazione);
    atr.setDataValiditaAgevolazione(dataValiditaAgevolazione);
    atr.setDataRichiestaAgevolazione(dataRichiestaAgevolazione);
    atr.setNome(nome);
    atr.setNumeroProtocolloDsu(numeroProtocolloDsu);
    atr.setOmissioneDifformita(omissioneDifformita);
    atr.setStatoRichiestaAgevolazione(statoRichiestaAgevolazione);

    return atr;
  }
}
