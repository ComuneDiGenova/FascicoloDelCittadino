package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.MezzoConsegna;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class MezzoConsegnaRenderer extends ChoiceRenderer<MezzoConsegna> {

  private static final long serialVersionUID = 3487601697909708225L;

  @Override
  public Object getDisplayValue(MezzoConsegna obj) {
    MezzoConsegna atto = obj;
    if (LabelFdCUtil.checkIfNotNull(atto)) {
      return atto.getTipoConsegnaScelta();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(MezzoConsegna obj, final int index) {
    MezzoConsegna atto = obj;
    if (LabelFdCUtil.checkIfNotNull(atto)) {
      return atto.getTipoConsegnaScelta();
    } else {
      return "";
    }
  }
}
