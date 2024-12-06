package it.liguriadigitale.ponmetro.portale.presentation.components.tarieng;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipoRimborsoEnum;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class TipoRimborsoTariEngDropDownChoice extends FdCDropDownChoice<TipoRimborsoEnum> {

  private static final long serialVersionUID = 2303079613360174167L;

  private IChoiceRenderer render;

  public TipoRimborsoTariEngDropDownChoice(String id, IModel<TipoRimborsoEnum> model) {
    super(id);

    setChoices(getListaTipi());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new TipoRimborsoTariEngRender();

    setChoiceRenderer(render);
  }

  private static List<TipoRimborsoEnum> getListaTipi() {
    return Arrays.asList(TipoRimborsoEnum.values());
  }
}
