package it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl;

/**
 * VPlTIstanzeSerie
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2021-08-10T12:52:52.334
 */
import java.io.Serializable;

public class VPlTIstanzeSerie implements Serializable {
  private static final long serialVersionUID = 1L;

  public VPlTIstanzeSerie() {
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

  /** Type : VARCHAR Name : FLG_INTEGRAZIONI */
  public String flgIntegrazioni;

  /** Type : BIGINT Name : TIPO_RELAZIONE_SERIE */
  public java.math.BigDecimal tipoRelazioneSerie;

  /** Type : VARCHAR Name : CODICE */
  public String codice;

  /** Type : VARCHAR Name : DESCRIZIONE_SERIE */
  public String descrizioneSerie;

  /** Type : BIGINT Name : TIPO */
  public java.math.BigDecimal tipo;

  /** Type : VARCHAR Name : TIPO_DESCRIZIONE */
  public String tipoDescrizione;

  /** Type : BIGINT Name : ARTICOLO */
  public java.math.BigDecimal articolo;

  /** Type : VARCHAR Name : SERIE */
  public String serie;

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

  /** Sets the value for flgIntegrazioni */
  public void setFlgIntegrazioni(String flgIntegrazioni) {
    this.flgIntegrazioni = flgIntegrazioni;
  }

  /** Gets the value for flgIntegrazioni */
  public String getFlgIntegrazioni() {
    return flgIntegrazioni;
  }

  /** Sets the value for tipoRelazioneSerie */
  public void setTipoRelazioneSerie(java.math.BigDecimal tipoRelazioneSerie) {
    this.tipoRelazioneSerie = tipoRelazioneSerie;
  }

  /** Gets the value for tipoRelazioneSerie */
  public java.math.BigDecimal getTipoRelazioneSerie() {
    return tipoRelazioneSerie;
  }

  /** Sets the value for codice */
  public void setCodice(String codice) {
    this.codice = codice;
  }

  /** Gets the value for codice */
  public String getCodice() {
    return codice;
  }

  /** Sets the value for descrizioneSerie */
  public void setDescrizioneSerie(String descrizioneSerie) {
    this.descrizioneSerie = descrizioneSerie;
  }

  /** Gets the value for descrizioneSerie */
  public String getDescrizioneSerie() {
    return descrizioneSerie;
  }

  /** Sets the value for tipo */
  public void setTipo(java.math.BigDecimal tipo) {
    this.tipo = tipo;
  }

  /** Gets the value for tipo */
  public java.math.BigDecimal getTipo() {
    return tipo;
  }

  /** Sets the value for tipoDescrizione */
  public void setTipoDescrizione(String tipoDescrizione) {
    this.tipoDescrizione = tipoDescrizione;
  }

  /** Gets the value for tipoDescrizione */
  public String getTipoDescrizione() {
    return tipoDescrizione;
  }

  /** Sets the value for articolo */
  public void setArticolo(java.math.BigDecimal articolo) {
    this.articolo = articolo;
  }

  /** Gets the value for articolo */
  public java.math.BigDecimal getArticolo() {
    return articolo;
  }

  /** Sets the value for serie */
  public void setSerie(String serie) {
    this.serie = serie;
  }

  /** Gets the value for serie */
  public String getSerie() {
    return serie;
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
