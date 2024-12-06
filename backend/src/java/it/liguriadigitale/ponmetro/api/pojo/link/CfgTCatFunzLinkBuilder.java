package it.liguriadigitale.ponmetro.api.pojo.link;

/**
 * CfgTCatFunzLink
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen null! Non modificare! Creato il:
 * 2023-01-09T10:42:10.943
 */
public class CfgTCatFunzLinkBuilder {

  public java.math.BigDecimal idLink;

  public java.math.BigDecimal idFunz;

  public String tipoLink;

  public String descrizioniTooltip;

  public String url;

  public String iconaCss;

  public Boolean flagAbilitazione;

  public Boolean idStatoRec;

  public Boolean flagResidente;

  public Boolean flagNonresidente;

  public java.math.BigDecimal codiceMaggioli;

  public CfgTCatFunzLinkBuilder getInstance() {
    return new CfgTCatFunzLinkBuilder();
  }

  public CfgTCatFunzLinkBuilder addIdLink(java.math.BigDecimal idLink) {
    this.idLink = idLink;
    return this;
  }

  public CfgTCatFunzLinkBuilder addIdFunz(java.math.BigDecimal idFunz) {
    this.idFunz = idFunz;
    return this;
  }

  public CfgTCatFunzLinkBuilder addTipoLink(String tipoLink) {
    this.tipoLink = tipoLink;
    return this;
  }

  public CfgTCatFunzLinkBuilder addDescrizioniTooltip(String descrizioniTooltip) {
    this.descrizioniTooltip = descrizioniTooltip;
    return this;
  }

  public CfgTCatFunzLinkBuilder addUrl(String url) {
    this.url = url;
    return this;
  }

  public CfgTCatFunzLinkBuilder addIconaCss(String iconaCss) {
    this.iconaCss = iconaCss;
    return this;
  }

  public CfgTCatFunzLinkBuilder addFlagAbilitazione(Boolean flagAbilitazione) {
    this.flagAbilitazione = flagAbilitazione;
    return this;
  }

  public CfgTCatFunzLinkBuilder addIdStatoRec(Boolean idStatoRec) {
    this.idStatoRec = idStatoRec;
    return this;
  }

  public CfgTCatFunzLinkBuilder addFlagResidente(Boolean flagResidente) {
    this.flagResidente = flagResidente;
    return this;
  }

  public CfgTCatFunzLinkBuilder addFlagNonresidente(Boolean flagNonresidente) {
    this.flagNonresidente = flagNonresidente;
    return this;
  }

  public CfgTCatFunzLinkBuilder addCodiceMaggioli(java.math.BigDecimal codiceMaggioli) {
    this.codiceMaggioli = codiceMaggioli;
    return this;
  }

  public CfgTCatFunzLink build() {
    CfgTCatFunzLink obj = new CfgTCatFunzLink();
    obj.setIdLink(this.idLink);
    obj.setIdFunz(this.idFunz);
    obj.setTipoLink(this.tipoLink);
    obj.setDescrizioniTooltip(this.descrizioniTooltip);
    obj.setUrl(this.url);
    obj.setIconaCss(this.iconaCss);
    obj.setFlagAbilitazione(this.flagAbilitazione);
    obj.setIdStatoRec(this.idStatoRec);
    obj.setFlagResidente(this.flagResidente);
    obj.setFlagNonresidente(this.flagNonresidente);
    obj.setCodiceMaggioli(this.codiceMaggioli);
    return obj;
  }
}
