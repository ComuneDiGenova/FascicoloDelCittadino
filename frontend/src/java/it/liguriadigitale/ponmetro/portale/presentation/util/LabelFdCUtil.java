package it.liguriadigitale.ponmetro.portale.presentation.util;

import java.util.List;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LabelFdCUtil {

  protected static Log log = LogFactory.getLog(LabelFdCUtil.class);

  public static boolean checkIfNotNull(Object obj) {
    return Optional.ofNullable(obj).isPresent();
  }

  public static boolean checkIfNull(Object obj) {
    return !Optional.ofNullable(obj).isPresent();
  }

  public static boolean checkNotEmpty(String str) {
    return str.isEmpty();
  }

  public static boolean checkEmptyList(List<?> lista) {
    return lista.isEmpty();
  }

  public static String defaultString(String str) {
    return str == null || str.isEmpty() ? "" : str;
  }
}
