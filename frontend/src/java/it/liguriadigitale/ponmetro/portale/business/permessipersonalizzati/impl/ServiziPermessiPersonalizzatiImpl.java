package it.liguriadigitale.ponmetro.portale.business.permessipersonalizzati.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Allegabile;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.AllegatoBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DichiarazioniResponse;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaCompletaResponse;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaResponse;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.IdDomandaProtocolloBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.PostResult;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Protocollazione;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.StatoProcedimento;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.TipologiaProcedimento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.permessipersonalizzati.service.ServiziPermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoMipGlobali;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziPermessiPersonalizzatiImpl implements ServiziPermessiPersonalizzati {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_PERMESSI_PERSONALIZZATI =
      "Errore di connessione alle API Permessi Personalizzati";

  @Override
  public PostResult putDomanda(DomandaBody domandaBody, Boolean isResidente)
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobali.getInstance()
          .getApiDomanda()
          .domandaResidenzaPost(isResidente, domandaBody);

    } catch (BusinessException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- domandaResidenzaPost: errore API permessi personalizzati:",
          e);
      throw new BusinessException(ERRORE_API_PERMESSI_PERSONALIZZATI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- domandaResidenzaPost: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- domandaResidenzaPost: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Permessi Personalizzati"));
    }
  }

  @Override
  public Protocollazione putDomanda(int idDomanda, IdDomandaProtocolloBody idDomandaProtocolloBody)
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobali.getInstance()
          .getApiDomanda()
          .domandaIdDomandaProtocolloPost(idDomanda, idDomandaProtocolloBody);

    } catch (BusinessException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- domandaIdDomandaProtocolloPost: errore API permessi personalizzati:",
          e);
      throw new BusinessException(ERRORE_API_PERMESSI_PERSONALIZZATI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- domandaIdDomandaProtocolloPost: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- domandaIdDomandaProtocolloPost: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Permessi Personalizzati"));
    }
  }

  @Override
  public DomandaCompletaResponse getDomanda(int idDomanda)
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobali.getInstance()
          .getApiDomanda()
          .domandaIdDomandaGet(idDomanda);

    } catch (BusinessException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getDomanda: errore API permessi personalizzati:",
          e);
      throw new BusinessException(ERRORE_API_PERMESSI_PERSONALIZZATI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getDomanda: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getDomanda: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Permessi Personalizzati"));
    }
  }

  @Override
  public List<DomandaResponse> getDomande(String codiceFiscale)
      throws BusinessException, ApiException, IOException {

    try {
      return ServiceLocatorLivelloUnoMipGlobali.getInstance()
          .getApiDomanda()
          .domandeSoggettoGet(codiceFiscale);

    } catch (BusinessException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getDomande: errore API permessi personalizzati:",
          e);
      throw new BusinessException(ERRORE_API_PERMESSI_PERSONALIZZATI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getDomande: errore WebApplicationException:"
              + e.getMessage());
      return new ArrayList<>();
    }
  }

  @Override
  public AllegatoBody getDomanda(int idDomanda, String nomeFile)
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobali.getInstance()
          .getApiDomanda()
          .domandaIdDomandaAllegatoNomeFileGet(idDomanda, nomeFile);

    } catch (BusinessException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getDomanda: errore API permessi personalizzati:",
          e);
      throw new BusinessException(ERRORE_API_PERMESSI_PERSONALIZZATI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getDomanda: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getDomanda: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Permessi Personalizzati"));
    }
  }

  @Override
  public List<Allegabile> getAllegabili() throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobali.getInstance().getApiUtils().allegabiliGet();

    } catch (BusinessException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getAllegabili: errore API permessi personalizzati:",
          e);
      throw new BusinessException(ERRORE_API_PERMESSI_PERSONALIZZATI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getAllegabili: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getAllegabili: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Permessi Personalizzati"));
    }
  }

  @Override
  public List<Allegabile> getAllegabili(String codice, Boolean isResidente)
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobali.getInstance()
          .getApiUtils()
          .allegabiliCodiceResidenzaGet(codice, isResidente);

    } catch (BusinessException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getAllegabili: errore API permessi personalizzati:",
          e);
      throw new BusinessException(ERRORE_API_PERMESSI_PERSONALIZZATI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getAllegabili: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getAllegabili: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Permessi Personalizzati"));
    }
  }

  @Override
  public List<TipologiaProcedimento> getTipologieProcedimento()
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobali.getInstance()
          .getApiUtils()
          .tipologieProcedimentoGet();

    } catch (BusinessException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getTipologieProcedimento: errore API permessi personalizzati:",
          e);
      throw new BusinessException(ERRORE_API_PERMESSI_PERSONALIZZATI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getTipologieProcedimento: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getTipologieProcedimento: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Permessi Personalizzati"));
    }
  }

  @Override
  public List<DichiarazioniResponse> getDichiarazioni()
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobali.getInstance().getApiUtils().dichiarazioniGet();

    } catch (BusinessException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getTipologieProcedimento: errore API permessi personalizzati:",
          e);
      throw new BusinessException(ERRORE_API_PERMESSI_PERSONALIZZATI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getTipologieProcedimento: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getTipologieProcedimento: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Permessi Personalizzati"));
    }
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggi()
      throws BusinessException, ApiException, IOException {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();
    MessaggiInformativi messaggio = new MessaggiInformativi();

    messaggio.setMessaggio(
        "In questa sezione, le persone disabili aventi deambulazione impedita o sensibilmente ridotta, possono richiedere un parcheggio riservato, in prossimit&agrave; della propria abitazione o del luogo di lavoro.\r\n"
            + "Il Regolamento al Codice della Strada afferma all'articolo 381 che: nei casi in cui ricorrono particolari condizioni di invalidit&agrave; della persona interessata, il Comune pu&ograve;, con propria ordinanza, assegnare a titolo gratuito un adeguato spazio di sosta individuato da apposita segnaletica indicante gli estremi del \"contrassegno di parcheggio per disabili\" del soggetto autorizzato ad usufruirne.\r\n"
            + "Per maggiori informazioni consultare la <a href=\"https://smart.comune.genova.it/node/12349\" target=\"_blank\">pagina informativa</a>");

    messaggio.setType("info");
    listaMessaggi.add(messaggio);
    return listaMessaggi;
  }

  @Override
  public List<Legenda> getListaLegenda() {
    List<Legenda> listaLegenda = new ArrayList<>();

    Legenda legenda1 = new Legenda();
    legenda1.setTesto("Rigettata");
    legenda1.setStile("badge badge-danger");
    listaLegenda.add(legenda1);

    Legenda legenda2 = new Legenda();
    legenda2.setTesto("In esame sospeso");
    legenda2.setStile("badge badge-warning");
    listaLegenda.add(legenda2);

    Legenda legenda3 = new Legenda();
    legenda3.setTesto("Completa, protocollata, accolta");
    legenda3.setStile("badge badge-success");
    listaLegenda.add(legenda3);

    return listaLegenda;
  }

  @Override
  public List<StatoProcedimento> getStatiProcedimento()
      throws BusinessException, ApiException, IOException {
    try {
      return ServiceLocatorLivelloUnoMipGlobali.getInstance().getApiUtils().statiProcedimentoGet();

    } catch (BusinessException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getStatiProcedimento: errore API permessi personalizzati:",
          e);
      throw new BusinessException(ERRORE_API_PERMESSI_PERSONALIZZATI);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getStatiProcedimento: errore nella Response:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziPermessiPersonalizzatiImpl -- getStatiProcedimento: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Permessi Personalizzati"));
    }
  }
}
