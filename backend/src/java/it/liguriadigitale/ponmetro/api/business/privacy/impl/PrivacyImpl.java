package it.liguriadigitale.ponmetro.api.business.privacy.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.api.business.common.datasource.PrivacyBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.privacy.service.PrivacyInterface;
import it.liguriadigitale.ponmetro.api.integration.dao.PrAnagraficaDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.PrPrivacyPresaVisioneDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.PrPrivacyVerDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.PrServiziDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.query.GetDocumentDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.query.GetMaxVersionePrivacyDAO;
import it.liguriadigitale.ponmetro.api.pojo.common.EsitoResponse;
import it.liguriadigitale.ponmetro.api.pojo.common.builder.EsitoResponseBuilder;
import it.liguriadigitale.ponmetro.api.pojo.privacy.db.PrAnagrafica;
import it.liguriadigitale.ponmetro.api.pojo.privacy.db.PrPrivacyPresaVisione;
import it.liguriadigitale.ponmetro.api.pojo.privacy.db.PrPrivacyVer;
import it.liguriadigitale.ponmetro.api.pojo.privacy.db.PrServizi;
import it.liguriadigitale.ponmetro.api.pojo.privacy.db.builder.PrPrivacyPresaVisioneBuilder;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.PrivacyRequest;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.PrivacyVersioneRequest;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.builder.PrivacyVersioneRequestBuilder;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyDocResponse;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyResponse;
import java.time.LocalDateTime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrivacyImpl implements PrivacyInterface {

  private static Log log = LogFactory.getLog(PrivacyImpl.class);

  @Override
  public PrivacyResponse verificaLetturaPrivacyCorrente(PrivacyRequest request) {
    PrivacyResponse response = new PrivacyResponse();
    PrivacyVersioneRequest requestVersione = new PrivacyVersioneRequest();
    try {
      requestVersione = cercaUltimaVersionePrivacy(request);
      response.setIdVersionePrivacy(requestVersione.getIdVersionePrivacy());
    } catch (BusinessException e) {
      response.setEsito(impostaEsito(e, "Nessuna informativa associata al servizio"));
      return response;
    }
    try {
      PrAnagrafica anagrafica = (PrAnagrafica) existAnagrafica(request);
      if (anagrafica == null) {
        PrAnagrafica pranagrafica = creaAnagrafica(request.getIdAnonimo());
        inserisciAnagrafica(pranagrafica);
      }
    } catch (BusinessException e) {
      log.error("Errore durante la ricerca anagrafica: ", e);
    }
    try {
      PrPrivacyPresaVisione presaVisione =
          (PrPrivacyPresaVisione) verificaPresaVisione(requestVersione);
      if (presaVisione == null)
        response.setEsito(impostaEsito("L'utente non ha preso visione dell'informativa"));
      else {
        response.setEsito(new EsitoResponseBuilder().setEsito(true).build());
      }
    } catch (BusinessException e) {
      response.setEsito(impostaEsito(e, "Impossibile verificare presa visione"));
    }
    return response;
  }

  @Override
  public PrivacyDocResponse getDocumentoPrivacy(PrivacyVersioneRequest request) {

    log.debug("[PrivacyImpl] input:" + request);
    PrivacyDocResponse response = new PrivacyDocResponse();
    if (request.getIdVersionePrivacy() == null) {
      try {
        request = cercaUltimaVersionePrivacy(request);
      } catch (BusinessException e) {
        response.setEsito(impostaEsito(e, "Nessuna informativa associata al servizio"));
        return response;
      }
    }
    try {
      GetDocumentDAO dao = new GetDocumentDAO(request);
      PrivacyBusinessHelper helper = new PrivacyBusinessHelper(dao);
      PrPrivacyVer privacy = (PrPrivacyVer) helper.cercaOggetto();
      log.debug("privacy: " + privacy);
      if (privacy != null && privacy.getPdfFile() != null) {
        response.setIdVersionePrivacy(privacy.getIdPrivacyVer());
        response.setEsito(new EsitoResponseBuilder().setEsito(true).build());
        response.setPdfFile(privacy.getPdfFile());
        return response;
      } else {
        response.setEsito(impostaEsito("Nessun pdf trovato"));
        return response;
      }
    } catch (BusinessException e) {
      response.setEsito(impostaEsito(e, "Errore durante la ricerca documento"));
      return response;
    }
  }

  @Override
  public EsitoResponse presaVisionePrivacy(PrivacyVersioneRequest request) {

    EsitoResponse response = new EsitoResponseBuilder().setEsito(true).build();
    try {
      PrPrivacyVer prPrivacyVer = existVersionePrivacy(request);
      if (null == prPrivacyVer) return impostaEsito("Versione dell'informativa non trovata");
    } catch (BusinessException e) {
      response = impostaEsito(e, "Impossibile verificare versione informativa");
      return response;
    }
    try {
      PrAnagrafica anagrafica = (PrAnagrafica) existAnagrafica(request);
      if (anagrafica == null) {
        return impostaEsito("Anagrafica non trovata");
      }
    } catch (BusinessException e) {
      response = impostaEsito(e, "Impossibile verificare anagrafica");
      return response;
    }
    PrServizi servizio = new PrServizi();
    try {
      servizio = existCodiceApplicazione(request);
      if (null == servizio) return impostaEsito("Servizio non trovato");
    } catch (BusinessException e) {
      response = impostaEsito(e, "Impossibile verificare codice applicazione");
      return response;
    }

    PrPrivacyPresaVisione presaVisione =
        new PrPrivacyPresaVisioneBuilder()
            .setDataAgg(LocalDateTime.now())
            .setDataIns(LocalDateTime.now())
            .setDataPresaVisione(LocalDateTime.now())
            .setIdAnagrafica(request.getIdAnonimo())
            .setIdPrivacyVer(request.getIdVersionePrivacy())
            .setIdServizio(servizio.getIdServizio())
            .setIdStatoRec(1L)
            .setUtenteAgg(BaseServiceImpl.COD_APP)
            .setUtenteIns(BaseServiceImpl.COD_APP)
            .build();
    try {
      inserisciPresaVisione(presaVisione);
    } catch (BusinessException e) {
      response = impostaEsito(e, "Impossibile inserire presa visione sulla bd.");
      return response;
    }

    return response;
  }

  private void inserisciPresaVisione(PrPrivacyPresaVisione presaVisione) throws BusinessException {
    PrPrivacyPresaVisioneDAO dao = new PrPrivacyPresaVisioneDAO(presaVisione);
    PrivacyBusinessHelper helper = new PrivacyBusinessHelper(dao);
    helper.inserisciOggetto();
  }

  private PrServizi existCodiceApplicazione(PrivacyVersioneRequest request)
      throws BusinessException {

    PrServizi prServizi = new PrServizi();
    prServizi.setCodServizio(request.getCodApplicazione());
    PrServiziDAO dao = new PrServiziDAO(prServizi);
    PrivacyBusinessHelper helper = new PrivacyBusinessHelper(dao);
    return (PrServizi) helper.cercaOggetto();
  }

  private PrPrivacyVer existVersionePrivacy(PrivacyVersioneRequest request)
      throws BusinessException {

    PrPrivacyVer prPrivacyVer = new PrPrivacyVer();
    prPrivacyVer.setIdPrivacyVer(request.getIdVersionePrivacy());
    PrPrivacyVerDAO dao = new PrPrivacyVerDAO(prPrivacyVer);
    PrivacyBusinessHelper helper = new PrivacyBusinessHelper(dao);
    return (PrPrivacyVer) helper.cercaOggetto();
  }

  private PrivacyVersioneRequest cercaUltimaVersionePrivacy(PrivacyRequest request)
      throws BusinessException {

    GetMaxVersionePrivacyDAO dao = new GetMaxVersionePrivacyDAO(request);
    PrivacyBusinessHelper helper = new PrivacyBusinessHelper(dao);
    Long maxIdPrivacy = (Long) helper.cercaOggetto();
    if (maxIdPrivacy != null && maxIdPrivacy > 0) {
      return new PrivacyVersioneRequestBuilder()
          .setCodApplicazione(request.getCodApplicazione())
          .setIdAnonimo(request.getIdAnonimo())
          .setIdVersionePrivacy(maxIdPrivacy)
          .build();
    } else {
      log.error(
          "[PrivacyImpl] Nessuna informativa associata al servizio: "
              + request.getCodApplicazione());
      throw new BusinessException(
          "Nessun risultato per il servizio: " + request.getCodApplicazione());
    }
  }

  private Object verificaPresaVisione(PrivacyVersioneRequest requestVersione)
      throws BusinessException {

    PrPrivacyPresaVisione prprivacypresavisione = new PrPrivacyPresaVisione();
    prprivacypresavisione.setIdPrivacyVer(requestVersione.getIdVersionePrivacy());
    prprivacypresavisione.setIdAnagrafica(requestVersione.getIdAnonimo());
    PrPrivacyPresaVisioneDAO dao = new PrPrivacyPresaVisioneDAO(prprivacypresavisione);
    PrivacyBusinessHelper helper = new PrivacyBusinessHelper(dao);
    return helper.cercaOggetto();
  }

  private PrAnagrafica creaAnagrafica(Long idAnonimo) {

    PrAnagrafica pranagrafica = new PrAnagrafica();
    pranagrafica.setIdAnagrafica(idAnonimo);
    pranagrafica.setDataIns(LocalDateTime.now());
    pranagrafica.setIdStatoRec(0L);
    pranagrafica.setUtenteIns(BaseServiceImpl.COD_APP);
    pranagrafica.setDataAgg(LocalDateTime.now());
    pranagrafica.setUtenteAgg(BaseServiceImpl.COD_APP);
    return pranagrafica;
  }

  private Object existAnagrafica(PrivacyRequest request) throws BusinessException {

    PrAnagrafica pranagrafica = new PrAnagrafica();
    pranagrafica.setIdAnagrafica(request.getIdAnonimo());
    PrAnagraficaDAO dao = new PrAnagraficaDAO(pranagrafica);
    PrivacyBusinessHelper helper = new PrivacyBusinessHelper(dao);
    return helper.cercaOggetto();
  }

  private void inserisciAnagrafica(PrAnagrafica pranagrafica) throws BusinessException {

    PrAnagraficaDAO dao = new PrAnagraficaDAO(pranagrafica);
    PrivacyBusinessHelper helper = new PrivacyBusinessHelper(dao);
    helper.inserisciOggetto();
  }

  private EsitoResponse impostaEsito(Exception e, String messaggio) {
    log.error("[PrivacyImpl]" + messaggio + ":", e);
    return new EsitoResponseBuilder()
        .setEsito(false)
        .setDescrizione(messaggio)
        .setEccezione(e.getLocalizedMessage())
        .build();
  }

  private EsitoResponse impostaEsito(String messaggio) {
    log.debug("[PrivacyImpl] " + messaggio);
    return new EsitoResponseBuilder().setEsito(false).setDescrizione(messaggio).build();
  }
}
