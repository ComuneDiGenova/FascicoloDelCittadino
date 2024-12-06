package it.liguriadigitale.ponmetro.portale.pojo.globo;

import java.io.Serializable;
import java.util.List;

public class Node implements Serializable {

  private static final long serialVersionUID = -7110397779925641154L;

  private Long id_card;
  private String titolo;
  private Long codice_maggioli;
  private Long codice_fdc;
  private List<Tag> tag_fascicolo;
  private List<Tag> tag_argomenti;
  private List<Tag> tag_target;
  private List<Collegamento> immagine;
  private List<Collegamento> url_servizio;
  private List<Collegamento> url_sito_approfondimenti;
  private List<Collegamento> url_scheda_ufficio;

  public Long getId_card() {
    return id_card;
  }

  public void setId_card(Long id_card) {
    this.id_card = id_card;
  }

  public String getTitolo() {
    return titolo;
  }

  public void setTitolo(String titolo) {
    this.titolo = titolo;
  }

  public Long getCodice_maggioli() {
    return codice_maggioli;
  }

  public void setCodice_maggioli(Long codice_maggioli) {
    this.codice_maggioli = codice_maggioli;
  }

  public List<Tag> getTag_fascicolo() {
    return tag_fascicolo;
  }

  public void setTag_fascicolo(List<Tag> tag_fascicolo) {
    this.tag_fascicolo = tag_fascicolo;
  }

  public List<Tag> getTag_argomenti() {
    return tag_argomenti;
  }

  public void setTag_argomenti(List<Tag> tag_argomenti) {
    this.tag_argomenti = tag_argomenti;
  }

  public List<Tag> getTag_target() {
    return tag_target;
  }

  public void setTag_target(List<Tag> tag_target) {
    this.tag_target = tag_target;
  }

  public List<Collegamento> getImmagine() {
    return immagine;
  }

  public void setImmagine(List<Collegamento> immagine) {
    this.immagine = immagine;
  }

  public List<Collegamento> getUrl_servizio() {
    return url_servizio;
  }

  public void setUrl_servizio(List<Collegamento> url_servizio) {
    this.url_servizio = url_servizio;
  }

  public List<Collegamento> getUrl_sito_approfondimenti() {
    return url_sito_approfondimenti;
  }

  public void setUrl_sito_approfondimenti(List<Collegamento> url_sito_approfondimenti) {
    this.url_sito_approfondimenti = url_sito_approfondimenti;
  }

  public List<Collegamento> getUrl_scheda_ufficio() {
    return url_scheda_ufficio;
  }

  public void setUrl_scheda_ufficio(List<Collegamento> url_scheda_ufficio) {
    this.url_scheda_ufficio = url_scheda_ufficio;
  }

  public Long getCodice_fdc() {
    return codice_fdc;
  }

  public void setCodice_fdc(Long codice_fdc) {
    this.codice_fdc = codice_fdc;
  }

  @Override
  public String toString() {
    return "Node [id_card="
        + id_card
        + ", titolo="
        + titolo
        + ", codice_maggioli="
        + codice_maggioli
        + ", codice_fdc="
        + codice_fdc
        + ", tag_fascicolo="
        + tag_fascicolo
        + ", tag_argomenti="
        + tag_argomenti
        + ", tag_target="
        + tag_target
        + ", immagine="
        + immagine
        + ", url_servizio="
        + url_servizio
        + ", url_sito_approfondimenti="
        + url_sito_approfondimenti
        + ", url_scheda_ufficio="
        + url_scheda_ufficio
        + "]";
  }
}
