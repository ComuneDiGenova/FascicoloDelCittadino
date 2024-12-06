package it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.condoni.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.edilizia.condono.model.CondonoResponse;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class CondoniEdiliziPanel extends BasePanel {

  private static final long serialVersionUID = 3121846087091414298L;

  private static final String ICON_ARPAL = "color-fc-secondary col-3 icon-rimborsi-imu";

  public CondoniEdiliziPanel(String id) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {

    List<CondonoResponse> listaCondoni = new ArrayList<>();

    try {

      listaCondoni =
          ServiceLocator.getInstance()
              .getServiziEdiliziaCondono()
              .getCondoni(getUtente().getCodiceFiscaleOperatore());

    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("condoni"));
    }

    ElencoCondoniPanel elencoCondoniPanel =
        new ElencoCondoniPanel("elencoCondoniPanel", listaCondoni);
    addOrReplace(elencoCondoniPanel);
  }
}
