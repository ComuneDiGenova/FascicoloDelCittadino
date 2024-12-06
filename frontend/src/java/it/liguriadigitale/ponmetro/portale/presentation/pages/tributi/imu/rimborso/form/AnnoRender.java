package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class AnnoRender implements IChoiceRenderer<Integer> {

  private static final long serialVersionUID = 1898798746315L;

  @Override
  public Object getDisplayValue(Integer value) {
    // TODO Auto-generated method stub
    return String.valueOf(value);
  }

  @Override
  public String getIdValue(Integer value, int arg1) {
    // TODO Auto-generated method stub
    return String.valueOf(value);
  }

  @Override
  public Integer getObject(String id, IModel<? extends List<? extends Integer>> lista) {
    // TODO Auto-generated method stub
    for (Integer dati : lista.getObject()) {
      if (LabelFdCUtil.checkIfNotNull(dati)) if (dati.equals(Integer.valueOf(id))) return dati;
    }

    return null;
  }
}
