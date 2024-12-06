package it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari;

import it.liguriadigitale.ponmetro.tarinetribe.apiclient.SintesiApi;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIResult;
import it.liguriadigitale.ponmetro.tarinetribe.model.TipologiaBollettino;
import java.util.List;

public class TariSintesiImpl implements SintesiApi {

  private SintesiApi instance;

  public TariSintesiImpl(SintesiApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<TARIResult> getSintesiTARIAnnoCorrente(
      String cf, Integer anno, TipologiaBollettino tipo) {
    return instance.getSintesiTARIAnnoCorrente(cf, anno, tipo);
  }

  @Override
  public TARIResult getTARI(String var1) {
    return instance.getTARI(var1);
  }
}
