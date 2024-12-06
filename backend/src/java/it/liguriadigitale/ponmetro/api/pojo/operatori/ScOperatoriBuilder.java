package it.liguriadigitale.ponmetro.api.pojo.operatori;

/**
 * ScOperatori
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen 2.0.7! Non modificare! Creato il:
 * 2022-04-01T11:49:27.061
 */
public class ScOperatoriBuilder {

  public Long idOperatore;

  public String opLogin;

  public String opCf;

  public String opNome;

  public String opCognome;

  public Long idStatoRec;

  public String utenteIns;

  public java.time.LocalDateTime dataIns;

  public String utenteAgg;

  public java.time.LocalDateTime dataAgg;

  public Long idRuolo;

  public ScOperatoriBuilder getInstance() {
    return new ScOperatoriBuilder();
  }

  public ScOperatoriBuilder addIdOperatore(Long idOperatore) {
    this.idOperatore = idOperatore;
    return this;
  }

  public ScOperatoriBuilder addOpLogin(String opLogin) {
    this.opLogin = opLogin;
    return this;
  }

  public ScOperatoriBuilder addOpCf(String opCf) {
    this.opCf = opCf;
    return this;
  }

  public ScOperatoriBuilder addOpNome(String opNome) {
    this.opNome = opNome;
    return this;
  }

  public ScOperatoriBuilder addOpCognome(String opCognome) {
    this.opCognome = opCognome;
    return this;
  }

  public ScOperatoriBuilder addIdStatoRec(Long idStatoRec) {
    this.idStatoRec = idStatoRec;
    return this;
  }

  public ScOperatoriBuilder addUtenteIns(String utenteIns) {
    this.utenteIns = utenteIns;
    return this;
  }

  public ScOperatoriBuilder addDataIns(java.time.LocalDateTime dataIns) {
    this.dataIns = dataIns;
    return this;
  }

  public ScOperatoriBuilder addUtenteAgg(String utenteAgg) {
    this.utenteAgg = utenteAgg;
    return this;
  }

  public ScOperatoriBuilder addDataAgg(java.time.LocalDateTime dataAgg) {
    this.dataAgg = dataAgg;
    return this;
  }

  public ScOperatoriBuilder addIdRuolo(Long idRuolo) {
    this.idRuolo = idRuolo;
    return this;
  }

  public ScOperatori build() {
    ScOperatori obj = new ScOperatori();
    obj.setIdOperatore(this.idOperatore);
    obj.setOpLogin(this.opLogin);
    obj.setOpCf(this.opCf);
    obj.setOpNome(this.opNome);
    obj.setOpCognome(this.opCognome);
    obj.setIdStatoRec(this.idStatoRec);
    obj.setUtenteIns(this.utenteIns);
    obj.setDataIns(this.dataIns);
    obj.setUtenteAgg(this.utenteAgg);
    obj.setDataAgg(this.dataAgg);
    obj.setIdRuolo(this.idRuolo);
    return obj;
  }
}
