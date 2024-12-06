package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DietaMotiviEticoReligiosi;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class MenuRender implements IChoiceRenderer<DietaMotiviEticoReligiosi> {

  private static final long serialVersionUID = 96075089909277785L;

  @Override
  public Object getDisplayValue(DietaMotiviEticoReligiosi obj) {
    DietaMotiviEticoReligiosi item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getDescrizione())) {
      return item.getDescrizione();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(DietaMotiviEticoReligiosi obj, final int index) {
    DietaMotiviEticoReligiosi item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getCodice())) {
      return String.valueOf(item.getCodice());
    } else {
      return "";
    }
  }

  @Override
  public DietaMotiviEticoReligiosi getObject(
      String id, IModel<? extends List<? extends DietaMotiviEticoReligiosi>> lista) {
    for (DietaMotiviEticoReligiosi dati : lista.getObject()) {
      if (LabelFdCUtil.checkIfNotNull(dati)) if (dati.getCodice().equalsIgnoreCase(id)) return dati;
    }

    return null;
  }
}
