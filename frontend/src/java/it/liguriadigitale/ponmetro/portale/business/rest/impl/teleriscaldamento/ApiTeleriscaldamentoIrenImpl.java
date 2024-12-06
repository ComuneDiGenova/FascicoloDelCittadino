package it.liguriadigitale.ponmetro.portale.business.rest.impl.teleriscaldamento;

import it.liguriadigitale.ponmetro.teleriscaldamentoiren.apiclient.InserimentoRichiestaBonusApi;
import it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonus;
import it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonusResponse;

public class ApiTeleriscaldamentoIrenImpl implements InserimentoRichiestaBonusApi {

  private InserimentoRichiestaBonusApi instance;

  public ApiTeleriscaldamentoIrenImpl(InserimentoRichiestaBonusApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public FileRichiestaBonusResponse apiServiceRichiestaBonusPost(FileRichiestaBonus arg0) {
    return instance.apiServiceRichiestaBonusPost(arg0);
  }
}
