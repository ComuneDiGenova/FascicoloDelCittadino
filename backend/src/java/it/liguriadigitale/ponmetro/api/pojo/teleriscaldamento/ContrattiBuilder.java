package it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento;

import it.liguriadigitale.ponmetro.teleriscaldamento.model.Contratto;

public class ContrattiBuilder {

  private String numeroContratto;

  private String numeroCliente;

  public static ContrattiBuilder getInstance() {
    return new ContrattiBuilder();
  }

  public ContrattiBuilder addNumeroContratto(String numeroContratto) {
    this.numeroContratto = numeroContratto;
    return this;
  }

  public ContrattiBuilder addNumeroCliente(String numeroCliente) {
    this.numeroCliente = numeroCliente;
    return this;
  }

  public Contratto build() {
    Contratto contratto = new Contratto();

    contratto.setNumeroCliente(numeroCliente);
    contratto.setNumeroContratto(numeroContratto);

    return contratto;
  }
}
