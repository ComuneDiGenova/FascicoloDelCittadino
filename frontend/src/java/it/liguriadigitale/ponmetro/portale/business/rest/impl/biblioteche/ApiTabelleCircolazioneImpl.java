package it.liguriadigitale.ponmetro.portale.business.rest.impl.biblioteche;

import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.TabelleCircolazioneApi;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaRecord;
import java.util.List;
import org.apache.commons.lang.NotImplementedException;

public class ApiTabelleCircolazioneImpl implements TabelleCircolazioneApi {

  private TabelleCircolazioneApi instance;

  public ApiTabelleCircolazioneImpl(TabelleCircolazioneApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<TabellaRecord> listaTabellaAcau(String arg0) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public List<TabellaRecord> listaTabellaStam(String arg0) {
    return instance.listaTabellaStam(arg0);
  }

  @Override
  public List<TabellaRecord> listaTabellaTkit(String arg0) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public List<TabellaRecord> listaTabellaTmov(String arg0) {
    return instance.listaTabellaTmov(arg0);
  }
}
