package it.liguriadigitale.ponmetro.portale.presentation.components.contributotari;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class TipologiaAmmortizzatoreSocialeDropDownChoice<T> extends DropDownChoice<T> {

  private static final long serialVersionUID = 6548475521172160720L;

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public TipologiaAmmortizzatoreSocialeDropDownChoice(String id, IModel modello) {
    super(id, new ComboLoadableDetachableModel(getListaTipologie()));

    setModel(modello);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Tipologia ammortizzatore"));
  }

  private static List<String> getListaTipologie() {
    List<String> listaTipologie = new ArrayList<String>();
    listaTipologie.add("Cassa integrazione Covid");
    listaTipologie.add("Cassa integrazione ordinaria");
    listaTipologie.add("Cassa integrazione straordinaria");
    listaTipologie.add("NASPI");
    listaTipologie.add("Altro");
    return listaTipologie;
  }
}
