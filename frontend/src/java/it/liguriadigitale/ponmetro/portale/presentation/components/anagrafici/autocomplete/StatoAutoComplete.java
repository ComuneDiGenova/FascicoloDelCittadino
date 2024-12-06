package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Stato;
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

public class StatoAutoComplete<T> extends AutoCompleteTextField<T> {

  private static final long serialVersionUID = -1937702062709909777L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings({"rawtypes", "unchecked"})
  public StatoAutoComplete(String id, IModel modello) {
    super(id, modello);

    setModel(modello);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Stato"));
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Iterator<T> getChoices(String input) {
    if (Strings.isEmpty(input)) {
      List<String> emptyList = Collections.emptyList();
      return (Iterator<T>) emptyList.iterator();
    }

    List<String> listaSceltePossibili = new ArrayList<>();

    String info = "Non esiste uno stato corrispondente ai caratteri inseriti";

    List<Stato> listaStati = getStati(input);
    for (Stato elem : listaStati) {
      String descrizione = elem.getDescrizione();
      String codice = elem.getCodice();

      if (descrizione.toUpperCase().contains(input.toUpperCase())) {
        listaSceltePossibili.add(descrizione.concat("-").concat(codice));
      }
    }

    if (listaSceltePossibili.isEmpty()) {
      listaSceltePossibili.add(info);
    }

    return (Iterator<T>) listaSceltePossibili.iterator();
  }

  private List<Stato> getStati(String input) {
    try {
      return ServiceLocator.getInstance()
          .getServiziAnagrafici()
          .getStati(null, input)
          .getResult()
          .getStati();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}