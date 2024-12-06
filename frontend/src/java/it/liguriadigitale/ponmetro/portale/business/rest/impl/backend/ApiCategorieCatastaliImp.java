package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.api.pojo.catasto.S3Ct46tCodCategorie;
import it.liguriadigitale.ponmetro.api.presentation.rest.catasto.service.CategorieCatastaliApi;
import java.util.List;

public class ApiCategorieCatastaliImp implements CategorieCatastaliApi {

  private CategorieCatastaliApi instance;

  public ApiCategorieCatastaliImp(CategorieCatastaliApi api) {
    instance = api;
  }

  @Override
  public List<S3Ct46tCodCategorie> getCategorieCatastali() {
    // TODO Auto-generated method stub
    return instance.getCategorieCatastali();
  }
}
