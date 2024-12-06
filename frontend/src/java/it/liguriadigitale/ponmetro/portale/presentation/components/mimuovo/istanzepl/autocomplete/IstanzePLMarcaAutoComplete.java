package it.liguriadigitale.ponmetro.portale.presentation.components.mimuovo.istanzepl.autocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class IstanzePLMarcaAutoComplete<T> extends AutoCompleteTextField<T> {

  private static final long serialVersionUID = -1;

  private Log log = LogFactory.getLog(getClass());
  private DatiRichiestaIstanzaPl datiRichiestaIstanza;

  public IstanzePLMarcaAutoComplete(
      String id, IModel modello, DatiRichiestaIstanzaPl datiRichiestaIstanza) {
    super(id, modello);

    setModel(modello);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    this.datiRichiestaIstanza = datiRichiestaIstanza;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Iterator<T> getChoices(String input) {
    if (Strings.isEmpty(input)) {
      List<String> emptyList = Collections.emptyList();
      return (Iterator<T>) emptyList.iterator();
    }

    List<String> listaSceltePossibili = new ArrayList<>();

    String info = "Non esiste una marca corrispondente ai caratteri inseriti";

    List<String> listaMarche = getMarche(input);
    for (String elem : listaMarche) {
      String descrizione = elem;
      listaSceltePossibili.add(elem);
    }

    if (listaSceltePossibili.isEmpty()) {
      listaSceltePossibili.add(info);
    }

    return (Iterator<T>) listaSceltePossibili.iterator();
  }

  private List<String> getMarche(String input) {
    try {
      List<String> tutteLeMarche = datiRichiestaIstanza.getTutteLeMarche();
      if (tutteLeMarche == null) {
        tutteLeMarche = ServiceLocator.getInstance().getServiziMieiVerbali().getMarcheVeicoli();
        datiRichiestaIstanza.setTutteLeMarche(tutteLeMarche);
      }
      return tutteLeMarche.stream()
          .filter(e -> e.toUpperCase().contains(input.toUpperCase()))
          .collect(Collectors.toList());
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
