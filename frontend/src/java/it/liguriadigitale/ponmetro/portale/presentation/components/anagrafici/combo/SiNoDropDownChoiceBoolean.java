package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.util.SiNoEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.model.IModel;

public class SiNoDropDownChoiceBoolean<T> extends FdCDropDownChoice<T> {

  private static final long serialVersionUID = 6775026836107540920L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public SiNoDropDownChoiceBoolean(String id, IModel modello) {
    super(id);

    setModel(modello);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    // setRequired(true);
    // setChoiceRenderer(null)
    setChoices(new ComboLoadableDetachableModel(getListaSiNo()));
  }

  private static List<SiNoEnum> getListaSiNo() {
    return Arrays.asList(SiNoEnum.values());
  }
}
