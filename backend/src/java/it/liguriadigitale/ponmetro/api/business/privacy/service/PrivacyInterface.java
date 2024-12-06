package it.liguriadigitale.ponmetro.api.business.privacy.service;

import it.liguriadigitale.ponmetro.api.pojo.common.EsitoResponse;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.PrivacyRequest;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.PrivacyVersioneRequest;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyDocResponse;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyResponse;

public interface PrivacyInterface {

  public PrivacyResponse verificaLetturaPrivacyCorrente(PrivacyRequest request);

  public EsitoResponse presaVisionePrivacy(PrivacyVersioneRequest request);

  public PrivacyDocResponse getDocumentoPrivacy(PrivacyVersioneRequest request);
}
