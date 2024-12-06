package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import it.liguriadigitale.framework.presentation.components.behavior.EnvelopeStyleBehavior;
import it.liguriadigitale.framework.presentation.components.form.components.interfaces.InvalidForceable;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.IModel;

public class CampoSceltaSingola extends RadioGroup<String> implements InvalidForceable {

  private static final long serialVersionUID = 4614014612564936883L;

  private boolean invalidForced;

  public CampoSceltaSingola(String nomeCampo, IModel<String> model) {
    super(nomeCampo, model);
    this.setInvalidForced(false);
    this.setOutputMarkupId(true);
    this.add(new EnvelopeStyleBehavior());
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
