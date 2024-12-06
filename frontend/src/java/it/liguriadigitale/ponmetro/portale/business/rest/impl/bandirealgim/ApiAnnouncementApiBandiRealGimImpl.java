package it.liguriadigitale.ponmetro.portale.business.rest.impl.bandirealgim;

import it.liguriadigitale.ponmetro.bandirealgim.apiclient.AnnouncementApi;
import it.liguriadigitale.ponmetro.bandirealgim.model.BandoAttivoFullDOMValidationResult;
import it.liguriadigitale.ponmetro.bandirealgim.model.BooleanValidationResult;
import java.time.OffsetDateTime;

public class ApiAnnouncementApiBandiRealGimImpl implements AnnouncementApi {

  private AnnouncementApi instance;

  public ApiAnnouncementApiBandiRealGimImpl(AnnouncementApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public BooleanValidationResult apiBandiAnnouncementCheckAnnouncementStatusGet(String arg0) {
    return instance.apiBandiAnnouncementCheckAnnouncementStatusGet(arg0);
  }

  @Override
  public BandoAttivoFullDOMValidationResult apiBandiAnnouncementGetActiveAnnouncementGet(
      OffsetDateTime arg0, String arg1) {
    return instance.apiBandiAnnouncementGetActiveAnnouncementGet(arg0, arg1);
  }
}
