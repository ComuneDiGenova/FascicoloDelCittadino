package it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati;

import java.io.Serializable;

public class Veicolo implements Serializable {

  private static final long serialVersionUID = -1957954324433937884L;

  String targaAutoVeicoloProprioODelFamiliare;
  String codiceFiscaleAutoVeicoloProprioODelFamiliare;
  String nomeProprietarioAutoVeicoloProprioODelFamiliare;
  String cognomeProprietarioAutoVeicoloProprioODelFamiliare;

  public String getTargaAutoVeicoloProprioODelFamiliare() {
    return targaAutoVeicoloProprioODelFamiliare;
  }

  public void setTargaAutoVeicoloProprioODelFamiliare(String targaAutoVeicoloProprioODelFamiliare) {
    this.targaAutoVeicoloProprioODelFamiliare = targaAutoVeicoloProprioODelFamiliare;
  }

  public String getCodiceFiscaleAutoVeicoloProprioODelFamiliare() {
    return codiceFiscaleAutoVeicoloProprioODelFamiliare;
  }

  public void setCodiceFiscaleAutoVeicoloProprioODelFamiliare(
      String codiceFiscaleAutoVeicoloProprioODelFamiliare) {
    this.codiceFiscaleAutoVeicoloProprioODelFamiliare =
        codiceFiscaleAutoVeicoloProprioODelFamiliare;
  }

  public String getNomeProprietarioAutoVeicoloProprioODelFamiliare() {
    return nomeProprietarioAutoVeicoloProprioODelFamiliare;
  }

  public void setNomeProprietarioAutoVeicoloProprioODelFamiliare(
      String nomeProprietarioAutoVeicoloProprioODelFamiliare) {
    this.nomeProprietarioAutoVeicoloProprioODelFamiliare =
        nomeProprietarioAutoVeicoloProprioODelFamiliare;
  }

  public String getCognomeProprietarioAutoVeicoloProprioODelFamiliare() {
    return cognomeProprietarioAutoVeicoloProprioODelFamiliare;
  }

  public void setCognomeProprietarioAutoVeicoloProprioODelFamiliare(
      String cognomeProprietarioAutoVeicoloProprioODelFamiliare) {
    this.cognomeProprietarioAutoVeicoloProprioODelFamiliare =
        cognomeProprietarioAutoVeicoloProprioODelFamiliare;
  }
}
