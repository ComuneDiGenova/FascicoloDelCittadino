package it.liguriadigitale.ponmetro.api.pojo.messaggiutente;

/**
 * CfgRFcittNotifiche
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import java.io.Serializable;

public class CfgRFcittNotifiche implements Serializable {
  private static final long serialVersionUID = 1L;

  public CfgRFcittNotifiche() {
    super();
  }

  /** Type : BIGINT Name : ID_FCITT_NOTIFICHE */
  public java.math.BigDecimal idFcittNotifiche;

  /** Type : BIGINT Name : ID_NOTIFICA */
  public Long idNotifica;

  /** Type : BIGINT Name : ID_FCITT */
  public Long idFcitt;

  /** Type : TIMESTAMP Name : DATA_PRESA_VISIONE */
  public java.time.LocalDateTime dataPresaVisione;

  /** Type : BIGINT Name : ID_STATO_REC */
  public Long idStatoRec;

  /** Type : VARCHAR Name : UTENTE_INS */
  public String utenteIns;

  /** Type : TIMESTAMP Name : DATA_INS */
  public java.time.LocalDateTime dataIns;

  /** Type : VARCHAR Name : UTENTE_AGG */
  public String utenteAgg;

  /** Type : TIMESTAMP Name : DATA_AGG */
  public java.time.LocalDateTime dataAgg;

  /** Sets the value for idFcittNotifiche */
  public void setIdFcittNotifiche(java.math.BigDecimal idFcittNotifiche) {
    this.idFcittNotifiche = idFcittNotifiche;
  }

  /** Gets the value for idFcittNotifiche */
  public java.math.BigDecimal getIdFcittNotifiche() {
    return idFcittNotifiche;
  }

  /** Sets the value for idNotifica */
  public void setIdNotifica(Long idNotifica) {
    this.idNotifica = idNotifica;
  }

  /** Gets the value for idNotifica */
  public Long getIdNotifica() {
    return idNotifica;
  }

  /** Sets the value for idFcitt */
  public void setIdFcitt(Long idFcitt) {
    this.idFcitt = idFcitt;
  }

  /** Gets the value for idFcitt */
  public Long getIdFcitt() {
    return idFcitt;
  }

  /** Sets the value for dataPresaVisione */
  public void setDataPresaVisione(java.time.LocalDateTime dataPresaVisione) {
    this.dataPresaVisione = dataPresaVisione;
  }

  /** Gets the value for dataPresaVisione */
  public java.time.LocalDateTime getDataPresaVisione() {
    return dataPresaVisione;
  }

  /** Sets the value for idStatoRec */
  public void setIdStatoRec(Long idStatoRec) {
    this.idStatoRec = idStatoRec;
  }

  /** Gets the value for idStatoRec */
  public Long getIdStatoRec() {
    return idStatoRec;
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
