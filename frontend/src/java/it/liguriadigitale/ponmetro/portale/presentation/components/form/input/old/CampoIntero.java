package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.RangeValidator;

public class CampoIntero extends CampoGenerico<Integer> {

  private static final long serialVersionUID = 8336904678972121288L;

  public CampoIntero(String nomeCampo) {
    super(nomeCampo);
  }

  public CampoIntero(String nomeCampo, IModel<Integer> model) {
    super(nomeCampo, model);
  }

  public CampoIntero(String nomeCampo, Long valoreMinimo, Long valoreMassimo) {
    this(nomeCampo);
    this.add(new RangeValidator<Long>(valoreMinimo, valoreMassimo));
  }

  public CampoIntero(
      String nomeCampo, Long valoreMinimo, Long valoreMassimo, IModel<Integer> model) {
    this(nomeCampo, model);
    this.add(new RangeValidator<Long>(valoreMinimo, valoreMassimo));
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
