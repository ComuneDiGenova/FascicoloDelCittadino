package it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi.builder;

import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravPermessi;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravPermessi.TipoFunzEnum;
import java.math.BigDecimal;

public class BravPermessiBuilder {

  private BigDecimal idPermesso;
  private BigDecimal idFunz;
  private String descrizionePermesso;
  private String tipoFunz;

  public BravPermessiBuilder setIdPermesso(BigDecimal idPermesso) {
    this.idPermesso = idPermesso;
    return this;
  }

  public BravPermessiBuilder setIdFunz(BigDecimal idFunz) {
    this.idFunz = idFunz;
    return this;
  }

  public BravPermessiBuilder setDescrizionePermesso(String descrizionePermesso) {
    this.descrizionePermesso = descrizionePermesso;
    return this;
  }

  public BravPermessiBuilder setTipoFunz(String tipoFunz) {
    this.tipoFunz = tipoFunz;
    return this;
  }

  public BravPermessi build() {
    BravPermessi toRet = new BravPermessi();

    toRet.setIdPermesso(idPermesso);
    toRet.setIdFunz(idFunz);
    toRet.setDescrizioneServizio(descrizionePermesso);
    toRet.setTipoFunz(TipoFunzEnum.valueOf(tipoFunz));

    return toRet;
  }
}
