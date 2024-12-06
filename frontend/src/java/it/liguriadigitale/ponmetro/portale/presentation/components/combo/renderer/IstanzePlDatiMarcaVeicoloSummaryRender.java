package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class IstanzePlDatiMarcaVeicoloSummaryRender extends ChoiceRenderer<String> {

  private static final long serialVersionUID = -1L;

  @Override
  public Object getDisplayValue(String obj) {
    String displayValue = obj;
    if (LabelFdCUtil.checkIfNotNull(displayValue)) {
      return displayValue;
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(String obj, final int index) {
    String idValue = obj;
    if (LabelFdCUtil.checkIfNotNull(idValue)) {
      return idValue;
    } else {
      return "";
    }
  }
}
