package it.liguriadigitale.ponmetro.portale.pojo.enums;

public enum PortateMensaEnum {
  MENU("00", "icon-primo-piatto col-3", "Menù"),
  PANE("90", "icon-pane col-3", "Pane"),
  PRIMO("30", "icon-primo-piatto col-3", "Primo piatto"),
  SECONDO("40", "icon-secondo-piatto col-3", "Secondo Piatto"),
  CONTORNO("50", "icon-contorno col-3", "Contorno"),
  FRUTTA("60", "icon-frutta col-3", "Frutta"),
  DESSERT("70", "icon-dessert col-3", "Dessert"),
  MERENDA("80", "icon-merenda col-3", "Merenda"),
  COLAZIONE("10", "icon-colazione col-3", "Colazione"),
  SPUNTINO("20", "icon-spuntino col-3", "Spuntino");

  private String codice;
  private String icona;
  private String description;

  private PortateMensaEnum(String codice, String icona, String description) {
    this.codice = codice;
    this.icona = icona;
    this.description = description;
  }

  public String getCodice() {
    return codice;
  }

  public String getIcona() {
    return icona;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return String.valueOf(codice);
  }

  public static PortateMensaEnum fromValue(String codice) {
    for (PortateMensaEnum b : PortateMensaEnum.values()) {
      if (b.codice.equals(codice)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected codice '" + codice + "'");
  }
}
