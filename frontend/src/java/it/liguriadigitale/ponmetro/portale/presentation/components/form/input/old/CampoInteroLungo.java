package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.validation.validator.RangeValidator;

public class CampoInteroLungo extends CampoGenerico<Long> {

  private static final long serialVersionUID = 5344704210239168218L;

  public CampoInteroLungo(String nomeCampo) {
    super(nomeCampo);
  }

  public CampoInteroLungo(String nomeCampo, Long valoreMinimo, Long valoreMassimo) {
    this(nomeCampo);
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
