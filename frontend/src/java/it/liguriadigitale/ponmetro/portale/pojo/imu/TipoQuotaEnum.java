package it.liguriadigitale.ponmetro.portale.pojo.imu;

public enum TipoQuotaEnum {
  Q1("Quota Comune"),
  Q2("Quota Stato"),
  Q3("Quota Comune + Quota Stato");

  private final String inQuanto;

  TipoQuotaEnum(final String text) {
    this.inQuanto = text;
  }

  public static TipoQuotaEnum getById(String id) {
    for (TipoQuotaEnum e : values()) {
      if (e.inQuanto.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return inQuanto;
  }
}
