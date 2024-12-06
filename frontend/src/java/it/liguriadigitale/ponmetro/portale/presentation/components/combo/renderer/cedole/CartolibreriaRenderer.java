package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.cedole;

import it.liguriadigitale.ponmetro.scuola.cedole.model.Cartolibreria;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class CartolibreriaRenderer extends ChoiceRenderer<Cartolibreria> {

  private static final long serialVersionUID = 8519661636463330168L;

  @Override
  public Object getDisplayValue(final Cartolibreria obj) {
    Cartolibreria c = obj;
    return c.getDenominazioneCartolibreria();
  }

  @Override
  public String getIdValue(final Cartolibreria obj, final int index) {
    Cartolibreria c = obj;
    if ((c != null) && (c.getIdCartolibreria() != null)) return c.getIdCartolibreria();
    else return "";
  }
}
