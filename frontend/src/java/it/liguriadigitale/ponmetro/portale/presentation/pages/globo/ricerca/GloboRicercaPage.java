package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.ricerca;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.globo.GloboFrontendInterface;
import it.liguriadigitale.ponmetro.portale.pojo.globo.GloboServiziOnlineSettings;
import it.liguriadigitale.ponmetro.portale.pojo.globo.Node;
import it.liguriadigitale.ponmetro.portale.pojo.globo.Tag;
import it.liguriadigitale.ponmetro.portale.pojo.globo.ricerca.RicercaGlobo;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.ricerca.panel.GloboListaFunzioniPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.ricerca.panel.GloboPannelloRicerca;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.panel.EmptyPanel;

public class GloboRicercaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3595319650876555040L;
  private List<Node> nodi;
  private List<Tag> tags;
  private RicercaGlobo ricercaDTO;

  public GloboRicercaPage() {
    super();
    ricercaDTO = new RicercaGlobo();

    GloboFrontendInterface globo = ServiceLocator.getInstance().getGlobo();
    try {
      nodi = globo.getServiziOnlineGlobo();
      log.debug("nodi: " + nodi);

      GloboServiziOnlineSettings ricerca2 = globo.getServiziOnlineGloboSettings();
      ricercaDTO.setTags(ricerca2.getTag_argomenti());
      log.debug("GloboServiziOnlineSettings: " + ricerca2);
    } catch (BusinessException e) {
      log.error("Error: ", e);
    }

    GloboPannelloRicerca pannello = new GloboPannelloRicerca("pannello", ricercaDTO);
    addOrReplace(pannello);

    EmptyPanel panelLista = new EmptyPanel("lista");
    add(panelLista);
    creaBreadcrumb();
  }

  private List<Node> getListaNodi(RicercaGlobo ricerca) {
    if (ricerca.getTestoLibero() != null || "".equals(ricerca.getTestoLibero())) {
      ricerca.setCombo(null);
      ricerca.setComboChild(null);
    }
    GloboFrontendInterface globo = ServiceLocator.getInstance().getGlobo();
    return globo.filtraLista(getNodi(), ricerca, getUtente());
  }

  public List<Node> getNodi() {
    return nodi;
  }

  public List<Tag> getTags() {
    return tags;
  }

  private void creaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();
    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("globoRicerca", "Ricerca funzioni"));

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare("GloboRicercaPage", getUtente()));
    log.debug("nome pagina: " + GloboRicercaPage.class.getSimpleName());
    addOrReplace(breadcrumbPanel);
  }

  public void refresh(RicercaGlobo ricerca) {
    log.debug("refresh");
    ricercaDTO = ricerca;
    this.remove("lista");
    GloboListaFunzioniPanel pannelloLista =
        new GloboListaFunzioniPanel("lista", getListaNodi(ricerca));
    add(pannelloLista);
    log.debug("refresh  -- ");
  }
}
