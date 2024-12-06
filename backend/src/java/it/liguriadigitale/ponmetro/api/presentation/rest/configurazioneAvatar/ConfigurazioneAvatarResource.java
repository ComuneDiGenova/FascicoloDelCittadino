package it.liguriadigitale.ponmetro.api.presentation.rest.configurazioneAvatar;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.BadRequestException;
import it.liguriadigitale.ponmetro.configurazione.apiclient.ConfigAvatarApi;
import it.liguriadigitale.ponmetro.configurazione.model.ImagineCaricata;
import java.io.File;
import java.math.BigDecimal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigurazioneAvatarResource implements ConfigAvatarApi {

  private static Log log = LogFactory.getLog(ConfigurazioneAvatarResource.class);

  @Override
  public ImagineCaricata configurazioneAvatarIdAnagraficaGet(BigDecimal idAnagrafica) {
    try {
      log.info("Metodo rest configurazioneAvatarIdAnagraficaGet" + "id vale:" + idAnagrafica);
      return ServiceLocator.getInstance().getConfigurazioneUtenteFdC().getAvatar(idAnagrafica);

    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    }
  }

  @Override
  public ImagineCaricata configurazioneAvatarIdAnagraficaPost(
      BigDecimal idAnagrafica, File imageAvatar) {
    try {
      log.info(
          "Metodo rest configurazioneAvatarIdAnagraficaPost"
              + "id vale:"
              + idAnagrafica
              + " image"
              + imageAvatar);

      return ServiceLocator.getInstance()
          .getConfigurazioneUtenteFdC()
          .uploadAvatar(idAnagrafica, imageAvatar);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    }
  }
}
