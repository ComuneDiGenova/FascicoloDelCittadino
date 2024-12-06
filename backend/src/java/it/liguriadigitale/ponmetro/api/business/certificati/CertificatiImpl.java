package it.liguriadigitale.ponmetro.api.business.certificati;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.integration.dao.view.VCertificatiTipiDAO;
import it.liguriadigitale.ponmetro.api.pojo.certificati.VCertificatiTipi;
import java.util.List;

public class CertificatiImpl implements CertificatiInterface {

  @Override
  public String getCertificabilita(String codiceCertificato, String categoriaCertificato)
      throws BusinessException {

    VCertificatiTipi certificati = new VCertificatiTipi();
    certificati.setCodiceAnpr(codiceCertificato);
    certificati.setTipo(categoriaCertificato);
    VCertificatiTipiDAO dao = new VCertificatiTipiDAO(certificati);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    VCertificatiTipi risultato = (VCertificatiTipi) helper.cercaOggetto();
    return risultato.getInvio();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<VCertificatiTipi> getCertificati() throws BusinessException {

    VCertificatiTipi certificati = new VCertificatiTipi();
    VCertificatiTipiDAO dao = new VCertificatiTipiDAO(certificati);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<VCertificatiTipi> lista = helper.cercaOggetti();
    return lista;
  }
}
