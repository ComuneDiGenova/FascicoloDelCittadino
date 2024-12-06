package it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi;

/**
 * BravTPermessi
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen null! Non modificare! Creato il:
 * 2023-11-14T12:14:51.263
 */
public class BravTPermessiBuilder {

  public java.math.BigDecimal idPermesso;

  public java.math.BigDecimal idFunz;

  public String tipoFunz;

  public String descrizioneFunz;

  public BravTPermessiBuilder getInstance() {
    return new BravTPermessiBuilder();
  }

  public BravTPermessiBuilder addIdPermesso(java.math.BigDecimal idPermesso) {
    this.idPermesso = idPermesso;
    return this;
  }

  public BravTPermessiBuilder addIdFunz(java.math.BigDecimal idFunz) {
    this.idFunz = idFunz;
    return this;
  }

  public BravTPermessiBuilder addTipoFunz(String tipoFunz) {
    this.tipoFunz = tipoFunz;
    return this;
  }

  public BravTPermessiBuilder addDescrizioneFunz(String descrizioneFunz) {
    this.descrizioneFunz = descrizioneFunz;
    return this;
  }

  public BravTPermessi build() {
    BravTPermessi obj = new BravTPermessi();
    obj.setIdPermesso(this.idPermesso);
    obj.setIdFunz(this.idFunz);
    obj.setTipoFunz(this.tipoFunz);
    obj.setDescrizioneFunz(this.descrizioneFunz);
    return obj;
  }
}
