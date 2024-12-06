package it.liguriadigitale.ponmetro.portale.pojo.portale;

import it.liguriadigitale.ponmetro.api.pojo.enums.AutocertificazioneTipoMinoreEnum;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import java.io.Serializable;
import java.time.LocalDate;

public class MinoreConvivente implements Serializable {

  private static final long serialVersionUID = -2069465961582399266L;

  private Residente datiDemografico;

  private String codiceFiscale;
  private String nome;
  private String cognome;
  private LocalDate dataNascita;
  private Long idPerson;
  private AutocertificazioneTipoMinoreEnum tipoParentela;
  private boolean figlioStatoCivile;
  private boolean minoreResidente;
  private boolean autodichiarazioneFiglio;
  private boolean bloccoAutodichiarazione;

  public boolean isFiglioStatoCivile() {
    return figlioStatoCivile;
  }

  public void setFiglioStatoCivile(boolean figlioStatoCivile) {
    this.figlioStatoCivile = figlioStatoCivile;
  }

  public boolean isMinoreResidente() {
    return minoreResidente;
  }

  public void setMinoreResidente(boolean minoreResidente) {
    this.minoreResidente = minoreResidente;
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

  public Residente getDatiDemografico() {
    return datiDemografico;
  }

  public void setDatiDemografico(Residente datiDemografico) {
    this.datiDemografico = datiDemografico;
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

  public Long getIdPerson() {
    return idPerson;
  }

  public void setIdPerson(Long idPerson) {
    this.idPerson = idPerson;
  }

  public AutocertificazioneTipoMinoreEnum getTipoParentela() {
    return tipoParentela;
  }

  public void setTipoParentela(AutocertificazioneTipoMinoreEnum tipoParentela) {
    this.tipoParentela = tipoParentela;
  }

  @Override
  public String toString() {
    return "MinoreConvivente [datiDemografico="
        + datiDemografico
        + ", codiceFiscale="
        + codiceFiscale
        + ", nome="
        + nome
        + ", cognome="
        + cognome
        + ", dataNascita="
        + dataNascita
        + ", idPerson="
        + idPerson
        + ", tipoParentela="
        + tipoParentela
        + ", figlioStatoCivile="
        + figlioStatoCivile
        + ", minoreResidente="
        + minoreResidente
        + ", autodichiarazioneFiglio="
        + autodichiarazioneFiglio
        + ", bloccoAutodichiarazione="
        + bloccoAutodichiarazione
        + "]";
  }
}
