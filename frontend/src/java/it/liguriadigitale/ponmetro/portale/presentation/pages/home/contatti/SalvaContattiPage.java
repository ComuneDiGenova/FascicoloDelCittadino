package it.liguriadigitale.ponmetro.portale.presentation.pages.home.contatti;

import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.contatti.panel.SalvaContattiPanel;
import java.util.List;

public class SalvaContattiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1139781915673264282L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SalvaContattiPage(boolean isBreadcrumb, List<ContattiUtente> contattiUtente) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    breadcrumbPanel.setVisible(isBreadcrumb);
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    boolean isObbligatorioInserireContatti = isBreadcrumb ? false : true;

    SalvaContattiPanel salvaContattiPanel =
        new SalvaContattiPanel(
            "salvaContattiPanel", isObbligatorioInserireContatti, contattiUtente);
    addOrReplace(salvaContattiPanel);

    setOutputMarkupId(true);
  }
}
