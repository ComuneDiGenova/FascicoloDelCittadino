package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.util;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class NazionalitaProprietarioRender extends ChoiceRenderer<NazionalitaProprietarioEnum> {

  private static final long serialVersionUID = -6774120855020520156L;

  @Override
  public Object getDisplayValue(NazionalitaProprietarioEnum obj) {
    NazionalitaProprietarioEnum elem = obj;
    if (LabelFdCUtil.checkIfNotNull(elem)) {
      return elem.getDescrizione();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(NazionalitaProprietarioEnum obj, final int index) {
    NazionalitaProprietarioEnum elem = obj;
    if (LabelFdCUtil.checkIfNotNull(elem)) {
      return elem.getId();
    } else {
      return "";
    }
  }
}
