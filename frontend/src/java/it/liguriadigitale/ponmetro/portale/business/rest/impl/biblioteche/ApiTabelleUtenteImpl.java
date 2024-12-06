package it.liguriadigitale.ponmetro.portale.business.rest.impl.biblioteche;

import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.TabelleUtenteApi;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaRecord;
import java.util.List;

public class ApiTabelleUtenteImpl implements TabelleUtenteApi {

  private TabelleUtenteApi instance;

  public ApiTabelleUtenteImpl(TabelleUtenteApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<TabellaRecord> listaTabellaComuAvvisi(String arg0) {
    return instance.listaTabellaComuAvvisi(arg0);
  }

  @Override
  public List<TabellaRecord> listaTabellaComuMessaggi(String arg0) {
    return instance.listaTabellaComuMessaggi(arg0);
  }

  @Override
  public List<TabellaRecord> listaTabellaProv() {
    return instance.listaTabellaProv();
  }

  @Override
  public List<TabellaRecord> listaTabellaTDocIdentita(String arg0) {
    return instance.listaTabellaTDocIdentita(arg0);
  }

  @Override
  public List<TabellaRecord> listaTabellaTpv(String arg0) {
    return instance.listaTabellaTpv(arg0);
  }

  @Override
  public List<TabellaRecord> listaTabellaTts(String arg0) {
    return instance.listaTabellaTts(arg0);
  }

  @Override
  public List<TabellaRecord> listaTabellaTut(String arg0) {
    return instance.listaTabellaTut(arg0);
  }
}
