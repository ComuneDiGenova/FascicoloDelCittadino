package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ComponenteNucleoAnagraficoDropDownChoice extends FdCDropDownChoice<Residente> {

  private IChoiceRenderer render;

  public ComponenteNucleoAnagraficoDropDownChoice(
      String id, IModel componente, List<Residente> lista) {
    super(id);

    setChoices(lista);
    setModel(componente);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new ComponenteNucleoAnagrafeRender();
    setChoiceRenderer(render);
  }
}
