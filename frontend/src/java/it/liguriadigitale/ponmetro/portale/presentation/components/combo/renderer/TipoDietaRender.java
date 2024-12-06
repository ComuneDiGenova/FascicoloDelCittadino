package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaRichiestaDietaSpeciale.TipoDietaSpecialeEnum;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class TipoDietaRender extends ChoiceRenderer<TipoDietaSpecialeEnum> {

  private static final long serialVersionUID = -3735572137173684203L;

  @Override
  public Object getDisplayValue(TipoDietaSpecialeEnum obj) {
    TipoDietaSpecialeEnum dieta = obj;
    if (LabelFdCUtil.checkIfNotNull(dieta)) {
      return dieta.value();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(TipoDietaSpecialeEnum obj, final int index) {
    TipoDietaSpecialeEnum dieta = obj;
    if (LabelFdCUtil.checkIfNotNull(dieta)) {
      return String.valueOf(dieta.value());
    } else {
      return "";
    }
  }
}
