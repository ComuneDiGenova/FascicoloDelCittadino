package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyResponse;
import it.liguriadigitale.ponmetro.api.presentation.rest.privacy.service.PrivacyRestInterface;
import javax.ws.rs.core.Response;

public class ApiServiziConfigurazionePrivacyImpl implements PrivacyRestInterface {

  private PrivacyRestInterface instance;

  public ApiServiziConfigurazionePrivacyImpl(PrivacyRestInterface instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Response getDocumentoPrivacy(String arg0, Long arg1, Long arg2) {
    return instance.getDocumentoPrivacy(arg0, arg1, arg2);
  }

  @Override
  public PrivacyResponse verificaLetturaPrivacyCorrente(String arg0, Long arg1) {
    return instance.verificaLetturaPrivacyCorrente(arg0, arg1);
  }

  @Override
  public PrivacyResponse presaVisionePrivacy(String arg0, Long arg1, Long arg2) {
    return instance.presaVisionePrivacy(arg0, arg1, arg2);
  }
}
