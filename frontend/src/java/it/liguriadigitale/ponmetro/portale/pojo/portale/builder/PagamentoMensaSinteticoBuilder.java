package it.liguriadigitale.ponmetro.portale.pojo.portale.builder;

import it.liguriadigitale.ponmetro.portale.pojo.portale.PagamentoMensaSintetico;

public class PagamentoMensaSinteticoBuilder {
  private String annoScolastico;
  private Long annoScolasticoPrimoNumero;
  private String codiceFiscaleImpegnato;
  private String situazionePagamento;
  private String metodoPagamento;

  public PagamentoMensaSinteticoBuilder setAnnoScolastico(String annoScolastico) {
    this.annoScolastico = annoScolastico;
    return this;
  }

  public PagamentoMensaSinteticoBuilder setAnnoScolasticoPrimoNumero(
      Long annoScolasticoPrimoNumero) {
    this.annoScolasticoPrimoNumero = annoScolasticoPrimoNumero;
    return this;
  }

  public PagamentoMensaSinteticoBuilder setCodiceFiscaleImpegnato(String codiceFiscaleImpegnato) {
    this.codiceFiscaleImpegnato = codiceFiscaleImpegnato;
    return this;
  }

  public PagamentoMensaSinteticoBuilder setSituazionePagamento(String situazionePagamento) {
    this.situazionePagamento = situazionePagamento;
    return this;
  }

  public PagamentoMensaSinteticoBuilder setMetodoPagamento(String metodoPagamento) {
    this.metodoPagamento = metodoPagamento;
    return this;
  }

  public PagamentoMensaSintetico build() {
    PagamentoMensaSintetico toRet = new PagamentoMensaSintetico();
    toRet.setAnnoScolastico(annoScolastico);
    toRet.setAnnoScolasticoPrimoNumero(annoScolasticoPrimoNumero);
    toRet.setCodiceFiscaleImpegnato(codiceFiscaleImpegnato);
    toRet.setSituazionePagamento(situazionePagamento);
    toRet.setMetodoPagamento(metodoPagamento);
    return toRet;
  }
}
