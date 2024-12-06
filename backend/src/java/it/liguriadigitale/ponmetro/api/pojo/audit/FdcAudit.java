package it.liguriadigitale.ponmetro.api.pojo.audit;

/**
 * FdcAudit
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-10-17T15:18:44.734496
 */
import java.io.Serializable;

public class FdcAudit implements Serializable {
  private static final long serialVersionUID = 1L;

  public FdcAudit() {
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
  public Long tipoUtente;

  /** Type : VARCHAR Name : ANNO_NASCITA */
  public String annoNascita;

  /** Type : VARCHAR Name : SESSO */
  public String sesso;

  /** Type : VARCHAR Name : SERVIZIO_ESTERNO */
  public String servizioEsterno;

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
  public void setTipoUtente(Long tipoUtente) {
    this.tipoUtente = tipoUtente;
  }

  /** Gets the value for tipoUtente */
  public Long getTipoUtente() {
    return tipoUtente;
  }

  /** Sets the value for annoNascita */
  public void setAnnoNascita(String annoNascita) {
    this.annoNascita = annoNascita;
  }

  /** Gets the value for annoNascita */
  public String getAnnoNascita() {
    return annoNascita;
  }

  /** Sets the value for sesso */
  public void setSesso(String sesso) {
    this.sesso = sesso;
  }

  /** Gets the value for sesso */
  public String getSesso() {
    return sesso;
  }

  /** Sets the value for servizioEsterno */
  public void setServizioEsterno(String servizioEsterno) {
    this.servizioEsterno = servizioEsterno;
  }

  /** Gets the value for servizioEsterno */
  public String getServizioEsterno() {
    return servizioEsterno;
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
