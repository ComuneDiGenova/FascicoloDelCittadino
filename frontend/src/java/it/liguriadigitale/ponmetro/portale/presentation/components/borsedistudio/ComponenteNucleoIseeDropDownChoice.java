package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ComponenteNucleoIseeDropDownChoice
    extends FdCDropDownChoice<NucleoFamiliareComponenteNucleoInner> {

  private static final long serialVersionUID = 3536562928669706702L;

  private IChoiceRenderer render;

  public ComponenteNucleoIseeDropDownChoice(
      String id,
      IModel componenteNucleo,
      List<NucleoFamiliareComponenteNucleoInner> listaComponenteNucleoIsee) {
    super(id);

    setChoices(listaComponenteNucleoIsee);
    setModel(componenteNucleo);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new ComponenteNucleoIseeRender();
    setChoiceRenderer(render);
  }
}
