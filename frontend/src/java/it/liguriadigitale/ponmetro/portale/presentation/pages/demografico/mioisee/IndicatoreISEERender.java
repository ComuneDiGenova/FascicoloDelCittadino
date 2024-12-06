package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mioisee;

import it.liguriadigitale.ponmetro.portale.pojo.enums.IndicatoreISEEEnum;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class IndicatoreISEERender extends ChoiceRenderer<IndicatoreISEEEnum> {

  private static final long serialVersionUID = -6793564243944283948L;

  @Override
  public Object getDisplayValue(final IndicatoreISEEEnum obj) {
    IndicatoreISEEEnum c = obj;
    return c.getDescrizione();
  }

  @Override
  public String getIdValue(final IndicatoreISEEEnum obj, final int index) {
    IndicatoreISEEEnum c = obj;
    if (c != null) return String.valueOf(c.getId());
    else return "";
  }
}
