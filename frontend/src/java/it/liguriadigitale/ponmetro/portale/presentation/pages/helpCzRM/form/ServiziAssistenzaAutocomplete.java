package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form;

import it.liguriadigitale.ponmetro.portale.pojo.richiestaassistenza.SottoCategoria;
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
import org.apache.wicket.util.string.Strings;

public class ServiziAssistenzaAutocomplete extends AutoCompleteTextField<SottoCategoria> {

  List<SottoCategoria> servizi;
  private IConverter<?> _converter;
  private Log log = LogFactory.getLog(getClass());

  public ServiziAssistenzaAutocomplete(
      String id,
      IModel<SottoCategoria> model,
      IAutoCompleteRenderer<SottoCategoria> render,
      AutoCompleteSettings settings) {
    super(id, model, SottoCategoria.class, render, settings);

    servizi = new ArrayList<SottoCategoria>();
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    _converter = this.createConverter(SottoCategoria.class);
  }

  @Override
  protected Iterator<SottoCategoria> getChoices(String choose) {
    if (Strings.isEmpty(choose)) {

      return servizi.stream().limit(10).collect(Collectors.toList()).iterator();
    }

    return servizi.stream()
        .filter(x -> x.getServizio().toLowerCase().contains(choose.toLowerCase()))
        .limit(10)
        .collect(Collectors.toList())
        .iterator();
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
    ServiziAssistenzaConverter converter = new ServiziAssistenzaConverter();
    converter.setListaServizi(servizi);
    return converter;
  }

  public void setServizi(List<SottoCategoria> servizi) {
    this.servizi = servizi;
    ((ServiziAssistenzaConverter) _converter).setListaServizi(servizi);
  }
}
