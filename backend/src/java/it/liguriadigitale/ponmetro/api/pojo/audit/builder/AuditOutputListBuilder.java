package it.liguriadigitale.ponmetro.api.pojo.audit.builder;

import it.liguriadigitale.ponmetro.audit.model.AuditOutputList;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class AuditOutputListBuilder {

  private BigDecimal personId;
  private String nomePagina;
  private String comparto;
  private String sezione;
  private String funzione;
  private Boolean isAutorizzato;
  private OffsetDateTime timeStamp;
  private String ambienteDeploy;
  private String sessionToken;
  private BigDecimal tipoUtente;
  private String descrizioneTipoUtente;
  private String sesso;
  private String annoNascita;
  private String servizioEsterno;

  private AuditOutputListBuilder() {
    super();
  }

  public static AuditOutputListBuilder getInstance() {
    return new AuditOutputListBuilder();
  }

  public AuditOutputListBuilder setAnnoNascita(String annoNascita) {
    this.annoNascita = annoNascita;
    return this;
  }

  public AuditOutputListBuilder setSesso(String sesso) {
    this.sesso = sesso;
    return this;
  }

  public AuditOutputListBuilder setServiziEsterni(String servizioEsterno) {
    this.servizioEsterno = servizioEsterno;
    return this;
  }

  public AuditOutputListBuilder setPersonId(BigDecimal personId) {
    this.personId = personId;
    return this;
  }

  public AuditOutputListBuilder setNomePagina(String nomePagina) {
    this.nomePagina = nomePagina;
    return this;
  }

  public AuditOutputListBuilder setComparto(String comparto) {
    this.comparto = comparto;
    return this;
  }

  public AuditOutputListBuilder setSezione(String sezione) {
    this.sezione = sezione;
    return this;
  }

  public AuditOutputListBuilder setIsAutorizzato(Boolean isAutorizzato) {
    this.isAutorizzato = isAutorizzato;
    return this;
  }

  public AuditOutputListBuilder setTimeStamp(OffsetDateTime timeStamp) {
    this.timeStamp = timeStamp;
    return this;
  }

  public AuditOutputListBuilder setAmbienteDeploy(String ambienteDeploy) {
    this.ambienteDeploy = ambienteDeploy;
    return this;
  }

  public AuditOutputListBuilder setSessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
    return this;
  }

  public AuditOutputListBuilder setFunzione(String funzione) {
    this.funzione = funzione;
    return this;
  }

  public AuditOutputListBuilder setTipoUtente(BigDecimal tipoUtente) {
    this.tipoUtente = tipoUtente;
    return this;
  }

  public AuditOutputListBuilder setDescrizioneTipoUtente(String descrizioneTipoUtente) {
    this.descrizioneTipoUtente = descrizioneTipoUtente;
    return this;
  }

  public AuditOutputList build() {
    AuditOutputList auditOutputList = new AuditOutputList();
    auditOutputList.setAmbienteDeploy(ambienteDeploy);
    auditOutputList.setComparto(comparto);
    auditOutputList.setIsAutorizzato(isAutorizzato);
    auditOutputList.setNomePagina(nomePagina);
    auditOutputList.setPersonId(personId);
    auditOutputList.setSessionToken(sessionToken);
    auditOutputList.setSezione(sezione);
    auditOutputList.setTimeStamp(timeStamp);
    auditOutputList.setFunzione(funzione);
    auditOutputList.setTipoUtente(tipoUtente);
    auditOutputList.setDescrizioneTipoUtente(descrizioneTipoUtente);
    auditOutputList.setServiziEsterni(servizioEsterno);
    auditOutputList.setSesso(sesso);
    auditOutputList.setAnnoNascita(annoNascita);

    return auditOutputList;
  }
}
