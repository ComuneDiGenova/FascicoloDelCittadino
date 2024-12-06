package it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni;

import it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni.Rapporto;
import java.io.Serializable;
import java.time.LocalDate;

public class OmissioneDifformita implements Serializable {

  private static final long serialVersionUID = -2654602565609716063L;

  private String TipoOmissioneDifformita;
  private String CodiceFiscale;
  private LocalDate DataControlloAE;
  private Rapporto[] rapporto;

  public Rapporto[] getRapporto() {
    return rapporto;
  }

  public void setRapporto(Rapporto[] rapporto) {
    this.rapporto = rapporto;
  }

  public String getTipoOmissioneDifformita() {
    return TipoOmissioneDifformita;
  }

  public void setTipoOmissioneDifformita(String tipoOmissioneDifformita) {
    TipoOmissioneDifformita = tipoOmissioneDifformita;
  }

  public String getCodiceFiscale() {
    return CodiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    CodiceFiscale = codiceFiscale;
  }

  public LocalDate getDataControlloAE() {
    return DataControlloAE;
  }

  public void setDataControlloAE(LocalDate dataControlloAE) {
    DataControlloAE = dataControlloAE;
  }

  @Override
  public String toString() {
    return "OmissioneDifformita [TipoOmissioneDifformita="
        + TipoOmissioneDifformita
        + ", CodiceFiscale="
        + CodiceFiscale
        + ", DataControlloAE="
        + DataControlloAE
        + ", rapporto="
        + rapporto
        + "]";
  }
}
