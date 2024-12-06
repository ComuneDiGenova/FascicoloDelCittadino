package it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Indirizzo.PosizioneEnum;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Indirizzo.VulnerabilitaEnum;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import java.io.Serializable;

public class CivicoZonaRossa implements Serializable {

  private static final long serialVersionUID = 7086178229158281342L;

  private int idUtente;

  private String pericolositaIdro;

  private String pericolositaIncendio;

  private String pericolositaFrane;

  // private FeaturesGeoserver geoserver;

  private FeaturesGeoserver autocompleteViario;

  private String indirizzoCompleto;

  private String idVia;

  private String numeroCivico;

  private String esponente;

  private String colore;

  private String interno;

  private String internoLettera;

  private String scala;

  private PosizioneEnum posizione;

  private VulnerabilitaEnum vulnerabilita;

  private String amministratore;

  private String proprietario;

  public String getIndirizzoCompleto() {
    return indirizzoCompleto;
  }

  public void setIndirizzoCompleto(String indirizzoCompleto) {
    this.indirizzoCompleto = indirizzoCompleto;
  }

  public String getIdVia() {
    return idVia;
  }

  public void setIdVia(String idVia) {
    this.idVia = idVia;
  }

  public String getNumeroCivico() {
    return numeroCivico;
  }

  public void setNumeroCivico(String numeroCivico) {
    this.numeroCivico = numeroCivico;
  }

  public String getEsponente() {
    return esponente;
  }

  public void setEsponente(String esponente) {
    this.esponente = esponente;
  }

  public String getColore() {
    return colore;
  }

  public void setColore(String colore) {
    this.colore = colore;
  }

  public PosizioneEnum getPosizione() {
    return posizione;
  }

  public void setPosizione(PosizioneEnum posizione) {
    this.posizione = posizione;
  }

  public VulnerabilitaEnum getVulnerabilita() {
    return vulnerabilita;
  }

  public void setVulnerabilita(VulnerabilitaEnum vulnerabilita) {
    this.vulnerabilita = vulnerabilita;
  }

  public String getAmministratore() {
    return amministratore;
  }

  public void setAmministratore(String amministratore) {
    this.amministratore = amministratore;
  }

  public String getProprietario() {
    return proprietario;
  }

  public void setProprietario(String proprietario) {
    this.proprietario = proprietario;
  }

  //	public FeaturesGeoserver getGeoserver() {
  //		return geoserver;
  //	}
  //
  //	public void setGeoserver(FeaturesGeoserver geoserver) {
  //		this.geoserver = geoserver;
  //	}

  public int getIdUtente() {
    return idUtente;
  }

  public void setIdUtente(int idUtente) {
    this.idUtente = idUtente;
  }

  public String getPericolositaIdro() {
    return pericolositaIdro;
  }

  public void setPericolositaIdro(String pericolositaIdro) {
    this.pericolositaIdro = pericolositaIdro;
  }

  public String getPericolositaIncendio() {
    return pericolositaIncendio;
  }

  public void setPericolositaIncendio(String pericolositaIncendio) {
    this.pericolositaIncendio = pericolositaIncendio;
  }

  public String getPericolositaFrane() {
    return pericolositaFrane;
  }

  public void setPericolositaFrane(String pericolositaFrane) {
    this.pericolositaFrane = pericolositaFrane;
  }

  public String getInterno() {
    return interno;
  }

  public void setInterno(String interno) {
    this.interno = interno;
  }

  public String getInternoLettera() {
    return internoLettera;
  }

  public void setInternoLettera(String internoLettera) {
    this.internoLettera = internoLettera;
  }

  public String getScala() {
    return scala;
  }

  public void setScala(String scala) {
    this.scala = scala;
  }

  public FeaturesGeoserver getAutocompleteViario() {
    return autocompleteViario;
  }

  public void setAutocompleteViario(FeaturesGeoserver autocompleteViario) {
    this.autocompleteViario = autocompleteViario;
  }

  @Override
  public String toString() {
    return "CivicoZonaRossa [idUtente="
        + idUtente
        + ", pericolositaIdro="
        + pericolositaIdro
        + ", pericolositaIncendio="
        + pericolositaIncendio
        + ", pericolositaFrane="
        + pericolositaFrane
        + ", autocompleteViario="
        + autocompleteViario
        + ", indirizzoCompleto="
        + indirizzoCompleto
        + ", idVia="
        + idVia
        + ", numeroCivico="
        + numeroCivico
        + ", esponente="
        + esponente
        + ", colore="
        + colore
        + ", interno="
        + interno
        + ", internoLettera="
        + internoLettera
        + ", scala="
        + scala
        + ", posizione="
        + posizione
        + ", vulnerabilita="
        + vulnerabilita
        + ", amministratore="
        + amministratore
        + ", proprietario="
        + proprietario
        + "]";
  }
}
