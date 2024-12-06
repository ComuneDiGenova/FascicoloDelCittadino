package it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati;

import java.io.Serializable;

public class DichiarazionePermessiPersonalizzati implements Serializable {

  private static final long serialVersionUID = -7486253340136093287L;

  private String codiceDichiarazione;
  private Boolean valoreDichiarazione;
  private String descrizioneDichiarazione;
  private String dichiarazioneAlternativa;

  private Veicolo veicolo;

  public String getCodiceDichiarazione() {
    return codiceDichiarazione;
  }

  public void setCodiceDichiarazione(String codiceDichiarazione) {
    this.codiceDichiarazione = codiceDichiarazione;
  }

  public Boolean getValoreDichiarazione() {
    return valoreDichiarazione;
  }

  public void setValoreDichiarazione(Boolean valoreDichiarazione) {
    this.valoreDichiarazione = valoreDichiarazione;
  }

  public String getDescrizioneDichiarazione() {
    return descrizioneDichiarazione;
  }

  public void setDescrizioneDichiarazione(String descrizioneDichiarazione) {
    this.descrizioneDichiarazione = descrizioneDichiarazione;
  }

  public String getDichiarazioneAlternativa() {
    return dichiarazioneAlternativa;
  }

  public void setDichiarazioneAlternativa(String dichiarazioneAlternativa) {
    this.dichiarazioneAlternativa = dichiarazioneAlternativa;
  }

  public Veicolo getVeicolo() {
    return veicolo;
  }

  public void setVeicolo(Veicolo veicolo) {
    this.veicolo = veicolo;
  }
}
