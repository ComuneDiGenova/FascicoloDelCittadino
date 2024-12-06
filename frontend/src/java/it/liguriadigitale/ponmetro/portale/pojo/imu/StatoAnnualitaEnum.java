package it.liguriadigitale.ponmetro.portale.pojo.imu;

public enum StatoAnnualitaEnum {
  C("In Compilazione"),
  P("Presentata"),
  S("Sospesa"),
  L("Liquidata"),
  R("Diniegata"),
  T("Approvata"),
  A("Annullata");

  private final String stato;

  StatoAnnualitaEnum(final String text) {
    this.stato = text;
  }

  public static StatoAnnualitaEnum getById(String id) {
    for (StatoAnnualitaEnum e : values()) {
      if (e.stato.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return stato;
  }
}
