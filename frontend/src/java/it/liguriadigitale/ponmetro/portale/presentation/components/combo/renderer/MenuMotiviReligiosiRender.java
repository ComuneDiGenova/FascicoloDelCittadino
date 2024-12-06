package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DietaMotiviEticoReligiosi;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class MenuMotiviReligiosiRender implements IChoiceRenderer<DietaMotiviEticoReligiosi> {

  private static final long serialVersionUID = -3625340648685970261L;

  @Override
  public Object getDisplayValue(DietaMotiviEticoReligiosi obj) {
    DietaMotiviEticoReligiosi dieta = obj;
    if (LabelFdCUtil.checkIfNotNull(dieta) && LabelFdCUtil.checkIfNotNull(dieta.getDescrizione())) {
      return dieta.getDescrizione();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(DietaMotiviEticoReligiosi obj, final int index) {
    DietaMotiviEticoReligiosi dieta = obj;
    if (LabelFdCUtil.checkIfNotNull(dieta) && LabelFdCUtil.checkIfNotNull(dieta.getCodice())) {
      return String.valueOf(dieta.getCodice());
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
