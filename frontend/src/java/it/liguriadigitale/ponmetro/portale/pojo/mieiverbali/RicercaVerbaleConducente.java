package it.liguriadigitale.ponmetro.portale.pojo.mieiverbali;

import java.io.Serializable;
import java.time.LocalDate;

public class RicercaVerbaleConducente implements Serializable {

  private static final long serialVersionUID = -6723162430001039688L;

  private String numeroAvviso;

  private LocalDate dataVerbale;

  private String targa;

  /*
   * private boolean personaFisicaPIva;
   *
   * private String nomeProprietario;
   *
   * private String cognomeProprietario;
   *
   * private String codiceFiscaleProprietario;
   *
   * private String piva;
   */

  private String nomeConducente;

  private String cognomeConducente;

  private String sessoConducente;

  private String codiceFiscaleConducente;

  private LocalDate dataNascitaConducente;

  private String cittaNascitaConducente;

  private String provinciaNascitaConducente;

  private String indirizzoConducente;

  private String capConducente;

  private String cittaConducente;

  private String numeroPatente;

  private String categoriaPatente;

  private String patenteRilasciataDa;

  private LocalDate patenteRilasciataIl;

  private LocalDate patenteValidaFinoAl;

  private boolean toggleDichiarazione;

  private String emailDichiarante;

  private byte[] byteFilePatente;

  private String nomeFilePatente;

  private String uploadPatente;

  public String getNumeroAvviso() {
    return numeroAvviso;
  }

  public void setNumeroAvviso(String numeroAvviso) {
    this.numeroAvviso = numeroAvviso;
  }

  public LocalDate getDataVerbale() {
    return dataVerbale;
  }

  public void setDataVerbale(LocalDate dataVerbale) {
    this.dataVerbale = dataVerbale;
  }

  public String getTarga() {
    return targa;
  }

  public void setTarga(String targa) {
    this.targa = targa;
  }

  public String getNomeConducente() {
    return nomeConducente;
  }

  public void setNomeConducente(String nomeConducente) {
    this.nomeConducente = nomeConducente;
  }

  public String getCognomeConducente() {
    return cognomeConducente;
  }

  public void setCognomeConducente(String cognomeConducente) {
    this.cognomeConducente = cognomeConducente;
  }

  public String getSessoConducente() {
    return sessoConducente;
  }

  public void setSessoConducente(String sessoConducente) {
    this.sessoConducente = sessoConducente;
  }

  public String getCodiceFiscaleConducente() {
    return codiceFiscaleConducente;
  }

  public void setCodiceFiscaleConducente(String codiceFiscaleConducente) {
    this.codiceFiscaleConducente = codiceFiscaleConducente;
  }

  public String getCittaNascitaConducente() {
    return cittaNascitaConducente;
  }

  public LocalDate getDataNascitaConducente() {
    return dataNascitaConducente;
  }

  public void setDataNascitaConducente(LocalDate dataNascitaConducente) {
    this.dataNascitaConducente = dataNascitaConducente;
  }

  public void setCittaNascitaConducente(String cittaNascitaConducente) {
    this.cittaNascitaConducente = cittaNascitaConducente;
  }

  public String getProvinciaNascitaConducente() {
    return provinciaNascitaConducente;
  }

  public void setProvinciaNascitaConducente(String provinciaNascitaConducente) {
    this.provinciaNascitaConducente = provinciaNascitaConducente;
  }

  public String getIndirizzoConducente() {
    return indirizzoConducente;
  }

  public void setIndirizzoConducente(String indirizzoConducente) {
    this.indirizzoConducente = indirizzoConducente;
  }

  public String getCapConducente() {
    return capConducente;
  }

  public void setCapConducente(String capConducente) {
    this.capConducente = capConducente;
  }

  public String getCittaConducente() {
    return cittaConducente;
  }

  public void setCittaConducente(String cittaConducente) {
    this.cittaConducente = cittaConducente;
  }

  public String getNumeroPatente() {
    return numeroPatente;
  }

  public void setNumeroPatente(String numeroPatente) {
    this.numeroPatente = numeroPatente;
  }

  public String getCategoriaPatente() {
    return categoriaPatente;
  }

  public void setCategoriaPatente(String categoriaPatente) {
    this.categoriaPatente = categoriaPatente;
  }

  public String getPatenteRilasciataDa() {
    return patenteRilasciataDa;
  }

  public void setPatenteRilasciataDa(String patenteRilasciataDa) {
    this.patenteRilasciataDa = patenteRilasciataDa;
  }

  public LocalDate getPatenteRilasciataIl() {
    return patenteRilasciataIl;
  }

  public void setPatenteRilasciataIl(LocalDate patenteRilasciataIl) {
    this.patenteRilasciataIl = patenteRilasciataIl;
  }

  public LocalDate getPatenteValidaFinoAl() {
    return patenteValidaFinoAl;
  }

  public void setPatenteValidaFinoAl(LocalDate patenteValidaFinoAl) {
    this.patenteValidaFinoAl = patenteValidaFinoAl;
  }

  public boolean isToggleDichiarazione() {
    return toggleDichiarazione;
  }

  public void setToggleDichiarazione(boolean toggleDichiarazione) {
    this.toggleDichiarazione = toggleDichiarazione;
  }

  public String getEmailDichiarante() {
    return emailDichiarante;
  }

  public void setEmailDichiarante(String emailDichiarante) {
    this.emailDichiarante = emailDichiarante;
  }

  public byte[] getByteFilePatente() {
    return byteFilePatente;
  }

  public void setByteFilePatente(byte[] byteFilePatente) {
    this.byteFilePatente = byteFilePatente;
  }

  public String getNomeFilePatente() {
    return nomeFilePatente;
  }

  public void setNomeFilePatente(String nomeFilePatente) {
    this.nomeFilePatente = nomeFilePatente;
  }

  public String getUploadPatente() {
    return uploadPatente;
  }

  public void setUploadPatente(String uploadPatente) {
    this.uploadPatente = uploadPatente;
  }

  @Override
  public String toString() {
    return "RicercaVerbaleConducente [numeroAvviso="
        + numeroAvviso
        + ", dataVerbale="
        + dataVerbale
        + ", targa="
        + targa
        + ", nomeConducente="
        + nomeConducente
        + ", cognomeConducente="
        + cognomeConducente
        + ", sessoConducente="
        + sessoConducente
        + ", codiceFiscaleConducente="
        + codiceFiscaleConducente
        + ", dataNascitaConducente="
        + dataNascitaConducente
        + ", cittaNascitaConducente="
        + cittaNascitaConducente
        + ", provinciaNascitaConducente="
        + provinciaNascitaConducente
        + ", indirizzoConducente="
        + indirizzoConducente
        + ", capConducente="
        + capConducente
        + ", cittaConducente="
        + cittaConducente
        + ", numeroPatente="
        + numeroPatente
        + ", categoriaPatente="
        + categoriaPatente
        + ", patenteRilasciataDa="
        + patenteRilasciataDa
        + ", patenteRilasciataIl="
        + patenteRilasciataIl
        + ", patenteValidaFinoAl="
        + patenteValidaFinoAl
        + ", toggleDichiarazione="
        + toggleDichiarazione
        + ", emailDichiarante="
        + emailDichiarante
        + ", nomeFilePatente="
        + nomeFilePatente
        + "]";
  }
}
