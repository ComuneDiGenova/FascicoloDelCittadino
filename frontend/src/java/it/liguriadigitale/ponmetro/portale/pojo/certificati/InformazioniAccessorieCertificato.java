package it.liguriadigitale.ponmetro.portale.pojo.certificati;

import it.liguriadigitale.ponmetro.servizianagrafici.model.Atto;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Certificato;
import java.io.Serializable;
import java.time.LocalDate;

public class InformazioniAccessorieCertificato implements Serializable {

  /** */
  private static final long serialVersionUID = -5365942063163173396L;

  private String descrizioneTipoCertificato;
  private String descrizioneMotivazioneRichiesta;
  private boolean isResidenteComuneGenova;
  private LocalDate dataA;
  private LocalDate dataDA;
  private String telefonoContatto;
  private Certificato certificato;
  private Atto atto;

  public Atto getAtto() {
    return atto;
  }

  public void setAtto(Atto atto) {
    this.atto = atto;
  }

  public Certificato getCertificato() {
    return certificato;
  }

  public void setCertificato(Certificato certificato) {
    this.certificato = certificato;
  }

  public String getDescrizioneTipoCertificato() {
    return descrizioneTipoCertificato;
  }

  public void setDescrizioneTipoCertificato(String descrizioneTipoCertificato) {
    this.descrizioneTipoCertificato = descrizioneTipoCertificato;
  }

  public String getDescrizioneMotivazioneRichiesta() {
    return descrizioneMotivazioneRichiesta;
  }

  public void setDescrizioneMotivazioneRichiesta(String descrizioneMotivazioneRichiesta) {
    this.descrizioneMotivazioneRichiesta = descrizioneMotivazioneRichiesta;
  }

  public boolean isResidenteComuneGenova() {
    return isResidenteComuneGenova;
  }

  public void setResidenteComuneGenova(boolean isResidenteComuneGenova) {
    this.isResidenteComuneGenova = isResidenteComuneGenova;
  }

  public LocalDate getDataA() {
    return dataA;
  }

  public void setDataA(LocalDate dataA) {
    this.dataA = dataA;
  }

  public LocalDate getDataDA() {
    return dataDA;
  }

  public void setDataDA(LocalDate dataDA) {
    this.dataDA = dataDA;
  }

  public String getTelefonoContatto() {
    return telefonoContatto;
  }

  public void setTelefonoContatto(String telefonoContatto) {
    this.telefonoContatto = telefonoContatto;
  }
}
