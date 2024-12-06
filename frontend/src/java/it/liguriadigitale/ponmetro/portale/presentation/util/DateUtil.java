package it.liguriadigitale.ponmetro.portale.presentation.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {

  private static Log log = LogFactory.getLog(DateUtil.class);

  public static Date addDays(final Date date, final long days) {
    if (date != null) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      cal.add(Calendar.DATE, (int) days);
      return cal.getTime();
    }
    return null;
  }

  public static Date getDate(String date) {
    Date startDate = null;
    if ((date != null) && !date.equalsIgnoreCase("")) {
      DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

      try {
        startDate = df.parse(date);

      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    return startDate;
  }

  public static String getDate(Date date) {
    if (date != null) {
      SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
      return format.format(date);
    } else return "";
  }

  public static boolean isSameDay(Date date1, Date date2) {

    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date1);
    cal2.setTime(date2);

    boolean sameDay =
        (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
            && (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));

    return sameDay;
  }

  public static Date getFirstDayNextMonthDate(Date data) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(data);
    calendar.add(Calendar.MONTH, 1);
    calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    Date nextMonthFirstDay = calendar.getTime();
    // calendar.set(Calendar.DATE,
    // calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    // Date nextMonthLastDay = calendar.getTime();
    return nextMonthFirstDay;
  }

  public static String getGiornoDellaSettimana(Date data) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(data);
    return calendar
        .getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ITALIAN)
        .toUpperCase();
  }

  public static int getNumerodiGiorniNelMeseDellaData(Date data) {
    Calendar cal = new GregorianCalendar();
    cal.setTime(data);
    return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  public static Long settimanaDelMese(String data) {
    if ((data != null) && !data.equalsIgnoreCase("")) {
      Date dtData = getDate(data);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dtData);
      return Long.valueOf(calendar.get(Calendar.WEEK_OF_MONTH));
    } else return 0l;
  }

  public static Long meseDellanno(String data) {
    if ((data != null) && !data.equalsIgnoreCase("")) {
      Date dtData = getDate(data);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dtData);
      return Long.valueOf(calendar.get(Calendar.MONTH) + 1);
    } else return 0l;
  }

  public static Long annoDellaData(String data) {
    if ((data != null) && !data.equalsIgnoreCase("")) {
      Date dtData = getDate(data);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dtData);
      return Long.valueOf(calendar.get(Calendar.YEAR));
    } else return 0l;
  }

  public static Date getTodatyDate() {
    Calendar today = Calendar.getInstance();
    return today.getTime();
  }

  public static String getNumeroSettimanaNelMese(Date data) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(data);
    return "" + calendar.get(Calendar.WEEK_OF_MONTH);
  }

  public static String getNumeroMeseAnno(Date data) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(data);
    return "" + (calendar.get(Calendar.MONTH) + 1);
  }

  public static String getGiornoFromDate(Date data) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(data);
    return "" + (calendar.get(Calendar.DAY_OF_MONTH));
  }

  public static String getAnno(Date data) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(data);
    return "" + calendar.get(Calendar.YEAR);
  }

  public static LocalDate toLocalDate(Date date) {
    if (date != null) return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    else return null;
  }

  public static boolean isStringValidDate(String value) {
    String format = "dd/MM/yyyy";
    Date date = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      date = sdf.parse(value);
      if (!value.equals(sdf.format(date))) {
        date = null;
      }
    } catch (ParseException ex) {
      log.error("Errore formato data");
    }

    return date != null;
  }

  public static boolean checkDateStringRilascioPatente(String rilasciataIl, String validaFinoAl) {
    Date rilasciataIlDate;
    Date validaFinoAlDate;
    try {
      rilasciataIlDate = new SimpleDateFormat("dd/MM/yyyy").parse(rilasciataIl);
      validaFinoAlDate = new SimpleDateFormat("dd/MM/yyyy").parse(validaFinoAl);
      return validaFinoAlDate.after(rilasciataIlDate);
    } catch (ParseException e) {
      log.error("Errore date patente");
      return false;
    }
  }

  public static boolean checkDateRilascioPatente(Date rilasciataIl, Date validaFinoAl) {
    return validaFinoAl.after(rilasciataIl);
  }

  public static boolean checkDateRilascioPatente(LocalDate rilasciataIl, LocalDate validaFinoAl) {
    return validaFinoAl.isAfter(rilasciataIl);
  }

  public static boolean checkDataRilascioPrimaDiDataNascita(
      Date dataRilascioPatente, LocalDate dataNascitaLocalDate) {
    Date dataNascita = fromLocalDateToDate(dataNascitaLocalDate);
    return dataRilascioPatente.before(dataNascita) && !dataRilascioPatente.equals(dataNascita);
  }

  public static boolean checkDataVerbaleEntro150ggDaOggi(LocalDate dataVerbale) {
    LocalDate oggi = LocalDate.now();
    LocalDate dataOggiMeno150gg = oggi.minusDays(150);
    return dataVerbale.isAfter(dataOggiMeno150gg) || dataVerbale.isEqual(dataOggiMeno150gg);
  }

  public static LocalDate fromDateToLocalDate(Date data) {
    LocalDate localDate = data.toInstant().atZone(ZoneId.of("Europe/Rome")).toLocalDate();
    return localDate;
  }

  public static Date fromLocalDateToDate(LocalDate localDate) {
    Date data = Date.from(localDate.atStartOfDay().atZone(ZoneId.of("Europe/Rome")).toInstant());
    return data;
  }

  public static boolean checkDataRilascioPrimaDiDataNascita(
      LocalDate patenteConducenteRilasciataIl, LocalDate dataNascitaConducente) {

    return (patenteConducenteRilasciataIl.isBefore(dataNascitaConducente)
        && !patenteConducenteRilasciataIl.isEqual(dataNascitaConducente));
  }
}
