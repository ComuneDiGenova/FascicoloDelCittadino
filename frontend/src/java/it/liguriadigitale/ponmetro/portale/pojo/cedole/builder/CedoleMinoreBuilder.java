package it.liguriadigitale.ponmetro.portale.pojo.cedole.builder;

import it.liguriadigitale.ponmetro.portale.pojo.cedole.CedoleMinore;
import it.liguriadigitale.ponmetro.portale.pojo.cedole.Minore;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;

public class CedoleMinoreBuilder {

  private Cedola cedola;
  private Minore minore;
  private Boolean errore = false;
  private Integer anno;
  private Boolean datiIscrizioneAnnoCorrente = true;

  public CedoleMinoreBuilder getInstance() {
    return new CedoleMinoreBuilder();
  }

  public CedoleMinoreBuilder addCedola(Cedola cedola) {
    this.cedola = cedola;
    return this;
  }

  public CedoleMinoreBuilder addMinore(Minore minore) {
    this.minore = minore;
    return this;
  }

  public CedoleMinoreBuilder addErrore(Boolean errore) {
    this.errore = errore;
    return this;
  }

  public CedoleMinoreBuilder addAnno(Integer anno) {
    this.anno = anno;
    return this;
  }

  public CedoleMinoreBuilder addDatiIscrizioneAnnoCorrente(Boolean datiIscrizioneAnnoCorrente) {
    this.datiIscrizioneAnnoCorrente = datiIscrizioneAnnoCorrente;
    return this;
  }

  public CedoleMinore build() {

    CedoleMinore cedoleMinore = new CedoleMinore();
    cedoleMinore.setAnno(anno);
    cedoleMinore.setCedola(cedola);
    cedoleMinore.setDatiIscrizioneAnnoCorrente(datiIscrizioneAnnoCorrente);
    cedoleMinore.setErrore(errore);
    cedoleMinore.setMinore(minore);
    return cedoleMinore;
  }
}
