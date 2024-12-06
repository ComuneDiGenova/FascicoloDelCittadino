package it.liguriadigitale.ponmetro.portale.presentation.pages.arte;

import it.liguriadigitale.ponmetro.arte.model.Fattura;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.arte.panel.FattureArtePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel.FascicoloTabPanel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class FattureArtePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -8767764613981475869L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public FattureArtePage(List<Fattura> fatture) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    tabsAnniFatture(LocalDate.now().getYear(), fatture);

    setOutputMarkupId(true);
  }

  @SuppressWarnings("serial")
  private void tabsAnniFatture(Integer annoCorrente, List<Fattura> listaTutteFatture) {

    final Integer anniDaMostrare = 5;
    Integer ultimoAnnoDaMostrare = annoCorrente - anniDaMostrare;
    List<ITab> listaTabsAnniFatture = new ArrayList<>();

    while (annoCorrente > ultimoAnnoDaMostrare) {

      log.debug("CP anno corr = " + annoCorrente);

      BigDecimal annoDaMostrare = BigDecimal.valueOf(annoCorrente);

      AbstractTab tab =
          new AbstractTab(new Model<>(String.valueOf(annoCorrente))) {

            @Override
            public WebMarkupContainer getPanel(String panelId) {

              List<Fattura> listaFattureDiQuellAnno = new ArrayList<Fattura>();

              log.debug("CP lista fatture anno = " + annoDaMostrare);

              listaFattureDiQuellAnno =
                  ServiceLocator.getInstance()
                      .getServiziArte()
                      .getListaFattureDiQuellAnno(listaTutteFatture, annoDaMostrare);

              return new FattureArtePanel(panelId, listaFattureDiQuellAnno);
            }
          };

      listaTabsAnniFatture.add(tab);

      annoCorrente = annoCorrente - 1;
    }

    FascicoloTabPanel<ITab> fascicoloTabPanel =
        new FascicoloTabPanel<ITab>("tabsPanel", listaTabsAnniFatture);
    fascicoloTabPanel.setOutputMarkupId(true);
    addOrReplace(fascicoloTabPanel);
  }
}
