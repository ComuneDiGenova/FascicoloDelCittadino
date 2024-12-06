package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.scuola.cedole.model.SoggettoAdulto.TipoSoggettoEnum;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class EnumTipoSoggettoRenderer extends ChoiceRenderer<TipoSoggettoEnum> {

  private static final long serialVersionUID = 2053299065206507198L;

  @Override
  public Object getDisplayValue(final TipoSoggettoEnum obj) {
    TipoSoggettoEnum c = obj;
    return c.toString();
  }

  @Override
  public String getIdValue(final TipoSoggettoEnum obj, final int index) {
    TipoSoggettoEnum c = obj;
    if (c != null) return String.valueOf(c.value());
    else return "";
  }
}
