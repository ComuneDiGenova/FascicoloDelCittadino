package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class EnumMonthRenderer extends ChoiceRenderer<Month> {

  private static final long serialVersionUID = 2053299065206507198L;

  @Override
  public Object getDisplayValue(final Month obj) {
    Month c = obj;
    return c.getDisplayName(TextStyle.FULL, Locale.ITALIAN);
  }

  @Override
  public String getIdValue(final Month obj, final int index) {
    Month c = obj;
    if (c != null) return String.valueOf(c.getValue());
    else return "";
  }
}
