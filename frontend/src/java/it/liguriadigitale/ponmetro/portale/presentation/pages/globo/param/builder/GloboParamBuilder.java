package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.builder;

import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboParam;

public class GloboParamBuilder {

  private String idFunzione;
  private String idProcedimento;

  public static GloboParamBuilder getInstance() {
    return new GloboParamBuilder();
  }

  public GloboParamBuilder addIdFunzione(String idFunzione) {
    if (!"na".equalsIgnoreCase(idFunzione)) {
      this.idFunzione = idFunzione;
    }
    return this;
  }

  public GloboParamBuilder addIdProcedimento(String idProcedimento) {
    this.idProcedimento = idProcedimento;
    return this;
  }

  public GloboParam build() {

    GloboParam globoParam = new GloboParam();
    globoParam.setIdFunzione(idFunzione);
    globoParam.setIdProcedimento(idProcedimento);
    return globoParam;
  }
}
