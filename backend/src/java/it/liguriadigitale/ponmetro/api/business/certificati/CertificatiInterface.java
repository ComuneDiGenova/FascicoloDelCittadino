package it.liguriadigitale.ponmetro.api.business.certificati;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.certificati.VCertificatiTipi;
import java.util.List;

public interface CertificatiInterface {

  public String getCertificabilita(String codiceCertificato, String categoriaCertificato)
      throws BusinessException;

  public List<VCertificatiTipi> getCertificati() throws BusinessException;
}
