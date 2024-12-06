package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import it.liguriadigitale.framework.presentation.components.behavior.EnvelopeStyleBehavior;
import it.liguriadigitale.framework.presentation.components.form.components.interfaces.InvalidForceable;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.IModel;

public class CampoCasellaControlloSingola extends CheckBox implements InvalidForceable {

  private static final long serialVersionUID = 620587439082551355L;
  private boolean invalidForced;

  public CampoCasellaControlloSingola(String nomeCampo, IModel<Boolean> model) {
    super(nomeCampo, model);
    this.setInvalidForced(false);
    this.setOutputMarkupId(true);
    this.add(new Behavior[] {new EnvelopeStyleBehavior()});

    // don't double encode the value. it is encoded by ComponentTag.writeOutput()
    // setEscapeModelStrings(false);
  }

  @Override
  public boolean isInvalidForced() {
    return this.invalidForced;
  }

  @Override
  public void setInvalidForced(boolean arg0) {
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
