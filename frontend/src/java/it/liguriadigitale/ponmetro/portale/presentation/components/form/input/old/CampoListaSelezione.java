package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import it.liguriadigitale.framework.presentation.components.behavior.ComponentStyleBehavior;
import it.liguriadigitale.framework.presentation.components.combo.dropdownchoice.DropDownChoiceDetachable;
import it.liguriadigitale.framework.presentation.components.form.components.interfaces.InvalidForceable;
import java.util.List;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class CampoListaSelezione<T> extends DropDownChoiceDetachable<T>
    implements InvalidForceable {

  private static final long serialVersionUID = -7790965532916965652L;

  private boolean invalidForced;

  public CampoListaSelezione(
      String idWicket, List<T> lista, IChoiceRenderer<T> choiceRenderer, IModel<T> modello) {
    super(idWicket, lista, choiceRenderer, modello);
    this.add(new ComponentStyleBehavior());
    this.setInvalidForced(false);
    super.setNullValid(true);
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
