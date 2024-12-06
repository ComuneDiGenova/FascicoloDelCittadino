package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.ricorsialprefetto;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.ricorsialprefetto.panel.RicorsiAlPrefettoPanel;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RicorsiAlPrefettoCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RicorsoAlPrefetto;
import java.util.ArrayList;
import java.util.List;

public class RicorsiAlPrefettoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 3980981076936846645L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RicorsiAlPrefettoPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    LinkDinamicoLaddaFunzione<Object> btnDomandaRicorsoAlPrefetto =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaRicorsoAlPrefetto",
            new LinkDinamicoFunzioneData(
                "RicorsiAlPrefettoPage.btnDomandaRicorsoAlPrefetto",
                "RichiestaRicorsoAlPrefettoPage",
                "RicorsiAlPrefettoPage.btnDomandaRicorsoAlPrefetto"),
            null,
            RicorsiAlPrefettoPage.this,
            "color-cyan col-auto icon-fogli");
    addOrReplace(btnDomandaRicorsoAlPrefetto);

    List<RicorsoAlPrefetto> listaRicorsi = popolaRicorsi(getUtente().getCodiceFiscaleOperatore());
    RicorsiAlPrefettoPanel ricorsiPanel = new RicorsiAlPrefettoPanel("ricorsiPanel", listaRicorsi);
    addOrReplace(ricorsiPanel);

    setOutputMarkupId(true);
  }

  private List<RicorsoAlPrefetto> popolaRicorsi(String codiceFiscale) {
    RicorsiAlPrefettoCollection ricorsi = new RicorsiAlPrefettoCollection();
    List<RicorsoAlPrefetto> listaRicorsi = new ArrayList<RicorsoAlPrefetto>();

    try {
      ricorsi =
          ServiceLocator.getInstance()
              .getRicorsiAlPrefetto()
              .getListaRircorsiAlPrefetto(codiceFiscale);

      listaRicorsi = ricorsi.getRicorsiAlPrefetto();
    } catch (BusinessException | ApiException e) {
      log.error("Errore popolaRicorsi: " + e.getMessage(), e);
    }

    return listaRicorsi;
  }
}
