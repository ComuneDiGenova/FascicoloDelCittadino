package it.liguriadigitale.ponmetro.portale.presentation.util.validator;

import java.util.regex.Pattern;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class TelefonoFissoCellulareValidator implements IValidator<String> {

  private static final long serialVersionUID = -1126695016232797922L;

  public TelefonoFissoCellulareValidator() {
    super();
  }

  @Override
  public void validate(IValidatable<String> numeroDaValidare) {
    String numero = numeroDaValidare.getValue();

    // final String FISSO_CELLULARE_PATTERN = "^([0-9]*\\-?\\ ?\\/?[0-9]*)$";
    final String FISSO_CELLULARE_PATTERN = "^\\+?\\d*$";

    Pattern patternFissoCellulare = Pattern.compile(FISSO_CELLULARE_PATTERN);

    if (!patternFissoCellulare.matcher(String.valueOf(numero)).find()) {
      error(numeroDaValidare, "erroreTelefono");
    }
  }

  private void error(IValidatable<String> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }
}
