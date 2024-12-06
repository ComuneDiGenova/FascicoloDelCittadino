package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiCompletiVerbale;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateTimeUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ArticoloViolato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale.StatoPagamentoEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ImportoPagato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Motivazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class Step3IstanzaPlAltriVerbaliIstanzaForm
    extends AbstracFrameworkForm<DatiRichiestaIstanzaPl> {

  private static final long serialVersionUID = -7349674609304452360L;
  protected PaginazioneFascicoloPanel paginazioneFascicolo;
  Component componentToRefresh;

  private Integer index;

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public Step3IstanzaPlAltriVerbaliIstanzaForm(
      String id, DatiRichiestaIstanzaPl model, Integer index) {
    super(id, model);

    log.debug("CP Step3IstanzaPlAltriVerbaliIstanzaForm= " + index);
    setIndex(index);
    addElementiForm();
  }

  public void setComponentToRefresh(Component componentToRefresh) {
    this.componentToRefresh = componentToRefresh;
  }

  @Override
  public void addElementiForm() {
    DatiRichiestaIstanzaPl datiRichiestaIstanza = getModelObject();
    List<DatiCompletiVerbale> verbaliSelezionabili = new ArrayList<DatiCompletiVerbale>();

    String codiceHermesMotivoSelezionato = datiRichiestaIstanza.getCodiceHermesMotivoSelezionato();
    Label labelNonpossibileselezionarealtriverbali =
        new Label("nonpossibileselezionarealtriverbali", "");
    addOrReplace(labelNonpossibileselezionarealtriverbali);

    Label labelPossibileselezionarealtriverbali = new Label("possibileselezionarealtriverbali", "");
    addOrReplace(labelPossibileselezionarealtriverbali);

    Label labelListavuotaVerbali =
        new Label("listavuotaVerbali", "Non vi sono altri verbali selezionabili");
    addOrReplace(labelListavuotaVerbali);

    labelNonpossibileselezionarealtriverbali.setVisible(false);
    labelPossibileselezionarealtriverbali.setVisible(true);

    log.debug("CP step 3 getIndex() = " + getIndex());
    if (LabelFdCUtil.checkIfNotNull(getIndex()) && getIndex() == 3) {
      verbaliSelezionabili =
          getVerbaliCompatibili(datiRichiestaIstanza, codiceHermesMotivoSelezionato);
    }

    datiRichiestaIstanza.setVerbaliUlterioriCompatibili(verbaliSelezionabili);

    long numeroElementi = 12;
    PageableListView<DatiCompletiVerbale> listView =
        new PageableListView<DatiCompletiVerbale>("box", verbaliSelezionabili, numeroElementi) {

          private static final long serialVersionUID = -1L;

          @Override
          protected void populateItem(ListItem<DatiCompletiVerbale> item) {
            DatiCompletiVerbale verbale = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(
                new AttributeAppender(
                    "class", " " + BasePanelGenericContent.ALL_ICON_IOMUO_ISTANZA_VERBALE));
            item.addOrReplace(icona);

            AjaxCheckBox selezionato =
                new AjaxCheckBox(
                    "selezionato", new PropertyModel<Boolean>(verbale, "selezionato")) {

                  private static final long serialVersionUID = -1L;

                  @Override
                  protected void onUpdate(AjaxRequestTarget target) {
                    datiRichiestaIstanza.setSelezionatoByNumeroVerbale(
                        verbale.isSelezionato(), verbale.getVerbale().getNumeroProtocollo());
                    target.add(componentToRefresh);
                  }
                };
            selezionato.setEnabled(true);
            selezionato.setLabel(Model.of("Selezionato"));
            selezionato.setOutputMarkupId(true);
            item.addOrReplace(selezionato);

            Label numeroavviso = new Label("numeroavviso", verbale.getVerbale().getNumeroAvviso());
            numeroavviso.setVisible(PageUtil.isStringValid(verbale.getVerbale().getNumeroAvviso()));
            item.addOrReplace(numeroavviso);

            Label numeroprotocollo =
                new Label("numeroprotocollo", verbale.getVerbale().getNumeroProtocollo());
            numeroprotocollo.setVisible(
                PageUtil.isStringValid(verbale.getVerbale().getNumeroProtocollo()));
            item.addOrReplace(numeroprotocollo);

            Label articoli = new Label("articoli", verbale.getStringaCommaArticoli());
            articoli.setVisible(PageUtil.isStringValid(verbale.getStringaCommaArticoli()));
            item.addOrReplace(articoli);

            Label serie = new Label("serie", verbale.getStringaSerie());
            serie.setVisible(PageUtil.isStringValid(verbale.getStringaSerie()));
            item.addOrReplace(serie);

            LocalDateLabel dataaccertamento =
                new LocalDateLabel("dataaccertamento", verbale.getVerbale().getDataAccertamento());
            dataaccertamento.setVisible(
                LabelFdCUtil.checkIfNotNull(verbale.getVerbale().getDataAccertamento()));
            item.addOrReplace(dataaccertamento);

            Label localita = new Label("localita", verbale.getVerbale().getLocalita());
            localita.setVisible(PageUtil.isStringValid(verbale.getVerbale().getLocalita()));
            item.addOrReplace(localita);
          }
        };

    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    addOrReplace(paginazioneFascicolo);

    if (verbaliSelezionabili == null || verbaliSelezionabili.isEmpty()) {
      labelListavuotaVerbali.setVisible(true);
      listView.setVisible(false);
      paginazioneFascicolo.setVisible(false);
    } else {
      labelListavuotaVerbali.setVisible(false);
      listView.setVisible(true);
      paginazioneFascicolo.setVisible(verbaliSelezionabili.size() > numeroElementi);
    }
  }

  private BigDecimal calcolaTotalePagato(DettaglioVerbale dettaglioVerbale) {

    BigDecimal totale = BigDecimal.ZERO;
    for (ImportoPagato importoPagato : dettaglioVerbale.getImportiPagati()) {
      totale = totale.add(importoPagato.getImportoPagato());
    }
    log.debug("calcolaTotalePagato= " + totale);
    return totale;
  }

  private List<DatiCompletiVerbale> getVerbaliCompatibili(
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl, String codiceHermesMotivoSelezionato) {
    // metto controllo anche qui, superfluo
    List<DatiCompletiVerbale> listDatiCompletiVerbale = new ArrayList<DatiCompletiVerbale>();

    try {
      // datiRichiestaIstanzaPl = inizializzaTuttiVerbali(datiRichiestaIstanzaPl);

      List<Verbale> listVerbali = datiRichiestaIstanzaPl.getListTuttiVerbali();
      log.debug("getVerbaliCompatibili -- listVerbali: " + listVerbali);
      log.debug(
          "getVerbaliCompatibili -- codiceHermesMotivoSelezionato: "
              + codiceHermesMotivoSelezionato);

      log.debug("CP listVerbali size = " + listVerbali.size());
      List<Verbale> listVerbaliFiltratiSoloStatoAperto =
          listVerbali.stream()
              .filter(elemVerbale -> elemVerbale.getStato().equalsIgnoreCase("APERTO"))
              .collect(Collectors.toList());
      log.debug(
          "CP listVerbaliFiltratiSoloStatoAperto size = "
              + listVerbaliFiltratiSoloStatoAperto.size());

      if ("01".equalsIgnoreCase(codiceHermesMotivoSelezionato)) {
        // verbali articolo 7 o 158
        // stessa via
        // stesso veicolo
        // +-24h
        log.debug("getVerbaliCompatibili -- DENTRO CODICE UNO");
        for (Verbale verbale : listVerbaliFiltratiSoloStatoAperto) {

          log.debug("CP in codice 01 verbale = " + verbale.getNumeroProtocollo());

          Boolean articoloOk = false;
          DettaglioVerbale dettaglioVerbale =
              datiRichiestaIstanzaPl.getMapDettaglioVerbale(
                  verbale.getNumeroProtocollo(), getModelObject().getUtente());
          if (dettaglioVerbale == null) {
            continue;
          }

          if (dettaglioVerbale.getStatoPagamento() != null
              && !dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.value())) {
            continue;
          }

          // dopo
          if (checkIfMotivoUguale(dettaglioVerbale.getIstanze(), codiceHermesMotivoSelezionato)) {
            continue;
          }
          // fine dopo

          if (verbale.getArticoliViolati() != null && !verbale.getArticoliViolati().isEmpty()) {
            for (ArticoloViolato articoloViolato : verbale.getArticoliViolati()) {
              if (articoloViolato.getNumero() == null) {
                continue;
              }
              if (articoloViolato.getNumero().equals(7L)
                  || articoloViolato.getNumero().equals(158L)) {
                articoloOk = true;
                break;
              }
            }
          }
          Boolean viaOk = false;
          if (articoloOk) {
            // stessa via
            String codeVia1VerbalePartenza = datiRichiestaIstanzaPl.getCodeVia1VerbalePartenza();
            String codeVia2VerbalePartenza = datiRichiestaIstanzaPl.getCodeVia2VerbalePartenza();
            String codeVia3VerbalePartenza = datiRichiestaIstanzaPl.getCodeVia3VerbalePartenza();
            String verbaleStreetNameCode1 = verbale.getOfficialStreetNameCode1();
            String verbaleStreetNameCode2 = verbale.getOfficialStreetNameCode2();
            String verbaleStreetNameCode3 = verbale.getOfficialStreetNameCode3();
            if (verbaleStreetNameCode1 != null
                && (verbaleStreetNameCode1.equalsIgnoreCase(codeVia1VerbalePartenza)
                    || verbaleStreetNameCode1.equalsIgnoreCase(codeVia2VerbalePartenza)
                    || verbaleStreetNameCode1.equalsIgnoreCase(codeVia3VerbalePartenza))) {
              viaOk = true;
            }
            if (!viaOk
                && verbaleStreetNameCode2 != null
                && (verbaleStreetNameCode2.equalsIgnoreCase(codeVia1VerbalePartenza)
                    || verbaleStreetNameCode2.equalsIgnoreCase(codeVia2VerbalePartenza)
                    || verbaleStreetNameCode2.equalsIgnoreCase(codeVia3VerbalePartenza))) {
              viaOk = true;
            }
            if (!viaOk
                && verbaleStreetNameCode3 != null
                && (verbaleStreetNameCode3.equalsIgnoreCase(codeVia1VerbalePartenza)
                    || verbaleStreetNameCode3.equalsIgnoreCase(codeVia2VerbalePartenza)
                    || verbaleStreetNameCode3.equalsIgnoreCase(codeVia3VerbalePartenza))) {
              viaOk = true;
            }
          }

          log.debug("CP viaOk = " + viaOk);

          Boolean veicoloOk = false;
          if (viaOk) {
            // stesso veicolo
            if (verbale.getTarga() != null
                && datiRichiestaIstanzaPl.getTargaVerbalePartenza() != null
                && verbale
                    .getTarga()
                    .equalsIgnoreCase(datiRichiestaIstanzaPl.getTargaVerbalePartenza())) {
              veicoloOk = true;
            }
          }
          Boolean dataOk = false;
          if (veicoloOk) {
            // +-24h
            LocalDateTime dataDataEOraAccertamentoVerbalePartenza =
                datiRichiestaIstanzaPl.getDataEOraAccertamentoVerbalePartenza();

            boolean stessaDataConVerbaleDiPartenza = false;
            LocalDate localDateVerbaleDiPartenza = null;
            if (LabelFdCUtil.checkIfNotNull(
                    datiRichiestaIstanzaPl.getDatiCompletiVerbaleDiPartenza())
                && LabelFdCUtil.checkIfNotNull(
                    datiRichiestaIstanzaPl
                        .getDatiCompletiVerbaleDiPartenza()
                        .getDettaglioVerbale())) {
              localDateVerbaleDiPartenza =
                  datiRichiestaIstanzaPl
                      .getDatiCompletiVerbaleDiPartenza()
                      .getDettaglioVerbale()
                      .getDataAccertamento();
            }

            log.debug(
                "CP dataDataEOraAccertamentoVerbalePartenza = "
                    + dataDataEOraAccertamentoVerbalePartenza);

            LocalDate dataAccertamentoVerbale = verbale.getDataAccertamento();

            log.debug("CP dataAccertamentoVerbale = " + dataAccertamentoVerbale);

            if (LabelFdCUtil.checkIfNotNull(localDateVerbaleDiPartenza)) {
              if (localDateVerbaleDiPartenza.isEqual(dataAccertamentoVerbale)
                  || localDateVerbaleDiPartenza.isEqual(dataAccertamentoVerbale.minusDays(1))
                  || localDateVerbaleDiPartenza.isEqual(dataAccertamentoVerbale.plusDays(1))) {
                stessaDataConVerbaleDiPartenza = true;
              }
            }

            log.debug("CP stessaDataConVerbaleDiPartenza = " + stessaDataConVerbaleDiPartenza);

            String timeStringAccertamentoVerbale = dettaglioVerbale.getOraAccertamento();

            log.debug("CP timeStringAccertamentoVerbale = " + timeStringAccertamentoVerbale);

            if (dataAccertamentoVerbale != null
                && timeStringAccertamentoVerbale != null
                && stessaDataConVerbaleDiPartenza) {
              LocalTime timeAccertamentoVerbale =
                  LocalDateTimeUtil.getLocalTimeFromStringHHmm(timeStringAccertamentoVerbale);

              log.debug("CP timeAccertamentoVerbale = " + timeAccertamentoVerbale);

              LocalDateTime dataDataEOraAccertamentoVerbaleIth =
                  LocalDateTimeUtil.getLocalDateTimeFromLocalDateAndLocalTime(
                      dataAccertamentoVerbale, timeAccertamentoVerbale);

              log.debug(
                  "CP dataDataEOraAccertamentoVerbaleIth = " + dataDataEOraAccertamentoVerbaleIth);

              dataOk =
                  LocalDateTimeUtil.isSecondInside24hFromFirst(
                      dataDataEOraAccertamentoVerbalePartenza, dataDataEOraAccertamentoVerbaleIth);

              log.debug("CP dataOk in if = " + dataOk);
            }
          }

          log.debug("CP dataOk = " + dataOk);

          log.debug(
              "CP prima di aggiungere in lista = "
                  + articoloOk
                  + " - "
                  + dataOk
                  + " - "
                  + viaOk
                  + " - "
                  + veicoloOk);

          // FRR per debug aggiungo lo stesso, ripristinare controllo
          if (articoloOk && dataOk && viaOk && veicoloOk) {
            DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale();
            datiCompletiVerbale.setVerbale(verbale);
            datiCompletiVerbale.setDettaglioVerbale(dettaglioVerbale);
            listDatiCompletiVerbale.add(datiCompletiVerbale);
          }
        }
      } else if ("02".equalsIgnoreCase(codiceHermesMotivoSelezionato)) {

        log.debug("getVerbaliCompatibili -- DENTRO CODICE 02");
        // verbali articolo 193
        for (Verbale verbale : listVerbaliFiltratiSoloStatoAperto) {
          Boolean articoloOk = false;
          DettaglioVerbale dettaglioVerbale =
              datiRichiestaIstanzaPl.getMapDettaglioVerbale(
                  verbale.getNumeroProtocollo(), getModelObject().getUtente());
          if (dettaglioVerbale == null) {
            continue;
          }

          if (dettaglioVerbale.getStatoPagamento() != null
              && !dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.value())) {
            continue;
          }

          if (checkIfMotivoUguale(dettaglioVerbale.getIstanze(), codiceHermesMotivoSelezionato)) {
            continue;
          }

          if (verbale.getArticoliViolati() != null && !verbale.getArticoliViolati().isEmpty()) {
            for (ArticoloViolato articoloViolato : verbale.getArticoliViolati()) {
              if (articoloViolato.getNumero() == null) {
                continue;
              }
              if (articoloViolato
                  .getNumero()
                  .equals(193L) /* || articoloViolato.getNumero().equals(158L) */) {
                articoloOk = true;
                break;
              }
            }
          }
          if (articoloOk) {
            DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale();
            datiCompletiVerbale.setVerbale(verbale);
            datiCompletiVerbale.setDettaglioVerbale(dettaglioVerbale);
            listDatiCompletiVerbale.add(datiCompletiVerbale);
          }
        }
      } else if ("03".equalsIgnoreCase(codiceHermesMotivoSelezionato)) {
        log.debug("getVerbaliCompatibili -- DENTRO CODICE 03");
        // controllare solo la data
        // dataAccertamento > di data denuncia
        // e
        // dataAccertamento < di data rinvenimento (se not null)
        LocalDate dataFurto = datiRichiestaIstanzaPl.getFurtoData();
        for (Verbale verbale : listVerbaliFiltratiSoloStatoAperto) {
          Boolean dataFurtoOk = false;
          DettaglioVerbale dettaglioVerbale =
              datiRichiestaIstanzaPl.getMapDettaglioVerbale(
                  verbale.getNumeroProtocollo(), getModelObject().getUtente());
          if (dettaglioVerbale == null) {
            continue;
          }

          if (dettaglioVerbale.getStatoPagamento() != null
              && !dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.value())) {
            continue;
          }

          if (checkIfMotivoUguale(dettaglioVerbale.getIstanze(), codiceHermesMotivoSelezionato)) {
            continue;
          }

          LocalDate dataAccertamentoVerbale = verbale.getDataAccertamento();
          if (dataAccertamentoVerbale == null) {
            continue;
          }
          dataFurtoOk = dataAccertamentoVerbale.isAfter(dataFurto);

          Boolean dataRinvenimentoOk = false;
          LocalDate dataRinvenimento = datiRichiestaIstanzaPl.getRitrovamentoData();

          if (dataRinvenimento != null) {
            // o uguale (stesso giorno)
            dataRinvenimentoOk = dataAccertamentoVerbale.isBefore(dataRinvenimento);
          }

          // CP dopo
          if (dataFurtoOk
              && ((LabelFdCUtil.checkIfNotNull(dataRinvenimento) && dataRinvenimentoOk)
                  || (LabelFdCUtil.checkIfNull(dataRinvenimento) && !dataRinvenimentoOk))) {
            DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale();
            datiCompletiVerbale.setVerbale(verbale);
            datiCompletiVerbale.setDettaglioVerbale(dettaglioVerbale);
            listDatiCompletiVerbale.add(datiCompletiVerbale);
          }
        }
      } else if ("05".equalsIgnoreCase(codiceHermesMotivoSelezionato)) {
        log.debug("getVerbaliCompatibili -- DENTRO CODICE 05");
        // String serieVerbalePartenza =
        // datiRichiestaIstanzaPl.getSerieVerbalePartenza();
        // serie y o serie u
        for (Verbale verbale : listVerbaliFiltratiSoloStatoAperto) {
          Boolean serieOk = false;

          DettaglioVerbale dettaglioVerbale =
              datiRichiestaIstanzaPl.getMapDettaglioVerbale(
                  verbale.getNumeroProtocollo(), getModelObject().getUtente());
          if (dettaglioVerbale == null) {
            continue;
          }

          if (dettaglioVerbale.getStatoPagamento() != null
              && !dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.value())) {
            continue;
          }

          if (checkIfMotivoUguale(dettaglioVerbale.getIstanze(), codiceHermesMotivoSelezionato)) {
            continue;
          }

          if (dettaglioVerbale.getSerieVerbale() != null
              && dettaglioVerbale.getSerieVerbale().getCodiceSerie() != null) {
            serieOk =
                dettaglioVerbale.getSerieVerbale().getCodiceSerie().equalsIgnoreCase("Y")
                    || dettaglioVerbale.getSerieVerbale().getCodiceSerie().equalsIgnoreCase("U");
          }
          if (serieOk) {
            DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale();
            datiCompletiVerbale.setVerbale(verbale);
            datiCompletiVerbale.setDettaglioVerbale(dettaglioVerbale);
            listDatiCompletiVerbale.add(datiCompletiVerbale);
          }
        }
      } else if ("06".equalsIgnoreCase(codiceHermesMotivoSelezionato)) {
        log.debug("getVerbaliCompatibili -- DENTRO CODICE 06");
        // serie p
        for (Verbale verbale : listVerbaliFiltratiSoloStatoAperto) {
          Boolean serieOk = false;

          DettaglioVerbale dettaglioVerbale =
              datiRichiestaIstanzaPl.getMapDettaglioVerbale(
                  verbale.getNumeroProtocollo(), getModelObject().getUtente());
          if (dettaglioVerbale == null) {
            continue;
          }

          if (dettaglioVerbale.getStatoPagamento() != null
              && !dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.value())) {
            continue;
          }

          if (checkIfMotivoUguale(dettaglioVerbale.getIstanze(), codiceHermesMotivoSelezionato)) {
            continue;
          }

          if (dettaglioVerbale.getSerieVerbale() != null
              && dettaglioVerbale.getSerieVerbale().getCodiceSerie() != null) {
            serieOk = dettaglioVerbale.getSerieVerbale().getCodiceSerie().equalsIgnoreCase("P");
          }
          if (serieOk) {
            DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale();
            datiCompletiVerbale.setVerbale(verbale);
            datiCompletiVerbale.setDettaglioVerbale(dettaglioVerbale);
            listDatiCompletiVerbale.add(datiCompletiVerbale);
          }
        }
      } else if ("07".equalsIgnoreCase(codiceHermesMotivoSelezionato)) {
        log.debug("getVerbaliCompatibili -- DENTRO CODICE 07");
        // serie j
        for (Verbale verbale : listVerbaliFiltratiSoloStatoAperto) {
          Boolean serieOk = false;

          DettaglioVerbale dettaglioVerbale =
              datiRichiestaIstanzaPl.getMapDettaglioVerbale(
                  verbale.getNumeroProtocollo(), getModelObject().getUtente());
          if (dettaglioVerbale == null) {
            continue;
          }

          if (dettaglioVerbale.getStatoPagamento() != null
              && !dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.value())) {
            continue;
          }

          if (checkIfMotivoUguale(dettaglioVerbale.getIstanze(), codiceHermesMotivoSelezionato)) {
            continue;
          }

          if (dettaglioVerbale.getSerieVerbale() != null
              && dettaglioVerbale.getSerieVerbale().getCodiceSerie() != null) {
            serieOk = dettaglioVerbale.getSerieVerbale().getCodiceSerie().equalsIgnoreCase("J");
          }
          if (serieOk) {
            DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale();
            datiCompletiVerbale.setVerbale(verbale);
            datiCompletiVerbale.setDettaglioVerbale(dettaglioVerbale);
            listDatiCompletiVerbale.add(datiCompletiVerbale);
          }
        }
      } else if ("08".equalsIgnoreCase(codiceHermesMotivoSelezionato)) {
        log.debug("getVerbaliCompatibili -- DENTRO CODICE 08");
        // serie y o articolo 7
        for (Verbale verbale : listVerbaliFiltratiSoloStatoAperto) {
          Boolean serieOk = false;

          DettaglioVerbale dettaglioVerbale =
              datiRichiestaIstanzaPl.getMapDettaglioVerbale(
                  verbale.getNumeroProtocollo(), getModelObject().getUtente());
          if (dettaglioVerbale == null) {
            dettaglioVerbale =
                ServiceLocator.getInstance()
                    .getServiziMieiVerbali()
                    .getDettaglioVerbale(
                        verbale.getNumeroProtocollo(), getModelObject().getUtente());
            datiRichiestaIstanzaPl.addMapDettaglioVerbale(
                verbale.getNumeroProtocollo(), getModelObject().getUtente(), dettaglioVerbale);
          }
          if (dettaglioVerbale == null) {
            continue;
          }

          if (dettaglioVerbale.getStatoPagamento() != null
              && !dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.value())) {
            continue;
          }

          if (checkIfMotivoUguale(dettaglioVerbale.getIstanze(), codiceHermesMotivoSelezionato)) {
            continue;
          }

          if (dettaglioVerbale != null && dettaglioVerbale.getSerieVerbale() != null) {
            serieOk = dettaglioVerbale.getSerieVerbale().getCodiceSerie().equalsIgnoreCase("Y");
          }
          Boolean articoloOk = false;
          // if (serieOk) {
          if (verbale.getArticoliViolati() != null && !verbale.getArticoliViolati().isEmpty()) {
            for (ArticoloViolato articoloViolato : verbale.getArticoliViolati()) {
              if (articoloViolato.getNumero() == null) {
                continue;
              }
              if (articoloViolato.getNumero().equals(7L)) {
                articoloOk = true;
                break;
              }
            }
          }
          // }
          if (serieOk || articoloOk) {
            DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale();
            datiCompletiVerbale.setVerbale(verbale);
            datiCompletiVerbale.setDettaglioVerbale(dettaglioVerbale);
            listDatiCompletiVerbale.add(datiCompletiVerbale);
          }
        }
      } else if ("09".equalsIgnoreCase(codiceHermesMotivoSelezionato)) {
        log.debug("getVerbaliCompatibili -- DENTRO CODICE 09");
        // articolo 7
        for (Verbale verbale : listVerbaliFiltratiSoloStatoAperto) {
          Boolean articoloOk = false;
          DettaglioVerbale dettaglioVerbale =
              datiRichiestaIstanzaPl.getMapDettaglioVerbale(
                  verbale.getNumeroProtocollo(), getModelObject().getUtente());
          if (dettaglioVerbale == null) {
            continue;
          }

          if (dettaglioVerbale.getStatoPagamento() != null
              && !dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.value())) {
            continue;
          }

          if (checkIfMotivoUguale(dettaglioVerbale.getIstanze(), codiceHermesMotivoSelezionato)) {
            continue;
          }

          if (verbale.getArticoliViolati() != null && !verbale.getArticoliViolati().isEmpty()) {
            for (ArticoloViolato articoloViolato : verbale.getArticoliViolati()) {
              if (articoloViolato.getNumero() == null) {
                continue;
              }
              if (articoloViolato.getNumero().equals(7L)) {
                articoloOk = true;
                break;
              }
            }
          }
          if (articoloOk) {
            DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale();
            datiCompletiVerbale.setVerbale(verbale);
            datiCompletiVerbale.setDettaglioVerbale(dettaglioVerbale);
            listDatiCompletiVerbale.add(datiCompletiVerbale);
          }
        }
      } else if ("13".equalsIgnoreCase(codiceHermesMotivoSelezionato)) {
        log.debug("getVerbaliCompatibili -- DENTRO CODICE 13");
        // serie u o y o articolo 7
        for (Verbale verbale : listVerbaliFiltratiSoloStatoAperto) {
          Boolean serieOk = false;
          DettaglioVerbale dettaglioVerbale =
              datiRichiestaIstanzaPl.getMapDettaglioVerbale(
                  verbale.getNumeroProtocollo(), getModelObject().getUtente());
          if (dettaglioVerbale == null) {
            continue;
          }

          if (dettaglioVerbale.getStatoPagamento() != null
              && !dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.value())) {
            continue;
          }

          if (checkIfMotivoUguale(dettaglioVerbale.getIstanze(), codiceHermesMotivoSelezionato)) {
            continue;
          }

          if (dettaglioVerbale.getSerieVerbale() != null
              && dettaglioVerbale.getSerieVerbale().getCodiceSerie() != null) {
            serieOk =
                dettaglioVerbale.getSerieVerbale().getCodiceSerie().equalsIgnoreCase("U")
                    || dettaglioVerbale.getSerieVerbale().getCodiceSerie().equalsIgnoreCase("Y");
          }
          Boolean articoloOk = false;
          // if (serieOk) {
          if (verbale.getArticoliViolati() != null && !verbale.getArticoliViolati().isEmpty()) {
            for (ArticoloViolato articoloViolato : verbale.getArticoliViolati()) {
              if (articoloViolato.getNumero() == null) {
                continue;
              }
              if (articoloViolato.getNumero().equals(7L)) {
                articoloOk = true;
                break;
              }
            }
          }
          // }
          if (serieOk || articoloOk) {
            DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale();
            datiCompletiVerbale.setVerbale(verbale);
            datiCompletiVerbale.setDettaglioVerbale(dettaglioVerbale);
            listDatiCompletiVerbale.add(datiCompletiVerbale);
          }
        }
      } else if ("14".equalsIgnoreCase(codiceHermesMotivoSelezionato)) {
        log.debug("getVerbaliCompatibili -- DENTRO CODICE 14");
        // serie u
        for (Verbale verbale : listVerbaliFiltratiSoloStatoAperto) {
          Boolean serieOk = false;
          DettaglioVerbale dettaglioVerbale =
              datiRichiestaIstanzaPl.getMapDettaglioVerbale(
                  verbale.getNumeroProtocollo(), getModelObject().getUtente());
          if (dettaglioVerbale == null) {
            continue;
          }

          if (dettaglioVerbale.getStatoPagamento() != null
              && !dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.value())) {
            continue;
          }

          if (checkIfMotivoUguale(dettaglioVerbale.getIstanze(), codiceHermesMotivoSelezionato)) {
            continue;
          }

          if (dettaglioVerbale.getSerieVerbale() != null) {
            serieOk = dettaglioVerbale.getSerieVerbale().getCodiceSerie().equalsIgnoreCase("U");
          }
          if (serieOk) {
            DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale();
            datiCompletiVerbale.setVerbale(verbale);
            datiCompletiVerbale.setDettaglioVerbale(dettaglioVerbale);
            listDatiCompletiVerbale.add(datiCompletiVerbale);
          }
        }
      }
    } catch (Exception e) {
      log.error("getVerbaliCompatibili -- errore " + e.getMessage(), e);
    }

    if (datiRichiestaIstanzaPl != null
        && datiRichiestaIstanzaPl.getVerbaliUlterioriCompatibili() != null
        && !datiRichiestaIstanzaPl.getVerbaliUlterioriCompatibili().isEmpty()) {
      listDatiCompletiVerbale = datiRichiestaIstanzaPl.getVerbaliUlterioriCompatibili();
    } else {
      log.debug(
          "getVerbaliCompatibili -- RIMUOVI DUPLICATI DALLA LISTA: " + listDatiCompletiVerbale);
      listDatiCompletiVerbale =
          listDatiCompletiVerbale.stream().distinct().collect(Collectors.toList());
      log.debug("getVerbaliCompatibili -- RITORNA LISTA: " + listDatiCompletiVerbale);
    }
    return listDatiCompletiVerbale;
  }

  private List<Istanza> filtraIstanzePresentate(List<Istanza> istanze) {

    List<Istanza> istanzePresentate = new ArrayList();
    String STATO_IN_COMPILAZIONE = "CMP";
    String STATO_RESPINTA = "EVR";

    for (Istanza istanzaInLista : istanze) {
      if (!istanzaInLista.getStatoCodice().equalsIgnoreCase(STATO_IN_COMPILAZIONE)
          && !istanzaInLista.getStatoCodice().equalsIgnoreCase(STATO_RESPINTA)) {
        istanzePresentate.add(istanzaInLista);
      }
    }
    return istanzePresentate;
  }

  private boolean checkIfMotivoUguale(
      List<Istanza> listaIstanze, String codiceHermesMotivoSelezionato) {
    boolean incompatibileConMotivoSelezionato = false;

    if (listaIstanze != null && !listaIstanze.isEmpty()) {
      for (Istanza istanza : filtraIstanzePresentate(listaIstanze)) {
        if (istanza.getMotivazioni() != null && !istanza.getMotivazioni().isEmpty()) {
          for (Motivazione motiv : istanza.getMotivazioni()) {
            if (codiceHermesMotivoSelezionato.equalsIgnoreCase(motiv.getCodice())) {
              incompatibileConMotivoSelezionato = true;
              break;
            }
          }
        }
        if (incompatibileConMotivoSelezionato) {
          break;
        }
      }
    }

    return incompatibileConMotivoSelezionato;
  }
}
