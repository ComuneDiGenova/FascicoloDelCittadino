package it.liguriadigitale.ponmetro.api.pojo.contatti.builder;

import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import java.math.BigDecimal;

public class GeoworksServiziBuilder {

  private BigDecimal idServizio;
  private BigDecimal idFunz;
  private String descrizioneServizio;

  public GeoworksServiziBuilder setIdServizio(BigDecimal idServizio) {
    this.idServizio = idServizio;
    return this;
  }

  public GeoworksServiziBuilder setIdFunz(BigDecimal idFunz) {
    this.idFunz = idFunz;
    return this;
  }

  public GeoworksServiziBuilder setDescrizioneServizio(String descrizioneServizio) {
    this.descrizioneServizio = descrizioneServizio;
    return this;
  }

  public GeoworksServizi build() {
    GeoworksServizi toRet = new GeoworksServizi();

    toRet.setIdServizio(idServizio);
    toRet.setIdFunz(idFunz);
    toRet.setDescrizioneServizio(descrizioneServizio);

    return toRet;
  }
}
