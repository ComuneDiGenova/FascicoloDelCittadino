package it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari;

import it.liguriadigitale.ponmetro.tarinetribe.apiclient.BollettinoApi;
import it.liguriadigitale.ponmetro.tarinetribe.model.RichiestaMail;
import it.liguriadigitale.ponmetro.tarinetribe.model.RichiestaMailResult;

public class TariBollettinoImpl implements BollettinoApi {

  private BollettinoApi instance;

  public TariBollettinoImpl(BollettinoApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public RichiestaMailResult createRichiestaMail(RichiestaMail arg0) {
    return instance.createRichiestaMail(arg0);
  }
}
