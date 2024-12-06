package it.liguriadigitale.ponmetro.api.pojo.contatti.builder;

import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni.TipoFunzEnum;
import java.math.BigDecimal;

public class GeoworksFunzioniBuilder {

  private BigDecimal idFunz;
  private TipoFunzEnum tipoFunz;
  private String descrizioneFunz;

  public GeoworksFunzioniBuilder setIdFunz(BigDecimal idFunz) {
    this.idFunz = idFunz;
    return this;
  }

  public GeoworksFunzioniBuilder setTipoFunz(TipoFunzEnum tipoFunz) {
    this.tipoFunz = tipoFunz;
    return this;
  }

  public GeoworksFunzioniBuilder setDescrizioneFunz(String descrizioneFunz) {
    this.descrizioneFunz = descrizioneFunz;
    return this;
  }

  public GeoworksFunzioni build() {
    GeoworksFunzioni toRet = new GeoworksFunzioni();

    toRet.setIdFunz(idFunz);
    toRet.setTipoFunz(tipoFunz);
    toRet.setDescrizioneFunz(descrizioneFunz);

    return toRet;
  }
}
