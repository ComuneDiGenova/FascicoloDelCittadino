package it.liguriadigitale.ponmetro.portale.presentation.pages.privacy.pojo;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import java.io.Serializable;
import org.apache.wicket.Page;

public class PrivacyServizio implements Serializable {

  private static final long serialVersionUID = 1793156043982018841L;

  private Utente utente;

  private String codicePrivacy;

  private Page paginaSuCuiAtterrare;

  private String messaggioErrore;

  private String nomePartecipata;

  public PrivacyServizio() {
    super();
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  public String getCodicePrivacy() {
    return codicePrivacy;
  }

  public void setCodicePrivacy(String codicePrivacy) {
    this.codicePrivacy = codicePrivacy;
  }

  public String getMessaggioErrore() {
    return messaggioErrore;
  }

  public void setMessaggioErrore(String messaggioErrore) {
    this.messaggioErrore = messaggioErrore;
  }

  public Page getPaginaSuCuiAtterrare() {
    return paginaSuCuiAtterrare;
  }

  public void setPaginaSuCuiAtterrare(Page paginaSuCuiAtterrare) {
    this.paginaSuCuiAtterrare = paginaSuCuiAtterrare;
  }

  public String getNomePartecipata() {
    return nomePartecipata;
  }

  public void setNomePartecipata(String nomePartecipata) {
    this.nomePartecipata = nomePartecipata;
  }

  @Override
  public String toString() {
    return "PrivacyServizio [utente="
        + utente
        + ", codicePrivacy="
        + codicePrivacy
        + ", paginaSuCuiAtterrare="
        + paginaSuCuiAtterrare
        + ", messaggioErrore="
        + messaggioErrore
        + ", nomePartecipata="
        + nomePartecipata
        + "]";
  }
}
