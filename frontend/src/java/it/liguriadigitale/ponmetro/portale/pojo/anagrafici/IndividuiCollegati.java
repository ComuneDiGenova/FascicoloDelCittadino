package it.liguriadigitale.ponmetro.portale.pojo.anagrafici;

import it.liguriadigitale.ponmetro.servizianagrafici.model.Parentela;
import java.io.Serializable;
import java.time.LocalDate;

public class IndividuiCollegati implements Serializable {

  private static final long serialVersionUID = -4892131256255973538L;

  private boolean selezionato;

  private String nome;

  private String cognome;

  private String cf;

  private String nominativo;

  private LocalDate dataNascita;

  private String parentela;

  private String nuovaParentela;

  private String idDemografico;

  private String possiedePatenti;

  private String possiedeVeicoli;

  private String genere;

  private int eta;

  private Parentela parentelaConCoabitante;

  public boolean isSelezionato() {
    return selezionato;
  }

  public void setSelezionato(boolean selezionato) {
    this.selezionato = selezionato;
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

  public String getNominativo() {
    return nominativo;
  }

  public void setNominativo(String nominativo) {
    this.nominativo = nominativo;
  }

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  public String getParentela() {
    return parentela;
  }

  public void setParentela(String parentela) {
    this.parentela = parentela;
  }

  public String getNuovaParentela() {
    return nuovaParentela;
  }

  public void setNuovaParentela(String nuovaParentela) {
    this.nuovaParentela = nuovaParentela;
  }

  public String getPossiedePatenti() {
    return possiedePatenti;
  }

  public void setPossiedePatenti(String possiedePatenti) {
    this.possiedePatenti = possiedePatenti;
  }

  public String getPossiedeVeicoli() {
    return possiedeVeicoli;
  }

  public void setPossiedeVeicoli(String possiedeVeicoli) {
    this.possiedeVeicoli = possiedeVeicoli;
  }

  public String getGenere() {
    return genere;
  }

  public void setGenere(String genere) {
    this.genere = genere;
  }

  public String getCf() {
    return cf;
  }

  public void setCf(String cf) {
    this.cf = cf;
  }

  public String getIdDemografico() {
    return idDemografico;
  }

  public void setIdDemografico(String idDemografico) {
    this.idDemografico = idDemografico;
  }

  public int getEta() {
    return eta;
  }

  public void setEta(int eta) {
    this.eta = eta;
  }

  public Parentela getParentelaConCoabitante() {
    return parentelaConCoabitante;
  }

  public void setParentelaConCoabitante(Parentela parentelaConCoabitante) {
    this.parentelaConCoabitante = parentelaConCoabitante;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("IndividuiCollegati [selezionato=");
    builder.append(selezionato);
    builder.append(", nome=");
    builder.append(nome);
    builder.append(", cognome=");
    builder.append(cognome);
    builder.append(", cf=");
    builder.append(cf);
    builder.append(", nominativo=");
    builder.append(nominativo);
    builder.append(", dataNascita=");
    builder.append(dataNascita);
    builder.append(", parentela=");
    builder.append(parentela);
    builder.append(", nuovaParentela=");
    builder.append(nuovaParentela);
    builder.append(", idDemografico=");
    builder.append(idDemografico);
    builder.append(", possiedePatenti=");
    builder.append(possiedePatenti);
    builder.append(", possiedeVeicoli=");
    builder.append(possiedeVeicoli);
    builder.append(", genere=");
    builder.append(genere);
    builder.append(", eta=");
    builder.append(eta);
    builder.append(", parentelaConCoabitante=");
    builder.append(parentelaConCoabitante);
    builder.append("]");
    return builder.toString();
  }
}
