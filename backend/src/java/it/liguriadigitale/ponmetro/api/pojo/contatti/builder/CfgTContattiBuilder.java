package it.liguriadigitale.ponmetro.api.pojo.contatti.builder;

/**
 * CfgTContatti
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen null! Non modificare! Creato il:
 * 2023-08-31T18:12:06.563
 */
import it.liguriadigitale.ponmetro.api.pojo.contatti.CfgTContatti;

public class CfgTContattiBuilder {

  public Long idFcitt;

  public String contatto;

  public java.time.LocalDateTime dataIns;

  public java.time.LocalDateTime dataAgg;

  public String tipo;

  public CfgTContattiBuilder getInstance() {
    return new CfgTContattiBuilder();
  }

  public CfgTContattiBuilder addIdFcitt(Long idFcitt) {
    this.idFcitt = idFcitt;
    return this;
  }

  public CfgTContattiBuilder addContatto(String contatto) {
    this.contatto = contatto;
    return this;
  }

  public CfgTContattiBuilder addDataIns(java.time.LocalDateTime dataIns) {
    this.dataIns = dataIns;
    return this;
  }

  public CfgTContattiBuilder addDataAgg(java.time.LocalDateTime dataAgg) {
    this.dataAgg = dataAgg;
    return this;
  }

  public CfgTContattiBuilder addTipo(String tipo) {
    this.tipo = tipo;
    return this;
  }

  public CfgTContatti build() {
    CfgTContatti obj = new CfgTContatti();
    obj.setIdFcitt(this.idFcitt);
    obj.setContatto(this.contatto);
    obj.setDataIns(this.dataIns);
    obj.setDataAgg(this.dataAgg);
    obj.setTipo(this.tipo);
    return obj;
  }
}
