package it.liguriadigitale.ponmetro.portale.presentation.util;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LocalDateUtil {

  private static Log log = LogFactory.getLog(LocalDateUtil.class);

  public static List<LocalDate> mondayInMonth(LocalDate firstMonday, LocalDate lastDayOfMonth) {
    List<LocalDate> dates = new ArrayList<>();
    int lastWeekOfMonth = lastDayOfMonth.get(WeekFields.ISO.weekOfMonth());
    for (int i = 0; i < lastWeekOfMonth + 1; i++) {
      LocalDate lunedi = firstMonday.plusWeeks(i);
      if (lunedi.isBefore(lastDayOfMonth.plusDays(1))) dates.add(lunedi);
    }
    return dates;
  }

  public static LocalDate firstMondayOfMonth(LocalDate day) {
    LocalDate firstDayOfMonth = firstDayOfMonth(day);
    LocalDate firstMonday = firstDayOfMonth;
    if (!firstDayOfMonth.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
      firstMonday = firstDayOfMonth.minusDays(1).with(DayOfWeek.MONDAY);
    }
    return firstMonday;
  }

  public static LocalDate firstDayOfMonth(LocalDate day) {
    return day.withDayOfMonth(1);
  }

  public static LocalDate today() {
    return LocalDate.now();
  }

  public static LocalDate lastDayOfMonth(LocalDate day) {
    LocalDate firstDayOfMonth = firstDayOfMonth(day);
    return firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
  }

  public static boolean isSameMonth(LocalDate day1, LocalDate day2) {
    return day1.getMonth().equals(day2.getMonth());
  }

  public static LocalDate nextMonth(LocalDate day) {
    LocalDate firstDayOfMonth = firstDayOfMonth(day);
    return firstDayOfMonth.plusMonths(1);
  }

  public static LocalDate previousMonth(LocalDate day) {
    LocalDate firstDayOfMonth = firstDayOfMonth(day);
    return firstDayOfMonth.minusMonths(1);
  }

  /** VALTER */
  public static LocalDate nextYear(LocalDate day) {
    return day.plusYears(1);
  }

  public static LocalDate previousYear(LocalDate day) {
    return day.minusYears(1);
  }

  public static boolean isMaggiorenne(LocalDate cpvDateOfBirth) {
    return calcolaEta(cpvDateOfBirth) > 17;
  }

  public static boolean isMaggiore15anni(LocalDate cpvDateOfBirth) {
    return calcolaEta(cpvDateOfBirth) > 15;
  }

  public static boolean isMaggioreVariabileSuDB(LocalDate cpvDateOfBirth) {
    return calcolaEta(cpvDateOfBirth)
        > Integer.parseInt(BaseServiceImpl.IO_GENITORE_VISIBILE_FINO_A);
  }

  public static boolean isMaggiore70anni(LocalDate cpvDateOfBirth) {
    return calcolaEta(cpvDateOfBirth) > 70;
  }

  public static boolean isMaggioreDiEta(LocalDate cpvDateOfBirth) {
    Integer minoreDi = Integer.parseInt(BaseServiceImpl.MINORE_DI);
    return calcolaEta(cpvDateOfBirth) > minoreDi;
  }

  public static int calcolaEta(LocalDate cpvDateOfBirth) {
    Period period = Period.between(cpvDateOfBirth, LocalDateUtil.today());
    return period.getYears();
  }

  public static int getMesiEta(LocalDate cpvDateOfBirth) {
    Period period = Period.between(cpvDateOfBirth, LocalDateUtil.today());
    return period.getMonths() + period.getYears() * 12;
  }

  public List<LocalDate> getGiorniDalPrimoDelMese(LocalDate day) {

    LocalDate firstOfMonth = day.withDayOfMonth(1);
    long daysBetween = ChronoUnit.DAYS.between(firstOfMonth, day);
    List<LocalDate> dates = new ArrayList<>();
    dates.add(firstOfMonth);
    for (int i = 0; i < daysBetween; i++) {
      LocalDate d = firstOfMonth.plusDays(i);
      dates.add(d);
    }
    return dates;
  }

  public static String getStringFormattedByOffsetDateTime(OffsetDateTime offsetDateTime) {
    String dataFormattata = "";
    if (offsetDateTime != null) {
      dataFormattata = offsetDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
    return dataFormattata;
  }

  public static OffsetDateTime getOffsetDateTimeByString(
      String stringOffsetDateTime, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    LocalDate localDate = LocalDate.parse(stringOffsetDateTime, formatter);
    return getOffsetDateTime(localDate);
  }

  public static OffsetDateTime getOffsetDateTime(LocalDate data) {
    ZoneOffset zoneOffset = OffsetDateTime.now().getOffset();
    LocalDateTime localDateTime = data.atStartOfDay();
    return localDateTime.atOffset(zoneOffset);
  }

  public static String getDataFormatoEuropeo(LocalDate date) {
    if (date == null) return "";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return date.format(formatter);
  }

  public static String getDataFormatoEuropeoTariEngMunicipia(LocalDate date) {
    if (date == null) return "";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return date.format(formatter);
  }

  public static LocalDate convertiDaFormatoEuropeo(String data) throws BusinessException {

    try {
      LocalDate localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
      log.trace("localDate=" + localDate);
      return localDate;
    } catch (DateTimeParseException e) {
      log.error("Stringa non parsabile", e);
      throw new BusinessException("Stringa non parsabile");
    }
  }

  public static LocalDate convertiDaFormatoEuropeoCondoni(String data) throws BusinessException {

    try {
      LocalDate localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
      log.trace("localDate=" + localDate);
      return localDate;
    } catch (DateTimeParseException e) {
      log.error("Stringa non parsabile", e);
      throw new BusinessException("Stringa non parsabile");
    }
  }

  public static LocalDate convertiDataStringaFallback(String dataStringa) {

    if (StringUtils.contains(dataStringa, "/")) {
      return LocalDateUtil.convertiDaFormatoEuropeoPerControlloIstanzeTarga(dataStringa);
    }
    if (StringUtils.contains(dataStringa, "-") && StringUtils.contains(dataStringa, ":")) {
      String dataSenzaOra = StringUtils.substring(dataStringa, 0, 9);
      return LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(dataSenzaOra);
    }
    if (StringUtils.contains(dataStringa, "-")) {
      return LocalDateUtil.convertiDaFormatoEuropeoPerControlloIstanzeTarga(dataStringa);
    } else {
      return LocalDate.now();
    }
  }

  public static LocalDate convertiDaFormatoEuropeoPerEngMunicipia(String data) /*
	 * throws
	 * BusinessException
	 */ {

    log.debug("CP convertiDaFormatoEuropeoPerEngMunicipia = " + data);

    LocalDate localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    log.debug("convertiDaFormatoEuropeoPerEngMunicipia localDate=" + localDate);
    return localDate;
  }

  public static LocalDate convertiDaFormatoEuropeoPerControlloIstanzeTarga(String data) {

    try {
      LocalDate localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
      log.trace("localDate=" + localDate);
      return localDate;
    } catch (DateTimeParseException e) {
      log.error("convertiDaFormatoEuropeoPerControlloIstanzeTarga: " + e.getMessage(), e);
      return null;
    }
  }

  public static LocalDate provaData() throws BusinessException {

    try {
      LocalDate today = LocalDate.now();
      String formattedDate = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      LocalDate localDate = LocalDate.parse(formattedDate, formatter);

      return localDate;

    } catch (DateTimeParseException e) {
      log.error("Stringa non parsabile", e);
      throw new BusinessException("Stringa non parsabile");
    }
  }

  public static LocalDate getRandomLocalDate(String anno) {
    long minDay = LocalDate.of(2001, 1, 1).toEpochDay();
    long maxDay = LocalDate.now().toEpochDay();
    if (anno != null) {
      minDay = LocalDate.of(Integer.valueOf(anno), 1, 1).toEpochDay();
      maxDay = LocalDate.of(Integer.valueOf(anno), 12, 31).toEpochDay();
    }
    long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
    return LocalDate.ofEpochDay(randomDay);
  }

  public static OffsetDateTime getRandomOffsetDateTime(String anno) {
    ZoneOffset zoneOffset = OffsetDateTime.now().getOffset();
    return (getRandomLocalDate(anno)).atStartOfDay().atOffset(zoneOffset);
  }

  public static LocalDate getLocalDateFromOffsetDateTime(OffsetDateTime offsetDateTime) {
    LocalDate localDate = offsetDateTime.toLocalDate();
    return localDate;
  }

  public static LocalDateTime getLocalDateTimeFromOffsetDateTime(OffsetDateTime offsetDateTime) {
    LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
    return localDateTime;
  }

  public static String getStringOffsetDateTime(OffsetDateTime offsetDateTime) {
    ZoneOffset zoneOffset = OffsetDateTime.now().getOffset();
    OffsetDateTime localeOffsetDateTime = offsetDateTime.withOffsetSameInstant(zoneOffset);

    LocalDateTime localDateTime = localeOffsetDateTime.toLocalDateTime();
    Locale italia = Locale.ITALY;
    DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withLocale(italia);
    String stringFormatted = localDateTime.format(formatter);
    return stringFormatted;
  }

  public static LocalDate getLocalDateFromDate(Date date) {
    LocalDate localDate =
        Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    return localDate;
  }

  public static Boolean isTodayAfterDateString(String dateFormattedEu) throws BusinessException {
    return today().isAfter(convertiDaFormatoEuropeo(dateFormattedEu));
  }

  public static Boolean isTodayAfterDate(LocalDate dateToCompare) throws BusinessException {
    return today().isAfter(dateToCompare);
  }

  public static Boolean isTodayInYearAfterDateString(String dateInYearFormattedEu)
      throws BusinessException {
    return isTodayAfterDateString(dateInYearFormattedEu + "-" + today().getYear());
  }

  public static long absoluteDifferenceInHours(LocalDate localDate1, LocalDate localDate2)
      throws BusinessException {
    return ChronoUnit.HOURS.between(localDate1, localDate2);
  }

  public static Boolean isSecondInside24hFromFirst(LocalDate localDate1, LocalDate localDate2)
      throws BusinessException {
    return absoluteDifferenceInHours(localDate1, localDate2) < 24;
  }

  public static LocalDate convertiOffsetDateTimePerApk(OffsetDateTime dataOffset) {
    log.debug("CP dataOffset = " + dataOffset);
    OffsetDateTime dataOffsetPiu2ore = dataOffset.plusHours(2);
    log.debug("CP dataOffsetPiu2ore = " + dataOffsetPiu2ore);
    int dayOfMonth = dataOffsetPiu2ore.getDayOfMonth();
    Month month = dataOffsetPiu2ore.getMonth();
    int year = dataOffsetPiu2ore.getYear();
    LocalDate data = LocalDate.of(year, month, dayOfMonth);
    log.debug("CP localdate = " + data);
    return data;
  }

  public static Boolean isTodayBetweenDate(LocalDate date1, LocalDate date2)
      throws BusinessException {
    return isFirstBetweenOthersDate(today(), date1, date2);
  }

  public static Boolean isFirstBetweenOthersDate(LocalDate oggi, LocalDate dataDa, LocalDate dataA)
      throws BusinessException {
    if (dataDa == null && dataA == null) {
      return false;
    }
    return dataDa == null
        ? true
        : (oggi.isAfter(dataDa) || oggi.isEqual(dataDa)) && dataA == null
            ? true
            : (oggi.isBefore(dataA) || oggi.isEqual(dataA));
  }

  public static Integer getYear(LocalDate attestazioneDataPresentazione) {
    return attestazioneDataPresentazione.getYear();
  }

  public static String getDataOraCorretteInItalia(OffsetDateTime offsetDateTime) {

    log.debug("getDataOraCorretteInItalia = " + offsetDateTime);

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    ZoneId italyZoneId = ZoneId.of("Europe/Rome");

    LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
    log.debug("localDateTime = " + localDateTime);

    ZonedDateTime zoneDateTime = ZonedDateTime.of(localDateTime, italyZoneId);

    int secondiDellOffset = zoneDateTime.getOffset().getTotalSeconds();
    log.debug("secondiDellOffset = " + secondiDellOffset);

    ZonedDateTime zoneDateTimeInItalia = zoneDateTime.plusSeconds(Long.valueOf(secondiDellOffset));
    log.debug("zoneDateTimeInItalia = " + zoneDateTimeInItalia);

    String dataOraInItaliaCorretta = zoneDateTimeInItalia.format(format);
    log.debug("dataOraInItaliaCorretta: " + dataOraInItaliaCorretta + " - " + italyZoneId);

    return dataOraInItaliaCorretta;
  }
}
