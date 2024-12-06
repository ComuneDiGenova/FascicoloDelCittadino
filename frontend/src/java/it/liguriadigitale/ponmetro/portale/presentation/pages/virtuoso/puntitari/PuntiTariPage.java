package it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.puntitari;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel.FascicoloTabPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.puntitari.panel.PuntiTariPanel;
import it.liguriadigitale.ponmetro.puntitari.model.PuntiTari;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class PuntiTariPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -2864622058737707389L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public PuntiTariPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    Integer annoCorrente = LocalDate.now().getYear();
    tabsPuntiTari(annoCorrente);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  private void tabsPuntiTari(Integer annoCorrente) {

    final Integer anniDaMostrare = 5;
    Integer ultimoAnnoDaMostrare = annoCorrente - anniDaMostrare;
    List<ITab> listaTabsPuntiTari = new ArrayList<>();

    while (annoCorrente > ultimoAnnoDaMostrare) {

      Integer annoTari = annoCorrente;

      AbstractTab tab =
          new AbstractTab(new Model<>(String.valueOf(annoCorrente))) {

            private static final long serialVersionUID = 7174383138225762509L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
              Panel panelRisultato = null;

              log.debug("puntiTari prima try");

              try {
                PuntiTari puntiTari = null;

                puntiTari =
                    ServiceLocator.getInstance()
                        .getServiziPuntiTari()
                        .getPuntiTari(getUtente().getCodiceFiscaleOperatore(), annoTari);

                panelRisultato = new PuntiTariPanel(panelId, puntiTari, annoTari);

                log.debug("panelRisultato : " + panelRisultato);

              } catch (ApiException | BusinessException | IOException e) {
                panelRisultato = new EmptyPanel(panelId);
              }

              return panelRisultato;
            }
          };

      annoCorrente = annoCorrente - 1;

      listaTabsPuntiTari.add(tab);
    }

    FascicoloTabPanel<ITab> fascicoloTabPanel =
        new FascicoloTabPanel<ITab>("tabsPanel", listaTabsPuntiTari);
    fascicoloTabPanel.setOutputMarkupId(true);
    addOrReplace(fascicoloTabPanel);
  }

  public List<MessaggiInformativi> popolaListaMessaggi() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();
    MessaggiInformativi messaggio = new MessaggiInformativi();
    messaggio.setMessaggio(getString("PuntiTariPage.messaggio"));
    messaggio.setType("info");
    listaMessaggi.add(messaggio);
    return listaMessaggi;
  }
}
