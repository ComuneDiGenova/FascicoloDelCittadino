package it.liguriadigitale.ponmetro.api.util;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
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
    ZoneOffset zoneOffset = OffsetDateTime.now().getOffset();
    LocalDateTime localDateTime = LocalDate.parse(stringOffsetDateTime, formatter).atStartOfDay();
    return localDateTime.atOffset(zoneOffset);
  }

  public static String getDataFormatoEuropeo(LocalDate date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return date.format(formatter);
  }

  public static LocalDate convertiDaFormatoEuropeo(String data) throws BusinessException {

    try {
      LocalDate localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
      log.trace("localDate=" + localDate);
      return localDate;
    } catch (DateTimeParseException e) {
      log.debug("Stringa non parsabile", e);
      throw new BusinessException("Stringa non parsabile: " + data);
    }
  }
}
