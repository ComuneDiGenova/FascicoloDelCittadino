package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.annulla;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.annulla.panel.AnnullaRitiroCedolaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.breadcrumb.BreadcrumbCedolePanel;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;

public class AnnullaRitiroCedolaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  public AnnullaRitiroCedolaPage(Cedola cedola) {
    super();

    BreadcrumbCedolePanel breadcrumbMieiFigliPanel = new BreadcrumbCedolePanel("breadcrumbPanel");
    add(breadcrumbMieiFigliPanel);

    AnnullaRitiroCedolaPanel panel = new AnnullaRitiroCedolaPanel("ritiro", cedola);
    add(panel);
  }
}
