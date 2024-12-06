package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import it.liguriadigitale.framework.presentation.components.form.components.validators.CodiceFiscaleValidator;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.IModel;

public class CampoCodiceFiscale extends CampoGenerico<String> {

  private static final long serialVersionUID = -8245089323388994982L;

  public CampoCodiceFiscale(String nomeCampo) {
    super(nomeCampo, 16);
    this.add(new CodiceFiscaleValidator());
  }

  public CampoCodiceFiscale(String nomeCampo, IModel<String> model) {
    super(nomeCampo, model);
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
