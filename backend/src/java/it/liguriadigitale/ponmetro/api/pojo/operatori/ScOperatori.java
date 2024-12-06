package it.liguriadigitale.ponmetro.api.pojo.operatori;

/**
 * ScOperatori
 *
 * <p>WARNING! Automatically generated file with TableGen 2.0.7! Do not edit! created:
 * 2022-04-01T11:49:27.034
 */
import java.io.Serializable;

public class ScOperatori implements Serializable {
  private static final long serialVersionUID = 1L;

  public ScOperatori() {
    super();
  }

  /** Type : BIGINT Name : ID_OPERATORE */
  public Long idOperatore;

  /** Type : VARCHAR Name : OP_LOGIN */
  public String opLogin;

  /** Type : VARCHAR Name : OP_CF */
  public String opCf;

  /** Type : VARCHAR Name : OP_NOME */
  public String opNome;

  /** Type : VARCHAR Name : OP_COGNOME */
  public String opCognome;

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

  /** Type : BIGINT Name : ID_RUOLO */
  public Long idRuolo;

  /** Sets the value for idOperatore */
  public void setIdOperatore(Long idOperatore) {
    this.idOperatore = idOperatore;
  }

  /** Gets the value for idOperatore */
  public Long getIdOperatore() {
    return idOperatore;
  }

  /** Sets the value for opLogin */
  public void setOpLogin(String opLogin) {
    this.opLogin = opLogin;
  }

  /** Gets the value for opLogin */
  public String getOpLogin() {
    return opLogin;
  }

  /** Sets the value for opCf */
  public void setOpCf(String opCf) {
    this.opCf = opCf;
  }

  /** Gets the value for opCf */
  public String getOpCf() {
    return opCf;
  }

  /** Sets the value for opNome */
  public void setOpNome(String opNome) {
    this.opNome = opNome;
  }

  /** Gets the value for opNome */
  public String getOpNome() {
    return opNome;
  }

  /** Sets the value for opCognome */
  public void setOpCognome(String opCognome) {
    this.opCognome = opCognome;
  }

  /** Gets the value for opCognome */
  public String getOpCognome() {
    return opCognome;
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

  /** Sets the value for idRuolo */
  public void setIdRuolo(Long idRuolo) {
    this.idRuolo = idRuolo;
  }

  /** Gets the value for idRuolo */
  public Long getIdRuolo() {
    return idRuolo;
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
