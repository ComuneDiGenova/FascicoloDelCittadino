package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.MieIstanzeFormPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.MieIstanzePage;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.Model;

public class HeaderMieIstanzePanel extends BasePanel {

  private static final long serialVersionUID = 87710262236758350L;

  public HeaderMieIstanzePanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {

    addOrReplace(creaLinkFlagIstanza("CMP"));
    addOrReplace(creaLinkFlagIstanza("IVT"));
    addOrReplace(creaLinkFlagIstanza("PCR"));
    addOrReplace(creaLinkFlagIstanza("ATT"));
    addOrReplace(creaLinkFlagIstanza("EVA"));
    addOrReplace(creaLinkFlagIstanza("EVP"));
    addOrReplace(creaLinkFlagIstanza("EVR"));
    addOrReplace(creaLinkFlagIstanza("TUTTE"));

    /*
     * addOrReplace(creaLinkFlagVerbaleInAttesa());
     * addOrReplace(creaLinkFlagVerbaleArchiviato());
     *
     * addOrReplace(creaLinkFlagVerbaleOggettoDiRicorso());
     * addOrReplace(creaLinkFlagVerbaleInvioAdAutorita());
     *
     * addOrReplace(creaLinkFlagVerbaleTutti());
     */
    AjaxLink<Void> btnRicercaIstanza =
        new AjaxLink<Void>("btnRicercaIstanza") {

          private static final long serialVersionUID = -5346843679912754805L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            setResponsePage(new MieIstanzeFormPage());
          }
        };
    addOrReplace(btnRicercaIstanza);
  }

  private LaddaAjaxLink<Object> creaLinkFlagIstanza(String codice) {
    LaddaAjaxLink<Object> link =
        new LaddaAjaxLink<Object>("flagIstanza" + codice, Type.Link) {

          private static final long serialVersionUID = 1448579012199876827L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(HeaderMieIstanzePanel.this);
            try {

              String resourceId = "HeaderMieIstanzePanel." + codice;
              String decodificaCodice = getString(resourceId);

              List<Istanza> listaIstanze =
                  ServiceLocator.getInstance()
                      .getServiziMieIstanze()
                      .getListaTutteIstanzeConFiltroStatoDaBadge(getUtente(), codice);

              setResponsePage(new MieIstanzePage(listaIstanze, decodificaCodice)); // , "IVT"));
            } catch (BusinessException | ApiException | IOException e) {
              log.debug("Errore durante la chiamata delle API", e);
              error("Servizio attualmente non disponibile");
              throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("istanze"));
            }
          }
        };

    link.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("HeaderMieIstanzePanel." + codice, HeaderMieIstanzePanel.this)));
    return link;
  }
}
