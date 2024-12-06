package it.liguriadigitale.ponmetro.portale.pojo.imu;

public enum ModalitaPagamentoEnum {
  RITIRO(
      "Ritiro presso la tesoreria comunale Unicredit (possibile solo per importi fino a 999,99€)"),
  IBAN("Bonifico bancario o postale su conto corrente");

  private final String categoria;

  ModalitaPagamentoEnum(final String text) {
    this.categoria = text;
  }

  public static ModalitaPagamentoEnum getById(String id) {
    for (ModalitaPagamentoEnum e : values()) {
      if (e.categoria.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return categoria;
  }
}
