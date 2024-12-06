package it.liguriadigitale.ponmetro.portale.business.rest.impl.biblioteche;

import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.TabelleBaseApi;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaPaeseRecord;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaRecord;
import java.util.List;

public class ApiTabelleBaseImpl implements TabelleBaseApi {

  private TabelleBaseApi instance;

  public ApiTabelleBaseImpl(TabelleBaseApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<TabellaRecord> listaTabellaBiblio(String arg0) {
    return instance.listaTabellaBiblio(arg0);
  }

  @Override
  public List<TabellaRecord> listaTabellaLingua(String arg0) {
    return instance.listaTabellaLingua(arg0);
  }

  @Override
  public List<TabellaPaeseRecord> listaTabellaPaese(String arg0) {
    return instance.listaTabellaPaese(arg0);
  }
}
