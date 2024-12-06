package it.liguriadigitale.ponmetro.portale.presentation.pages.domandeunionicivili;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.DatiMatrimonio;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.TipologiaRichiestaEnum;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.domandeMatrimonio.panel.DomandeMatrimonioPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomandeUnioniCiviliPage extends LayoutNoFeedBackPage {

  @SuppressWarnings({"rawtypes", "unchecked"})
  public DomandeUnioniCiviliPage() {

    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    TipologiaRichiestaEnum tipologia = TipologiaRichiestaEnum.UNIONECIVILE;

    List<DatiMatrimonio> matrimoni =
        popolaUnioni(getUtente().getCodiceFiscaleOperatore(), tipologia);

    boolean isRichiestaVisibile = !esisteUnaRichiestaInCorso(matrimoni);

    LinkDinamicoLaddaFunzione<Object> btnDomandaUnione =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaUnione",
            new LinkDinamicoFunzioneData(
                "DomandeUnioniCiviliPage.btnDomandaUnione",
                "RichiestaUnioniCiviliPage",
                "DomandeUnioniCiviliPage.btnDomandaUnione"),
            null,
            DomandeUnioniCiviliPage.this,
            "color-yellow col-auto icon-aggiungi-preferiti",
            isRichiestaVisibile);
    addOrReplace(btnDomandaUnione);

    DomandeMatrimonioPanel domandeUnione =
        new DomandeMatrimonioPanel("domandeUnionePanel", matrimoni, tipologia);
    addOrReplace(domandeUnione);
  }

  private List<DatiMatrimonio> popolaUnioni(
      String codiceFiscale, TipologiaRichiestaEnum tipologia) {
    log.debug("DomandeUnioniCiviliPage popolaUnioni");

    List<DatiMatrimonio> lista = new ArrayList<DatiMatrimonio>();
    try {
      return ServiceLocator.getInstance()
          .getMatrimoniAccenture()
          .getListaDomandeRichiedenteConiugeFiltrateInBaseATipologia(codiceFiscale, tipologia);

    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaUnioni: " + e.getMessage(), e);
    }
    return lista;
  }

  @SuppressWarnings("serial")
  private boolean esisteUnaRichiestaInCorso(List<DatiMatrimonio> matrimoni) {
    List<String> statiMatrimonioNonRichiedibili =
        new ArrayList<String>() {
          {
            add("Presentata");
            add("Da prendere in carico");
            add("Presa in carico");
            add("Fissato appuntamento");
            add("Sospesa");
          }
        };

    return matrimoni.stream()
        .anyMatch(elem -> statiMatrimonioNonRichiedibili.contains(elem.getStatoPraticaC()));
  }
}
