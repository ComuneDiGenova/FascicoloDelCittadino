package it.liguriadigitale.ponmetro.portale.presentation.components.nucleo;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class ComponenteNucleoAmtRender extends ChoiceRenderer<ComponenteNucleoAmt> {

  private static final long serialVersionUID = 6867701326111507362L;

  @Override
  public Object getDisplayValue(ComponenteNucleoAmt obj) {
    ComponenteNucleoAmt elem = obj;
    if (LabelFdCUtil.checkIfNotNull(elem)
        && LabelFdCUtil.checkIfNotNull(elem.getComponenteNucleo())) {
      return elem.getComponenteNucleo().getDatiCittadino().getCpvFamilyName()
          + " "
          + elem.getComponenteNucleo().getDatiCittadino().getCpvGivenName();
    } else {
      return "TUTTI";
    }
  }

  @Override
  public String getIdValue(ComponenteNucleoAmt obj, final int index) {
    ComponenteNucleoAmt elem = obj;
    if (LabelFdCUtil.checkIfNotNull(elem)
        && LabelFdCUtil.checkIfNotNull(elem.getComponenteNucleo())) {
      return elem.getComponenteNucleo().getDatiCittadino().getCpvTaxCode();
    } else {
      return "TUTTI";
    }
  }
}
