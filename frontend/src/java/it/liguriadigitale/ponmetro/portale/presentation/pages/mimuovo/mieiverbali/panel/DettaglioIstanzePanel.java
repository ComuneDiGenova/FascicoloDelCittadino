package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.enums.StatoIstanza;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiCompletiVerbale;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.MieIstanzePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.form.UtilIstanze;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.GestisciIstanzaPLPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirate.RichiestaRateizzazionePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.BigDecimalUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaNotifica;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ImportoPagato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Motivazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale.StatoEnum;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class DettaglioIstanzePanel extends BasePanel {

  private static final long serialVersionUID = 3731261365449841093L;

  DatiCompletiVerbale datiCompletiVerbale;

  public DettaglioIstanzePanel(String id) {
    super(id);
    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  public DettaglioIstanzePanel(String id, DatiCompletiVerbale datiCompletiVerbale) {
    this(id);
    this.datiCompletiVerbale = datiCompletiVerbale;
  }

  @Override
  public void fillDati(Object dati) {
    DatiCompletiVerbale datiCompletiVerbale = (DatiCompletiVerbale) dati;
    DettaglioVerbale dettaglioVerbale = datiCompletiVerbale.getDettaglioVerbale();
    Verbale verbale = datiCompletiVerbale.getVerbale();

    // !(isVerbaleArchiviato || haPagatoQualcosa);
    boolean puoPresentareIstanza = isIstanzaPresentabile(datiCompletiVerbale);
    // puoPresentareIstanza = puoPresentareIstanza || Constants.DEPLOY ==
    // TIPO_DEPLOY.SVILUPPO;
    log.debug(
        "DettaglioIstanzePanelDettaglioIstanzePanelDettaglioIstanzePanel puoPresentareIstanza: "
            + puoPresentareIstanza);

    BigDecimal codiceAnagraficaHermes = getCodiceHermesAnagrafica(dettaglioVerbale);
    log.debug("DettaglioIstanzePanel codiceAnagraficaHermes: " + codiceAnagraficaHermes);
    // se verbala archiviato o ha pagato quaclosa non deve essere visibile
    LinkDinamicoLaddaFunzione<Object> btnCreaIstanza =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnCreaIstanza",
            new LinkDinamicoFunzioneData(
                "DettaglioIstanzePanel.creaIstanza",
                "GestisciIstanzaPLPage",
                "DettaglioIstanzePanel.creaIstanza"),
            datiCompletiVerbale,
            DettaglioIstanzePanel.this,
            "color-cyan col-auto icon-fogli",
            puoPresentareIstanza);
    log.debug(
        "DettaglioIstanzePanelDettaglioIstanzePanelDettaglioIstanzePanel puoPresentareIstanza2: "
            + puoPresentareIstanza);

    btnCreaIstanza.setVisible(puoPresentareIstanza);
    addOrReplace(btnCreaIstanza);
    addOrReplace(creaButtonChiediIstanzaRateizzazione(datiCompletiVerbale));
    addOrReplace(creaButtonChiediIstanzaRimborso(datiCompletiVerbale));

    WebMarkupContainer nonPuoiPresentareIstanza =
        new WebMarkupContainer("nonPuoiPresentareIstanza");
    nonPuoiPresentareIstanza.setVisible(!puoPresentareIstanza);
    addOrReplace(nonPuoiPresentareIstanza);

    List<Istanza> listaIstanze = dettaglioVerbale.getIstanze();
    log.debug(
        "DettaglioIstanzePanelDettaglioIstanzePanelDettaglioIstanzePanel puoPresentareIstanza3: "
            + puoPresentareIstanza);
    WebMarkupContainer nessunaIstanza = new WebMarkupContainer("nessunaIstanza");
    nessunaIstanza.setVisible(
        LabelFdCUtil.checkIfNull(listaIstanze)
            || (LabelFdCUtil.checkIfNotNull(listaIstanze)
                && LabelFdCUtil.checkEmptyList(listaIstanze)));
    addOrReplace(nessunaIstanza);

    // ListView<Istanza> listView = null;
    if (listaIstanze != null && !listaIstanze.isEmpty()) {
      listaIstanze =
          listaIstanze.stream().filter(elem -> elem != null).collect(Collectors.toList());
    }

    ListView<Istanza> listView =
        new ListView<Istanza>("listViewDatiIstanze", listaIstanze) {

          private static final long serialVersionUID = 7230352860083311866L;

          @Override
          protected void populateItem(ListItem<Istanza> item) {
            Istanza istanza = item.getModelObject();

            item.setOutputMarkupId(true);

            if (istanza != null) {
              String stato = "";
              if (istanza.getStatoDescrizione() != null) {
                stato =
                    getString("DettaglioIstanzePanel.info1")
                        .concat(istanza.getStatoDescrizione())
                        .concat(getString("DettaglioIstanzePanel.info2"));
              }

              String motivazioni = "";
              if (istanza.getMotivazioni() != null && !istanza.getMotivazioni().isEmpty()) {
                for (Motivazione motivazioneElem : istanza.getMotivazioni()) {
                  String codice = "";
                  String descrizioneMotivazione = "";

                  if (motivazioneElem.getCodice() != null) {
                    codice =
                        getString("DettaglioIstanzePanel.codice")
                            .concat(" ")
                            .concat(motivazioneElem.getCodice());
                  }

                  if (motivazioneElem.getDescrizione() != null) {
                    descrizioneMotivazione =
                        codice.concat(" - ").concat(motivazioneElem.getDescrizione());
                  }
                  motivazioni = motivazioni.concat(descrizioneMotivazione).concat("\n");
                }
              }

              String dettaglioIstanza =
                  getString("DettaglioIstanzePanel.identificativoIstanza")
                      .concat(" ")
                      .concat(String.valueOf(istanza.getId()))
                      .concat(" ")
                      .concat(stato)
                      .concat(" ")
                      .concat(getString("DettaglioIstanzePanel.data"))
                      .concat(" ")
                      .concat(LocalDateUtil.getDataFormatoEuropeo(istanza.getData()))
                      .concat(" ")
                      .concat(getString("DettaglioIstanzePanel.soggetto"))
                      .concat(" ")
                      .concat(istanza.getSoggetto().getRdfsLabel())
                      .concat(" ");

              if (!motivazioni.isEmpty()) {
                dettaglioIstanza =
                    dettaglioIstanza.concat(getString("DettaglioIstanzePanel.motivazioni"));
              }

              Label datiMotivazione = new Label("datiMotivazione", motivazioni);
              datiMotivazione.setVisible(!motivazioni.isEmpty());
              item.add(datiMotivazione);

              Label datiIstanza = new Label("datiIstanza", dettaglioIstanza);
              datiIstanza.setVisible(istanza != null);
              item.add(datiIstanza);
              item.add(creaLinkDettagliIstanza(istanza));
              item.add(creaLinkIntegraIstanza(istanza));
              item.add(creaButtonCompletaIstanzaRateizzazione(istanza));
            }
          }

          private LaddaAjaxLink<Object> creaLinkDettagliIstanza(Istanza dettagliIstanza) {
            LaddaAjaxLink<Object> linkDettagliIstanza =
                new LaddaAjaxLink<Object>("btnDettagliIstanza", Type.Primary) {

                  private static final long serialVersionUID = -4096151585528594905L;

                  @Override
                  public void onClick(AjaxRequestTarget target) {

                    target.add(DettaglioIstanzePanel.this);

                    List<Istanza> listaIstanzeAux = new ArrayList<Istanza>();
                    listaIstanzeAux.add(dettagliIstanza);

                    setResponsePage(new MieIstanzePage(listaIstanzeAux));
                  }
                };
            linkDettagliIstanza.setLabel(
                Model.of(
                    Application.get()
                        .getResourceSettings()
                        .getLocalizer()
                        .getString("DettaglioIstanzePanel.dettagli", DettaglioIstanzePanel.this)));

            return linkDettagliIstanza;
          }

          private LaddaAjaxLink<Object> creaLinkIntegraIstanza(Istanza dettagliIstanza) {
            LaddaAjaxLink<Object> linkIntegraOConcludiIstanza =
                new LaddaAjaxLink<Object>("btnIntegraOConcludiIstanza", Type.Primary) {

                  private static final long serialVersionUID = -111111111111111L;

                  @Override
                  public void onClick(AjaxRequestTarget target) {
                    target.add(DettaglioIstanzePanel.this);
                    BigDecimal codiceAnagraficaHermes = getCodiceHermesAnagrafica(dettaglioVerbale);
                    setResponsePage(
                        new GestisciIstanzaPLPage(dettagliIstanza, codiceAnagraficaHermes));
                  }
                };

            // FRR in attesa di stato riempito per sviluppo lo faccio vedere
            // lo stesso
            String keyDaVisualizzare =
                !dettagliIstanza
                        .getStatoCodice()
                        .equalsIgnoreCase(StatoIstanza.ATTESA_DI_INTEGRAZIONE.toString())
                    ? "DettaglioIstanzePanel.concludi"
                    : "DettaglioIstanzePanel.integra";
            // In compilazione: quando i dati inviati ad Hermes sono
            // ancora nella scrivania utente: sono incompleti e lutente
            // non ha ancora espresso la volonta di consegnarli al Comune
            // Inviata: quando lutente, dopo aver passato i controlli di
            // FDC sulla compilazione, invia al Comune la pratica
            // Presa in carico: stato automatico al primo accesso
            // delloperatore sul back office di hermes
            // In attesa di integrazione: quando il back office richiede
            // allutente documentazione integrativa (solo allegati). Quando
            // lutente invia al Comune la documentazione la pratica passa
            // allo stato inviata
            // Accetta: stato definitivo in caso di esito positivo della
            // istruttoria
            // Respinta: stato definitivo in caso di esito negativo della
            // istruttoria

            linkIntegraOConcludiIstanza.setLabel(
                Model.of(
                    Application.get()
                        .getResourceSettings()
                        .getLocalizer()
                        .getString(keyDaVisualizzare, DettaglioIstanzePanel.this)));

            linkIntegraOConcludiIstanza.setVisible(
                !UtilIstanze.isIstanzaRateizzazione(dettagliIstanza)
                    && (dettagliIstanza
                            .getStatoCodice()
                            .equalsIgnoreCase(StatoIstanza.IN_COMPILAZIONE.toString())
                        || "Richiesta integrazione"
                            .equalsIgnoreCase(dettagliIstanza.getStatoDescrizione())));
            return linkIntegraOConcludiIstanza;
          }
        };

    // }
    boolean isVisibleListViewIstanze = listaIstanze != null && !listaIstanze.isEmpty();
    listView.setVisible(isVisibleListViewIstanze);
    add(listView);
  }

  private BigDecimal calcolaTotalePagato(DettaglioVerbale dettaglioVerbale) {
    BigDecimal totale = BigDecimal.ZERO;

    try {

      for (ImportoPagato importoPagato : dettaglioVerbale.getImportiPagati()) {
        totale = totale.add(importoPagato.getImportoPagato());
      }
      log.debug("calcolaTotalePagato= " + totale);
    } catch (Exception ex) {
      totale = totale.add(new BigDecimal(0));
    }

    return totale;
  }

  private LinkDinamicoLaddaFunzione<Object> creaButtonChiediIstanzaRateizzazione(
      DatiCompletiVerbale datiCompletiVerbale) {
    CompoundPropertyModel<DatiCompletiVerbale> cDatiCompletiVerbale =
        new CompoundPropertyModel<DatiCompletiVerbale>(datiCompletiVerbale);

    LinkDinamicoLaddaFunzione<Object> btnChiediIstanzaRateizzazione =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnChiediIstanzaRateizzazione",
            new LinkDinamicoFunzioneData(
                "DettaglioIstanzePanel.chiediIstanzaRatezzazione",
                "RichiestaRateizzazionePage",
                "DettaglioIstanzePanel.chiediIstanzaRatezzazione"),
            cDatiCompletiVerbale,
            DettaglioIstanzePanel.this,
            "color-cyan col-auto icon-fogli",
            IsRateizzabile());

    return btnChiediIstanzaRateizzazione;
  }

  private LinkDinamicoLaddaFunzione<Object> creaButtonChiediIstanzaRimborso(
      DatiCompletiVerbale datiCompletiVerbale) {

    CompoundPropertyModel<DatiCompletiVerbale> cDatiCompletiVerbale =
        new CompoundPropertyModel<DatiCompletiVerbale>(datiCompletiVerbale);

    LinkDinamicoLaddaFunzione<Object> btnChiediRimborso =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnChiediIstanzaRimborso",
            new LinkDinamicoFunzioneData(
                "DettaglioIstanzePanel.chiediIstanzaRimborso",
                "RichiestaRimborsoPage",
                "DettaglioIstanzePanel.chiediIstanzaRimborso"),
            cDatiCompletiVerbale,
            DettaglioIstanzePanel.this,
            "color-cyan col-auto icon-fogli",
            IsRimborsabile());

    return btnChiediRimborso;
  }

  /// TODO: Chiedere ad Ivan di valorizzare sempre il flag Ratealizzabile
  /// dell'istanza per eliminare questo controllo
  private boolean IsRateizzabile() {

    try {

      return LabelFdCUtil.checkIfNotNull(
              datiCompletiVerbale.getDettaglioVerbale().getImportiPagabili().getRatealizzabile())
          ? datiCompletiVerbale.getDettaglioVerbale().getImportiPagabili().getRatealizzabile()
          : false;
    } catch (Exception ex) {
      log.debug("[IsRateizzabile] Errore nel recupero del flag IsRateizzabile" + ex);
      return false;
    }
  }

  private boolean IsRimborsabile() {

    try {

      return LabelFdCUtil.checkIfNotNull(
              datiCompletiVerbale.getDettaglioVerbale().getRimborsabile())
          ? datiCompletiVerbale.getDettaglioVerbale().getRimborsabile()
          : false;
    } catch (Exception ex) {
      log.debug("[IsRimborsabile] Errore nel recupero del flag IsRateizzabile" + ex);
      return false;
    }
  }

  private LaddaAjaxLink<Object> creaButtonCompletaIstanzaRateizzazione(Istanza istanza) {

    log.debug("[creaButtonCompletaIstanzaRateizzazione] Istanza da Completare" + istanza);

    LaddaAjaxLink<Object> btnCompletaIstanzaRateizzazione =
        new LaddaAjaxLink<Object>("btnCompletaIstanzaRateizzazione", Type.Primary) {

          /** */
          private static final long serialVersionUID = 1L;

          @Override
          public void onClick(AjaxRequestTarget arg0) {
            setResponsePage(new RichiestaRateizzazionePage(3, istanza));
          }
        };

    log.debug(
        "[creaButtonCompletaIstanzaRateizzazione] Codice Stato Istanza: "
            + istanza.getStatoCodice());
    boolean isIstanzaIncompleted =
        istanza.getStatoCodice().equalsIgnoreCase(StatoIstanza.ATTESA_DI_INTEGRAZIONE.toString())
            && UtilIstanze.isIstanzaRateizzazione(istanza);

    log.debug(
        "[creaButtonCompletaIstanzaRateizzazione] Istanza da Completare:  " + isIstanzaIncompleted);

    btnCompletaIstanzaRateizzazione.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DettaglioIstanzePanel.completaIstanzaRatezzazione",
                    DettaglioIstanzePanel.this)));
    // forse meglio brasare false
    btnCompletaIstanzaRateizzazione.setVisible(isIstanzaIncompleted);

    return btnCompletaIstanzaRateizzazione;
  }

  private boolean isIstanzaPresentabile(DatiCompletiVerbale datiCompletiVerbale) {
    // Istanza presentabile se:
    // non archiviato
    // importo maggiore di zero
    // importi pagati = 0

    //		Verbale verbale = datiCompletiVerbale.getVerbale();
    //		boolean isVerbaleNonArchiviato = !verbale.getStato()
    //				.equalsIgnoreCase(StatoEnum.ARCHIVIATO.value());
    //		BigDecimal importo = verbale.getImporto();
    //		Boolean isImportoMaggioreZero = BigDecimalUtil.isMaggioreBigDecimal(importo,
    // BigDecimal.ZERO);
    //		BigDecimal totalePagato = calcolaTotalePagato(datiCompletiVerbale.getDettaglioVerbale());
    //		Boolean isImportiPagatiUgualeZero = BigDecimalUtil.isEqualBigDecimal(totalePagato,
    // BigDecimal.ZERO);
    //		// FRR commenta senno non riesco a fare prove
    //		return // true;
    //		isVerbaleNonArchiviato && isImportoMaggioreZero && isImportiPagatiUgualeZero;

    // 31/05/2022 JIRA FDCOMGE-545 crea istanza visibile se è in stato aperto e non
    // pagato

    //		boolean creaIstanza = false;
    //
    //		if (LabelFdCUtil.checkIfNotNull(datiCompletiVerbale)
    //				&& LabelFdCUtil.checkIfNotNull(datiCompletiVerbale.getDettaglioVerbale())) {
    //			boolean verbaleDaPagare = false;
    //			if
    // (LabelFdCUtil.checkIfNotNull(datiCompletiVerbale.getDettaglioVerbale().getStatoPagamento()))
    // {
    //				verbaleDaPagare = datiCompletiVerbale.getDettaglioVerbale().getStatoPagamento()
    //						.equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.value());
    //			}
    //
    //			boolean verbaleAperto = false;
    //			if (LabelFdCUtil.checkIfNotNull(datiCompletiVerbale.getDettaglioVerbale().getStato())) {
    //				verbaleAperto = datiCompletiVerbale.getDettaglioVerbale().getStato()
    //						.equalsIgnoreCase(StatoEnum.APERTO.value());
    //			}
    //
    //			creaIstanza = verbaleDaPagare && verbaleAperto;
    //		}
    //
    //		return creaIstanza;

    boolean verbaleAperto = false;
    if (LabelFdCUtil.checkIfNotNull(datiCompletiVerbale.getDettaglioVerbale().getStato())) {
      verbaleAperto =
          datiCompletiVerbale
              .getDettaglioVerbale()
              .getStato()
              .equalsIgnoreCase(StatoEnum.APERTO.value());
    }

    Verbale verbale = datiCompletiVerbale.getVerbale();

    BigDecimal importo = BigDecimal.ZERO;
    if (LabelFdCUtil.checkIfNotNull(verbale)) {
      importo = verbale.getImporto();
    }

    Boolean isImportoMaggioreZero = BigDecimalUtil.isMaggioreBigDecimal(importo, BigDecimal.ZERO);
    BigDecimal totalePagato = calcolaTotalePagato(datiCompletiVerbale.getDettaglioVerbale());
    Boolean isImportiPagatiUgualeZero =
        BigDecimalUtil.isEqualBigDecimal(totalePagato, BigDecimal.ZERO);

    log.debug(
        "CP verbaleAperto = "
            + verbaleAperto
            + " - isImportoMaggioreZero = "
            + isImportoMaggioreZero
            + " - isImportiPagatiUgualeZero = "
            + isImportiPagatiUgualeZero);

    return verbaleAperto && isImportoMaggioreZero && isImportiPagatiUgualeZero;
  }

  public BigDecimal getCodiceHermesAnagrafica(DettaglioVerbale dettaglioVerbale) {
    // cf richiedente uguale all'elemento iesimo
    // cerca il cf dentro array
    // getDettaglioVerbaleDiPartenza().getAnagraficheNotifiche()
    // getDettaglioVerbaleDiPartenza().getAnagraficheNotifiche();
    log.debug("7777 1 ServiziMieiVerbaliImpl -- getCodiceHermesAnagrafica: ");
    log.debug(
        "7777 2 ServiziMieiVerbaliImpl -- getCodiceHermesAnagrafica getDettaglioVerbaleDiPartenza: "
            + dettaglioVerbale);
    try {
      List<AnagraficaNotifica> listAnagraficaNotifica = dettaglioVerbale.getAnagraficheNotifiche();
      log.debug(
          "7777 3 ServiziMieiVerbaliImpl -- getCodiceHermesAnagrafica getAnagraficheNotifiche: "
              + dettaglioVerbale.getAnagraficheNotifiche());
      AnagraficaNotifica anagraficaNotifica =
          listAnagraficaNotifica.stream()
              .filter(
                  elemento ->
                      elemento
                          .getDatiAnagrafici()
                          .getCpvTaxCode()
                          .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore()))
              .findFirst()
              .orElse(null);

      return anagraficaNotifica != null
          ? anagraficaNotifica.getCodiceAnagraficaHermes()
          : new BigDecimal(0);
    } catch (Exception e) {
      log.error(
          "7777 9999  ServiziMieiVerbaliImpl -- getCodiceHermesAnagrafica " + e.getStackTrace());
      return new BigDecimal(0);
    }
  }
}
