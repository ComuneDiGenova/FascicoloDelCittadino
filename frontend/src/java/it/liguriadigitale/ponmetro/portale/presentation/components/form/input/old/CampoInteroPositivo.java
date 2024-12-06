package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import it.liguriadigitale.framework.presentation.components.form.components.types.CampoGenerico;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.RangeValidator;

/**
 * Componente per campo intero positivo HTML 5 compatibile Attenzione: nell'html non inserire nessun
 * tag di tipo type (es. type="text",type="number"...).
 *
 * @author fiorito
 */
public class CampoInteroPositivo extends CampoGenerico<Integer> {

  private static final long serialVersionUID = 8336904678972121288L;

  public CampoInteroPositivo(String nomeCampo) {
    super(nomeCampo);
  }

  public CampoInteroPositivo(String nomeCampo, IModel<Integer> model) {
    super(nomeCampo, model);
  }

  public CampoInteroPositivo(String nomeCampo, Long valoreMinimo, Long valoreMassimo) {
    this(nomeCampo);
    this.add(new RangeValidator<Long>(valoreMinimo, valoreMassimo));
  }

  public CampoInteroPositivo(
      String nomeCampo, Long valoreMinimo, Long valoreMassimo, IModel<Integer> model) {
    this(nomeCampo, model);
    this.add(new RangeValidator<Long>(valoreMinimo, valoreMassimo));
  }

  @Override
  protected void onComponentTag(final ComponentTag tag) {
    super.onComponentTag(tag);
    tag.put("min", "0");
    tag.put("step", "1");
    tag.put("type", "number");
    tag.put("pattern", "\\d+");
    tag.put(
        "onkeypress",
        "return (event.charCode == 8 || event.charCode == 0 || event.charCode == 13) ? null : event.charCode >= 48 && event.charCode <= 57");
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
