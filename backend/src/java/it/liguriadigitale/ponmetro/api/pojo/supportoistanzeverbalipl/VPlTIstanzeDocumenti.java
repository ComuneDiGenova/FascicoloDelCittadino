package it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl;

/**
 * VPlTIstanzeDocumenti
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2021-08-10T12:52:52.255
 */
import java.io.Serializable;

public class VPlTIstanzeDocumenti implements Serializable {
  private static final long serialVersionUID = 1L;

  public VPlTIstanzeDocumenti() {
    super();
  }

  /** Type : VARCHAR Name : CODICE_HERMES */
  public String codiceHermes;

  /** Type : VARCHAR Name : TIPOLOGIA */
  public String tipologia;

  /** Type : VARCHAR Name : RIFERIMENTO_LEGGE */
  public String riferimentoLegge;

  /** Type : BIGINT Name : MIN_DOC */
  public java.math.BigDecimal minDoc;

  /** Type : BIGINT Name : MAX_DOC */
  public java.math.BigDecimal maxDoc;

  /** Type : BIGINT Name : CODICE */
  public java.math.BigDecimal codice;

  /** Type : VARCHAR Name : DOCUMENTO */
  public String documento;

  /** Type : VARCHAR Name : FLG_AUTODICHIARAZIONE */
  public String flgAutodichiarazione;

  /** Type : BIGINT Name : ID_DOC_ALTERNATIVO */
  public java.math.BigDecimal idDocAlternativo;

  /** Type : VARCHAR Name : DOCUMENTO_ALTERNATIVO */
  public String documentoAlternativo;

  /** Type : VARCHAR Name : OBBLIGATORIO */
  public String obbligatorio;

  /** Sets the value for codiceHermes */
  public void setCodiceHermes(String codiceHermes) {
    this.codiceHermes = codiceHermes;
  }

  /** Gets the value for codiceHermes */
  public String getCodiceHermes() {
    return codiceHermes;
  }

  /** Sets the value for tipologia */
  public void setTipologia(String tipologia) {
    this.tipologia = tipologia;
  }

  /** Gets the value for tipologia */
  public String getTipologia() {
    return tipologia;
  }

  /** Sets the value for riferimentoLegge */
  public void setRiferimentoLegge(String riferimentoLegge) {
    this.riferimentoLegge = riferimentoLegge;
  }

  /** Gets the value for riferimentoLegge */
  public String getRiferimentoLegge() {
    return riferimentoLegge;
  }

  /** Sets the value for minDoc */
  public void setMinDoc(java.math.BigDecimal minDoc) {
    this.minDoc = minDoc;
  }

  /** Gets the value for minDoc */
  public java.math.BigDecimal getMinDoc() {
    return minDoc;
  }

  /** Sets the value for maxDoc */
  public void setMaxDoc(java.math.BigDecimal maxDoc) {
    this.maxDoc = maxDoc;
  }

  /** Gets the value for maxDoc */
  public java.math.BigDecimal getMaxDoc() {
    return maxDoc;
  }

  /** Sets the value for codice */
  public void setCodice(java.math.BigDecimal codice) {
    this.codice = codice;
  }

  /** Gets the value for codice */
  public java.math.BigDecimal getCodice() {
    return codice;
  }

  /** Sets the value for documento */
  public void setDocumento(String documento) {
    this.documento = documento;
  }

  /** Gets the value for documento */
  public String getDocumento() {
    return documento;
  }

  /** Sets the value for flgAutodichiarazione */
  public void setFlgAutodichiarazione(String flgAutodichiarazione) {
    this.flgAutodichiarazione = flgAutodichiarazione;
  }

  /** Gets the value for flgAutodichiarazione */
  public String getFlgAutodichiarazione() {
    return flgAutodichiarazione;
  }

  /** Sets the value for idDocAlternativo */
  public void setIdDocAlternativo(java.math.BigDecimal idDocAlternativo) {
    this.idDocAlternativo = idDocAlternativo;
  }

  /** Gets the value for idDocAlternativo */
  public java.math.BigDecimal getIdDocAlternativo() {
    return idDocAlternativo;
  }

  /** Sets the value for documentoAlternativo */
  public void setDocumentoAlternativo(String documentoAlternativo) {
    this.documentoAlternativo = documentoAlternativo;
  }

  /** Gets the value for documentoAlternativo */
  public String getDocumentoAlternativo() {
    return documentoAlternativo;
  }

  /** Sets the value for obbligatorio */
  public void setObbligatorio(String obbligatorio) {
    this.obbligatorio = obbligatorio;
  }

  /** Gets the value for obbligatorio */
  public String getObbligatorio() {
    return obbligatorio;
  }

  @Override
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
