package it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Indirizzo.PosizioneEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.model.IModel;

public class PosizioneDropDownChoice extends FdCDropDownChoice<PosizioneEnum> {

  private static final long serialVersionUID = 5619052129744478598L;

  public PosizioneDropDownChoice(String id, IModel<PosizioneEnum> model) {
    super(id);

    setChoices(getListaPosizione());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  private static List<PosizioneEnum> getListaPosizione() {
    return Arrays.asList(PosizioneEnum.values());
  }
}
