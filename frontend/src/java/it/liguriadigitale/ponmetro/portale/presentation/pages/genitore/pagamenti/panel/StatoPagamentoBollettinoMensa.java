package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel;

public enum StatoPagamentoBollettinoMensa {
  PAGAMENTO_DA_EFFETTUARE("PAGAMENTO DA EFFETTUARE"),
  PAGAMENTO_EFFETTUATO("PAGAMENTO EFFETTUATO");

  private final String categoria;

  StatoPagamentoBollettinoMensa(String text) {
    // TODO Auto-generated constructor stub
    this.categoria = text;
  }

  public static StatoPagamentoBollettinoMensa getById(String id) {
    for (StatoPagamentoBollettinoMensa e : values()) {
      if (e.categoria.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return categoria;
  }
}
