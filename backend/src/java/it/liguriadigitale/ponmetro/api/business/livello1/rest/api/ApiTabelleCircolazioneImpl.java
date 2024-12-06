package it.liguriadigitale.ponmetro.api.business.livello1.rest.api;

import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.TabelleCircolazioneApi;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaRecord;
import java.util.List;

public class ApiTabelleCircolazioneImpl implements TabelleCircolazioneApi {

  private TabelleCircolazioneApi instance;

  public ApiTabelleCircolazioneImpl(TabelleCircolazioneApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<TabellaRecord> listaTabellaAcau(String arg0) {
    return instance.listaTabellaAcau(arg0);
  }

  @Override
  public List<TabellaRecord> listaTabellaStam(String arg0) {
    return instance.listaTabellaStam(arg0);
  }

  @Override
  public List<TabellaRecord> listaTabellaTkit(String arg0) {
    return instance.listaTabellaTkit(arg0);
  }

  @Override
  public List<TabellaRecord> listaTabellaTmov(String arg0) {
    return instance.listaTabellaTmov(arg0);
  }
}
