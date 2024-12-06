package it.liguriadigitale.ponmetro.portale.presentation.util.validator;

import it.liguriadigitale.ponmetro.portale.presentation.util.CodiceFiscaleUtil;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class CodiceFiscaleValidatorUtil implements IValidator<String> {

  private static final long serialVersionUID = -9110441726527658621L;

  protected static final Log log = LogFactory.getLog(CodiceFiscaleValidatorUtil.class);

  public CodiceFiscaleValidatorUtil() {
    super();
  }

  @Override
  public void validate(IValidatable<String> cfDaValidare) {
    String codicefiscale = cfDaValidare.getValue().trim().toUpperCase();
    @SuppressWarnings("unused")
    CodiceFiscaleUtil util = new CodiceFiscaleUtil();

    final String CF_PATTERN =
        "^(?:(?:[B-DF-HJ-NP-TV-Z]|[AEIOU])[AEIOU][AEIOUX]|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}[\\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[1256LMRS][\\dLMNP-V])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$";

    Pattern pattern = Pattern.compile(CF_PATTERN);

    /*
     * if (pattern.matcher(codicefiscale).matches()) {
     *
     * if (!util.verificaCarattereDiControllo(codicefiscale)) {
     * log.debug("--->" + codicefiscale +
     * " verifica del codice di controllo del CF non superata");
     * ValidationError error = new ValidationError(this); error.
     * setMessage("Il valore del codice di controllo inserito per il codice fiscale "
     * + codicefiscale + " non è valido."); cfDaValidare.error(error); } }
     * else { log.debug("--->" + codicefiscale +
     * " campo codice fiscale formalmente scorretto"); ValidationError error
     * = new ValidationError(this); cfDaValidare.error(error);
     * error.setMessage("Il valore di Codice Fiscale inserito " +
     * codicefiscale + " è formalmente scorretto."); }
     */

    if (!pattern.matcher(codicefiscale).find()) {
      error(cfDaValidare, "cf");
    }
  }

  private void error(IValidatable<String> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }

  public static LocalDate getDataNascitaFromCf(String valore) {
    if (StringUtils.isNotEmpty(valore) && isStringaLunga16Caratteri(valore)) {
      String codiceFiscale = valore.toUpperCase();
      log.debug("CP getDataNascitaFromCf = " + codiceFiscale);
      String genere = getSessoFromCf(codiceFiscale.toUpperCase());

      String giornoNascita = StringUtils.substring(codiceFiscale, 9, 11);
      Integer giorno = Integer.parseInt(giornoNascita);
      if (genere.equalsIgnoreCase("F")) {
        giorno = giorno - 40;
      }

      String letteraMese = StringUtils.substring(codiceFiscale, 8, 9);
      HashMap<String, Integer> codificaMese = getMeseNascita();
      Integer mese = codificaMese.get(letteraMese);

      Integer annoCorrente = LocalDate.now().getYear();
      Integer annoInizioMaggiorenni = annoCorrente - 18;
      LocalDate inizioMaggiorenni = LocalDate.of(annoInizioMaggiorenni, Month.JANUARY, 1);

      String annoNascita = StringUtils.substring(codiceFiscale, 6, 8);
      String mcm = "19";
      String mm = "20";
      String annoNascitaCittadino = mm.concat(annoNascita);
      Integer anno = Integer.parseInt(annoNascitaCittadino);

      LocalDate dataNascita = LocalDate.of(anno, mese, giorno);
      if (dataNascita.isAfter(inizioMaggiorenni) || dataNascita.isAfter(LocalDate.now())) {
        annoNascitaCittadino = mcm.concat(annoNascita);
        anno = Integer.parseInt(annoNascitaCittadino);
        dataNascita = LocalDate.of(anno, mese, giorno);

        // TODO da decidere
        /*
         * Integer eta = LocalDateUtil.calcolaEta(dataNascita);
         * log.debug("dataNascita = " + dataNascita + " - eta = " +
         * eta); if (eta > 100) { annoNascitaCittadino =
         * mm.concat(annoNascita); anno =
         * Integer.parseInt(annoNascitaCittadino); dataNascita =
         * LocalDate.of(anno, mese, giorno); }
         */
      }
      log.debug("anno:" + anno);
      log.debug("mese:" + mese);
      log.debug("giorno:" + giorno);
      return dataNascita;
    } else {
      return LocalDate.now();
    }
  }

  public static LocalDate getDataNascitaFromCfDichiarazioneMinore(String codiceFiscale) {
    log.debug(
        "CP getDataNascitaFromCfDichiarazioneMinore = " + StringUtils.upperCase(codiceFiscale));
    String genere = getSessoFromCf(StringUtils.upperCase(codiceFiscale));

    String giornoNascita = StringUtils.substring(codiceFiscale, 9, 11);
    Integer giorno = Integer.parseInt(giornoNascita);
    if (genere.equalsIgnoreCase("F")) {
      giorno = giorno - 40;
    }

    String letteraMese = StringUtils.substring(codiceFiscale, 8, 9);

    HashMap<String, Integer> codificaMese = getMeseNascita();
    Integer mese = codificaMese.get(StringUtils.upperCase(letteraMese));

    String annoNascita = StringUtils.substring(codiceFiscale, 6, 8);
    String mm = "20";
    String annoNascitaCittadino = mm.concat(annoNascita);

    Integer anno = Integer.parseInt(annoNascitaCittadino);

    LocalDate dataNascita = LocalDate.of(anno, mese, giorno);

    log.debug("CP data nascita minore = " + dataNascita);

    return dataNascita;
  }

  public static HashMap<String, Integer> getMeseNascita() {
    HashMap<String, Integer> codificaMese = new HashMap<String, Integer>();
    codificaMese.put("A", 1);
    codificaMese.put("B", 2);
    codificaMese.put("C", 3);
    codificaMese.put("D", 4);
    codificaMese.put("E", 5);
    codificaMese.put("H", 6);
    codificaMese.put("L", 7);
    codificaMese.put("M", 8);
    codificaMese.put("P", 9);
    codificaMese.put("R", 10);
    codificaMese.put("S", 11);
    codificaMese.put("T", 12);
    return codificaMese;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static String getSessoFromCf(String codiceFiscale) {
    String sesso = "";
    if (isStringaLunga16Caratteri(codiceFiscale)) {
      String sessoInCf = StringUtils.substring(codiceFiscale, 9, 11);
      Range rangeMaschio = Range.between(1, 31);
      Range rangeFemmina = Range.between(41, 71);
      Integer genere = Integer.parseInt(sessoInCf);
      if (rangeMaschio.contains(genere)) {
        sesso = "M";
      }
      if (rangeFemmina.contains(genere)) {
        sesso = "F";
      }
    }
    return sesso;
  }

  public static boolean isStringaLunga16Caratteri(String codiceFiscale) {
    return StringUtils.length(codiceFiscale) == 16;
  }

  public static String getCodiceBelFioreFromCf(String codiceFiscale) {
    String codiceBelfiore = StringUtils.substring(codiceFiscale, 11, 15);
    return codiceBelfiore;
  }
}
