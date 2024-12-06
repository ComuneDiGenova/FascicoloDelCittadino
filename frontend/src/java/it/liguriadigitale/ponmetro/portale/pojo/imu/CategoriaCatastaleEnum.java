package it.liguriadigitale.ponmetro.portale.pojo.imu;

public enum CategoriaCatastaleEnum {
  A01("A01 - Abitazioni di tipo signorile"),
  A02("A02 - Abitazioni di tipo civile"),
  A03("A03 - Abitazioni di tipo economico"),
  A04("A04 - Abitazioni di tipo popolare"),
  A05("A05 - Abitazioni di tipo ultrapopolare"),
  A06("A06 - Abitazioni di tipo rurale"),
  A07("A07 - Abitazioni in villini"),
  A08("A08 - Abitazioni in ville"),
  A11("A11 - Abitazioni ed alloggi tipici dei luoghi"),
  C01("C01 - Negozi e Botteghe"),
  C02("C02 - Magazzini e locali di deposito"),
  C06("C06 - Stalle, scuderie, rimesse, autorimesse (senza fine di lucro)");

  private final String categoria;

  CategoriaCatastaleEnum(final String text) {
    this.categoria = text;
  }

  public static CategoriaCatastaleEnum getById(String id) {
    for (CategoriaCatastaleEnum e : values()) {
      if (e.categoria.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return categoria;
  }
}
