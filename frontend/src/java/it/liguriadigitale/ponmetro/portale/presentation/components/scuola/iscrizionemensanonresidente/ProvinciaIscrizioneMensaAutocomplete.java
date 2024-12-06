package it.liguriadigitale.ponmetro.portale.presentation.components.scuola.iscrizionemensanonresidente;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Provincia;
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

public class ProvinciaIscrizioneMensaAutocomplete extends AutoCompleteTextField<Provincia> {

  private static final long serialVersionUID = 4651020687135109418L;

  List<Provincia> listaProvince;
  private IConverter<?> _converter;
  private Log log = LogFactory.getLog(getClass());

  public ProvinciaIscrizioneMensaAutocomplete(
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
    ProvinciaIscrizioneMensaConverter converter = new ProvinciaIscrizioneMensaConverter();
    converter.setListaProvince(listaProvince);
    return converter;
  }

  public void setListaProvince(List<Provincia> listaProvince) {
    this.listaProvince = listaProvince;
    ((ProvinciaIscrizioneMensaConverter) _converter).setListaProvince(listaProvince);
  }

  private List<Provincia> getProvince(String input) {
    List<Provincia> lista = new ArrayList<>();
    try {
      lista = ServiceLocator.getInstance().getServiziRistorazione().getProvinceAutocomplete(input);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore province mensa: " + e.getMessage(), e);
    }
    return lista;
  }
}
