package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDirezioneTerritoriale;
import java.io.IOException;
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
import org.apache.wicket.util.string.Strings;

public class DirezioneTerritorialeAutocomplete
    extends AutoCompleteTextField<DatiDirezioneTerritoriale> {

  private static final long serialVersionUID = 1L;
  List<DatiDirezioneTerritoriale> _direzioneTerritoriale;

  protected static Log log = LogFactory.getLog(DirezioneTerritorialeAutocomplete.class);

  private IConverter<?> _converter;

  public DirezioneTerritorialeAutocomplete(
      String id,
      IModel<DatiDirezioneTerritoriale> model,
      IAutoCompleteRenderer<DatiDirezioneTerritoriale> render,
      AutoCompleteSettings settings) {
    super(id, model, DatiDirezioneTerritoriale.class, render, settings);
    // TODO Auto-generated constructor stub
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    _direzioneTerritoriale = getListaTipi();
    _converter = this.createConverter(DatiDirezioneTerritoriale.class);
  }

  @Override
  protected Iterator<DatiDirezioneTerritoriale> getChoices(String choose) {
    if (Strings.isEmpty(choose)) {

      return _direzioneTerritoriale.stream().limit(10).collect(Collectors.toList()).iterator();
    }

    return _direzioneTerritoriale.stream()
        .filter(x -> x.getNome().toLowerCase().contains(choose.toLowerCase()))
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
    DatiDirezioneTerritorialeConverter conv = new DatiDirezioneTerritorialeConverter();
    conv.setListaDatiDirezioneTerritoriale(_direzioneTerritoriale);
    return conv;
  }

  private List<DatiDirezioneTerritoriale> getListaTipi() {
    // TODO Auto-generated method stub
    try {
      return ServiceLocator.getInstance()
          .getServiziRistorazione()
          .getListaDatiDirezioneTerritoriali();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
