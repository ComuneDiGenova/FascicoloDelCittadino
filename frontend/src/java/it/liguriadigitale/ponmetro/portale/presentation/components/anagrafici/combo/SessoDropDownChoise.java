package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class SessoDropDownChoise<T> extends FdCDropDownChoice<T> {

  private static final long serialVersionUID = -6004752109601468666L;

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public SessoDropDownChoise(String id, IModel modello) {
    super(id);

    setModel(modello);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Sesso"));

    setChoices(new ComboLoadableDetachableModel(getListaGenere()));
  }

  private static List<String> getListaGenere() {
    List<String> listaGenere = new ArrayList<String>();
    listaGenere.add("Maschio");
    listaGenere.add("Femmina");
    return listaGenere;
  }
}
