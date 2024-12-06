package it.liguriadigitale.ponmetro.portale.business.rest.impl.accenture;

import it.liguriadigitale.ponmetro.richiesta.assistenza.apiclient.RichiestaAssistenzaApi;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV560UiApiObjectInfoWorkTypeGroupPicklistValues012000000000000AAACategoriadiServizioCGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsAccountIdCasesGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCaseIdCommentiRGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCaseIdEmailsGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCasePost201Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCasePostRequest;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsEmailMessageIDGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsServiceTerritoryTerritoryIDGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsWorkTypeGroupIdGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsWorkTypeGroupIdUOResponsabiliRGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsWorkTypeGroupListviewsGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsWorkTypeGroupListviewsListviewIdResultsGet200Response;

public class ApiRichiestaAssistenzaImpl implements RichiestaAssistenzaApi {

  public RichiestaAssistenzaApi instance;

  public ApiRichiestaAssistenzaImpl(RichiestaAssistenzaApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public
  ServicesDataV560UiApiObjectInfoWorkTypeGroupPicklistValues012000000000000AAACategoriadiServizioCGet200Response
      servicesDataV560UiApiObjectInfoWorkTypeGroupPicklistValues012000000000000AAACategoriadiServizioCGet() {
    return instance
        .servicesDataV560UiApiObjectInfoWorkTypeGroupPicklistValues012000000000000AAACategoriadiServizioCGet();
  }

  @Override
  public ServicesDataV590SobjectsAccountCodiceFiscaleCCodiceFiscaleGet200Response
      servicesDataV590SobjectsAccountCodiceFiscaleCCodiceFiscaleGet(String arg0) {
    return instance.servicesDataV590SobjectsAccountCodiceFiscaleCCodiceFiscaleGet(arg0);
  }

  @Override
  public ServicesDataV590SobjectsAccountIdCasesGet200Response
      servicesDataV590SobjectsAccountIdCasesGet(String arg0) {
    return instance.servicesDataV590SobjectsAccountIdCasesGet(arg0);
  }

  @Override
  public ServicesDataV590SobjectsCaseIdCommentiRGet200Response
      servicesDataV590SobjectsCaseIdCommentiRGet(String arg0) {
    return instance.servicesDataV590SobjectsCaseIdCommentiRGet(arg0);
  }

  @Override
  public ServicesDataV590SobjectsCaseIdEmailsGet200Response servicesDataV590SobjectsCaseIdEmailsGet(
      String arg0) {
    return instance.servicesDataV590SobjectsCaseIdEmailsGet(arg0);
  }

  @Override
  public ServicesDataV590SobjectsCasePost201Response servicesDataV590SobjectsCasePost(
      ServicesDataV590SobjectsCasePostRequest arg0) {
    return instance.servicesDataV590SobjectsCasePost(arg0);
  }

  @Override
  public ServicesDataV590SobjectsServiceTerritoryTerritoryIDGet200Response
      servicesDataV590SobjectsServiceTerritoryTerritoryIDGet(String arg0) {
    return instance.servicesDataV590SobjectsServiceTerritoryTerritoryIDGet(arg0);
  }

  @Override
  public ServicesDataV590SobjectsWorkTypeGroupIdGet200Response
      servicesDataV590SobjectsWorkTypeGroupIdGet(String arg0) {
    return instance.servicesDataV590SobjectsWorkTypeGroupIdGet(arg0);
  }

  @Override
  public ServicesDataV590SobjectsWorkTypeGroupIdUOResponsabiliRGet200Response
      servicesDataV590SobjectsWorkTypeGroupIdUOResponsabiliRGet(String arg0) {
    return instance.servicesDataV590SobjectsWorkTypeGroupIdUOResponsabiliRGet(arg0);
  }

  @Override
  public ServicesDataV590SobjectsWorkTypeGroupListviewsGet200Response
      servicesDataV590SobjectsWorkTypeGroupListviewsGet() {
    return instance.servicesDataV590SobjectsWorkTypeGroupListviewsGet();
  }

  @Override
  public ServicesDataV590SobjectsWorkTypeGroupListviewsListviewIdResultsGet200Response
      servicesDataV590SobjectsWorkTypeGroupListviewsListviewIdResultsGet(
          String arg0, String arg1, Integer arg2) {
    return instance.servicesDataV590SobjectsWorkTypeGroupListviewsListviewIdResultsGet(
        arg0, arg1, arg2);
  }

  @Override
  public ServicesDataV590SobjectsEmailMessageIDGet200Response
      servicesDataV590SobjectsEmailMessageIDGet(String arg0) {
    return instance.servicesDataV590SobjectsEmailMessageIDGet(arg0);
  }
}
