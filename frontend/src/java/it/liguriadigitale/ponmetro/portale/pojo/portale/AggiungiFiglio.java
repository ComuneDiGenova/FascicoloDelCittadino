package it.liguriadigitale.ponmetro.portale.pojo.portale;

import it.liguriadigitale.ponmetro.api.pojo.enums.AutocertificazioneTipoMinoreEnum;
import java.io.Serializable;
import java.time.LocalDate;

public class AggiungiFiglio implements Serializable {

  private static final long serialVersionUID = -4610122226261696675L;

  private Long idFCitt;
  private Long idPerson;
  private String codiceFiscale;
  private String nome;
  private String cognome;
  private LocalDate dataNascita;
  private AutocertificazioneTipoMinoreEnum tipoParentela;
  private boolean autodichiarazioneFiglio;
  private boolean bloccoAutodichiarazione;

  public Long getIdFCitt() {
    return idFCitt;
  }

  public void setIdFCitt(Long idFCitt) {
    this.idFCitt = idFCitt;
  }

  public Long getIdPerson() {
    return idPerson;
  }

  public void setIdPerson(Long idPerson) {
    this.idPerson = idPerson;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
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

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  public AutocertificazioneTipoMinoreEnum getTipoParentela() {
    return tipoParentela;
  }

  public void setTipoParentela(AutocertificazioneTipoMinoreEnum tipoParentela) {
    this.tipoParentela = tipoParentela;
  }

  public boolean isAutodichiarazioneFiglio() {
    return autodichiarazioneFiglio;
  }

  public void setAutodichiarazioneFiglio(boolean autodichiarazioneFiglio) {
    this.autodichiarazioneFiglio = autodichiarazioneFiglio;
  }

  public boolean isBloccoAutodichiarazione() {
    return bloccoAutodichiarazione;
  }

  public void setBloccoAutodichiarazione(boolean bloccoAutodichiarazione) {
    this.bloccoAutodichiarazione = bloccoAutodichiarazione;
  }

  @Override
  public String toString() {
    return "AggiungiFiglio [idFCitt="
        + idFCitt
        + ", idPerson="
        + idPerson
        + ", codiceFiscale="
        + codiceFiscale
        + ", nome="
        + nome
        + ", cognome="
        + cognome
        + ", dataNascita="
        + dataNascita
        + ", tipoParentela="
        + tipoParentela
        + ", autodichiarazioneFiglio="
        + autodichiarazioneFiglio
        + ", bloccoAutodichiarazione="
        + bloccoAutodichiarazione
        + "]";
  }
}
