package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;

public class SiNoDropDownChoice<T> extends FdCDropDownChoice<T> {

  private static final long serialVersionUID = 6775026836107540920L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public SiNoDropDownChoice(String id, IModel modello) {
    super(id);

    setModel(modello);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    // setRequired(true);

    setChoices(new ComboLoadableDetachableModel(getListaSiNo()));
  }

  private static List<String> getListaSiNo() {
    List<String> listaGenere = new ArrayList<String>();
    listaGenere.add("Sì");
    listaGenere.add("No");
    return listaGenere;
  }
}
