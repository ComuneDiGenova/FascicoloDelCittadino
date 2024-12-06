package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete;

import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.request.Response;

public class GeoserverRender implements IAutoCompleteRenderer<FeaturesGeoserver> {

  private Log log = LogFactory.getLog(getClass());

  private static final long serialVersionUID = 326121980840970999L;

  @Override
  public void render(FeaturesGeoserver arg0, Response arg1, String arg2) {
    log.debug("CP render");
  }

  @Override
  public void renderFooter(Response arg0, int arg1) {
    log.debug("CP renderFooter");
  }

  @Override
  public void renderHeader(Response arg0) {
    log.debug("CP renderHeader");
  }

  //		@Override
  //		public Object getDisplayValue(FeaturesGeoserver obj) {
  //			FeaturesGeoserver item = obj;
  //			String descrizione = "";
  //			if(LabelFdCUtil.checkIfNotNull(item)) {
  //				if(PageUtil.isStringValid(item.getMACHINE_LAST_UPD())) {
  //					descrizione = item.getMACHINE_LAST_UPD();
  //				}
  //			}
  //			return descrizione;
  //		}
  //
  //		@Override
  //		public String getIdValue(FeaturesGeoserver obj, int index) {
  //			FeaturesGeoserver item = obj;
  //			String codiceStrada = "";
  //			if(LabelFdCUtil.checkIfNotNull(item)) {
  //				if(PageUtil.isStringValid(item.getCOD_STRADA())) {
  //					codiceStrada = item.getCOD_STRADA();
  //				}
  //			}
  //			return codiceStrada;
  //		}
  //
  //		@Override
  //		public FeaturesGeoserver getObject(String id, IModel<? extends List<? extends
  // FeaturesGeoserver>> lista) {
  //			FeaturesGeoserver result = null;
  //			for(FeaturesGeoserver elem : lista.getObject()) {
  //				if(LabelFdCUtil.checkIfNotNull(elem)) {
  //					if(elem.getCOD_STRADA().equalsIgnoreCase(id)) {
  //						result = elem;
  //					}
  //				}
  //			}
  //			return result;
  //		}

}
