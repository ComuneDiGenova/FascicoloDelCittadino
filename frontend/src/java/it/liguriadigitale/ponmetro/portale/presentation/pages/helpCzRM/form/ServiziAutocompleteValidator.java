package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form;

import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmServizi;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class ServiziAutocompleteValidator implements IValidator<CzrmServizi> {
  private static final long serialVersionUID = -1654548776531354L;

  @Override
  public void validate(IValidatable<CzrmServizi> value) {
    if (LabelFdCUtil.checkIfNull(value.getValue())) {
      error(value, "servizioNotSelected");
    }
  }

  private void error(IValidatable<CzrmServizi> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }
}
