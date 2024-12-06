package it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.abitabilita;

import it.liguriadigitale.ponmetro.portale.pojo.edilizia.abitabilita.Abitabilita;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.abitabilita.panel.AbitabilitaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;

public class AbitabilitaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1304702932379535008L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AbitabilitaPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    Abitabilita abitabilita = new Abitabilita();

    String via = null;
    Integer civico = 0;
    if (getUtente().isResidente()) {
      via = getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvOfficialStreetName();
      if (getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvStreetNumber() != null) {

        civico =
            Integer.parseInt(
                getUtente()
                    .getDatiCittadinoResidente()
                    .getCpvHasAddress()
                    .getClvStreetNumber()
                    .replaceAll("[a-zA-Z]", ""));
      }
    }
    abitabilita.setVia(via);

    abitabilita.setCivicoDa(civico);
    abitabilita.setCivicoA(civico);

    AbitabilitaPanel abitabilitaPanel = new AbitabilitaPanel("abitabilitaPanel", abitabilita);
    // TODO fill dati con servizio GET
    // abitabilitaPanel.fillDati(popolaPraticheArpal());
    addOrReplace(abitabilitaPanel);

    setOutputMarkupId(true);
  }
}
