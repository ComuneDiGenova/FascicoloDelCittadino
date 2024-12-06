package it.liguriadigitale.ponmetro.portale.business.segnalazionitraffico.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.segnalazionitraffico.service.ServiziSegnalazioniTrafficoService;
import it.liguriadigitale.ponmetro.portale.pojo.constants.SegnalazioniTrafficoBBComuneGenovaConstants;
import it.liguriadigitale.ponmetro.portale.pojo.segnalazionitraffico.DatiSegnalazioneTraffico;
import it.liguriadigitale.ponmetro.portale.pojo.segnalazionitraffico.builder.DatiSegnalazioneTrafficoBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.segnalazionitraffico.model.DatiSegnalazioneTrafficoItem;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziSegnalazioniTrafficoImpl implements ServiziSegnalazioniTrafficoService {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public List<DatiSegnalazioneTraffico> getListSegnalazioniTraffico()
      throws BusinessException, ApiException {
    String sC = getClass().getName();
    String sM = "getListSegnalazioniTraffico";
    String sPrefix = "[" + sC + "] " + sM;
    log.debug(sPrefix + " -- INIZIO");

    List<DatiSegnalazioneTraffico> listSegnalazioneTraffico = new ArrayList<>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      List<DatiSegnalazioneTrafficoItem> listEventi =
          instance
              .getProxyApiSegnalazioniTraffico()
              .serviziTrafficoTrafficGet(
                  SegnalazioniTrafficoBBComuneGenovaConstants.TOP_RIGHT_LATITUDE, // upperRight
                  // BB
                  // Latitude
                  SegnalazioniTrafficoBBComuneGenovaConstants.TOP_RIGHT_LONGITUDE, // upperRight
                  // BB
                  // Longitude
                  SegnalazioniTrafficoBBComuneGenovaConstants.BOTTOM_LEFT_LATITUDE, // lowerLeft
                  // BB
                  // Latitude
                  SegnalazioniTrafficoBBComuneGenovaConstants.BOTTOM_LEFT_LONGITUDE, // lowerLeft
                  // BB
                  // Longitude
                  true, // getId, boolean TRUE
                  false, // getDistance, boolean FALSE
                  0, // unit distance, used if getDistance
                  null, // point for distance Latitude, used if
                  // getDistance
                  null, // point for distance Longitude, used if
                  // getDistance
                  null, // maxResults, used if getDistance
                  null // session id "s" session e' OPZIONALE e non
                  // obbligatorio
                  );

      if (listEventi != null) {
        listEventi.sort((o1, o2) -> o2.getS().compareTo(o1.getS()));

        listSegnalazioneTraffico =
            listEventi.stream()
                .filter(ithitem -> ithitem != null)
                .map(
                    ithitem ->
                        new DatiSegnalazioneTrafficoBuilder()
                            .setIdentificativo(ithitem.getN())
                            .setStart(ithitem.getS())
                            .setEnd(ithitem.getE())
                            .setLocalita(ithitem.getL())
                            .setTipologia(ithitem.getTy())
                            .setDescrizione(ithitem.getDesc())
                            .setDettaglio(ithitem.getDty())
                            .setLat(ithitem.getY())
                            .setLng(ithitem.getX())
                            .build())
                .collect(Collectors.toList());
      }

    } catch (BusinessException e) {
      log.error(sPrefix + " -- errore getListSegnalazioniTraffico:", e);
    } catch (WebApplicationException e) {
      log.error(sPrefix + " -- errore webapp:", e);
      throw new BusinessException(
          "Errore durante la chiamata alla API getListSegnalazioniTraffico: " + e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziSegnalazioniTrafficoImpl -- getListSegnalazioniTraffico: errore durante la chiamata delle API traffico ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("informazioni traffico"));
    } finally {
      instance.closeConnection();
    }
    return listSegnalazioneTraffico;
  }
}
