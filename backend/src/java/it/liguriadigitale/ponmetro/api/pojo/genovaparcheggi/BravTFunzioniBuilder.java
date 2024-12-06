package it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi;

/**
 * BravTFunzioni
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen null! Non modificare! Creato il:
 * 2023-11-14T12:14:50.580
 */
public class BravTFunzioniBuilder {

  public java.math.BigDecimal idFunz;

  public String tipoFunz;

  public String descrizioneFunz;

  public BravTFunzioniBuilder getInstance() {
    return new BravTFunzioniBuilder();
  }

  public BravTFunzioniBuilder addIdFunz(java.math.BigDecimal idFunz) {
    this.idFunz = idFunz;
    return this;
  }

  public BravTFunzioniBuilder addTipoFunz(String tipoFunz) {
    this.tipoFunz = tipoFunz;
    return this;
  }

  public BravTFunzioniBuilder addDescrizioneFunz(String descrizioneFunz) {
    this.descrizioneFunz = descrizioneFunz;
    return this;
  }

  public BravTFunzioni build() {
    BravTFunzioni obj = new BravTFunzioni();
    obj.setIdFunz(this.idFunz);
    obj.setTipoFunz(this.tipoFunz);
    obj.setDescrizioneFunz(this.descrizioneFunz);
    return obj;
  }
}
