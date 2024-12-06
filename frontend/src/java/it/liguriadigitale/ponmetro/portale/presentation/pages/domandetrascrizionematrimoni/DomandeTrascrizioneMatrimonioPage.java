package it.liguriadigitale.ponmetro.portale.presentation.pages.domandetrascrizionematrimoni;

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

public class DomandeTrascrizioneMatrimonioPage extends LayoutNoFeedBackPage {

  @SuppressWarnings({"rawtypes", "unchecked"})
  public DomandeTrascrizioneMatrimonioPage() {

    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    TipologiaRichiestaEnum tipologia = TipologiaRichiestaEnum.TRASCRIZIONEMATRIMONIO;

    List<DatiMatrimonio> matrimoni =
        popolaTrascrizioneMatrimonio(getUtente().getCodiceFiscaleOperatore(), tipologia);

    boolean isRichiestaVisibile = !esisteUnaRichiestaInCorso(matrimoni);

    LinkDinamicoLaddaFunzione<Object> btnDomandaTrascrizioneMatrimonio =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaTrascrizioneMatrimonio",
            new LinkDinamicoFunzioneData(
                "DomandeTrascrizioneMatrimoniPage.btnDomandaTrascrizioneMatrimonio",
                "RichiestaTrascrizioneMatrimoniPage",
                "DomandeTrascrizioneMatrimoniPage.btnDomandaTrascrizioneMatrimonio"),
            null,
            DomandeTrascrizioneMatrimonioPage.this,
            "color-yellow col-auto icon-aggiungi-preferiti",
            isRichiestaVisibile);
    addOrReplace(btnDomandaTrascrizioneMatrimonio);

    DomandeMatrimonioPanel domandeTrascrizioneMatrimoni =
        new DomandeMatrimonioPanel("domandeTrascrizioneMatrimonioPanel", matrimoni, tipologia);
    addOrReplace(domandeTrascrizioneMatrimoni);
  }

  private List<DatiMatrimonio> popolaTrascrizioneMatrimonio(
      String codiceFiscale, TipologiaRichiestaEnum tipologia) {
    log.debug("DomandeTrascrizioneMatrimoniPage popolaTrascrizioneMatrimonio");

    List<DatiMatrimonio> lista = new ArrayList<DatiMatrimonio>();
    try {
      return ServiceLocator.getInstance()
          .getMatrimoniAccenture()
          .getListaDomandeRichiedenteConiugeFiltrateInBaseATipologia(codiceFiscale, tipologia);

    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaTrascrizioneMatrimoni: " + e.getMessage(), e);
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
