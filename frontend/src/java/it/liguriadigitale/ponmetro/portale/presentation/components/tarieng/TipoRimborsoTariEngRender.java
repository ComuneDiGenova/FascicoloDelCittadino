package it.liguriadigitale.ponmetro.portale.presentation.components.tarieng;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipoRimborsoEnum;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class TipoRimborsoTariEngRender implements IChoiceRenderer<TipoRimborsoEnum> {

  private static final long serialVersionUID = 9113581571719465071L;

  @Override
  public Object getDisplayValue(TipoRimborsoEnum obj) {
    TipoRimborsoEnum item = obj;
    String descrizione = "";
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(descrizione)) {

      if (item.equals(TipoRimborsoEnum.ECCEDENZADARESIDUONEGATIVO)) {
        // descrizione = "Eccedenza da residuo negativo";
        descrizione = "Eccedenza da altri pagamenti";
      }

      if (item.equals(TipoRimborsoEnum.ECCEDENZAREALE)) {
        // descrizione = "Eccedenza reale";
        descrizione = "TARI";
      }

      return descrizione;
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(TipoRimborsoEnum obj, final int index) {
    TipoRimborsoEnum item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.value())) {
      return String.valueOf(item.value());
    } else {
      return "";
    }
  }

  @Override
  public TipoRimborsoEnum getObject(
      String id, IModel<? extends List<? extends TipoRimborsoEnum>> lista) {
    for (TipoRimborsoEnum dati : lista.getObject()) {
      if (LabelFdCUtil.checkIfNotNull(dati)) if (dati.value().equalsIgnoreCase(id)) return dati;
    }

    return null;
  }
}
