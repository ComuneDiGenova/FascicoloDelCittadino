package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ComuneNascitaDropDownChoise<T> extends DropDownChoice<T> {

  private static final long serialVersionUID = 569171277250255349L;

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ComuneNascitaDropDownChoise(String id, IModel modello) {
    super(id, new ComboLoadableDetachableModel(getListaComuniNascita()));

    setModel(modello);

    setRequired(true);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Comune di nascita"));
  }

  private static List<String> getListaComuniNascita() {
    List<String> listaComuniNascira = new ArrayList<String>();
    listaComuniNascira.add("Italiano");
    listaComuniNascira.add("Estero");
    return listaComuniNascira;
  }
}
