package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class AnnoScolasticoRenderer extends ChoiceRenderer<Integer> {

  private static final long serialVersionUID = 6103052906421386197L;

  @Override
  public Object getDisplayValue(final Integer anno) {
    Integer annoSeguente = anno + 1;
    return anno.toString() + "/" + annoSeguente;
  }

  @Override
  public String getIdValue(final Integer obj, final int index) {
    Integer c = obj;
    return c.toString();
  }
}
