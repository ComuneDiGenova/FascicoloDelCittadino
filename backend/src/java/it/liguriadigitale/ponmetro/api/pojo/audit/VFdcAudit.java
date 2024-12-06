package it.liguriadigitale.ponmetro.api.pojo.audit;

/**
 * VFdcAudit
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2021-02-17T10:01:33.039
 */
import java.io.Serializable;

public class VFdcAudit implements Serializable {
  private static final long serialVersionUID = 1L;

  public VFdcAudit() {
    super();
  }

  /** Type : BIGINT Name : ID_FCITT */
  public java.math.BigDecimal idFcitt;

  /** Type : VARCHAR Name : NOME_PAGINA */
  public String nomePagina;

  /** Type : VARCHAR Name : AMBIENTE */
  public String ambiente;

  /** Type : VARCHAR Name : SESSION_ID */
  public String sessionId;

  /** Type : BIGINT Name : AUTORIZZATO */
  public Boolean autorizzato;

  /** Type : TIMESTAMP Name : TIME_STAMP */
  public java.time.LocalDateTime timeStamp;

  /** Type : BIGINT Name : TIPO_UTENTE */
  public java.math.BigDecimal tipoUtente;

  /** Type : VARCHAR Name : DESCRIZIONE_TIPO_UTENTE */
  public String descrizioneTipoUtente;

  /** Type : VARCHAR Name : DESCRIZIONE_FUNZ */
  public String descrizioneFunz;

  /** Type : BIGINT Name : ID_FUNZ */
  public java.math.BigDecimal idFunz;

  /** Type : VARCHAR Name : DENOMINAZIONE_COMP */
  public String denominazioneComp;

  /** Type : BIGINT Name : ID_COMP */
  public java.math.BigDecimal idComp;

  /** Type : VARCHAR Name : DESCRIZIONE_SEZ */
  public String descrizioneSez;

  /** Type : BIGINT Name : ID_SEZ */
  public java.math.BigDecimal idSez;

  /** Sets the value for idFcitt */
  public void setIdFcitt(java.math.BigDecimal idFcitt) {
    this.idFcitt = idFcitt;
  }

  /** Gets the value for idFcitt */
  public java.math.BigDecimal getIdFcitt() {
    return idFcitt;
  }

  /** Sets the value for nomePagina */
  public void setNomePagina(String nomePagina) {
    this.nomePagina = nomePagina;
  }

  /** Gets the value for nomePagina */
  public String getNomePagina() {
    return nomePagina;
  }

  /** Sets the value for ambiente */
  public void setAmbiente(String ambiente) {
    this.ambiente = ambiente;
  }

  /** Gets the value for ambiente */
  public String getAmbiente() {
    return ambiente;
  }

  /** Sets the value for sessionId */
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  /** Gets the value for sessionId */
  public String getSessionId() {
    return sessionId;
  }

  /** Sets the value for autorizzato */
  public void setAutorizzato(Boolean autorizzato) {
    this.autorizzato = autorizzato;
  }

  /** Gets the value for autorizzato */
  public Boolean getAutorizzato() {
    return autorizzato;
  }

  /** Sets the value for timeStamp */
  public void setTimeStamp(java.time.LocalDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  /** Gets the value for timeStamp */
  public java.time.LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  /** Sets the value for tipoUtente */
  public void setTipoUtente(java.math.BigDecimal tipoUtente) {
    this.tipoUtente = tipoUtente;
  }

  /** Gets the value for tipoUtente */
  public java.math.BigDecimal getTipoUtente() {
    return tipoUtente;
  }

  /** Sets the value for descrizioneTipoUtente */
  public void setDescrizioneTipoUtente(String descrizioneTipoUtente) {
    this.descrizioneTipoUtente = descrizioneTipoUtente;
  }

  /** Gets the value for descrizioneTipoUtente */
  public String getDescrizioneTipoUtente() {
    return descrizioneTipoUtente;
  }

  /** Sets the value for descrizioneFunz */
  public void setDescrizioneFunz(String descrizioneFunz) {
    this.descrizioneFunz = descrizioneFunz;
  }

  /** Gets the value for descrizioneFunz */
  public String getDescrizioneFunz() {
    return descrizioneFunz;
  }

  /** Sets the value for idFunz */
  public void setIdFunz(java.math.BigDecimal idFunz) {
    this.idFunz = idFunz;
  }

  /** Gets the value for idFunz */
  public java.math.BigDecimal getIdFunz() {
    return idFunz;
  }

  /** Sets the value for denominazioneComp */
  public void setDenominazioneComp(String denominazioneComp) {
    this.denominazioneComp = denominazioneComp;
  }

  /** Gets the value for denominazioneComp */
  public String getDenominazioneComp() {
    return denominazioneComp;
  }

  /** Sets the value for idComp */
  public void setIdComp(java.math.BigDecimal idComp) {
    this.idComp = idComp;
  }

  /** Gets the value for idComp */
  public java.math.BigDecimal getIdComp() {
    return idComp;
  }

  /** Sets the value for descrizioneSez */
  public void setDescrizioneSez(String descrizioneSez) {
    this.descrizioneSez = descrizioneSez;
  }

  /** Gets the value for descrizioneSez */
  public String getDescrizioneSez() {
    return descrizioneSez;
  }

  /** Sets the value for idSez */
  public void setIdSez(java.math.BigDecimal idSez) {
    this.idSez = idSez;
  }

  /** Gets the value for idSez */
  public java.math.BigDecimal getIdSez() {
    return idSez;
  }

  @Override
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
