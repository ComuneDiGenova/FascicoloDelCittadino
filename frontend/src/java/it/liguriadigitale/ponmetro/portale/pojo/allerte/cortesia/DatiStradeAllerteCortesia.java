package it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia;

import it.liguriadigitale.ponmetro.allertecortesia.model.STRADE;
import it.liguriadigitale.ponmetro.allertecortesia.model.Tratti;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import java.io.Serializable;

public class DatiStradeAllerteCortesia implements Serializable {

  private static final long serialVersionUID = -4356559737456465792L;

  private FeaturesGeoserver autocompleteViario;

  private String email;

  private String idServizio;

  private String strada;

  private String start;

  private String length;

  private Tratti tratti;

  private STRADE stradaScelta;

  private int numeroOccorrenze;

  public FeaturesGeoserver getAutocompleteViario() {
    return autocompleteViario;
  }

  public void setAutocompleteViario(FeaturesGeoserver autocompleteViario) {
    this.autocompleteViario = autocompleteViario;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getIdServizio() {
    return idServizio;
  }

  public void setIdServizio(String idServizio) {
    this.idServizio = idServizio;
  }

  public String getStrada() {
    return strada;
  }

  public void setStrada(String strada) {
    this.strada = strada;
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public Tratti getTratti() {
    return tratti;
  }

  public void setTratti(Tratti tratti) {
    this.tratti = tratti;
  }

  public STRADE getStradaScelta() {
    return stradaScelta;
  }

  public void setStradaScelta(STRADE stradaScelta) {
    this.stradaScelta = stradaScelta;
  }

  public int getNumeroOccorrenze() {
    return numeroOccorrenze;
  }

  public void setNumeroOccorrenze(int numeroOccorrenze) {
    this.numeroOccorrenze = numeroOccorrenze;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DatiStradeAllerteCortesia [email=");
    builder.append(email);
    builder.append(", idServizio=");
    builder.append(idServizio);
    builder.append(", strada=");
    builder.append(strada);
    builder.append(", start=");
    builder.append(start);
    builder.append(", length=");
    builder.append(length);
    builder.append(", tratti=");
    builder.append(tratti);
    builder.append(", stradaScelta=");
    builder.append(stradaScelta);
    builder.append(", numeroOccorrenze=");
    builder.append(numeroOccorrenze);
    builder.append("]");
    return builder.toString();
  }
}
