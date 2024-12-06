package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.autocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaPaeseRecord;
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

public class CittadinanzaBibliotecheAutocomplete extends AutoCompleteTextField<TabellaPaeseRecord> {

  List<TabellaPaeseRecord> listaCittadinanze;
  private IConverter<?> _converter;

  private Log log = LogFactory.getLog(getClass());

  public CittadinanzaBibliotecheAutocomplete(
      String id,
      IModel<TabellaPaeseRecord> model,
      IAutoCompleteRenderer<TabellaPaeseRecord> render,
      AutoCompleteSettings settings) {
    super(id, model, TabellaPaeseRecord.class, render, settings);

    listaCittadinanze = new ArrayList<TabellaPaeseRecord>();
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    _converter = this.createConverter(TabellaPaeseRecord.class);
  }

  @Override
  protected Iterator<TabellaPaeseRecord> getChoices(String choose) {

    List<TabellaPaeseRecord> listaCittadinanze = getCittadinanze(choose);
    setListaCittadinanze(listaCittadinanze);

    return listaCittadinanze.stream().collect(Collectors.toList()).iterator();
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
    CittadinanzeAutocompleteConverter converter = new CittadinanzeAutocompleteConverter();
    converter.setListaCittadinanze(listaCittadinanze);
    return converter;
  }

  public void setListaCittadinanze(List<TabellaPaeseRecord> listaCittadinanze) {
    this.listaCittadinanze = listaCittadinanze;
    ((CittadinanzeAutocompleteConverter) _converter).setListaCittadinanze(listaCittadinanze);
  }

  private List<TabellaPaeseRecord> getCittadinanze(String input) {
    List<TabellaPaeseRecord> lista = new ArrayList<>();
    try {
      List<TabellaPaeseRecord> listaPaesi =
          ServiceLocator.getInstance().getServiziBiblioteche().getPaesi(input);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore cittadinanze biblioteche: " + e.getMessage(), e);
    }
    return lista;
  }
}
