package it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente;

import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Comune;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Provincia;
import java.io.Serializable;

public class DatiNascitaResidenzaDomicilio implements Serializable {

  private static final long serialVersionUID = 8575791312948293993L;

  private Provincia autocompleteProvinciaNascita;

  private Comune autocompleteComuneNascita;

  private Provincia autocompleteProvinciaResidenza;

  private Comune autocompleteComuneResidenza;

  private Provincia autocompleteProvinciaDomicilio;

  private Comune autocompleteComuneDomicilio;

  private FeaturesGeoserver autocompleteViario;

  public Provincia getAutocompleteProvinciaNascita() {
    return autocompleteProvinciaNascita;
  }

  public void setAutocompleteProvinciaNascita(Provincia autocompleteProvinciaNascita) {
    this.autocompleteProvinciaNascita = autocompleteProvinciaNascita;
  }

  public Comune getAutocompleteComuneNascita() {
    return autocompleteComuneNascita;
  }

  public void setAutocompleteComuneNascita(Comune autocompleteComuneNascita) {
    this.autocompleteComuneNascita = autocompleteComuneNascita;
  }

  public Provincia getAutocompleteProvinciaResidenza() {
    return autocompleteProvinciaResidenza;
  }

  public void setAutocompleteProvinciaResidenza(Provincia autocompleteProvinciaResidenza) {
    this.autocompleteProvinciaResidenza = autocompleteProvinciaResidenza;
  }

  public Comune getAutocompleteComuneResidenza() {
    return autocompleteComuneResidenza;
  }

  public void setAutocompleteComuneResidenza(Comune autocompleteComuneResidenza) {
    this.autocompleteComuneResidenza = autocompleteComuneResidenza;
  }

  public Provincia getAutocompleteProvinciaDomicilio() {
    return autocompleteProvinciaDomicilio;
  }

  public void setAutocompleteProvinciaDomicilio(Provincia autocompleteProvinciaDomicilio) {
    this.autocompleteProvinciaDomicilio = autocompleteProvinciaDomicilio;
  }

  public Comune getAutocompleteComuneDomicilio() {
    return autocompleteComuneDomicilio;
  }

  public void setAutocompleteComuneDomicilio(Comune autocompleteComuneDomicilio) {
    this.autocompleteComuneDomicilio = autocompleteComuneDomicilio;
  }

  public FeaturesGeoserver getAutocompleteViario() {
    return autocompleteViario;
  }

  public void setAutocompleteViario(FeaturesGeoserver autocompleteViario) {
    this.autocompleteViario = autocompleteViario;
  }

  @Override
  public String toString() {
    return "DatiNascitaResidenzaDomicilio [autocompleteProvinciaNascita="
        + autocompleteProvinciaNascita
        + ", autocompleteComuneNascita="
        + autocompleteComuneNascita
        + ", autocompleteProvinciaResidenza="
        + autocompleteProvinciaResidenza
        + ", autocompleteComuneResidenza="
        + autocompleteComuneResidenza
        + ", autocompleteProvinciaDomicilio="
        + autocompleteProvinciaDomicilio
        + ", autocompleteComuneDomicilio="
        + autocompleteComuneDomicilio
        + ", autocompleteViario="
        + autocompleteViario
        + "]";
  }
}
