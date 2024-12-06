package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form;

import it.liguriadigitale.ponmetro.portale.pojo.richiestaassistenza.SottoCategoria;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class ServizioAssistenzaValidator implements IValidator<SottoCategoria> {

  @Override
  public void validate(IValidatable<SottoCategoria> value) {
    if (LabelFdCUtil.checkIfNull(value.getValue())) {
      error(value, "servizioNotSelected");
    }
  }

  private void error(IValidatable<SottoCategoria> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }
}
