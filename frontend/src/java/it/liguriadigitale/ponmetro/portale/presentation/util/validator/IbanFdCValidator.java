package it.liguriadigitale.ponmetro.portale.presentation.util.validator;

import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.PatternValidator;

public class IbanFdCValidator implements IValidator<String> {

  private static final long serialVersionUID = -3486100597776838395L;

  protected static final Log log = LogFactory.getLog(IbanFdCValidator.class);

  private PatternValidator patternValidatorWhiteSpace =
      new PatternValidator(MetaPattern.WHITESPACE);

  private final String abiPosteItaliane = "07601";

  private final String cabLibrettoPostale = "03384";

  public IbanFdCValidator() {
    super();
  }

  @Override
  public void validate(IValidatable<String> ibanDaValidare) {

    String iban = ibanDaValidare.getValue();
    Pattern patternWhiteSpace = patternValidatorWhiteSpace.getPattern();

    if (PageUtil.isStringValid(iban) && iban.length() < 31 && iban.length() > 8) {

      if (patternWhiteSpace.matcher(iban).find()) {
        error(ibanDaValidare, "whiteSpace");
      }
      String ibanTrim = iban.trim();
      if (ibanTrim.length() > 4) {
        String codiceABI = ibanTrim.substring(5, 10);
        String codiceCAB = ibanTrim.substring(10, 15);

        if (codiceABI.equalsIgnoreCase(abiPosteItaliane)
            && codiceCAB.equalsIgnoreCase(cabLibrettoPostale)) {
          error(ibanDaValidare, "librettoPostale");
        }
      }
    } else {
      error(ibanDaValidare, "lunghezzaIban");
    }
  }

  private void error(IValidatable<String> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }
}
