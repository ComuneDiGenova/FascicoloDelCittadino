package it.liguriadigitale.ponmetro.portale.presentation.components.teleriscaldamento;

import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.TipoUtenzaEnum;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class TipoUtenzaRender extends ChoiceRenderer<TipoUtenzaEnum> {

  private static final long serialVersionUID = -6793564243944283948L;

  @Override
  public Object getDisplayValue(final TipoUtenzaEnum obj) {
    TipoUtenzaEnum c = obj;
    return c.getDescrizione();
  }

  @Override
  public String getIdValue(final TipoUtenzaEnum obj, final int index) {
    TipoUtenzaEnum c = obj;
    if (c != null) return String.valueOf(c.getId());
    else return "";
  }
}
