package it.liguriadigitale.ponmetro.portale.presentation.util.validator;

import java.util.regex.Pattern;
import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.PatternValidator;

public class SwiftValidator implements IValidator<String> {

  private static final long serialVersionUID = 3149048705294125483L;

  private final PatternValidator patternValidatorAlphanumeric =
      new PatternValidator(MetaPattern.VARIABLE_NAME);

  private final PatternValidator patternValidatorWhiteSpace =
      new PatternValidator(MetaPattern.WHITESPACE);

  public SwiftValidator() {
    super();
  }

  @Override
  public void validate(IValidatable<String> swiftDaValidare) {
    String swift = swiftDaValidare.getValue().trim();

    Pattern patternWhiteSpace = patternValidatorWhiteSpace.getPattern();
    if (patternWhiteSpace.matcher(swift).find()) {
      error(swiftDaValidare, "whiteSpace");
    }

    Pattern patternAlphanumeric = patternValidatorAlphanumeric.getPattern();
    if (!patternAlphanumeric.matcher(swift).find()) {
      error(swiftDaValidare, "swift");
    }
  }

  private void error(IValidatable<String> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }
}
