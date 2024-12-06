package it.liguriadigitale.ponmetro.test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

public class CalendarTest {

  public static void main(String[] args) {

    List<LocalDate> lista = getGiorniDalPrimoDelMese();
    LocalDate firstOfMonth = lista.get(0);
    // LocalDate now = LocalDate.now();
    System.out.println("firstOfMonth:" + firstOfMonth);
    LocalDate firstMonday = firstOfMonth;
    if (!firstOfMonth.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
      firstMonday = firstOfMonth.minusDays(1).with(DayOfWeek.MONDAY);
      System.out.println("firstMonday:" + firstMonday);
    }
    LocalDate ultimoDelMese = firstOfMonth.plusMonths(1).minusDays(1);
    System.out.println("ultimoDelMese:" + ultimoDelMese);
    System.out.println("week:" + ultimoDelMese.getDayOfWeek());
    if (!ultimoDelMese.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
      System.out.println("LastSunday:" + ultimoDelMese.plusDays(1).with(DayOfWeek.SUNDAY));
    }

    LocalDate lastDayOfMonth = firstOfMonth.withDayOfMonth(firstOfMonth.lengthOfMonth());
    int lastWeekOfMonth = lastDayOfMonth.get(WeekFields.ISO.weekOfMonth());
    System.out.println("lastWeekOfMonth:" + lastWeekOfMonth);

    List<LocalDate> elencoLunedi = creaElencoLunedi(firstMonday, lastDayOfMonth);
    System.out.println("elencoLuned�:" + elencoLunedi);
  }

  private static List<LocalDate> creaElencoLunedi(LocalDate firstMonday, LocalDate lastDayOfMonth) {
    List<LocalDate> dates = new ArrayList<>();
    int lastWeekOfMonth = lastDayOfMonth.get(WeekFields.ISO.weekOfMonth());
    for (int i = 0; i < lastWeekOfMonth + 1; i++) {
      LocalDate secondo = firstMonday.plusWeeks(i);
      System.out.println("luned�::" + secondo);
      dates.add(secondo);
    }
    return dates;
  }

  private static List<LocalDate> getGiorniDalPrimoDelMese() {

    LocalDate today = LocalDate.now();
    LocalDate firstOfMonth = today.withDayOfMonth(1);
    long daysBetween = ChronoUnit.DAYS.between(firstOfMonth, today);
    List<LocalDate> dates = new ArrayList<>();
    for (int i = 0; i < daysBetween; i++) {
      LocalDate d = firstOfMonth.plusDays(i);
      dates.add(d);
    }
    return dates;
  }
}
