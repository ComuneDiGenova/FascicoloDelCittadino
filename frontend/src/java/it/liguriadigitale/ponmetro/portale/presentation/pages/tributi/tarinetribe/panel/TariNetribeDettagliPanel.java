package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.EsitoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiFileAllegatoNetribe;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.RataDaPagareTariNetribe;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants.TIPO_DEPLOY;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.ImportoEuroLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.Allegato;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import it.liguriadigitale.ponmetro.tarinetribe.model.PagamentoAvviso;
import it.liguriadigitale.ponmetro.tarinetribe.model.Rata;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIResult;
import it.liguriadigitale.ponmetro.tarinetribe.model.TestataAvviso;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.StringResourceModel;

public class TariNetribeDettagliPanel extends BasePanel {

  private static final long serialVersionUID = -1560822895656066465L;

  public TariNetribeDettagliPanel(String id) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {
    TARIResult datiTari = (TARIResult) dati;

    TestataAvviso testataAvviso = datiTari.getTestataAvviso();
    PagamentoAvviso datiPagamento = datiTari.getDatiPagamento();
    Allegato allegato = datiTari.getAllegato();

    NotEmptyLabel tipologia =
        new NotEmptyLabel(
            "tipologia",
            new StringResourceModel("TariNetribeDettagliPanel.tipologia", this)
                .setParameters(
                    datiTari.getTipologia(),
                    datiTari.getTipologiaUtenza(),
                    String.valueOf(datiPagamento.getAnnoRiferimento())));
    addOrReplace(tipologia);

    addOrReplace(
        new AmtCardLabel<>(
            "codiceIntestatario",
            testataAvviso.getCodiceIntestatario(),
            TariNetribeDettagliPanel.this));

    addOrReplace(
        new AmtCardLabel<>("idAvviso", testataAvviso.getIdAvviso(), TariNetribeDettagliPanel.this));

    addOrReplace(
        new AmtCardLabel<>(
            "annoDeliberazione",
            datiPagamento.getAnnoRiferimento(),
            TariNetribeDettagliPanel.this));

    String idFile = "";
    if (LabelFdCUtil.checkIfNotNull(allegato)) {
      idFile = allegato.getIdFile();
    }
    @SuppressWarnings("rawtypes")
    BottoneAJAXDownloadWithError btnScaricaBolletta = creaDownloadBolletta(idFile);
    addOrReplace(btnScaricaBolletta);

    String fraseInfoMsgAnnullato =
        ServiceLocator.getInstance()
            .getServiziTariNetribe()
            .getValoreDaDb("TARI_NETRIBE_MSG_ANNULLATO");
    Label infoIsAnnullato = new Label("infoIsAnnullato", fraseInfoMsgAnnullato);
    infoIsAnnullato.setOutputMarkupId(true);
    infoIsAnnullato.setOutputMarkupPlaceholderTag(true);
    infoIsAnnullato.setVisible(
        PageUtil.isStringValid(datiTari.getIsAnnullato())
            && datiTari.getIsAnnullato().equalsIgnoreCase("1"));
    addOrReplace(infoIsAnnullato);

    String fraseInfoMsgIsScrittoARuolo =
        ServiceLocator.getInstance()
            .getServiziTariNetribe()
            .getValoreDaDb("TARI_NETRIBE_MSG_ISCRITTO_A_RUOLO");
    Label infoIsIscrittoARuolo = new Label("infoIsIscrittoARuolo", fraseInfoMsgIsScrittoARuolo);
    infoIsIscrittoARuolo.setOutputMarkupId(true);
    infoIsIscrittoARuolo.setOutputMarkupPlaceholderTag(true);
    infoIsIscrittoARuolo.setVisible(
        PageUtil.isStringValid(datiTari.getIsIscrittoARuolo())
            && datiTari.getIsIscrittoARuolo().equalsIgnoreCase("1"));
    addOrReplace(infoIsIscrittoARuolo);

    NotEmptyLabel tipologiaNegliImporti =
        new NotEmptyLabel(
            "tipologiaNegliImporti",
            new StringResourceModel("TariNetribeDettagliPanel.tipologiaNegliImporti", this)
                .setParameters(
                    datiTari.getTipologia(),
                    datiTari.getTipologiaUtenza(),
                    String.valueOf(datiPagamento.getAnnoRiferimento())));
    addOrReplace(tipologiaNegliImporti);

    ImportoEuroLabel totaleImporto =
        new ImportoEuroLabel("totaleImporto", datiPagamento.getTotaleImporto());
    totaleImporto.setVisible(LabelFdCUtil.checkIfNotNull(datiPagamento));
    addOrReplace(totaleImporto);

    ImportoEuroLabel totalePagamenti =
        new ImportoEuroLabel("totalePagamenti", datiPagamento.getTotalePagamenti());
    totalePagamenti.setVisible(LabelFdCUtil.checkIfNotNull(datiPagamento));
    addOrReplace(totalePagamenti);

    ImportoEuroLabel saldo = new ImportoEuroLabel("saldo", datiPagamento.getSaldo());
    saldo.setVisible(LabelFdCUtil.checkIfNotNull(datiPagamento));
    addOrReplace(saldo);

    Rata rataUnica =
        ServiceLocator.getInstance()
            .getServiziTariNetribe()
            .getRataUnica(datiPagamento.getRateizzazione(), testataAvviso.getIuvTotaleDocumento());

    WebMarkupContainer containerRataUnica = new WebMarkupContainer("containerRataUnica");
    containerRataUnica.setOutputMarkupId(true);
    containerRataUnica.setOutputMarkupPlaceholderTag(true);

    NotEmptyLabel rataUnicaLabel =
        new NotEmptyLabel(
            "rataUnica",
            new StringResourceModel("TariNetribeDettagliPanel.numeroRata", this)
                .setParameters("Unica"));
    containerRataUnica.addOrReplace(rataUnicaLabel);

    NotEmptyLabel scadenzaRataUnica =
        new NotEmptyLabel(
            "scadenzaRataUnica",
            new StringResourceModel("TariNetribeDettagliPanel.scadenzaRata", this)
                .setParameters(
                    LocalDateUtil.getDataFormatoEuropeo(rataUnica.getDataScadenzaRata())));
    containerRataUnica.addOrReplace(scadenzaRataUnica);

    NotEmptyLabel importoRataUnica =
        new NotEmptyLabel(
            "importoRataUnica",
            new StringResourceModel("TariNetribeDettagliPanel.importoRata", this)
                .setParameters(rataUnica.getImportoRata()));
    containerRataUnica.addOrReplace(importoRataUnica);

    NotEmptyLabel iuvRataUnica =
        new NotEmptyLabel(
            "iuvRataUnica",
            new StringResourceModel("TariNetribeDettagliPanel.iuvRata", this)
                .setParameters(rataUnica.getIuvRata()));
    iuvRataUnica.setVisible(PageUtil.isStringValid(rataUnica.getIuvRata()));
    containerRataUnica.addOrReplace(iuvRataUnica);

    Double importoRataUnicaIncassatoMIP = null;
    Double importoRataUnicaDaPagareMIP = null;
    EsitoPagamento esitoPagamentoRataUnicaMIP = null;
    String idServizioRataUnicaMip = "";

    Debito debitoRataUnicaMIP = null;

    if (PageUtil.isStringValid(rataUnica.getIuvRata())) {
      try {
        debitoRataUnicaMIP =
            ServiceLocator.getInstance()
                .getServiziTariNetribe()
                .getDebitoMIPDaIUV(getUtente().getCodiceFiscaleOperatore(), rataUnica.getIuvRata());

        log.debug("CP debitoRataUnicaMIP = " + debitoRataUnicaMIP);

        if (LabelFdCUtil.checkIfNotNull(debitoRataUnicaMIP)) {
          esitoPagamentoRataUnicaMIP = debitoRataUnicaMIP.getEsitoPagamento();

          if (LabelFdCUtil.checkIfNotNull(debitoRataUnicaMIP.getEsitoPagamento())
              && debitoRataUnicaMIP.getEsitoPagamento().equals(EsitoPagamento.OK)) {
            importoRataUnicaIncassatoMIP = debitoRataUnicaMIP.getImportoDaPagare();
          } else {
            importoRataUnicaIncassatoMIP = debitoRataUnicaMIP.getImportoPagato();
          }

          importoRataUnicaDaPagareMIP = debitoRataUnicaMIP.getImportoDaPagare();
          idServizioRataUnicaMip = debitoRataUnicaMIP.getServizio();
        }
      } catch (BusinessException | ApiException e) {
        log.error("Errore MIP e Netribe: " + e.getMessage());
      }
    }
    NotEmptyLabel importoIncassatoRataUnicaMIP =
        new NotEmptyLabel(
            "importoIncassatoRataUnicaMIP",
            new StringResourceModel("TariNetribeDettagliPanel.importoIncassatoRataMIP", this)
                .setParameters(importoRataUnicaIncassatoMIP));
    importoIncassatoRataUnicaMIP.setVisible(
        PageUtil.isStringValid(rataUnica.getIuvRata())
            && LabelFdCUtil.checkIfNotNull(importoRataUnicaIncassatoMIP));
    containerRataUnica.addOrReplace(importoIncassatoRataUnicaMIP);

    @SuppressWarnings("rawtypes")
    BottoneAJAXDownloadWithError btnRicevutaRataUnicaMIP =
        creaDownloadRicevuta(
            rataUnica.getIuvRata(), esitoPagamentoRataUnicaMIP, "btnRicevutaRataUnicaMIP");
    containerRataUnica.addOrReplace(btnRicevutaRataUnicaMIP);

    RataDaPagareTariNetribe rataUnicaDaPagare = new RataDaPagareTariNetribe();
    rataUnicaDaPagare.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
    rataUnicaDaPagare.setEsitoPagamento(esitoPagamentoRataUnicaMIP);
    rataUnicaDaPagare.setIdServizio(idServizioRataUnicaMip);
    rataUnicaDaPagare.setImporto(importoRataUnicaDaPagareMIP);
    rataUnicaDaPagare.setIuv(rataUnica.getIuvRata());

    if ((PageUtil.isStringValid(rataUnica.getIuvRata())
            && LabelFdCUtil.checkIfNotNull(debitoRataUnicaMIP))
        && ((LabelFdCUtil.checkIfNotNull(esitoPagamentoRataUnicaMIP)
                && !esitoPagamentoRataUnicaMIP.equals(EsitoPagamento.OK))
            || (LabelFdCUtil.checkIfNull(esitoPagamentoRataUnicaMIP)))) {
      containerRataUnica.addOrReplace(creaBtnPaga(rataUnicaDaPagare, "btnPagaRataUnica"));
    } else {
      containerRataUnica.addOrReplace(new WebMarkupContainer("btnPagaRataUnica").setVisible(false));
    }

    addOrReplace(containerRataUnica);

    Map<LocalDate, List<Rata>> rateRaggruppatePerData =
        ServiceLocator.getInstance()
            .getServiziTariNetribe()
            .getListaRateRaggruppatePerDataScadenza(datiPagamento.getRateizzazione());

    List<LocalDate> listaRatePerDataScadenza = new ArrayList<>(rateRaggruppatePerData.keySet());
    Collections.sort(listaRatePerDataScadenza);

    Integer numeroRateTotali = rateRaggruppatePerData.entrySet().size();

    NotEmptyLabel totaleRate =
        new NotEmptyLabel(
            "totaleRate",
            new StringResourceModel("TariNetribeDettagliPanel.numeroRateTotali", this)
                .setParameters(numeroRateTotali));

    totaleRate.setVisible(
        LabelFdCUtil.checkIfNotNull(listaRatePerDataScadenza)
            && listaRatePerDataScadenza.size() > 1);

    addOrReplace(totaleRate);

    ListView<LocalDate> boxRate =
        new ListView<LocalDate>("boxRate", listaRatePerDataScadenza) {

          private static final long serialVersionUID = -7503046220067032276L;

          @Override
          protected void populateItem(ListItem<LocalDate> itemRata) {
            final LocalDate dataRata = itemRata.getModelObject();

            List<Rata> listaRate = rateRaggruppatePerData.get(dataRata);

            Integer numeroRata = itemRata.getIndex() + 1;

            Double importoTotaleRata =
                listaRate.stream()
                    .map(elem -> elem.getImportoRata())
                    .collect(Collectors.summingDouble(Double::doubleValue));

            String iuvRata = listaRate.get(0).getIuvRata();

            LocalDate dataScadenzaRata = listaRate.get(0).getDataScadenzaRata();
            String scadenza = LocalDateUtil.getDataFormatoEuropeo(dataScadenzaRata);

            NotEmptyLabel numeroRataLabel =
                new NotEmptyLabel(
                    "numeroRata",
                    new StringResourceModel("TariNetribeDettagliPanel.numeroRata", this)
                        .setParameters(numeroRata));
            itemRata.addOrReplace(numeroRataLabel);

            NotEmptyLabel scadenzaRata =
                new NotEmptyLabel(
                    "scadenzaRata",
                    new StringResourceModel("TariNetribeDettagliPanel.scadenzaRata", this)
                        .setParameters(scadenza));
            itemRata.addOrReplace(scadenzaRata);

            NotEmptyLabel importoRata =
                new NotEmptyLabel(
                    "importoRata",
                    new StringResourceModel("TariNetribeDettagliPanel.importoRata", this)
                        .setParameters(importoTotaleRata));
            itemRata.addOrReplace(importoRata);

            NotEmptyLabel iuvRataLabel =
                new NotEmptyLabel(
                    "iuvRata",
                    new StringResourceModel("TariNetribeDettagliPanel.iuvRata", this)
                        .setParameters(iuvRata));
            iuvRataLabel.setVisible(PageUtil.isStringValid(iuvRata));
            itemRata.addOrReplace(iuvRataLabel);

            Double importoIncassatoMIP = null;
            Double importoDaPagareMIP = null;
            EsitoPagamento esitoPagamentoMIP = null;
            String idServizioMip = "";

            Debito debitoMIP = null;

            if (PageUtil.isStringValid(iuvRata)) {
              try {
                debitoMIP =
                    ServiceLocator.getInstance()
                        .getServiziTariNetribe()
                        .getDebitoMIPDaIUV(getUtente().getCodiceFiscaleOperatore(), iuvRata);

                log.debug("CP debitoMIP = " + debitoMIP);

                if (LabelFdCUtil.checkIfNotNull(debitoMIP)) {
                  esitoPagamentoMIP = debitoMIP.getEsitoPagamento();

                  if (LabelFdCUtil.checkIfNotNull(debitoMIP.getEsitoPagamento())
                      && debitoMIP.getEsitoPagamento().equals(EsitoPagamento.OK)) {
                    importoIncassatoMIP = debitoMIP.getImportoDaPagare();
                  } else {
                    importoIncassatoMIP = debitoMIP.getImportoPagato();
                  }

                  importoDaPagareMIP = debitoMIP.getImportoDaPagare();
                  idServizioMip = debitoMIP.getServizio();
                }
              } catch (BusinessException | ApiException e) {
                log.error("Errore MIP e Netribe: " + e.getMessage());
              }
            }
            NotEmptyLabel importoIncassatoRataMIP =
                new NotEmptyLabel(
                    "importoIncassatoRataMIP",
                    new StringResourceModel(
                            "TariNetribeDettagliPanel.importoIncassatoRataMIP", this)
                        .setParameters(importoIncassatoMIP));
            importoIncassatoRataMIP.setVisible(
                PageUtil.isStringValid(iuvRata)
                    && LabelFdCUtil.checkIfNotNull(importoIncassatoMIP));
            itemRata.addOrReplace(importoIncassatoRataMIP);

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnRicevutaRataMIP =
                creaDownloadRicevuta(iuvRata, esitoPagamentoMIP, "btnRicevutaRataMIP");
            itemRata.addOrReplace(btnRicevutaRataMIP);

            RataDaPagareTariNetribe rataDaPagare = new RataDaPagareTariNetribe();
            rataDaPagare.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
            rataDaPagare.setEsitoPagamento(esitoPagamentoMIP);
            rataDaPagare.setIdServizio(idServizioMip);
            rataDaPagare.setImporto(importoDaPagareMIP);
            rataDaPagare.setIuv(iuvRata);

            if ((PageUtil.isStringValid(iuvRata) && LabelFdCUtil.checkIfNotNull(debitoMIP))
                && ((LabelFdCUtil.checkIfNotNull(esitoPagamentoMIP)
                        && !esitoPagamentoMIP.equals(EsitoPagamento.OK))
                    || (LabelFdCUtil.checkIfNull(esitoPagamentoMIP)))) {
              itemRata.addOrReplace(creaBtnPaga(rataDaPagare, "btnPagaRata"));
            } else {
              itemRata.addOrReplace(new WebMarkupContainer("btnPagaRata").setVisible(false));
            }
          }
        };

    boxRate.setVisible(
        LabelFdCUtil.checkIfNotNull(datiPagamento.getRateizzazione())
            && datiPagamento.getRateizzazione().size() > 1);

    addOrReplace(boxRate);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadBolletta(String identificativoDocumento) {
    BottoneAJAXDownloadWithError btnScaricaBolletta =
        new BottoneAJAXDownloadWithError("btnScaricaBolletta", TariNetribeDettagliPanel.this) {

          private static final long serialVersionUID = 4364550638100670558L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            try {

              DatiFileAllegatoNetribe datiFileAllegato =
                  ServiceLocator.getInstance()
                      .getServiziTariNetribe()
                      .datiFileAllegatoNetribe(identificativoDocumento);

              // FileAllegato documento =
              // ServiceLocator.getInstance().getServiziTariNetribe().getPdfNetribe(identificativoDocumento);
              FileDaScaricare fileDaScaricare = new FileDaScaricare();

              if (datiFileAllegato.isScaricato()) {
                FileAllegato documento = datiFileAllegato.getFileAllegatoNetribe();

                fileDaScaricare.setFileBytes(documento.getFile());
                fileDaScaricare.setFileName(documento.getNomeFile());
                fileDaScaricare.setEsitoOK(datiFileAllegato.isScaricato());
              } else {
                fileDaScaricare.setMessaggioErrore(datiFileAllegato.getMessaggioErrore());
                fileDaScaricare.setEsitoOK(datiFileAllegato.isScaricato());
              }

              return fileDaScaricare;

            } catch (ApiException | BusinessException | IOException e) {
              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setMessaggioErrore("Errore durante download file");
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }
        };

    btnScaricaBolletta.setVisibileDopoDownload(true);

    btnScaricaBolletta.setVisible(PageUtil.isStringValid(identificativoDocumento));

    return btnScaricaBolletta;
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadRicevuta(
      String iuvRata, EsitoPagamento esitoPagamento, String wickteId) {
    BottoneAJAXDownloadWithError btnRicevutaMIP =
        new BottoneAJAXDownloadWithError(wickteId, TariNetribeDettagliPanel.this) {

          private static final long serialVersionUID = 357540332322474771L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            try {

              RicevutaPagamento ricevuta =
                  ServiceLocator.getInstance()
                      .getServiziTariNetribe()
                      .getRicevutaMipDaIUV(getUtente(), iuvRata);

              FileDaScaricare fileDaScaricare = new FileDaScaricare();

              if (LabelFdCUtil.checkIfNotNull(ricevuta)
                  && LabelFdCUtil.checkIfNotNull(ricevuta.getPdfRicevuta())) {
                fileDaScaricare.setFileBytes(ricevuta.getPdfRicevuta().getFile());
                fileDaScaricare.setFileName(ricevuta.getPdfRicevuta().getNomeFile());
                fileDaScaricare.setEsitoOK(true);

              } else {
                fileDaScaricare.setMessaggioErrore("Errore durante download file");
                fileDaScaricare.setEsitoOK(false);
              }

              return fileDaScaricare;

            } catch (ApiException | BusinessException e) {
              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setMessaggioErrore("Errore durante download file");
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }

          @Override
          protected String creaLabelEtichetta(Component pannello, String id) {
            return "Scarica ricevuta";
          }
        };

    btnRicevutaMIP.setVisibileDopoDownload(true);

    btnRicevutaMIP.setVisible(
        PageUtil.isStringValid(iuvRata)
            && LabelFdCUtil.checkIfNotNull(esitoPagamento)
            && esitoPagamento.equals(EsitoPagamento.OK));

    return btnRicevutaMIP;
  }

  private AbstractLink creaBtnPaga(RataDaPagareTariNetribe rataDaPagare, String wicketId) {
    AbstractLink linkPaga = null;

    if (LabelFdCUtil.checkIfNotNull(rataDaPagare)
        && PageUtil.isStringValid(rataDaPagare.getIuv())
        && ((LabelFdCUtil.checkIfNotNull(rataDaPagare.getEsitoPagamento())
                && !rataDaPagare.getEsitoPagamento().equals(EsitoPagamento.OK))
            || (LabelFdCUtil.checkIfNull(rataDaPagare.getEsitoPagamento())))) {

      linkPaga =
          ServiceLocator.getInstance()
              .getServiziTariNetribe()
              .creaBtnPaga(rataDaPagare, getUtente(), wicketId);
    }

    String btnPagaDB =
        ServiceLocator.getInstance().getServiziTariNetribe().getValoreDaDb("TARI_NETRIBE_BTN_PAGA");
    boolean btnPagaVisibile = btnPagaDB.equalsIgnoreCase("ENABLE") ? true : false;

    // per far fare i test a serena
    if (Constants.DEPLOY != TIPO_DEPLOY.ESERCIZIO) {
      btnPagaVisibile = true;
    }
    //

    log.debug("CP btnPagaVisibile = " + btnPagaVisibile);

    linkPaga.setVisible(btnPagaVisibile);

    return linkPaga;
  }
}
