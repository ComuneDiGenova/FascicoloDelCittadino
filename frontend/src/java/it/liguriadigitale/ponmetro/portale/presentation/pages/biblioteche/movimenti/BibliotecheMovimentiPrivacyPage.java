package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti;

import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.panel.BibliotecheMovimentiPrivacyPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.util.List;

public class BibliotecheMovimentiPrivacyPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 351289570640600659L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public BibliotecheMovimentiPrivacyPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    Boolean maggiorenne = true;
    BibliotecheMovimentiPrivacyPanel privacyPanel =
        (BibliotecheMovimentiPrivacyPanel)
            new BibliotecheMovimentiPrivacyPanel("privacy", maggiorenne).setRenderBodyOnly(true);
    addOrReplace(privacyPanel);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public BibliotecheMovimentiPrivacyPage(Boolean maggiorenne) {
    super();

    List<BreadcrumbFdC> listaBreadcrumb =
        ServiceLocator.getInstance().getServiziBiblioteche().getListaBreadcrumbInternet();
    FdCBreadcrumbPanel breadcrumbPanel = new FdCBreadcrumbPanel("breadcrumbPanel", listaBreadcrumb);
    addOrReplace(breadcrumbPanel);

    BibliotecheMovimentiPrivacyPanel privacyPanel =
        (BibliotecheMovimentiPrivacyPanel)
            new BibliotecheMovimentiPrivacyPanel("privacy", maggiorenne).setRenderBodyOnly(true);
    addOrReplace(privacyPanel);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public BibliotecheMovimentiPrivacyPage(ComponenteNucleo bambino, boolean maggiorenne) {
    super();

    List<BreadcrumbFdC> listaBreadcrumb =
        ServiceLocator.getInstance().getServiziBiblioteche().getListaBreadcrumbInternet();
    FdCBreadcrumbPanel breadcrumbPanel = new FdCBreadcrumbPanel("breadcrumbPanel", listaBreadcrumb);
    addOrReplace(breadcrumbPanel);

    BibliotecheMovimentiPrivacyPanel privacyPanel =
        (BibliotecheMovimentiPrivacyPanel)
            new BibliotecheMovimentiPrivacyPanel("privacy", bambino, maggiorenne)
                .setRenderBodyOnly(true);
    addOrReplace(privacyPanel);
  }
}
