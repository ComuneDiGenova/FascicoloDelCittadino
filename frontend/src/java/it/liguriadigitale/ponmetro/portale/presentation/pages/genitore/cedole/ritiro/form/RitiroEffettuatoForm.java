package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.ritiro.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.scuola.cedole.model.RitiroCedola;
import java.time.LocalDate;
import org.apache.wicket.validation.validator.RangeValidator;

public class RitiroEffettuatoForm extends AbstracFrameworkForm<RitiroCedola> {

  private static final long serialVersionUID = 7384393816597461769L;

  public RitiroEffettuatoForm(String id, RitiroCedola model) {
    super(id, model);
  }

  @Override
  public void addElementiForm() {

    FdCLocalDateTextfield dataRitiro = new FdCLocalDateTextfield("dataRitiro");
    dataRitiro.setRequired(true);
    dataRitiro.add(RangeValidator.maximum(LocalDate.now()));
    add(dataRitiro);
  }
}
