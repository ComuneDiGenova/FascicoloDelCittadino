package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel.FascicoloTabPanel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class LayoutNoFeedBackPageExtended extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1L;

  protected FascicoloTabPanel<ITab> createTabAndContent() {
    return createTabAndContent(null);
  }

  protected FascicoloTabPanel<ITab> createTabAndContent(LayoutNoFeedBackPageExtended t) {
    Integer annoCorrente = LocalDate.now().getYear();

    final Integer anniDaMostrare = 6;
    Integer ultimoAnnoDaMostrare = annoCorrente - anniDaMostrare;
    List<ITab> listaTabs = new ArrayList<>();

    while (annoCorrente > ultimoAnnoDaMostrare) {

      Integer annoIesimo = annoCorrente;

      AbstractTab tabIesimo =
          new AbstractTab(new Model<>(String.valueOf(annoCorrente))) {

            private static final long serialVersionUID = -1L;

            @Override
            public Panel getPanel(String panelId) {
              Panel panelRisultato = null;
              try {
                log.debug("bbbbbbbbbbbbb ::::::::::::::: ");
                panelRisultato = t.getPanelRisultatoByYear(panelId, annoIesimo);
                log.debug("::::::::::::::: bbbbbbbbbbbbb");
              } catch (Exception e) {
                log.error("Errore tabIesimo createTabAndContent: " + e.getMessage());
                NotificationPanel vuoto = createAndGetFeedback(panelId);
                String messaggioRicevuto =
                    "Servizio momentaneamente non disponibile, riprovare piu' tardi";
                Integer indexOf = messaggioRicevuto.indexOf(":");
                String messaggioDaVisualizzare =
                    messaggioRicevuto.substring(indexOf + 1, messaggioRicevuto.length());
                vuoto.error(messaggioDaVisualizzare);
                panelRisultato = vuoto;
              }
              return panelRisultato;
            }
          };
      listaTabs.add(tabIesimo);
      annoCorrente = annoCorrente - 1;
    }

    FascicoloTabPanel<ITab> fascicoloTabPanel = new FascicoloTabPanel<ITab>("tabsPanel", listaTabs);
    fascicoloTabPanel.setOutputMarkupId(true);
    return fascicoloTabPanel;
  }

  protected Panel getPanelRisultatoByYear(String panelId, Integer annoIesimo) {
    // TODO Auto-generated method stub
    return null;
  }

  protected NotificationPanel createAndGetFeedback(String panelId) {
    NotificationPanel vuoto =
        new NotificationPanel(panelId) {

          private static final long serialVersionUID = 6718790861531219567L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    add(vuoto);
    return vuoto;
  }
}
