package it.liguriadigitale.ponmetro.portale.presentation.components.form.validator;

import org.apache.wicket.validation.validator.PatternValidator;

public class NumeroCellulareValidator extends PatternValidator {

  private static final long serialVersionUID = 4426731177961817750L;

  public NumeroCellulareValidator() {
    super("^((00|\\+)39[\\|.\\- ]??)??3\\d{2}[\\|.\\- \\\\]??\\d{6,7}$");
  }
}
