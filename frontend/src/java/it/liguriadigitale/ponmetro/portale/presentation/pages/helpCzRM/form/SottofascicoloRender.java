package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form;

import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmSottoFascicoli;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class SottofascicoloRender implements IChoiceRenderer<CzrmSottoFascicoli> {

  private static final long serialVersionUID = -198797976544118L;

  @Override
  public Object getDisplayValue(CzrmSottoFascicoli fascicolo) {
    // TODO Auto-generated method stub
    return fascicolo.sottoFascicoloText;
  }

  @Override
  public String getIdValue(CzrmSottoFascicoli fascicolo, int index) {
    // TODO Auto-generated method stub
    return fascicolo.sottoFascicoloValue;
  }

  @Override
  public CzrmSottoFascicoli getObject(
      String fascicolo, IModel<? extends List<? extends CzrmSottoFascicoli>> model) {
    @SuppressWarnings("unchecked")
    List<CzrmSottoFascicoli> list = (List<CzrmSottoFascicoli>) model.getObject();

    if (!list.stream().anyMatch(x -> x.getSottoFascicoloValue().equals(fascicolo))) {
      return null;
    }

    return list.stream()
        .filter(x -> x.getSottoFascicoloValue().equals(fascicolo))
        .findFirst()
        .get();
  }
}
