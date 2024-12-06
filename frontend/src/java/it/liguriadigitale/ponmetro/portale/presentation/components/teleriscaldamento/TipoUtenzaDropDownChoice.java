package it.liguriadigitale.ponmetro.portale.presentation.components.teleriscaldamento;

import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.TipoUtenzaEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class TipoUtenzaDropDownChoice extends FdCDropDownChoice<TipoUtenzaEnum> {

  private static final long serialVersionUID = -6513394163934828041L;

  private IChoiceRenderer render;

  public TipoUtenzaDropDownChoice(String id, IModel<TipoUtenzaEnum> model) {
    super(id);

    setChoices(getListaTipiUtenza());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new TipoUtenzaRender();
    setChoiceRenderer(render);
  }

  private static List<TipoUtenzaEnum> getListaTipiUtenza() {
    return Arrays.asList(TipoUtenzaEnum.values());
  }
}
