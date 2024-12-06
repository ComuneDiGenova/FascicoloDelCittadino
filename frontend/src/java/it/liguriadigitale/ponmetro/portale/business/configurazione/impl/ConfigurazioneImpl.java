package it.liguriadigitale.ponmetro.portale.business.configurazione.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.anonim.model.AnonimoData;
import it.liguriadigitale.ponmetro.anonim.model.IdCPVPerson;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.CitizenResponse;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.Relative;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ListaFunzioni;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyResponse;
import it.liguriadigitale.ponmetro.configurazione.model.ImagineCaricata;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiFunzione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiGestioneNotificaComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiGestioneVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiNotificaComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.EnumEntitaConfigFiltroBaseGenerico;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.EnumFiltroFlag;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.configurazione.service.ConfigurazioneInterface;
import it.liguriadigitale.ponmetro.portale.business.demografico.impl.DemograficoImpl;
import it.liguriadigitale.ponmetro.portale.business.demografico.service.DemograficoInterface;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.portale.AggiungiFiglio;
import it.liguriadigitale.ponmetro.portale.pojo.portale.MinoreConvivente;
import it.liguriadigitale.ponmetro.portale.pojo.portale.configurazione.WidgetSelezionati;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ConfigurazioneImpl implements ConfigurazioneInterface {

  private static Log log = LogFactory.getLog(ConfigurazioneImpl.class);

  @Override
  public byte[] getInformativa(Utente utente) throws BusinessException {
    log.debug("getInformativa: " + utente);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    Response response =
        instance
            .getApiPrivacy()
            .getDocumentoPrivacy(
                BaseServiceImpl.COD_APP,
                utente.getIdAnonimoComuneGenova(),
                utente.getUltimaVersioneInformativaPrivacy());
    InputStream myinputstream = response.readEntity(InputStream.class);
    try {
      return IOUtils.toByteArray(myinputstream);
    } catch (IOException e) {
      log.error("Impossibile recuperare PDF: ", e);
      throw new BusinessException("Impossibile recuperare PDF");
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public PrivacyResponse presaVisione(Utente utente) throws BusinessException {
    log.debug("utente.getIdAnonimoComuneGenova()=" + utente.getIdAnonimoComuneGenova());
    log.debug(
        "utente.getUltimaVersioneInformativaPrivacy()="
            + utente.getUltimaVersioneInformativaPrivacy());
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    return closeConnectionPresaVisione(instance, utente);
  }

  private PrivacyResponse closeConnectionPresaVisione(
      ServiceLocatorLivelloUno instance, Utente utente) {
    try {
      PrivacyResponse response =
          instance
              .getApiPrivacy()
              .presaVisionePrivacy(
                  BaseServiceImpl.COD_APP,
                  utente.getIdAnonimoComuneGenova(),
                  utente.getUltimaVersioneInformativaPrivacy());
      log.debug("response=" + response);
      if (response.getEsito() != null) {
        return response;
      } else {
        log.error("Errore esito null: " + response);
        throw new BusinessException("Errore");
      }
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public MinoreConvivente updateMinoreConviventePerDichiazioneGenitore(
      MinoreConvivente minore, Utente utente) throws BusinessException {

    log.debug("BEGIN updateMinoreConviventePerDichiazioneGenitore: minore=" + minore);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    return closeConnectionInUpdateMinoreConviventePerDichiazioneGenitore(instance, minore, utente);
  }

  @Override
  public MinoreConvivente cancellaMinoreConviventePerDichiazioneGenitore(
      MinoreConvivente minore, Utente utente) throws BusinessException {

    log.debug("BEGIN cancellaMinoreConviventePerDichiazioneGenitore: minore=" + minore);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    return closeConnectionInDeleteMinoreConviventePerDichiazioneGenitore(instance, minore, utente);
  }

  private MinoreConvivente closeConnectionInDeleteMinoreConviventePerDichiazioneGenitore(
      ServiceLocatorLivelloUno instance, MinoreConvivente minore, Utente utente)
      throws BusinessException {

    try {
      Long idPersonMinore = minore.getIdPerson();
      Long idPersonGenitore = utente.getIdAnonimoComuneGenova();

      log.error(
          "ID minore = "
              + idPersonMinore
              + "ID genitore = "
              + idPersonGenitore
              + "*************************");

      CitizenResponse response = null;
      if (utente.isResidente()) {
        response =
            instance
                .getApiFamily()
                .cancellazioneLogicaMinoreResidente(idPersonMinore, idPersonGenitore);
      } else {
        response = instance.getApiFamily().cancellazioneLogica(idPersonMinore, idPersonGenitore);
      }

      if (response != null && response.getEsito().isEsito()) return minore;
      else throw new BusinessException("Errore durante la cancellazione");
    } catch (BusinessException e) {
      throw new BusinessException("Errore durante la cancellazione dell'autodichiarazione.");
    } catch (WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {
      instance.closeConnection();
    }
  }

  private MinoreConvivente closeConnectionInUpdateMinoreConviventePerDichiazioneGenitore(
      ServiceLocatorLivelloUno instance, MinoreConvivente minore, Utente utente)
      throws BusinessException {
    try {
      Long idPersonMinore = minore.getIdPerson();
      Long idPersonGenitore = utente.getIdAnonimoComuneGenova();

      if ((minore.getIdPerson() == null) || (minore.getIdPerson() == 0)) {

        IdCPVPerson person =
            instance.getApiPerson().cpvPersonCodiceFiscaleGet(minore.getCodiceFiscale());
        idPersonMinore = person.getPersonId().longValue();
        minore.setIdPerson(idPersonMinore);
      }

      CitizenResponse response =
          instance
              .getApiFamily()
              .salvaFamigliare(
                  idPersonMinore, idPersonGenitore, minore.getTipoParentela().name(), true);

      log.debug("END minore=" + minore);

      if (response.getEsito().isEsito()) {
        return minore;
      } else {
        throw new BusinessException("Errore durante il salvataggio");
      }
    } catch (BusinessException e) {
      throw new BusinessException("Errore durante il salvataggio dell'autodichiarazione.");
    } catch (WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public AggiungiFiglio updateMinoreConviventePerDichiarazioneGenitoreNonResidente(
      AggiungiFiglio minore, Utente utente) throws BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionUpdateMinoreConviventePerDichiarazioneGenitoreNonResidente(
        instance, minore, utente);
  }

  private AggiungiFiglio closeConnectionUpdateMinoreConviventePerDichiarazioneGenitoreNonResidente(
      ServiceLocatorLivelloUno instance, AggiungiFiglio minore, Utente utente) {

    try {
      log.debug(
          "BEGIN updateMinoreConviventePerDichiarazioneGenitoreNonResidente: minore="
              + minore
              + " - genitore = "
              + utente.getCodiceFiscaleOperatore()
              + " - residente = "
              + utente.isResidente());

      Long idPersonMinore = minore.getIdPerson();
      Long idPersonGenitore = utente.getIdAnonimoComuneGenova();

      log.debug("idPersonMinore = " + idPersonMinore + " - idPersonGenitore = " + idPersonGenitore);
      if ((minore.getIdPerson() == null) || (minore.getIdPerson() == 0)) {
        AnonimoData anonimoData = new AnonimoData();
        anonimoData.setCodiceFiscale(minore.getCodiceFiscale());
        anonimoData.setCognome(minore.getCognome());
        anonimoData.setNome(minore.getNome());

        minore.setDataNascita(
            CodiceFiscaleValidatorUtil.getDataNascitaFromCfDichiarazioneMinore(
                minore.getCodiceFiscale()));
        anonimoData.setDataNascita(minore.getDataNascita());

        anonimoData.setSesso(CodiceFiscaleValidatorUtil.getSessoFromCf(minore.getCodiceFiscale()));
        anonimoData.setCodiceBelfiore(
            CodiceFiscaleValidatorUtil.getCodiceBelFioreFromCf(minore.getCodiceFiscale()));

        log.debug("CP anonimoData = " + anonimoData);

        IdCPVPerson person = instance.getApiAnonym().anonimizzaPost(anonimoData);
        if (person.getIsCittadinoComuneGe()) {
          log.error(
              "ATTENZIONE: tentativo di registrare un bambino residente come non residente. Utente collegato: "
                  + utente.getCodiceFiscaleOperatore()
                  + " bambino: "
                  + person);
          throw new BusinessException("Non e' possibile completare l'operazione");
        }
        idPersonMinore = person.getPersonId().longValue();
        log.debug("idPersonMinore dopo anonimizza post = " + idPersonMinore);
        minore.setIdPerson(idPersonMinore);

        utente.resettaListaNucleoFamiliareAllargato();
      }

      CitizenResponse response =
          instance
              .getApiFamily()
              .inserisciFamigliare(
                  idPersonMinore, idPersonGenitore, minore.getTipoParentela().name(), true);

      log.debug("END updateMinoreConviventePerDichiarazioneGenitoreNonResidente minore=" + minore);

      if (response.getEsito().isEsito()) {
        return minore;
      } else {
        throw new BusinessException("Errore durante il salvataggio");
      }

    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("MipGestionaliVerticali"));

    } finally {

      instance.closeConnection();
    }
  }

  @Override
  public AggiungiFiglio insertMinoreConviventePerDichiarazioneGenitoreNonResidente(
      AggiungiFiglio minore, Utente utente) throws BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionInsertMinoreConviventePerDichiarazioneGenitoreNonResidente(
        instance, minore, utente);
  }

  private AggiungiFiglio closeConnectionInsertMinoreConviventePerDichiarazioneGenitoreNonResidente(
      ServiceLocatorLivelloUno instance, AggiungiFiglio minore, Utente utente) {

    try {
      log.debug(
          "BEGIN closeConnectionInsertMinoreConviventePerDichiarazioneGenitoreNonResidente: minore="
              + minore
              + " - genitore = "
              + utente.getCodiceFiscaleOperatore()
              + " - residente = "
              + utente.isResidente());

      Long idPersonMinore = minore.getIdPerson();
      Long idPersonGenitore = utente.getIdAnonimoComuneGenova();

      log.debug("idPersonMinore = " + idPersonMinore + " - idPersonGenitore = " + idPersonGenitore);
      if ((minore.getIdPerson() == null) || (minore.getIdPerson() == 0)) {
        AnonimoData anonimoData = new AnonimoData();
        anonimoData.setCodiceFiscale(minore.getCodiceFiscale());
        anonimoData.setCognome(minore.getCognome());
        anonimoData.setNome(minore.getNome());

        minore.setDataNascita(
            CodiceFiscaleValidatorUtil.getDataNascitaFromCfDichiarazioneMinore(
                minore.getCodiceFiscale()));
        anonimoData.setDataNascita(minore.getDataNascita());

        anonimoData.setSesso(CodiceFiscaleValidatorUtil.getSessoFromCf(minore.getCodiceFiscale()));
        anonimoData.setCodiceBelfiore(
            CodiceFiscaleValidatorUtil.getCodiceBelFioreFromCf(minore.getCodiceFiscale()));

        log.debug("CP anonimoData = " + anonimoData);

        IdCPVPerson person = instance.getApiAnonym().anonimizzaPost(anonimoData);
        if (person.getIsCittadinoComuneGe()) {
          log.error(
              "ATTENZIONE: tentativo di registrare un bambino residente come non residente. Utente collegato: "
                  + utente.getCodiceFiscaleOperatore()
                  + " bambino: "
                  + person);
          throw new BusinessException("Non e' possibile completare l'operazione");
        }

        idPersonMinore = person.getPersonId().longValue();
        log.debug("idPersonMinore dopo anonimizza post = " + idPersonMinore);
        minore.setIdPerson(idPersonMinore);

        utente.resettaListaNucleoFamiliareAllargato();
      }

      log.debug(
          "START insertMinoreConviventePerDichiarazioneGenitoreNonResidente minore=" + minore);

      CitizenResponse response =
          instance
              .getApiFamily()
              .inserisciFamigliare(
                  idPersonMinore, idPersonGenitore, minore.getTipoParentela().name(), true);

      log.debug("END insertMinoreConviventePerDichiarazioneGenitoreNonResidente minore=" + minore);

      if (response.getEsito().isEsito()) {
        return minore;
      } else {
        throw new BusinessException("Errore durante il salvataggio");
      }

    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("MipGestionaliVerticali"));

    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public void updateWidget(Utente utente, WidgetSelezionati sezione) throws BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    closeConnectionUpdateWidget(instance, utente, sezione);
  }

  private void closeConnectionUpdateWidget(
      ServiceLocatorLivelloUno instance, Utente utente, WidgetSelezionati sezione) {
    try {
      List<DatiGestioneVisualizzazioneSezioneWidget> listaWidget = convertiInListaWidget(sezione);
      instance
          .createEntitaConfigurazioneUtenteApi()
          .sezioneWidgetGestisciIdSezioneIdAnagraficaPut(
              sezione.getSezione().getIdSezione(),
              utente.getIdAnonimoComuneGenova().toString(),
              listaWidget);

    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("MipGestionaliVerticali"));
    } finally {
      instance.closeConnection();
    }
  }

  private List<DatiGestioneVisualizzazioneSezioneWidget> convertiInListaWidget(
      WidgetSelezionati sezione) {

    DatiGestioneVisualizzazioneSezioneWidget dati1 = new DatiGestioneVisualizzazioneSezioneWidget();
    DatiGestioneVisualizzazioneSezioneWidget dati2 = new DatiGestioneVisualizzazioneSezioneWidget();
    DatiGestioneVisualizzazioneSezioneWidget dati3 = new DatiGestioneVisualizzazioneSezioneWidget();
    DatiGestioneVisualizzazioneSezioneWidget dati4 = new DatiGestioneVisualizzazioneSezioneWidget();
    dati1.setFlagAssocia(true);
    dati2.setFlagAssocia(true);
    dati3.setFlagAssocia(false);
    dati4.setFlagAssocia(false);

    // FRR FASCITT-550 null pointer in salvataggio in caso di non residente
    String idWidgetDati1 = null;
    String idWidgetDati2 = null;
    String idWidgetDati3 = null;
    String idWidgetDati4 = null;
    if (sezione != null) {
      if (sezione.getWidgetTop() != null && sezione.getWidgetTop().getWidget() != null) {
        idWidgetDati1 = sezione.getWidgetTop().getWidget().getIdWidget();
      }
      if (sezione.getWidgetBottom() != null && sezione.getWidgetBottom().getWidget() != null) {
        idWidgetDati2 = sezione.getWidgetBottom().getWidget().getIdWidget();
      }
      if (sezione.getWidgetTopStatoIniziale() != null
          && sezione.getWidgetTopStatoIniziale().getWidget() != null) {
        idWidgetDati3 = sezione.getWidgetTopStatoIniziale().getWidget().getIdWidget();
      }
      if (sezione.getWidgetBottomStatoIniziale() != null
          && sezione.getWidgetBottomStatoIniziale().getWidget() != null) {
        idWidgetDati4 = sezione.getWidgetBottomStatoIniziale().getWidget().getIdWidget();
      }
    }
    dati1.setIdWidget(idWidgetDati1);
    dati2.setIdWidget(idWidgetDati2);
    dati3.setIdWidget(idWidgetDati3);
    dati4.setIdWidget(idWidgetDati4);
    // FRR End
    List<DatiGestioneVisualizzazioneSezioneWidget> listaWidget = new ArrayList<>();
    listaWidget.add(dati3);
    listaWidget.add(dati4);
    listaWidget.add(dati1);
    listaWidget.add(dati2);
    return listaWidget;
  }

  @Override
  public List<DatiVisualizzazioneSezioneWidget> getListaWidgetHomePage(Utente utente)
      throws BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionGetListaWidgetHomePage(instance, utente);
  }

  private List<DatiVisualizzazioneSezioneWidget> closeConnectionGetListaWidgetHomePage(
      ServiceLocatorLivelloUno instance, Utente utente) {
    try {
      String segnapostoIsUtenteDelegato = "-";
      if (utente.isUtenteDelegato()) {
        segnapostoIsUtenteDelegato = "-9999";
      }
      log.debug("segnapostoIsUtenteDelegato=" + segnapostoIsUtenteDelegato);
      return instance
          .createEntitaConfigurazioneUtenteApi()
          .sezioneWidgetDaVisualizzareIdSezioneIdAnagraficaIsResidenteGet(
              segnapostoIsUtenteDelegato,
              utente.getIdAnonimoComuneGenova().toString(),
              utente.isResidente());
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<DatiVisualizzazioneSezioneWidget> getListaWidgetPerSezione(
      Utente utente, DatiSezione sezione) throws BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionGetListaWidgetPerSezione(instance, utente, sezione);
  }

  private List<DatiVisualizzazioneSezioneWidget> closeConnectionGetListaWidgetPerSezione(
      ServiceLocatorLivelloUno instance, Utente utente, DatiSezione sezione) {
    try {
      return instance
          .createEntitaConfigurazioneUtenteApi()
          .sezioneWidgetGestisciIdSezioneIdAnagraficaGet(
              sezione.getIdSezione(), utente.getIdAnonimoComuneGenova().toString());
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<DatiNotificaComparto> getListaTutteNotificheComparti(Utente utente)
      throws BusinessException, ApiException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionGetListaTutteNotificheComparti(instance, utente);
  }

  private List<DatiNotificaComparto> closeConnectionGetListaTutteNotificheComparti(
      ServiceLocatorLivelloUno instance, Utente utente) {
    try {

      log.debug("inizio getListaTutteNotificheComparti ");
      Long idFcitt = getIdFCittByUtente(utente);
      log.debug(" getListaTutteNotificheComparti idFcitt : " + idFcitt);
      return instance
          .getApiEntitaConfigurazioneUtente()
          .notificheCompartiIdSezioneIdCompartoIdAnagraficaGet(
              EnumEntitaConfigFiltroBaseGenerico.MINUS.toString(),
              EnumEntitaConfigFiltroBaseGenerico.MINUS.toString(),
              String.valueOf(idFcitt));

    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {
      instance.closeConnection();
    }
  }

  private Long getIdFCittByUtente(Utente utente) {

    if (utente == null) {
      log.debug("utente null");
      return 0L;
    }
    Long idFcitt = utente.getIdAnonimoComuneGenova();
    if (idFcitt == null) {
      log.debug("idFcitt null");
      idFcitt = 0L;
    }
    return idFcitt;
  }

  @Override
  public void updateNotifica(DatiNotificaComparto dati, Utente utente, Boolean isSelezionato)
      throws BusinessException, ApiException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    closeConnectionUpdateNotifica(instance, dati, utente, isSelezionato);
  }

  private void closeConnectionUpdateNotifica(
      ServiceLocatorLivelloUno instance,
      DatiNotificaComparto dati,
      Utente utente,
      Boolean isSelezionato) {
    try {
      List<DatiGestioneNotificaComparto> lista = new ArrayList<>();
      DatiGestioneNotificaComparto notifica = convertiDatiNotifica(dati, isSelezionato);
      lista.add(notifica);
      instance
          .getApiEntitaConfigurazioneUtente()
          .notificheCompartiIdSezioneIdCompartoIdAnagraficaPut(
              dati.getSezione().getIdSezione(),
              dati.getComparto().getIdComparto(),
              utente.getIdAnonimoComuneGenova().toString(),
              lista);

    } catch (BusinessException | WebApplicationException e) {
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public void updateNotificheConBtnSalva(
      Utente utente, Map<DatiNotificaComparto, Boolean> hashMapNotifiche)
      throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    closeConnectionUpdateNotificheConBtnSalva(instance, utente, hashMapNotifiche);
  }

  private void closeConnectionUpdateNotificheConBtnSalva(
      ServiceLocatorLivelloUno instance,
      Utente utente,
      Map<DatiNotificaComparto, Boolean> hashMapNotifiche) {
    try {
      List<DatiGestioneNotificaComparto> lista = new ArrayList<DatiGestioneNotificaComparto>();
      for (Map.Entry<DatiNotificaComparto, Boolean> elem : hashMapNotifiche.entrySet()) {

        DatiGestioneNotificaComparto notifica =
            convertiDatiNotifica(elem.getKey(), elem.getValue());

        lista.add(notifica);
        instance
            .getApiEntitaConfigurazioneUtente()
            .notificheCompartiIdSezioneIdCompartoIdAnagraficaPut(
                elem.getKey().getSezione().getIdSezione(),
                elem.getKey().getComparto().getIdComparto(),
                utente.getIdAnonimoComuneGenova().toString(),
                lista);
      }

    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {
      instance.closeConnection();
    }
  }

  private DatiGestioneNotificaComparto convertiDatiNotifica(
      DatiNotificaComparto dati, Boolean isSelezionato) {

    DatiGestioneNotificaComparto datiGestioneNotificaComparto = new DatiGestioneNotificaComparto();
    datiGestioneNotificaComparto.setFlagRegistra(isSelezionato);
    datiGestioneNotificaComparto.setIdComparto(dati.getComparto().getIdComparto());
    return datiGestioneNotificaComparto;
  }

  @Override
  public List<DatiVisualizzazioneSezioneWidget> getListaWidgetPerConfigurazione(Utente utente)
      throws BusinessException {

    log.debug(" -getListaWidgetPerConfigurazione: BEGIN");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionInGetListaWidgetPerConfigurazione(instance, utente);
  }

  private List<DatiVisualizzazioneSezioneWidget> closeConnectionInGetListaWidgetPerConfigurazione(
      ServiceLocatorLivelloUno instance, Utente utente) {
    try {
      return instance
          .getApiEntitaConfigurazioneUtente()
          .sezioneWidgetDaConfigurareIdSezioneIdAnagraficaFiltroFlagIsResidenteGet(
              EnumFiltroFlag.MINUS.toString(),
              utente.getIdAnonimoComuneGenova().toString(),
              EnumFiltroFlag.SENZA_FILTRO,
              utente.isResidente());
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<DatiCompletiFunzione> getListaFunzioni(Utente utente) throws BusinessException {
    log.debug(" -getListaFunzioni: BEGIN");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    return closeConnectionInGetListaFunzioni(instance, utente);
  }

  private List<DatiCompletiFunzione> closeConnectionInGetListaFunzioni(
      ServiceLocatorLivelloUno instance, Utente utente) {
    try {
      return instance
          .getApiEntitaConfigurazioneUtente()
          .funzioniCompartiSezioniFiltroFlagGet(EnumFiltroFlag.SENZA_FILTRO);
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public byte[] getImagineCaricata(Utente utente)
      throws BusinessException, ApiException, IOException {
    log.debug("bj - getImagineCaricata");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    return closeConnectionInGetImagineCaricata(instance, utente);
  }

  private byte[] closeConnectionInGetImagineCaricata(
      ServiceLocatorLivelloUno instance, Utente utente) {
    try {
      Long idFcitt = utente.getIdAnonimoComuneGenova();
      ImagineCaricata file =
          instance
              .getApiConfigurazioneAvatar()
              .configurazioneAvatarIdAnagraficaGet(BigDecimal.valueOf(idFcitt));

      return DatatypeConverter.parseBase64Binary(file.getFileImage());
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public ImagineCaricata uploadAvatar(Utente utente, File avatarFile) throws BusinessException {
    log.debug("bj - uploadAvatar");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionInUploadAvatar(instance, utente, avatarFile);
  }

  private ImagineCaricata closeConnectionInUploadAvatar(
      ServiceLocatorLivelloUno instance, Utente utente, File avatarFile) {
    try {
      Long idFcitt = utente.getIdAnonimoComuneGenova();
      ImagineCaricata file =
          instance
              .getApiConfigurazioneAvatar()
              .configurazioneAvatarIdAnagraficaPost(BigDecimal.valueOf(idFcitt), avatarFile);
      return file;
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public byte[] getInformativaSebina(Utente utente) throws BusinessException {
    log.debug("getInformativaSebina: " + utente);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    Response response =
        instance
            .getApiPrivacy()
            .getDocumentoPrivacy(
                BaseServiceImpl.COD_SEBINA,
                utente.getIdAnonimoComuneGenova(),
                utente.getUltimaVersioneInformativaPrivacySebina());
    InputStream myinputstream = response.readEntity(InputStream.class);
    try {
      return IOUtils.toByteArray(myinputstream);
    } catch (IOException e) {
      log.error("Impossibile recuperare PDF Sebina: ", e);
      throw new BusinessException("Impossibile recuperare PDF Sebina");
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public PrivacyResponse presaVisioneSebina(Utente utente) throws BusinessException {
    log.debug("anonimizzaSebina:");
    log.debug("utente.getIdAnonimoComuneGenova()=" + utente.getIdAnonimoComuneGenova());
    log.debug(
        "utente.getUltimaVersioneInformativaPrivacySebina()="
            + utente.getUltimaVersioneInformativaPrivacySebina());
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionPresaVisioneSebina(instance, utente);
  }

  private PrivacyResponse closeConnectionPresaVisioneSebina(
      ServiceLocatorLivelloUno instance, Utente utente) {
    try {
      PrivacyResponse response =
          instance
              .getApiPrivacy()
              .presaVisionePrivacy(
                  BaseServiceImpl.COD_SEBINA,
                  utente.getIdAnonimoComuneGenova(),
                  utente.getUltimaVersioneInformativaPrivacySebina());
      log.debug("response= " + response);
      if (response.getEsito() != null) {
        return response;
      } else {
        log.error("Errore esito null: " + response);
        throw new BusinessException("Errore");
      }
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {

      instance.closeConnection();
    }
  }

  @Override
  public byte[] getInformativaServiziCortesia(Utente utente) throws BusinessException {
    log.debug("getInformativaServiziCortesia: " + utente);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    Response response =
        instance
            .getApiPrivacy()
            .getDocumentoPrivacy(
                BaseServiceImpl.COD_SERVIZI_DI_CORTESIA,
                utente.getIdAnonimoComuneGenova(),
                utente.getUltimaVersioneInformativaPrivacyServiziCortesia());
    InputStream myinputstream = response.readEntity(InputStream.class);
    try {
      return IOUtils.toByteArray(myinputstream);
    } catch (IOException e) {
      log.error("Impossibile recuperare PDF Privacy Servizi cortesia: ", e);
      throw new BusinessException("Impossibile recuperare PDF Servizi cortesia");
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public PrivacyResponse presaVisioneServiziCortesia(Utente utente) throws BusinessException {
    log.debug("presaVisioneServiziCortesia:");
    log.debug("utente.getIdAnonimoComuneGenova()=" + utente.getIdAnonimoComuneGenova());
    log.debug(
        "utente.getUltimaVersioneInformativaPrivacyServiziCortesia()="
            + utente.getUltimaVersioneInformativaPrivacyServiziCortesia());
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionPresaVisioneServiziCortesia(instance, utente);
  }

  private PrivacyResponse closeConnectionPresaVisioneServiziCortesia(
      ServiceLocatorLivelloUno instance, Utente utente) {

    try {
      PrivacyResponse response =
          instance
              .getApiPrivacy()
              .presaVisionePrivacy(
                  BaseServiceImpl.COD_SERVIZI_DI_CORTESIA,
                  utente.getIdAnonimoComuneGenova(),
                  utente.getUltimaVersioneInformativaPrivacyServiziCortesia());
      log.debug("response= " + response);
      if (response.getEsito() != null) {
        return response;
      } else {
        log.error("Errore esito null: " + response);
        throw new BusinessException("Errore");
      }
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ConfigurazioneImpl"));
    } finally {

      instance.closeConnection();
    }
  }

  @Override
  public byte[] getInformativaServiziTeleriscaldamento(Utente utente) throws BusinessException {
    log.debug("getInformativaServiziTeleriscaldamento: " + utente);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    Response response =
        instance
            .getApiPrivacy()
            .getDocumentoPrivacy(
                BaseServiceImpl.COD_TELERISCALDAMENTO, utente.getIdAnonimoComuneGenova(), -1L);
    InputStream myinputstream = response.readEntity(InputStream.class);
    try {
      return IOUtils.toByteArray(myinputstream);
    } catch (IOException e) {
      log.error("Impossibile recuperare PDF Privacy Teleriscaldamento IREN: ", e);
      throw new BusinessException("Impossibile recuperare PDF Teleriscaldamento IREN");
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @SuppressWarnings("unused")
  private List<ComponenteNucleo> getMinoriAutodichiarazioneNonResidente(Utente capoFamiglia)
      throws BusinessException {

    if (capoFamiglia.isResidente()) {
      throw new BusinessException("L'utente loggato e' residente");
    }
    List<ComponenteNucleo> listaNucleoTemp = new ArrayList<>();

    List<ComponenteNucleo> listaNucleo =
        creaNucleoFamiliareAllargatoDaAutodichiarazione(capoFamiglia, listaNucleoTemp);
    return null;
  }

  private List<ComponenteNucleo> creaNucleoFamiliareAllargatoDaAutodichiarazione(
      Utente capoFamiglia, List<ComponenteNucleo> listaNucleoTemp) throws BusinessException {

    DemograficoInterface service = new DemograficoImpl();
    @SuppressWarnings("unused")
    List<Relative> listaAutodichiarazione = service.getMinoriDaAutodichiarazione(capoFamiglia);

    /* TODO da implementare con nuovo metodo su YAML */

    // return null;
    throw new NotImplementedException();
  }

  @Override
  public List<FunzioniDisponibili> getFunzioniAbilitate() throws BusinessException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ListaFunzioni listaFunzioni = instance.getApiHomePage().getListaFunzioni();

      return listaFunzioni.getListaFunzioni();
    } catch (BusinessException e) {
      log.error("Errore getFunzioniAbilitate: " + e.getMessage(), e);
      throw new BusinessException("Errore");
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public byte[] getInformativaServiziCedole(Utente utente) throws BusinessException {
    log.debug("getInformativaServiziCedole: " + utente);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    Response response =
        instance
            .getApiPrivacy()
            .getDocumentoPrivacy(
                BaseServiceImpl.COD_CEDOLE,
                utente.getIdAnonimoComuneGenova(),
                utente.getUltimaVersioneInformativaPrivacyServiziCedole());
    InputStream myinputstream = response.readEntity(InputStream.class);
    try {
      return IOUtils.toByteArray(myinputstream);
    } catch (IOException e) {
      log.error("Impossibile recuperare PDF Privacy Servizi cedole: ", e);
      throw new BusinessException("Impossibile recuperare PDF Servizi cedole");
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public PrivacyResponse presaVisioneServiziCedole(Utente utente) throws BusinessException {
    log.debug("presaVisioneServiziCedole:");
    log.debug("utente.getIdAnonimoComuneGenova()=" + utente.getIdAnonimoComuneGenova());
    log.debug(
        "utente.getUltimaVersioneInformativaPrivacyServiziCedole()="
            + utente.getUltimaVersioneInformativaPrivacyServiziCedole());
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionPresaVisioneServiziCedole(instance, utente);
  }

  private PrivacyResponse closeConnectionPresaVisioneServiziCedole(
      ServiceLocatorLivelloUno instance, Utente utente) {

    try {
      PrivacyResponse response =
          instance
              .getApiPrivacy()
              .presaVisionePrivacy(
                  BaseServiceImpl.COD_CEDOLE,
                  utente.getIdAnonimoComuneGenova(),
                  utente.getUltimaVersioneInformativaPrivacyServiziCedole());
      log.debug("response= " + response);
      if (response.getEsito() != null) {
        return response;
      } else {
        log.error("Errore esito null: " + response);
        throw new BusinessException("Errore");
      }
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Configurazione privacy cedole"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<ContattiUtente> getContattiUtente(Utente utente)
      throws BusinessException, ApiException, IOException {
    log.debug("getContattiUtente: ");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      Long idFcitt = utente.getIdAnonimoComuneGenova();

      List<ContattiUtente> contatti = instance.getApiContattiUtente().getContatti(idFcitt);
      return contatti;

    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("salva contatti utente"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public void upInsertContattiUtente(
      Utente utente, String tipo, ContattiUtente contattiUtenteDaAggiornare)
      throws BusinessException, ApiException, IOException {
    log.debug("upInsertContattiUtente: ");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      Long idFcitt = utente.getIdAnonimoComuneGenova();

      instance.getApiContattiUtente().upsertContatti(idFcitt, tipo, contattiUtenteDaAggiornare);

    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("salva contatti utente"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public int getGiorniPerAggiornareContatti() {
    log.debug("CP getGiorniPerAggiornareContatti");

    String chiave = "SALVA_CONTATTI";
    String numeroGiorniSuDb = "";
    int numeroGiorni = 0;

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);
      if (LabelFdCUtil.checkIfNotNull(valore)) {
        numeroGiorniSuDb = valore.getValore();
        numeroGiorni = Integer.parseInt(numeroGiorniSuDb);
      }
    } catch (BusinessException e) {
      log.error("Errore durante getGiorniPerAggiornareContatti da DB = " + e.getMessage(), e);
    } finally {
      instance.closeConnection();
    }

    log.debug("CP numero giorni = " + numeroGiorni);

    return numeroGiorni;
  }

  @Override
  public void cancellaContattiUtente(Utente utente, String tipo)
      throws BusinessException, ApiException, IOException {
    log.debug("cancellaContattiUtente: ");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      Long idFcitt = utente.getIdAnonimoComuneGenova();

      instance.getApiContattiUtente().deleteContatti(idFcitt, tipo);

    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("salva contatti utente"));
    } finally {
      instance.closeConnection();
    }
  }

  // BRAV

  @Override
  public byte[] getInformativaServiziBrav(Utente utente) throws BusinessException {
    log.debug(
        "getInformativaServiziBrav: " + utente.getUltimaVersioneInformativaPrivacyServiziBrav());
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    Response response =
        instance
            .getApiPrivacy()
            .getDocumentoPrivacy(
                BaseServiceImpl.COD_BRAV,
                utente.getIdAnonimoComuneGenova(),
                utente.getUltimaVersioneInformativaPrivacyServiziBrav());
    InputStream myinputstream = response.readEntity(InputStream.class);
    try {
      return IOUtils.toByteArray(myinputstream);
    } catch (IOException e) {
      log.error("Impossibile recuperare PDF Privacy Servizi Brav: ", e);
      throw new BusinessException("Impossibile recuperare PDF Servizi BRAV");
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public PrivacyResponse presaVisioneServiziBrav(Utente utente) throws BusinessException {
    log.debug("presaVisioneServiziBrav:");
    log.debug("utente.getIdAnonimoComuneGenova()=" + utente.getIdAnonimoComuneGenova());
    log.debug(
        "utente.getUltimaVersioneInformativaPrivacyServiziBrav()="
            + utente.getUltimaVersioneInformativaPrivacyServiziCedole());
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionPresaVisioneServiziBrav(instance, utente);
  }

  private PrivacyResponse closeConnectionPresaVisioneServiziBrav(
      ServiceLocatorLivelloUno instance, Utente utente) {

    try {
      PrivacyResponse response =
          instance
              .getApiPrivacy()
              .presaVisionePrivacy(
                  BaseServiceImpl.COD_BRAV,
                  utente.getIdAnonimoComuneGenova(),
                  utente.getUltimaVersioneInformativaPrivacyServiziBrav());
      log.debug("response= " + response);
      if (response.getEsito() != null) {
        return response;
      } else {
        log.error("Errore esito null: " + response);
        throw new BusinessException("Errore");
      }
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Configurazione privacy genova parcheggi"));
    } finally {
      instance.closeConnection();
    }
  }

  // Canone Idrico

  @Override
  public byte[] getInformativaServiziCanoneIdrico(Utente utente) throws BusinessException {
    log.debug(
        "getInformativaServiziCanoneIdrico: "
            + utente.getUltimaVersioneInformativaPrivacyServiziCanoneIdrico());
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    Response response =
        instance
            .getApiPrivacy()
            .getDocumentoPrivacy(
                BaseServiceImpl.COD_CANONEIDRICO,
                utente.getIdAnonimoComuneGenova(),
                utente.getUltimaVersioneInformativaPrivacyServiziCanoneIdrico());
    InputStream myinputstream = response.readEntity(InputStream.class);
    try {
      return IOUtils.toByteArray(myinputstream);
    } catch (IOException e) {
      log.error("Impossibile recuperare PDF Privacy Servizi Canone Idrico: ", e);
      throw new BusinessException("Impossibile recuperare PDF Servizi Canone Idrico");
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public PrivacyResponse presaVisioneServiziCanoneIdrico(Utente utente) throws BusinessException {
    log.debug("presaVisioneServiziCanoneIdrico:");
    log.debug("utente.getIdAnonimoComuneGenova()=" + utente.getIdAnonimoComuneGenova());
    log.debug(
        "utente.getUltimaVersioneInformativaPrivacyServiziCanoneIdrico()="
            + utente.getUltimaVersioneInformativaPrivacyServiziCanoneIdrico());
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionPresaVisioneServiziCanoneIdrico(instance, utente);
  }

  private PrivacyResponse closeConnectionPresaVisioneServiziCanoneIdrico(
      ServiceLocatorLivelloUno instance, Utente utente) {

    try {
      PrivacyResponse response =
          instance
              .getApiPrivacy()
              .presaVisionePrivacy(
                  BaseServiceImpl.COD_CANONEIDRICO,
                  utente.getIdAnonimoComuneGenova(),
                  utente.getUltimaVersioneInformativaPrivacyServiziCanoneIdrico());
      log.debug("response= " + response);
      if (response.getEsito() != null) {
        return response;
      } else {
        log.error("Errore esito null: " + response);
        throw new BusinessException("Errore");
      }
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Configurazione privacy canone idrico"));
    } finally {
      instance.closeConnection();
    }
  }
}
