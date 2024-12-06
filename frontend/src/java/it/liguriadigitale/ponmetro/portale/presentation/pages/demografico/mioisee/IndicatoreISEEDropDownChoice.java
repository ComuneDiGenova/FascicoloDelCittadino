package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mioisee;

import it.liguriadigitale.ponmetro.portale.pojo.enums.IndicatoreISEEEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.model.IModel;

public class IndicatoreISEEDropDownChoice extends FdCDropDownChoice<IndicatoreISEEEnum> {

  private static final long serialVersionUID = -6513394163934828041L;

  public IndicatoreISEEDropDownChoice(String id, IModel<IndicatoreISEEEnum> model) {
    super(id);

    setChoices(getListaTipiUtenza());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    setChoiceRenderer(new IndicatoreISEERender());
  }

  private static List<IndicatoreISEEEnum> getListaTipiUtenza() {
    return Arrays.asList(IndicatoreISEEEnum.values());
  }
}
