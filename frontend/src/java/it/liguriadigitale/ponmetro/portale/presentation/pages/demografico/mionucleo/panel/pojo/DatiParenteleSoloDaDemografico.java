package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mionucleo.panel.pojo;

import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale;
import java.io.Serializable;
import java.time.LocalDate;

public class DatiParenteleSoloDaDemografico implements Serializable {

  private static final long serialVersionUID = 5313505252776252647L;

  private String codiceFiscale;

  private String relazione;

  private String nomeCognome;

  private LocalDate dataNascita;

  private String luogoNascita;

  private String genere;

  private ItemRelazioneParentale itemRelazioneParentale;

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getRelazione() {
    return relazione;
  }

  public void setRelazione(String relazione) {
    this.relazione = relazione;
  }

  public String getNomeCognome() {
    return nomeCognome;
  }

  public void setNomeCognome(String nomeCognome) {
    this.nomeCognome = nomeCognome;
  }

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  public String getLuogoNascita() {
    return luogoNascita;
  }

  public void setLuogoNascita(String luogoNascita) {
    this.luogoNascita = luogoNascita;
  }

  public String getGenere() {
    return genere;
  }

  public void setGenere(String genere) {
    this.genere = genere;
  }

  public ItemRelazioneParentale getItemRelazioneParentale() {
    return itemRelazioneParentale;
  }

  public void setItemRelazioneParentale(ItemRelazioneParentale itemRelazioneParentale) {
    this.itemRelazioneParentale = itemRelazioneParentale;
  }

  @Override
  public String toString() {
    return "DatiParenteleSoloDaDemografico [codiceFiscale="
        + codiceFiscale
        + ", relazione="
        + relazione
        + ", nomeCognome="
        + nomeCognome
        + ", dataNascita="
        + dataNascita
        + ", luogoNascita="
        + luogoNascita
        + ", genere="
        + genere
        + ", itemRelazioneParentale="
        + itemRelazioneParentale
        + "]";
  }
}
