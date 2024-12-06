package it.liguriadigitale.ponmetro.portale.business.geoworks.impl.util;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni.TipoFunzEnum;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GeoworksUtils {

  private static Log log = LogFactory.getLog(GeoworksUtils.class);

  public static GeoworksFunzioni getFunzioneGeoworks(TipoFunzEnum tipologia) {
    GeoworksFunzioni funzione = null;

    try {
      funzione =
          ServiceLocator.getInstance()
              .getServiziGeoworksHelper()
              .getGeoworksFunzione(tipologia.value());
    } catch (BusinessException e) {
      log.error("Errore getFunzioneGeoworks: " + e.getMessage());
    }

    return funzione;
  }

  public static List<GeoworksServizi> getServiziGeoworks(TipoFunzEnum tipologia) {
    List<GeoworksServizi> lista = new ArrayList<GeoworksServizi>();

    try {
      lista =
          ServiceLocator.getInstance()
              .getServiziGeoworksHelper()
              .getGeoworksServizi(tipologia.value());
    } catch (BusinessException e) {
      log.error("Errore getServiziGeoworks: " + e.getMessage());
    }

    return lista;
  }
}
