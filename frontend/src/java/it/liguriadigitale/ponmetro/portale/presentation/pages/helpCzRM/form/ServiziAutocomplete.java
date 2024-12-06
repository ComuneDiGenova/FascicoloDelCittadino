package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form;

import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmServizi;
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

public class ServiziAutocomplete extends AutoCompleteTextField<CzrmServizi> {

  private static final long serialVersionUID = -1546464646513218L;
  List<CzrmServizi> servizi;
  private IConverter<?> _converter;
  private Log log = LogFactory.getLog(getClass());

  public ServiziAutocomplete(
      String id,
      IModel<CzrmServizi> model,
      IAutoCompleteRenderer<CzrmServizi> render,
      AutoCompleteSettings settings) {
    super(id, model, CzrmServizi.class, render, settings);

    servizi = new ArrayList<CzrmServizi>();
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    _converter = this.createConverter(CzrmServizi.class);
  }

  @Override
  protected Iterator<CzrmServizi> getChoices(String choose) {
    // TODO Auto-generated method stub
    if (Strings.isEmpty(choose)) {

      return servizi.stream().limit(10).collect(Collectors.toList()).iterator();
    }

    return servizi.stream()
        .filter(x -> x.getServizioText().toLowerCase().contains(choose.toLowerCase()))
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
    CzRMServiziConverter converter = new CzRMServiziConverter();
    converter.setListaServizi(servizi);
    return converter;
  }

  public void setServizi(List<CzrmServizi> servizi) {
    this.servizi = servizi;
    ((CzRMServiziConverter) _converter).setListaServizi(servizi);
  }
}
