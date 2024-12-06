package it.liguriadigitale.ponmetro.api.business.geoworks.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.configurazione.impl.ContattiImpl;
import it.liguriadigitale.ponmetro.api.business.geoworks.service.GeoworksService;
import it.liguriadigitale.ponmetro.api.integration.dao.GeoworksTFunzioniDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.GeoworksTServiziDAO;
import it.liguriadigitale.ponmetro.api.pojo.contatti.builder.GeoworksFunzioniBuilder;
import it.liguriadigitale.ponmetro.api.pojo.contatti.builder.GeoworksServiziBuilder;
import it.liguriadigitale.ponmetro.api.pojo.geoworks.GeoworksTFunzioni;
import it.liguriadigitale.ponmetro.api.pojo.geoworks.GeoworksTServizi;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni.TipoFunzEnum;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GeoworksImpl implements GeoworksService {

  private static Log log = LogFactory.getLog(ContattiImpl.class);

  @SuppressWarnings("unchecked")
  @Override
  public List<GeoworksTFunzioni> selectGeoworksFunzioni() throws BusinessException {
    log.debug("selectGeoworksFunzioni");

    GeoworksTFunzioni funzioni = new GeoworksTFunzioni();
    GeoworksTFunzioniDAO dao = new GeoworksTFunzioniDAO(funzioni);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<GeoworksTFunzioni> listaFunzioniGeoworks = helper.cercaOggetti();

    log.debug("Select geoworks funzioni = " + listaFunzioniGeoworks);
    return listaFunzioniGeoworks;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<GeoworksTServizi> selectGeoworksServizi(String tipoFunz) throws BusinessException {
    log.debug("selectGeoworksServizi tipoFunz = " + tipoFunz);

    GeoworksTServizi servizi = new GeoworksTServizi();
    servizi.setTipoFunz(tipoFunz);
    GeoworksTServiziDAO dao = new GeoworksTServiziDAO(servizi);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<GeoworksTServizi> listaServiziGeoworks = helper.cercaOggetti();

    log.debug("Select geoworks servizi = " + listaServiziGeoworks);
    return listaServiziGeoworks;
  }

  @Override
  public List<GeoworksFunzioni> getGeoworksFunzioni() throws BusinessException {

    List<GeoworksTFunzioni> selectGeoworksFunzioni = selectGeoworksFunzioni();

    List<GeoworksFunzioni> funzioni = new ArrayList<>();

    if (selectGeoworksFunzioni != null) {
      for (GeoworksTFunzioni elem : selectGeoworksFunzioni) {
        GeoworksFunzioni funzione = buildGeoworksFunzioni(elem);
        funzioni.add(funzione);
      }
    }

    return funzioni;
  }

  @Override
  public List<GeoworksServizi> getGeoworksServizi(String tipoFunz) throws BusinessException {
    List<GeoworksTServizi> selectGeoworksServizi = selectGeoworksServizi(tipoFunz);

    List<GeoworksServizi> servizi = new ArrayList<>();

    if (selectGeoworksServizi != null) {
      for (GeoworksTServizi elem : selectGeoworksServizi) {
        GeoworksServizi servizio = buildGeoworksServizi(elem);
        servizi.add(servizio);
      }
    }

    return servizi;
  }

  @Override
  public GeoworksTFunzioni selectGeoworksFunzione(String tipoFunz) throws BusinessException {
    log.debug("selectGeoworksFunzione tipoFunz = " + tipoFunz);

    GeoworksTFunzioni funzione = new GeoworksTFunzioni();
    funzione.setTipoFunz(tipoFunz);
    GeoworksTFunzioniDAO dao = new GeoworksTFunzioniDAO(funzione);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    GeoworksTFunzioni funzioneGeoworks = (GeoworksTFunzioni) helper.cercaOggetto();

    log.debug("Select geoworks funzione = " + funzioneGeoworks);
    return funzioneGeoworks;
  }

  @Override
  public GeoworksFunzioni getGeoworksFunzione(String tipoFunz) throws BusinessException {
    GeoworksTFunzioni selectGeoworksFunzione = selectGeoworksFunzione(tipoFunz);

    GeoworksFunzioni funzione = new GeoworksFunzioni();

    if (selectGeoworksFunzione != null) {
      funzione = buildGeoworksFunzioni(selectGeoworksFunzione);
    }

    return funzione;
  }

  private GeoworksFunzioni buildGeoworksFunzioni(GeoworksTFunzioni funzioni) {
    log.debug("buildGeoworksFunzioni");

    GeoworksFunzioniBuilder funzioniBuilder =
        new GeoworksFunzioniBuilder()
            .setIdFunz(funzioni.getIdFunz())
            .setTipoFunz(TipoFunzEnum.valueOf(funzioni.getTipoFunz()))
            .setDescrizioneFunz(funzioni.getDescrizioneFunz());

    GeoworksFunzioni funzioniResult = funzioniBuilder.build();

    log.debug("buildGeoworksFunzioni funzioniResult = " + funzioniResult);

    return funzioniResult;
  }

  private GeoworksServizi buildGeoworksServizi(GeoworksTServizi servizi) {
    log.debug("buildGeoworksServizi");

    GeoworksServiziBuilder serviziBuilder =
        new GeoworksServiziBuilder()
            .setIdServizio(servizi.getIdServizio())
            .setIdFunz(servizi.getIdFunz())
            .setDescrizioneServizio(servizi.getDescrizioneServizio());

    GeoworksServizi serviziResult = serviziBuilder.build();

    log.debug("buildGeoworksServizi serviziResult = " + serviziResult);

    return serviziResult;
  }
}
