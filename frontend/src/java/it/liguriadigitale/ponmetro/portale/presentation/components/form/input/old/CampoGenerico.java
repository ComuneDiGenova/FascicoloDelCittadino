package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import it.liguriadigitale.framework.presentation.components.behavior.ComponentStyleBehavior;
import it.liguriadigitale.framework.presentation.components.form.components.interfaces.InvalidForceable;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

public class CampoGenerico<T> extends TextField<T> implements InvalidForceable {

  private static final long serialVersionUID = 1303973879565241917L;

  private boolean invalidForced;

  public CampoGenerico(String nomeCampo) {
    super(nomeCampo);
    this.add(new ComponentStyleBehavior());
    this.setOutputMarkupId(true);
    this.setInvalidForced(false);
  }

  public CampoGenerico(String nomeCampo, final IModel<T> model) {
    super(nomeCampo, model);
    this.add(new ComponentStyleBehavior());
    this.setOutputMarkupId(true);
    this.setInvalidForced(false);
  }

  public CampoGenerico(String nomeCampo, Integer lunghezzaFissa) {
    this(nomeCampo);
  }

  public CampoGenerico(String nomeCampo, Integer lunghezzaMinima, Integer lunghezzaMassima) {
    this(nomeCampo);
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
