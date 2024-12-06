package it.liguriadigitale.ponmetro.portale.presentation.util.validator;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class CFAutodichiarazioneFiglioValidator implements IValidator<String> {

  private static final long serialVersionUID = -2729722121136505954L;

  protected static final Log log = LogFactory.getLog(CodiceFiscaleValidatorUtil.class);

  private Utente utente;

  public CFAutodichiarazioneFiglioValidator(Utente utente) {
    super();

    this.utente = utente;
  }

  @Override
  public void validate(IValidatable<String> cfDaValidare) {
    String codicefiscale = cfDaValidare.getValue().trim().toUpperCase();

    final String CF_PATTERN =
        "^(?:(?:[B-DF-HJ-NP-TV-Z]|[AEIOU])[AEIOU][AEIOUX]|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}[\\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[1256LMRS][\\dLMNP-V])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$";

    Pattern pattern = Pattern.compile(CF_PATTERN);

    if (!pattern.matcher(codicefiscale).find()) {
      error(cfDaValidare, "cf");
    } else {
      if (LabelFdCUtil.checkIfNotNull(codicefiscale)
          && codicefiscale.equalsIgnoreCase(utente.getCodiceFiscaleOperatore())) {
        error(cfDaValidare, "cfLoggato");
      }

      LocalDate dataNascitaBambino = getDataNascitaFromCf(codicefiscale);
      if (LocalDateUtil.isMaggioreDiEta(dataNascitaBambino)) {
        error(cfDaValidare, "etaBambino");
      }
    }
  }

  private void error(IValidatable<String> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }

  public static LocalDate getDataNascitaFromCf(String codiceFiscale) {
    log.debug("CP getDataNascitaFromCf = " + codiceFiscale);
    String genere = CodiceFiscaleValidatorUtil.getSessoFromCf(codiceFiscale);

    String giornoNascita = codiceFiscale.substring(9, 11);
    Integer giorno = Integer.parseInt(giornoNascita);
    if (genere.equalsIgnoreCase("F")) {
      giorno = giorno - 40;
    }

    String letteraMese = codiceFiscale.substring(8, 9);
    HashMap<String, Integer> codificaMese = CodiceFiscaleValidatorUtil.getMeseNascita();
    Integer mese = codificaMese.get(letteraMese);

    String annoNascita = codiceFiscale.substring(6, 8);
    String mm = "20";
    String annoNascitaCittadino = mm.concat(annoNascita);
    Integer anno = Integer.parseInt(annoNascitaCittadino);

    LocalDate dataNascita = LocalDate.of(anno, mese, giorno);
    if (dataNascita.isAfter(LocalDate.now())) {
      dataNascita = dataNascita.minusYears(100);
    }

    log.debug("CP data nascita autodichiarazione = " + dataNascita);

    return dataNascita;
  }
}
