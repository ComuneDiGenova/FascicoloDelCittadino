package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Professione;
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

public class ProfessioneAutoComplete<T> extends AutoCompleteTextField<T> {

  private static final long serialVersionUID = -1883270851352314071L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ProfessioneAutoComplete(String id, IModel modello) {
    super(id, modello);

    setModel(modello);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Professione"));
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Iterator<T> getChoices(String input) {
    if (Strings.isEmpty(input)) {
      List<String> emptyList = Collections.emptyList();
      return (Iterator<T>) emptyList.iterator();
    }

    List<String> listaSceltePossibili = new ArrayList<>();

    String info = "Non esiste una professione corrispondente ai caratteri inseriti";

    List<Professione> listaProfessioni = getProfessioni();
    for (Professione elem : listaProfessioni) {
      String descrizione = elem.getDescrizioneM();
      Integer codice = elem.getCodice();

      if (descrizione.toUpperCase().contains(input.toUpperCase())) {
        listaSceltePossibili.add(descrizione.concat("-").concat(String.valueOf(codice)));
      }
    }

    if (listaSceltePossibili.isEmpty()) {
      listaSceltePossibili.add(info);
    }

    return (Iterator<T>) listaSceltePossibili.iterator();
  }

  private List<Professione> getProfessioni() {
    try {
      return ServiceLocator.getInstance()
          .getServiziAnagrafici()
          .getProfessioni()
          .getResult()
          .getProfessioni();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
