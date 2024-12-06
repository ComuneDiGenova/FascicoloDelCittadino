package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.util.lang.Classes;

public class TrueFalseRenderer extends ChoiceRenderer<Boolean> {

  private static final long serialVersionUID = -4739959724551237817L;

  private Component resourceSource;

  public TrueFalseRenderer(Component resourceSource) {
    super();
    this.resourceSource = resourceSource;
  }

  @Override
  public Object getDisplayValue(final Boolean object) {
    String key = resourceKey(object);

    if (resourceSource != null) {
      return resourceSource.getString(key);
    } else {
      return Application.get().getResourceSettings().getLocalizer().getString(key, null);
    }
  }

  @Override
  public String getIdValue(final Boolean obj, final int index) {
    return obj.toString();
  }

  protected String resourceKey(Boolean object) {
    return Classes.simpleName(resourceSource.getClass()) + '.' + object;
  }
}
