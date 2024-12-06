package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze.accertato.dettagli;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.AccertatoScadenzeExt;
import it.liguriadigitale.ponmetro.portale.presentation.application.HomeWebApplication;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPageExtended;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze.accertato.dettagli.panel.DatiAccertamentoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze.accertato.dettagli.panel.DatiVersamentiAttoPanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DettagliScadenzaAccertatoPage extends LayoutNoFeedBackPageExtended {

  private static final long serialVersionUID = 1L;
  protected Log log = LogFactory.getLog(getClass());

  private AccertatoScadenzeExt _accertatoScadenzeExt = null;

  public DettagliScadenzaAccertatoPage(AccertatoScadenzeExt accertatoScadenzeExt) {
    super();

    // chiamo una sola volta e poi "filtro" senza richiamare servizio
    _accertatoScadenzeExt = accertatoScadenzeExt;
    this.initPage(
        HomeWebApplication
            .ID_DETTAGLIOACCERTATO_OFTRIBSCADENZE_OFSCADENZEEVERSATO_OFTRIBUTI_OFHOME);
  }

  private void initPage(final String idPage) {
    // 1 - breadcrumb > path per navigare nel sito
    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    // 2 - header > indicazioni aggiuntive per informazioni su pagina
    // visitata (messaggi)
    // add(new InfoBasePanel(idPage));

    // 4 - content > NO TAB solo panel
    createContent();
  }

  private void createContent() {
    // Panel generico largo 6
    DatiAccertamentoPanel datiAccertamentoPanel =
        new DatiAccertamentoPanel("datiAccertamentoPanel");
    datiAccertamentoPanel.fillDati(_accertatoScadenzeExt);
    add(datiAccertamentoPanel);

    // Panel generico largo 6
    DatiVersamentiAttoPanel datiVersamentiAttoPanel =
        new DatiVersamentiAttoPanel("datiVersamentiAttoPanel");
    datiVersamentiAttoPanel.fillDati(_accertatoScadenzeExt);
    add(datiVersamentiAttoPanel);
  }
}
