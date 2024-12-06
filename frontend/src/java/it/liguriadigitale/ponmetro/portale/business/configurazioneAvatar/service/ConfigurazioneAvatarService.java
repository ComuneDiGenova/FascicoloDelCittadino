package it.liguriadigitale.ponmetro.portale.business.configurazioneAvatar.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.configurazione.model.ImagineCaricata;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import java.io.IOException;

public interface ConfigurazioneAvatarService {
  ImagineCaricata getImagineCaricata(Utente utente)
      throws BusinessException, ApiException, IOException;
}
