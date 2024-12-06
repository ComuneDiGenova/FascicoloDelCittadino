package it.liguriadigitale.ponmetro.portale.presentation.pages.autodichiarazionefigli;

import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.autodichiarazionefigli.panel.DomandeAutodichiarazioneFigliPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;

public class DomandeAutodichiarazioneFigliPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3147749692974367537L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public DomandeAutodichiarazioneFigliPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    LinkDinamicoLaddaFunzione<Object> btnDomandaAutodichiarazione =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaAutodichiarazione",
            new LinkDinamicoFunzioneData(
                "DomandeAutodichiarazioneFigliPage.btnDomandaAutodichiarazione",
                "RichiestaAutodichiarazioneFigliPage",
                "DomandeAutodichiarazioneFigliPage.btnDomandaAutodichiarazione"),
            null,
            DomandeAutodichiarazioneFigliPage.this,
            "color-yellow col-auto icon-aggiungi-preferiti");
    addOrReplace(btnDomandaAutodichiarazione);

    DomandeAutodichiarazioneFigliPanel domandeAutodichiarazioniPanel =
        new DomandeAutodichiarazioneFigliPanel("domandeAutodichiarazioniPanel", null);
    addOrReplace(domandeAutodichiarazioniPanel);
  }
}
