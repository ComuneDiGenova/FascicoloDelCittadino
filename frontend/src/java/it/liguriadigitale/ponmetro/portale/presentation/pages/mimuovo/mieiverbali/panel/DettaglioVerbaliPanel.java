package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiCompletiVerbale;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaVerbaleConducente;
import it.liguriadigitale.ponmetro.portale.presentation.components.icon.LdIconType;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.DichiarazionePuntiPatentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbali;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Allegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaNotifica;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ArticoloViolato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale.StatoEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale.StatoPagamentoEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

public class DettaglioVerbaliPanel extends BasePanel {

  private static final long serialVersionUID = 1075053729152172060L;

  private Verbale verbale;

  private boolean isArticoliDaVisualizzare = false;

  public DettaglioVerbaliPanel(String id) {
    this(id, null);
  }

  public DettaglioVerbaliPanel(String id, Verbale verbale) {
    super(id);
    this.verbale = verbale;
    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @SuppressWarnings("unused")
  @Override
  public void fillDati(Object dati) {

    DettaglioVerbale dettaglioVerbale = (DettaglioVerbale) dati;

    WebMarkupContainer emergenzaCovid = new WebMarkupContainer("emergenzaCovid");
    emergenzaCovid.setVisible(false);
    addOrReplace(emergenzaCovid);

    AttributeModifier statoVerbaleAperto =
        new AttributeModifier("style", "background-color: #d9364f");
    AttributeModifier statoVerbaleInAttesa =
        new AttributeModifier("style", "background-color: #a66300");
    AttributeModifier statoVerbaleArchiviato =
        new AttributeModifier("style", "background-color: #008758");
    AttributeModifier statoVerbaleggettoRicorso =
        new AttributeModifier("style", "background-color: #06c");
    AttributeModifier statoVerbaleInvioAdAutorita =
        new AttributeModifier("style", "background-color: #06c");
    AttributeModifier statoVerbaleIscrittoARuolo =
        new AttributeModifier("style", "background-color: #06c");
    String statoValue = "";
    if (LabelFdCUtil.checkIfNotNull(dettaglioVerbale.getStato())) {
      statoValue = dettaglioVerbale.getStato();
    }
    Label statoVerbale =
        new Label(
            "statoVerbale",
            getString("DettaglioVerbaliPanel.statoVerbaleSteppers").concat(" ").concat(statoValue));
    if (dettaglioVerbale.getStato().equalsIgnoreCase(StatoEnum.APERTO.value())) {
      statoVerbale.add(statoVerbaleAperto);
    } else if (dettaglioVerbale.getStato().equalsIgnoreCase(StatoEnum.ARCHIVIATO.value())) {
      statoVerbale.add(statoVerbaleArchiviato);
    } else if (dettaglioVerbale
        .getStato()
        .equalsIgnoreCase(StatoEnum.IN_ATTESA_DI_VERIFICA.value())) {
      statoVerbale.add(statoVerbaleInAttesa);
    } else if (dettaglioVerbale.getStato().equalsIgnoreCase(StatoEnum.OGGETTO_DI_RICORSO.value())) {
      statoVerbale.add(statoVerbaleggettoRicorso);
    } else if (dettaglioVerbale
        .getStato()
        .equalsIgnoreCase(StatoEnum.INVIO_AD_AUTORITA_COMPETENTE.value())) {
      statoVerbale.add(statoVerbaleInvioAdAutorita);
    } else if (dettaglioVerbale.getStato().equalsIgnoreCase(StatoEnum.ISCRITTO_A_RUOLO.value())) {
      statoVerbale.add(statoVerbaleIscrittoARuolo);
    }

    statoVerbale.setVisible(PageUtil.isStringValid(dettaglioVerbale.getStato()));
    addOrReplace(statoVerbale);

    Label statoPagamentoVerbale =
        new Label("statoPagamentoVerbale", "Pagamento " + dettaglioVerbale.getStatoPagamento());
    if (dettaglioVerbale.getStatoPagamento() != null) {
      if (dettaglioVerbale
          .getStatoPagamento()
          .equalsIgnoreCase(StatoPagamentoEnum.EFFETTUATO.value())) {
        statoPagamentoVerbale.add(statoVerbaleArchiviato);
      } else {
        statoPagamentoVerbale.add(statoVerbaleAperto);
      }
    }
    statoPagamentoVerbale.setVisible(PageUtil.isStringValid(dettaglioVerbale.getStatoPagamento()));
    addOrReplace(statoPagamentoVerbale);

    Label numeroAvviso = new Label("numeroAvviso", dettaglioVerbale.getNumeroAvviso());
    numeroAvviso.setVisible(PageUtil.isStringValid(dettaglioVerbale.getNumeroAvviso()));
    addOrReplace(numeroAvviso);

    String NP =
        getString("DettaglioVerbaliPanel.util1")
            .concat(
                getString("DettaglioVerbaliPanel.numeroProtocollo")
                    .concat(dettaglioVerbale.getNumeroProtocollo())
                    .concat(getString("DettaglioVerbaliPanel.util2")));
    Label numeroProtocollo = new Label("numeroProtocollo", NP);
    numeroProtocollo.setVersioned(PageUtil.isStringValid(dettaglioVerbale.getNumeroProtocollo()));
    addOrReplace(numeroProtocollo);

    LocalDateLabel dataVerbale =
        new LocalDateLabel("dataVerbale", dettaglioVerbale.getDataAccertamento());
    dataVerbale.setVisible(dettaglioVerbale.getDataAccertamento() != null);
    addOrReplace(dataVerbale);

    Label oraAccertamento = new Label("oraAccertamento", dettaglioVerbale.getOraAccertamento());
    oraAccertamento.setVersioned(PageUtil.isStringValid(dettaglioVerbale.getOraAccertamento()));
    addOrReplace(oraAccertamento);

    Label tipoVeicolo = new Label("tipoVeicolo", dettaglioVerbale.getTipoVeicolo());
    tipoVeicolo.setVisible(PageUtil.isStringValid(dettaglioVerbale.getTipoVeicolo()));
    addOrReplace(tipoVeicolo);

    Label targa = new Label("targa", dettaglioVerbale.getTarga());
    targa.setVisible(PageUtil.isStringValid(dettaglioVerbale.getTarga()));
    addOrReplace(targa);

    Label modello = new Label("modello", dettaglioVerbale.getModello());
    modello.setVisible(PageUtil.isStringValid(dettaglioVerbale.getModello()));
    addOrReplace(modello);

    String dettaglioSerieVerbale = "";
    if (dettaglioVerbale.getSerieVerbale() != null) {
      dettaglioSerieVerbale =
          dettaglioVerbale
              .getSerieVerbale()
              .getCodiceSerie()
              .concat(" - ")
              .concat(dettaglioVerbale.getSerieVerbale().getDescrizioneSerie());
    }
    Label serieVerbale = new Label("serieVerbale", dettaglioSerieVerbale);
    serieVerbale.setVisible(PageUtil.isStringValid(dettaglioSerieVerbale));
    addOrReplace(serieVerbale);

    List<AnagraficaNotifica> listaAnagraficaNotifica = dettaglioVerbale.getAnagraficheNotifiche();

    ListView<AnagraficaNotifica> listViewAnagraficaNotifica = null;
    if (listaAnagraficaNotifica != null) {
      listViewAnagraficaNotifica =
          new ListView<AnagraficaNotifica>("listViewListaAnagrafica", listaAnagraficaNotifica) {

            private static final long serialVersionUID = 5862666258541530554L;

            @Override
            protected void populateItem(ListItem<AnagraficaNotifica> item) {
              AnagraficaNotifica anagraficaNotifica = item.getModelObject();

              item.setOutputMarkupId(true);

              String dettaglioDatiAnagrafici = "";
              String tipologiaSoggetto = "";
              String tipologiaAnagrafica = "";

              String datiNotifica = "";
              String dataNotifica = "";
              String dataSpedizione = "";
              String tipoNotifica = "";

              if (anagraficaNotifica != null) {
                String givenName = "";
                String familyName = "";
                String taxCode = "";

                if (anagraficaNotifica.getDatiAnagrafici().getCpvGivenName() != null) {
                  givenName = anagraficaNotifica.getDatiAnagrafici().getCpvGivenName().concat(" ");
                }

                if (anagraficaNotifica.getDatiAnagrafici().getCpvFamilyName() != null) {
                  familyName =
                      anagraficaNotifica.getDatiAnagrafici().getCpvFamilyName().concat(" ");
                }

                if (anagraficaNotifica.getDatiAnagrafici().getCpvTaxCode() != null) {
                  taxCode =
                      getString("DettaglioVerbaliPanel.util1")
                          .concat(anagraficaNotifica.getDatiAnagrafici().getCpvTaxCode())
                          .concat(getString("DettaglioVerbaliPanel.util2"));
                }

                dettaglioDatiAnagrafici = givenName.concat(familyName).concat(taxCode);

                tipologiaSoggetto = anagraficaNotifica.getTipologiaSoggetto();

                tipologiaAnagrafica = anagraficaNotifica.getTipologiaAnagrafica();

                String tipoSoggetto = getString("DettaglioVerbaliPanel.tipologiaSoggetto");
                String tipoAnagrafica = getString("DettaglioVerbaliPanel.tipologiaAnagrafica");

                if (anagraficaNotifica.getDatiNotifica() != null) {
                  if (anagraficaNotifica.getDatiNotifica().getDataNotifica() != null) {
                    dataNotifica =
                        getString("DettaglioVerbaliPanel.dataNotifica")
                            .concat(" ")
                            .concat(
                                LocalDateUtil.getDataFormatoEuropeo(
                                    anagraficaNotifica.getDatiNotifica().getDataNotifica()));
                  }

                  if (anagraficaNotifica.getDatiNotifica().getDataSpedizione() != null) {
                    dataSpedizione =
                        getString("DettaglioVerbaliPanel.dataSpedizione")
                            .concat(" ")
                            .concat(
                                LocalDateUtil.getDataFormatoEuropeo(
                                    anagraficaNotifica.getDatiNotifica().getDataSpedizione()));
                  }
                }
                datiNotifica =
                    dataNotifica
                        .concat(" ")
                        .concat(dataSpedizione)
                        .concat(" ")
                        .concat(tipoNotifica);

                String descrizione =
                    dettaglioDatiAnagrafici
                        .concat("\n")
                        .concat(tipoSoggetto)
                        .concat(" ")
                        .concat(tipologiaSoggetto)
                        .concat("\n")
                        .concat(tipoAnagrafica)
                        .concat(" ")
                        .concat(tipologiaAnagrafica)
                        .concat("\n")
                        .concat(datiNotifica);

                MultiLineLabel datiAnagrafici = new MultiLineLabel("datiAnagrafici", descrizione);
                item.addOrReplace(datiAnagrafici);
              }
            }
          };
    } else {
      listViewAnagraficaNotifica =
          new ListView<AnagraficaNotifica>("listViewListaAnagrafica") {

            private static final long serialVersionUID = 763419261687248608L;

            @Override
            protected void populateItem(ListItem<AnagraficaNotifica> item) {}
          };
    }
    boolean isVisibleAnagraficaNotifiche = false;
    if (listaAnagraficaNotifica != null) {
      if (!listaAnagraficaNotifica.isEmpty()) {
        isVisibleAnagraficaNotifiche = true;
      }
    } else {
      isVisibleAnagraficaNotifiche = false;
    }
    listViewAnagraficaNotifica.setVisible(isVisibleAnagraficaNotifiche);
    addOrReplace(listViewAnagraficaNotifica);

    Label localita = new Label("localita", dettaglioVerbale.getLocalita());
    localita.setVisible(PageUtil.isStringValid(dettaglioVerbale.getLocalita()));
    addOrReplace(localita);

    AttributeModifier bold = new AttributeModifier("style", "font-weight: 600");

    List<ArticoloViolato> listaArticoliViolati =
        LabelFdCUtil.checkIfNotNull(dettaglioVerbale.getArticoliViolati())
            ? dettaglioVerbale.getArticoliViolati()
            : new ArrayList<ArticoloViolato>();

    ListView<ArticoloViolato> listViewArticoliViolati =
        new ListView<ArticoloViolato>("listViewArticoliViolati", listaArticoliViolati) {

          private static final long serialVersionUID = 1L;

          @Override
          protected void populateItem(ListItem<ArticoloViolato> item) {
            ArticoloViolato articoloViolato = item.getModelObject();

            item.setOutputMarkupId(true);

            log.debug("[Articolo Violato] Articolo: " + articoloViolato);

            if (articoloViolato != null) {
              String articoloCommaDescrizione = "";
              if (articoloViolato.getArticoloDaVisualizzare() != null) {
                articoloCommaDescrizione =
                    articoloCommaDescrizione.concat(articoloViolato.getArticoloDaVisualizzare());
                isArticoliDaVisualizzare = true;
              }

              log.debug("[Articolo Violato] Articolo: " + articoloViolato);

              if (articoloViolato.getDescrizioneViolazione() != null) {
                articoloCommaDescrizione =
                    articoloCommaDescrizione
                        + Application.get()
                            .getResourceSettings()
                            .getLocalizer()
                            .getString(
                                "DettaglioVerbaliPanel.descrizioneViolazione",
                                DettaglioVerbaliPanel.this)
                        + " "
                        + articoloViolato.getDescrizioneViolazione();
              }

              Label articoloViolatoLabel = new Label("articoloViolato", articoloCommaDescrizione);
              articoloViolatoLabel.setVisible(articoloViolato != null && isArticoliDaVisualizzare);
              item.addOrReplace(articoloViolatoLabel);
            }
          }
        };

    List<ArticoloViolato> listaArticoliNumeroComma =
        listaArticoliViolati.stream()
            .filter(elem -> elem.getNumero() != null)
            .collect(Collectors.toList());
    listViewArticoliViolati.setVisible(!listaArticoliNumeroComma.isEmpty());
    addOrReplace(listViewArticoliViolati);

    ListView<ArticoloViolato> listViewDocumentazione = null;
    if (!listaArticoliViolati.isEmpty()) {
      listViewDocumentazione =
          new ListView<ArticoloViolato>("listViewDocumentazione", listaArticoliViolati) {

            private static final long serialVersionUID = -7491493475502281680L;

            @Override
            protected void populateItem(ListItem<ArticoloViolato> item) {
              ArticoloViolato articoloViolato = item.getModelObject();

              item.setOutputMarkupId(true);

              if (articoloViolato != null) {
                String documentazione = articoloViolato.getDocumentoDaPresentare();

                Label documentazioneLabel = new Label("documentazione", documentazione);
                documentazioneLabel.setVisible(articoloViolato.getDocumentoDaPresentare() != null);
                item.addOrReplace(documentazioneLabel);
              }
            }
          };
    }
    List<ArticoloViolato> listaArticoliDocumentazione =
        listaArticoliViolati.stream()
            .filter(elem -> elem.getDocumentoDaPresentare() != null)
            .collect(Collectors.toList());
    listViewDocumentazione.setVisible(!listaArticoliDocumentazione.isEmpty());
    addOrReplace(listViewDocumentazione);

    Label motivoMancataContestazione =
        new Label("motivoMancataContestazione", dettaglioVerbale.getMotivoMancataContestazione());
    motivoMancataContestazione.setVisible(
        PageUtil.isStringValid(dettaglioVerbale.getMotivoMancataContestazione()));
    addOrReplace(motivoMancataContestazione);

    Label apparecchiatura = new Label("apparecchiatura", dettaglioVerbale.getApparecchiatura());
    apparecchiatura.setVisible(PageUtil.isStringValid(dettaglioVerbale.getApparecchiatura()));
    addOrReplace(apparecchiatura);

    NumberFormat numberFormatEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);

    int importoDaMostrare = dettaglioVerbale.getImporto().compareTo(new BigDecimal(0.0));
    Label importo = new Label("importo", numberFormatEuro.format(dettaglioVerbale.getImporto()));
    importo.setVisible(importoDaMostrare == 1);
    addOrReplace(importo);

    Integer totPuntiPatente = 0;
    if (!listaArticoliViolati.isEmpty()) {
      for (ArticoloViolato articoloViolatoElem : listaArticoliViolati) {
        if (articoloViolatoElem != null) {
          totPuntiPatente += articoloViolatoElem.getPuntiPatente();
        }
      }
    }
    Label totalePuntiPatente = new Label("totalePuntiPatente", totPuntiPatente);
    totalePuntiPatente.setVisible(listaArticoliViolati != null);
    addOrReplace(totalePuntiPatente);

    WebMarkupContainer documentazioneDaPresentare =
        new WebMarkupContainer("documentazioneDaPresentare");

    LaddaAjaxLink<Object> linkDPP = creaLinkPuntiPatente(dettaglioVerbale, verbale);
    linkDPP.setVisible(totPuntiPatente > 0);
    addOrReplace(linkDPP);

    LocalDate dataEntro60gg = null;
    boolean checkDataEntro70gg = false;
    boolean checkDataEntro60gg = false;
    AnagraficaNotifica anagraficaProprietario = null;
    if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getAnagraficheNotifiche())) {
      anagraficaProprietario =
          dettaglioVerbale.getAnagraficheNotifiche().stream()
              .filter(elem -> elem.getTipologiaAnagrafica().equalsIgnoreCase("Proprietario"))
              .findAny()
              .orElse(null);
    }
    if (UtilVerbali.checkIfNotNull(anagraficaProprietario)
        && UtilVerbali.checkIfNotNull(anagraficaProprietario.getDatiNotifica())) {
      LocalDate dataNotifica = anagraficaProprietario.getDatiNotifica().getDataNotifica();
      if (UtilVerbali.checkIfNotNull(dataNotifica)) {
        LocalDate max70gg = dataNotifica.plusDays(70);

        log.debug("CP dataNotifica = " + dataNotifica + " - max70gg = " + max70gg);

        dataEntro60gg = dataNotifica.plusDays(60);
        log.debug("CP data entro 60 gg = " + dataEntro60gg);

        checkDataEntro70gg = UtilVerbali.checkDataOggiIsBefore(max70gg);

        checkDataEntro60gg = UtilVerbali.checkDataOggiIsBefore(dataEntro60gg);
      }
    }

    // per verbale senza data notifica
    if (!UtilVerbali.checkIfNotNull(dataEntro60gg)) {
      checkDataEntro70gg = true;
      checkDataEntro60gg = true;
    }
    log.debug("CP checkDataEntro70gg prima del controllo array anagr = " + checkDataEntro70gg);

    log.debug("CP checkDataEntro60gg prima del controllo array anagr = " + checkDataEntro60gg);

    // per verbale senza anagrafica non facciamo vedere dpp
    /*
     * if (dettaglioVerbale.getAnagraficheNotifiche().isEmpty()) {
     * checkDataEntro70gg = false; }
     */
    log.debug("CP checkDataEntro70gg dopo del controllo array anagr = " + checkDataEntro70gg);

    LocalDateLabel dataFineDpp = new LocalDateLabel("dataFineDpp", dataEntro60gg);
    dataFineDpp.setVisible(dataEntro60gg != null);
    documentazioneDaPresentare.addOrReplace(dataFineDpp);

    documentazioneDaPresentare.addOrReplace(linkDPP);

    AnagraficaNotifica anagraficaConducente = null;
    if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getAnagraficheNotifiche())) {
      anagraficaConducente =
          dettaglioVerbale.getAnagraficheNotifiche().stream()
              .filter(elem -> elem.getTipologiaAnagrafica().equalsIgnoreCase("Conducente"))
              .findAny()
              .orElse(null);
    }

    List<Allegato> listaTuttiAllegati = dettaglioVerbale.getAllegati();

    List<Allegato> listaTuttiAllegatiSenzaNull =
        listaTuttiAllegati != null
            ? listaTuttiAllegati.stream().filter(elem -> elem != null).collect(Collectors.toList())
            : new ArrayList<Allegato>();

    List<Allegato> listaAllegati =
        listaTuttiAllegatiSenzaNull.stream()
            .filter(elem -> !elem.getDescrizione().startsWith("Dichiarazione punti"))
            .collect(Collectors.toList());
    List<Allegato> listaDocumentiDpp =
        listaTuttiAllegatiSenzaNull.stream()
            .filter(elem -> elem.getDescrizione().startsWith("Dichiarazione punti"))
            .collect(Collectors.toList());

    List<String> listaStatiDpp = new ArrayList<String>();
    for (Allegato elem : listaDocumentiDpp) {
      log.debug("CP elem descrizione = " + elem.getDescrizione());
      String[] descrizioneStatoCompletaSplitted = elem.getDescrizione().split(Pattern.quote("*"));
      listaStatiDpp.add(descrizioneStatoCompletaSplitted[1].trim());
    }
    log.debug("CP listaStatiDpp = " + listaStatiDpp);
    boolean checkStatoDpp =
        listaStatiDpp.stream()
            .anyMatch(
                elem ->
                    elem.equalsIgnoreCase("accettata") || elem.equalsIgnoreCase("inElaborazione"));
    log.debug("CP checkStatoDpp = " + checkStatoDpp);

    log.debug("totPuntiPatente=" + totPuntiPatente);
    log.debug(
        "anagraficaConducente=" + anagraficaConducente == null ? "null" : anagraficaConducente);
    log.debug("checkDataEntro70gg=" + checkDataEntro70gg);
    log.debug("checkDataEntro60gg=" + checkDataEntro60gg);
    log.debug("checkStatoDpp=" + checkStatoDpp);

    //		boolean visibilitaDocumentiDaPresentare = totPuntiPatente > 0 && anagraficaConducente ==
    // null
    //				&& checkDataEntro70gg && !checkStatoDpp;

    boolean visibilitaDocumentiDaPresentare =
        totPuntiPatente > 0 && anagraficaConducente == null && checkDataEntro60gg && !checkStatoDpp;

    log.debug("CP visibilitaDocumentiDaPresentare = " + visibilitaDocumentiDaPresentare);

    documentazioneDaPresentare.setVisible(visibilitaDocumentiDaPresentare);

    addOrReplace(documentazioneDaPresentare);

    addOrReplace(creaDettaglioPagamenti(dettaglioVerbale));

    DocumentiPanel documentiPanel =
        (DocumentiPanel)
            new DocumentiPanel("documentiPanel", dettaglioVerbale).setRenderBodyOnly(true);
    documentiPanel.fillDati(dettaglioVerbale);
    documentiPanel.setVisible(
        dettaglioVerbale.getDocumenti() != null && !dettaglioVerbale.getDocumenti().isEmpty());
    addOrReplace(documentiPanel);

    AllegatiPanel allegatiPanel =
        (AllegatiPanel)
            new AllegatiPanel("allegatiPanel", dettaglioVerbale, listaAllegati)
                .setRenderBodyOnly(true);
    allegatiPanel.fillDati(listaAllegati);
    allegatiPanel.setVisible(listaAllegati != null && !listaAllegati.isEmpty());
    addOrReplace(allegatiPanel);

    DocumentiDppPanel documentiDppPanel =
        (DocumentiDppPanel)
            new DocumentiDppPanel("documentiDppPanel", dettaglioVerbale, listaDocumentiDpp)
                .setRenderBodyOnly(true);
    documentiDppPanel.fillDati(listaDocumentiDpp);
    documentiDppPanel.setVisible(listaDocumentiDpp != null && !listaDocumentiDpp.isEmpty());
    addOrReplace(documentiDppPanel);

    DatiCompletiVerbale datiCompletiVerbale = new DatiCompletiVerbale(verbale, dettaglioVerbale);

    DettaglioIstanzePanel dettaglioIstanzePanel =
        (DettaglioIstanzePanel)
            new DettaglioIstanzePanel("istanzePanel", datiCompletiVerbale).setRenderBodyOnly(true);
    dettaglioIstanzePanel.fillDati(datiCompletiVerbale);
    // TODO mettere visibilita se crea istanza e abilitata su db
    // dettaglioIstanzePanel
    // .setVisible(dettaglioVerbale.getIstanze() != null &&
    // !dettaglioVerbale.getIstanze().isEmpty());
    addOrReplace(dettaglioIstanzePanel);

    RicorsiPanel ricorsiPanel =
        (RicorsiPanel) new RicorsiPanel("ricorsiPanel", dettaglioVerbale).setRenderBodyOnly(true);
    ricorsiPanel.fillDati(dettaglioVerbale);
    addOrReplace(ricorsiPanel);
  }

  private LaddaAjaxLink<Object> creaLinkPuntiPatente(
      final DettaglioVerbale dettaglioVerbale, Verbale verbale) {
    LaddaAjaxLink<Object> linkPuntiPatente =
        new LaddaAjaxLink<Object>("linkPuntiPatente", Type.Primary) {

          private static final long serialVersionUID = -5784737441147894945L;

          @SuppressWarnings("unused")
          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DettaglioVerbaliPanel.this);
            RicercaVerbaleConducente ricercaVerbaleConducente = new RicercaVerbaleConducente();
            DichiarazionePuntiPatentePage page =
                new DichiarazionePuntiPatentePage(dettaglioVerbale, verbale);
            throw new RestartResponseAtInterceptPageException(page);
          }
        };
    linkPuntiPatente.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DettaglioVerbaliPanel.puntiPatente", DettaglioVerbaliPanel.this)));

    linkPuntiPatente.setIconType(new LdIconType("icon-bandi mx-1"));

    return linkPuntiPatente;
  }

  private DettaglioPagamentiRatePanel manageDettaglioPagamentiRate(
      DettaglioVerbale dettaglioVerbale) {
    DettaglioPagamentiRatePanel dettaglioPagamentiRatePanel =
        new DettaglioPagamentiRatePanel("dettaglioPagamentiPanel", dettaglioVerbale);
    dettaglioPagamentiRatePanel.fillDati(dettaglioVerbale);
    return dettaglioPagamentiRatePanel;
  }

  private DettaglioPagamentiPanel manageDettaglioPagamenti(DettaglioVerbale dettaglioVerbale) {
    DettaglioPagamentiPanel dettaglioPagamentiPanel =
        new DettaglioPagamentiPanel("dettaglioPagamentiPanel", dettaglioVerbale);

    dettaglioPagamentiPanel.fillDati(dettaglioVerbale);
    boolean visibilitaPagamenti = false;
    if (dettaglioVerbale.getStato().equalsIgnoreCase(StatoEnum.ARCHIVIATO.value())
        && (dettaglioVerbale.getImportiPagabili() == null
            || dettaglioVerbale.getImportiPagabili().toString().isEmpty())
        && (dettaglioVerbale.getImportiPagati() == null
            || dettaglioVerbale.getImportiPagati().isEmpty())) {
      visibilitaPagamenti = false;
    } else {
      visibilitaPagamenti = true;
    }
    log.debug("CP visibilitaPagamenti = " + visibilitaPagamenti);
    dettaglioPagamentiPanel.setVisible(visibilitaPagamenti);

    return dettaglioPagamentiPanel;
  }

  private BasePanel creaDettaglioPagamenti(DettaglioVerbale dettaglioVerbale) {
    return manageDettaglioPagamenti(dettaglioVerbale);
  }
}
