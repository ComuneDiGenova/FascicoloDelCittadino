package it.liguriadigitale.ponmetro.portale.business.domandealloggio.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.bandirealgim.model.BandoAttivoFullDOMValidationResult;
import it.liguriadigitale.ponmetro.bandirealgim.model.BandoAttivoFullDOMValidationResultElementDOM;
import it.liguriadigitale.ponmetro.bandirealgim.model.BandoDomandaCategoriaFullDOM;
import it.liguriadigitale.ponmetro.bandirealgim.model.BooleanValidationResult;
import it.liguriadigitale.ponmetro.bandirealgim.model.V1BandoDomandeStatusDOM;
import it.liguriadigitale.ponmetro.bandirealgim.model.V1BandoDomandeViewFullDOM;
import it.liguriadigitale.ponmetro.portale.business.domandealloggio.service.ServiziDomandeAlloggioService;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoScaiRealGimm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import javax.ws.rs.ServiceUnavailableException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziDomandeAlloggioImpl implements ServiziDomandeAlloggioService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_DOMANDE_ALLOGGIO =
      "Errore di connessione alle API Domande alloggio";

  @Override
  public BooleanValidationResult checkAnnouncementStatus(String codBando)
      throws BusinessException, ApiException, IOException {
    log.debug("ServiziDomandeAlloggioImpl -- checkAnnouncementStatus");

    try {

      BooleanValidationResult checkAnnouncementStatus =
          ServiceLocatorLivelloUnoScaiRealGimm.getInstance()
              .getApiAnnouncement()
              .apiBandiAnnouncementCheckAnnouncementStatusGet("BAN4");

      log.debug("CP checkAnnouncementStatus = " + checkAnnouncementStatus);

      return checkAnnouncementStatus;
    } catch (BusinessException e) {
      log.error("ServiziDomandeAlloggioImpl -- checkAnnouncementStatus: errore API bandi:", e);
      throw new BusinessException(ERRORE_API_DOMANDE_ALLOGGIO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- checkAnnouncementStatus: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- checkAnnouncementStatus: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Bandi Alloggio"));
    }
  }

  @Override
  public BandoAttivoFullDOMValidationResult getActiveAnnouncement(
      OffsetDateTime data, String codTipologiaBando)
      throws BusinessException, ApiException, IOException {
    log.debug("ServiziDomandeAlloggioImpl -- getActiveAnnouncement");

    try {

      BandoAttivoFullDOMValidationResult getActiveAnnouncement =
          ServiceLocatorLivelloUnoScaiRealGimm.getInstance()
              .getApiAnnouncement()
              .apiBandiAnnouncementGetActiveAnnouncementGet(data, codTipologiaBando);

      log.debug("CP getActiveAnnouncement = " + getActiveAnnouncement);

      return getActiveAnnouncement;
    } catch (BusinessException e) {
      log.error("ServiziDomandeAlloggioImpl -- getActiveAnnouncement: errore API bandi:", e);
      throw new BusinessException(ERRORE_API_DOMANDE_ALLOGGIO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getActiveAnnouncement: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getActiveAnnouncement: errore RuntimeException nella Response:"
              + e.getMessage());

      // throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Bandi Alloggio"));

      // TODO tapullo:
      BandoAttivoFullDOMValidationResult tapullo = new BandoAttivoFullDOMValidationResult();
      BandoAttivoFullDOMValidationResultElementDOM bandoTapullo =
          new BandoAttivoFullDOMValidationResultElementDOM();

      if (codTipologiaBando.equalsIgnoreCase("01")) {
        String bandoAlloggio = getValoreDaDb("COD_BANDO_ALLOGGIO");
        bandoTapullo.setCodBando(bandoAlloggio);
      }

      if (codTipologiaBando.equalsIgnoreCase("02")) {
        String bandoAlloggio = getValoreDaDb("COD_BANDO_LOCAZIONE");
        bandoTapullo.setCodBando(bandoAlloggio);
      }

      tapullo.setElementDOM(bandoTapullo);
      return tapullo;
      // FINE TAPULLO
    }
  }

  @Override
  public V1BandoDomandeViewFullDOM getAnnouncementQuestionDetail(
      Boolean flagExternal, String codiceFiscale, String codBando)
      throws BusinessException, ApiException, IOException {
    log.debug("ServiziDomandeAlloggioImpl -- getAnnouncementQuestionDetail");

    try {

      V1BandoDomandeViewFullDOM getAnnouncementQuestionDetail =
          ServiceLocatorLivelloUnoScaiRealGimm.getInstance()
              .getApiAnnouncementQuestions()
              .apiBandiAnnouncementQuestionsGetAnnouncementQuestionDetailGet(
                  flagExternal, codiceFiscale, codBando);

      log.debug("CP getAnnouncementQuestionDetail = " + getAnnouncementQuestionDetail);

      return getAnnouncementQuestionDetail;
    } catch (BusinessException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getAnnouncementQuestionDetail: errore API bandi:", e);
      throw new BusinessException(ERRORE_API_DOMANDE_ALLOGGIO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getAnnouncementQuestionDetail: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getAnnouncementQuestionDetail: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Bandi Alloggio"));
    }
  }

  @Override
  public V1BandoDomandeStatusDOM getAnnouncementQuestionStatus(
      Boolean flagExternal, String codiceFiscale, String codBando, String codTipologiaBando)
      throws BusinessException, ApiException, IOException {
    log.debug("ServiziDomandeAlloggioImpl -- getAnnouncementQuestionStatus");

    try {

      V1BandoDomandeStatusDOM getAnnouncementQuestionStatus =
          ServiceLocatorLivelloUnoScaiRealGimm.getInstance()
              .getApiAnnouncementQuestions()
              .apiBandiAnnouncementQuestionsGetAnnouncementQuestionStatusGet(
                  flagExternal, codiceFiscale, codBando, codTipologiaBando);

      log.debug("CP getAnnouncementQuestionStatus = " + getAnnouncementQuestionStatus);

      return getAnnouncementQuestionStatus;
    } catch (BusinessException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getAnnouncementQuestionStatus: errore API bandi:", e);
      throw new BusinessException(ERRORE_API_DOMANDE_ALLOGGIO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getAnnouncementQuestionStatus: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getAnnouncementQuestionStatus: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Bandi Alloggio"));
    }
  }

  @Override
  public List<BandoDomandaCategoriaFullDOM> getListAnnouncementQuestionCategory(Integer anno)
      throws BusinessException, ApiException, IOException {
    log.debug("ServiziDomandeAlloggioImpl -- getListAnnouncementQuestionCategory");

    try {

      List<BandoDomandaCategoriaFullDOM> getListAnnouncementQuestionCategory =
          ServiceLocatorLivelloUnoScaiRealGimm.getInstance()
              .getApiAnnouncementQuestions()
              .apiBandiAnnouncementQuestionsGetListAnnouncementQuestionCategoryAnnoGet(anno);

      log.debug("CP getListAnnouncementQuestionCategory = " + getListAnnouncementQuestionCategory);

      return getListAnnouncementQuestionCategory;
    } catch (BusinessException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getListAnnouncementQuestionCategory: errore API bandi:",
          e);
      throw new BusinessException(ERRORE_API_DOMANDE_ALLOGGIO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getListAnnouncementQuestionCategory: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getListAnnouncementQuestionCategory: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Bandi Alloggio"));
    }
  }

  @Override
  public String getValoreDaDb(String chiave) {
    String data = "";
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);
      if (LabelFdCUtil.checkIfNotNull(valore)) {
        data = valore.getValore();
      }
    } catch (BusinessException e) {
      log.error("Errore durante get in bandi alloggio da DB = " + e.getMessage(), e);
    } finally {
      instance.closeConnection();
    }

    return data;
  }

  @Override
  public byte[] getDownloadDocumentAnnouncementQuestion(String codiceFiscale, String idBandoDomande)
      throws BusinessException, ApiException, IOException {
    log.debug("ServiziDomandeAlloggioImpl -- getDownloadDocumentAnnouncementQuestion");

    try {

      return ServiceLocatorLivelloUnoScaiRealGimm.getInstance()
          .getApiAnnouncementQuestions()
          .apiBandiAnnouncementQuestionsDownloadDocumentAnnouncementQuestionGet(
              codiceFiscale, idBandoDomande);

    } catch (BusinessException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getDownloadDocumentAnnouncementQuestion: errore API bandi:",
          e);
      throw new BusinessException(ERRORE_API_DOMANDE_ALLOGGIO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getDownloadDocumentAnnouncementQuestion: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziDomandeAlloggioImpl -- getDownloadDocumentAnnouncementQuestion: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Bandi Alloggio"));
    }
  }
}
