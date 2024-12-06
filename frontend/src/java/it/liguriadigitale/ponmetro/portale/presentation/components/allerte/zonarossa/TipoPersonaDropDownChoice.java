package it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Componente.TipoEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.model.IModel;

public class TipoPersonaDropDownChoice extends FdCDropDownChoice<TipoEnum> {

  private static final long serialVersionUID = 1L;

  public TipoPersonaDropDownChoice(String id, IModel<TipoEnum> model) {
    super(id);

    setChoices(getListaTipo());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  private static List<TipoEnum> getListaTipo() {
    return Arrays.asList(TipoEnum.values()).stream()
        .filter(elem -> !elem.value().equalsIgnoreCase(TipoEnum.CAPO_FAMIGLIA.value()))
        .collect(Collectors.toList());
  }
}
