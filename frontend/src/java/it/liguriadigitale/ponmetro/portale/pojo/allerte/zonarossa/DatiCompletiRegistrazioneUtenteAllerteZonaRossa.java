package it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa;

import java.io.Serializable;

public class DatiCompletiRegistrazioneUtenteAllerteZonaRossa implements Serializable {

  private static final long serialVersionUID = -4063373462307261549L;

  // STEP 1
  private DettagliUtenteZonaRossa dettagliUtenteZonaRossa;

  // STEP 2
  private CivicoZonaRossa civicoZonaRossa;

  // costr
  public DatiCompletiRegistrazioneUtenteAllerteZonaRossa() {
    dettagliUtenteZonaRossa = new DettagliUtenteZonaRossa();
    civicoZonaRossa = new CivicoZonaRossa();
  }

  // get e set
  public DettagliUtenteZonaRossa getDettagliUtenteZonaRossa() {
    return dettagliUtenteZonaRossa;
  }

  public void setDettagliUtenteZonaRossa(DettagliUtenteZonaRossa dettagliUtenteZonaRossa) {
    this.dettagliUtenteZonaRossa = dettagliUtenteZonaRossa;
  }

  public CivicoZonaRossa getCivicoZonaRossa() {
    return civicoZonaRossa;
  }

  public void setCivicoZonaRossa(CivicoZonaRossa civicoZonaRossa) {
    this.civicoZonaRossa = civicoZonaRossa;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DatiCompletiRegistrazioneUtenteAllerteZonaRossa [dettagliUtenteZonaRossa=");
    builder.append(dettagliUtenteZonaRossa);
    builder.append(", civicoZonaRossa=");
    builder.append(civicoZonaRossa);
    builder.append("]");
    return builder.toString();
  }
}
