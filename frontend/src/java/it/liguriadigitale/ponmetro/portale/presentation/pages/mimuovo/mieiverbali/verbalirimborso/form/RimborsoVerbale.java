package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirimborso.form;

import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza.ModalitaPagamentoEnum;
import java.io.Serializable;

public class RimborsoVerbale implements Serializable {

  private static final long serialVersionUID = 1L;

  private ModalitaPagamentoEnum tipoLiquidazione;
  private String iban;
  private String swift;

  public ModalitaPagamentoEnum getTipoLiquidazione() {
    return tipoLiquidazione;
  }

  public void setTipoLiquidazione(ModalitaPagamentoEnum tipoLiquidazione) {
    this.tipoLiquidazione = tipoLiquidazione;
  }

  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public String getSwift() {
    return swift;
  }

  public void setSwift(String swift) {
    this.swift = swift;
  }

  public String getTipoLiquidazioneHermes() {
    return tipoLiquidazione.equals(ModalitaPagamentoEnum.CAB) ? "I" : "C";
  }
}
