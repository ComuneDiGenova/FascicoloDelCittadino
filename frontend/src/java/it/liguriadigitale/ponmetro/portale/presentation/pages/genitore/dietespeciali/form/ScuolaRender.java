package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIstituto;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ScuolaRender implements IChoiceRenderer<DatiIstituto> {

  private static final long serialVersionUID = 1L;

  @Override
  public Object getDisplayValue(DatiIstituto obj) {
    DatiIstituto item = obj;
    return LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getDescScuola())
        ? concatenaDatiIstuto(obj)
        : "";
  }

  @Override
  public String getIdValue(DatiIstituto obj, int index) {
    DatiIstituto item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getCodScuola())) {
      return String.valueOf(item.getCodScuola());
    } else {
      return "";
    }
  }

  @Override
  public DatiIstituto getObject(String id, IModel<? extends List<? extends DatiIstituto>> lista) {
    for (DatiIstituto dati : lista.getObject()) {
      if (LabelFdCUtil.checkIfNotNull(dati))
        if (dati.getCodScuola().equalsIgnoreCase(id)) return dati;
    }

    return null;
  }

  private static String concatenaDatiIstuto(DatiIstituto istituto) {
    StringBuilder builder = new StringBuilder();

    builder
        .append(LabelFdCUtil.defaultString(istituto.getDescScuola()))
        .append("/ ")
        .append(LabelFdCUtil.defaultString(istituto.getCategoria()));

    if (PageUtil.isStringValid(istituto.getIndirizzo())) {
      builder.append("/ ").append(istituto.getIndirizzo().trim());
    }

    return builder.toString();
  }
}
