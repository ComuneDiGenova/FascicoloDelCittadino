package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.ponmetro.borsestudio.model.Comune;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ComuneBorseStudioRender implements IChoiceRenderer<Comune> {

  private static final long serialVersionUID = 648632094957053902L;

  @Override
  public Object getDisplayValue(final Comune obj) {
    Comune c = obj;
    return c.getDescrizione();
  }

  @Override
  public String getIdValue(final Comune obj, final int index) {
    Comune c = obj;
    if ((c != null) && (c.getCodice() != null)) return c.getCodice();
    else return "";
  }

  @Override
  public Comune getObject(String id, IModel<? extends List<? extends Comune>> lista) {
    for (Comune dati : lista.getObject()) {
      if (dati != null && dati.getCodice() != null && (id.equalsIgnoreCase(dati.getCodice()))) {
        return dati;
      }
    }
    return null;
  }
}
