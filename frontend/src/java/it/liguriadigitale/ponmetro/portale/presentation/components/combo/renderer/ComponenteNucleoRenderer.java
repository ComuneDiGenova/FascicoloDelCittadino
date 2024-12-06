package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni.ComponenteNucleo;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class ComponenteNucleoRenderer extends ChoiceRenderer<ComponenteNucleo> {

  private static final long serialVersionUID = 414089100204415675L;

  @Override
  public Object getDisplayValue(final ComponenteNucleo obj) {
    ComponenteNucleo c = obj;
    return c.getCognome() + " " + c.getNome();
  }

  @Override
  public String getIdValue(final ComponenteNucleo obj, final int index) {
    ComponenteNucleo c = obj;
    if (c != null) return String.valueOf(c.getCodiceFiscale());
    else return "";
  }
}
