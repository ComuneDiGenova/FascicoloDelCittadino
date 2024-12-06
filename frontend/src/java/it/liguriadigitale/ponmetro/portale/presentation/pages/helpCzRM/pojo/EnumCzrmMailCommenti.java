package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.pojo;

public enum EnumCzrmMailCommenti {
  E("EMAIL"),
  C("COMMENTI");

  private final String tipo;

  EnumCzrmMailCommenti(final String text) {
    this.tipo = text;
  }

  public static EnumCzrmMailCommenti getById(String id) {
    for (EnumCzrmMailCommenti e : values()) {
      if (e.tipo.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return tipo;
  }
}
