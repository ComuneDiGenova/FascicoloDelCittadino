package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.panel.template;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.io.IOException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;

public abstract class CedoleTemplate extends BasePanel {

  private static final long serialVersionUID = 5574858422004383429L;
  protected boolean erroreBusiness;
  protected WebMarkupContainer container;

  protected abstract void logicaBusinessDelPannello(Object dati)
      throws BusinessException, ApiException, IOException;

  public CedoleTemplate(String id) {
    super(id);
    erroreBusiness = false;
    container = new WebMarkupContainer("containerMieiDati");
    add(container);
    createFeedBackPanel();
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
      String messaggioErrore = getString("CedoleTemplate.messaggioErrore");
      warn(messaggioErrore);
      container.setVisible(false);
    } else {
      container.setVisible(true);
    }
  }

  protected void gestioneErroreBusiness(Exception e) {
    log.error("Errore API:", e);
    erroreBusiness = true;
  }

  protected MarkupContainer myAdd(Component... children) {
    return container.add(children);
  }
}
