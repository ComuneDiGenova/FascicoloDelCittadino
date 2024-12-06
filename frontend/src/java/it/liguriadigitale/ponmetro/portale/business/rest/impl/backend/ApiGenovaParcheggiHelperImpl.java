package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.genovaparcheggihelper.apiclient.GenovaParcheggiHelperApi;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravPermessi;
import java.util.List;

public class ApiGenovaParcheggiHelperImpl implements GenovaParcheggiHelperApi {

  private GenovaParcheggiHelperApi instance;

  public ApiGenovaParcheggiHelperImpl(GenovaParcheggiHelperApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public BravFunzioni getBravFunzione(String arg0) {
    return instance.getBravFunzione(arg0);
  }

  @Override
  public List<BravFunzioni> getBravFunzioni() {
    return instance.getBravFunzioni();
  }

  @Override
  public List<BravPermessi> getBravPermessi(String arg0) {
    return instance.getBravPermessi(arg0);
  }
}
