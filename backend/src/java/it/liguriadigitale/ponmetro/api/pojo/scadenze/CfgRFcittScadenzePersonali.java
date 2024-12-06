package it.liguriadigitale.ponmetro.api.pojo.scadenze;

/**
 * CfgRFcittScadenzePersonali
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2020-01-23T11:08:10.811
 */
import java.io.Serializable;

public class CfgRFcittScadenzePersonali implements Serializable {
  private static final long serialVersionUID = 1L;

  public CfgRFcittScadenzePersonali() {
    super();
  }

  /** Type : BIGINT Name : ID_SCADENZA */
  public Long idScadenza;

  /** Type : BIGINT Name : ID_T_EVENTO */
  public Long idTEvento;

  /** Type : BIGINT Name : ID_STATO */
  public Long idStato;

  /** Type : BIGINT Name : ID_FCITT */
  public Long idFcitt;

  /** Type : VARCHAR Name : SCADENZA */
  public String scadenza;

  /** Type : TIMESTAMP Name : DATA_SCADENZA */
  public java.time.LocalDateTime dataScadenza;

  /** Type : TIMESTAMP Name : DATA_INVIO_MSG */
  public java.time.LocalDateTime dataInvioMsg;

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

  /** Type : VARCHAR Name : OGGETTO */
  public String oggetto;

  /** Sets the value for idScadenza */
  public void setIdScadenza(Long idScadenza) {
    this.idScadenza = idScadenza;
  }

  /** Gets the value for idScadenza */
  public Long getIdScadenza() {
    return idScadenza;
  }

  /** Sets the value for idTEvento */
  public void setIdTEvento(Long idTEvento) {
    this.idTEvento = idTEvento;
  }

  /** Gets the value for idTEvento */
  public Long getIdTEvento() {
    return idTEvento;
  }

  /** Sets the value for idStato */
  public void setIdStato(Long idStato) {
    this.idStato = idStato;
  }

  /** Gets the value for idStato */
  public Long getIdStato() {
    return idStato;
  }

  /** Sets the value for idFcitt */
  public void setIdFcitt(Long idFcitt) {
    this.idFcitt = idFcitt;
  }

  /** Gets the value for idFcitt */
  public Long getIdFcitt() {
    return idFcitt;
  }

  /** Sets the value for scadenza */
  public void setScadenza(String scadenza) {
    this.scadenza = scadenza;
  }

  /** Gets the value for scadenza */
  public String getScadenza() {
    return scadenza;
  }

  /** Sets the value for dataScadenza */
  public void setDataScadenza(java.time.LocalDateTime dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  /** Gets the value for dataScadenza */
  public java.time.LocalDateTime getDataScadenza() {
    return dataScadenza;
  }

  /** Sets the value for dataInvioMsg */
  public void setDataInvioMsg(java.time.LocalDateTime dataInvioMsg) {
    this.dataInvioMsg = dataInvioMsg;
  }

  /** Gets the value for dataInvioMsg */
  public java.time.LocalDateTime getDataInvioMsg() {
    return dataInvioMsg;
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

  /** Sets the value for oggetto */
  public void setOggetto(String oggetto) {
    this.oggetto = oggetto;
  }

  /** Gets the value for oggetto */
  public String getOggetto() {
    return oggetto;
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
