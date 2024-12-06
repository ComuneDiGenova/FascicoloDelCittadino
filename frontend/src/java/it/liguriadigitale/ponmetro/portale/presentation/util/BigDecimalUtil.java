package it.liguriadigitale.ponmetro.portale.presentation.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

  public static BigDecimalUtil getIstance() {
    return new BigDecimalUtil();
  }

  public static boolean isMaggioreBigDecimal(
      BigDecimal firstBigDecimal, BigDecimal secondBigDecimal) {
    return firstBigDecimal.compareTo(secondBigDecimal) > 0;
  }

  public static boolean isMinoreBigDecimal(
      BigDecimal firstBigDecimal, BigDecimal secondBigDecimal) {
    return firstBigDecimal.compareTo(secondBigDecimal) < 0;
  }

  public static boolean isEqualBigDecimal(BigDecimal firstBigDecimal, BigDecimal secondBigDecimal) {
    return firstBigDecimal.compareTo(secondBigDecimal) == 0;
  }
}
