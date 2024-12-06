package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.bollo.PagamentoDto;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.BottoneLinkDinamicoFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.template.BolloAutoTemplate;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tassaauto.model.Bollo;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioCalcoloBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.PagamentoBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class BolloAutoPanel extends BolloAutoTemplate {

  private static final long serialVersionUID = 5973836846831319823L;

  private Veicolo veicolo;

  private Map<String, Boolean> mapVisibleCalcolaBollo;
  private Map<String, Boolean> mapVisiblePagamenti;

  private static final String ICON_AUTOVEICOLO = "color-cyan col-3 icon-autoveicolo";
  private static final String ICON_MOTOVEICOLO = "color-cyan col-3 icon-motoveicolo";

  public BolloAutoPanel(String id) {
    super(id);
    mapVisibleCalcolaBollo = new HashMap<String, Boolean>();
    mapVisiblePagamenti = new HashMap<String, Boolean>();
  }

  public BolloAutoPanel(String id, Veicolo veicolo) {
    this(id);
    this.veicolo = veicolo;
    List<Bollo> listaBollo = popolaDettagliBollo(veicolo);
    fillDati(listaBollo);
  }

  private List<Bollo> popolaDettagliBollo(Veicolo veicolo) {
    try {
      List<Bollo> listaBolli =
          ServiceLocator.getInstance().getServiziMieiMezzi().getListaDettagliBolli(veicolo);
      return listaBolli;
    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void logicaBusinessDelPannello(Object dati) throws BusinessException, ApiException {

    WebMarkupContainer emergenzaCovid = new WebMarkupContainer("emergenzaCovid");
    emergenzaCovid.setVisible(false);
    myAdd(emergenzaCovid);

    List<Bollo> lista = (List<Bollo>) dati;

    ListView<Bollo> listView =
        new ListView<Bollo>("box", lista) {

          private static final long serialVersionUID = 6367938275392230162L;

          @Override
          protected void populateItem(ListItem<Bollo> item) {
            Bollo bollo = item.getModelObject();

            item.setOutputMarkupId(true);

            mapVisibleCalcolaBollo.put(bollo.getAnno(), false);
            mapVisiblePagamenti.put(bollo.getAnno(), false);

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaVeicolo(veicolo));
            item.add(icona);

            WebMarkupContainer containerMessaggioListaPagamentiVuoto =
                new WebMarkupContainer("containerMessaggioListaPagamentiVuoto");
            containerMessaggioListaPagamentiVuoto.setOutputMarkupId(true);
            containerMessaggioListaPagamentiVuoto.setOutputMarkupPlaceholderTag(true);
            containerMessaggioListaPagamentiVuoto.setVisible(false);

            WebMarkupContainer dettagliCalcolaBolloDiv =
                new WebMarkupContainer("dettagliCalcolaBollo");
            dettagliCalcolaBolloDiv.setVisible(mapVisibleCalcolaBollo.get(bollo.getAnno()));
            dettagliCalcolaBolloDiv.setOutputMarkupId(true);

            WebMarkupContainer dettagliPagamentoBolloDiv =
                new WebMarkupContainer("dettagliPagamentoBollo");
            dettagliPagamentoBolloDiv.setVisible(mapVisiblePagamenti.get(bollo.getAnno()));
            dettagliPagamentoBolloDiv.setOutputMarkupId(true);

            Label targa = new Label("targa", bollo.getTarga());
            item.add(targa);

            Label anno = new Label("anno", bollo.getAnno());
            item.add(anno);

            Label tipoVeicolo = new Label("tipoVeicolo", bollo.getTipoVeicolo());
            tipoVeicolo.setVisible(PageUtil.isStringValid(bollo.getTipoVeicolo()));
            item.add(tipoVeicolo);

            Label scadenza = new Label("scadenza", bollo.getScadenza());
            scadenza.setVisible(PageUtil.isStringValid(bollo.getScadenza()));
            item.add(scadenza);

            Label validita = new Label("validita", bollo.getValidita());
            validita.setVisible(PageUtil.isStringValid(bollo.getValidita()));
            item.add(validita);

            LocalDate dataScadenza;
            String dataScadenzaFormattata;
            try {
              dataScadenza = ricavaScadenzaBollo(bollo);
              dataScadenzaFormattata = LocalDateUtil.getDataFormatoEuropeo(dataScadenza);
            } catch (BusinessException e) {
              log.debug("Errore durante il parsing della data, stampo valore di default:", e);
              dataScadenzaFormattata = "n/d";
            }

            String dettaglioSituazione = bollo.getRisultato();
            if (dettaglioSituazione.equalsIgnoreCase(
                "Posizione annuale non corretta ma non ancora pagabile")) {
              dettaglioSituazione =
                  "Posizione annuale pagabile a partire dal ".concat(dataScadenzaFormattata);
            }

            Label situazione = new Label("situazione", dettaglioSituazione);
            situazione.setVisible(PageUtil.isStringValid(bollo.getRisultato()));
            item.add(situazione);

            String isPagabileValue = (bollo.getIsPagabile().equals(true)) ? "Si" : "No";
            Label isPagabile = new Label("isPagabile", isPagabileValue);
            item.add(isPagabile);

            String isRuoloValue = (bollo.getHasRuolo().equals(true)) ? "Si" : "No";
            Label hasRuolo = new Label("hasRuolo", isRuoloValue);
            hasRuolo.setVisible(false);
            item.add(hasRuolo);

            AjaxLink<Void> btnGeneraAvviso =
                new AjaxLink<Void>("btnGeneraAvviso") {

                  private static final long serialVersionUID = 111734078088721025L;

                  @Override
                  public void onClick(AjaxRequestTarget arg0) {
                    log.debug("CP genera avviso click");
                  }
                };

            AjaxLink<Void> btnCalcolaLink =
                new AjaxLink<Void>("btnCalcola") {

                  private static final long serialVersionUID = 8056119305674810290L;

                  @Override
                  public void onClick(AjaxRequestTarget target) {

                    try {
                      DettaglioCalcoloBollo dettaglioCalcoloBollo =
                          ServiceLocator.getInstance()
                              .getServiziMieiMezzi()
                              .getCalcolaBollo(veicolo, bollo);

                      PagamentoDto pagamentoDto =
                          new PagamentoDto(bollo, dettaglioCalcoloBollo, veicolo);

                      @SuppressWarnings("serial")
                      BottoneLinkDinamicoFunzione<PagamentoDto> btnPagaOnline =
                          new BottoneLinkDinamicoFunzione<PagamentoDto>(
                              "btnPagaOnline",
                              new LinkDinamicoFunzioneData(
                                  "BolloAutoPanel.pagaOnline",
                                  "PagaOnlinePage",
                                  "BolloAutoPanel.pagaOnline"),
                              pagamentoDto,
                              BolloAutoPanel.this,
                              "icon-bollo-auto") {
                            @Override
                            public boolean isVisible() {

                              if (bollo.getHasRuolo()) {
                                return false;
                              } else {
                                return true;
                              }
                            }
                          };

                      Boolean mostraDivCalcolo = false;

                      Boolean containerMessaggioListaPagamentiVuotoVisibile = false;

                      if (dettaglioCalcoloBollo == null) {
                        // FALSE TUTTA LA MAPPA
                        mapVisibleCalcolaBollo.forEach(
                            (key, value) -> mapVisibleCalcolaBollo.replace(key, false));
                      } else {
                        String anno = dettaglioCalcoloBollo.getDataScadenza().getAnno();

                        mapVisibleCalcolaBollo.put(anno, false);

                        if (!mapVisibleCalcolaBollo.get(anno)) {
                          getDatiCalcolaBollo(dettagliCalcolaBolloDiv, dettaglioCalcoloBollo);
                        } else {
                          mapVisibleCalcolaBollo.replace(anno, false);
                        }
                        mostraDivCalcolo = mapVisibleCalcolaBollo.get(anno);
                      }

                      btnGeneraAvviso.setVisible(false);
                      dettagliCalcolaBolloDiv.addOrReplace(btnGeneraAvviso);

                      btnPagaOnline.setOutputMarkupId(true);
                      btnPagaOnline.setOutputMarkupPlaceholderTag(true);

                      dettagliCalcolaBolloDiv.addOrReplace(btnPagaOnline);

                      dettagliCalcolaBolloDiv.setVisible(mostraDivCalcolo);
                      item.add(dettagliCalcolaBolloDiv);

                      containerMessaggioListaPagamentiVuoto.setVisible(
                          containerMessaggioListaPagamentiVuotoVisibile);

                      target.add(item, containerMessaggioListaPagamentiVuoto);

                    } catch (BusinessException | ApiException e) {
                      log.debug("Errore durante la chiamata delle API", e);

                      gestioneErroreBusiness(e);
                    }
                  }

                  private void getDatiCalcolaBollo(
                      WebMarkupContainer dettagliCalcolaBolloDiv,
                      final DettaglioCalcoloBollo dettaglioCalcoloBollo) {
                    String anno = dettaglioCalcoloBollo.getDataScadenza().getAnno();

                    Label tipoRiduzione =
                        new Label(
                            "tipoRiduzione",
                            dettaglioCalcoloBollo.getTipoRiduzione().getDescrizione());
                    tipoRiduzione.setVisible(
                        PageUtil.isStringValid(
                            dettaglioCalcoloBollo.getTipoRiduzione().getDescrizione()));
                    dettagliCalcolaBolloDiv.addOrReplace(tipoRiduzione);

                    Label percentualeRiduzione =
                        new Label(
                            "percentualeRiduzione",
                            dettaglioCalcoloBollo.getPercentualeRiduzione());
                    percentualeRiduzione.setVisible(
                        PageUtil.isStringValid(dettaglioCalcoloBollo.getPercentualeRiduzione()));
                    dettagliCalcolaBolloDiv.addOrReplace(percentualeRiduzione);

                    Label importoTassa =
                        new Label(
                            "importoTassa",
                            dettaglioCalcoloBollo
                                .getImporto()
                                .getTassa()
                                .getImporto()
                                .concat(" ")
                                .concat(
                                    dettaglioCalcoloBollo
                                        .getImporto()
                                        .getTassa()
                                        .getDivisa()
                                        .getDescrizione()));
                    importoTassa.setVisible(
                        PageUtil.isStringValid(
                                dettaglioCalcoloBollo.getImporto().getTassa().getImporto())
                            && PageUtil.isStringValid(
                                dettaglioCalcoloBollo
                                    .getImporto()
                                    .getTassa()
                                    .getDivisa()
                                    .getDescrizione()));

                    dettagliCalcolaBolloDiv.addOrReplace(importoTassa);

                    Label importoSanzioni =
                        new Label(
                            "importoSanzioni",
                            dettaglioCalcoloBollo
                                .getImporto()
                                .getSanzioni()
                                .getImporto()
                                .concat(" ")
                                .concat(
                                    dettaglioCalcoloBollo
                                        .getImporto()
                                        .getSanzioni()
                                        .getDivisa()
                                        .getDescrizione()));
                    importoSanzioni.setVisible(
                        PageUtil.isStringValid(
                                dettaglioCalcoloBollo.getImporto().getSanzioni().getImporto())
                            && PageUtil.isStringValid(
                                dettaglioCalcoloBollo
                                    .getImporto()
                                    .getSanzioni()
                                    .getDivisa()
                                    .getDescrizione()));
                    dettagliCalcolaBolloDiv.addOrReplace(importoSanzioni);

                    Label importoInteressi =
                        new Label(
                            "importoInteressi",
                            dettaglioCalcoloBollo
                                .getImporto()
                                .getInteressi()
                                .getImporto()
                                .concat(" ")
                                .concat(
                                    dettaglioCalcoloBollo
                                        .getImporto()
                                        .getInteressi()
                                        .getDivisa()
                                        .getDescrizione()));
                    importoInteressi.setVisible(
                        PageUtil.isStringValid(
                                dettaglioCalcoloBollo.getImporto().getInteressi().getImporto())
                            && PageUtil.isStringValid(
                                dettaglioCalcoloBollo
                                    .getImporto()
                                    .getInteressi()
                                    .getDivisa()
                                    .getDescrizione()));
                    dettagliCalcolaBolloDiv.addOrReplace(importoInteressi);

                    Label importoTotale =
                        new Label(
                            "importoTotale",
                            dettaglioCalcoloBollo
                                .getImporto()
                                .getTotale()
                                .getImporto()
                                .concat(" ")
                                .concat(
                                    dettaglioCalcoloBollo
                                        .getImporto()
                                        .getTotale()
                                        .getDivisa()
                                        .getDescrizione()));
                    importoTotale.setVisible(
                        PageUtil.isStringValid(
                                dettaglioCalcoloBollo.getImporto().getTotale().getImporto())
                            && PageUtil.isStringValid(
                                dettaglioCalcoloBollo
                                    .getImporto()
                                    .getTotale()
                                    .getDivisa()
                                    .getDescrizione()));
                    dettagliCalcolaBolloDiv.addOrReplace(importoTotale);

                    Label esplicativo =
                        new Label("esplicativo", dettaglioCalcoloBollo.getEsplicativo());
                    esplicativo.setVisible(
                        PageUtil.isStringValid(dettaglioCalcoloBollo.getEsplicativo()));
                    dettagliCalcolaBolloDiv.addOrReplace(esplicativo);

                    Label ultimoGiornoUtile =
                        new Label(
                            "ultimoGiornoUtile",
                            dettaglioCalcoloBollo.getDataUltimoGiornoPagamento());
                    ultimoGiornoUtile.setVisible(
                        PageUtil.isStringValid(
                            dettaglioCalcoloBollo.getDataUltimoGiornoPagamento()));
                    dettagliCalcolaBolloDiv.addOrReplace(ultimoGiornoUtile);

                    Label ultimoGiornoUtileProrogato =
                        new Label(
                            "ultimoGiornoUtileProrogato",
                            dettaglioCalcoloBollo.getDataUltimoGiornoPagamentoProrogato());
                    ultimoGiornoUtileProrogato.setVisible(
                        PageUtil.isStringValid(
                            dettaglioCalcoloBollo.getDataUltimoGiornoPagamentoProrogato()));
                    dettagliCalcolaBolloDiv.addOrReplace(ultimoGiornoUtileProrogato);

                    mapVisibleCalcolaBollo.replace(anno, true);
                  }
                };

            AjaxLink<Void> btnPagamento =
                new AjaxLink<Void>("btnPagamento") {

                  private static final long serialVersionUID = 6225384372899862567L;

                  @Override
                  public void onClick(AjaxRequestTarget target) {

                    try {
                      List<PagamentoBollo> listaPagamenti =
                          ServiceLocator.getInstance()
                              .getServiziMieiMezzi()
                              .getPagamenti(veicolo, bollo);

                      Boolean mostraDivPagamento = false;

                      Boolean containerMessaggioListaPagamentiVuotoVisibile = false;

                      if (listaPagamenti.isEmpty()) {
                        // FALSE TUTTA LA MAPPA
                        mapVisiblePagamenti.forEach(
                            (key, value) -> mapVisiblePagamenti.replace(key, false));

                        log.debug("CP entro in caso lista pagamenti vuota");

                        containerMessaggioListaPagamentiVuotoVisibile = true;

                      } else {
                        String anno = listaPagamenti.get(0).getDataScadenza().getAnno();
                        mapVisiblePagamenti.put(anno, false);
                        if (!mapVisiblePagamenti.get(anno)) {
                          getDatiPagamentoBollo(dettagliPagamentoBolloDiv, listaPagamenti);
                        } else {
                          mapVisiblePagamenti.replace(anno, false);
                        }
                        mostraDivPagamento = mapVisiblePagamenti.get(anno);

                        containerMessaggioListaPagamentiVuotoVisibile = false;
                      }

                      dettagliPagamentoBolloDiv.setVisible(mostraDivPagamento);
                      item.add(dettagliPagamentoBolloDiv);

                      containerMessaggioListaPagamentiVuoto.setVisible(
                          containerMessaggioListaPagamentiVuotoVisibile);

                      target.add(item, containerMessaggioListaPagamentiVuoto);

                    } catch (BusinessException | ApiException e) {
                      log.debug("Errore durante la chiamata delle API", e);
                    }
                  }

                  private void getDatiPagamentoBollo(
                      WebMarkupContainer dettagliPagamentoBolloDiv,
                      List<PagamentoBollo> listaPagamenti) {

                    String anno = listaPagamenti.get(0).getDataScadenza().getAnno();

                    Label identificativoPagamento =
                        new Label(
                            "identificativoPagamento",
                            listaPagamenti.get(0).getIdPagamento().getId());
                    dettagliPagamentoBolloDiv.addOrReplace(identificativoPagamento);

                    String importoRiduzioneValue = "";
                    String tipoRiduzioneValue = "";
                    if (!listaPagamenti.get(0).getRiduzioneList().isEmpty()) {
                      importoRiduzioneValue =
                          listaPagamenti.get(0).getRiduzioneList().get(0).getImporto().getImporto()
                              + " "
                              + listaPagamenti
                                  .get(0)
                                  .getRiduzioneList()
                                  .get(0)
                                  .getImporto()
                                  .getDivisa()
                                  .getDescrizione();

                      tipoRiduzioneValue =
                          listaPagamenti
                              .get(0)
                              .getRiduzioneList()
                              .get(0)
                              .getTipoRiduzione()
                              .getDescrizione();
                    } else {
                      importoRiduzioneValue = "";
                      tipoRiduzioneValue = "";
                    }
                    Label importoRiduzione = new Label("importoRiduzione", importoRiduzioneValue);
                    importoRiduzione.setVisible(PageUtil.isStringValid(importoRiduzioneValue));
                    dettagliPagamentoBolloDiv.addOrReplace(importoRiduzione);

                    Label tipoRiduzioneVersamento =
                        new Label("tipoRiduzioneVersamento", tipoRiduzioneValue);
                    tipoRiduzioneVersamento.setVisible(PageUtil.isStringValid(tipoRiduzioneValue));
                    dettagliPagamentoBolloDiv.addOrReplace(tipoRiduzioneVersamento);

                    Label regioneBeneficiaria =
                        new Label(
                            "regioneBeneficiaria",
                            listaPagamenti.get(0).getRegioneBeneficiaria().getDescrizione());
                    regioneBeneficiaria.setVisible(
                        PageUtil.isStringValid(
                            listaPagamenti.get(0).getRegioneBeneficiaria().getDescrizione()));
                    dettagliPagamentoBolloDiv.addOrReplace(regioneBeneficiaria);

                    Label regioneIncasso =
                        new Label(
                            "regioneIncasso",
                            listaPagamenti.get(0).getRegioneIncasso().getDescrizione());
                    regioneIncasso.setVisible(
                        PageUtil.isStringValid(
                            listaPagamenti.get(0).getRegioneIncasso().getDescrizione()));
                    dettagliPagamentoBolloDiv.addOrReplace(regioneIncasso);

                    Label datiCalcolo =
                        new Label("datiCalcolo", listaPagamenti.get(0).getDatiCalcolo());
                    datiCalcolo.setVisible(
                        PageUtil.isStringValid(listaPagamenti.get(0).getDatiCalcolo()));
                    dettagliPagamentoBolloDiv.addOrReplace(datiCalcolo);

                    Label tipoIntermediario =
                        new Label(
                            "tipoIntermediario",
                            listaPagamenti.get(0).getTipoIntermediario().getDescrizione());
                    tipoIntermediario.setVisible(
                        PageUtil.isStringValid(
                            listaPagamenti.get(0).getTipoIntermediario().getDescrizione()));
                    dettagliPagamentoBolloDiv.addOrReplace(tipoIntermediario);

                    Label dataPagamento =
                        new Label(
                            "dataPagamento", listaPagamenti.get(0).getDataPagamentoEffettuato());
                    dataPagamento.setVisible(
                        PageUtil.isStringValid(listaPagamenti.get(0).getDataPagamento()));
                    dettagliPagamentoBolloDiv.addOrReplace(dataPagamento);

                    Label numeroRicevuta =
                        new Label("numeroRicevuta", listaPagamenti.get(0).getNumeroRicevuta());
                    numeroRicevuta.setVisible(
                        PageUtil.isStringValid(listaPagamenti.get(0).getNumeroRicevuta()));
                    dettagliPagamentoBolloDiv.addOrReplace(numeroRicevuta);

                    Label importoTassaVersamento =
                        new Label(
                            "importoTassaVersamento",
                            listaPagamenti.get(0).getImporto().getTassa().getImporto()
                                + " "
                                + listaPagamenti
                                    .get(0)
                                    .getImporto()
                                    .getTassa()
                                    .getDivisa()
                                    .getDescrizione());
                    importoTassaVersamento.setVisible(
                        PageUtil.isStringValid(
                                listaPagamenti.get(0).getImporto().getTassa().getImporto())
                            && PageUtil.isStringValid(
                                listaPagamenti
                                    .get(0)
                                    .getImporto()
                                    .getTassa()
                                    .getDivisa()
                                    .getDescrizione()));

                    dettagliPagamentoBolloDiv.addOrReplace(importoTassaVersamento);

                    Label importoSanzioniVersamento =
                        new Label(
                            "importoSanzioniVersamento",
                            listaPagamenti.get(0).getImporto().getSanzioni().getImporto()
                                + " "
                                + listaPagamenti
                                    .get(0)
                                    .getImporto()
                                    .getSanzioni()
                                    .getDivisa()
                                    .getDescrizione());
                    importoSanzioniVersamento.setVisible(
                        PageUtil.isStringValid(
                                listaPagamenti.get(0).getImporto().getSanzioni().getImporto())
                            && PageUtil.isStringValid(
                                listaPagamenti
                                    .get(0)
                                    .getImporto()
                                    .getSanzioni()
                                    .getDivisa()
                                    .getDescrizione()));
                    dettagliPagamentoBolloDiv.addOrReplace(importoSanzioniVersamento);

                    Label importoInteressiVersamento =
                        new Label(
                            "importoInteressiVersamento",
                            listaPagamenti.get(0).getImporto().getInteressi().getImporto()
                                + " "
                                + listaPagamenti
                                    .get(0)
                                    .getImporto()
                                    .getInteressi()
                                    .getDivisa()
                                    .getDescrizione());
                    importoInteressiVersamento.setVisible(
                        PageUtil.isStringValid(
                                listaPagamenti.get(0).getImporto().getInteressi().getImporto())
                            && PageUtil.isStringValid(
                                listaPagamenti
                                    .get(0)
                                    .getImporto()
                                    .getInteressi()
                                    .getDivisa()
                                    .getDescrizione()));
                    dettagliPagamentoBolloDiv.addOrReplace(importoInteressiVersamento);

                    Label importoTotaleVersamento =
                        new Label(
                            "importoTotaleVersamento",
                            listaPagamenti.get(0).getImporto().getTotale().getImporto()
                                + " "
                                + listaPagamenti
                                    .get(0)
                                    .getImporto()
                                    .getTotale()
                                    .getDivisa()
                                    .getDescrizione());
                    importoTotaleVersamento.setVisible(
                        PageUtil.isStringValid(
                                listaPagamenti.get(0).getImporto().getTotale().getImporto())
                            && PageUtil.isStringValid(
                                listaPagamenti
                                    .get(0)
                                    .getImporto()
                                    .getTotale()
                                    .getDivisa()
                                    .getDescrizione()));
                    dettagliPagamentoBolloDiv.addOrReplace(importoTotaleVersamento);

                    Label dataScadenza =
                        new Label(
                            "dataScadenza",
                            listaPagamenti.get(0).getDataScadenza().getMese().getDescrizione()
                                + " "
                                + listaPagamenti.get(0).getDataScadenza().getAnno());
                    dataScadenza.setVisible(
                        PageUtil.isStringValid(
                            listaPagamenti.get(0).getDataScadenza().getMese().getDescrizione()));
                    dettagliPagamentoBolloDiv.addOrReplace(dataScadenza);

                    Label validita = new Label("validita", listaPagamenti.get(0).getValidita());
                    validita.setVisible(listaPagamenti.get(0).getValidita() != null);
                    dettagliPagamentoBolloDiv.addOrReplace(validita);

                    Label messaggioPiuVersamenti =
                        new Label(
                            "messaggioPiuVersamenti",
                            getString("BolloAutoPanel.messaggioPiuVersamenti"));
                    messaggioPiuVersamenti.setVisible(listaPagamenti.size() > 1);
                    dettagliPagamentoBolloDiv.addOrReplace(messaggioPiuVersamenti);

                    mapVisiblePagamenti.replace(anno, true);
                  }
                };

            if (bollo.getIsCalcolabile() && bollo.getIsPagabile() && !bollo.getIsInsufficiente()) {
              btnCalcolaLink.setVisible(true);
            } else {
              btnCalcolaLink.setVisible(false);
            }

            btnCalcolaLink.setOutputMarkupId(true);
            item.add(btnCalcolaLink);

            btnPagamento.setOutputMarkupId(true);
            if ((!bollo.getIsPagabile() && bollo.getIsCalcolabile())
                || (bollo.getIsInsufficiente())) {
              btnPagamento.setVisible(true);
            } else {
              btnPagamento.setVisible(false);
            }
            item.add(btnPagamento);

            item.add(dettagliCalcolaBolloDiv);
            item.add(dettagliPagamentoBolloDiv);

            item.addOrReplace(containerMessaggioListaPagamentiVuoto);
          }

          private AttributeAppender getCssIconaVeicolo(Veicolo veicolo) {
            String css = "";

            if (veicolo.getTipoVeicolo().equalsIgnoreCase("motoveicolo")) {
              css = ICON_MOTOVEICOLO;
            } else if (veicolo.getTipoVeicolo().equalsIgnoreCase("autoveicolo")) {
              css = ICON_AUTOVEICOLO;
            }
            return new AttributeAppender("class", " " + css);
          }
        };
    myAdd(listView);
  }

  @Override
  protected void gestioneErroreBusiness(Exception e) {
    super.gestioneErroreBusiness(e);
  }

  private LocalDate ricavaScadenzaBollo(Bollo bollo) throws BusinessException {

    LocalDate dataScadenzaBollo =
        LocalDateUtil.convertiDaFormatoEuropeo("01/" + bollo.getScadenza());
    LocalDate primoGiornoUtile = dataScadenzaBollo.plusMonths(1).minusYears(1);
    return primoGiornoUtile;
  }
}
