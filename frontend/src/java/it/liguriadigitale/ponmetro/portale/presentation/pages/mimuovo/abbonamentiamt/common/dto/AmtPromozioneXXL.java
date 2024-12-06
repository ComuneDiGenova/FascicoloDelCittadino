package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto;

import java.io.Serializable;

public class AmtPromozioneXXL implements Serializable {

  private static final long serialVersionUID = 4386244801199605759L;

  private String promozioneXXLAttiva;

  private String urlNuovaTessera;

  private String urlRinnovo;

  private String cityPass;

  private DatiPersonaliAmt titolare;

  private DatiPersonaliAmt richiedente;

  private boolean residente;

  private boolean agevolazione;

  private Double importoIsee;

  private String protocolloIsee;

  public AmtPromozioneXXL() {
    super();
  }

  public String getPromozioneXXLAttiva() {
    return promozioneXXLAttiva;
  }

  public void setPromozioneXXLAttiva(String promozioneXXLAttiva) {
    this.promozioneXXLAttiva = promozioneXXLAttiva;
  }

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

  public Double getImportoIsee() {
    return importoIsee;
  }

  public void setImportoIsee(Double importoIsee) {
    this.importoIsee = importoIsee;
  }

  public String getUrlNuovaTessera() {
    return urlNuovaTessera;
  }

  public void setUrlNuovaTessera(String urlNuovaTessera) {
    this.urlNuovaTessera = urlNuovaTessera;
  }

  public String getUrlRinnovo() {
    return urlRinnovo;
  }

  public void setUrlRinnovo(String urlRinnovo) {
    this.urlRinnovo = urlRinnovo;
  }

  public String getProtocolloIsee() {
    return protocolloIsee;
  }

  public void setProtocolloIsee(String protocolloIsee) {
    this.protocolloIsee = protocolloIsee;
  }

  @Override
  public String toString() {
    return "AmtPromozioneXXL [promozioneXXLAttiva="
        + promozioneXXLAttiva
        + ", urlNuovaTessera="
        + urlNuovaTessera
        + ", urlRinnovo="
        + urlRinnovo
        + ", cityPass="
        + cityPass
        + ", titolare="
        + titolare
        + ", richiedente="
        + richiedente
        + ", residente="
        + residente
        + ", agevolazione="
        + agevolazione
        + ", importoIsee="
        + importoIsee
        + ", protocolloIsee="
        + protocolloIsee
        + "]";
  }
}
