package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetViarioResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Via;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

public class ViarioAutoComplete<T> extends AutoCompleteTextField<T> {

  private static final long serialVersionUID = 2914403289001532776L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ViarioAutoComplete(String id, IModel modello) {
    super(id, modello);

    setModel(modello);

    setRequired(true);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Viario"));
  }

  @Override
  protected Iterator<T> getChoices(String input) {
    if (Strings.isEmpty(input)) {
      List<String> emptyList = Collections.emptyList();
      return (Iterator<T>) emptyList.iterator();
    }

    List<String> listaSceltePossibili = new ArrayList<>();

    String info = "Non esiste una via corrispondente ai caratteri inseriti";

    List<Via> listaVie = getVie(input);
    for (Via via : listaVie) {
      String descrizioneVia = via.getDescrizioneVia();
      String tipoVia = via.getTipoVia();
      Integer idVia = via.getIdVia();

      if (descrizioneVia.toUpperCase().contains(input.toUpperCase())) {
        listaSceltePossibili.add(
            tipoVia.concat(" ").concat(descrizioneVia).concat("-").concat(String.valueOf(idVia)));
      }
    }

    if (listaSceltePossibili.isEmpty()) {
      listaSceltePossibili.add(info);
    }

    return (Iterator<T>) listaSceltePossibili.iterator();
  }

  private List<Via> getVie(String input) {
    try {
      GetViarioResponseGenericResponse response =
          ServiceLocator.getInstance().getServiziAnagrafici().getViario(null, input);
      List<Via> listaVie = new ArrayList<Via>();
      if (LabelFdCUtil.checkIfNotNull(response)
          && LabelFdCUtil.checkIfNotNull(response.getResult())) {
        listaVie = response.getResult().getViario();
      }

      return listaVie;
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
