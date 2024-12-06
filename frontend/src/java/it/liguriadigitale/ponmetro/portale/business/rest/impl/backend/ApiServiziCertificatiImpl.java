package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.api.pojo.certificati.CertificatiResponse;
import it.liguriadigitale.ponmetro.api.presentation.rest.certificati.service.CertificatiRestInterface;

public class ApiServiziCertificatiImpl implements CertificatiRestInterface {

  private CertificatiRestInterface instance;

  public ApiServiziCertificatiImpl(CertificatiRestInterface instance) {
    super();
    this.instance = instance;
  }

  @Override
  public CertificatiResponse getListaCertificati() {
    return instance.getListaCertificati();
  }
}
