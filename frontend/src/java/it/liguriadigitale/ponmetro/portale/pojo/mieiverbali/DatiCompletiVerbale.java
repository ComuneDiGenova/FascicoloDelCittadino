package it.liguriadigitale.ponmetro.portale.pojo.mieiverbali;

import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.io.Serializable;
import java.util.stream.Collectors;

public class DatiCompletiVerbale implements Serializable {

  private static final long serialVersionUID = 1L;

  private boolean selezionato;
  private DettaglioVerbale dettaglioVerbale;
  private Verbale verbale;

  public DatiCompletiVerbale() {}

  public boolean isSelezionato() {
    return selezionato;
  }

  public void setSelezionato(boolean selezionato) {
    this.selezionato = selezionato;
  }

  public DatiCompletiVerbale(Verbale verbale, DettaglioVerbale dettaglioVerbale) {
    this.verbale = verbale;
    this.dettaglioVerbale = dettaglioVerbale;
  }

  public DettaglioVerbale getDettaglioVerbale() {
    return dettaglioVerbale;
  }

  public void setDettaglioVerbale(DettaglioVerbale dettaglioVerbale) {
    this.dettaglioVerbale = dettaglioVerbale;
  }

  public Verbale getVerbale() {
    return verbale;
  }

  public void setVerbale(Verbale verbale) {
    this.verbale = verbale;
  }

  @Override
  public String toString() {
    return "DatiCompletiVerbale [dettaglioVerbale="
        + dettaglioVerbale
        + ", verbale="
        + verbale
        + "]";
  }

  public String getStringaCommaArticoli() {
    return (verbale.getArticoliViolati())
        .stream().map(articolo -> "" + articolo.getNumero()).collect(Collectors.toList()).stream()
            .collect(Collectors.joining(","));
  }

  public String getStringaSerie() {
    String dettaglioSerieVerbale = "";
    if (dettaglioVerbale.getSerieVerbale() != null) {
      dettaglioSerieVerbale =
          dettaglioVerbale
              .getSerieVerbale()
              .getCodiceSerie()
              .concat(" - ")
              .concat(dettaglioVerbale.getSerieVerbale().getDescrizioneSerie());
    }
    return dettaglioSerieVerbale;
  }
}
