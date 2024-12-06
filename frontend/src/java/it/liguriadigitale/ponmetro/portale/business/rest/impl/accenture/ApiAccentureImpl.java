package it.liguriadigitale.ponmetro.portale.business.rest.impl.accenture;

import it.liguriadigitale.ponmetro.accenture.apiclient.DefaultApi;
import it.liguriadigitale.ponmetro.accenture.model.ServicesApexrestRichiestaMatrimoniWebServiceCreateRichiestaPost200Response;
import it.liguriadigitale.ponmetro.accenture.model.ServicesApexrestRichiestaMatrimoniWebServiceCreateRichiestaPostRequest;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV560SobjectsContentVersionPost200Response;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV560SobjectsContentVersionPostRequest;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountIdCasesGet200Response;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet200Response;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsCaseIdCommentiRGet200Response;
import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsCaseIdEmailsGet200Response;

public class ApiAccentureImpl implements DefaultApi {

  private DefaultApi instance;

  public ApiAccentureImpl(DefaultApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public ServicesApexrestRichiestaMatrimoniWebServiceCreateRichiestaPost200Response
      servicesApexrestRichiestaMatrimoniWebServiceCreateRichiestaPost(
          ServicesApexrestRichiestaMatrimoniWebServiceCreateRichiestaPostRequest arg0) {
    return instance.servicesApexrestRichiestaMatrimoniWebServiceCreateRichiestaPost(arg0);
  }

  @Override
  public ServicesDataV560SobjectsContentVersionPost200Response
      servicesDataV560SobjectsContentVersionPost(
          ServicesDataV560SobjectsContentVersionPostRequest arg0) {
    return instance.servicesDataV560SobjectsContentVersionPost(arg0);
  }

  @Override
  public ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      servicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet(String arg0) {
    return instance.servicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet(arg0);
  }

  @Override
  public ServicesDataV580SobjectsAccountIdCasesGet200Response
      servicesDataV580SobjectsAccountIdCasesGet(String arg0) {
    return instance.servicesDataV580SobjectsAccountIdCasesGet(arg0);
  }

  @Override
  public ServicesDataV580SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      servicesDataV580SobjectsAccountIdGet(String arg0) {
    return instance.servicesDataV580SobjectsAccountIdGet(arg0);
  }

  @Override
  public ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response
      servicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet(String arg0) {
    return instance.servicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet(arg0);
  }

  @Override
  public ServicesDataV580SobjectsCaseIdEmailsGet200Response servicesDataV580SobjectsCaseIdEmailsGet(
      String arg0) {
    return instance.servicesDataV580SobjectsCaseIdEmailsGet(arg0);
  }

  @Override
  public ServicesDataV580SobjectsCaseIdCommentiRGet200Response
      servicesDataV580SobjectsCaseIdCommentiRGet(String arg0) {
    return instance.servicesDataV580SobjectsCaseIdCommentiRGet(arg0);
  }

  @Override
  public ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet200Response
      servicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet(String arg0) {
    return instance.servicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet(arg0);
  }

  //	@Override
  //	public ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200Response
  // servicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet(
  //			String arg0) {
  //		return
  // instance.servicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet(arg0);
  //	}

}
