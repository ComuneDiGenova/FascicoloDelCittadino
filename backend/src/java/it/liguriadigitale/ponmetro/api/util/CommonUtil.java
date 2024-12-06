package it.liguriadigitale.ponmetro.api.util;

import java.util.List;
import java.util.stream.Collectors;

public class CommonUtil {

  @SuppressWarnings("unused")
  public static <T> List<T> manageOffsetAndLimit(List<T> list, Integer offset, Integer limit) {
    return list.stream()
        .skip((offset == null || Integer.compare(offset, -1) == 0) ? 0 : offset.longValue())
        .limit(
            (limit == null || Integer.compare(limit, -1) == 0 || Integer.compare(limit, 0) == 0)
                ? 999999
                : limit.longValue())
        .collect(Collectors.toList());
  }

  @SuppressWarnings("unused")
  public static <T> List<T> manageOffsetAndLimit(List<T> list, long offset, long limit) {
    return manageOffsetAndLimit(list, Math.toIntExact(offset), Math.toIntExact(limit));
  }
}
