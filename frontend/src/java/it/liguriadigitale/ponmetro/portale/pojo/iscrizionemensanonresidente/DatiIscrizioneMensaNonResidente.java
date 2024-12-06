package it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente;

import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import java.io.Serializable;

public class DatiIscrizioneMensaNonResidente implements Serializable {

  private static final long serialVersionUID = -4873309961367951199L;

  private DatiGenitoreExtend datiGenitore;

  private DatiFiglioExtend datiBambino;

  private FeaturesGeoserver autocompleteViario;

  private String datiResidenzaDomicilioCoincidonoGenitore;

  private String datiResidenzaDomicilioCoincidonoFiglio;

  public DatiGenitoreExtend getDatiGenitore() {
    return datiGenitore;
  }

  public void setDatiGenitore(DatiGenitoreExtend datiGenitore) {
    this.datiGenitore = datiGenitore;
  }

  public DatiFiglioExtend getDatiBambino() {
    return datiBambino;
  }

  public void setDatiBambino(DatiFiglioExtend datiBambino) {
    this.datiBambino = datiBambino;
  }

  public String getDatiResidenzaDomicilioCoincidonoGenitore() {
    return datiResidenzaDomicilioCoincidonoGenitore;
  }

  public void setDatiResidenzaDomicilioCoincidonoGenitore(
      String datiResidenzaDomicilioCoincidonoGenitore) {
    this.datiResidenzaDomicilioCoincidonoGenitore = datiResidenzaDomicilioCoincidonoGenitore;
  }

  public String getDatiResidenzaDomicilioCoincidonoFiglio() {
    return datiResidenzaDomicilioCoincidonoFiglio;
  }

  public void setDatiResidenzaDomicilioCoincidonoFiglio(
      String datiResidenzaDomicilioCoincidonoFiglio) {
    this.datiResidenzaDomicilioCoincidonoFiglio = datiResidenzaDomicilioCoincidonoFiglio;
  }

  public FeaturesGeoserver getAutocompleteViario() {
    return autocompleteViario;
  }

  public void setAutocompleteViario(FeaturesGeoserver autocompleteViario) {
    this.autocompleteViario = autocompleteViario;
  }

  @Override
  public String toString() {
    return "DatiIscrizioneMensaNonResidente [datiGenitore="
        + datiGenitore
        + ", datiBambino="
        + datiBambino
        + ", autocompleteViario="
        + autocompleteViario
        + ", datiResidenzaDomicilioCoincidonoGenitore="
        + datiResidenzaDomicilioCoincidonoGenitore
        + ", datiResidenzaDomicilioCoincidonoFiglio="
        + datiResidenzaDomicilioCoincidonoFiglio
        + "]";
  }
}
