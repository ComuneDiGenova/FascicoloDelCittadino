package it.liguriadigitale.ponmetro.portale.presentation.util;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeUtil {

  public static String getStringByOffsetDateTime(OffsetDateTime odt, String sPattern) {
    if (odt != null) {
      return getStringByLocalDateTime(odt.toLocalDateTime(), sPattern);
    }
    return null;
  }

  public static String getStringByLocalDateTime(LocalDateTime ldt, String sPattern) {
    if (ldt != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(sPattern);
      return ldt.format(formatter);
    }
    return null;
  }

  public static LocalDateTime getLocalDateTimeByMilliseconds(String milliseconds) {
    return getLocalDateTimeByMilliseconds(Long.parseLong(milliseconds));
  }

  public static LocalDateTime getLocalDateTimeByMilliseconds(Long milliseconds) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
  }

  public static boolean isInLocalDateTime(LocalDateTime localDateTime, int month, int year) {
    return localDateTime.getMonthValue() == month && localDateTime.getYear() == year;
  }

  public static LocalDateTime getLocalDateTimeFromLocalDateAndLocalTime(
      LocalDate ld, LocalTime lt) {
    if (ld == null || lt == null) {
      return null;
    }
    return LocalDateTime.of(ld, lt);
  }

  public static long absoluteDifferenceInHours(LocalDateTime localDate1, LocalDateTime localDate2)
      throws BusinessException {
    return ChronoUnit.HOURS.between(localDate1, localDate2);
  }

  public static Boolean isSecondInside24hFromFirst(LocalDateTime lt1, LocalDateTime lt2)
      throws BusinessException {
    return absoluteDifferenceInHours(lt1, lt2) < 24;
  }

  public static LocalTime getLocalTimeFromStringHHmm(String HHmm) {
    if (HHmm == null) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime lt = LocalTime.parse(HHmm, formatter);
    return lt;
  }
}
