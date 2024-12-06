package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

public class GeoserverAutoComplete<T> extends AutoCompleteTextField<T> {

  private static final long serialVersionUID = 180590334320794360L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings({"rawtypes", "unchecked"})
  public GeoserverAutoComplete(String id, IModel modello) {
    super(id, modello);

    setModel(modello);

    setRequired(true);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Via e numero civico della sezione 'Nuovo indirizzo'"));
  }

  @Override
  protected Iterator<T> getChoices(String input) {
    if (Strings.isEmpty(input)) {
      List<String> emptyList = Collections.emptyList();
      return (Iterator<T>) emptyList.iterator();
    }

    List<FeaturesGeoserver> listaSceltePossibili = new ArrayList<>();

    String info = "Non esiste una via corrispondente ai caratteri inseriti";
    FeaturesGeoserver vuoto = new FeaturesGeoserver();

    List<FeaturesGeoserver> listaFeatures = getFeaturesGeoserver(input);

    for (FeaturesGeoserver elem : listaFeatures) {
      listaSceltePossibili.add(elem);
    }

    if (listaSceltePossibili.isEmpty()) {
      vuoto.setMACHINE_LAST_UPD(info);
      listaSceltePossibili.add(vuoto);
    }

    return (Iterator<T>) listaSceltePossibili.iterator();
  }

  private List<FeaturesGeoserver> getFeaturesGeoserver(String input) {

    log.debug("CP getFeaturesGeoserver = " + input);

    try {
      List<FeaturesGeoserver> lista = new ArrayList<FeaturesGeoserver>();

      List<FeaturesGeoserver> response =
          ServiceLocator.getInstance().getServiziGeoserver().getGeoserver(input);

      if (LabelFdCUtil.checkIfNotNull(response) && LabelFdCUtil.checkIfNotNull(response)) {
        lista = response;
      }

      return lista;
    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
