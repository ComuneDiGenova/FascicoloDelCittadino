package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.converter.ViarioGeoserverConverter;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

public class ViarioGeoserverAutoComplete extends AutoCompleteTextField<FeaturesGeoserver> {

  private static final long serialVersionUID = 8671644679893129996L;

  List<FeaturesGeoserver> listaIndirizzi;
  private IConverter<?> _converter;
  private Log log = LogFactory.getLog(getClass());

  public ViarioGeoserverAutoComplete(
      String id,
      IModel<FeaturesGeoserver> model,
      IAutoCompleteRenderer<FeaturesGeoserver> render,
      AutoCompleteSettings settings) {
    super(id, model, FeaturesGeoserver.class, render, settings);

    listaIndirizzi = new ArrayList<FeaturesGeoserver>();
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    _converter = this.createConverter(FeaturesGeoserver.class);
  }

  @Override
  protected Iterator<FeaturesGeoserver> getChoices(String choose) {

    List<FeaturesGeoserver> listaFeatures = getFeaturesGeoserver(choose);
    setListaIndirizzi(listaFeatures);

    return listaIndirizzi.stream().collect(Collectors.toList()).iterator();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public IConverter<?> getConverter(Class type) {
    log.debug("[Get Converter]");
    return this._converter;
  }

  @Override
  protected IConverter<?> createConverter(Class<?> type) {
    log.debug("[Create Converter]");
    ViarioGeoserverConverter converter = new ViarioGeoserverConverter();
    converter.setListaIndirizzi(listaIndirizzi);
    return converter;
  }

  public void setListaIndirizzi(List<FeaturesGeoserver> listaIndirizzi) {
    this.listaIndirizzi = listaIndirizzi;
    ((ViarioGeoserverConverter) _converter).setListaIndirizzi(listaIndirizzi);
  }

  private List<FeaturesGeoserver> getFeaturesGeoserver(String input) {

    try {
      List<FeaturesGeoserver> lista = new ArrayList<FeaturesGeoserver>();

      List<FeaturesGeoserver> response =
          ServiceLocator.getInstance().getServiziGeoserver().getGeoserver(input);

      if (LabelFdCUtil.checkIfNotNull(response) && LabelFdCUtil.checkIfNotNull(response)) {
        lista = response;
      }

      Comparator<FeaturesGeoserver> comparator =
          Comparator.comparing(
              FeaturesGeoserver::getMACHINE_LAST_UPD,
              Comparator.nullsLast(Comparator.naturalOrder()));
      List<FeaturesGeoserver> listaOrdinata =
          lista.stream().sorted(comparator).collect(Collectors.toList());

      return listaOrdinata;
    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
