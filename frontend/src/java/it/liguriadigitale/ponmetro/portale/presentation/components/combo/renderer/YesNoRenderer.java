package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class YesNoRenderer extends ChoiceRenderer<Boolean> {

  private static final long serialVersionUID = -4739959724551237817L;

  private String key;

  public YesNoRenderer(String key) {
    super();
    this.key = key;
  }

  @Override
  public Object getDisplayValue(final Boolean obj) {

    if (obj)
      return Application.get().getResourceSettings().getLocalizer().getString(key + ".si", null);
    else return Application.get().getResourceSettings().getLocalizer().getString(key + ".no", null);
  }

  @Override
  public String getIdValue(final Boolean obj, final int index) {
    return obj.toString();
  }
}
