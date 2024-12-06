package it.liguriadigitale.ponmetro.portale.business.pagopa.util;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import java.math.BigDecimal;

public class NodoPagamentiConverterUtilities {

  public static String preprocessing(String importo) throws BusinessException {

    int dot = importo.indexOf(".");
    int comma = importo.indexOf(",");
    if (dot != -1 || comma != -1)
      throw new BusinessException("Inserito importo in euro anziche' in centesimi: " + importo);

    importo =
        (importo.length() == 1
            ? "00" + importo
            : (importo.length() == 2 ? "0" + importo : importo));

    String centesimi = importo.substring(importo.length() - 2);
    String euro = importo.substring(0, importo.length() - 2);

    return euro + centesimi;
  }

  public static String euroToEurocent(BigDecimal euro) {

    return euro.multiply(BigDecimal.valueOf(100l)).toBigInteger().toString();
  }

  public static BigDecimal getImportoBigDecimal(String importo) {

    return new BigDecimal(importo.replace(".", ""))
        .divide(new BigDecimal("100"), 2, BigDecimal.ROUND_UP);
  }

  public static String euroToString(BigDecimal euro) {
    return euroToString(euro, ".");
  }

  public static String euroToEurocentString_conZeriNonSignificativi(BigDecimal euro) {

    String eurocent = euroToEurocent(euro);
    int length = eurocent.length();
    return (length == 1 ? "00" + eurocent : (length == 2 ? "0" + eurocent : eurocent));
  }

  public static String euroToString(BigDecimal euro, String separatoreDecimali) {

    String eurocent = euroToEurocent(euro);
    int length = eurocent.length();
    eurocent = (length == 1 ? "00" + eurocent : (length == 2 ? "0" + eurocent : eurocent));
    length = eurocent.length();
    return eurocent.substring(0, length - 2) + separatoreDecimali + eurocent.substring(length - 2);
  }
}
