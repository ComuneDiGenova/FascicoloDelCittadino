package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.ponmetro.borsestudio.model.Provincia;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ProvinciaBorseStudioRender implements IChoiceRenderer<Provincia> {

  private static final long serialVersionUID = 798211959095400727L;

  @Override
  public Object getDisplayValue(final Provincia obj) {
    Provincia c = obj;
    return c.getDescrizione();
  }

  @Override
  public String getIdValue(final Provincia obj, final int index) {
    Provincia c = obj;
    if ((c != null) && (c.getCodice() != null)) return c.getCodice();
    else return "";
  }

  @Override
  public Provincia getObject(String id, IModel<? extends List<? extends Provincia>> lista) {
    for (Provincia dati : lista.getObject()) {
      if (dati != null && dati.getCodice() != null && (id.equalsIgnoreCase(dati.getCodice()))) {
        return dati;
      }
    }
    return null;
  }
}
