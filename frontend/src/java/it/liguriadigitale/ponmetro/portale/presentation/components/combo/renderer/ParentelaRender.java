package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Parentela;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ParentelaRender implements IChoiceRenderer<Parentela> {

  private static final long serialVersionUID = -7521795121565776233L;

  @Override
  public Object getDisplayValue(Parentela obj) {
    Parentela item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getDescrizione())) {
      return item.getDescrizione();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(Parentela obj, final int index) {
    Parentela item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getCodice())) {
      return String.valueOf(item.getCodice());
    } else {
      return "";
    }
  }

  @Override
  public Parentela getObject(String id, IModel<? extends List<? extends Parentela>> lista) {
    for (Parentela dati : lista.getObject()) {
      if (LabelFdCUtil.checkIfNotNull(dati)) if (dati.getCodice().equalsIgnoreCase(id)) return dati;
    }

    return null;
  }
}
