package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.richiesta;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaRimborsoTariNetribe;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.richiesta.panel.RichiestaRimborsoTariNetribePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.util.RimborsiTariUtil;

public class RichiestaRimborsoTariEredeNetribePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -4663906742388556879L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RichiestaRimborsoTariEredeNetribePage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    boolean isIntestatario = false;
    DatiRichiestaRimborsoTariNetribe datiRimborso =
        RimborsiTariUtil.setDatiRichiedenteRimborso(getUtente(), isIntestatario);

    RichiestaRimborsoTariNetribePanel rimborsoPanel =
        new RichiestaRimborsoTariNetribePanel("rimborsoPanel", datiRimborso);
    addOrReplace(rimborsoPanel);

    setOutputMarkupId(true);
  }
}
