package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Comune;
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
import org.apache.wicket.util.string.Strings;

public class ComuniItalianiAutoComplete<T> extends AutoCompleteTextField<T> {

  private static final long serialVersionUID = -4028264652284441385L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ComuniItalianiAutoComplete(String id, IModel modello) {
    super(id, modello);

    setModel(modello);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    // setLabel(Model.of("Comune Agenzia Entrate"));

  }

  @SuppressWarnings("unchecked")
  @Override
  protected Iterator<T> getChoices(String input) {
    if (Strings.isEmpty(input)) {
      List<String> emptyList = Collections.emptyList();
      return (Iterator<T>) emptyList.iterator();
    }

    List<String> listaSceltePossibili = new ArrayList<>();

    String info = "Non esiste un comune corrispondente ai caratteri inseriti";

    List<Comune> listaComuni = getComuni(input);
    for (Comune elem : listaComuni) {
      String descrizione = elem.getDescrizione();
      String provincia = "";
      String codice = String.valueOf(elem.getCodice());

      if (LabelFdCUtil.checkIfNotNull(elem.getCodiceProvincia())) {
        provincia = "(".concat(elem.getCodiceProvincia()).concat(")");
      }

      if (descrizione.toUpperCase().contains(input.toUpperCase())) {
        if (LabelFdCUtil.checkNotEmpty(provincia)) {
          listaSceltePossibili.add(descrizione.concat(provincia).concat("-").concat(codice));
        } else {
          listaSceltePossibili.add(descrizione.concat("-").concat(codice));
        }
      }
    }

    if (listaSceltePossibili.isEmpty()) {
      listaSceltePossibili.add(info);
    }

    return (Iterator<T>) listaSceltePossibili.iterator();
  }

  private List<Comune> getComuni(String input) {
    try {
      return ServiceLocator.getInstance()
          .getServiziAnagrafici()
          .getTuttiComuniApkappa(null, input)
          .getResult()
          .getComuni();

    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
