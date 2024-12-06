package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.ponmetro.borsestudio.model.AnnoScolastico;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class AnnoScolasticoBorseStudioRender implements IChoiceRenderer<AnnoScolastico> {

  private static final long serialVersionUID = 713273211181851359L;

  @Override
  public Object getDisplayValue(final AnnoScolastico obj) {
    AnnoScolastico c = obj;
    return c.getDescrizione();
  }

  @Override
  public String getIdValue(final AnnoScolastico obj, final int index) {
    AnnoScolastico c = obj;
    if ((c != null) && (c.getCodice() != null)) return String.valueOf(c.getCodice());
    else return "";
  }

  @Override
  public AnnoScolastico getObject(
      String id, IModel<? extends List<? extends AnnoScolastico>> lista) {
    for (AnnoScolastico dati : lista.getObject()) {
      if (dati != null
          && dati.getCodice() != null
          && (id.equalsIgnoreCase(String.valueOf(dati.getCodice())))) {
        return dati;
      }
    }
    return null;
  }
}
