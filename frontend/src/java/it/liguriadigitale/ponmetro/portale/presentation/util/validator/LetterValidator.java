package it.liguriadigitale.ponmetro.portale.presentation.util.validator;

import java.util.regex.Pattern;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class LetterValidator implements IValidator<String> {

  private static final long serialVersionUID = 4835224925135774914L;

  // private static final Pattern LETTER_PATTERN =
  // Pattern.compile("^[a-zA-Z]+$");

  private static final Pattern PATTERN_DIGIT = Pattern.compile("^/D+$");

  @Override
  public void validate(IValidatable<String> validatable) {
    String valore = validatable.getValue();

    /*
     * if (!LETTER_PATTERN.matcher(valore).find()) { error(validatable,
     * "only-letter"); }
     */

    if (PATTERN_DIGIT.matcher(valore).find()) {
      error(validatable, "only-letter");
    }
  }

  private void error(IValidatable<String> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }
}
