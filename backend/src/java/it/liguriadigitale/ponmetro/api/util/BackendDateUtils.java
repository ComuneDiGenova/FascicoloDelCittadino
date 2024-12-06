package it.liguriadigitale.ponmetro.api.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BackendDateUtils {
  private static Log log = LogFactory.getLog(BackendDateUtils.class);
  private static Integer _ID;

  // to avoid NoClassDefFoundError in different package
  public BackendDateUtils(Integer Id) {
    this._ID = Id;
  }

  public static OffsetDateTime fromLocalDateTimeToOffsetDateTime(
      final LocalDateTime localDateTime) {
    if (localDateTime != null) {
      ZoneOffset offset = OffsetDateTime.now().getOffset();
      return localDateTime.atOffset(offset);
    }
    return null;
  }
}
