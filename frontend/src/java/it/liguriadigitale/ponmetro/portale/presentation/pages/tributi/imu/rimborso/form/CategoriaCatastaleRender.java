package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form;

import it.liguriadigitale.ponmetro.portale.pojo.imu.CategoriaCatastale;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class CategoriaCatastaleRender implements IChoiceRenderer<CategoriaCatastale> {

  private static final long serialVersionUID = -1879874654648L;

  @Override
  public Object getDisplayValue(CategoriaCatastale value) {
    // TODO Auto-generated method stub
    return value.getCodice() + "-" + value.getDescrizione();
  }

  @Override
  public String getIdValue(CategoriaCatastale value, int index) {
    // TODO Auto-generated method stub
    return value.getCodice();
  }

  @Override
  public CategoriaCatastale getObject(
      String id, IModel<? extends List<? extends CategoriaCatastale>> model) {
    @SuppressWarnings("unchecked")
    List<CategoriaCatastale> list = (List<CategoriaCatastale>) model.getObject();

    if (!list.stream().anyMatch(x -> x.getCodice().equals(id))) {
      return null;
    }

    return list.stream().filter(x -> x.getCodice().equals(id)).findFirst().get();
  }
}
