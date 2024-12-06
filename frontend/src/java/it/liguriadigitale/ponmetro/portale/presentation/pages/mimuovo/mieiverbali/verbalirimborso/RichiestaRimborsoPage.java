package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirimborso;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiCompletiVerbale;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiIstanzaUtil;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirimborso.form.RimborsoVerbale;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirimborso.panel.RichiestaRimborsoPanel;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivoSummary;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import java.math.BigDecimal;
import org.apache.wicket.model.CompoundPropertyModel;

public class RichiestaRimborsoPage extends LayoutNoFeedBackPage {

  /** */
  private static final long serialVersionUID = 1L;

  private final String CODICE_MOTIVO_RIMBORSO = "42";

  @SuppressWarnings({"rawtypes", "unchecked"})
  public RichiestaRimborsoPage(int index) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }

  public RichiestaRimborsoPage(
      int index, CompoundPropertyModel<DatiCompletiVerbale> datiCompletiVerbale) {

    this(index);

    DatiRichiestaIstanzaPl datiRichiestaIstanzaPl = CreaIstanza(datiCompletiVerbale.getObject());

    RichiestaRimborsoPanel panel =
        new RichiestaRimborsoPanel(
            "richiestaRimborsoPanel", new RimborsoVerbale(), datiRichiestaIstanzaPl);
    addOrReplace(panel);
  }

  public RichiestaRimborsoPage(CompoundPropertyModel<DatiCompletiVerbale> datiCompletiVerbale) {
    this(1, datiCompletiVerbale);
  }

  public RichiestaRimborsoPage(int index, Istanza dettagliIstanza) {
    this(index);
  }

  private DatiRichiestaIstanzaPl CreaIstanza(DatiCompletiVerbale datiCompletiVerbale) {
    log.debug(
        "[RichiestaRateizzazionePage] Crea Istanza Rateizzazione verbale "
            + datiCompletiVerbale.getDettaglioVerbale().getNumeroAvviso());
    log.debug("[RichiestaRateizzazionePage] Dati Completi Verbali: " + datiCompletiVerbale);

    BigDecimal codiceHermesAnagrafica =
        DatiIstanzaUtil.getCodiceHermesAnagrafica(
            datiCompletiVerbale.getDettaglioVerbale(), getUtente().getCodiceFiscaleOperatore());

    DatiRichiestaIstanzaPl datiRichiestaIstanzaPl =
        new DatiRichiestaIstanzaPl(false, false)
            .setDatiCompletiVerbaleDiPartenzaAndGet(datiCompletiVerbale);

    // Si può recuperare da qualche parte ?
    DatiMotivoSummary motivoSummary = new DatiMotivoSummary();
    motivoSummary.setCodice(CODICE_MOTIVO_RIMBORSO);
    motivoSummary.setDescrizione("Istanza di Rateizzazione");

    // getDatiDatiMotivoSummaryByCodice(CODICE_MOTIVO_RATEIZZAZIONE);

    datiRichiestaIstanzaPl.setCodiceHermesAnagraficaUtenteCollegato(codiceHermesAnagrafica);
    datiRichiestaIstanzaPl.setDatiCompletiVerbale(datiCompletiVerbale);
    datiRichiestaIstanzaPl.setDatiMotivoSummarySelezionato(motivoSummary);
    datiRichiestaIstanzaPl.setUtente(getUtente());

    // Importo minore di zero nel caso di rimborso. Lo metto positivo
    datiCompletiVerbale
        .getDettaglioVerbale()
        .setImporto(
            new BigDecimal(
                Math.abs(datiCompletiVerbale.getDettaglioVerbale().getImporto().doubleValue())));

    log.debug("[RichiestaRateizzazionePage] Dati Istanza: " + datiRichiestaIstanzaPl);
    return datiRichiestaIstanzaPl;
  }
}
