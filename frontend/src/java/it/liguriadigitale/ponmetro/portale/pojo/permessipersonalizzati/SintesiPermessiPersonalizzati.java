package it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati;

import java.io.Serializable;

public class SintesiPermessiPersonalizzati implements Serializable {

  private static final long serialVersionUID = -1701171330953005157L;

  int idDomanda;
  String descrizioneProcedimento;
  int numeroProtocollo;
  int annoProtocollo;

  public int getIdDomanda() {
    return idDomanda;
  }

  public void setIdDomanda(int idDomanda) {
    this.idDomanda = idDomanda;
  }

  public String getDescrizioneProcedimento() {
    return descrizioneProcedimento;
  }

  public void setDescrizioneProcedimento(String descrizioneProcedimento) {
    this.descrizioneProcedimento = descrizioneProcedimento;
  }

  public int getNumeroProtocollo() {
    return numeroProtocollo;
  }

  public void setNumeroProtocollo(int numeroProtocollo) {
    this.numeroProtocollo = numeroProtocollo;
  }

  public int getAnnoProtocollo() {
    return annoProtocollo;
  }

  public void setAnnoProtocollo(int annoProtocollo) {
    this.annoProtocollo = annoProtocollo;
  }
}
