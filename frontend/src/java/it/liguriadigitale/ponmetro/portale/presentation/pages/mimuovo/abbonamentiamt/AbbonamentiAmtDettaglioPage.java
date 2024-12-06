package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt;

import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto.AmtPromozioneXXL;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.panel.AbbonamentiAmtDettaglioPanel;
import org.apache.wicket.model.CompoundPropertyModel;

public class AbbonamentiAmtDettaglioPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -9220223623402482939L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AbbonamentiAmtDettaglioPage(CompoundPropertyModel<AmtPromozioneXXL> datiAmtXXL) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    AbbonamentiAmtDettaglioPanel dettaglioPanel =
        new AbbonamentiAmtDettaglioPanel("dettaglioPanel", datiAmtXXL);
    addOrReplace(dettaglioPanel);

    setOutputMarkupId(true);
  }
}
