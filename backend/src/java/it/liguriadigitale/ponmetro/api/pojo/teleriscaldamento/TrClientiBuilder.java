package it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento;

/**
 * TrClienti
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen null! Non modificare! Creato il:
 * 2022-11-07T15:35:43.459
 */
public class TrClientiBuilder {

  public String numeroCliente;

  public TrClientiBuilder getInstance() {
    return new TrClientiBuilder();
  }

  public TrClientiBuilder addNumeroCliente(String numeroCliente) {
    this.numeroCliente = numeroCliente;
    return this;
  }

  public TrClienti build() {
    TrClienti obj = new TrClienti();
    obj.setNumeroCliente(this.numeroCliente);
    return obj;
  }
}
