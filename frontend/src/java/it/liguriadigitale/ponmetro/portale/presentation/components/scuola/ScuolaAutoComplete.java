package it.liguriadigitale.ponmetro.portale.presentation.components.scuola;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.commissionimensa.model.Istituto;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
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

public class ScuolaAutoComplete<T> extends AutoCompleteTextField<T> {

  private static final long serialVersionUID = 8734492794079277933L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ScuolaAutoComplete(String id, IModel modello) {
    super(id, modello);

    setModel(modello);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Scuola"));
    setRequired(true);
  }

  @Override
  protected Iterator<T> getChoices(String input) {
    if (Strings.isEmpty(input)) {
      List<String> emptyList = Collections.emptyList();
      return (Iterator<T>) emptyList.iterator();
    }

    List<String> listaSceltePossibili = new ArrayList<>();

    String info = "Non esiste una scuola corrispondente ai caratteri inseriti";

    List<Istituto> listaIstituti = getIstituti();
    for (Istituto istituto : listaIstituti) {
      String descrizione = istituto.getDescScuola();
      String codiceScuola = istituto.getCodScuola();

      if (descrizione.toUpperCase().contains(input.toUpperCase())) {
        listaSceltePossibili.add(descrizione.concat(" ").concat("-").concat(codiceScuola));
      }
    }

    if (listaSceltePossibili.isEmpty()) {
      listaSceltePossibili.add(info);
    }

    return (Iterator<T>) listaSceltePossibili.iterator();
  }

  private List<Istituto> getIstituti() {
    try {
      return ServiceLocator.getInstance().getServiziCommissioniMensa().getIstituti();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
