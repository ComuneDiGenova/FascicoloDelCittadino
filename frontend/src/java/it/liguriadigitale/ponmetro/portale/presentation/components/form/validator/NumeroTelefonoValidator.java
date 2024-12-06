package it.liguriadigitale.ponmetro.portale.presentation.components.form.validator;

import org.apache.wicket.validation.validator.PatternValidator;

public class NumeroTelefonoValidator extends PatternValidator {

  private static final long serialVersionUID = 4426731177961817750L;

  public NumeroTelefonoValidator() {
    super("^[0-9]{9,10}$");
  }
}
