package it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Componente.TipoEnum;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Utente.VulnerabilitaPersonaleEnum;
import java.io.Serializable;
import java.time.LocalDate;

public class ComponenteNucleoZonaRossa implements Serializable {

  private static final long serialVersionUID = -8820079971829956773L;

  private int idUtente;

  private int idCivico;

  private LocalDate dataRegistrazione;

  private String nome;

  private String cognome;

  private String codiceFiscale;

  private String eMail;

  private VulnerabilitaPersonaleEnum vulnerabilitaPersonale;

  private TipoEnum tipo;

  public int getIdUtente() {
    return idUtente;
  }

  public void setIdUtente(int idUtente) {
    this.idUtente = idUtente;
  }

  public int getIdCivico() {
    return idCivico;
  }

  public void setIdCivico(int idCivico) {
    this.idCivico = idCivico;
  }

  public LocalDate getDataRegistrazione() {
    return dataRegistrazione;
  }

  public void setDataRegistrazione(LocalDate dataRegistrazione) {
    this.dataRegistrazione = dataRegistrazione;
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

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String geteMail() {
    return eMail;
  }

  public void seteMail(String eMail) {
    this.eMail = eMail;
  }

  public VulnerabilitaPersonaleEnum getVulnerabilitaPersonale() {
    return vulnerabilitaPersonale;
  }

  public void setVulnerabilitaPersonale(VulnerabilitaPersonaleEnum vulnerabilitaPersonale) {
    this.vulnerabilitaPersonale = vulnerabilitaPersonale;
  }

  public TipoEnum getTipo() {
    return tipo;
  }

  public void setTipo(TipoEnum tipo) {
    this.tipo = tipo;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ComponenteNucleoZonaRossa [idUtente=");
    builder.append(idUtente);
    builder.append(", idCivico=");
    builder.append(idCivico);
    builder.append(", dataRegistrazione=");
    builder.append(dataRegistrazione);
    builder.append(", nome=");
    builder.append(nome);
    builder.append(", cognome=");
    builder.append(cognome);
    builder.append(", codiceFiscale=");
    builder.append(codiceFiscale);
    builder.append(", eMail=");
    builder.append(eMail);
    builder.append(", vulnerabilitaPersonale=");
    builder.append(vulnerabilitaPersonale);
    builder.append(", tipo=");
    builder.append(tipo);
    builder.append("]");
    return builder.toString();
  }
}
