package it.liguriadigitale.ponmetro.portale.presentation.pages.impiantitermici;

import it.liguriadigitale.ponmetro.catastoimpianti.model.Impianto;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel.FascicoloTabPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.impiantitermici.panel.MacchineCaitelPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.impiantitermici.panel.ManutenzioniCaitelPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.impiantitermici.panel.RapportiCaitelPanel;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class DettagliCaitelPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -8371958479102751629L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public DettagliCaitelPage(Impianto impianto) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    //		List<MessaggiInformativi> listaMessaggi =
    // ServiceLocator.getInstance().getServiziImpiantiTermici()
    //				.popolaListaMessaggiDettagli();
    //
    //		AlertBoxPanel<Component> messaggi = (AlertBoxPanel<Component>) new
    // AlertBoxPanel<Component>("messaggi",
    //				listaMessaggi).setRenderBodyOnly(true);
    //		addOrReplace(messaggi);
    //
    //

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    List<ITab> listaTabsCaitel = new ArrayList<>();

    AbstractTab tabRapporti =
        new AbstractTab(new Model<>(getString("DettagliCaitelPage.rapporti"))) {

          private static final long serialVersionUID = -1363866084761917078L;

          @Override
          public Panel getPanel(String panelId) {

            return new RapportiCaitelPanel(panelId, impianto);
          }
        };

    AbstractTab tabMacchine =
        new AbstractTab(new Model<>(getString("DettagliCaitelPage.macchine"))) {

          private static final long serialVersionUID = 1026448214997582394L;

          @Override
          public Panel getPanel(String panelId) {

            return new MacchineCaitelPanel(panelId, impianto);
          }
        };

    AbstractTab tabManutenzioni =
        new AbstractTab(new Model<>(getString("DettagliCaitelPage.manutenzioni"))) {

          private static final long serialVersionUID = -4335049806591534954L;

          @Override
          public Panel getPanel(String panelId) {

            return new ManutenzioniCaitelPanel(panelId, impianto);
          }
        };

    listaTabsCaitel.add(tabRapporti);
    listaTabsCaitel.add(tabMacchine);
    listaTabsCaitel.add(tabManutenzioni);

    FascicoloTabPanel<ITab> fascicoloTabPanel =
        new FascicoloTabPanel<>("tabsCaitel", listaTabsCaitel);
    fascicoloTabPanel.setOutputMarkupId(true);
    addOrReplace(fascicoloTabPanel);

    setOutputMarkupId(true);
  }
}
