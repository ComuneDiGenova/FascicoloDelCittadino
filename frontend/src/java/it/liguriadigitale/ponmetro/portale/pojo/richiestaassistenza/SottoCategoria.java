package it.liguriadigitale.ponmetro.portale.pojo.richiestaassistenza;

import java.io.Serializable;

public class SottoCategoria implements Serializable {

  private static final long serialVersionUID = -5892748361892643004L;

  private String servizio;

  private String id;

  private String sottoFascicolo;

  public String getServizio() {
    return servizio;
  }

  public void setServizio(String servizio) {
    this.servizio = servizio;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSottoFascicolo() {
    return sottoFascicolo;
  }

  public void setSottoFascicolo(String sottoFascicolo) {
    this.sottoFascicolo = sottoFascicolo;
  }

  @Override
  public String toString() {
    return "SottoCategoria [servizio="
        + servizio
        + ", id="
        + id
        + ", sottoFascicolo="
        + sottoFascicolo
        + "]";
  }
}
