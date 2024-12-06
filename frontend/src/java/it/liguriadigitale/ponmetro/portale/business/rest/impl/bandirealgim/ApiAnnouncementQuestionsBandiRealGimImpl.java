package it.liguriadigitale.ponmetro.portale.business.rest.impl.bandirealgim;

import it.liguriadigitale.ponmetro.bandirealgim.apiclient.AnnouncementQuestionsApi;
import it.liguriadigitale.ponmetro.bandirealgim.model.BandoDomandaCategoriaFullDOM;
import it.liguriadigitale.ponmetro.bandirealgim.model.V1BandoDomandeStatusDOM;
import it.liguriadigitale.ponmetro.bandirealgim.model.V1BandoDomandeViewFullDOM;
import java.util.List;

public class ApiAnnouncementQuestionsBandiRealGimImpl implements AnnouncementQuestionsApi {

  private AnnouncementQuestionsApi instance;

  public ApiAnnouncementQuestionsBandiRealGimImpl(AnnouncementQuestionsApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public V1BandoDomandeViewFullDOM apiBandiAnnouncementQuestionsGetAnnouncementQuestionDetailGet(
      Boolean var1, String var2, String var3) {
    return instance.apiBandiAnnouncementQuestionsGetAnnouncementQuestionDetailGet(var1, var2, var3);
  }

  @Override
  public List<BandoDomandaCategoriaFullDOM>
      apiBandiAnnouncementQuestionsGetListAnnouncementQuestionCategoryAnnoGet(Integer var1) {
    return instance.apiBandiAnnouncementQuestionsGetListAnnouncementQuestionCategoryAnnoGet(var1);
  }

  @Override
  public byte[] apiBandiAnnouncementQuestionsDownloadDocumentAnnouncementQuestionGet(
      String arg0, String arg1) {
    return instance.apiBandiAnnouncementQuestionsDownloadDocumentAnnouncementQuestionGet(
        arg0, arg1);
  }

  @Override
  public V1BandoDomandeStatusDOM apiBandiAnnouncementQuestionsGetAnnouncementQuestionStatusGet(
      Boolean arg0, String arg1, String arg2, String arg3) {
    return instance.apiBandiAnnouncementQuestionsGetAnnouncementQuestionStatusGet(
        arg0, arg1, arg2, arg3);
  }

  @Override
  public List<V1BandoDomandeViewFullDOM>
      apiBandiAnnouncementQuestionsGetListAnnouncementQuestionsDetailGet(
          Boolean arg0, String arg1, String arg2) {
    return instance.apiBandiAnnouncementQuestionsGetListAnnouncementQuestionsDetailGet(
        arg0, arg1, arg2);
  }
}
