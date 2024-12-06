package it.liguriadigitale.ponmetro.api.pojo.messaggi;

/**
 * CfgTTesti
 *
 * <p>WARNING! Automatically generated file with TableGen 2.0.7! Do not edit! created:
 * 2022-06-06T17:11:07.324
 */
import java.io.Serializable;

public class CfgTTesti implements Serializable {
  private static final long serialVersionUID = 1L;

  public CfgTTesti() {
    super();
  }

  /** Type : BIGINT Name : ID_CFG_TESTI */
  public Long idCfgTesti;

  /** Type : VARCHAR Name : TIPO_TESTO */
  public String tipoTesto;

  /** Type : VARCHAR Name : CFG_VALUE */
  public String cfgValue;

  /** Type : VARCHAR Name : CLASSE_JAVA */
  public String classeJava;

  /** Type : BIGINT Name : ORDINE */
  public Long ordine;

  /** Type : TIMESTAMP Name : DATA_VAL_INIZIO */
  public java.time.LocalDateTime dataValInizio;

  /** Type : TIMESTAMP Name : DATA_VAL_FINE */
  public java.time.LocalDateTime dataValFine;

  /** Type : VARCHAR Name : UTENTE_INS */
  public String utenteIns;

  /** Type : TIMESTAMP Name : DATA_INS */
  public java.time.LocalDateTime dataIns;

  /** Type : VARCHAR Name : UTENTE_AGG */
  public String utenteAgg;

  /** Type : TIMESTAMP Name : DATA_AGG */
  public java.time.LocalDateTime dataAgg;

  /** Sets the value for idCfgTesti */
  public void setIdCfgTesti(Long idCfgTesti) {
    this.idCfgTesti = idCfgTesti;
  }

  /** Gets the value for idCfgTesti */
  public Long getIdCfgTesti() {
    return idCfgTesti;
  }

  /** Sets the value for tipoTesto */
  public void setTipoTesto(String tipoTesto) {
    this.tipoTesto = tipoTesto;
  }

  /** Gets the value for tipoTesto */
  public String getTipoTesto() {
    return tipoTesto;
  }

  /** Sets the value for cfgValue */
  public void setCfgValue(String cfgValue) {
    this.cfgValue = cfgValue;
  }

  /** Gets the value for cfgValue */
  public String getCfgValue() {
    return cfgValue;
  }

  /** Sets the value for classeJava */
  public void setClasseJava(String classeJava) {
    this.classeJava = classeJava;
  }

  /** Gets the value for classeJava */
  public String getClasseJava() {
    return classeJava;
  }

  /** Sets the value for ordine */
  public void setOrdine(Long ordine) {
    this.ordine = ordine;
  }

  /** Gets the value for ordine */
  public Long getOrdine() {
    return ordine;
  }

  /** Sets the value for dataValInizio */
  public void setDataValInizio(java.time.LocalDateTime dataValInizio) {
    this.dataValInizio = dataValInizio;
  }

  /** Gets the value for dataValInizio */
  public java.time.LocalDateTime getDataValInizio() {
    return dataValInizio;
  }

  /** Sets the value for dataValFine */
  public void setDataValFine(java.time.LocalDateTime dataValFine) {
    this.dataValFine = dataValFine;
  }

  /** Gets the value for dataValFine */
  public java.time.LocalDateTime getDataValFine() {
    return dataValFine;
  }

  /** Sets the value for utenteIns */
  public void setUtenteIns(String utenteIns) {
    this.utenteIns = utenteIns;
  }

  /** Gets the value for utenteIns */
  public String getUtenteIns() {
    return utenteIns;
  }

  /** Sets the value for dataIns */
  public void setDataIns(java.time.LocalDateTime dataIns) {
    this.dataIns = dataIns;
  }

  /** Gets the value for dataIns */
  public java.time.LocalDateTime getDataIns() {
    return dataIns;
  }

  /** Sets the value for utenteAgg */
  public void setUtenteAgg(String utenteAgg) {
    this.utenteAgg = utenteAgg;
  }

  /** Gets the value for utenteAgg */
  public String getUtenteAgg() {
    return utenteAgg;
  }

  /** Sets the value for dataAgg */
  public void setDataAgg(java.time.LocalDateTime dataAgg) {
    this.dataAgg = dataAgg;
  }

  /** Gets the value for dataAgg */
  public java.time.LocalDateTime getDataAgg() {
    return dataAgg;
  }

  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append(super.toString());
    try {
      java.lang.reflect.Field[] fields = getClass().getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
        String fieldName = fields[i].getName();
        Object fieldValue = fields[i].get(this);
        String line = "\n" + fieldName + ": " + fieldValue;
        str.append(line);
      }
      return str.toString();
    } catch (Exception e) {
      return str.toString();
    }
  }
}
