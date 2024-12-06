package it.liguriadigitale.ponmetro.portale.presentation.pages.segnalazionedanni;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
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
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.segnalazionedanni.panel.DomandeSegnalazioneDanniBeniPrivatiPanel;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsAccountIdProcedimentiRGet200Response;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DomandeSegnalazioneDanniBeniPrivatiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public DomandeSegnalazioneDanniBeniPrivatiPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessagi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessagi);

    LinkDinamicoLaddaFunzione<Object> btnDomandaSegnalazioneDanni =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaSegnalazioneDanni",
            new LinkDinamicoFunzioneData(
                "DomandeSegnalazioneDanniBeniPrivatiPage.btnDomandaSegnalazioneDanni",
                "RichiestaSegnalazioneDanniBeniPrivatiPage",
                "DomandeSegnalazioneDanniBeniPrivatiPage.btnDomandaSegnalazioneDanni"),
            null,
            DomandeSegnalazioneDanniBeniPrivatiPage.this,
            "color-fc-pink col-auto icon-fogli",
            true);
    addOrReplace(btnDomandaSegnalazioneDanni);

    TipologiaRichiestaEnum tipologia = TipologiaRichiestaEnum.SEGNALAZIONE_DANNI_BENI_PRIVATI;

    DomandeSegnalazioneDanniBeniPrivatiPanel domandeSegnalazioneDanniBeniPrivatiPanel =
        new DomandeSegnalazioneDanniBeniPrivatiPanel(
            "domandeSegnalazioneDanniBeniPrivatiPanel",
            popolaSegnalazioneDanniBeniPrivati(getUtente().getCodiceFiscaleOperatore(), tipologia),
            tipologia);

    addOrReplace(domandeSegnalazioneDanniBeniPrivatiPanel);
  }

  private List<ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner>
      popolaSegnalazioneDanniBeniPrivati(String codiceFiscale, TipologiaRichiestaEnum tipologia) {

    List<ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner> listaRecords =
        new ArrayList<>();

    try {

      ServicesDataV580SobjectsAccountIdProcedimentiRGet200Response dati =
          ServiceLocator.getInstance()
              .getSegnalazioneDanniBeniPrivati()
              .getListSegnalazioneDanniBeniPrivati(
                  codiceFiscale, TipologiaRichiestaEnum.SEGNALAZIONE_DANNI_BENI_PRIVATI);

      if (dati != null && dati.getRecords() != null) {
        listaRecords =
            dati.getRecords().stream()
                .filter(
                    t ->
                        t.getTipologiaPraticaC()
                            .equalsIgnoreCase("Segnalazione danni per eventi calamitosi"))
                .sorted(
                    Comparator.comparing(
                            ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner
                                ::getDataEventoCalamitosoC)
                        .reversed())
                .collect(Collectors.toList());
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popola segnalazione danni beni privati: " + e.getMessage(), e);
    }

    return listaRecords;
  }
}
