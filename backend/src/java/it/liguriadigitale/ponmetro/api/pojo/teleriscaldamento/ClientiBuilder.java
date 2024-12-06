package it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento;

import it.liguriadigitale.ponmetro.teleriscaldamento.model.Cliente;

public class ClientiBuilder {

  private String numeroCliente;

  public static ClientiBuilder getInstance() {
    return new ClientiBuilder();
  }

  public ClientiBuilder addNumeroCliente(String numeroCliente) {
    this.numeroCliente = numeroCliente;
    return this;
  }

  public Cliente build() {
    Cliente cliente = new Cliente();

    cliente.setNumeroCliente(numeroCliente);

    return cliente;
  }
}
