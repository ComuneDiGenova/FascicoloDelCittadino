package it.liguriadigitale.ponmetro.portale.presentation.pages.home.status.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.markup.html.basic.Label;

@SuppressWarnings("rawtypes")
public abstract class CaricamentoPanel extends AjaxLazyLoadPanel {

  private static final long serialVersionUID = -2222575855488452572L;
  private static Log log = LogFactory.getLog(CaricamentoPanel.class);

  protected abstract void chiamaApiBackend() throws BusinessException, ApiException, IOException;

  protected abstract String getMsgErrore();

  public CaricamentoPanel(String id) {
    super(id);
  }

  @Override
  public Component getLazyLoadComponent(String id) {

    log.debug("--- getLazyLoadComponent INIZIO");

    NotificationPanel panel =
        new NotificationPanel(id) {

          private static final long serialVersionUID = 1019279207745028874L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };

    try {
      log.debug("Prima");
      chiamaApiBackend();
      log.debug("DOPO");
    } catch (BusinessException | ApiException | IOException e) {
      String msgErrore = getMsgErrore();
      log.error("Errore " + msgErrore + ": ", e);
      error(msgErrore);
    }
    log.debug("--- getLazyLoadComponent FINE");
    return panel;
  }

  @Override
  public Component getLoadingComponent(String id) {
    return new Label(id);
  }
}
