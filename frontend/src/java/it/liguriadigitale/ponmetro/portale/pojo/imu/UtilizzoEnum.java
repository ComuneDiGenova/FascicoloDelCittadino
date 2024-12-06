package it.liguriadigitale.ponmetro.portale.pojo.imu;

public enum UtilizzoEnum {
  U1("Abitazione principale"),
  U2("Pertinenza dell'abitazione principale"),
  U3("Concesso in comodato gratuito"),
  U4("Locato a canone concordato (Legge 09/12/1998, n.431, art. 2, com. 3)"),
  U5("Utilizzo diretto dell'esercizio d'impresa (cat. C1, C3)"),
  U6("Altro");

  private final String categoria;

  UtilizzoEnum(final String text) {
    this.categoria = text;
  }

  public static UtilizzoEnum getById(String id) {
    for (UtilizzoEnum e : values()) {
      if (e.categoria.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return categoria;
  }
}
