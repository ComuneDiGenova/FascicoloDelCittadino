package it.liguriadigitale.ponmetro.portale.business.rest.impl.segnalazionedannibeniprivati;

import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.apiclient.DefaultApi;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.DatiDomanda;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.DatiRichiesta;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesApexrestRichiestaMatrimoniWebServiceCreateRichiestaPost200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesApexrestRichiestaMatrimoniWebServiceCreateRichiestaPostRequest;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV560SobjectsContentVersionPost200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV560SobjectsContentVersionPostRequest;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsAccountIdCasesGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsAccountIdProcedimentiRGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsCaseIdCommentiRGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsCaseIdEmailsGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsProcedimentoCIdGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsPubblicazioneMatrimonioCIdGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV590SobjectsTornataElettoraleCListviewsGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV590SobjectsTornataElettoraleCListviewsListviewIdResultsGet200Response;

public class ApiSegnalazioneDanniBeniPrivatiImpl implements DefaultApi {

  private DefaultApi instance;

  public ApiSegnalazioneDanniBeniPrivatiImpl(DefaultApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public DatiRichiesta servicesApexrestProcedimentiWebServiceCreateProcedimentoPost(
      DatiDomanda var1) {
    return instance.servicesApexrestProcedimentiWebServiceCreateProcedimentoPost(var1);
  }

  @Override
  public ServicesApexrestRichiestaMatrimoniWebServiceCreateRichiestaPost200Response
      servicesApexrestRichiestaMatrimoniWebServiceCreateRichiestaPost(
          ServicesApexrestRichiestaMatrimoniWebServiceCreateRichiestaPostRequest var1) {
    return instance.servicesApexrestRichiestaMatrimoniWebServiceCreateRichiestaPost(var1);
  }

  @Override
  public ServicesDataV560SobjectsContentVersionPost200Response
      servicesDataV560SobjectsContentVersionPost(
          ServicesDataV560SobjectsContentVersionPostRequest var1) {
    return instance.servicesDataV560SobjectsContentVersionPost(var1);
  }

  @Override
  public ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      servicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet(String var1) {
    return instance.servicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet(var1);
  }

  @Override
  public ServicesDataV580SobjectsAccountIdCasesGet200Response
      servicesDataV580SobjectsAccountIdCasesGet(String var1) {
    return instance.servicesDataV580SobjectsAccountIdCasesGet(var1);
  }

  @Override
  public ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      servicesDataV580SobjectsAccountIdGet(String var1) {
    return instance.servicesDataV580SobjectsAccountIdGet(var1);
  }

  @Override
  public ServicesDataV580SobjectsAccountIdProcedimentiRGet200Response
      servicesDataV580SobjectsAccountIdProcedimentiRGet(String var1) {
    return instance.servicesDataV580SobjectsAccountIdProcedimentiRGet(var1);
  }

  @Override
  public ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response
      servicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet(String var1) {
    return instance.servicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet(var1);
  }

  @Override
  public ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response
      servicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet(String var1) {
    return instance.servicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet(var1);
  }

  @Override
  public ServicesDataV580SobjectsCaseIdCommentiRGet200Response
      servicesDataV580SobjectsCaseIdCommentiRGet(String var1) {
    return instance.servicesDataV580SobjectsCaseIdCommentiRGet(var1);
  }

  @Override
  public ServicesDataV580SobjectsCaseIdEmailsGet200Response servicesDataV580SobjectsCaseIdEmailsGet(
      String var1) {
    return instance.servicesDataV580SobjectsCaseIdEmailsGet(var1);
  }

  @Override
  public ServicesDataV580SobjectsProcedimentoCIdGet200Response
      servicesDataV580SobjectsProcedimentoCIdGet(String var1) {
    return instance.servicesDataV580SobjectsProcedimentoCIdGet(var1);
  }

  @Override
  public ServicesDataV580SobjectsPubblicazioneMatrimonioCIdGet200Response
      servicesDataV580SobjectsPubblicazioneMatrimonioCIdGet(String var1) {
    return instance.servicesDataV580SobjectsPubblicazioneMatrimonioCIdGet(var1);
  }

  @Override
  public ServicesDataV590SobjectsTornataElettoraleCListviewsGet200Response
      servicesDataV590SobjectsTornataElettoraleCListviewsGet() {
    return instance.servicesDataV590SobjectsTornataElettoraleCListviewsGet();
  }

  @Override
  public ServicesDataV590SobjectsTornataElettoraleCListviewsListviewIdResultsGet200Response
      servicesDataV590SobjectsTornataElettoraleCListviewsListviewIdResultsGet(
          String var1, String var2, Integer var3) {
    return instance.servicesDataV590SobjectsTornataElettoraleCListviewsListviewIdResultsGet(
        var1, var2, var3);
  }
}
