package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.ubicazioneparcheggio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.presentation.components.select2.Response;
import it.liguriadigitale.ponmetro.portale.presentation.components.select2.TextChoiceProvider;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import java.util.Collection;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class FeaturesGeoserverProvider extends TextChoiceProvider<FeaturesGeoserver> {

  private static final long serialVersionUID = 1602432260568929265L;

  @Override
  protected String getDisplayText(FeaturesGeoserver choice) {
    return choice.getMACHINE_LAST_UPD();
  }

  @Override
  protected Object getId(FeaturesGeoserver choice) {
    return choice.getMACHINE_LAST_UPD();
  }

  @Override
  public void query(String term, int page, Response<FeaturesGeoserver> response) {
    response.addAll(getFeaturesGeoserver(term));
    response.setHasMore(response.size() == 10);
  }

  @Override
  public Collection<FeaturesGeoserver> toChoices(Collection<String> ids) {
    return getFeaturesGeoserver(ids.iterator().next());
  }

  private List<FeaturesGeoserver> getFeaturesGeoserver(String input) {
    try {
      List<FeaturesGeoserver> response =
          ServiceLocator.getInstance().getServiziGeoserver().getGeoserver(input);
      return response;
    } catch (BusinessException | ApiException e) {
      // log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
