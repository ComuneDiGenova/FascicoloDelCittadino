package it.liguriadigitale.ponmetro.portale.pojo.mieiverbali;

import java.io.Serializable;
import java.time.LocalDate;

public class PuntiPatenteProprietario implements Serializable {

  private static final long serialVersionUID = -8571170992166187343L;

  private String targa;

  private boolean dichiarazioneProprietario;

  private String cognomeConducente;

  private String nomeConducente;

  private String cfConducente;

  private String conducenteNatoA;

  private String conducenteNatoAProvincia;

  private LocalDate conducenteNatoIl;

  private String conducenteResidenteA;

  private String conducenteResidenteIn;

  private String conducenteResidenteProvincia;

  private String conducenteResidenteNumCivico;

  private String conducenteResidenteCap;

  private String categoriaPatente;

  private String numeroPatente;

  private String patenteRilasciataDa;

  private LocalDate patenteRilasciataIl;

  private LocalDate patenteValidaFinoAl;

  private String numeroAvviso;

  private String numeroProtocollo;

  private LocalDate dataVerbale;

  private String nomeProprietario;

  private String cognomeProprietario;

  private String cfProprietario;

  private String proprietarioNatoA;

  private String proprietarioNatoAProvincia;

  private LocalDate proprietarioNatoIl;

  private String proprietarioResidenteIn;

  private String proprietarioResidenteCap;

  private String proprietarioResidenteCitta;

  private String emailProprietario;

  private String emailConducente;

  private String categoriaPatenteConducente;

  private String numeroPatenteConducente;

  private String patenteConducenteRilasciataDa;

  private LocalDate patenteConducenteRilasciataIl;

  private LocalDate patenteConducenteValidaFinoAl;

  private String patenteProprietarioUpload;

  private String nomePatenteProprietarioUpload;

  private byte[] bytePatenteProprietarioUpload;

  private String patenteConducenteUpload;

  private String nomePatenteConducenteUpload;

  private byte[] bytePatenteConducenteUpload;

  public String getTarga() {
    return targa;
  }

  public void setTarga(String targa) {
    this.targa = targa;
  }

  public boolean isDichiarazioneProprietario() {
    return dichiarazioneProprietario;
  }

  public void setDichiarazioneProprietario(boolean dichiarazioneProprietario) {
    this.dichiarazioneProprietario = dichiarazioneProprietario;
  }

  public String getCognomeConducente() {
    return cognomeConducente;
  }

  public void setCognomeConducente(String cognomeConducente) {
    this.cognomeConducente = cognomeConducente;
  }

  public String getNomeConducente() {
    return nomeConducente;
  }

  public void setNomeConducente(String nomeConducente) {
    this.nomeConducente = nomeConducente;
  }

  public String getConducenteNatoA() {
    return conducenteNatoA;
  }

  public void setConducenteNatoA(String conducenteNatoA) {
    this.conducenteNatoA = conducenteNatoA;
  }

  public String getConducenteNatoAProvincia() {
    return conducenteNatoAProvincia;
  }

  public void setConducenteNatoAProvincia(String conducenteNatoAProvincia) {
    this.conducenteNatoAProvincia = conducenteNatoAProvincia;
  }

  public LocalDate getConducenteNatoIl() {
    return conducenteNatoIl;
  }

  public void setConducenteNatoIl(LocalDate conducenteNatoIl) {
    this.conducenteNatoIl = conducenteNatoIl;
  }

  public String getConducenteResidenteA() {
    return conducenteResidenteA;
  }

  public void setConducenteResidenteA(String conducenteResidenteA) {
    this.conducenteResidenteA = conducenteResidenteA;
  }

  public String getConducenteResidenteIn() {
    return conducenteResidenteIn;
  }

  public void setConducenteResidenteIn(String conducenteResidenteIn) {
    this.conducenteResidenteIn = conducenteResidenteIn;
  }

  public String getConducenteResidenteProvincia() {
    return conducenteResidenteProvincia;
  }

  public void setConducenteResidenteProvincia(String conducenteResidenteProvincia) {
    this.conducenteResidenteProvincia = conducenteResidenteProvincia;
  }

  public String getConducenteResidenteNumCivico() {
    return conducenteResidenteNumCivico;
  }

  public void setConducenteResidenteNumCivico(String conducenteResidenteNumCivico) {
    this.conducenteResidenteNumCivico = conducenteResidenteNumCivico;
  }

  public String getConducenteResidenteCap() {
    return conducenteResidenteCap;
  }

  public void setConducenteResidenteCap(String conducenteResidenteCap) {
    this.conducenteResidenteCap = conducenteResidenteCap;
  }

  public String getCategoriaPatente() {
    return categoriaPatente;
  }

  public void setCategoriaPatente(String categoriaPatente) {
    this.categoriaPatente = categoriaPatente;
  }

  public String getNumeroPatente() {
    return numeroPatente;
  }

  public void setNumeroPatente(String numeroPatente) {
    this.numeroPatente = numeroPatente;
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

  public String getNumeroAvviso() {
    return numeroAvviso;
  }

  public void setNumeroAvviso(String numeroAvviso) {
    this.numeroAvviso = numeroAvviso;
  }

  public String getNumeroProtocollo() {
    return numeroProtocollo;
  }

  public void setNumeroProtocollo(String numeroProtocollo) {
    this.numeroProtocollo = numeroProtocollo;
  }

  public LocalDate getDataVerbale() {
    return dataVerbale;
  }

  public void setDataVerbale(LocalDate dataVerbale) {
    this.dataVerbale = dataVerbale;
  }

  public String getNomeProprietario() {
    return nomeProprietario;
  }

  public void setNomeProprietario(String nomeProprietario) {
    this.nomeProprietario = nomeProprietario;
  }

  public String getCognomeProprietario() {
    return cognomeProprietario;
  }

  public void setCognomeProprietario(String cognomeProprietario) {
    this.cognomeProprietario = cognomeProprietario;
  }

  public String getCfProprietario() {
    return cfProprietario;
  }

  public void setCfProprietario(String cfProprietario) {
    this.cfProprietario = cfProprietario;
  }

  public String getProprietarioNatoA() {
    return proprietarioNatoA;
  }

  public void setProprietarioNatoA(String proprietarioNatoA) {
    this.proprietarioNatoA = proprietarioNatoA;
  }

  public String getProprietarioNatoAProvincia() {
    return proprietarioNatoAProvincia;
  }

  public void setProprietarioNatoAProvincia(String proprietarioNatoAProvincia) {
    this.proprietarioNatoAProvincia = proprietarioNatoAProvincia;
  }

  public LocalDate getProprietarioNatoIl() {
    return proprietarioNatoIl;
  }

  public void setProprietarioNatoIl(LocalDate proprietarioNatoIl) {
    this.proprietarioNatoIl = proprietarioNatoIl;
  }

  public String getProprietarioResidenteIn() {
    return proprietarioResidenteIn;
  }

  public void setProprietarioResidenteIn(String proprietarioResidenteIn) {
    this.proprietarioResidenteIn = proprietarioResidenteIn;
  }

  public String getProprietarioResidenteCap() {
    return proprietarioResidenteCap;
  }

  public void setProprietarioResidenteCap(String proprietarioResidenteCap) {
    this.proprietarioResidenteCap = proprietarioResidenteCap;
  }

  public String getProprietarioResidenteCitta() {
    return proprietarioResidenteCitta;
  }

  public void setProprietarioResidenteCitta(String proprietarioResidenteCitta) {
    this.proprietarioResidenteCitta = proprietarioResidenteCitta;
  }

  public String getEmailProprietario() {
    return emailProprietario;
  }

  public void setEmailProprietario(String emailProprietario) {
    this.emailProprietario = emailProprietario;
  }

  public String getEmailConducente() {
    return emailConducente;
  }

  public void setEmailConducente(String emailConducente) {
    this.emailConducente = emailConducente;
  }

  public String getCategoriaPatenteConducente() {
    return categoriaPatenteConducente;
  }

  public void setCategoriaPatenteConducente(String categoriaPatenteConducente) {
    this.categoriaPatenteConducente = categoriaPatenteConducente;
  }

  public String getNumeroPatenteConducente() {
    return numeroPatenteConducente;
  }

  public void setNumeroPatenteConducente(String numeroPatenteConducente) {
    this.numeroPatenteConducente = numeroPatenteConducente;
  }

  public String getPatenteConducenteRilasciataDa() {
    return patenteConducenteRilasciataDa;
  }

  public void setPatenteConducenteRilasciataDa(String patenteConducenteRilasciataDa) {
    this.patenteConducenteRilasciataDa = patenteConducenteRilasciataDa;
  }

  public LocalDate getPatenteConducenteRilasciataIl() {
    return patenteConducenteRilasciataIl;
  }

  public void setPatenteConducenteRilasciataIl(LocalDate patenteConducenteRilasciataIl) {
    this.patenteConducenteRilasciataIl = patenteConducenteRilasciataIl;
  }

  public LocalDate getPatenteConducenteValidaFinoAl() {
    return patenteConducenteValidaFinoAl;
  }

  public void setPatenteConducenteValidaFinoAl(LocalDate patenteConducenteValidaFinoAl) {
    this.patenteConducenteValidaFinoAl = patenteConducenteValidaFinoAl;
  }

  public String getPatenteProprietarioUpload() {
    return patenteProprietarioUpload;
  }

  public void setPatenteProprietarioUpload(String patenteProprietarioUpload) {
    this.patenteProprietarioUpload = patenteProprietarioUpload;
  }

  public String getNomePatenteProprietarioUpload() {
    return nomePatenteProprietarioUpload;
  }

  public void setNomePatenteProprietarioUpload(String nomePatenteProprietarioUpload) {
    this.nomePatenteProprietarioUpload = nomePatenteProprietarioUpload;
  }

  public byte[] getBytePatenteProprietarioUpload() {
    return bytePatenteProprietarioUpload;
  }

  public void setBytePatenteProprietarioUpload(byte[] bytePatenteProprietarioUpload) {
    this.bytePatenteProprietarioUpload = bytePatenteProprietarioUpload;
  }

  public String getPatenteConducenteUpload() {
    return patenteConducenteUpload;
  }

  public void setPatenteConducenteUpload(String patenteConducenteUpload) {
    this.patenteConducenteUpload = patenteConducenteUpload;
  }

  public String getNomePatenteConducenteUpload() {
    return nomePatenteConducenteUpload;
  }

  public void setNomePatenteConducenteUpload(String nomePatenteConducenteUpload) {
    this.nomePatenteConducenteUpload = nomePatenteConducenteUpload;
  }

  public byte[] getBytePatenteConducenteUpload() {
    return bytePatenteConducenteUpload;
  }

  public void setBytePatenteConducenteUpload(byte[] bytePatenteConducenteUpload) {
    this.bytePatenteConducenteUpload = bytePatenteConducenteUpload;
  }

  public String getCfConducente() {
    return cfConducente;
  }

  public void setCfConducente(String cfConducente) {
    this.cfConducente = cfConducente;
  }

  @Override
  public String toString() {
    return "PuntiPatenteProprietario [targa="
        + targa
        + ", dichiarazioneProprietario="
        + dichiarazioneProprietario
        + ", cognomeConducente="
        + cognomeConducente
        + ", nomeConducente="
        + nomeConducente
        + ", cfConducente="
        + cfConducente
        + ", conducenteNatoA="
        + conducenteNatoA
        + ", conducenteNatoAProvincia="
        + conducenteNatoAProvincia
        + ", conducenteNatoIl="
        + conducenteNatoIl
        + ", conducenteResidenteA="
        + conducenteResidenteA
        + ", conducenteResidenteIn="
        + conducenteResidenteIn
        + ", conducenteResidenteProvincia="
        + conducenteResidenteProvincia
        + ", conducenteResidenteNumCivico="
        + conducenteResidenteNumCivico
        + ", conducenteResidenteCap="
        + conducenteResidenteCap
        + ", categoriaPatente="
        + categoriaPatente
        + ", numeroPatente="
        + numeroPatente
        + ", patenteRilasciataDa="
        + patenteRilasciataDa
        + ", patenteRilasciataIl="
        + patenteRilasciataIl
        + ", patenteValidaFinoAl="
        + patenteValidaFinoAl
        + ", numeroAvviso="
        + numeroAvviso
        + ", numeroProtocollo="
        + numeroProtocollo
        + ", dataVerbale="
        + dataVerbale
        + ", nomeProprietario="
        + nomeProprietario
        + ", cognomeProprietario="
        + cognomeProprietario
        + ", cfProprietario="
        + cfProprietario
        + ", proprietarioNatoA="
        + proprietarioNatoA
        + ", proprietarioNatoAProvincia="
        + proprietarioNatoAProvincia
        + ", proprietarioNatoIl="
        + proprietarioNatoIl
        + ", proprietarioResidenteIn="
        + proprietarioResidenteIn
        + ", proprietarioResidenteCap="
        + proprietarioResidenteCap
        + ", proprietarioResidenteCitta="
        + proprietarioResidenteCitta
        + ", emailProprietario="
        + emailProprietario
        + ", emailConducente="
        + emailConducente
        + ", categoriaPatenteConducente="
        + categoriaPatenteConducente
        + ", numeroPatenteConducente="
        + numeroPatenteConducente
        + ", patenteConducenteRilasciataDa="
        + patenteConducenteRilasciataDa
        + ", patenteConducenteRilasciataIl="
        + patenteConducenteRilasciataIl
        + ", patenteConducenteValidaFinoAl="
        + patenteConducenteValidaFinoAl
        + ", nomePatenteProprietarioUpload="
        + nomePatenteProprietarioUpload
        + ", nomePatenteConducenteUpload="
        + nomePatenteConducenteUpload
        + "]";
  }
}
