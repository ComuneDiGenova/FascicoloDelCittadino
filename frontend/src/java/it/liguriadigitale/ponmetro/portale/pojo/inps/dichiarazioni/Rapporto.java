package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;
import java.time.LocalDate;

public class Rapporto implements Serializable {

  private static final long serialVersionUID = -4683342814324531347L;

  private String TipoRapporto;
  private String Identificativo;
  private String CodiceFiscaleOperatore;
  private String Saldo3112;
  private String ConsistenzaMedia;
  private String Valore;
  private String DescrizioneOperatoreFinanziario;
  private LocalDate DataInizio;

  public String getTipoRapporto() {
    return TipoRapporto;
  }

  public void setTipoRapporto(String tipoRapporto) {
    TipoRapporto = tipoRapporto;
  }

  public String getIdentificativo() {
    return Identificativo;
  }

  public void setIdentificativo(String identificativo) {
    Identificativo = identificativo;
  }

  public String getCodiceFiscaleOperatore() {
    return CodiceFiscaleOperatore;
  }

  public void setCodiceFiscaleOperatore(String codiceFiscaleOperatore) {
    CodiceFiscaleOperatore = codiceFiscaleOperatore;
  }

  public String getSaldo3112() {
    return Saldo3112;
  }

  public void setSaldo3112(String saldo3112) {
    Saldo3112 = saldo3112;
  }

  public String getConsistenzaMedia() {
    return ConsistenzaMedia;
  }

  public void setConsistenzaMedia(String consistenzaMedia) {
    ConsistenzaMedia = consistenzaMedia;
  }

  public String getValore() {
    return Valore;
  }

  public void setValore(String valore) {
    Valore = valore;
  }

  public String getDescrizioneOperatoreFinanziario() {
    return DescrizioneOperatoreFinanziario;
  }

  public void setDescrizioneOperatoreFinanziario(String descrizioneOperatoreFinanziario) {
    DescrizioneOperatoreFinanziario = descrizioneOperatoreFinanziario;
  }

  public LocalDate getDataInizio() {
    return DataInizio;
  }

  public void setDataInizio(LocalDate dataInizio) {
    DataInizio = dataInizio;
  }

  @Override
  public String toString() {
    return "Rapporto [TipoRapporto="
        + TipoRapporto
        + ", Identificativo="
        + Identificativo
        + ", CodiceFiscaleOperatore="
        + CodiceFiscaleOperatore
        + ", Saldo3112="
        + Saldo3112
        + ", ConsistenzaMedia="
        + ConsistenzaMedia
        + ", Valore="
        + Valore
        + ", DescrizioneOperatoreFinanziario="
        + DescrizioneOperatoreFinanziario
        + ", DataInizio="
        + DataInizio
        + "]";
  }
}
