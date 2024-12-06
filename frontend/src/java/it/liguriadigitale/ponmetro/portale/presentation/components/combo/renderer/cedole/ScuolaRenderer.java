package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.cedole;

import it.liguriadigitale.ponmetro.scuola.cedole.model.Scuola;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class ScuolaRenderer extends ChoiceRenderer<Scuola> {

  private static final long serialVersionUID = 8519661636463330168L;

  @Override
  public Object getDisplayValue(final Scuola obj) {
    Scuola c = obj;
    return c.getDenominazioneScuola();
  }

  @Override
  public String getIdValue(final Scuola obj, final int index) {
    Scuola c = obj;
    if ((c != null) && (c.getIdScuola() != null)) return c.getIdScuola();
    else return "";
  }
}
