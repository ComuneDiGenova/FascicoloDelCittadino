package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.ListItem;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ListItemRenderer implements IChoiceRenderer<ListItem> {

  private static final int LIMIT_CHAR = 150;
  private static final long serialVersionUID = -3483330902123170036L;

  @Override
  public Object getDisplayValue(ListItem obj) {
    ListItem item = obj;
    String descrizione = item.getDescrizione();
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(descrizione)) {
      if (descrizione.length() > LIMIT_CHAR) {
        return descrizione.substring(0, LIMIT_CHAR - 1) + "...";
      } else {
        return descrizione;
      }

    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(ListItem obj, final int index) {
    ListItem item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getCodice())) {
      return String.valueOf(item.getCodice());
    } else {
      return "";
    }
  }

  @Override
  public ListItem getObject(String id, IModel<? extends List<? extends ListItem>> lista) {
    for (ListItem dati : lista.getObject()) {
      if (LabelFdCUtil.checkIfNotNull(dati)) if (dati.getCodice().equalsIgnoreCase(id)) return dati;
    }

    return null;
  }
}
