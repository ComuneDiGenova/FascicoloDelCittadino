package it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.builder;

import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivo;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivoSummary;

public class DatiMotivoBuilder {

  private DatiMotivoSummary datiSummary;
  private String riferimentoLegge;
  private String minDoc;
  private String maxDoc;
  private String tipoRelazioneSerie;
  private String codice;
  private String tipo;
  private String tipoDescrizione;
  private String articolo;
  private String serie;

  public DatiMotivoSummary getDatiSummary() {
    return datiSummary;
  }

  public DatiMotivoBuilder setDatiSummary(DatiMotivoSummary datiSummary) {
    this.datiSummary = datiSummary;
    return this;
  }

  public String getRiferimentoLegge() {
    return riferimentoLegge;
  }

  public DatiMotivoBuilder setRiferimentoLegge(String riferimentoLegge) {
    this.riferimentoLegge = riferimentoLegge;
    return this;
  }

  public String getMinDoc() {
    return minDoc;
  }

  public DatiMotivoBuilder setMinDoc(String minDoc) {
    this.minDoc = minDoc;
    return this;
  }

  public String getMaxDoc() {
    return maxDoc;
  }

  public DatiMotivoBuilder setMaxDoc(String maxDoc) {
    this.maxDoc = maxDoc;
    return this;
  }

  public String getTipoRelazioneSerie() {
    return tipoRelazioneSerie;
  }

  public DatiMotivoBuilder setTipoRelazioneSerie(String tipoRelazioneSerie) {
    this.tipoRelazioneSerie = tipoRelazioneSerie;
    return this;
  }

  public String getCodice() {
    return codice;
  }

  public DatiMotivoBuilder setCodice(String codice) {
    this.codice = codice;
    return this;
  }

  public String getTipo() {
    return tipo;
  }

  public DatiMotivoBuilder setTipo(String tipo) {
    this.tipo = tipo;
    return this;
  }

  public String getTipoDescrizione() {
    return tipoDescrizione;
  }

  public DatiMotivoBuilder setTipoDescrizione(String tipoDescrizione) {
    this.tipoDescrizione = tipoDescrizione;
    return this;
  }

  public String getArticolo() {
    return articolo;
  }

  public DatiMotivoBuilder setArticolo(String articolo) {
    this.articolo = articolo;
    return this;
  }

  public String getSerie() {
    return serie;
  }

  public DatiMotivoBuilder setSerie(String serie) {
    this.serie = serie;
    return this;
  }

  public DatiMotivo build() {
    DatiMotivo toRet = new DatiMotivo();

    toRet.setDatiSummary(datiSummary);
    toRet.setRiferimentoLegge(riferimentoLegge);
    toRet.setMinDoc(minDoc);
    toRet.setMaxDoc(maxDoc);
    toRet.setTipoRelazioneSerie(tipoRelazioneSerie);
    toRet.setCodice(codice);
    toRet.setTipo(tipo);
    toRet.setTipoDescrizione(tipoDescrizione);
    toRet.setArticolo(articolo);
    toRet.setSerie(serie);

    return toRet;
  }
}
