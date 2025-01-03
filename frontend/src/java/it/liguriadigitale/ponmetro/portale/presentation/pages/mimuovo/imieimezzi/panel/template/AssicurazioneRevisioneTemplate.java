package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.template;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;

public abstract class AssicurazioneRevisioneTemplate extends BasePanel {

  private static final long serialVersionUID = 1L;

  protected boolean erroreBusiness;
  protected WebMarkupContainer container;

  protected abstract void logicaBusinessDelPannello(Object dati)
      throws BusinessException, ApiException;

  public AssicurazioneRevisioneTemplate(String id) {
    super(id);

    erroreBusiness = false;
    container = new WebMarkupContainer("containerAssicurazioneRevisione");
    add(container);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    try {
      logicaBusinessDelPannello(dati);
    } catch (BusinessException | ApiException | ProcessingException | WebApplicationException e) {
      gestioneErroreBusiness(e);
    }
  }

  @Override
  protected void onConfigure() {
    super.onConfigure();
    log.debug("onConfigure - BEGIN assicurazione e revisione" + erroreBusiness);
    if (erroreBusiness) {
      this.getClass().getCanonicalName();
      String messaggioErrore = getString("AssicurazioneRevisioneTemplate.messaggioErrore");
      warn(messaggioErrore);
      container.setVisible(false);
    } else {
      container.setVisible(true);
    }
  }

  protected void gestioneErroreBusiness(Exception e) {
    log.error("Errore API assicurazione e revisione:", e);
    erroreBusiness = true;
  }

  protected MarkupContainer myAdd(Component... children) {
    return container.add(children);
  }
}
