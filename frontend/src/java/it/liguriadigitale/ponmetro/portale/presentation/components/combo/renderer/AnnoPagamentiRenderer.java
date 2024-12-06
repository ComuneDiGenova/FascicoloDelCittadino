package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class AnnoPagamentiRenderer extends ChoiceRenderer<Integer> {

  private static final long serialVersionUID = 1L;

  private static final String PREFISSO_ANNO = "<";
  private static final String SUFFISSO_ANNO = ">";

  @Override
  public Object getDisplayValue(final Integer anno) {
    return anno.toString();
  }

  @Override
  public String getIdValue(final Integer obj, final int index) {
    Integer c = obj;
    return c.toString();
  }
}
