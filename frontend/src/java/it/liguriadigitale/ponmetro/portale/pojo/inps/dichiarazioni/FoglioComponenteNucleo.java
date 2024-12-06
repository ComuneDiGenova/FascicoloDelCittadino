package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;

public class FoglioComponenteNucleo implements Serializable {

  private static final long serialVersionUID = 335482646996298997L;

  private String codiceFiscale;
  private DatiComponente datiComponente;
  private PatrimonioImmobiliare patrimonioImmobiliare;
  private PatrimonioMobiliare patrimonioMobiliare;
  private RedditiDaDichiarare redditiDaDichiarare;
  private Veicoli veicoli;
  private AltriRedditi altriRedditi;

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public DatiComponente getDatiComponente() {
    return datiComponente;
  }

  public void setDatiComponente(DatiComponente datiComponente) {
    this.datiComponente = datiComponente;
  }

  public PatrimonioImmobiliare getPatrimonioImmobiliare() {
    return patrimonioImmobiliare;
  }

  public void setPatrimonioImmobiliare(PatrimonioImmobiliare patrimonioImmobiliare) {
    this.patrimonioImmobiliare = patrimonioImmobiliare;
  }

  public PatrimonioMobiliare getPatrimonioMobiliare() {
    return patrimonioMobiliare;
  }

  public void setPatrimonioMobiliare(PatrimonioMobiliare patrimonioMobiliare) {
    this.patrimonioMobiliare = patrimonioMobiliare;
  }

  public RedditiDaDichiarare getRedditiDaDichiarare() {
    return redditiDaDichiarare;
  }

  public void setRedditiDaDichiarare(RedditiDaDichiarare redditiDaDichiarare) {
    this.redditiDaDichiarare = redditiDaDichiarare;
  }

  public Veicoli getVeicoli() {
    return veicoli;
  }

  public void setVeicoli(Veicoli veicoli) {
    this.veicoli = veicoli;
  }

  public AltriRedditi getAltriRedditi() {
    return altriRedditi;
  }

  public void setAltriRedditi(AltriRedditi altriRedditi) {
    this.altriRedditi = altriRedditi;
  }

  @Override
  public String toString() {
    return "FoglioComponenteNucleo [codiceFiscale="
        + codiceFiscale
        + ", datiComponente="
        + datiComponente
        + ", patrimonioImmobiliare="
        + patrimonioImmobiliare
        + ", patrimonioMobiliare="
        + patrimonioMobiliare
        + ", redditiDaDichiarare="
        + redditiDaDichiarare
        + ", veicoli="
        + veicoli
        + ", altriRedditi="
        + altriRedditi
        + "]";
  }
}
