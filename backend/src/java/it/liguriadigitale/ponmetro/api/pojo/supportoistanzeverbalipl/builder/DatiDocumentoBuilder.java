package it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.builder;

import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiDocumento;

public class DatiDocumentoBuilder {

  private String codice;
  private String descrizione;
  private String riferimentoLegge;
  private String minDoc;
  private String maxDoc;
  private String codiceInterno;
  private String documento;
  private String flagAutodichiarazione;
  private String idDocumentoAlternativo;
  private String documentoAlternativo;
  private String obbligatorio;

  public String getCodice() {
    return codice;
  }

  public DatiDocumentoBuilder setCodice(String codice) {
    this.codice = codice;
    return this;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public DatiDocumentoBuilder setDescrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

  public String getRiferimentoLegge() {
    return riferimentoLegge;
  }

  public DatiDocumentoBuilder setRiferimentoLegge(String riferimentoLegge) {
    this.riferimentoLegge = riferimentoLegge;
    return this;
  }

  public String getMinDoc() {
    return minDoc;
  }

  public DatiDocumentoBuilder setMinDoc(String minDoc) {
    this.minDoc = minDoc;
    return this;
  }

  public String getMaxDoc() {
    return maxDoc;
  }

  public DatiDocumentoBuilder setMaxDoc(String maxDoc) {
    this.maxDoc = maxDoc;
    return this;
  }

  public String getCodiceInterno() {
    return codiceInterno;
  }

  public DatiDocumentoBuilder setCodiceInterno(String codiceInterno) {
    this.codiceInterno = codiceInterno;
    return this;
  }

  public String getDocumento() {
    return documento;
  }

  public DatiDocumentoBuilder setDocumento(String documento) {
    this.documento = documento;
    return this;
  }

  public String getFlagAutodichiarazione() {
    return flagAutodichiarazione;
  }

  public DatiDocumentoBuilder setFlagAutodichiarazione(String flagAutodichiarazione) {
    this.flagAutodichiarazione = flagAutodichiarazione;
    return this;
  }

  public String getIdDocumentoAlternativo() {
    return idDocumentoAlternativo;
  }

  public DatiDocumentoBuilder setIdDocumentoAlternativo(String idDocumentoAlternativo) {
    this.idDocumentoAlternativo = idDocumentoAlternativo;
    return this;
  }

  public String getDocumentoAlternativo() {
    return documentoAlternativo;
  }

  public DatiDocumentoBuilder setDocumentoAlternativo(String documentoAlternativo) {
    this.documentoAlternativo = documentoAlternativo;
    return this;
  }

  public String getObbligatorio() {
    return obbligatorio;
  }

  public DatiDocumentoBuilder setObbligatorio(String obbligatorio) {
    this.obbligatorio = obbligatorio;
    return this;
  }

  public DatiDocumento build() {
    DatiDocumento toRet = new DatiDocumento();

    toRet.setCodice(codice);
    toRet.setDescrizione(descrizione);
    toRet.setRiferimentoLegge(riferimentoLegge);
    toRet.setMinDoc(minDoc);
    toRet.setMaxDoc(maxDoc);
    toRet.setCodiceInterno(codiceInterno);
    toRet.setDocumento(documento);
    toRet.setFlagAutodichiarazione(flagAutodichiarazione);
    toRet.setIdDocumentoAlternativo(idDocumentoAlternativo);
    toRet.setDocumentoAlternativo(documentoAlternativo);
    toRet.setObbligatorio(obbligatorio);

    return toRet;
  }
}
