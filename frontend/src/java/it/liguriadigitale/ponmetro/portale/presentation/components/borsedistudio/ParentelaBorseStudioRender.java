package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.ponmetro.borsestudio.model.Parentela;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ParentelaBorseStudioRender implements IChoiceRenderer<Parentela> {

  private static final long serialVersionUID = -4157101533318134598L;

  @Override
  public Object getDisplayValue(final Parentela obj) {
    Parentela c = obj;
    return c.getDescrizione();
  }

  @Override
  public String getIdValue(final Parentela obj, final int index) {
    Parentela c = obj;
    if ((c != null) && (c.getCodice() != null)) return String.valueOf(c.getCodice());
    else return "";
  }

  @Override
  public Parentela getObject(String id, IModel<? extends List<? extends Parentela>> lista) {
    for (Parentela dati : lista.getObject()) {
      if (dati != null
          && dati.getCodice() != null
          && (id.equalsIgnoreCase(String.valueOf(dati.getCodice())))) {
        return dati;
      }
    }
    return null;
  }
}
