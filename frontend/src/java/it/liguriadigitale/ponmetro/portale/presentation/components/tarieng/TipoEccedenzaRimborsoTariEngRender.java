package it.liguriadigitale.ponmetro.portale.presentation.components.tarieng;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class TipoEccedenzaRimborsoTariEngRender
    implements IChoiceRenderer<TipoEccedenzaRimborsoTariEng> {

  private static final long serialVersionUID = -8648516319472829933L;

  @Override
  public Object getDisplayValue(TipoEccedenzaRimborsoTariEng obj) {
    TipoEccedenzaRimborsoTariEng item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getDescrizione())) {
      return item.getDescrizione();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(TipoEccedenzaRimborsoTariEng obj, final int index) {
    TipoEccedenzaRimborsoTariEng item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getId())) {
      return String.valueOf(item.getId());
    } else {
      return "";
    }
  }

  @Override
  public TipoEccedenzaRimborsoTariEng getObject(
      String id, IModel<? extends List<? extends TipoEccedenzaRimborsoTariEng>> lista) {
    for (TipoEccedenzaRimborsoTariEng dati : lista.getObject()) {
      if (LabelFdCUtil.checkIfNotNull(dati))
        if (String.valueOf(dati.getId()).equalsIgnoreCase(id)) return dati;
    }

    return null;
  }
}
