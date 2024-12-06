package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.scuola.cedole.model.Cartolibreria;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class CartolibreriaRenderer implements IChoiceRenderer<Cartolibreria> {

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

  @Override
  public Cartolibreria getObject(String id, IModel<? extends List<? extends Cartolibreria>> lista) {
    for (Cartolibreria dati : lista.getObject()) {
      if (dati != null && (id.equalsIgnoreCase(dati.getIdCartolibreria()))) {
        return dati;
      }
    }
    return null;
  }
}
