package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto;

import java.io.Serializable;

public class AmtPromozioneXXLSmall implements Serializable {

  private static final long serialVersionUID = 4363101265527981715L;

  private String cityPass;

  private DatiPersonaliAmt titolare;

  private DatiPersonaliAmt richiedente;

  private boolean residente;

  private boolean agevolazione;

  // private String protocolloIsee;

  public String getCityPass() {
    return cityPass;
  }

  public void setCityPass(String cityPass) {
    this.cityPass = cityPass;
  }

  public DatiPersonaliAmt getTitolare() {
    return titolare;
  }

  public void setTitolare(DatiPersonaliAmt titolare) {
    this.titolare = titolare;
  }

  public DatiPersonaliAmt getRichiedente() {
    return richiedente;
  }

  public void setRichiedente(DatiPersonaliAmt richiedente) {
    this.richiedente = richiedente;
  }

  public boolean isResidente() {
    return residente;
  }

  public void setResidente(boolean residente) {
    this.residente = residente;
  }

  public boolean isAgevolazione() {
    return agevolazione;
  }

  public void setAgevolazione(boolean agevolazione) {
    this.agevolazione = agevolazione;
  }

  /*public String getProtocolloIsee() {
  	return protocolloIsee;
  }

  public void setProtocolloIsee(String protocolloIsee) {
  	this.protocolloIsee = protocolloIsee;
  }*/

  @Override
  public String toString() {
    return "AmtPromozioneXXLSmall [cityPass="
        + cityPass
        + ", titolare="
        + titolare
        + ", richiedente="
        + richiedente
        + ", residente="
        + residente
        + ", agevolazione="
        + agevolazione
        + "]";
  }
}
