package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.template;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.io.IOException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;

public abstract class MieiDatiTemplate extends BasePanel {

  private static final long serialVersionUID = -4568270500517707529L;
  protected boolean erroreBusiness;
  protected WebMarkupContainer container;
  protected NotificationPanel templateFeedBackPanel;

  protected abstract void logicaBusinessDelPannello(Object dati)
      throws BusinessException, ApiException, IOException;

  public MieiDatiTemplate(String id) {
    super(id);
    erroreBusiness = false;
    container = new WebMarkupContainer("containerMieiDati");
    add(container);
    templateFeedBackPanel = createFeedBackPannello();
    add(templateFeedBackPanel);
  }

  @Override
  public void fillDati(Object dati) {
    try {
      logicaBusinessDelPannello(dati);
    } catch (BusinessException
        | ApiException
        | ProcessingException
        | WebApplicationException
        | IOException e) {
      gestioneErroreBusiness(e);
    }
  }

  @Override
  protected void onConfigure() {
    super.onConfigure();
    log.debug("onConfigure - BEGIN");
    if (erroreBusiness) {
      this.getClass().getCanonicalName();
      // feedBackPanel.setFilter(new
      // ContainerFeedbackMessageFilter(this));
      container.setVisible(false);
      templateFeedBackPanel.setVisible(true);
    } else {
      container.setVisible(true);
      templateFeedBackPanel.setVisible(false);
    }
  }

  protected void gestioneErroreBusiness(Exception e) {
    log.error("Errore API:", e);
    erroreBusiness = true;
  }

  protected MarkupContainer myAdd(Component... children) {
    return container.add(children);
  }

  private NotificationPanel createFeedBackPannello() {
    NotificationPanel feedback =
        new NotificationPanel("feedbackPanel") {

          private static final long serialVersionUID = -883302032153540620L;

          @Override
          protected boolean isCloseButtonVisible() {
            /* Feedback senza X per la chiusura */
            return false;
          }
        };
    feedback.setOutputMarkupId(true);
    return feedback;
  }
}
