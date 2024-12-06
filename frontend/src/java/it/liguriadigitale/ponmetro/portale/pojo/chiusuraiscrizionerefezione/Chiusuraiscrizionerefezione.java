package it.liguriadigitale.ponmetro.portale.pojo.chiusuraiscrizionerefezione;

import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoDomandaChiusuraServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.Serializable;

public class Chiusuraiscrizionerefezione implements Serializable {

  private static final long serialVersionUID = -1870214429570763559L;

  private UtenteServiziRistorazione utente;
  private DatiStatoDomandaChiusuraServiziRistorazione datiChiusura;
  private String emailContatto;
  private String note;
  private String telefonoContatto;

  public UtenteServiziRistorazione getUtente() {
    return utente;
  }

  public void setUtente(UtenteServiziRistorazione utente) {
    this.utente = utente;
  }

  public DatiStatoDomandaChiusuraServiziRistorazione getDatiChiusura() {
    return datiChiusura;
  }

  public void setDatiChiusura(DatiStatoDomandaChiusuraServiziRistorazione datiChiusura) {
    this.datiChiusura = datiChiusura;
  }

  public String getEmailContatto() {
    return emailContatto;
  }

  public void setEmailContatto(String emailContatto) {
    this.emailContatto = emailContatto;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getTelefonoContatto() {
    return telefonoContatto;
  }

  public void setTelefonoContatto(String telefonoContatto) {
    this.telefonoContatto = telefonoContatto;
  }

  @Override
  public String toString() {
    return "Chiusuraiscrizionerefezione [utente="
        + utente
        + ", datiChiusura="
        + datiChiusura
        + ", emailContatto="
        + emailContatto
        + ", note="
        + note
        + ", telefonoContatto="
        + telefonoContatto
        + "]";
  }
}
