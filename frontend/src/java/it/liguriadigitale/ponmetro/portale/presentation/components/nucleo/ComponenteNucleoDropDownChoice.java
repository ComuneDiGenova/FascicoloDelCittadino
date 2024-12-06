package it.liguriadigitale.ponmetro.portale.presentation.components.nucleo;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ComponenteNucleoDropDownChoice<T> extends FdCDropDownChoice<T> {

  private static final long serialVersionUID = -8982892898130020752L;

  protected static Log log = LogFactory.getLog(ComponenteNucleoDropDownChoice.class);

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ComponenteNucleoDropDownChoice(
      String id, IModel modello, List<ComponenteNucleoAmt> listaNucleoFamiliare) {
    super(id);

    this.render = new ComponenteNucleoAmtRender();

    setModel(modello);
    setChoiceRenderer(render);
    setNullValid(false);

    setRequired(true);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Scegli il componente del nucleo familiare"));

    setChoices(new ComboLoadableDetachableModel(listaNucleoFamiliare));
  }
}
