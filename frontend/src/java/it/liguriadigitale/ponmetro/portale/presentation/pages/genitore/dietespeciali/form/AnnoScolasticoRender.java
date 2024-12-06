package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AnnoScolastico;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class AnnoScolasticoRender implements IChoiceRenderer<AnnoScolastico> {

  private static final long serialVersionUID = -4555256142238420122L;

  @Override
  public Object getDisplayValue(AnnoScolastico obj) {
    AnnoScolastico item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getDescrizione())) {
      return item.getDescrizione();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(AnnoScolastico obj, int index) {
    AnnoScolastico item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getCodice())) {
      return String.valueOf(item.getCodice());
    } else {
      return "";
    }
  }

  @Override
  public AnnoScolastico getObject(
      String id, IModel<? extends List<? extends AnnoScolastico>> lista) {
    for (AnnoScolastico dati : lista.getObject()) {
      if (LabelFdCUtil.checkIfNotNull(dati) && LabelFdCUtil.checkIfNotNull(dati.getCodice())) {
        if (String.valueOf(dati.getCodice()).equalsIgnoreCase(id)) return dati;
      }
    }

    return null;
  }
}
