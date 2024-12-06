package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.ponmetro.borsestudio.model.Categoria;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class CategoriaBorseStudioRender implements IChoiceRenderer<Categoria> {

  private static final long serialVersionUID = 1458701977751916334L;

  @Override
  public Object getDisplayValue(final Categoria obj) {
    Categoria c = obj;
    return c.getDescrizione();
  }

  @Override
  public String getIdValue(final Categoria obj, final int index) {
    Categoria c = obj;
    if ((c != null) && (c.getCodice() != null)) return String.valueOf(c.getCodice());
    else return "";
  }

  @Override
  public Categoria getObject(String id, IModel<? extends List<? extends Categoria>> lista) {
    for (Categoria dati : lista.getObject()) {
      if (dati != null && dati.getCodice() != null && (id.equalsIgnoreCase(dati.getCodice()))) {
        return dati;
      }
    }
    return null;
  }
}
