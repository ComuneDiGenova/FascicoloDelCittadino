package it.liguriadigitale.ponmetro.portale.presentation.components.comboselect2;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ComboGeoserver extends FdCDropDownChoice<FeaturesGeoserver> {

  private static final long serialVersionUID = 8717881868378434676L;

  private Log log = LogFactory.getLog(getClass());

  IModel<FeaturesGeoserver> model;

  public ComboGeoserver(String id, IModel<FeaturesGeoserver> model) {
    super(id);

    this.model = model;
    this.inizializza(null);
    this.setOutputMarkupId(true);
    this.setOutputMarkupPlaceholderTag(true);
    this.setChoiceRenderer(new GeoserverRender());
    this.setModel(model);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of(getString("ComboGeoserver.indirizzo")));
  }

  public void inizializza(String input) {

    log.debug("CP getFeaturesGeoserver inizializza = " + input);

    if (PageUtil.isStringValid(input)) {
      try {
        List<FeaturesGeoserver> lista = new ArrayList<FeaturesGeoserver>();

        List<FeaturesGeoserver> response =
            ServiceLocator.getInstance().getServiziGeoserver().getGeoserver(input);

        if (LabelFdCUtil.checkIfNotNull(response) && LabelFdCUtil.checkIfNotNull(response)) {
          lista = response;
        }

        this.setChoices(lista);

      } catch (BusinessException | ApiException e) {
        log.debug("Errore durante la chiamata delle API", e);
        throw new RestartResponseAtInterceptPageException(
            new ErroreServiziPage("Allerte zona rossa"));
      }
    }
  }
}
