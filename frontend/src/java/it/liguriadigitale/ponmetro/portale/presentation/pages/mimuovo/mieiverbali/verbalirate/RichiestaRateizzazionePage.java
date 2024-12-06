package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiCompletiVerbale;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiIstanzaUtil;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirate.panel.RichiestaRateizzazionePanel;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivoSummary;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaCpvHasAddress;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaCpvHasBirthPlace;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Contatto;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Motivazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.model.CompoundPropertyModel;

public class RichiestaRateizzazionePage extends LayoutNoFeedBackPage {

  /** */
  private static final long serialVersionUID = 1L;

  private final String CODICE_MOTIVO_RATEIZZAZIONE = "43";

  @SuppressWarnings({"rawtypes", "unchecked"})
  public RichiestaRateizzazionePage(int index) {
    super();

    log.debug("[RichiestaRateizzazionePage] Recupero il breadcrump per RichiestaRateizzazionePage");
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }

  public RichiestaRateizzazionePage(
      int index, CompoundPropertyModel<DatiCompletiVerbale> datiCompletiVerbale) {

    this(index);

    log.debug(
        "[RichiestaRateizzazionePage] Creo Istanza da Dati Completi Verbale: "
            + datiCompletiVerbale.getObject());
    CompoundPropertyModel<DatiRichiestaIstanzaPl> cDatiRichiestaIstanzaPl =
        new CompoundPropertyModel<DatiRichiestaIstanzaPl>(
            CreaIstanza(datiCompletiVerbale.getObject()));

    log.debug("[RichiestaRateizzazionePage] Creo e aggiungo RichiestaRateizzazionePanel");
    RichiestaRateizzazionePanel richiestaRatei =
        new RichiestaRateizzazionePanel("richiestaRateizzazionePanel", cDatiRichiestaIstanzaPl);
    addOrReplace(richiestaRatei);
  }

  public RichiestaRateizzazionePage(int index, Istanza dettagliIstanza) {
    this(index);

    log.debug("[RichiestaRateizzazionePage] Recupero Istanza: " + dettagliIstanza);

    log.debug("[RichiestaRateizzazionePage] Aggiungo RichiestaRateizzazionePanel");
    RichiestaRateizzazionePanel richiestaRatei =
        new RichiestaRateizzazionePanel(
            "richiestaRateizzazionePanel", popolaDatiRichiestaIstanzaPl(dettagliIstanza), index);
    addOrReplace(richiestaRatei);
  }

  public RichiestaRateizzazionePage(
      CompoundPropertyModel<DatiCompletiVerbale> datiCompletiVerbale) {
    this(1, datiCompletiVerbale);
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
    motivoSummary.setCodice(CODICE_MOTIVO_RATEIZZAZIONE);
    motivoSummary.setDescrizione("Istanza di Rateizzazione");

    // getDatiDatiMotivoSummaryByCodice(CODICE_MOTIVO_RATEIZZAZIONE);

    datiRichiestaIstanzaPl.setCodiceHermesAnagraficaUtenteCollegato(codiceHermesAnagrafica);
    datiRichiestaIstanzaPl.setDatiCompletiVerbale(datiCompletiVerbale);
    datiRichiestaIstanzaPl.setDatiMotivoSummarySelezionato(motivoSummary);
    datiRichiestaIstanzaPl.setUtente(getUtente());

    log.debug("[RichiestaRateizzazionePage] Dati Istanza: " + datiRichiestaIstanzaPl);
    return datiRichiestaIstanzaPl;
  }

  private DatiMotivoSummary getDatiDatiMotivoSummaryByCodice(String codiceHermes) {
    DatiMotivoSummary toRet = new DatiMotivoSummary();
    try {
      toRet =
          ServiceLocator.getInstance()
              .getServiziSupportoIstanzeVerbaliPL()
              .getElencoMotiviUgualiCodiceASummary(codiceHermes)
              .get(0);
    } catch (RestartResponseAtInterceptPageException | ApiException | BusinessException e) {
      log.error("getDatiDatiMotivoSummaryByCodice" + e.getMessage(), e);
      toRet = null; // cosi scoppia
    }
    return toRet;
  }

  private DettaglioVerbale popolaDettagliVerbale(String numeroProtocollo) {
    try {
      return ServiceLocator.getInstance()
          .getServiziMieiVerbali()
          .getDettaglioVerbale(numeroProtocollo, getUtente());
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  private CompoundPropertyModel<DatiRichiestaIstanzaPl> popolaDatiRichiestaIstanzaPl(
      Istanza dettagliIstanza) {
    log.debug("costruttore4 GestisciIstanzaPLPage - codiceAnagraficaHermes=" + BigDecimal.ZERO);
    DatiRichiestaIstanzaPl datiRichiestaIstanzaPl =
        new DatiRichiestaIstanzaPl(true, "ATT".equalsIgnoreCase(dettagliIstanza.getStatoCodice()))
            .setDatiIstanzaAndGet(dettagliIstanza);
    datiRichiestaIstanzaPl.setCodiceHermesAnagraficaUtenteCollegato(BigDecimal.ZERO);

    // 43 codice Hermes
    Motivazione motivazione = dettagliIstanza.getMotivazioni().get(0);
    datiRichiestaIstanzaPl.setDatiMotivoSummary(
        getDatiDatiMotivoSummaryByCodice(motivazione.getCodice()), false);

    DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale();
    Verbale verbale = dettagliIstanza.getVerbali().get(0);
    datiCompletiVerbale.setVerbale(verbale);
    datiCompletiVerbale.setDettaglioVerbale(popolaDettagliVerbale(verbale.getNumeroProtocollo()));
    // init dettaglio verbale
    datiRichiestaIstanzaPl.setDatiCompletiVerbaleDiPartenza(datiCompletiVerbale);

    AnagraficaCpvHasBirthPlace anagraficaCpvHasBirthPlace =
        dettagliIstanza.getSoggetto().getCpvHasBirthPlace();
    datiRichiestaIstanzaPl.setNascitaComune(
        anagraficaCpvHasBirthPlace != null ? anagraficaCpvHasBirthPlace.getClvCity() : "");
    datiRichiestaIstanzaPl.setNascitaData(dettagliIstanza.getSoggetto().getCpvDateOfBirth());

    AnagraficaCpvHasAddress anagraficaCpvHasAddress =
        dettagliIstanza.getSoggetto().getCpvHasAddress();
    datiRichiestaIstanzaPl.setResidenzaComune(
        anagraficaCpvHasAddress != null ? anagraficaCpvHasAddress.getClvCity() : "");
    datiRichiestaIstanzaPl.setResidenzaVia(
        anagraficaCpvHasAddress != null ? anagraficaCpvHasAddress.getClvOfficialStreetName() : "");
    datiRichiestaIstanzaPl.setResidenzaNumeroCivico(
        anagraficaCpvHasAddress != null ? anagraficaCpvHasAddress.getClvStreetNumber() : "");

    datiRichiestaIstanzaPl.setResidenzaNumeroCivico(
        anagraficaCpvHasAddress != null ? anagraficaCpvHasAddress.getClvStreetNumber() : "");
    List<Contatto> listContatto = dettagliIstanza.getSoggetto().getContatti();
    Contatto contattoTelefono = null;
    Contatto contattoEmail = null;
    if (listContatto != null && listContatto.get(0) != null) {
      for (Contatto contatto : listContatto) {
        if ("telefono".equalsIgnoreCase(contatto.getTipo())) {
          contattoTelefono = contatto;
        } else if ("email".equalsIgnoreCase(contatto.getTipo())) {
          contattoEmail = contatto;
        }
      }
    }
    datiRichiestaIstanzaPl.setRichiedenteTelefono(
        contattoTelefono != null ? contattoTelefono.getRecapito() : "");
    datiRichiestaIstanzaPl.setRichiedenteEmail(
        contattoEmail != null ? contattoEmail.getRecapito() : "");

    return new CompoundPropertyModel<DatiRichiestaIstanzaPl>(datiRichiestaIstanzaPl);
  }
}
