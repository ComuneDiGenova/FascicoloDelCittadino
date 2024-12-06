package it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.pratiche.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.PraticaSingola;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.Pratiche;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class PraticheEdiliziePanel extends BasePanel {

  private static final long serialVersionUID = -3785431132461148859L;

  private static final String ICON_ARPAL = "color-fc-secondary col-3 icon-rimborsi-imu";

  public PraticheEdiliziePanel(String id) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {

    Pratiche pratiche = new Pratiche();

    try {

      pratiche =
          ServiceLocator.getInstance()
              .getServiziEdiliziaPratiche()
              .getPratiche(getUtente().getCodiceFiscaleOperatore());

    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("pratiche"));
    }

    List<PraticaSingola> elencoPratiche = new ArrayList<>();
    if (pratiche != null) {
      elencoPratiche = pratiche.getElencoPratiche();
    }

    ElencoPratichePanel elencoPratichePanel =
        new ElencoPratichePanel("elencoPratichePanel", elencoPratiche);
    if (elencoPratiche.isEmpty() || elencoPratiche.get(0).getPRANPROTNUMERO() == null) {
      warn("Nessuna pratica  trovata");
      elencoPratichePanel.setVisible(false);
    }

    addOrReplace(elencoPratichePanel);
  }
}
