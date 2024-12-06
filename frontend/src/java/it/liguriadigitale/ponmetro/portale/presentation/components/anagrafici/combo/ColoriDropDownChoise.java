package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.util.ColoriEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ColoriDropDownChoise<T> extends FdCDropDownChoice<T> {

  private static final long serialVersionUID = -3201612318251465948L;

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ColoriDropDownChoise(String id, IModel modello) {
    super(id);

    setModel(modello);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Seleziona Colore"));

    setChoices(new ComboLoadableDetachableModel(getListaColori()));
  }

  private static List<ColoriEnum> getListaColori() {
    return Arrays.asList(ColoriEnum.values());
  }
}
