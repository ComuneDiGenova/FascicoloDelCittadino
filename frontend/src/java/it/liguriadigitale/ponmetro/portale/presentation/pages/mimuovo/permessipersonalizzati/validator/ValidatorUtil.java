package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.validator;

import it.liguriadigitale.ponmetro.portale.presentation.util.CodiceFiscaleUtil;
import java.time.LocalDate;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ValidatorUtil {

  private static Log log = LogFactory.getLog(ValidatorUtil.class);

  public static boolean codiceFiscaleValido(String codicFiscaleDaValidare) {

    String codicefiscale = codicFiscaleDaValidare.trim().toUpperCase();
    CodiceFiscaleUtil util = new CodiceFiscaleUtil();

    final String CF_PATTERN =
        "^(?:(?:[B-DF-HJ-NP-TV-Z]|[AEIOU])[AEIOU][AEIOUX]|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}[\\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[1256LMRS][\\dLMNP-V])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$";

    Pattern pattern = Pattern.compile(CF_PATTERN);

    return pattern.matcher(codicefiscale).find();
  }

  public static boolean dataRilascioPatenteValida(LocalDate dataRilascioPatente) {

    return dataRilascioPatente.isBefore(LocalDate.now());
  }

  public static boolean dataScadenzaPatenteValida(LocalDate dataRilascioPatente) {

    return dataRilascioPatente.isAfter(LocalDate.now());
  }

  public static boolean dataRilascioContrassegnoCUDEValida(LocalDate dataRilascioContrassegnoCUDE) {

    return dataRilascioContrassegnoCUDE.isBefore(LocalDate.now());
  }

  public static boolean dataScadenzaContrassegnoCUDEValida(
      LocalDate dataScadenzaContrassegnoCUDE, LocalDate dataRilascioContrassegnoCUDE) {

    return dataScadenzaContrassegnoCUDE.isAfter(LocalDate.now())
        && dataScadenzaContrassegnoCUDE.isAfter(dataRilascioContrassegnoCUDE);
  }
}
