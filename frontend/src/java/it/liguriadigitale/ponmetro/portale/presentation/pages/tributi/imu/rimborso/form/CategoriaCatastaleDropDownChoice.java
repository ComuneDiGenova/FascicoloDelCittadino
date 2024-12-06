package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form;

import it.liguriadigitale.ponmetro.portale.pojo.imu.CategoriaCatastale;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel.RichiestaRimborsoImuHelper;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class CategoriaCatastaleDropDownChoice extends DropDownChoice<CategoriaCatastale> {
  private static final long serialVersionUID = -164564653213215489L;

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  @SuppressWarnings("unchecked")
  public CategoriaCatastaleDropDownChoice(String id, IModel<CategoriaCatastale> model) {
    super(id);
    // TODO Auto-generated constructor stub

    setOutputMarkupId(true);
    setChoices(RichiestaRimborsoImuHelper.getCategorieCatastali());
    setModel(model);
    setOutputMarkupPlaceholderTag(true);

    render = new CategoriaCatastaleRender();

    setChoiceRenderer(render);
  }
}
