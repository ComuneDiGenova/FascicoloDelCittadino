package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.seggielettorali.pojo;

import java.io.Serializable;

public class TipoPagamento implements Serializable {

  private static final long serialVersionUID = 1223150628039543162L;

  int idTipoPagamento;
  String descTipoPagamento;

  public int getIdTipoPagamento() {
    return idTipoPagamento;
  }

  public void setIdTipoPagamento(int idTipoPagamento) {
    this.idTipoPagamento = idTipoPagamento;
  }

  public String getDescTipoPagamento() {
    return descTipoPagamento;
  }

  public void setDescTipoPagamento(String descTipoPagamento) {
    this.descTipoPagamento = descTipoPagamento;
  }
}
