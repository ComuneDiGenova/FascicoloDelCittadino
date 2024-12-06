package it.liguriadigitale.ponmetro.api.presentation.rest.certificati;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.certificati.CertificatiResponse;
import it.liguriadigitale.ponmetro.api.pojo.common.EsitoResponse;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.certificati.service.CertificatiRestInterface;
import java.util.List;

public class CertificatiRestResource implements CertificatiRestInterface {

  @Override
  public CertificatiResponse getListaCertificati() {

    CertificatiResponse response = new CertificatiResponse();
    try {
      EsitoResponse esito = new EsitoResponse();
      esito.setEsito(true);
      response.setEsito(esito);
      List lista = ServiceLocator.getInstance().getCertificati().getCertificati();
      response.setListaCertificati(lista);
    } catch (BusinessException e) {
      EsitoResponse esito = new EsitoResponse();
      esito.setEsito(false);
      response.setEsito(esito);
    }

    return response;
  }
}
