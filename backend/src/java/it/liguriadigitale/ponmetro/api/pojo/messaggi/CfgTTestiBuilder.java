package it.liguriadigitale.ponmetro.api.pojo.messaggi;

/**
 * CfgTTesti
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen 2.0.7! Non modificare! Creato il:
 * 2022-06-06T17:11:07.345
 */
public class CfgTTestiBuilder {

  public Long idCfgTesti;

  public String tipoTesto;

  public String cfgValue;

  public String classeJava;

  public Long ordine;

  public java.time.LocalDateTime dataValInizio;

  public java.time.LocalDateTime dataValFine;

  public String utenteIns;

  public java.time.LocalDateTime dataIns;

  public String utenteAgg;

  public java.time.LocalDateTime dataAgg;

  public CfgTTestiBuilder getInstance() {
    return new CfgTTestiBuilder();
  }

  public CfgTTestiBuilder addIdCfgTesti(Long idCfgTesti) {
    this.idCfgTesti = idCfgTesti;
    return this;
  }

  public CfgTTestiBuilder addTipoTesto(String tipoTesto) {
    this.tipoTesto = tipoTesto;
    return this;
  }

  public CfgTTestiBuilder addCfgValue(String cfgValue) {
    this.cfgValue = cfgValue;
    return this;
  }

  public CfgTTestiBuilder addClasseJava(String classeJava) {
    this.classeJava = classeJava;
    return this;
  }

  public CfgTTestiBuilder addOrdine(Long ordine) {
    this.ordine = ordine;
    return this;
  }

  public CfgTTestiBuilder addDataValInizio(java.time.LocalDateTime dataValInizio) {
    this.dataValInizio = dataValInizio;
    return this;
  }

  public CfgTTestiBuilder addDataValFine(java.time.LocalDateTime dataValFine) {
    this.dataValFine = dataValFine;
    return this;
  }

  public CfgTTestiBuilder addUtenteIns(String utenteIns) {
    this.utenteIns = utenteIns;
    return this;
  }

  public CfgTTestiBuilder addDataIns(java.time.LocalDateTime dataIns) {
    this.dataIns = dataIns;
    return this;
  }

  public CfgTTestiBuilder addUtenteAgg(String utenteAgg) {
    this.utenteAgg = utenteAgg;
    return this;
  }

  public CfgTTestiBuilder addDataAgg(java.time.LocalDateTime dataAgg) {
    this.dataAgg = dataAgg;
    return this;
  }

  public CfgTTesti build() {
    CfgTTesti obj = new CfgTTesti();
    obj.setIdCfgTesti(this.idCfgTesti);
    obj.setTipoTesto(this.tipoTesto);
    obj.setCfgValue(this.cfgValue);
    obj.setClasseJava(this.classeJava);
    obj.setOrdine(this.ordine);
    obj.setDataValInizio(this.dataValInizio);
    obj.setDataValFine(this.dataValFine);
    obj.setUtenteIns(this.utenteIns);
    obj.setDataIns(this.dataIns);
    obj.setUtenteAgg(this.utenteAgg);
    obj.setDataAgg(this.dataAgg);
    return obj;
  }
}
