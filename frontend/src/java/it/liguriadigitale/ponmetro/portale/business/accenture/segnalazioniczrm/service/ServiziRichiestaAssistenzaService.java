package it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.richiestaassistenza.SottoCategoria;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form.Help;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCasePost201Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsWorkTypeGroupListviewsGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsWorkTypeGroupListviewsListviewIdResultsGet200Response;
import java.io.IOException;
import java.util.List;

public interface ServiziRichiestaAssistenzaService {

  ServicesDataV590SobjectsWorkTypeGroupListviewsGet200Response getListViews()
      throws BusinessException, IOException, ApiException;

  ServicesDataV590SobjectsWorkTypeGroupListviewsListviewIdResultsGet200Response getListViewsConId(
      String listViewId) throws BusinessException, IOException, ApiException;

  List<SottoCategoria> getListaConSottoFascicolo(String sottoFascicolo);

  List<SottoCategoria> getSottoCategoria();

  ServicesDataV590SobjectsCasePost201Response postAssistenza(Help segnalazioneHelp)
      throws BusinessException, IOException, ApiException;
}
