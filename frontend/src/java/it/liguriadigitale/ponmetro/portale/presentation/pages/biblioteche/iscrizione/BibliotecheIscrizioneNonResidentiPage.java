package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione;

import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.panel.BibliotecheIscrizioneNonResidentiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.util.List;

public class BibliotecheIscrizioneNonResidentiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 3359923110194909973L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public BibliotecheIscrizioneNonResidentiPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    BibliotecheIscrizioneNonResidentiPanel bibliotecheIscrizioneNonResidentiPanel =
        (BibliotecheIscrizioneNonResidentiPanel)
            new BibliotecheIscrizioneNonResidentiPanel("bibliotecheIscrizionePanel")
                .setRenderBodyOnly(true);
    addOrReplace(bibliotecheIscrizioneNonResidentiPanel);
  }

  public BibliotecheIscrizioneNonResidentiPage(ComponenteNucleo bambino) {
    super();

    List<BreadcrumbFdC> listaBreadcrumb =
        ServiceLocator.getInstance().getServiziBiblioteche().getListaBreadcrumbIscrizioneBambini();
    FdCBreadcrumbPanel breadcrumbPanel = new FdCBreadcrumbPanel("breadcrumbPanel", listaBreadcrumb);
    addOrReplace(breadcrumbPanel);

    BibliotecheIscrizioneNonResidentiPanel bibliotecheIscrizioneNonResidentiPanel =
        (BibliotecheIscrizioneNonResidentiPanel)
            new BibliotecheIscrizioneNonResidentiPanel("bibliotecheIscrizionePanel")
                .setRenderBodyOnly(true);
    addOrReplace(bibliotecheIscrizioneNonResidentiPanel);
  }
}
