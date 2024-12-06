package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.commissionimensa;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.commissionimensa.panel.VerbaliCommissioneMensaPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class VerbaliCommissioneMensaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -447343906527291014L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public VerbaliCommissioneMensaPage(UtenteServiziRistorazione iscrizione) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    VerbaliCommissioneMensaPanel commissioneMensaPanel =
        new VerbaliCommissioneMensaPanel(iscrizione);
    addOrReplace(commissioneMensaPanel);
  }
}
