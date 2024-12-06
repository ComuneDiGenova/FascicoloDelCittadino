package it.liguriadigitale.ponmetro.portale.presentation.components.util;

public enum EnumTipoDomandaPermessoPersonalizzato {
  DISABILE_GUIDATORE_RESIDENZA("DGR"),
  DISABILE_GUIDATORE_LAVORO("DGL"),
  DISABILE_ACCOMPAGNATO("DAC"),
  DISABILE_MINORE("DMI"),
  DISABILE_TUTORE("DMT"),
  UNKNOWN("UNK");

  private final String idTipoDomanda;

  EnumTipoDomandaPermessoPersonalizzato(final String text) {
    this.idTipoDomanda = text;
  }

  @Override
  public String toString() {
    return idTipoDomanda;
  }

  public static EnumTipoDomandaPermessoPersonalizzato getById(String id) {
    for (EnumTipoDomandaPermessoPersonalizzato e : values()) {
      if (e.idTipoDomanda.equalsIgnoreCase(id)) return e;
    }
    return UNKNOWN;
  }
}
