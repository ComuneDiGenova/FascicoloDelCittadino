package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import it.liguriadigitale.framework.presentation.components.behavior.EnvelopeStyleBehavior;
import it.liguriadigitale.framework.presentation.components.form.components.interfaces.InvalidForceable;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.model.IModel;

public class CampoCasellaControlloFlag extends CheckGroup<Boolean> implements InvalidForceable {

  private static final long serialVersionUID = 620587439082551355L;

  private boolean invalidForced;

  public CampoCasellaControlloFlag(String nomeCampo, IModel<Boolean> model) {
    super(nomeCampo);
    this.setInvalidForced(false);
    this.setOutputMarkupId(true);
    this.add(new EnvelopeStyleBehavior());
  }

  public boolean isInvalidForced() {
    return invalidForced;
  }

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
