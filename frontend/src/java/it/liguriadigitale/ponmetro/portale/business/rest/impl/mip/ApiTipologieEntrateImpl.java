package it.liguriadigitale.ponmetro.portale.business.rest.impl.mip;

import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.TipologiaEntrataApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.TipologiaEntrata;
import java.util.List;

public class ApiTipologieEntrateImpl implements TipologiaEntrataApi {

  private TipologiaEntrataApi instance;

  public ApiTipologieEntrateImpl(TipologiaEntrataApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public TipologiaEntrata dettaglioTipologiaEntrata(String arg0) {
    return instance.dettaglioTipologiaEntrata(arg0);
  }

  @Override
  public List<TipologiaEntrata> listaTipologieEntrate(Integer arg0, Integer arg1) {
    return instance.listaTipologieEntrate(arg0, arg1);
  }
}
