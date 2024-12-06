package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.pagopa;

import it.liguriadigitale.ponmetro.tassaauto.model.DatiAvvioPagamento;

public class DatiAvvioPagoPa extends DatiAvvioPagamento {

  private static final long serialVersionUID = -1266881022109881887L;

  public DatiAvvioPagoPa() {
    super();
  }

  public DatiAvvioPagoPa(DatiAvvioPagamento datiAvvioPagamento) {
    setAlias(datiAvvioPagamento.getAlias());
    setAnagraficaPagatore(datiAvvioPagamento.getAnagraficaPagatore());
    setCodTrans(datiAvvioPagamento.getCodTrans());
    setDivisa(datiAvvioPagamento.getDivisa());
    setIdentificativoFiscalePagatore(datiAvvioPagamento.getIdentificativoFiscalePagatore());
    setImporto(datiAvvioPagamento.getImporto());
    setMac(datiAvvioPagamento.getMac());
    setMail(datiAvvioPagamento.getMail());
    setTipoPagatore(datiAvvioPagamento.getTipoPagatore());
    setUrl(datiAvvioPagamento.getUrl());
    setUrlAction(datiAvvioPagamento.getUrlAction());
    setUrlpost(datiAvvioPagamento.getUrlpost());
    setUrlBack(datiAvvioPagamento.getUrlBack());

    this.url_back = datiAvvioPagamento.getUrlBack();
  }

  private String url_back;

  public String getUrl_back() {
    return url_back;
  }

  public void setUrl_back(String url_back) {
    this.url_back = url_back;
  }
}
