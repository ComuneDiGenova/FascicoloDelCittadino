package it.liguriadigitale.ponmetro.portale.presentation.util.validator;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class CodiceFiscaleAncheStranieroValidatorUtil implements IValidator<String> {

  private static final long serialVersionUID = 6685429961544014056L;

  protected static final Log log = LogFactory.getLog(CodiceFiscaleValidatorUtil.class);

  public CodiceFiscaleAncheStranieroValidatorUtil() {
    super();
  }

  @Override
  public void validate(IValidatable<String> cfDaValidare) {
    String codicefiscale = cfDaValidare.getValue().trim().toUpperCase();

    final String CF_NORMALE_PATTERN =
        "^(?:(?:[B-DF-HJ-NP-TV-Z]|[AEIOU])[AEIOU][AEIOUX]|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}[\\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[1256LMRS][\\dLMNP-V])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$";

    Pattern patternCfNormale = Pattern.compile(CF_NORMALE_PATTERN);

    final String CF_STRANIERO_PROVVISORIO_PATTERN = "^[0-9]*$";

    Pattern patternCfStranieroProvvisorio = Pattern.compile(CF_STRANIERO_PROVVISORIO_PATTERN);

    if (LabelFdCUtil.checkIfNotNull(codicefiscale)) {
      if (codicefiscale.length() == 11) {
        if (!patternCfStranieroProvvisorio.matcher(codicefiscale).find()) {
          error(cfDaValidare, "cf");
        }
      } else if (codicefiscale.length() == 16) {
        if ((!patternCfNormale.matcher(codicefiscale).find())) {
          error(cfDaValidare, "cf");
        }
      } else {
        error(cfDaValidare, "cf");
      }
    }
  }

  private void error(IValidatable<String> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }
}
