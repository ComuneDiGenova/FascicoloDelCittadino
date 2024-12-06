package it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi.builder;

import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni.TipoFunzEnum;
import java.math.BigDecimal;

public class BravFunzioniBuilder {

  private BigDecimal idFunz;
  private TipoFunzEnum tipoFunz;
  private String descrizioneFunz;

  public BravFunzioniBuilder setIdFunz(BigDecimal idFunz) {
    this.idFunz = idFunz;
    return this;
  }

  public BravFunzioniBuilder setTipoFunz(TipoFunzEnum tipoFunz) {
    this.tipoFunz = tipoFunz;
    return this;
  }

  public BravFunzioniBuilder setDescrizioneFunz(String descrizioneFunz) {
    this.descrizioneFunz = descrizioneFunz;
    return this;
  }

  public BravFunzioni build() {
    BravFunzioni toRet = new BravFunzioni();

    toRet.setIdFunz(idFunz);
    toRet.setTipoFunz(tipoFunz);
    toRet.setDescrizioneFunz(descrizioneFunz);

    return toRet;
  }
}
