package it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento;

/**
 * TrContratti
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen null! Non modificare! Creato il:
 * 2022-11-07T15:37:26.129
 */
public class TrContrattiBuilder {

  public String numContratto;

  public String numeroCliente;

  public TrContrattiBuilder getInstance() {
    return new TrContrattiBuilder();
  }

  public TrContrattiBuilder addNumContratto(String numContratto) {
    this.numContratto = numContratto;
    return this;
  }

  public TrContrattiBuilder addNumeroCliente(String numeroCliente) {
    this.numeroCliente = numeroCliente;
    return this;
  }

  public TrContratti build() {
    TrContratti obj = new TrContratti();
    obj.setNumContratto(this.numContratto);
    obj.setNumeroCliente(this.numeroCliente);
    return obj;
  }
}
