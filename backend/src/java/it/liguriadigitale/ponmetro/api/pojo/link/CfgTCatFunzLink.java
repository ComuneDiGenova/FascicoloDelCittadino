package it.liguriadigitale.ponmetro.api.pojo.link;

/**
 * CfgTCatFunzLink
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-01-09T10:42:10.921
 */
import java.io.Serializable;

public class CfgTCatFunzLink implements Serializable {
  private static final long serialVersionUID = 1L;

  public CfgTCatFunzLink() {
    super();
  }

  /** Type : BIGINT Name : ID_LINK */
  public java.math.BigDecimal idLink;

  /** Type : BIGINT Name : ID_FUNZ */
  public java.math.BigDecimal idFunz;

  /** Type : VARCHAR Name : TIPO_LINK */
  public String tipoLink;

  /** Type : VARCHAR Name : DESCRIZIONI_TOOLTIP */
  public String descrizioniTooltip;

  /** Type : VARCHAR Name : URL */
  public String url;

  /** Type : VARCHAR Name : ICONA_CSS */
  public String iconaCss;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE */
  public Boolean flagAbilitazione;

  /** Type : BIGINT Name : ID_STATO_REC */
  public Boolean idStatoRec;

  /** Type : BIGINT Name : FLAG_RESIDENTE */
  public Boolean flagResidente;

  /** Type : BIGINT Name : FLAG_NONRESIDENTE */
  public Boolean flagNonresidente;

  /** Type : BIGINT Name : CODICE_MAGGIOLI */
  public java.math.BigDecimal codiceMaggioli;

  /** Sets the value for idLink */
  public void setIdLink(java.math.BigDecimal idLink) {
    this.idLink = idLink;
  }

  /** Gets the value for idLink */
  public java.math.BigDecimal getIdLink() {
    return idLink;
  }

  /** Sets the value for idFunz */
  public void setIdFunz(java.math.BigDecimal idFunz) {
    this.idFunz = idFunz;
  }

  /** Gets the value for idFunz */
  public java.math.BigDecimal getIdFunz() {
    return idFunz;
  }

  /** Sets the value for tipoLink */
  public void setTipoLink(String tipoLink) {
    this.tipoLink = tipoLink;
  }

  /** Gets the value for tipoLink */
  public String getTipoLink() {
    return tipoLink;
  }

  /** Sets the value for descrizioniTooltip */
  public void setDescrizioniTooltip(String descrizioniTooltip) {
    this.descrizioniTooltip = descrizioniTooltip;
  }

  /** Gets the value for descrizioniTooltip */
  public String getDescrizioniTooltip() {
    return descrizioniTooltip;
  }

  /** Sets the value for url */
  public void setUrl(String url) {
    this.url = url;
  }

  /** Gets the value for url */
  public String getUrl() {
    return url;
  }

  /** Sets the value for iconaCss */
  public void setIconaCss(String iconaCss) {
    this.iconaCss = iconaCss;
  }

  /** Gets the value for iconaCss */
  public String getIconaCss() {
    return iconaCss;
  }

  /** Sets the value for flagAbilitazione */
  public void setFlagAbilitazione(Boolean flagAbilitazione) {
    this.flagAbilitazione = flagAbilitazione;
  }

  /** Gets the value for flagAbilitazione */
  public Boolean getFlagAbilitazione() {
    return flagAbilitazione;
  }

  /** Sets the value for idStatoRec */
  public void setIdStatoRec(Boolean idStatoRec) {
    this.idStatoRec = idStatoRec;
  }

  /** Gets the value for idStatoRec */
  public Boolean getIdStatoRec() {
    return idStatoRec;
  }

  /** Sets the value for flagResidente */
  public void setFlagResidente(Boolean flagResidente) {
    this.flagResidente = flagResidente;
  }

  /** Gets the value for flagResidente */
  public Boolean getFlagResidente() {
    return flagResidente;
  }

  /** Sets the value for flagNonresidente */
  public void setFlagNonresidente(Boolean flagNonresidente) {
    this.flagNonresidente = flagNonresidente;
  }

  /** Gets the value for flagNonresidente */
  public Boolean getFlagNonresidente() {
    return flagNonresidente;
  }

  /** Sets the value for codiceMaggioli */
  public void setCodiceMaggioli(java.math.BigDecimal codiceMaggioli) {
    this.codiceMaggioli = codiceMaggioli;
  }

  /** Gets the value for codiceMaggioli */
  public java.math.BigDecimal getCodiceMaggioli() {
    return codiceMaggioli;
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
