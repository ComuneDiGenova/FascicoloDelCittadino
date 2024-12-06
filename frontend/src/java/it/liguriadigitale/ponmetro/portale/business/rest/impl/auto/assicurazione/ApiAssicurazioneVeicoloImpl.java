package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.assicurazione;

import it.liguriadigitale.ponmetro.assicurazioneveicoli.apiclient.AssicurazioneVeicoloApi;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.TipoVeicolo;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.VerificaAssicurazioneVeicoli;

public class ApiAssicurazioneVeicoloImpl implements AssicurazioneVeicoloApi {

  private AssicurazioneVeicoloApi instance;

  public ApiAssicurazioneVeicoloImpl(AssicurazioneVeicoloApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public VerificaAssicurazioneVeicoli
      verificaCoperturaAssicurativaDatiAnagraficiTargaCodiceTipoVeicoloDataRiferimentoGet(
          String arg0, TipoVeicolo arg1, String arg2) {
    return instance
        .verificaCoperturaAssicurativaDatiAnagraficiTargaCodiceTipoVeicoloDataRiferimentoGet(
            arg0, arg1, arg2);
  }
}
