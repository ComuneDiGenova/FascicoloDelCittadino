package it.liguriadigitale.ponmetro.portale.presentation.pages.account;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.panel.AutodichiarazioneGenitorePanel;

public class AutocertificazioneGenitorePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1303479588957705534L;

  public AutodichiarazioneGenitorePanel panel;

  public AutocertificazioneGenitorePage() {
    super();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    add(breadcrumbPanel);

    panel = new AutodichiarazioneGenitorePanel("autocertificazione");
    add(panel);
  }

  public boolean checkMappa() {
    if (panel != null) return panel.checkMappa();
    else return true;
  }
}
