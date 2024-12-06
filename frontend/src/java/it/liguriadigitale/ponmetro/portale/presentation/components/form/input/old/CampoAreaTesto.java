package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import it.liguriadigitale.framework.presentation.components.behavior.ComponentStyleBehavior;
import it.liguriadigitale.framework.presentation.components.form.components.interfaces.InvalidForceable;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.form.TextArea;

public class CampoAreaTesto<T> extends TextArea<T> implements InvalidForceable {

  private static final long serialVersionUID = 8888746019612407584L;

  private boolean invalidForced;

  public CampoAreaTesto(String id) {
    super(id);
    this.add(new ComponentStyleBehavior());
    this.setOutputMarkupId(true);
    this.setInvalidForced(false);
  }

  @Override
  public boolean isInvalidForced() {
    return invalidForced;
  }

  @Override
  public void setInvalidForced(boolean invalidForced) {
    this.invalidForced = invalidForced;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);

    StringBuilder sb = new StringBuilder();
    if (isRequired()) {
      sb.append(
          "$(document).ready(function() { \r\n"
              + "$('#"
              + getMarkupId()
              + "').rules( \"add\", { required: true}) ;});");
    }
    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }
}
