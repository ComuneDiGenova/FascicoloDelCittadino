package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio.autocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.Provincia;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

public class ProvinciaBorseStudioAutocomplete extends AutoCompleteTextField<Provincia> {

  private static final long serialVersionUID = -8885489119110839639L;

  List<Provincia> listaProvince;
  private IConverter<?> _converter;
  private Log log = LogFactory.getLog(getClass());

  public ProvinciaBorseStudioAutocomplete(
      String id,
      IModel<Provincia> model,
      IAutoCompleteRenderer<Provincia> render,
      AutoCompleteSettings settings) {
    super(id, model, Provincia.class, render, settings);

    listaProvince = new ArrayList<Provincia>();
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    _converter = this.createConverter(Provincia.class);
  }

  @Override
  protected Iterator<Provincia> getChoices(String choose) {

    List<Provincia> listaProvince = getProvince(choose);
    setListaProvince(listaProvince);

    return listaProvince.stream().collect(Collectors.toList()).iterator();
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
    ProvinciaBorseStudioAutocompleteConverter converter =
        new ProvinciaBorseStudioAutocompleteConverter();
    converter.setListaProvince(listaProvince);
    return converter;
  }

  public void setListaProvince(List<Provincia> listaProvince) {
    this.listaProvince = listaProvince;
    ((ProvinciaBorseStudioAutocompleteConverter) _converter).setListaProvince(listaProvince);
  }

  private List<Provincia> getProvince(String input) {
    List<Provincia> lista = new ArrayList<>();
    try {
      lista = ServiceLocator.getInstance().getServiziBorseDiStudio().getProvinciaSelect2(input);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore province borse: " + e.getMessage(), e);
    }
    return lista;
  }
}
