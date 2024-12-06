package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.ponmetro.borsestudio.model.Scuola;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ScuolaBorseStudioRender implements IChoiceRenderer<Scuola> {

  private static final long serialVersionUID = 51672718080133832L;

  @Override
  public Object getDisplayValue(final Scuola obj) {
    Scuola c = obj;
    return c.getDescrizione();
  }

  @Override
  public String getIdValue(final Scuola obj, final int index) {
    Scuola c = obj;
    if ((c != null) && (c.getCodice() != null)) return String.valueOf(c.getCodice());
    else return "";
  }

  @Override
  public Scuola getObject(String id, IModel<? extends List<? extends Scuola>> lista) {
    for (Scuola dati : lista.getObject()) {
      if (dati != null && dati.getCodice() != null && (id.equalsIgnoreCase(dati.getCodice()))) {
        return dati;
      }
    }
    return null;
  }
}
