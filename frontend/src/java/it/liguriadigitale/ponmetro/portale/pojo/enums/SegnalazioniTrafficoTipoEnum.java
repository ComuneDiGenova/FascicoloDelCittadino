package it.liguriadigitale.ponmetro.portale.pojo.enums;

import java.util.HashMap;
import java.util.Map;

public enum SegnalazioniTrafficoTipoEnum {
  NONE(0),
  ACCIDENT(1),
  GENERALOBSTRUCTION(2),
  ABNORMALTRAFFIC(4),
  ACTIVITIES(8),
  MAINTENANCEWORKS(16),
  CONSTRUCTIONWORKS(32),
  ROADSIDEASSISTANCE(64),
  TRAFFICRELATEDSITUATION(128),
  MARKET(256),
  GENERICEVENT(512),
  PUBLICEVENT(1024),
  UNKNOWN(999999);

  private Integer value;
  private static Map<Object, Object> map = new HashMap<>();

  private SegnalazioniTrafficoTipoEnum(Integer value) {
    this.value = value;
  }

  static {
    for (SegnalazioniTrafficoTipoEnum type : SegnalazioniTrafficoTipoEnum.values()) {
      map.put(type.value, type);
    }
  }

  public static SegnalazioniTrafficoTipoEnum valueOf(Integer type) {
    return (type == null) ? UNKNOWN : (SegnalazioniTrafficoTipoEnum) map.get(type);
  }

  public Integer getValue() {
    return value;
  }

  public static String getStringUnknown() {
    return UNKNOWN.toString();
  }
}
