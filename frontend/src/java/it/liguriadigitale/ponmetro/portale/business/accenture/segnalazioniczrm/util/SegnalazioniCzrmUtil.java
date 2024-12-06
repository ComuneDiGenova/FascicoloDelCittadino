package it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.util;

import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsAccountIdCasesGet200Response;
import java.io.Serializable;

public class SegnalazioniCzrmUtil implements Serializable {

  private static final long serialVersionUID = 2990265049126714435L;

  private String nomeCognome;

  private String email;

  private ServicesDataV590SobjectsAccountIdCasesGet200Response segnalazioni;

  public String getNomeCognome() {
    return nomeCognome;
  }

  public void setNomeCognome(String nomeCognome) {
    this.nomeCognome = nomeCognome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public ServicesDataV590SobjectsAccountIdCasesGet200Response getSegnalazioni() {
    return segnalazioni;
  }

  public void setSegnalazioni(ServicesDataV590SobjectsAccountIdCasesGet200Response segnalazioni) {
    this.segnalazioni = segnalazioni;
  }

  @Override
  public String toString() {
    return "SegnalazioniCzrmUtil [nomeCognome="
        + nomeCognome
        + ", email="
        + email
        + ", segnalazioni="
        + segnalazioni
        + "]";
  }
}
