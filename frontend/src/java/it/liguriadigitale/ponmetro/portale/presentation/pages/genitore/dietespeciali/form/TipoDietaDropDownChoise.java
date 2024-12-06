package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.TipoDietaRender;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaRichiestaDietaSpeciale.TipoDietaSpecialeEnum;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class TipoDietaDropDownChoise extends FdCDropDownChoice<TipoDietaSpecialeEnum> {

  private static final long serialVersionUID = 2303079613360174187L;

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  @SuppressWarnings("unchecked")
  public TipoDietaDropDownChoise(
      String id, IModel<TipoDietaSpecialeEnum> model, boolean isRequired) {

    super(id);

    setChoices(getListaTipi());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setRequired(isRequired);

    this.render = new TipoDietaRender();

    setChoiceRenderer(render);
  }

  private static List<TipoDietaSpecialeEnum> getListaTipi() {
    // TODO Auto-generated method stub
    return Arrays.asList(TipoDietaSpecialeEnum.values());
  }
}
