package it.liguriadigitale.ponmetro.portale.pojo.enums;

public enum StatoIstanza {
  ACCOLTA("EVA"),
  ACCOLTA_PARZIALMENTE("EVP"),
  IN_ELABORAZIONE("PCR"),
  INVIATA("INV"),
  IN_COMPILAZIONE("CMP"),
  ATTESA_DI_INTEGRAZIONE("ATT"),
  RESPINTA("EVR");

  private final String value;

  StatoIstanza(final String st) {
    value = st;
  }
  ;

  public String toString() {
    return value;
  }
}
