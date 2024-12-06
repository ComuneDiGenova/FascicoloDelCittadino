package it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari;

import it.liguriadigitale.ponmetro.tarinetribe.apiclient.RimborsoApi;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIRimborsi;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIRimborsoResult;
import java.util.List;

public class TariRimborsoImpl implements RimborsoApi {

  private RimborsoApi instance;

  public TariRimborsoImpl(RimborsoApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<TARIRimborsi> getRimborsiTARIAnnoCorrente(String arg0) {
    return instance.getRimborsiTARIAnnoCorrente(arg0);
  }

  @Override
  public List<TARIRimborsoResult> createRichiestaRimborso(TARIRimborsi arg0) {
    return instance.createRichiestaRimborso(arg0);
  }
}
