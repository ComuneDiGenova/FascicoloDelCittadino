package it.liguriadigitale.ponmetro.portale.business.rest.impl.oggettismarriti;

import it.liguriadigitale.ponmetro.oggettismarriti.apiclient.OggettiSmarritiApi;
import it.liguriadigitale.ponmetro.oggettismarriti.model.OggettiSmarriti;
import it.liguriadigitale.ponmetro.oggettismarriti.model.PayloadOggettiSmarriti;
import java.util.List;

public class ApiOggettiSmarritiImpl implements OggettiSmarritiApi {

  private OggettiSmarritiApi instance;

  public ApiOggettiSmarritiImpl(OggettiSmarritiApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<OggettiSmarriti> getOggettiSmarriti(PayloadOggettiSmarriti arg0) {
    return instance.getOggettiSmarriti(arg0);
  }
}
