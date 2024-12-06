package it.liguriadigitale.ponmetro.portale.business.configurazioneAvatar.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.configurazione.model.ImagineCaricata;
import it.liguriadigitale.ponmetro.portale.business.configurazioneAvatar.service.ConfigurazioneAvatarService;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.WebApplicationException;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ConfigurazioneAvatarImpl implements ConfigurazioneAvatarService {

  @Override
  public ImagineCaricata getImagineCaricata(Utente utente)
      throws BusinessException, ApiException, IOException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionInGetImagineCaricata(instance, utente);
  }

  private ImagineCaricata closeConnectionInGetImagineCaricata(
      ServiceLocatorLivelloUno instance, Utente utente) {
    try {
      Long idFcitt = utente.getIdAnonimoComuneGenova();
      return instance
          .getApiConfigurazioneAvatar()
          .configurazioneAvatarIdAnagraficaGet(BigDecimal.valueOf(idFcitt));
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneAvatar"));
    } finally {
      instance.closeConnection();
    }
  }
}
