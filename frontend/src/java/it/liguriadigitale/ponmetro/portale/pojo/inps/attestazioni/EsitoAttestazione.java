package it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni;

import java.io.Serializable;
import java.time.LocalDate;

public class EsitoAttestazione implements Serializable {

  private static final long serialVersionUID = -6385179724745663836L;

  private String attestazioneCodiceFiscaleDichiarante;
  private String attestazioneNumeroProtocolloDSU;
  private LocalDate attestazioneDataPresentazione;
  private LocalDate attestazioneDataValidita;
  private Double iseeOrdinarioISEE;
  private NucleoFamiliare nucleoFamiliare;
  private ISEEMin iseeMin;
  private OmissioneDifformita[] omissioneDifformita;

  public String getAttestazioneCodiceFiscaleDichiarante() {
    return attestazioneCodiceFiscaleDichiarante;
  }

  public void setAttestazioneCodiceFiscaleDichiarante(String attestazioneCodiceFiscaleDichiarante) {
    this.attestazioneCodiceFiscaleDichiarante = attestazioneCodiceFiscaleDichiarante;
  }

  public String getAttestazioneNumeroProtocolloDSU() {
    return attestazioneNumeroProtocolloDSU;
  }

  public void setAttestazioneNumeroProtocolloDSU(String attestazioneNumeroProtocolloDSU) {
    this.attestazioneNumeroProtocolloDSU = attestazioneNumeroProtocolloDSU;
  }

  public LocalDate getAttestazioneDataPresentazione() {
    return attestazioneDataPresentazione;
  }

  public void setAttestazioneDataPresentazione(LocalDate attestazioneDataPresentazione) {
    this.attestazioneDataPresentazione = attestazioneDataPresentazione;
  }

  public LocalDate getAttestazioneDataValidita() {
    return attestazioneDataValidita;
  }

  public void setAttestazioneDataValidita(LocalDate attestazioneDataValidita) {
    this.attestazioneDataValidita = attestazioneDataValidita;
  }

  public Double getIseeOrdinarioISEE() {
    return iseeOrdinarioISEE;
  }

  public void setIseeOrdinarioISEE(Double iseeOrdinarioISEE) {
    this.iseeOrdinarioISEE = iseeOrdinarioISEE;
  }

  public ISEEMin getIseeMin() {
    return iseeMin;
  }

  public void setIseeMin(ISEEMin iseeMin) {
    this.iseeMin = iseeMin;
  }

  public NucleoFamiliare getNucleoFamiliare() {
    return nucleoFamiliare;
  }

  public void setNucleoFamiliare(NucleoFamiliare nucleoFamiliare) {
    this.nucleoFamiliare = nucleoFamiliare;
  }

  @Override
  public String toString() {
    return "EsitoAttestazione [attestazioneCodiceFiscaleDichiarante="
        + attestazioneCodiceFiscaleDichiarante
        + ", attestazioneNumeroProtocolloDSU="
        + attestazioneNumeroProtocolloDSU
        + ", attestazioneDataPresentazione="
        + attestazioneDataPresentazione
        + ", attestazioneDataValidita="
        + attestazioneDataValidita
        + ", iseeOrdinarioISEE="
        + iseeOrdinarioISEE
        + ", nucleo="
        + nucleoFamiliare
        + ", iseeMin="
        + iseeMin
        + "]";
  }

  public OmissioneDifformita[] getOmissioneDifformita() {
    return omissioneDifformita;
  }

  public void setOmissioneDifformita(OmissioneDifformita[] omissioneDifformita) {
    this.omissioneDifformita = omissioneDifformita;
  }
}
