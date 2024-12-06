package it.liguriadigitale.ponmetro.api.pojo.geoworks.builder;

import it.liguriadigitale.ponmetro.api.pojo.geoworks.GeoworksTFunzioni;

/**
 * GeoworksTFunzioni
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen null! Non modificare! Creato il:
 * 2023-10-24T10:39:21.289
 */
public class GeoworksTFunzioniBuilder {

  public java.math.BigDecimal idFunz;

  public String tipoFunz;

  public String descrizioneFunz;

  public GeoworksTFunzioniBuilder getInstance() {
    return new GeoworksTFunzioniBuilder();
  }

  public GeoworksTFunzioniBuilder addIdFunz(java.math.BigDecimal idFunz) {
    this.idFunz = idFunz;
    return this;
  }

  public GeoworksTFunzioniBuilder addTipoFunz(String tipoFunz) {
    this.tipoFunz = tipoFunz;
    return this;
  }

  public GeoworksTFunzioniBuilder addDescrizioneFunz(String descrizioneFunz) {
    this.descrizioneFunz = descrizioneFunz;
    return this;
  }

  public GeoworksTFunzioni build() {
    GeoworksTFunzioni obj = new GeoworksTFunzioni();
    obj.setIdFunz(this.idFunz);
    obj.setTipoFunz(this.tipoFunz);
    obj.setDescrizioneFunz(this.descrizioneFunz);
    return obj;
  }
}
