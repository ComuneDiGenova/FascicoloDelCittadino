package it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni;

import java.io.Serializable;

public class AttestazioniISEE implements Serializable {

  private static final long serialVersionUID = 3650727102497523829L;

  private EsitoAttestazione esitoAttestazione;

  public EsitoAttestazione getEsitoAttestazione() {
    return esitoAttestazione;
  }

  public void setEsitoAttestazione(EsitoAttestazione esitoAttestazione) {
    this.esitoAttestazione = esitoAttestazione;
  }

  public Double getValoreIsee() {
    return esitoAttestazione.getIseeOrdinarioISEE();
  }

  @Override
  public String toString() {
    return "AttestazioniISEE [EsitoAttestazione=" + esitoAttestazione + "]";
  }
}
