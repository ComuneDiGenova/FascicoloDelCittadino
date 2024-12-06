package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.prenotazione.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.CartoLibreriaDropDownChoice;
import org.apache.wicket.model.PropertyModel;

public class PrenotazioneCedolaForm extends AbstracFrameworkForm<Prenotazione> {

  private static final long serialVersionUID = 7384393816597461769L;

  public PrenotazioneCedolaForm(String id, Prenotazione model) {
    super(id, model);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void addElementiForm() {

    Prenotazione modello = getModelObject();
    CartoLibreriaDropDownChoice comboCartolibreria =
        new CartoLibreriaDropDownChoice(
            "comboCartolibreria", new PropertyModel(modello, "comboCartolibreria"));
    add(comboCartolibreria);
  }
}
