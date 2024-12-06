package it.liguriadigitale.ponmetro.portale.pojo.util;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;

public class PojoUtils {
  public static <T1 extends T2, T2> T1 copyAndReturn(T1 to, T2 from) {
    try {
      BeanUtils.copyProperties(to, from);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return to;
  }
}
