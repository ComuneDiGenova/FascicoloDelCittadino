package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.configurazione.apiclient.ConfigAvatarApi;
import it.liguriadigitale.ponmetro.configurazione.model.ImagineCaricata;
import java.io.File;
import java.math.BigDecimal;

public class ApiConfigurazioneAvatarImpl implements ConfigAvatarApi {

  private ConfigAvatarApi instance;

  public ApiConfigurazioneAvatarImpl(ConfigAvatarApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public ImagineCaricata configurazioneAvatarIdAnagraficaGet(BigDecimal arg0) {
    return instance.configurazioneAvatarIdAnagraficaGet(arg0);
  }

  @Override
  public ImagineCaricata configurazioneAvatarIdAnagraficaPost(BigDecimal idAnagrafica, File file) {
    return instance.configurazioneAvatarIdAnagraficaPost(idAnagrafica, file);
  }
}
