package it.liguriadigitale.ponmetro.portale.pojo.imu;

public enum TipoAllegatoEnum {
  VERSAMENTO_TRIBUTARIO("DOCUMENTAZIONE ATTESTANTE I VERSAMENTI TRIBUTARI EFFETTUATI"),
  DELEGA_COERDE("DELEGA COEREDE"),
  AUTOCERTIFICAZIONE_EREDI("AUTOCERTIFICAZIONE EREDI"),
  DELEGA_RITIRO("DOCUMENTO D'IDENTITA' DEL DELEGATO AL RITIRO"),
  ALTRI_ALLEGATI("ALTRI ALLEGATI");

  private final String categoria;

  TipoAllegatoEnum(final String text) {
    this.categoria = text;
  }

  public static TipoAllegatoEnum getById(String id) {
    for (TipoAllegatoEnum e : values()) {
      if (e.categoria.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return categoria;
  }
}
