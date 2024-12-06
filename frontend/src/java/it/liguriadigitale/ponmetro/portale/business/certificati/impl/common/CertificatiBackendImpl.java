package it.liguriadigitale.ponmetro.portale.business.certificati.impl.common;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.certificati.CertificatiResponse;
import it.liguriadigitale.ponmetro.api.pojo.certificati.VCertificatiTipi;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CertificatiBackendImpl {

  public enum UFFICIO {
    ANAGRAFE,
    STATO_CIVILE;
  }

  private static Log log = LogFactory.getLog(CertificatiBackendImpl.class);

  private List<VCertificatiTipi> getBackend() {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      CertificatiResponse response = instance.getApiCertificatiBackend().getListaCertificati();
      if (response.getEsito().isEsito()) {
        log.debug("lista backend:" + response.getListaCertificati().size());
        return response.getListaCertificati();
      }
    } catch (BusinessException e) {
      log.error("Errore");
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
    return null;
  }

  protected List<VCertificatiTipi> getCertificatiBackendPerUfficio(UFFICIO tipo) {
    List<VCertificatiTipi> lista = getBackend();

    String filtro = tipo.name().replace("_", " ");
    List<VCertificatiTipi> listaTipo =
        lista.stream()
            .filter(certificato -> filtro.equalsIgnoreCase(certificato.getTipo()))
            .collect(Collectors.toList());
    return listaTipo;
  }

  protected List<VCertificatiTipi> getCertificatoPerCodice(
      String idCert, List<VCertificatiTipi> lista) {
    log.debug("lista:" + lista.size());
    List<VCertificatiTipi> listaFiltrata =
        lista.stream()
            .filter(certificato -> idCert.equalsIgnoreCase(certificato.getCodiceAnpr()))
            .collect(Collectors.toList());
    log.debug("getCertificatoPerCodice '" + idCert + "' - listaFiltrata:" + listaFiltrata.size());
    return listaFiltrata;
  }

  protected List<VCertificatiTipi> getCertificatiOnLine(List<VCertificatiTipi> lista) {
    log.debug("lista:" + lista.size());
    String filtro = "ONLINE";
    List<VCertificatiTipi> listaFiltrata =
        lista.stream()
            .filter(certificato -> filtro.equalsIgnoreCase(certificato.getInvio()))
            .collect(Collectors.toList());
    log.debug("getCertificatiOnLine - listaFiltrata:" + listaFiltrata);
    return listaFiltrata;
  }

  protected List<VCertificatiTipi> getCertificatiSoloNucleoFamigliare(
      List<VCertificatiTipi> lista) {
    log.debug("lista:" + lista.size());
    String filtro = "SOLO NUCLEO";
    List<VCertificatiTipi> listaFiltrata =
        lista.stream()
            .filter(certificato -> filtro.equalsIgnoreCase(certificato.getRestrizioni()))
            .collect(Collectors.toList());
    log.debug("getCertificatiSoloNucleoFamigliare - listaFiltrata:" + listaFiltrata.size());
    return listaFiltrata;
  }

  protected List<VCertificatiTipi> getCertificatiPerTutti(List<VCertificatiTipi> lista) {
    log.debug("lista:" + lista.size());
    String filtro = "SOLO NUCLEO";
    List<VCertificatiTipi> listaFiltrata =
        lista.stream()
            .filter(certificato -> !filtro.equalsIgnoreCase(certificato.getRestrizioni()))
            .collect(Collectors.toList());
    log.debug("getCertificatiSoloNucleoFamigliare - listaFiltrata:" + listaFiltrata.size());
    return listaFiltrata;
  }
}
