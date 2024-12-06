package it.liguriadigitale.ponmetro.portale.pojo.biblioteche;

import java.io.Serializable;

public class BibliotecheInternet implements Serializable {

  private static final long serialVersionUID = -6745075820449312208L;

  private Long idSebina;

  private boolean flAbil;

  public boolean isFlAbil() {
    return flAbil;
  }

  public void setFlAbil(boolean flAbil) {
    this.flAbil = flAbil;
  }

  public Long getIdSebina() {
    return idSebina;
  }

  public void setIdSebina(Long idSebina) {
    this.idSebina = idSebina;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("BibliotecheInternet [idSebina=");
    builder.append(idSebina);
    builder.append(", flAbil=");
    builder.append(flAbil);
    builder.append("]");
    return builder.toString();
  }
}
