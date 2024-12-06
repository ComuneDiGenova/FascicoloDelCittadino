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
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIResult;

public class RichiestaRimborsoTariNetribePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 3784596055491739568L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RichiestaRimborsoTariNetribePage(TARIResult tari) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    boolean isIntestatario = true;
    DatiRichiestaRimborsoTariNetribe datiRimborso =
        RimborsiTariUtil.setDatiRichiedenteRimborso(getUtente(), isIntestatario);

    if (LabelFdCUtil.checkIfNotNull(tari.getTestataAvviso())
        && PageUtil.isStringValid(tari.getTestataAvviso().getIdAvviso())) {
      datiRimborso.setIdAvvisoBolletta(tari.getTestataAvviso().getIdAvviso());
    }

    if (LabelFdCUtil.checkIfNotNull(tari.getDatiPagamento())
        && LabelFdCUtil.checkIfNotNull(tari.getDatiPagamento().getSaldo())) {
      datiRimborso.setImportoRimborso(Math.abs(tari.getDatiPagamento().getSaldo()));
    }

    if (LabelFdCUtil.checkIfNotNull(tari.getDatiPagamento())
        && LabelFdCUtil.checkIfNotNull(tari.getDatiPagamento().getSaldi())) {
      datiRimborso.setListaSaldi(tari.getDatiPagamento().getSaldi());
    }

    RichiestaRimborsoTariNetribePanel rimborsoPanel =
        new RichiestaRimborsoTariNetribePanel("rimborsoPanel", datiRimborso);
    addOrReplace(rimborsoPanel);

    setOutputMarkupId(true);
  }
}
