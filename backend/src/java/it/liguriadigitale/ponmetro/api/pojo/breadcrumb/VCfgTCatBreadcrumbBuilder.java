package it.liguriadigitale.ponmetro.api.pojo.breadcrumb;

/**
 * VCfgTCatBreadcrumb
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen null! Non modificare! Creato il:
 * 2023-11-24T13:57:37.586
 */
public class VCfgTCatBreadcrumbBuilder {

  public Long figliofunz;

  public java.math.BigDecimal funz;

  public String descrfunz;

  public String idpagina;

  public java.math.BigDecimal isfiglio;

  public java.math.BigDecimal issezione;

  public java.math.BigDecimal isdelegabile;

  public java.math.BigDecimal idSez;

  public String descrizioneSez;

  public String uriSez;

  public VCfgTCatBreadcrumbBuilder getInstance() {
    return new VCfgTCatBreadcrumbBuilder();
  }

  public VCfgTCatBreadcrumbBuilder addFigliofunz(Long figliofunz) {
    this.figliofunz = figliofunz;
    return this;
  }

  public VCfgTCatBreadcrumbBuilder addFunz(java.math.BigDecimal funz) {
    this.funz = funz;
    return this;
  }

  public VCfgTCatBreadcrumbBuilder addDescrfunz(String descrfunz) {
    this.descrfunz = descrfunz;
    return this;
  }

  public VCfgTCatBreadcrumbBuilder addIdpagina(String idpagina) {
    this.idpagina = idpagina;
    return this;
  }

  public VCfgTCatBreadcrumbBuilder addIsfiglio(java.math.BigDecimal isfiglio) {
    this.isfiglio = isfiglio;
    return this;
  }

  public VCfgTCatBreadcrumbBuilder addIssezione(java.math.BigDecimal issezione) {
    this.issezione = issezione;
    return this;
  }

  public VCfgTCatBreadcrumbBuilder addIsdelegabile(java.math.BigDecimal isdelegabile) {
    this.isdelegabile = isdelegabile;
    return this;
  }

  public VCfgTCatBreadcrumbBuilder addIdSez(java.math.BigDecimal idSez) {
    this.idSez = idSez;
    return this;
  }

  public VCfgTCatBreadcrumbBuilder addDescrizioneSez(String descrizioneSez) {
    this.descrizioneSez = descrizioneSez;
    return this;
  }

  public VCfgTCatBreadcrumbBuilder addUriSez(String uriSez) {
    this.uriSez = uriSez;
    return this;
  }

  public VCfgTCatBreadcrumb build() {
    VCfgTCatBreadcrumb obj = new VCfgTCatBreadcrumb();
    obj.setFigliofunz(this.figliofunz);
    obj.setFunz(this.funz);
    obj.setDescrfunz(this.descrfunz);
    obj.setIdpagina(this.idpagina);
    obj.setIsfiglio(this.isfiglio);
    obj.setIssezione(this.issezione);
    obj.setIsdelegabile(this.isdelegabile);
    obj.setIdSez(this.idSez);
    obj.setDescrizioneSez(this.descrizioneSez);
    obj.setUriSez(this.uriSez);
    return obj;
  }
}
