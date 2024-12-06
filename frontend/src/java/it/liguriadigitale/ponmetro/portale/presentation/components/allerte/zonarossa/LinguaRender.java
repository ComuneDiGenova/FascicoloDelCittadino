package it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Lingua;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class LinguaRender implements IChoiceRenderer<Lingua> {

  private static final long serialVersionUID = -4827448113386047926L;

  @Override
  public Object getDisplayValue(Lingua obj) {
    Lingua item = obj;
    String descrizione = item.getDescrizione();
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(descrizione)) {
      return descrizione;
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(Lingua obj, final int index) {
    Lingua item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getIdLingua())) {
      return String.valueOf(item.getIdLingua());
    } else {
      return "";
    }
  }

  @Override
  public Lingua getObject(String id, IModel<? extends List<? extends Lingua>> lista) {
    for (Lingua dati : lista.getObject()) {
      if (LabelFdCUtil.checkIfNotNull(dati))
        if (dati.getIdLingua().equalsIgnoreCase(id)) return dati;
    }

    return null;
  }
}
