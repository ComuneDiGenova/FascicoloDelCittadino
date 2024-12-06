package it.liguriadigitale.ponmetro.api.pojo.breadcrumb;

/**
 * VCfgTCatBreadcrumb
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-11-24T13:57:37.572
 */
import java.io.Serializable;

public class VCfgTCatBreadcrumb implements Serializable {
  private static final long serialVersionUID = 1L;

  public VCfgTCatBreadcrumb() {
    super();
  }

  /** Type : BIGINT Name : FIGLIOFUNZ */
  public Long figliofunz;

  /** Type : BIGINT Name : FUNZ */
  public java.math.BigDecimal funz;

  /** Type : VARCHAR Name : DESCRFUNZ */
  public String descrfunz;

  /** Type : VARCHAR Name : IDPAGINA */
  public String idpagina;

  /** Type : BIGINT Name : ISFIGLIO */
  public java.math.BigDecimal isfiglio;

  /** Type : BIGINT Name : ISSEZIONE */
  public java.math.BigDecimal issezione;

  /** Type : BIGINT Name : ISDELEGABILE */
  public java.math.BigDecimal isdelegabile;

  /** Type : BIGINT Name : ID_SEZ */
  public java.math.BigDecimal idSez;

  /** Type : VARCHAR Name : DESCRIZIONE_SEZ */
  public String descrizioneSez;

  /** Type : VARCHAR Name : URI_SEZ */
  public String uriSez;

  /** Sets the value for figliofunz */
  public void setFigliofunz(Long figliofunz) {
    this.figliofunz = figliofunz;
  }

  /** Gets the value for figliofunz */
  public Long getFigliofunz() {
    return figliofunz;
  }

  /** Sets the value for funz */
  public void setFunz(java.math.BigDecimal funz) {
    this.funz = funz;
  }

  /** Gets the value for funz */
  public java.math.BigDecimal getFunz() {
    return funz;
  }

  /** Sets the value for descrfunz */
  public void setDescrfunz(String descrfunz) {
    this.descrfunz = descrfunz;
  }

  /** Gets the value for descrfunz */
  public String getDescrfunz() {
    return descrfunz;
  }

  /** Sets the value for idpagina */
  public void setIdpagina(String idpagina) {
    this.idpagina = idpagina;
  }

  /** Gets the value for idpagina */
  public String getIdpagina() {
    return idpagina;
  }

  /** Sets the value for isfiglio */
  public void setIsfiglio(java.math.BigDecimal isfiglio) {
    this.isfiglio = isfiglio;
  }

  /** Gets the value for isfiglio */
  public java.math.BigDecimal getIsfiglio() {
    return isfiglio;
  }

  /** Sets the value for issezione */
  public void setIssezione(java.math.BigDecimal issezione) {
    this.issezione = issezione;
  }

  /** Gets the value for issezione */
  public java.math.BigDecimal getIssezione() {
    return issezione;
  }

  /** Sets the value for isdelegabile */
  public void setIsdelegabile(java.math.BigDecimal isdelegabile) {
    this.isdelegabile = isdelegabile;
  }

  /** Gets the value for isdelegabile */
  public java.math.BigDecimal getIsdelegabile() {
    return isdelegabile;
  }

  /** Sets the value for idSez */
  public void setIdSez(java.math.BigDecimal idSez) {
    this.idSez = idSez;
  }

  /** Gets the value for idSez */
  public java.math.BigDecimal getIdSez() {
    return idSez;
  }

  /** Sets the value for descrizioneSez */
  public void setDescrizioneSez(String descrizioneSez) {
    this.descrizioneSez = descrizioneSez;
  }

  /** Gets the value for descrizioneSez */
  public String getDescrizioneSez() {
    return descrizioneSez;
  }

  /** Sets the value for uriSez */
  public void setUriSez(String uriSez) {
    this.uriSez = uriSez;
  }

  /** Gets the value for uriSez */
  public String getUriSez() {
    return uriSez;
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
