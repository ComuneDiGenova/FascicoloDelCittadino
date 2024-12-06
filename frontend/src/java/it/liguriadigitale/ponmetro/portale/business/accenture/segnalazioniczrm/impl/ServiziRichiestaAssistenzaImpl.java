package it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.service.ServiziRichiestaAssistenzaService;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoAccenture;
import it.liguriadigitale.ponmetro.portale.pojo.richiestaassistenza.SottoCategoria;
import it.liguriadigitale.ponmetro.portale.pojo.richiestaassistenza.mapper.AssistenzaMapper;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form.Help;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCasePost201Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCasePostRequest;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCasePostRequestRecordType;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsWorkTypeGroupListviewsGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsWorkTypeGroupListviewsGet200ResponseListviewsInner;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsWorkTypeGroupListviewsListviewIdResultsGet200Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.modelmapper.ModelMapper;

public class ServiziRichiestaAssistenzaImpl implements ServiziRichiestaAssistenzaService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_RICHIESTA_ASSISTENZA =
      "Errore di connessione alle API Richiesta Assistenza";

  @Override
  public ServicesDataV590SobjectsWorkTypeGroupListviewsGet200Response getListViews()
      throws BusinessException, IOException, ApiException {
    log.debug("ServiziRichiestaAssistenzaImpl getListViews");

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV590SobjectsWorkTypeGroupListviewsGet200Response response = null;

    try {
      response =
          instance.getApiRichiestaAssistenza().servicesDataV590SobjectsWorkTypeGroupListviewsGet();

      return response;
    } catch (BusinessException e) {
      log.error("ServiziRichiestaAssistenzaImpl -- getListViews: errore API ACENTURE:", e);
      throw new BusinessException(ERRORE_API_RICHIESTA_ASSISTENZA);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziRichiestaAssistenzaImpl -- getListViews: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRichiestaAssistenzaImpl -- getListViews: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Segnalazioni al Fascicolo del Cittadino"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ServicesDataV590SobjectsWorkTypeGroupListviewsListviewIdResultsGet200Response
      getListViewsConId(String listViewId) throws BusinessException, IOException, ApiException {
    log.debug("ServiziRichiestaAssistenzaImpl getListViewsConId");

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV590SobjectsWorkTypeGroupListviewsListviewIdResultsGet200Response response = null;

    try {

      String limit = "2000";

      Integer offset = null;

      response =
          instance
              .getApiRichiestaAssistenza()
              .servicesDataV590SobjectsWorkTypeGroupListviewsListviewIdResultsGet(
                  listViewId, limit, offset);

      return response;
    } catch (BusinessException e) {
      log.error("ServiziRichiestaAssistenzaImpl -- getListViewsConId: errore API ACENTURE:", e);
      throw new BusinessException(ERRORE_API_RICHIESTA_ASSISTENZA);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziRichiestaAssistenzaImpl -- getListViewsConId: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRichiestaAssistenzaImpl -- getListViewsConId: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Segnalazioni al Fascicolo del Cittadino"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<SottoCategoria> getListaConSottoFascicolo(String sottoFascicolo) {
    log.debug("ServiziRichiestaAssistenzaImpl getListaConSottoFascicolo = " + sottoFascicolo);

    List<SottoCategoria> listaCategoria = getSottoCategoria();

    log.debug("CP listaCategoria = " + listaCategoria);

    return listaCategoria.stream()
        .filter(x -> Objects.nonNull(x.getSottoFascicolo()))
        .filter(x -> x.getSottoFascicolo().equalsIgnoreCase(sottoFascicolo))
        .collect(Collectors.toList());
  }

  @Override
  public List<SottoCategoria> getSottoCategoria() {
    log.debug("ServiziRichiestaAssistenzaImpl getSottoCategoria");

    List<SottoCategoria> listaSottoCategoria = new ArrayList<>();

    try {

      ServicesDataV590SobjectsWorkTypeGroupListviewsGet200Response responseGetListViews =
          getListViews();

      log.debug("CP responseGetListViews = " + responseGetListViews);

      String labelPerFdC = "Servizi del Fascicolo del Cittadino";

      ServicesDataV590SobjectsWorkTypeGroupListviewsGet200ResponseListviewsInner serviziFdC =
          responseGetListViews.getListviews().stream()
              .filter(x -> x.getLabel().equalsIgnoreCase(labelPerFdC))
              .findFirst()
              .orElseThrow(null);

      ServicesDataV590SobjectsWorkTypeGroupListviewsListviewIdResultsGet200Response
          responseSubListViewsConId = getListViewsConId(serviziFdC.getId());

      log.debug("CP responseSubListViewsConId = " + responseSubListViewsConId);

      responseSubListViewsConId.getRecords().stream()
          .forEach(
              row -> {
                SottoCategoria sottoCategoria = new SottoCategoria();
                row.getColumns()
                    .forEach(
                        col -> {
                          switch (col.getFieldNameOrPath()) {
                            case "Name":
                              sottoCategoria.setServizio(col.getValue());
                              break;

                            case "Sottofascicolo__c":
                              sottoCategoria.setSottoFascicolo(col.getValue());
                              break;

                            case "Id":
                              sottoCategoria.setId(col.getValue());
                              break;

                            default:
                              break;
                          }
                        });
                listaSottoCategoria.add(sottoCategoria);
              });

    } catch (BusinessException | IOException | ApiException e) {
      log.error("Errore ServiziRichiestaAssistenzaImpl getSottoCategoria = " + e.getMessage());
    }

    return listaSottoCategoria;
  }

  @Override
  public ServicesDataV590SobjectsCasePost201Response postAssistenza(Help assistenza)
      throws BusinessException, IOException, ApiException {
    log.debug("ServiziRichiestaAssistenzaImpl postAssistenza = " + assistenza);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    ServicesDataV590SobjectsCasePost201Response responsePost = null;

    try {

      ModelMapper modelMapper = new ModelMapper();
      modelMapper.addMappings(new AssistenzaMapper());

      ServicesDataV590SobjectsCasePostRequest bodyRichiesta =
          modelMapper.map(assistenza, ServicesDataV590SobjectsCasePostRequest.class);

      bodyRichiesta.setOrigin("HelpFascicoloCittadino");

      ServicesDataV590SobjectsCasePostRequestRecordType recordType =
          new ServicesDataV590SobjectsCasePostRequestRecordType();
      recordType.setName("RichiestaAssistenza");
      bodyRichiesta.setRecordType(recordType);

      log.debug("CP bodyRichiesta = " + bodyRichiesta);

      responsePost =
          instance.getApiRichiestaAssistenza().servicesDataV590SobjectsCasePost(bodyRichiesta);

    } catch (BusinessException e) {
      log.error("ServiziRichiestaAssistenzaImpl -- postAssistenza: errore API ACENTURE:", e);
      throw new BusinessException(ERRORE_API_RICHIESTA_ASSISTENZA);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziRichiestaAssistenzaImpl -- postAssistenza: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRichiestaAssistenzaImpl -- postAssistenza: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Segnalazioni al Fascicolo del Cittadino"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }

    return responsePost;
  }
}
