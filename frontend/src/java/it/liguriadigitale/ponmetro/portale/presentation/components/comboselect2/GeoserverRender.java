package it.liguriadigitale.ponmetro.portale.presentation.components.comboselect2;

import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class GeoserverRender extends ChoiceRenderer<FeaturesGeoserver> {

  private static final long serialVersionUID = 1403528634067352227L;

  @Override
  public Object getDisplayValue(FeaturesGeoserver obj) {
    FeaturesGeoserver geoserver = obj;
    if (LabelFdCUtil.checkIfNotNull(geoserver)) {
      return geoserver.getMACHINE_LAST_UPD();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(FeaturesGeoserver obj, final int index) {
    FeaturesGeoserver geoserver = obj;
    if (LabelFdCUtil.checkIfNotNull(geoserver)
        && LabelFdCUtil.checkIfNotNull(geoserver.getCODICE_INDIRIZZO())) {
      return String.valueOf(geoserver.getCODICE_INDIRIZZO());
    } else {
      return "";
    }
  }
}
