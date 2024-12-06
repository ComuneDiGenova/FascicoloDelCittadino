package it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe;

import java.io.Serializable;

public class DatiRichiestaContributoTariNetribeResult implements Serializable {

  private static final long serialVersionUID = -7380692894857245750L;

  private boolean esito;

  private String messaggioErrore;

  private Integer idRichiesta;

  private String numeroProtocollo;

  private String annoProtocollo;

  private String idFileProtocollo;

  public boolean isEsito() {
    return esito;
  }

  public void setEsito(boolean esito) {
    this.esito = esito;
  }

  public String getMessaggioErrore() {
    return messaggioErrore;
  }

  public void setMessaggioErrore(String messaggioErrore) {
    this.messaggioErrore = messaggioErrore;
  }

  public Integer getIdRichiesta() {
    return idRichiesta;
  }

  public void setIdRichiesta(Integer idRichiesta) {
    this.idRichiesta = idRichiesta;
  }

  public String getNumeroProtocollo() {
    return numeroProtocollo;
  }

  public void setNumeroProtocollo(String numeroProtocollo) {
    this.numeroProtocollo = numeroProtocollo;
  }

  public String getIdFileProtocollo() {
    return idFileProtocollo;
  }

  public void setIdFileProtocollo(String idFileProtocollo) {
    this.idFileProtocollo = idFileProtocollo;
  }

  public String getAnnoProtocollo() {
    return annoProtocollo;
  }

  public void setAnnoProtocollo(String annoProtocollo) {
    this.annoProtocollo = annoProtocollo;
  }

  @Override
  public String toString() {
    return "DatiRichiestaContributoTariNetribeResult [esito="
        + esito
        + ", messaggioErrore="
        + messaggioErrore
        + ", idRichiesta="
        + idRichiesta
        + ", numeroProtocollo="
        + numeroProtocollo
        + ", annoProtocollo="
        + annoProtocollo
        + ", idFileProtocollo="
        + idFileProtocollo
        + "]";
  }
}
