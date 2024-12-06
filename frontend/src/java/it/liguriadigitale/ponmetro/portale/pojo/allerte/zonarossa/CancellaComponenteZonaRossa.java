package it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa;

import java.io.Serializable;
import java.math.BigDecimal;

public class CancellaComponenteZonaRossa implements Serializable {

  private static final long serialVersionUID = 3035632140152502807L;

  private int idUtente;

  private String motivo;

  private String nome;

  private String cognome;

  private BigDecimal idCivico;

  public int getIdUtente() {
    return idUtente;
  }

  public void setIdUtente(int idUtente) {
    this.idUtente = idUtente;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
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

  public BigDecimal getIdCivico() {
    return idCivico;
  }

  public void setIdCivico(BigDecimal idCivico) {
    this.idCivico = idCivico;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CancellaComponenteZonaRossa [idUtente=");
    builder.append(idUtente);
    builder.append(", motivo=");
    builder.append(motivo);
    builder.append(", nome=");
    builder.append(nome);
    builder.append(", cognome=");
    builder.append(cognome);
    builder.append("]");
    return builder.toString();
  }
}
