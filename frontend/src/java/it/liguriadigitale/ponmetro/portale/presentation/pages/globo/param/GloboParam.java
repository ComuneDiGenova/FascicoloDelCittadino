package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param;

import java.io.Serializable;

public class GloboParam implements Serializable {

  private static final long serialVersionUID = -8112384498794094510L;

  private String idFunzione;
  private String idMaggioli;
  private String idProcedimento;

  public String getIdFunzione() {
    return idFunzione;
  }

  public void setIdFunzione(String idFunzione) {
    this.idFunzione = idFunzione;
  }

  public String getIdMaggioli() {
    return idMaggioli;
  }

  public void setIdMaggioli(String idMaggioli) {
    this.idMaggioli = idMaggioli;
  }

  public String getIdProcedimento() {
    return idProcedimento;
  }

  public void setIdProcedimento(String idProcedimento) {
    this.idProcedimento = idProcedimento;
  }

  @Override
  public String toString() {
    return "GloboParam [idFunzione="
        + idFunzione
        + ", idMaggioli="
        + idMaggioli
        + ", idProcedimento="
        + idProcedimento
        + "]";
  }
}
