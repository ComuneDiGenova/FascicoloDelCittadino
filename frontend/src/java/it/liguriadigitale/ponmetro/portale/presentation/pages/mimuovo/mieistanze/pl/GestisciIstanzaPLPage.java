package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl;

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
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.step1.Step1IstanzaPlDatiGeneraliPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.step2.Step2IstanzaPlDatiSpecificiIstanzaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.step3.Step3IstanzaPlAltriVerbaliIstanzaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.step4.Step4IstanzaPlCaricaDocumentiIstanzaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.step5.Step5IstanzaPlConfermaDatiIstanzaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.step6.Step6IstanzaPlEsitoIstanzaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivoSummary;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaCpvHasAddress;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaCpvHasBirthPlace;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Contatto;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.EsitoOperazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Motivazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class GestisciIstanzaPLPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 7336477405169521487L;

  protected Integer index;

  protected Boolean isIntegrazioneOrDaConcludere;

  public GestisciIstanzaPLPage(Integer index, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    super();
    log.debug("costruttore1 GestisciIstanzaPLPage - DatiRichiestaIstanzaPl");
    initGestisciIstanzaPLPage(index, datiRichiestaIstanzaPl);
  }

  public GestisciIstanzaPLPage(Istanza dettagliIstanza) {
    this(dettagliIstanza, BigDecimal.ZERO);
    log.debug("costruttore2 GestisciIstanzaPLPage - BigDecimal.ZERO");
  }

  public GestisciIstanzaPLPage(DatiCompletiVerbale datiCompletiVerbale) {
    super();
    // Arrivo qui da Crea Istanza
    log.debug("costruttore3 GestisciIstanzaPLPage - DatiCompletiVerbale");
    BigDecimal codiceHermesAnagrafica =
        DatiIstanzaUtil.getCodiceHermesAnagrafica(
            datiCompletiVerbale.getDettaglioVerbale(), getUtente().getCodiceFiscaleOperatore());
    DatiRichiestaIstanzaPl datiCompletiVerbaleDiPartenzaAndGet =
        new DatiRichiestaIstanzaPl(false, false)
            .setDatiCompletiVerbaleDiPartenzaAndGet(datiCompletiVerbale);
    datiCompletiVerbaleDiPartenzaAndGet.setCodiceHermesAnagraficaUtenteCollegato(
        codiceHermesAnagrafica);
    datiCompletiVerbaleDiPartenzaAndGet.setDatiCompletiVerbale(datiCompletiVerbale);
    initGestisciIstanzaPLPage(1, datiCompletiVerbaleDiPartenzaAndGet /* , null */);
  }

  public GestisciIstanzaPLPage(Istanza dettagliIstanza, BigDecimal codiceAnagraficaHermes) {
    super();
    log.debug(
        "costruttore4 GestisciIstanzaPLPage - codiceAnagraficaHermes=" + codiceAnagraficaHermes);
    DatiRichiestaIstanzaPl datiRichiestaIstanzaPl =
        new DatiRichiestaIstanzaPl(true, "ATT".equalsIgnoreCase(dettagliIstanza.getStatoCodice()))
            .setDatiIstanzaAndGet(dettagliIstanza);
    datiRichiestaIstanzaPl.setCodiceHermesAnagraficaUtenteCollegato(codiceAnagraficaHermes);
    // preferisco farlo qui per non fare troppe chiamate lato pagina verbale
    // si presuppone una motivazione sola
    Motivazione motivazione = dettagliIstanza.getMotivazioni().get(0);
    // if( motivazione.getCodice().contains("-")) {
    // String[] parts = motivazione.getCodice().split("-");
    // String part1 = parts[0]; // 03
    // String part2 = parts[1]; // NO
    //
    // datiRichiestaIstanzaPl.setDatiMotivoSummary(getDatiDatiMotivoSummaryByCodice(part1));
    // if( "03".equalsIgnoreCase(part1)) {
    // datiRichiestaIstanzaPl.setAutodichiarazioneFurtoRitrovamento("SI".equalsIgnoreCase(part2));
    // }
    // }
    // else {
    datiRichiestaIstanzaPl.setDatiMotivoSummary(
        getDatiDatiMotivoSummaryByCodice(motivazione.getCodice()), false);
    // }
    DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale();
    Verbale verbale = dettagliIstanza.getVerbali().get(0);
    datiCompletiVerbale.setVerbale(verbale);
    datiCompletiVerbale.setDettaglioVerbale(popolaDettagliVerbale(verbale.getNumeroProtocollo()));
    // init dettaglio verbale
    datiRichiestaIstanzaPl.setDatiCompletiVerbaleDiPartenza(datiCompletiVerbale);
    datiRichiestaIstanzaPl.setDatiCompletiVerbale(datiCompletiVerbale);

    AnagraficaCpvHasBirthPlace anagraficaCpvHasBirthPlace =
        dettagliIstanza.getSoggetto().getCpvHasBirthPlace();
    datiRichiestaIstanzaPl.setNascitaComune(
        anagraficaCpvHasBirthPlace != null ? anagraficaCpvHasBirthPlace.getClvCity() : "");
    datiRichiestaIstanzaPl.setNascitaData(dettagliIstanza.getSoggetto().getCpvDateOfBirth());

    AnagraficaCpvHasAddress anagraficaCpvHasAddress =
        dettagliIstanza.getSoggetto().getCpvHasAddress();
    datiRichiestaIstanzaPl.setResidenzaComune(
        anagraficaCpvHasAddress != null ? anagraficaCpvHasAddress.getClvCity() : "");
    //		datiRichiestaIstanzaPl.setResidenzaVia(
    //				anagraficaCpvHasAddress != null ? anagraficaCpvHasAddress.getClvOfficialStreetName() :
    // "");
    datiRichiestaIstanzaPl.setResidenzaVia(
        anagraficaCpvHasAddress != null ? anagraficaCpvHasAddress.getClvFullAddress() : "");

    datiRichiestaIstanzaPl.setResidenzaNumeroCivico(
        anagraficaCpvHasAddress != null && anagraficaCpvHasAddress.getClvStreenNumberOnly() != null
            ? anagraficaCpvHasAddress
                .getClvStreenNumberOnly()
                .concat(anagraficaCpvHasAddress.getClvStreetNumberExponent())
            : "");

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

    // ora devo iniziare da 4 perche' logica da riprendere diversa
    initGestisciIstanzaPLPage(4, datiRichiestaIstanzaPl /* , null */);
  }

  public GestisciIstanzaPLPage(
      Integer index2,
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl,
      EsitoOperazione esitoOperazione) {
    super();
    log.debug("costruttore con esito GestisciIstanzaPLPage - DatiRichiestaIstanzaPl");
    initGestisciIstanzaPLPage(index, datiRichiestaIstanzaPl /* , esitoOperazione */);
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

  private void initGestisciIstanzaPLPage(
      Integer index, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    this.index = index;
    isIntegrazioneOrDaConcludere = datiRichiestaIstanzaPl.getIsIntegrazioneOrDaConcludere();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    StepPanel stepPanel = new StepPanel("stepPanel", index, getListaStep());
    addOrReplace(stepPanel);

    // TODO N step per N pannelli
    Step1IstanzaPlDatiGeneraliPanel step1IstanzaPlDatiGeneraliPanel =
        new Step1IstanzaPlDatiGeneraliPanel(
            "step1IstanzaPlDatiGeneraliPanel", index, datiRichiestaIstanzaPl);
    step1IstanzaPlDatiGeneraliPanel.setVisible(index == 1); // rimossa per
    // allineare
    // compilazione
    // con
    // conclusione /
    // integrazione
    // &&
    // !isIntegrazioneOrDaConcludere);
    addOrReplace(step1IstanzaPlDatiGeneraliPanel);

    Step2IstanzaPlDatiSpecificiIstanzaPanel step2IstanzaPlDatiSpecificiIstanzaPanel =
        new Step2IstanzaPlDatiSpecificiIstanzaPanel(
            "step2IstanzaPlDatiSpecificiIstanzaPanel", index, datiRichiestaIstanzaPl);
    step2IstanzaPlDatiSpecificiIstanzaPanel.setVisible(index == 2); // rimossa
    // per
    // allineare
    // compilazione
    // con
    // conclusione
    // /
    // integrazione
    // &&
    // !isIntegrazioneOrDaConcludere);
    addOrReplace(step2IstanzaPlDatiSpecificiIstanzaPanel);

    Step3IstanzaPlAltriVerbaliIstanzaPanel step3IstanzaPlAltriVerbaliIstanzaPanel =
        new Step3IstanzaPlAltriVerbaliIstanzaPanel(
            "step3IstanzaPlAltriVerbaliIstanzaPanel", index, datiRichiestaIstanzaPl);
    step3IstanzaPlAltriVerbaliIstanzaPanel.setVisible(index == 3); // rimossa
    // per
    // allineare
    // compilazione
    // con
    // conclusione
    // /
    // integrazione
    // &&
    // !isIntegrazioneOrDaConcludere);
    addOrReplace(step3IstanzaPlAltriVerbaliIstanzaPanel);

    Step4IstanzaPlCaricaDocumentiIstanzaPanel step4IstanzaPlCaricaDocumentiIstanzaPanel =
        new Step4IstanzaPlCaricaDocumentiIstanzaPanel(
            "step4IstanzaPlCaricaDocumentiIstanzaPanel", index, datiRichiestaIstanzaPl);
    step4IstanzaPlCaricaDocumentiIstanzaPanel.setVisible(
        index == 4); // rimossa per allineare compilazione
    // con conclusione / integrazione ||
    // (isIntegrazioneOrDaConcludere &&
    // index == 1));
    addOrReplace(step4IstanzaPlCaricaDocumentiIstanzaPanel);

    Step5IstanzaPlConfermaDatiIstanzaPanel step5IstanzaPlConfermaDatiIstanzaPanel =
        new Step5IstanzaPlConfermaDatiIstanzaPanel(
            "step5IstanzaPlConfermaDatiIstanzaPanel", index, datiRichiestaIstanzaPl);
    step5IstanzaPlConfermaDatiIstanzaPanel.setVisible(index == 5); // rimossa
    // per
    // allineare
    // compilazione
    // con
    // conclusione
    // /
    // integrazione
    // ||
    // (isIntegrazioneOrDaConcludere
    // &&
    // index
    // ==
    // 2));
    addOrReplace(step5IstanzaPlConfermaDatiIstanzaPanel);

    Step6IstanzaPlEsitoIstanzaPanel step6IstanzaPlEsitoIstanzaPanel =
        new Step6IstanzaPlEsitoIstanzaPanel(
            "step6IstanzaPlEsitoIstanzaPanel", index, datiRichiestaIstanzaPl);
    step6IstanzaPlEsitoIstanzaPanel.setVisible(index == 6); // rimossa per
    // allineare
    // compilazione
    // con
    // conclusione /
    // integrazione
    // ||
    // (isIntegrazioneOrDaConcludere
    // && index ==
    // 3));
    addOrReplace(step6IstanzaPlEsitoIstanzaPanel);
  }

  private List<StepFdC> getListaStep() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();
    Integer indicePerBuildStep = 1;

    listaStep.add(
        new StepFdC(
            getString("GestisciIstanzaPLPage.datiGenerali"),
            indicePerBuildStep++,
            true)); // rimossa
    // per
    // allineare
    // compilazione
    // con
    // conclusione
    // /
    // integrazione
    // !isIntegrazioneOrDaConcludere));
    listaStep.add(
        new StepFdC(
            getString("GestisciIstanzaPLPage.datiSpecifici"),
            indicePerBuildStep++,
            true)); // rimossa
    // per
    // allineare
    // compilazione
    // con
    // conclusione
    // /
    // integrazione
    // !isIntegrazioneOrDaConcludere));
    listaStep.add(
        new StepFdC(
            getString("GestisciIstanzaPLPage.altriVerbali"),
            indicePerBuildStep++,
            true)); // rimossa
    // per
    // allineare
    // compilazione
    // con
    // conclusione
    // /
    // integrazione
    // !isIntegrazioneOrDaConcludere));
    // rimossa per allineare compilazione con conclusione / integrazioneif
    // (isIntegrazioneOrDaConcludere)
    // indicePerBuildStep = 1;
    listaStep.add(
        new StepFdC(getString("GestisciIstanzaPLPage.caricaDocumenti"), indicePerBuildStep++));
    listaStep.add(
        new StepFdC(getString("GestisciIstanzaPLPage.confermaDati"), indicePerBuildStep++));
    listaStep.add(new StepFdC(getString("GestisciIstanzaPLPage.esito"), indicePerBuildStep++));

    return listaStep;
  }

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }
}
