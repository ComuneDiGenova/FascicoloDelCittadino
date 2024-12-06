package it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto.TipoEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.model.IModel;

public class TipoTelefonoDropDownChoice extends FdCDropDownChoice<TipoEnum> {

  private static final long serialVersionUID = 33444597163174877L;

  public TipoTelefonoDropDownChoice(String id, IModel<TipoEnum> model) {
    super(id);

    setChoices(getListaTipiTelefoni());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  private static List<TipoEnum> getListaTipiTelefoni() {
    return Arrays.asList(TipoEnum.values());
  }
}
