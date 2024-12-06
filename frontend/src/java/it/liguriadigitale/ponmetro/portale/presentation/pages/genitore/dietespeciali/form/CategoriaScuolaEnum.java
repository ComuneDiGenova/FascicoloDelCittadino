package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

public enum CategoriaScuolaEnum {
  SCUOLA_PRIMARIA_STATALE("0000000005"),
  SECONDARIA_I_GRADO_STATALE("0000000003");

  private final String categoria;

  CategoriaScuolaEnum(final String text) {
    this.categoria = text;
  }

  @Override
  public String toString() {
    return categoria;
  }

  public static CategoriaScuolaEnum getById(String id) {
    for (CategoriaScuolaEnum e : values()) {
      if (e.categoria.equalsIgnoreCase(id)) return e;
    }
    return null;
  }
}
