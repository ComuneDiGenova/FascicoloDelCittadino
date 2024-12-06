package it.liguriadigitale.ponmetro.portale.presentation.pages.domandeseparazionidivorzi;

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

public class DomandeSeparazioniDivorziPage extends LayoutNoFeedBackPage {

  @SuppressWarnings({"rawtypes", "unchecked"})
  public DomandeSeparazioniDivorziPage() {

    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    TipologiaRichiestaEnum tipologia = TipologiaRichiestaEnum.SEPARAZIONEDIVORZIO;

    List<DatiMatrimonio> matrimoni =
        popolaSeparazioniDivorzi(getUtente().getCodiceFiscaleOperatore(), tipologia);

    boolean isRichiestaVisibile = !esisteUnaRichiestaInCorso(matrimoni);

    LinkDinamicoLaddaFunzione<Object> btnDomandaSeparazioneDivorzi =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaSeparazioneDivorzi",
            new LinkDinamicoFunzioneData(
                "DomandeSeparazioniDivorziPage.btnDomandaSeparazioneDivorzi",
                "RichiestaSeparazioneDivorzioPage",
                "DomandeSeparazioniDivorziPage.btnDomandaSeparazioneDivorzi"),
            null,
            DomandeSeparazioniDivorziPage.this,
            "color-yellow col-auto icon-fogli",
            isRichiestaVisibile);
    addOrReplace(btnDomandaSeparazioneDivorzi);

    DomandeMatrimonioPanel domandeSeparazioniDivorzi =
        new DomandeMatrimonioPanel("domandeSeparazioniDivorziPanel", matrimoni, tipologia);
    addOrReplace(domandeSeparazioniDivorzi);
  }

  private List<DatiMatrimonio> popolaSeparazioniDivorzi(
      String codiceFiscale, TipologiaRichiestaEnum tipologia) {
    log.debug("DomandeSeparazioniDivorziPage popolaSeparazioniDivorzi");

    List<DatiMatrimonio> lista = new ArrayList<DatiMatrimonio>();
    try {
      return ServiceLocator.getInstance()
          .getMatrimoniAccenture()
          .getListaDomandeRichiedenteConiugeFiltrateInBaseATipologia(codiceFiscale, tipologia);

    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaSeparazioniDivorzi: " + e.getMessage(), e);
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
