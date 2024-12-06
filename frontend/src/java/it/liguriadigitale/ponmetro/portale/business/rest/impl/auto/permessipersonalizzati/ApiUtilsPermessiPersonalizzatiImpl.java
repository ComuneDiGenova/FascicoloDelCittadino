package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.permessipersonalizzati;

import it.liguriadigitale.ponmetro.permessipersonalizzati.apiclient.UtilsApi;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Allegabile;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DichiarazioniResponse;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.StatoProcedimento;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.TipologiaProcedimento;
import java.util.List;

public class ApiUtilsPermessiPersonalizzatiImpl implements UtilsApi {

  private UtilsApi instance;

  public ApiUtilsPermessiPersonalizzatiImpl(UtilsApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<Allegabile> allegabiliCodiceGet(String arg0) {
    return instance.allegabiliCodiceGet(arg0);
  }

  @Override
  public List<Allegabile> allegabiliGet() {
    return instance.allegabiliGet();
  }

  @Override
  public List<DichiarazioniResponse> dichiarazioniGet() {
    return instance.dichiarazioniGet();
  }

  @Override
  public List<TipologiaProcedimento> tipologieProcedimentoGet() {
    return instance.tipologieProcedimentoGet();
  }

  @Override
  public List<Allegabile> allegabiliCodiceResidenzaGet(String arg0, Boolean arg1) {
    return instance.allegabiliCodiceResidenzaGet(arg0, arg1);
  }

  @Override
  public List<StatoProcedimento> statiProcedimentoGet() {
    return instance.statiProcedimentoGet();
  }
}
