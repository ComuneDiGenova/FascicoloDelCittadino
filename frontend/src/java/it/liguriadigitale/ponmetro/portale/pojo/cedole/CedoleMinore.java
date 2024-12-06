package it.liguriadigitale.ponmetro.portale.pojo.cedole;

import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import java.io.Serializable;

public class CedoleMinore implements Serializable {

  private static final long serialVersionUID = -3667226466269574755L;

  private Cedola cedola;
  private Minore minore;
  private Boolean errore = false;
  private Integer anno;
  private Boolean datiIscrizioneAnnoCorrente = true;

  public Cedola getCedola() {
    return cedola;
  }

  public void setCedola(Cedola cedola) {
    this.cedola = cedola;
  }

  public Minore getMinore() {
    return minore;
  }

  public void setMinore(Minore minore) {
    this.minore = minore;
  }

  public Boolean getErrore() {
    return errore;
  }

  public void setErrore(Boolean errore) {
    this.errore = errore;
  }

  public Integer getAnno() {
    return anno;
  }

  public void setAnno(Integer anno) {
    this.anno = anno;
  }

  public Boolean getDatiIscrizioneAnnoCorrente() {
    return datiIscrizioneAnnoCorrente;
  }

  public void setDatiIscrizioneAnnoCorrente(Boolean datiIscrizioneAnnoCorrente) {
    this.datiIscrizioneAnnoCorrente = datiIscrizioneAnnoCorrente;
  }
}
