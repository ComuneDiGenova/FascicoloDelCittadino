package it.liguriadigitale.ponmetro.portale.pojo.enums;

public enum ScaricaPrivacyEnum {
  PRIVACY_FDC(1),
  PRIVACY_SEBINA(2);

  private int id;

  private ScaricaPrivacyEnum(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
