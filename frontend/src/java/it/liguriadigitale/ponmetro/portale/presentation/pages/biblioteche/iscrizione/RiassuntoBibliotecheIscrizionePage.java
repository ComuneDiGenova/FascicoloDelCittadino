package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione;

import it.liguriadigitale.ponmetro.portale.pojo.biblioteche.BibliotecheIscrizione;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.panel.RiassuntoBibliotecheIscrizionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class RiassuntoBibliotecheIscrizionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 4118428330711862064L;

  public RiassuntoBibliotecheIscrizionePage() {
    super();
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public RiassuntoBibliotecheIscrizionePage(
      BibliotecheIscrizione bibliotecheIscrizione, ComponenteNucleo componenteNucleo) {

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RiassuntoBibliotecheIscrizionePanel riassuntoBibliotecheIscrizionePanel =
        new RiassuntoBibliotecheIscrizionePanel(bibliotecheIscrizione, componenteNucleo);
    add(riassuntoBibliotecheIscrizionePanel);
  }
}
