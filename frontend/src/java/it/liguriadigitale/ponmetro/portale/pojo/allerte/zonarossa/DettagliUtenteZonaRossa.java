package it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto.LinguaEnum;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto.TipoEnum;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Lingua;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Utente.VulnerabilitaPersonaleEnum;
import java.io.Serializable;
import java.time.LocalDate;

public class DettagliUtenteZonaRossa implements Serializable {

  private static final long serialVersionUID = -722805784006397056L;

  private LocalDate dataRegistrazione;

  private String nome;

  private String cognome;

  private String codiceFiscale;

  private String eMail;

  private VulnerabilitaPersonaleEnum vulnerabilitaPersonale;

  private String numero;

  private TipoEnum tipo;

  private LinguaEnum lingua;

  private Lingua linguaNoItalia;

  private int idUtente;

  private int idContatto;

  private it.liguriadigitale.ponmetro.allertezonarossa.model.Componente.TipoEnum tipoPersona;

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public TipoEnum getTipo() {
    return tipo;
  }

  public void setTipo(TipoEnum tipo) {
    this.tipo = tipo;
  }

  public LinguaEnum getLingua() {
    return lingua;
  }

  public void setLingua(LinguaEnum lingua) {
    this.lingua = lingua;
  }

  public Lingua getLinguaNoItalia() {
    return linguaNoItalia;
  }

  public void setLinguaNoItalia(Lingua linguaNoItalia) {
    this.linguaNoItalia = linguaNoItalia;
  }

  public int getIdUtente() {
    return idUtente;
  }

  public void setIdUtente(int idUtente) {
    this.idUtente = idUtente;
  }

  public int getIdContatto() {
    return idContatto;
  }

  public void setIdContatto(int idContatto) {
    this.idContatto = idContatto;
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

  public it.liguriadigitale.ponmetro.allertezonarossa.model.Componente.TipoEnum getTipoPersona() {
    return tipoPersona;
  }

  public void setTipoPersona(
      it.liguriadigitale.ponmetro.allertezonarossa.model.Componente.TipoEnum tipoPersona) {
    this.tipoPersona = tipoPersona;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DettagliUtenteZonaRossa [dataRegistrazione=");
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
    builder.append(", numero=");
    builder.append(numero);
    builder.append(", tipo=");
    builder.append(tipo);
    builder.append(", lingua=");
    builder.append(lingua);
    builder.append(", linguaNoItalia=");
    builder.append(linguaNoItalia);
    builder.append(", idUtente=");
    builder.append(idUtente);
    builder.append(", idContatto=");
    builder.append(idContatto);
    builder.append(", tipoPersona=");
    builder.append(tipoPersona);
    builder.append("]");
    return builder.toString();
  }
}
