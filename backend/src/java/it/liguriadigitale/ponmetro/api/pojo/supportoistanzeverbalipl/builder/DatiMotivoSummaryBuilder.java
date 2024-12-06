package it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.builder;

import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivoSummary;

public class DatiMotivoSummaryBuilder {

  private String codice;
  private String descrizione;
  private String flagIntegrazione;
  private String riferimentoLegge;
  private String descrizioneSerieArticolo;

  public String getCodice() {
    return codice;
  }

  public DatiMotivoSummaryBuilder setCodice(String codice) {
    this.codice = codice;
    return this;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public DatiMotivoSummaryBuilder setDescrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

  public String getDescrizioneSerieArticolo() {
    return descrizioneSerieArticolo;
  }

  public DatiMotivoSummaryBuilder setDescrizioneSerieArticolo(String descrizioneSerieArticolo) {
    this.descrizioneSerieArticolo = descrizioneSerieArticolo;
    return this;
  }

  public String getFlagIntegrazione() {
    return flagIntegrazione;
  }

  public DatiMotivoSummaryBuilder setFlagIntegrazione(String flagIntegrazione) {
    this.flagIntegrazione = flagIntegrazione;
    return this;
  }

  public String getRiferimentoLegge() {
    return riferimentoLegge;
  }

  public DatiMotivoSummaryBuilder setRiferimentoLegge(String riferimentoLegge) {
    this.riferimentoLegge = riferimentoLegge;
    return this;
  }

  public DatiMotivoSummary build() {
    DatiMotivoSummary toRet = new DatiMotivoSummary();

    toRet.setCodice(codice);
    toRet.setDescrizione(descrizione);
    toRet.setFlagIntegrazione(flagIntegrazione);
    toRet.setRiferimentoLegge(riferimentoLegge);
    toRet.setDescrizioneSerieArticolo(descrizioneSerieArticolo);

    return toRet;
  }
}
