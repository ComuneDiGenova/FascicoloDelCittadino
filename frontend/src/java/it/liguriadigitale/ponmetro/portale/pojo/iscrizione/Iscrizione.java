package it.liguriadigitale.ponmetro.portale.pojo.iscrizione;

import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.Serializable;

public class Iscrizione implements Serializable {

  private static final long serialVersionUID = -3206475603216395161L;

  private UtenteServiziRistorazione utente;

  // TODO da togliere 1.3.4
  /*
   * private boolean dietaMotiviSanitari; private boolean
   * dietaMotiviReligiosi; private boolean dietaEpisodiAllergici;
   */

  private String emailContatto;

  private String indirizzo;
  private String comune;
  private String cap;

  // TODO da togliere 1.3.4
  /*
   * public boolean isDietaMotiviSanitari() { return dietaMotiviSanitari; }
   *
   * public void setDietaMotiviSanitari(boolean dietaMotiviSanitari) {
   * this.dietaMotiviSanitari = dietaMotiviSanitari; }
   *
   * public boolean isDietaMotiviReligiosi() { return dietaMotiviReligiosi; }
   *
   * public void setDietaMotiviReligiosi(boolean dietaMotiviReligiosi) {
   * this.dietaMotiviReligiosi = dietaMotiviReligiosi; }
   *
   * public boolean isDietaEpisodiAllergici() { return dietaEpisodiAllergici;
   * }
   *
   * public void setDietaEpisodiAllergici(boolean dietaEpisodiAllergici) {
   * this.dietaEpisodiAllergici = dietaEpisodiAllergici; }
   */

  public UtenteServiziRistorazione getUtente() {
    return utente;
  }

  public void setUtente(UtenteServiziRistorazione utente) {
    this.utente = utente;
  }

  public String getEmailContatto() {
    return emailContatto;
  }

  public void setEmailContatto(String emailContatto) {
    this.emailContatto = emailContatto;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public String getComune() {
    return comune;
  }

  public void setComune(String comune) {
    this.comune = comune;
  }

  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  @Override
  public String toString() {
    return "Iscrizione [utente="
        + utente
        + ", emailContatto="
        + emailContatto
        + ", indirizzo="
        + indirizzo
        + ", comune="
        + comune
        + ", cap="
        + cap
        + "]";
  }
}
