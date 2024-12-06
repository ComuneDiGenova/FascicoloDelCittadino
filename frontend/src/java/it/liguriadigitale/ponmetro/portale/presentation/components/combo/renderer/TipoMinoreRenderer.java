package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.api.pojo.enums.AutocertificazioneTipoMinoreEnum;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class TipoMinoreRenderer extends ChoiceRenderer<AutocertificazioneTipoMinoreEnum> {

  private static final long serialVersionUID = 6103052906421386197L;

  @Override
  public Object getDisplayValue(final AutocertificazioneTipoMinoreEnum obj) {
    AutocertificazioneTipoMinoreEnum c = obj;
    return c.getDescription();
  }

  @Override
  public String getIdValue(final AutocertificazioneTipoMinoreEnum obj, final int index) {
    AutocertificazioneTipoMinoreEnum c = obj;
    if ((c != null) && (c.value() != null)) return c.value();
    else return "";
  }
}
