package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.permessipersonalizzati;

import it.liguriadigitale.ponmetro.permessipersonalizzati.apiclient.DomandaApi;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.AllegatoBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaCompletaResponse;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaResponse;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.IdDomandaProtocolloBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.PostResult;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Protocollazione;
import java.util.List;

public class ApiDomandaPermessiPersonalizzatiImpl implements DomandaApi {

  private DomandaApi instance;

  public ApiDomandaPermessiPersonalizzatiImpl(DomandaApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Protocollazione domandaIdDomandaProtocolloPost(
      Integer arg0, IdDomandaProtocolloBody arg1) {
    return instance.domandaIdDomandaProtocolloPost(arg0, arg1);
  }

  @Override
  public List<DomandaResponse> domandeSoggettoGet(String arg0) {
    return instance.domandeSoggettoGet(arg0);
  }

  @Override
  public AllegatoBody domandaIdDomandaAllegatoNomeFileGet(Integer arg0, String arg1) {
    return instance.domandaIdDomandaAllegatoNomeFileGet(arg0, arg1);
  }

  @Override
  public DomandaCompletaResponse domandaIdDomandaGet(Integer arg0) {
    return instance.domandaIdDomandaGet(arg0);
  }

  @Override
  public PostResult domandaResidenzaPost(Boolean var1, DomandaBody var2) {
    return instance.domandaResidenzaPost(var1, var2);
  }
}
