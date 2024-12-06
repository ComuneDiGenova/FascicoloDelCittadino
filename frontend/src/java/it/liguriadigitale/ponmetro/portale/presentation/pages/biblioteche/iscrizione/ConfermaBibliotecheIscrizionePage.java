package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione;

import it.liguriadigitale.ponmetro.portale.pojo.biblioteche.BibliotecheIscrizione;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.panel.ConfermaBibliotecheIscrizionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class ConfermaBibliotecheIscrizionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -9116592277704658816L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public ConfermaBibliotecheIscrizionePage(BibliotecheIscrizione bibliotecheIscrizione) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    ConfermaBibliotecheIscrizionePanel bibliotecheIscrizionePanel =
        new ConfermaBibliotecheIscrizionePanel(bibliotecheIscrizione);
    addOrReplace(bibliotecheIscrizionePanel);
  }
}
