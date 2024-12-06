package it.liguriadigitale.ponmetro.api.pojo.geoworks.builder;

/**
 * GeoworksTServizi
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen null! Non modificare! Creato il:
 * 2023-10-24T12:30:36.824
 */
import it.liguriadigitale.ponmetro.api.pojo.geoworks.GeoworksTServizi;

public class GeoworksTServiziBuilder {

  public java.math.BigDecimal idServizio;

  public java.math.BigDecimal idFunz;

  public String descrizioneServizio;

  public String tipoFunz;

  public GeoworksTServiziBuilder getInstance() {
    return new GeoworksTServiziBuilder();
  }

  public GeoworksTServiziBuilder addIdServizio(java.math.BigDecimal idServizio) {
    this.idServizio = idServizio;
    return this;
  }

  public GeoworksTServiziBuilder addIdFunz(java.math.BigDecimal idFunz) {
    this.idFunz = idFunz;
    return this;
  }

  public GeoworksTServiziBuilder addDescrizioneServizio(String descrizioneServizio) {
    this.descrizioneServizio = descrizioneServizio;
    return this;
  }

  public GeoworksTServiziBuilder addTipoFunz(String tipoFunz) {
    this.tipoFunz = tipoFunz;
    return this;
  }

  public GeoworksTServizi build() {
    GeoworksTServizi obj = new GeoworksTServizi();
    obj.setIdServizio(this.idServizio);
    obj.setIdFunz(this.idFunz);
    obj.setDescrizioneServizio(this.descrizioneServizio);
    obj.setTipoFunz(this.tipoFunz);
    return obj;
  }
}
