package it.liguriadigitale.ponmetro.portale.presentation.util.validator;

import java.util.regex.Pattern;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class EuroValidator implements IValidator<Double> {

  private static final long serialVersionUID = 5918884911015122087L;

  public EuroValidator() {
    super();
  }

  @Override
  public void validate(IValidatable<Double> importoDaValidare) {
    Double importo = importoDaValidare.getValue();

    final String EURO_PATTERN =
        "^[+-]?(([0-9]*([,][0-9]{1,2})?)|[0-9]{1,3}(([.][0-9]{3})*([,][0-9]{1,2})?))$";

    Pattern patternImportoEuro = Pattern.compile(EURO_PATTERN);

    if (!patternImportoEuro.matcher(String.valueOf(importo)).find()) {
      error(importoDaValidare, "euro", importo);
    }
  }

  private void error(IValidatable<Double> validatable, String errorKey, Double importo) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    error.setVariable("importo", importo);
    validatable.error(error);
  }
}
