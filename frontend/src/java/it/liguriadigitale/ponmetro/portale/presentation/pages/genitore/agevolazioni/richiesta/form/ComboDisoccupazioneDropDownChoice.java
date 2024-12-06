package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.form;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.NucleoFamiliareComponenteNucleoInnerRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ComboDisoccupazioneDropDownChoice<T> extends FdCDropDownChoice<T> {

  private static final long serialVersionUID = 6227197185578449030L;

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  //	public ComboDisoccupazioneDropDownChoice(String id, IModel modello,
  //			List<ComponenteNucleo> listaPortatoriDiReddito) {
  public ComboDisoccupazioneDropDownChoice(
      String id,
      IModel modello,
      List<NucleoFamiliareComponenteNucleoInner> listaPortatoriDiReddito) {
    super(id);

    // this.render = new ComponenteNucleoRenderer();
    this.render = new NucleoFamiliareComponenteNucleoInnerRenderer();

    setModel(modello);
    setChoiceRenderer(render);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Seleziona genitore"));

    setChoices(new ComboLoadableDetachableModel(listaPortatoriDiReddito));
  }
}
