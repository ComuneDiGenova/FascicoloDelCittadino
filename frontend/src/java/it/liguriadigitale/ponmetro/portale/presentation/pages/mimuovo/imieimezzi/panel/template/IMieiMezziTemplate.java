package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.template;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;

public abstract class IMieiMezziTemplate extends BasePanel {

  private static final long serialVersionUID = -8611235453853007400L;

  protected boolean erroreBusiness;

  protected WebMarkupContainer container;
  protected WebMarkupContainer containerBox = new WebMarkupContainer("box");
  protected WebMarkupContainer containerMessaggioListaVuota =
      new WebMarkupContainer("messaggioListaVuota");
  protected WebMarkupContainer containerErrore = new WebMarkupContainer("containerErrore");

  protected abstract void logicaBusinessDelPannello(Object dati)
      throws BusinessException, ApiException;

  public IMieiMezziTemplate(String id) {
    super(id);

    erroreBusiness = false;
    container =
        (WebMarkupContainer) new WebMarkupContainer("containerIMieiMezzi").setRenderBodyOnly(true);
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
    log.debug("onConfigure - BEGIN miei mezzi " + erroreBusiness);
    if (erroreBusiness) {
      this.getClass().getCanonicalName();
      String messaggioErrore = getString("IMieiMezziTemplate.messaggioErrore");
      warn(messaggioErrore);
      containerErrore.setVisible(true);
      addOrReplace(containerErrore);
      container.setVisible(false);
      addOrReplace(containerBox);
      containerBox.setVisible(false);
      addOrReplace(containerMessaggioListaVuota);
      containerMessaggioListaVuota.setVisible(false);
    } else {
      addOrReplace(containerErrore);
      containerErrore.setVisible(false);

      container.setVisible(true);
    }
  }

  protected void gestioneErroreBusiness(Exception e) {
    log.error("Errore API miei mezzi: " + erroreBusiness, e);
    erroreBusiness = true;
  }

  protected MarkupContainer myAdd(Component... children) {
    return container.add(children);
  }
}
