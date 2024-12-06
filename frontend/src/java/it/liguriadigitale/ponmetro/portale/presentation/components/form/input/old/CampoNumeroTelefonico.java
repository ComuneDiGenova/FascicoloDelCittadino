package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import it.liguriadigitale.framework.presentation.components.form.components.validators.NumeroTelefonicoValidator;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;

public class CampoNumeroTelefonico extends CampoGenerico<String> {

  private static final long serialVersionUID = 4851472691238517416L;

  public CampoNumeroTelefonico(String nomeCampo) {
    super(nomeCampo, 16);
    this.add(new NumeroTelefonicoValidator());
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
