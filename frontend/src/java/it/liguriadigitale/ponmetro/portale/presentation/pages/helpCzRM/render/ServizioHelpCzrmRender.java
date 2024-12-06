package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.render;

import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmServizi;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ServizioHelpCzrmRender implements IChoiceRenderer<CzrmServizi> {

  private static final long serialVersionUID = -8469269544110602173L;

  @Override
  public Object getDisplayValue(CzrmServizi obj) {
    CzrmServizi c = obj;
    return c.getServizioText();
  }

  @Override
  public String getIdValue(CzrmServizi obj, int index) {
    CzrmServizi c = obj;
    if ((c != null) && (c.getServizioValue() != null)) return String.valueOf(c.getServizioValue());
    else return "";
  }

  @Override
  public CzrmServizi getObject(String id, IModel<? extends List<? extends CzrmServizi>> lista) {
    for (CzrmServizi dati : lista.getObject()) {
      if (dati != null
          && dati.getServizioValue() != null
          && (id.equalsIgnoreCase(dati.getServizioValue()))) {
        return dati;
      }
    }
    return null;
  }
}
