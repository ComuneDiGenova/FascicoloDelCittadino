package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio.autocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.Comune;
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

public class ComuneBorseStudioAutocomplete extends AutoCompleteTextField<Comune> {

  List<Comune> listaComuni;
  private IConverter<?> _converter;

  public String codiceProvincia;

  private Log log = LogFactory.getLog(getClass());

  public ComuneBorseStudioAutocomplete(
      String id,
      IModel<Comune> model,
      IAutoCompleteRenderer<Comune> render,
      AutoCompleteSettings settings,
      String codiceProvincia) {
    super(id, model, Comune.class, render, settings);

    listaComuni = new ArrayList<Comune>();
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    setCodiceProvincia(codiceProvincia);

    _converter = this.createConverter(Provincia.class);
  }

  @Override
  protected Iterator<Comune> getChoices(String choose) {

    log.debug("CP getChoices = " + choose + " - " + getCodiceProvincia());

    List<Comune> listaProvince = getComuni(choose);
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
    ComuneBorseStudioAutocompleteConverter converter = new ComuneBorseStudioAutocompleteConverter();
    converter.setListaComuni(listaComuni);
    return converter;
  }

  public void setListaProvince(List<Comune> listaComuni) {
    this.listaComuni = listaComuni;
    ((ComuneBorseStudioAutocompleteConverter) _converter).setListaComuni(listaComuni);
  }

  private List<Comune> getComuni(String input) {
    List<Comune> lista = new ArrayList<>();
    try {
      lista =
          ServiceLocator.getInstance()
              .getServiziBorseDiStudio()
              .getComuniSelect2(getCodiceProvincia(), input);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore comuni borse: " + e.getMessage(), e);
    }
    return lista;
  }

  public String getCodiceProvincia() {
    return codiceProvincia;
  }

  public void setCodiceProvincia(String codiceProvincia) {
    this.codiceProvincia = codiceProvincia;
  }
}
