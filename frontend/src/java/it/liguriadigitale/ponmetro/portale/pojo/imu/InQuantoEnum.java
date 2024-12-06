package it.liguriadigitale.ponmetro.portale.pojo.imu;

public enum InQuantoEnum {
  PROPRIETARIO_TITOLARE("Proprietario/ Titolare di diritto reale"),
  EREDE("Erede"),
  ALTRO("Altro");

  private final String inQuanto;

  InQuantoEnum(final String text) {
    this.inQuanto = text;
  }

  public static InQuantoEnum getById(String id) {
    for (InQuantoEnum e : values()) {
      if (e.inQuanto.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return inQuanto;
  }
}
