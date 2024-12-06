package it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Utente.VulnerabilitaPersonaleEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.model.IModel;

public class VulnerabilitaPersonaleDropDownChoice
    extends FdCDropDownChoice<VulnerabilitaPersonaleEnum> {

  private static final long serialVersionUID = 33444597163174877L;

  public VulnerabilitaPersonaleDropDownChoice(String id, IModel<VulnerabilitaPersonaleEnum> model) {
    super(id);

    setChoices(getListaVulnerabilitaPersonale());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  private static List<VulnerabilitaPersonaleEnum> getListaVulnerabilitaPersonale() {
    return Arrays.asList(VulnerabilitaPersonaleEnum.values());
  }
}
