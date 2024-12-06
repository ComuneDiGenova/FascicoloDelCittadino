package it.liguriadigitale.ponmetro.portale.business.geoserver.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.geoserver.model.FeatureCollection;
import it.liguriadigitale.ponmetro.geoserver.model.Features;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.geoserver.service.GeoserverInterface;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class GeoserverImpl implements GeoserverInterface {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_GEOSERVER = "Errore di connessione alle API Geoserver";

  @Override
  public FeatureCollection getWfs(String input) throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("CP GeoserverImpl getwfs = " + input);

      // SU POSTMAN USO QUESTO
      // https://apiprod.comune.genova.it:28243/geoserver/wfs?
      // service=WFS&
      // version=1.1.0&
      // request=GetFeature&
      // outputFormat=application%2Fjson&
      // maxFeatures=100&
      // typeName=SITGEO:CIVICI_COD_TOPON&
      // cql_filter=(MACHINE_LAST_UPD+ILIKE+%27%25CORSO%25%27)%20AND%20(MACHINE_LAST_UPD+ILIKE+%27%25europa%25%27)&
      // sortBy=NUMERO&
      // srsName=EPSG:4326&
      // startIndex=0
      //

      if (input.contains("'")) {
        input = input.replaceAll("'", "''");
      }

      if (input.contains(" ")) {
        input = input.replaceAll(" ", "%' AND MACHINE_LAST_UPD ILIKE '%");
      }

      String service = "WFS";
      String version = "1.1.0";
      String request = "GetFeature";
      String typeName = "SITGEO:CIVICI_COD_TOPON";
      String maxFeatures = "20";
      String outputFormat = "application/json";
      // String cql_filter =
      // "(MACHINE_LAST_UPD+ILIKE+%27%25%25%27)%20AND%20(MACHINE_LAST_UPD+ILIKE+%27%25"
      // + input + "%25%27)&";
      String cql_filter = "(MACHINE_LAST_UPD ILIKE '%" + input + "%')";
      String sort_by = "NUMERO";
      String srsName = "EPSG:4326";
      String startIndex = "0";

      log.debug("CP cql_filter = " + cql_filter);

      FeatureCollection risposta =
          instance
              .getApiGeoserver()
              .getWfs(
                  service,
                  version,
                  request,
                  typeName,
                  maxFeatures,
                  outputFormat,
                  cql_filter,
                  sort_by,
                  srsName,
                  startIndex);

      log.debug("CP risposta geoserver = " + risposta);

      return risposta;

    } catch (BusinessException e) {
      log.error("GeoserverImpl -- getWfs: errore API geoserver:", e);
      throw new BusinessException(ERRORE_API_GEOSERVER);
    } catch (WebApplicationException e) {
      log.error("GeoserverImpl -- getWfs: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug("GeoserverImpl -- getWfs: errore durante la chiamata delle API geoserver ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Toponomstica"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<FeaturesGeoserver> getGeoserver(String input) throws BusinessException, ApiException {
    try {
      List<FeaturesGeoserver> listaFeaturesGeoserver = new ArrayList<FeaturesGeoserver>();

      FeatureCollection wfs = getWfs(input);

      if (LabelFdCUtil.checkIfNotNull(wfs)) {
        List<Features> listaFeatures = wfs.getFeatures();
        for (Features elem : listaFeatures) {
          if (LabelFdCUtil.checkIfNotNull(elem)) {
            FeaturesGeoserver featuresGeoserver = new FeaturesGeoserver();

            featuresGeoserver.setID(elem.getProperties().getID());
            featuresGeoserver.setDESVIA(elem.getProperties().getDESVIA());
            featuresGeoserver.setCOD_STRADA(elem.getProperties().getCODSTRADA());
            featuresGeoserver.setNUMERO(elem.getProperties().getNUMERO());
            featuresGeoserver.setLETTERA(elem.getProperties().getLETTERA());
            featuresGeoserver.setCOLORE(elem.getProperties().getCOLORE());
            featuresGeoserver.setTESTO(elem.getProperties().getTESTO());
            featuresGeoserver.setCODICE_INDIRIZZO(elem.getProperties().getCODICEINDIRIZZO());
            featuresGeoserver.setIDOGGETTORIFERIMENTO(
                elem.getProperties().getIDOGGETTORIFERIMENTO());
            featuresGeoserver.setMACHINE_LAST_UPD(elem.getProperties().getMACHINELASTUPD());
            featuresGeoserver.setCOD_TOPON(elem.getProperties().getCODTOPON());

            listaFeaturesGeoserver.add(featuresGeoserver);
          }
        }
      }

      return listaFeaturesGeoserver;

    } catch (BusinessException e) {
      log.error("GeoserverImpl -- getWfs: errore API geoserver:", e);
      throw new BusinessException(ERRORE_API_GEOSERVER);
    } catch (WebApplicationException e) {
      log.error("GeoserverImpl -- getWfs: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug("GeoserverImpl -- getWfs: errore durante la chiamata delle API geoserver ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Toponomastica"));
    }
  }

  @Override
  public FeaturesGeoserver getToponomasticaResidenzaUtenteLoggato(Utente utente)
      throws BusinessException, ApiException {

    log.debug("CP getToponomasticaResidenzaUtenteLoggato");

    try {
      FeaturesGeoserver toponomasticaResidenza = null;

      if (utente.isResidente()
          && LabelFdCUtil.checkIfNotNull(utente.getDatiCittadinoResidente())
          && LabelFdCUtil.checkIfNotNull(utente.getDatiCittadinoResidente().getCpvHasAddress())) {
        String toponomyDemografico =
            utente.getDatiCittadinoResidente().getCpvHasAddress().getClvToponymQualifier();
        String nameStreetDemografico =
            utente.getDatiCittadinoResidente().getCpvHasAddress().getClvOfficialStreetName();
        String numberStreetDemografico =
            utente.getDatiCittadinoResidente().getCpvHasAddress().getClvStreenNumberOnly();

        String codiceIndirizzoDemografico =
            utente.getDatiCittadinoResidente().getCpvHasAddress().getGenovaOntoStreetNumberCode();

        if (PageUtil.isStringValid(toponomyDemografico)
            && PageUtil.isStringValid(nameStreetDemografico)
            && PageUtil.isStringValid(numberStreetDemografico)) {
          String toponomyStreetDemografico =
              toponomyDemografico
                  .concat(" ")
                  .concat(nameStreetDemografico)
                  .concat(" ")
                  .concat(numberStreetDemografico);

          log.debug("CP toponomyStreetDemografico = " + toponomyStreetDemografico);

          List<FeaturesGeoserver> listaGeoserver =
              ServiceLocator.getInstance()
                  .getServiziGeoserver()
                  .getGeoserver(toponomyStreetDemografico);

          if (LabelFdCUtil.checkIfNotNull(listaGeoserver)
              && !LabelFdCUtil.checkEmptyList(listaGeoserver)) {
            log.debug("CP listaGeoserver size = " + listaGeoserver.size());

            toponomasticaResidenza =
                listaGeoserver.stream()
                    .filter(
                        elem ->
                            LabelFdCUtil.checkIfNotNull(elem.getCODICE_INDIRIZZO())
                                && elem.getCODICE_INDIRIZZO()
                                    .equals(Integer.parseInt(codiceIndirizzoDemografico)))
                    .findAny()
                    .orElse(null);
            log.debug("CP toponomasticaResidenza = " + toponomasticaResidenza);
          }
        }
      }

      return toponomasticaResidenza;

    } catch (BusinessException e) {
      log.error(
          "GeoserverImpl -- getToponomasticaResidenzaUtenteLoggato: errore API geoserver:", e);
      throw new BusinessException(ERRORE_API_GEOSERVER);
    } catch (WebApplicationException e) {
      log.error(
          "GeoserverImpl -- getToponomasticaResidenzaUtenteLoggato: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "GeoserverImpl -- getToponomasticaResidenzaUtenteLoggato: errore durante la chiamata delle API geoserver ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Toponomastica"));
    }
  }

  @Override
  public FeatureCollection getWfsByCodiceStradaNumeroCivico(
      String codiceViaDomicilio, String numeroCivico) throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug(
          "CP GeoserverImpl getWfsByCodiceStradaNumeroCivico = "
              + codiceViaDomicilio
              + " "
              + numeroCivico);

      String codiceViaSenzaSpazi = codiceViaDomicilio.trim();
      String numeroCivicoSenzaSpazi = numeroCivico.trim();

      String service = "WFS";
      String version = "1.1.0";
      String request = "GetFeature";
      String typeName = "SITGEO:CIVICI_COD_TOPON";
      String maxFeatures = "10";
      String outputFormat = "application/json";
      String cql_filter =
          "(COD_STRADA ILIKE '%"
              + codiceViaSenzaSpazi
              + "%' AND NUMERO ILIKE '%"
              + numeroCivicoSenzaSpazi
              + "%')";
      String sort_by = "NUMERO";
      String srsName = "EPSG:4326";
      String startIndex = "0";

      log.debug("CP cql_filter = " + cql_filter);

      FeatureCollection risposta =
          instance
              .getApiGeoserver()
              .getWfs(
                  service,
                  version,
                  request,
                  typeName,
                  maxFeatures,
                  outputFormat,
                  cql_filter,
                  sort_by,
                  srsName,
                  startIndex);

      log.debug("CP risposta geoserver = " + risposta);

      return risposta;

    } catch (BusinessException e) {
      log.error("GeoserverImpl -- getWfs: errore API geoserver:", e);
      throw new BusinessException(ERRORE_API_GEOSERVER);
    } catch (WebApplicationException e) {
      log.error("GeoserverImpl -- getWfs: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug("GeoserverImpl -- getWfs: errore durante la chiamata delle API geoserver ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Toponomastica"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public FeaturesGeoserver getGeoserverByCodiceStradaNumeroCivico(
      String codiceViaDomicilio, String numeroCivico, String viaDomicilio)
      throws BusinessException, ApiException {
    FeaturesGeoserver geoserver = null;

    FeatureCollection response = getWfsByCodiceStradaNumeroCivico(codiceViaDomicilio, numeroCivico);

    if (LabelFdCUtil.checkIfNotNull(response)
        && LabelFdCUtil.checkIfNotNull(response.getFeatures())) {
      String numeroCivicoTrim = numeroCivico.trim();

      Integer numeroCivicoInNumero = Integer.parseInt(numeroCivicoTrim);

      String viaNumeroCivico =
          viaDomicilio.concat(" ").concat(String.valueOf(numeroCivicoInNumero));

      log.debug(
          "Numero civico = "
              + numeroCivico
              + " - "
              + numeroCivicoInNumero
              + " - "
              + viaNumeroCivico);

      Features features =
          response.getFeatures().stream()
              .filter(
                  elem ->
                      elem.getProperties() != null
                          && elem.getProperties()
                              .getMACHINELASTUPD()
                              .equalsIgnoreCase(viaNumeroCivico))
              .findAny()
              .orElse(null);
      if (LabelFdCUtil.checkIfNotNull(features)
          && LabelFdCUtil.checkIfNotNull(features.getProperties())) {
        geoserver = new FeaturesGeoserver();

        geoserver.setCOD_STRADA(features.getProperties().getCODSTRADA());
        geoserver.setNUMERO(features.getProperties().getNUMERO());
        geoserver.setDESVIA(features.getProperties().getDESVIA());
        geoserver.setMACHINE_LAST_UPD(features.getProperties().getMACHINELASTUPD());
      }
    }

    return geoserver;
  }
}
