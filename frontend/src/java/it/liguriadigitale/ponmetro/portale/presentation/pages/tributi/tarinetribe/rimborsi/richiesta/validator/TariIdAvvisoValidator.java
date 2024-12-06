package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.richiesta.validator;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class TariIdAvvisoValidator implements IValidator<String> {

  private static final long serialVersionUID = 6685429961544014056L;

  private Log log = LogFactory.getLog(getClass());

  public TariIdAvvisoValidator() {
    super();
  }

  @Override
  public void validate(IValidatable<String> cfDaValidare) {
    String idAvviso = cfDaValidare.getValue();

    Integer lunghezzaIdAvvisoEsperta = 14;
    Integer lunghezzaIdAvvisoGeri = 23;

    if (LabelFdCUtil.checkIfNotNull(idAvviso)) {

      if (idAvviso.length() == lunghezzaIdAvvisoEsperta
          || idAvviso.length() == lunghezzaIdAvvisoGeri) {
        if (idAvviso.contains("/")) {
          error(cfDaValidare, "slash");
        }

      } else {
        error(cfDaValidare, "lunghezza");
      }
    }
  }

  private void error(IValidatable<String> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }
}
