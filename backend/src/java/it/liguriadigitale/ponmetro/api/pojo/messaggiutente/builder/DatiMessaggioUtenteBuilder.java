package it.liguriadigitale.ponmetro.api.pojo.messaggiutente.builder;

import it.liguriadigitale.ponmetro.messaggi.utente.model.DatiMessaggioUtente;
import java.time.OffsetDateTime;

public class DatiMessaggioUtenteBuilder {

  private Long idFcittNotifiche;
  private String sezioneDenominazione;
  private String sezioneDescrizione;
  private String sezioneUri;
  private String compartoDenominazione;
  private String compartoDescrizione;
  private String compartoUri;
  private OffsetDateTime compartoDataCatalogazione;
  private Long ordinamento;
  private String notificaTesto;
  private OffsetDateTime notificaData;
  private String notificaUri;
  private OffsetDateTime visualizzazioneData;
  private Long statoRecord;

  public DatiMessaggioUtenteBuilder setIdFcittNotifiche(Long idFcittNotifiche) {
    this.idFcittNotifiche = idFcittNotifiche;

    return this;
  }

  public DatiMessaggioUtenteBuilder setSezioneDenominazione(String sezioneDenominazione) {
    this.sezioneDenominazione = sezioneDenominazione;
    return this;
  }

  public DatiMessaggioUtenteBuilder setSezioneDescrizione(String sezioneDescrizione) {
    this.sezioneDescrizione = sezioneDescrizione;
    return this;
  }

  public DatiMessaggioUtenteBuilder setSezioneUri(String sezioneUri) {
    this.sezioneUri = sezioneUri;
    return this;
  }

  public DatiMessaggioUtenteBuilder setCompartoDenominazione(String compartoDenominazione) {
    this.compartoDenominazione = compartoDenominazione;
    return this;
  }

  public DatiMessaggioUtenteBuilder setCompartoDescrizione(String compartoDescrizione) {
    this.compartoDescrizione = compartoDescrizione;
    return this;
  }

  public DatiMessaggioUtenteBuilder setCompartoUri(String compartoUri) {
    this.compartoUri = compartoUri;
    return this;
  }

  public DatiMessaggioUtenteBuilder setCompartoDataCatalogazione(
      OffsetDateTime compartoDataCatalogazione) {
    this.compartoDataCatalogazione = compartoDataCatalogazione;
    return this;
  }

  public DatiMessaggioUtenteBuilder setOrdinamento(Long ordinamento) {
    this.ordinamento = ordinamento;
    return this;
  }

  public DatiMessaggioUtenteBuilder setNotificaTesto(String notificaTesto) {
    this.notificaTesto = notificaTesto;
    return this;
  }

  public DatiMessaggioUtenteBuilder setNotificaData(OffsetDateTime notificaData) {
    this.notificaData = notificaData;
    return this;
  }

  public DatiMessaggioUtenteBuilder setNotificaUri(String notificaUri) {
    this.notificaUri = notificaUri;
    return this;
  }

  public DatiMessaggioUtenteBuilder setVisualizzazioneData(OffsetDateTime visualizzazioneData) {
    this.visualizzazioneData = visualizzazioneData;
    return this;
  }

  public DatiMessaggioUtenteBuilder setStatoRecord(Long statoRecord) {
    this.statoRecord = statoRecord;
    return this;
  }

  public DatiMessaggioUtente build() {
    DatiMessaggioUtente toRet = new DatiMessaggioUtente();

    toRet.setIdFcittNotifiche(idFcittNotifiche);
    toRet.setSezioneDenominazione(sezioneDenominazione);
    toRet.setSezioneDescrizione(sezioneDescrizione);
    toRet.setSezioneUri(sezioneUri);
    toRet.setCompartoDenominazione(compartoDenominazione);
    toRet.setCompartoDescrizione(compartoDescrizione);
    toRet.setCompartoUri(compartoUri);
    toRet.setCompartoDataCatalogazione(compartoDataCatalogazione);
    toRet.setOrdinamento(ordinamento);
    toRet.setNotificaTesto(notificaTesto);
    toRet.setNotificaData(notificaData);
    toRet.setNotificaUri(notificaUri);
    toRet.setVisualizzazioneData(visualizzazioneData);
    toRet.setStatoRecord(statoRecord);

    return toRet;
  }
}
