package it.liguriadigitale.ponmetro.portale.presentation.components.tarieng;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.EnteBeneficiarioRimborsoEnum;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.model.IModel;

public class EnteBeneficiarioRimborsoTariEngDropDownChoice
    extends FdCDropDownChoice<EnteBeneficiarioRimborsoEnum> {

  private static final long serialVersionUID = -972798735622158405L;

  public EnteBeneficiarioRimborsoTariEngDropDownChoice(
      String id, IModel<EnteBeneficiarioRimborsoEnum> model) {
    super(id);

    setChoices(getListaEnti());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  private static List<EnteBeneficiarioRimborsoEnum> getListaEnti() {
    return Arrays.asList(EnteBeneficiarioRimborsoEnum.values());
  }
}
