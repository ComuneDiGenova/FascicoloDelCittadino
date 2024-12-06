package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali;

import java.time.LocalDate;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilVerbali {

  protected static Log log = LogFactory.getLog(UtilVerbali.class);

  private static LocalDate dataOggi = LocalDate.now();

  public static boolean checkIfNotNull(Object obj) {
    return Optional.ofNullable(obj).isPresent();
  }

  public static boolean checkIfNull(Object obj) {
    return !Optional.ofNullable(obj).isPresent();
  }

  public static boolean checkNotEmpty(String str) {
    return str.isEmpty();
  }

  public static boolean checkDataOggiIsBefore(LocalDate date) {
    return dataOggi.isBefore(date);
  }

  public static boolean checkDataOggiIsAfter(LocalDate date) {
    return dataOggi.isAfter(date);
  }

  public static boolean checkDataOggiIsEqual(LocalDate date) {
    return dataOggi.isEqual(date);
  }

  /*
   * public static boolean checkDataPagamentoPeople(LocalDate date) { LocalDate
   * dataFinePeople = LocalDate.of(2020, 11, 03); return
   * date.isBefore(dataFinePeople) || date.isEqual(dataFinePeople); }
   */

  public static boolean checkDataAccertamentoFinePeople(LocalDate date) {
    LocalDate dataFinePeople = LocalDate.of(2018, 12, 31);
    return date.isBefore(dataFinePeople) || date.isEqual(dataFinePeople);
  }

  public static boolean checkDataAccertamentoInizioPagoPa(LocalDate date) {
    LocalDate dataFinePeople = LocalDate.of(2019, 01, 01);
    return date.isAfter(dataFinePeople) || date.isEqual(dataFinePeople);
  }

  public static boolean checkData5gg(LocalDate date) {
    boolean data5ggVisibile = false;

    if (checkIfNotNull(date) && (checkDataOggiIsBefore(date) || checkDataOggiIsEqual(date))) {
      data5ggVisibile = true;
    }

    return data5ggVisibile;
  }

  public static boolean checkDataEntro60gg(LocalDate date5, LocalDate date60) {
    boolean data60ggVisibile = false;

    /*
     * if (checkIfNotNull(date5) && checkIfNotNull(date60) &&
     * checkDataOggiIsAfter(date5) && (checkDataOggiIsBefore(date60) ||
     * checkDataOggiIsEqual(date60))) { data60ggVisibile = true; }
     */

    // JIRA FDCOMGE-68 verbale con data notifica ma senza data entro 5 gg
    if (checkIfNotNull(date5)) {
      if (checkIfNotNull(date60)
          && checkDataOggiIsAfter(date5)
          && (checkDataOggiIsBefore(date60) || checkDataOggiIsEqual(date60))) {
        data60ggVisibile = true;
      }
    } else {
      if (checkIfNotNull(date60)
          && (checkDataOggiIsBefore(date60) || checkDataOggiIsEqual(date60))) {
        data60ggVisibile = true;
      }
    }

    return data60ggVisibile;
  }

  public static boolean checkDataOltre60gg(LocalDate date) {
    boolean data60ggVisibile = false;

    if (checkIfNotNull(date) && checkDataOggiIsAfter(date)) {
      data60ggVisibile = true;
    }

    return data60ggVisibile;
  }
}
