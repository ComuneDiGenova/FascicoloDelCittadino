package it.liguriadigitale.ponmetro.portale.business.messaggi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.messaggi.utente.model.BodyCambiaStato;
import it.liguriadigitale.ponmetro.messaggi.utente.model.DatiMessaggioUtente;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumAzione;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumFiltroTipologia;
import it.liguriadigitale.ponmetro.messaggi.utente.model.NumeroMessaggiUtente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.messaggi.service.ServiziMessaggiService;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziMessaggiImpl implements ServiziMessaggiService {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public List<DatiMessaggioUtente> getListaTuttiMessaggi(Utente utente)
      throws BusinessException, ApiException {
    log.debug("inizio getListaTuttiMessaggi ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return gestionCloseConnection(instance, utente);
  }

  private List<DatiMessaggioUtente> gestionCloseConnection(
      ServiceLocatorLivelloUno instance, Utente utente) {
    Long idFcitt = getIdFCittByUtente(utente);
    EnumFiltroTipologia tutti = EnumFiltroTipologia.TUTTI;
    Long idComparto = null;
    try {
      return instance.getApiMessaggiUtente().messaggiUtenteListaGet(idFcitt, tutti, idComparto);
    } catch (BusinessException | WebApplicationException e) {
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("MESSAGGI"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Long getNumeroMessaggiDaLeggere(Utente utente) throws BusinessException, ApiException {
    log.debug("inizio getNumeroMessaggiDaLeggere ");
    Long idFcitt = getIdFCittByUtente(utente);

    EnumFiltroTipologia daLeggere = EnumFiltroTipologia.DA_LEGGERE;
    Long idComparto = null;
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      NumeroMessaggiUtente numeroMessaggiUtente =
          instance.getApiMessaggiUtente().messaggiUtenteNumeroGet(idFcitt, daLeggere, idComparto);

      return numeroMessaggiUtente.getNumeroMessaggi();
    } catch (WebApplicationException we) {
      log.debug(" getNumeroMessaggiDaLeggere Errore : " + we.getMessage());
      return 0L;
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public void setAzioneMessaggio(Utente utente, DatiMessaggioUtente messaggio, EnumAzione azione)
      throws BusinessException, ApiException {
    if (messaggio == null) {
      throw new BusinessException("setAzioneMessaggio: messaggio null");
    }
    List<DatiMessaggioUtente> messaggi = new ArrayList<DatiMessaggioUtente>();
    messaggi.add(messaggio);
    setAzioneMessaggi(utente, messaggi, azione);
  }

  @Override
  public void setAzioneMessaggi(
      Utente utente, List<DatiMessaggioUtente> messaggi, EnumAzione azione)
      throws BusinessException, ApiException {
    log.debug("inizio setAzioneMessaggio ");
    Long idFcitt = getIdFCittByUtente(utente);
    if (messaggi == null || messaggi.isEmpty()) {
      throw new BusinessException("setAzioneMessaggio: lista vuota");
    }
    List<Long> listaIdMessaggi =
        messaggi.stream()
            .filter(ithitem -> ithitem != null)
            .map(ithitem -> ithitem.getIdFcittNotifiche())
            .collect(Collectors.toList());
    if (listaIdMessaggi == null || listaIdMessaggi.isEmpty()) {
      throw new BusinessException("setAzioneMessaggio: listaIdMessaggi vuota");
    }
    BodyCambiaStato bodyCambiaStato = new BodyCambiaStato();
    bodyCambiaStato.setUtenteAgg(idFcitt.toString());
    bodyCambiaStato.setListaMessaggi(listaIdMessaggi);
    bodyCambiaStato.setAzione(azione);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      instance.getApiMessaggiUtente().messaggiUtenteCambiaStatoPut(idFcitt, bodyCambiaStato);

    } catch (BusinessException e) {
      log.error("[ServiziInpsImpl] setAzioneMessaggi -- errore:", e);
      throw new BusinessException("Errore durante la chiamata alla API: " + e.getMessage());
    } catch (WebApplicationException e) {
      log.error("[ServiziInpsImpl] setAzioneMessaggi -- errore API:", e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  private Long getIdFCittByUtente(Utente utente) /* throws BusinessException */ {
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
  public List<MessaggiInformativi> getMessaggiInformativi(String classeWicket)
      throws BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      List<MessaggiInformativi> lista =
          instance.getApiHomePage().getMessaggiInformativi(classeWicket);
      if (lista != null) {
        log.debug("lista= " + lista.size());
        return lista;
      } else {
        return new ArrayList<>();
      }
    } catch (BusinessException e) {
      log.error("Errore: ", e);
      return null;
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
