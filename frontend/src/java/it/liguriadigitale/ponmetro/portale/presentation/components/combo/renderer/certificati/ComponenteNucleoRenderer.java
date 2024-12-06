package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati;

import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class ComponenteNucleoRenderer extends ChoiceRenderer<ComponenteNucleo> {

  private static final long serialVersionUID = 6867701326111507362L;

  @Override
  public Object getDisplayValue(ComponenteNucleo obj) {
    ComponenteNucleo atto = obj;
    if (LabelFdCUtil.checkIfNotNull(atto) && LabelFdCUtil.checkIfNotNull(atto.getDatiCittadino())) {
      return atto.getDatiCittadino().getCpvFamilyName()
          + " "
          + atto.getDatiCittadino().getCpvGivenName();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(ComponenteNucleo obj, final int index) {
    ComponenteNucleo atto = obj;
    if (LabelFdCUtil.checkIfNotNull(atto) && LabelFdCUtil.checkIfNotNull(atto.getDatiCittadino())) {
      return atto.getDatiCittadino().getCpvTaxCode();
    } else {
      return "";
    }
  }
}
