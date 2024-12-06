package it.liguriadigitale.ponmetro.portale.presentation.util.validator;

import java.util.regex.Pattern;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class CAPValidator implements IValidator<String> {

  private static final long serialVersionUID = -1126695016232797922L;

  public CAPValidator() {
    super();
  }

  @Override
  public void validate(IValidatable<String> capDaValidare) {
    String cap = capDaValidare.getValue();

    final String CAP_PATTERN = "\\b\\d{5}\\b";

    Pattern patternCAP = Pattern.compile(CAP_PATTERN);

    if (!patternCAP.matcher(String.valueOf(cap)).find()) {
      error(capDaValidare, "erroreCAP");
    }
  }

  private void error(IValidatable<String> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }
}
