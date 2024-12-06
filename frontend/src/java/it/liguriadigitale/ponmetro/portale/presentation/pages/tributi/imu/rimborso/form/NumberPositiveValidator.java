package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form;

import java.math.BigDecimal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class NumberPositiveValidator<T extends Number> implements IValidator<T> {

  private static final long serialVersionUID = 1L;

  protected static final Log log = LogFactory.getLog(NumberPositiveValidator.class);

  private final boolean isRequired;

  public NumberPositiveValidator(final boolean isRequired) {
    this.isRequired = isRequired;
  }

  @Override
  public void validate(IValidatable<T> valueDaValidare) {
    // TODO Auto-generated method stub
    T value = valueDaValidare.getValue();

    log.debug("[validate] Valore inserito: " + value);

    if (value == null && isRequired) {
      error(valueDaValidare, "numberMandatory");
    }

    if (value instanceof BigDecimal && ((BigDecimal) value).compareTo(BigDecimal.ZERO) < 0) {
      error(valueDaValidare, "numberMinorZero");
    } else if (value instanceof Integer && ((Integer) value) < 0) {
      error(valueDaValidare, "numberMinorZero");
    } else if (value instanceof Float && ((Float) value) < 0) {
      error(valueDaValidare, "numberMinorZero");
    } else if (value instanceof Double && ((Double) value) < 0) {
      error(valueDaValidare, "numberMinorZero");
    } else if (value instanceof Long && ((Long) value) < 0) {
      error(valueDaValidare, "numberMinorZero");
    }
  }

  private void error(IValidatable<T> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }
}
