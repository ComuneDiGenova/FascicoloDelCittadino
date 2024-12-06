package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class ClasseCatastaleValidator implements IValidator<String> {
  private static final long serialVersionUID = 179879756465489L;

  @Override
  public void validate(IValidatable<String> value) {
    // TODO Auto-generated method stub
    String tester = value.getValue();
    Pattern p = Pattern.compile("[0-9]+$");
    Matcher m = p.matcher(tester);

    if (!m.matches()) {
      error(value, "classeCatastaleSoloCifre");
    }
  }

  private void error(IValidatable<String> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }
}
