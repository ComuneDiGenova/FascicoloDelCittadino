package it.liguriadigitale.ponmetro.portale.presentation.components.scuola.iscrizionemensanonresidente;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Comune;
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

public class ComuneIscrizioneMensaAutocomplete extends AutoCompleteTextField<Comune> {

  List<Comune> listaComuni;
  private IConverter<?> _converter;

  public String codiceProvincia;

  private Log log = LogFactory.getLog(getClass());

  public ComuneIscrizioneMensaAutocomplete(
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
    ComuneIscrizioneMensaConverter converter = new ComuneIscrizioneMensaConverter();
    converter.setListaComuni(listaComuni);
    return converter;
  }

  public void setListaProvince(List<Comune> listaComuni) {
    this.listaComuni = listaComuni;
    ((ComuneIscrizioneMensaConverter) _converter).setListaComuni(listaComuni);
  }

  private List<Comune> getComuni(String input) {

    log.debug("CP comuni " + codiceProvincia + " - " + getCodiceProvincia());

    List<Comune> lista = new ArrayList<>();
    try {
      lista =
          ServiceLocator.getInstance()
              .getServiziRistorazione()
              .getComuniAutocomplete(getCodiceProvincia(), input);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore comuni mensa non residente: " + e.getMessage(), e);
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
