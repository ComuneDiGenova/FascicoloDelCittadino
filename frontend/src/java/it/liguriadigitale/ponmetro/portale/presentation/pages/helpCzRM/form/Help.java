package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form;

import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmSottoFascicoli;
import it.liguriadigitale.ponmetro.portale.pojo.richiestaassistenza.SottoCategoria;
import java.io.Serializable;
import java.util.List;

public class Help implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nome;
  private String cognome;
  private String cf;
  private String telefono;
  private String email;
  private String oggetto;
  private String descrizione;
  private CzrmSottoFascicoli sottofascicolo;

  private List<SottoCategoria> listaServizi;
  private SottoCategoria servizioAssistenza;

  private String privacy;

  public CzrmSottoFascicoli getSottofascicolo() {
    return sottofascicolo;
  }

  public void setSottofascicolo(CzrmSottoFascicoli sottofascicolo) {
    this.sottofascicolo = sottofascicolo;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public String getCf() {
    return cf;
  }

  public void setCf(String cf) {
    this.cf = cf;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getOggetto() {
    return oggetto;
  }

  public void setOggetto(String oggetto) {
    this.oggetto = oggetto;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public List<SottoCategoria> getListaServizi() {
    return listaServizi;
  }

  public void setListaServizi(List<SottoCategoria> listaServizi) {
    this.listaServizi = listaServizi;
  }

  public SottoCategoria getServizioAssistenza() {
    return servizioAssistenza;
  }

  public void setServizioAssistenza(SottoCategoria servizioAssistenza) {
    this.servizioAssistenza = servizioAssistenza;
  }

  public String getPrivacy() {
    return privacy;
  }

  public void setPrivacy(String privacy) {
    this.privacy = privacy;
  }

  @Override
  public String toString() {
    return "Help [nome="
        + nome
        + ", cognome="
        + cognome
        + ", cf="
        + cf
        + ", telefono="
        + telefono
        + ", email="
        + email
        + ", oggetto="
        + oggetto
        + ", descrizione="
        + descrizione
        + ", sottofascicolo="
        + sottofascicolo
        + ", listaServizi="
        + listaServizi
        + ", servizioAssistenza="
        + servizioAssistenza
        + ", privacy="
        + privacy
        + "]";
  }
}
