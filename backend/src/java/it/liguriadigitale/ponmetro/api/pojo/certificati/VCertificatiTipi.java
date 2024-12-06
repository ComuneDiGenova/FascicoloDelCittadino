package it.liguriadigitale.ponmetro.api.pojo.certificati;

/**
 * VCertificatiTipi
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2021-09-06T15:51:39.642
 */
import java.io.Serializable;

public class VCertificatiTipi implements Serializable {
  private static final long serialVersionUID = 1L;

  public VCertificatiTipi() {
    super();
  }

  /** Type : BIGINT Name : ID_T_CERTIFICATO */
  public Long idTCertificato;

  /** Type : VARCHAR Name : CODICE_ANPR */
  public String codiceAnpr;

  /** Type : VARCHAR Name : TIPO */
  public String tipo;

  /** Type : VARCHAR Name : EVENTO */
  public String evento;

  /** Type : VARCHAR Name : CERTIFICATO */
  public String certificato;

  /** Type : VARCHAR Name : INVIO */
  public String invio;

  /** Type : BIGINT Name : ANNO_DA */
  public Long annoDa;

  /** Type : BIGINT Name : ANNO_A */
  public Long annoA;

  /** Type : VARCHAR Name : MARCA_DA_BOLLO */
  public String marcaDaBollo;

  /** Type : VARCHAR Name : RIMBORSO_SPESE */
  public String rimborsoSpese;

  /** Type : VARCHAR Name : DIRITTI_DI_SEGRETERIA */
  public String dirittiDiSegreteria;

  /** Type : VARCHAR Name : RESTRIZIONI */
  public String restrizioni;

  /** Type : VARCHAR Name : INFO_DA_CHIEDERE */
  public String infoDaChiedere;

  /** Type : BIGINT Name : ID_STATO_REC_TIPO */
  public Long idStatoRecTipo;

  /** Type : BIGINT Name : ID_STATO_REC_INVIO */
  public Long idStatoRecInvio;

  /** Type : VARCHAR Name : UTENTE_INS */
  public String utenteIns;

  /** Type : TIMESTAMP Name : DATA_INS */
  public java.time.LocalDateTime dataIns;

  /** Type : VARCHAR Name : UTENTE_AGG */
  public String utenteAgg;

  /** Type : TIMESTAMP Name : DATA_AGG */
  public java.time.LocalDateTime dataAgg;

  /** Sets the value for idTCertificato */
  public void setIdTCertificato(Long idTCertificato) {
    this.idTCertificato = idTCertificato;
  }

  /** Gets the value for idTCertificato */
  public Long getIdTCertificato() {
    return idTCertificato;
  }

  /** Sets the value for codiceAnpr */
  public void setCodiceAnpr(String codiceAnpr) {
    this.codiceAnpr = codiceAnpr;
  }

  /** Gets the value for codiceAnpr */
  public String getCodiceAnpr() {
    return codiceAnpr;
  }

  /** Sets the value for tipo */
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  /** Gets the value for tipo */
  public String getTipo() {
    return tipo;
  }

  /** Sets the value for evento */
  public void setEvento(String evento) {
    this.evento = evento;
  }

  /** Gets the value for evento */
  public String getEvento() {
    return evento;
  }

  /** Sets the value for certificato */
  public void setCertificato(String certificato) {
    this.certificato = certificato;
  }

  /** Gets the value for certificato */
  public String getCertificato() {
    return certificato;
  }

  /** Sets the value for invio */
  public void setInvio(String invio) {
    this.invio = invio;
  }

  /** Gets the value for invio */
  public String getInvio() {
    return invio;
  }

  /** Sets the value for annoDa */
  public void setAnnoDa(Long annoDa) {
    this.annoDa = annoDa;
  }

  /** Gets the value for annoDa */
  public Long getAnnoDa() {
    return annoDa;
  }

  /** Sets the value for annoA */
  public void setAnnoA(Long annoA) {
    this.annoA = annoA;
  }

  /** Gets the value for annoA */
  public Long getAnnoA() {
    return annoA;
  }

  /** Sets the value for marcaDaBollo */
  public void setMarcaDaBollo(String marcaDaBollo) {
    this.marcaDaBollo = marcaDaBollo;
  }

  /** Gets the value for marcaDaBollo */
  public String getMarcaDaBollo() {
    return marcaDaBollo;
  }

  /** Sets the value for rimborsoSpese */
  public void setRimborsoSpese(String rimborsoSpese) {
    this.rimborsoSpese = rimborsoSpese;
  }

  /** Gets the value for rimborsoSpese */
  public String getRimborsoSpese() {
    return rimborsoSpese;
  }

  /** Sets the value for dirittiDiSegreteria */
  public void setDirittiDiSegreteria(String dirittiDiSegreteria) {
    this.dirittiDiSegreteria = dirittiDiSegreteria;
  }

  /** Gets the value for dirittiDiSegreteria */
  public String getDirittiDiSegreteria() {
    return dirittiDiSegreteria;
  }

  /** Sets the value for restrizioni */
  public void setRestrizioni(String restrizioni) {
    this.restrizioni = restrizioni;
  }

  /** Gets the value for restrizioni */
  public String getRestrizioni() {
    return restrizioni;
  }

  /** Sets the value for infoDaChiedere */
  public void setInfoDaChiedere(String infoDaChiedere) {
    this.infoDaChiedere = infoDaChiedere;
  }

  /** Gets the value for infoDaChiedere */
  public String getInfoDaChiedere() {
    return infoDaChiedere;
  }

  /** Sets the value for idStatoRecTipo */
  public void setIdStatoRecTipo(Long idStatoRecTipo) {
    this.idStatoRecTipo = idStatoRecTipo;
  }

  /** Gets the value for idStatoRecTipo */
  public Long getIdStatoRecTipo() {
    return idStatoRecTipo;
  }

  /** Sets the value for idStatoRecInvio */
  public void setIdStatoRecInvio(Long idStatoRecInvio) {
    this.idStatoRecInvio = idStatoRecInvio;
  }

  /** Gets the value for idStatoRecInvio */
  public Long getIdStatoRecInvio() {
    return idStatoRecInvio;
  }

  /** Sets the value for utenteIns */
  public void setUtenteIns(String utenteIns) {
    this.utenteIns = utenteIns;
  }

  /** Gets the value for utenteIns */
  public String getUtenteIns() {
    return utenteIns;
  }

  /** Sets the value for dataIns */
  public void setDataIns(java.time.LocalDateTime dataIns) {
    this.dataIns = dataIns;
  }

  /** Gets the value for dataIns */
  public java.time.LocalDateTime getDataIns() {
    return dataIns;
  }

  /** Sets the value for utenteAgg */
  public void setUtenteAgg(String utenteAgg) {
    this.utenteAgg = utenteAgg;
  }

  /** Gets the value for utenteAgg */
  public String getUtenteAgg() {
    return utenteAgg;
  }

  /** Sets the value for dataAgg */
  public void setDataAgg(java.time.LocalDateTime dataAgg) {
    this.dataAgg = dataAgg;
  }

  /** Gets the value for dataAgg */
  public java.time.LocalDateTime getDataAgg() {
    return dataAgg;
  }

  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append(super.toString());
    try {
      java.lang.reflect.Field[] fields = getClass().getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
        String fieldName = fields[i].getName();
        Object fieldValue = fields[i].get(this);
        String line = "\n" + fieldName + ": " + fieldValue;
        str.append(line);
      }
      return str.toString();
    } catch (Exception e) {
      return str.toString();
    }
  }
}
