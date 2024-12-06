package it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Indirizzo.VulnerabilitaEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.model.IModel;

public class VulnerabilitaCivicoDropDownChoice extends FdCDropDownChoice<VulnerabilitaEnum> {

  private static final long serialVersionUID = 4675394973602001985L;

  public VulnerabilitaCivicoDropDownChoice(String id, IModel<VulnerabilitaEnum> model) {
    super(id);

    setChoices(getListaVulnerabilita());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  private static List<VulnerabilitaEnum> getListaVulnerabilita() {
    return Arrays.asList(VulnerabilitaEnum.values());
  }
}
