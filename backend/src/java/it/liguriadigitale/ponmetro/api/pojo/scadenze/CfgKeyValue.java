package it.liguriadigitale.ponmetro.api.pojo.scadenze;

/**
 * CfgKeyValue
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2020-01-23T11:08:09.966
 */
import java.io.Serializable;

public class CfgKeyValue implements Serializable {
  private static final long serialVersionUID = 1L;

  public CfgKeyValue() {
    super();
  }

  /** Type : VARCHAR Name : CFG_KEY */
  public String cfgKey;

  /** Type : VARCHAR Name : CFG_VALUE */
  public String cfgValue;

  /** Type : VARCHAR Name : UTENTE_INS */
  public String utenteIns;

  /** Type : TIMESTAMP Name : DATA_INS */
  public java.time.LocalDateTime dataIns;

  /** Type : VARCHAR Name : UTENTE_AGG */
  public String utenteAgg;

  /** Type : TIMESTAMP Name : DATA_AGG */
  public java.time.LocalDateTime dataAgg;

  /** Sets the value for cfgKey */
  public void setCfgKey(String cfgKey) {
    this.cfgKey = cfgKey;
  }

  /** Gets the value for cfgKey */
  public String getCfgKey() {
    return cfgKey;
  }

  /** Sets the value for cfgValue */
  public void setCfgValue(String cfgValue) {
    this.cfgValue = cfgValue;
  }

  /** Gets the value for cfgValue */
  public String getCfgValue() {
    return cfgValue;
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
