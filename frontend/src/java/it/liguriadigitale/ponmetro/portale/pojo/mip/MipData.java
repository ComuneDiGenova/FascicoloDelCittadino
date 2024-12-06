package it.liguriadigitale.ponmetro.portale.pojo.mip;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import java.io.Serializable;
import java.math.BigDecimal;

public class MipData implements Serializable {

  private static final long serialVersionUID = -9101493638444129304L;

  private String idServizio;
  private String numeroDocumento;
  private BigDecimal importoAvviso;
  private Utente utente;

  public String getIdServizio() {
    return idServizio;
  }

  public void setIdServizio(String idServizio) {
    this.idServizio = idServizio;
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public BigDecimal getImportoAvviso() {
    return importoAvviso;
  }

  public void setImportoAvviso(BigDecimal importoAvviso) {
    this.importoAvviso = importoAvviso;
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  @Override
  public String toString() {
    return "MipData [idServizio="
        + idServizio
        + ", numeroDocumento="
        + numeroDocumento
        + ", importoAvviso="
        + importoAvviso
        + ", utente="
        + utente
        + "]";
  }
}
