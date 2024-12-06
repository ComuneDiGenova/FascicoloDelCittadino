package it.liguriadigitale.ponmetro.portale.presentation.components.scuola.iscrizionemensanonresidente;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Cittadinanza;
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

public class CittadinanzaIscrizioneMensaAutocomplete extends AutoCompleteTextField<Cittadinanza> {

  private static final long serialVersionUID = -3159650332761839017L;

  List<Cittadinanza> listaCittadinanze;
  private IConverter<?> _converter;

  public String codiceProvincia;

  private Log log = LogFactory.getLog(getClass());

  public CittadinanzaIscrizioneMensaAutocomplete(
      String id,
      IModel<Cittadinanza> model,
      IAutoCompleteRenderer<Cittadinanza> render,
      AutoCompleteSettings settings) {
    super(id, model, Cittadinanza.class, render, settings);

    listaCittadinanze = new ArrayList<Cittadinanza>();
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    _converter = this.createConverter(Provincia.class);
  }

  @Override
  protected Iterator<Cittadinanza> getChoices(String choose) {

    List<Cittadinanza> listaCittadinanze = getCittadinanze(choose);
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
    CittadinanzeIscrizioneMensaConverter converter = new CittadinanzeIscrizioneMensaConverter();
    converter.setListaCittadinanze(listaCittadinanze);
    return converter;
  }

  public void setListaCittadinanze(List<Cittadinanza> listaCittadinanze) {
    this.listaCittadinanze = listaCittadinanze;
    ((CittadinanzeIscrizioneMensaConverter) _converter).setListaCittadinanze(listaCittadinanze);
  }

  private List<Cittadinanza> getCittadinanze(String input) {
    List<Cittadinanza> lista = new ArrayList<>();
    try {
      lista =
          ServiceLocator.getInstance().getServiziRistorazione().getCittadinanzeAutocomplete(input);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore cittadinanze borse: " + e.getMessage(), e);
    }
    return lista;
  }
}
