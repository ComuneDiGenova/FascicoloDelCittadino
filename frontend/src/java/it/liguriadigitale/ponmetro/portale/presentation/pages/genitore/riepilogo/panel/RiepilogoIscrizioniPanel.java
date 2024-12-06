package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneLinkEsterni;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoDomandaChiusuraServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoDomandaChiusuraServiziRistorazione.StatoRichiestaChiusuraIscrizioneEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class RiepilogoIscrizioniPanel extends BasePanel {

  private static final String STATALE = "STATALE";
  private static final String ICON_BIMBA = "color-orange col-3 icon-bimba";
  private static final String ICON_BIMBO = "color-orange col-3 icon-bimbo";
  private static final long serialVersionUID = 6022066790441301109L;

  // TODO commento ma funziona, serve per gestione log persistenti JIRA FASCITT-1003
  // byte[] bytes;

  public RiepilogoIscrizioniPanel(String id) {
    super(id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    // TODO commento ma funziona, serve per gestione log persistenti JIRA FASCITT-1003
    // addDownloadLink();

    List<UtenteServiziRistorazione> lista = (List<UtenteServiziRistorazione>) dati;
    ListView<UtenteServiziRistorazione> listView =
        new ListView<UtenteServiziRistorazione>("box", lista) {

          private static final long serialVersionUID = 6065212493986751411L;

          @SuppressWarnings("unused")
          @Override
          protected void populateItem(ListItem<UtenteServiziRistorazione> item) {
            final UtenteServiziRistorazione iscritto = item.getModelObject();

            log.debug("iscritto_dati " + iscritto);

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaMaschioFemmina(iscritto));
            Label nomeCognome = new Label("nome", iscritto.getNome() + " " + iscritto.getCognome());
            LocalDateLabel dataNascita = new LocalDateLabel("data", iscritto.getDataNascita());
            LocalDateLabel dataIscrizione =
                new LocalDateLabel("dataIscrizione", getDataDomandaDaVisualizzare(iscritto));

            DatiStatoDomandaChiusuraServiziRistorazione datiStatoChiusura =
                new DatiStatoDomandaChiusuraServiziRistorazione();
            try {

              datiStatoChiusura =
                  ServiceLocator.getInstance()
                      .getServiziRistorazione()
                      .getDatiStatoDomandaChiusuraServiziRistorazione(iscritto.getCodiceFiscale());
            } catch (ApiException | BusinessException e) {
              log.error("Errore dati chiusura");
            }

            Label statoChiusuraIscrizione =
                new Label(
                    "statoChiusuraIscrizione",
                    datiStatoChiusura.getStatoRichiestaChiusuraIscrizione());
            statoChiusuraIscrizione.setVisible(
                !datiStatoChiusura
                    .getStatoRichiestaChiusuraIscrizione()
                    .equalsIgnoreCase("Non Presentata"));

            int calcoloEta = LocalDateUtil.getMesiEta(iscritto.getDataNascita());
            String etaFiglio = "";
            String mesi = "";
            if (calcoloEta < 1) {
              etaFiglio = calcoloEta + " ";
              mesi = getString("RiepilogoIscrizioniPanel.mese");
            } else if (calcoloEta < 12) {
              etaFiglio = calcoloEta + " ";
              mesi = getString("RiepilogoIscrizioniPanel.mesi");
            } else if (calcoloEta < 24) {
              etaFiglio = calcoloEta / 12 + " ";
              mesi = getString("RiepilogoIscrizioniPanel.anno");
            } else {
              etaFiglio = calcoloEta / 12 + " ";
              mesi = getString("RiepilogoIscrizioniPanel.anni");
            }
            Label eta = new Label("eta", etaFiglio);
            Label mesiAnni = new Label("mesiAnni", mesi);

            String statoIscrizioneFiglio = "";
            final String domanda = "Domanda ";
            if (iscritto.getStatoIscrizioneServiziRistorazione().equalsIgnoreCase("Non iscritto")) {
              statoIscrizioneFiglio =
                  iscritto
                      .getStatoIscrizioneServiziRistorazione()
                      .concat(" al servizio di ristorazione");
            } else if (iscritto
                .getStatoIscrizioneServiziRistorazione()
                .equalsIgnoreCase("Inviata")) {
              statoIscrizioneFiglio =
                  domanda.concat(iscritto.getStatoIscrizioneServiziRistorazione().toLowerCase());
            } else if (iscritto
                .getStatoIscrizioneServiziRistorazione()
                .equalsIgnoreCase("In elaborazione")) {
              statoIscrizioneFiglio =
                  domanda.concat(iscritto.getStatoIscrizioneServiziRistorazione().toLowerCase());
            } else if (iscritto
                .getStatoIscrizioneServiziRistorazione()
                .equalsIgnoreCase("Accettata")) {
              statoIscrizioneFiglio =
                  domanda.concat(iscritto.getStatoIscrizioneServiziRistorazione().toLowerCase());
            } else if (iscritto
                .getStatoIscrizioneServiziRistorazione()
                .equalsIgnoreCase("Sospesa")) {
              statoIscrizioneFiglio =
                  domanda.concat(iscritto.getStatoIscrizioneServiziRistorazione().toLowerCase());
            }

            // TO-DO interrogare solo se necessario (parente della
            // proprieta' di visualizzazione)
            boolean boGetSituazionePagamenti = true;
            String situazioneSinteticaPagamenti =
                boGetSituazionePagamenti
                    ? getStringSituazionePagamentiByCfIscritto(iscritto.getCodiceFiscale())
                    : null;

            Label statoIscrizione = new Label("stato", statoIscrizioneFiglio);
            Label labelSituazioneSinteticaPagamenti =
                new Label("situazionepagamenti", situazioneSinteticaPagamenti);

            boolean visibilitaLinkPartitario = isVisibileLinkPartitario(iscritto);
            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkPartitario =
                new LinkDinamicoLaddaFunzione<>(
                    "partitario",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.testoLinkPartitario")
                        .setWicketClassName("PartitarioIscrittoMensaPage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid"); // visibilitaLinkPartitario

            // FDCOMGE-61
            /*
             * boolean visibilitaLinkNonInRegolaConPagamenti = linkPartitario.isVisible() &&
             * isVisibileSituazionePagamenti(iscritto, situazioneSinteticaPagamenti) &&
             * situazioneSinteticaPagamenti.
             * equalsIgnoreCase("Non in regola con i pagamenti") &&
             * isImpegnatoAlPagamento(iscritto);
             */
            boolean visibilitaLinkNonInRegolaConPagamenti =
                linkPartitario.isVisible()
                    && isVisibileSituazionePagamenti(iscritto, situazioneSinteticaPagamenti)
                    && situazioneSinteticaPagamenti.equalsIgnoreCase(
                        "Non in regola con i pagamenti");
            LinkDinamicoLaddaFunzione<Object> linkNonInRegolaConPagamenti =
                new LinkDinamicoLaddaFunzione<Object>(
                    "nonInRegolaConPagamenti",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.nonInRegolaConPagamenti")
                        .setWicketClassName("PagamentiBollettiniMensaPage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    null,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid",
                    visibilitaLinkNonInRegolaConPagamenti);

            Label scuola;
            Label classe;
            Label sezione;
            Label iscrittoDal;
            Label iscrizioneLabel;

            if (StatoIscrizioneServiziRistorazioneEnum.NON_ISCRITTO
                .value()
                .equalsIgnoreCase(iscritto.getStatoIscrizioneServiziRistorazione())) {
              scuola = new Label("scuola", "");
              classe = new Label("classe", "");
              sezione = new Label("sezione", "");
              iscrizioneLabel = new Label("iscritto", "");
            } else {

              scuola =
                  new Label(
                      "scuola",
                      iscritto.getStrutturaScolastica() == null
                          ? null
                          : StringUtils.lowerCase(iscritto.getCategoriaStrutturaScolastica())
                              + "  "
                              + StringUtils.lowerCase(
                                  iscritto.getStrutturaScolastica().toLowerCase()));

              classe =
                  new Label(
                      "classe",
                      iscritto.getClasse() == null ? null : iscritto.getClasse().toLowerCase());

              sezione =
                  new Label(
                      "sezione",
                      iscritto.getSezione() == null ? null : iscritto.getSezione().toLowerCase());

              iscrizioneLabel = new Label("iscritto", getTextIscrizione(iscritto));
            }

            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkNuovaIscrizione =
                creaLinkNuovaIscrizione(iscritto);

            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkNuovaIscrizionePNRR =
                creaLinkNuovaIscrizionePNRR(iscritto);

            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkNuovaIscrizioneNonResidente =
                creaLinkNuovaIscrizioneNonResidente(iscritto);

            boolean visibilitaLinkVerificaIscrizione = !isVisibileLinkNuovaIscrizione(iscritto);
            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkVerificaIscrizione =
                new LinkDinamicoLaddaFunzione<>(
                    "iscrizione",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.testo.verificaIscrizione")
                        .setWicketClassName("StatoIscrizionePage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid",
                    visibilitaLinkVerificaIscrizione);

            boolean visibilitaLinkMensa = isVisibileLinkMensa(iscritto);
            log.debug("voir " + visibilitaLinkMensa);

            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkMensa =
                new LinkDinamicoLaddaFunzione<>(
                    "mensa",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.presenzeMensa")
                        .setWicketClassName("PresenzeInMensaPage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid",
                    visibilitaLinkMensa);

            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkAgevolazione =
                new LinkDinamicoLaddaFunzione<>(
                    "richiestaAgevolazione",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.richiediAgevolazione")
                        .setWicketClassName("RichiestaAgevolazioneStep1Page")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid");

            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkAgevolazionePNRR =
                new LinkDinamicoLaddaFunzione<>(
                    "richiestaAgevolazionePNRR",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.richiediAgevolazione")
                        .setWicketClassName("DomandaAgevolazioneTariffariaMensaPage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid");

            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkStatoAgevolazione =
                new LinkDinamicoLaddaFunzione<>(
                    "statoAgevolazione",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.statoAgevolazione")
                        .setWicketClassName("StatoAgevolazionePage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid");

            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkDieteSpeciali =
                new LinkDinamicoLaddaFunzione<>(
                    "dieteSpeciali",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.dieteSpeciali")
                        .setWicketClassName("DieteSpecialiPage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid");

            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkCedoleLibrarie =
                new LinkDinamicoLaddaFunzione<UtenteServiziRistorazione>(
                    "cedoleLibrarie",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.cedoleLibrarie")
                        .setWicketClassName("CedoleLibrariePage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid") {

                  private static final long serialVersionUID = 3908048359087120465L;

                  @Override
                  public boolean isVisible() {

                    int calcoloEta = LocalDateUtil.getMesiEta(iscritto.getDataNascita());

                    // FDCOMGE-599
                    // if (calcoloEta > 12) {
                    // int anni = calcoloEta / 12;
                    // if ((anni > 4) && (anni < 11)) {
                    // return true;
                    // } else {
                    // return false;
                    // }
                    // } else {
                    // return false;
                    // }

                    boolean visibilitaCedole = false;

                    // try {
                    // Residente datiResidenzaIscritto =
                    // ServiceLocator.getInstance().getServizioDemografico().getDatiResidente(iscritto.getCodiceFiscale());

                    // if(LabelFdCUtil.checkIfNotNull(datiResidenzaIscritto)) {
                    if (calcoloEta > 12) {
                      int anni = calcoloEta / 12;
                      if ((anni > 4 && anni < 18)) {
                        visibilitaCedole = true;
                      }
                    }
                    // }
                    // } catch (BusinessException | ApiException e) {
                    //	log.error("Errore dati residente bambino per cedole: " + e.getMessage());
                    // }

                    return visibilitaCedole;
                  }
                };

            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkCommissioniMensa =
                new LinkDinamicoLaddaFunzione<>(
                    "commissioniMensa",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.commissioniMensa")
                        .setWicketClassName("VerbaliCommissioneMensaPage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid");

            boolean linkChiusuraVisible = false;
            if (datiStatoChiusura
                    .getStatoRichiestaChiusuraIscrizione()
                    .equalsIgnoreCase(StatoRichiestaChiusuraIscrizioneEnum.IN_ELABORAZIONE.value())
                || datiStatoChiusura
                    .getStatoRichiestaChiusuraIscrizione()
                    .equalsIgnoreCase(StatoRichiestaChiusuraIscrizioneEnum.ACCETTATA.value())) {
              linkChiusuraVisible = false;
            } else {
              linkChiusuraVisible = true;
            }

            boolean visibilitaLinkChiusuraIscrizione =
                linkChiusuraVisible
                    && isStatoIscrizionePerLinkChiusura(iscritto)
                    && isVisibleLinkChiusuraIscrizioneRistorazione(iscritto);

            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkChiusuraIscrizione =
                new LinkDinamicoLaddaFunzione<>(
                    "chiusuraIscrizione",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText(
                            "RiepilogoIscrizioniPanel.testoLinkChiusuraIscrizione")
                        .setWicketClassName("RichiestaChiusuraIscrizioneRefezionePage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid",
                    visibilitaLinkChiusuraIscrizione);

            String urlSimulazioneTariffaria =
                "https://www.applicazioni.comune.genova.it/tariffaristorazione/intro.asp";
            ExternalLink linkSimulazioneTariffaria =
                new ExternalLink("simulazioneTariffaria", urlSimulazioneTariffaria);
            linkSimulazioneTariffaria.setVisible(false);

            scuola.setVisible(isVisibileScuola(iscritto));
            sezione.setVisible(isVisibileSezione(iscritto));
            classe.setVisible(isVisibileClasse(iscritto));

            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> linkAttestatoPagamenti =
                new LinkDinamicoLaddaFunzione<>(
                    "attestatoPagamenti",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.attestatoPagamenti")
                        .setWicketClassName("AttestatoPagamentiPage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid");

            // FDCOMGE-61
            /*
             * labelSituazioneSinteticaPagamenti .setVisible( linkPartitario.isVisible() &&
             * isVisibileSituazionePagamenti(iscritto, situazioneSinteticaPagamenti) &&
             * !situazioneSinteticaPagamenti
             * .equalsIgnoreCase("Non in regola con i pagamenti") &&
             * isImpegnatoAlPagamento(iscritto));
             */

            labelSituazioneSinteticaPagamenti.setVisible(
                linkPartitario.isVisible()
                    && isVisibileSituazionePagamenti(iscritto, situazioneSinteticaPagamenti)
                    && !situazioneSinteticaPagamenti.equalsIgnoreCase(
                        "Non in regola con i pagamenti"));

            log.debug("Impegnato al pagamento = " + isImpegnatoAlPagamento(iscritto));

            Label labelNonInRegolaConPagamenti =
                new Label(
                    "labelNonInRegolaConPagamenti",
                    getString("RiepilogoIscrizioniPanel.nonInRegolaConPagamenti"));

            // FDCOMGE-61
            /*
             * labelNonInRegolaConPagamenti.setVisible(linkPartitario. isVisible() &&
             * isVisibileSituazionePagamenti(iscritto, situazioneSinteticaPagamenti) &&
             * situazioneSinteticaPagamenti.
             * equalsIgnoreCase("Non in regola con i pagamenti") &&
             * !isImpegnatoAlPagamento(iscritto));
             */

            labelNonInRegolaConPagamenti.setVisible(
                linkPartitario.isVisible()
                    && isVisibileSituazionePagamenti(iscritto, situazioneSinteticaPagamenti)
                    && situazioneSinteticaPagamenti.equalsIgnoreCase(
                        "Non in regola con i pagamenti")
                    && !visibilitaLinkNonInRegolaConPagamenti);

            dataIscrizione.setVisible(isVisibileDataIscrizione(iscritto));

            String testoLinkIscrizione = getTestoLabelLinkIscrizioneMensa(iscritto);
            linkNuovaIscrizione.add(
                new AttributeModifier("title", testoLinkIscrizione + " " + iscritto.getNome()));
            linkNuovaIscrizioneNonResidente.add(
                new AttributeModifier("title", testoLinkIscrizione + " " + iscritto.getNome()));
            linkNuovaIscrizionePNRR.add(
                new AttributeModifier("title", testoLinkIscrizione + " " + iscritto.getNome()));

            LinkDinamicoFunzioneLinkEsterni<Object> videoTutorialDieteSpeciali =
                new LinkDinamicoFunzioneLinkEsterni<Object>(
                    "videoTutorialDieteSpeciali",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.tutorialBenvunaCard")
                        .setWicketClassName("VideoTutorialDieteSpecialiPage")
                        .build(),
                    null,
                    RiepilogoIscrizioniPanel.this,
                    "icon-video-tutorial",
                    "",
                    false);

            LinkDinamicoFunzioneLinkEsterni<Object> videoTutorialCedoleLibrarie =
                new LinkDinamicoFunzioneLinkEsterni<Object>(
                    "videoTutorialCedoleLibrarie",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.tutorialBenvunaCard")
                        .setWicketClassName("VideoTutorialCedoleLibrariePage")
                        .build(),
                    null,
                    RiepilogoIscrizioniPanel.this,
                    "icon-video-tutorial",
                    "",
                    false);

            LinkDinamicoFunzioneLinkEsterni<Object> videoTutorialVerbaliDiCommissioniMensa =
                new LinkDinamicoFunzioneLinkEsterni<Object>(
                    "videoTutorialVerbaliDiCommissioniMensa",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.tutorialBenvunaCard")
                        .setWicketClassName("VideoTutorialVerbaliDiCommissioniMensaPage")
                        .build(),
                    null,
                    RiepilogoIscrizioniPanel.this,
                    "icon-video-tutorial",
                    "",
                    false);

            LinkDinamicoFunzioneLinkEsterni<Object> videoTutorialAttestazioniDiPagamento =
                new LinkDinamicoFunzioneLinkEsterni<Object>(
                    "videoTutorialAttestazioniDiPagamento",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.tutorialBenvunaCard")
                        .setWicketClassName("VideoTutorialAttestazioniDiPagamentoPage")
                        .build(),
                    null,
                    RiepilogoIscrizioniPanel.this,
                    "icon-video-tutorial",
                    "",
                    false);

            LinkDinamicoFunzioneLinkEsterni<Object> videoTutorialRichiestaAgevolazioneTariffaria =
                new LinkDinamicoFunzioneLinkEsterni<Object>(
                    "videoTutorialRichiestaAgevolazioneTariffaria",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.tutorialBenvunaCard")
                        .setWicketClassName("VideoTutorialRichiestaAgevolazioneTariffariaPage")
                        .build(),
                    null,
                    RiepilogoIscrizioniPanel.this,
                    "icon-video-tutorial",
                    "",
                    false);

            LinkDinamicoFunzioneLinkEsterni<Object>
                videoTutorialStoricoRichiesteAgevolazioneTariffaria =
                    new LinkDinamicoFunzioneLinkEsterni<Object>(
                        "videoTutorialStoricoRichiesteAgevolazioneTariffaria",
                        LinkDinamicoFunzioneDataBuilder.getInstance()
                            .setWicketLabelKeyText("RiepilogoIscrizioniPanel.tutorialBenvunaCard")
                            .setWicketClassName("VideoTutorialStoricoAgevolazioneTariffariaPage")
                            .build(),
                        null,
                        RiepilogoIscrizioniPanel.this,
                        "icon-video-tutorial",
                        "",
                        false);

            LinkDinamicoFunzioneLinkEsterni<Object> VideoTutorialMovimentiPagamenti =
                new LinkDinamicoFunzioneLinkEsterni<Object>(
                    "VideoTutorialMovimentiPagamenti",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.tutorialBenvunaCard")
                        .setWicketClassName("VideoTutorialMovimentiPagamentiPage")
                        .build(),
                    null,
                    RiepilogoIscrizioniPanel.this,
                    "icon-video-tutorial",
                    "",
                    false);

            LinkDinamicoFunzioneLinkEsterni<Object> videoTutorialChiusuraIscrizioneRistorazione =
                new LinkDinamicoFunzioneLinkEsterni<Object>(
                    "videoTutorialChiusuraIscrizioneRistorazione",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.tutorialBenvunaCard")
                        .setWicketClassName("VideoTutorialChiusuraIscrizioneRistorazionePage")
                        .build(),
                    null,
                    RiepilogoIscrizioniPanel.this,
                    "icon-video-tutorial",
                    "",
                    false);

            log.debug("cedoleLibrarie_info " + linkCedoleLibrarie);

            item.addOrReplace(videoTutorialChiusuraIscrizioneRistorazione);
            item.addOrReplace(VideoTutorialMovimentiPagamenti);

            item.addOrReplace(videoTutorialStoricoRichiesteAgevolazioneTariffaria);

            item.addOrReplace(videoTutorialRichiestaAgevolazioneTariffaria);
            item.addOrReplace(videoTutorialAttestazioniDiPagamento);
            item.addOrReplace(videoTutorialDieteSpeciali);

            // videoTutorialCedoleLibrarie.setVisible(isVisibleCedole(iscritto));
            // videoTutorialCedoleLibrarie.setVisibilityAllowed(isVisible());
            videoTutorialCedoleLibrarie.setVisibilityAllowed(isVisibleCedole(iscritto));
            item.addOrReplace(videoTutorialCedoleLibrarie);

            item.addOrReplace(videoTutorialVerbaliDiCommissioniMensa);
            item.addOrReplace(linkNuovaIscrizione);
            item.addOrReplace(linkNuovaIscrizionePNRR);
            item.addOrReplace(linkNuovaIscrizioneNonResidente);
            item.addOrReplace(linkVerificaIscrizione);
            item.addOrReplace(linkMensa);
            item.addOrReplace(linkAgevolazione);
            item.addOrReplace(linkAgevolazionePNRR);

            item.addOrReplace(linkStatoAgevolazione);
            item.addOrReplace(linkSimulazioneTariffaria);
            item.addOrReplace(linkPartitario);
            item.addOrReplace(linkDieteSpeciali);
            item.addOrReplace(linkCedoleLibrarie);
            item.addOrReplace(linkCommissioniMensa);
            item.addOrReplace(linkAttestatoPagamenti);
            item.addOrReplace(linkChiusuraIscrizione);
            item.addOrReplace(eta);
            item.addOrReplace(mesiAnni);
            item.addOrReplace(icona);
            item.addOrReplace(scuola);
            item.addOrReplace(classe);
            item.addOrReplace(sezione);
            item.addOrReplace(statoIscrizione);
            item.addOrReplace(labelSituazioneSinteticaPagamenti);

            item.addOrReplace(labelNonInRegolaConPagamenti);

            item.addOrReplace(linkNonInRegolaConPagamenti);

            item.addOrReplace(dataNascita);
            item.addOrReplace(dataIscrizione);
            item.addOrReplace(nomeCognome);
            item.addOrReplace(iscrizioneLabel).setRenderBodyOnly(true);

            item.addOrReplace(statoChiusuraIscrizione);
          }

          private boolean isIscrittoServizioRistorazione(UtenteServiziRistorazione iscritto) {
            // TODO Auto-generated method stub
            return iscritto
                .getStatoIscrizioneServiziRistorazione()
                .equalsIgnoreCase(
                    UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.ACCETTATA
                        .value());
          }

          public boolean isVisibleCedole(UtenteServiziRistorazione iscritto) {

            int calcoloEta = LocalDateUtil.getMesiEta(iscritto.getDataNascita());
            if (calcoloEta > 12) {
              int anni = calcoloEta / 12;
              if ((anni > 4)) {
                log.debug("test_eta " + iscritto.getDataNascita());
                return true;
              } else {
                return false;
              }
            } else {
              return false;
            }
          }

          private boolean isVisibileLinkPartitario(UtenteServiziRistorazione iscritto) {
            if (iscritto.getCodiceFiscaleImpegnato() == null
                || getUtente().getCodiceFiscaleOperatore() == null) {
              return false;
            }

            // FDCOMGE-61
            /*
             * return (iscritto.getCodiceFiscaleImpegnato().equals(getUtente().
             * getCodiceFiscaleOperatore()) && isVisibileChiusuraIscrizione(iscritto));
             */

            return isVisibileChiusuraIscrizione(iscritto);
          }

          private boolean isVisibileSituazionePagamenti(
              UtenteServiziRistorazione iscritto, String situazioneSinteticaPagamenti) {
            return isVisibileChiusuraIscrizione(iscritto);
          }

          private boolean isVisibileChiusuraIscrizione(UtenteServiziRistorazione iscritto) {
            return !isVisibileLinkNuovaIscrizione(iscritto);
          }

          private boolean isVisibleLinkChiusuraIscrizioneRistorazione(
              UtenteServiziRistorazione iscritto) {
            if (iscritto
                .getCodiceFiscaleImpegnato()
                .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())) {
              return true;
            } else {
              return false;
            }
          }

          private boolean isImpegnatoAlPagamento(UtenteServiziRistorazione iscritto) {
            if (iscritto != null && iscritto.getCodiceFiscaleImpegnato() != null) {
              return iscritto
                  .getCodiceFiscaleImpegnato()
                  .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore());
            } else {
              return false;
            }
          }

          private String getStringSituazionePagamentiByCfIscritto(String codiceFiscale) {
            String toRet = "";
            try {
              toRet =
                  ServiceLocator.getInstance()
                      .getServiziRistorazione()
                      .getSituazioneSintenticoPagamenti(codiceFiscale);
            } catch (BusinessException | ApiException e) {
              log.error("Errore durante l'invio della domanda:", e);
              error("Errore durante l'invio della domanda");
            }
            return toRet;
          }

          private LocalDate getDataDomandaDaVisualizzare(final UtenteServiziRistorazione iscritto) {

            if (StatoIscrizioneServiziRistorazioneEnum.ACCETTATA
                    .value()
                    .equalsIgnoreCase(iscritto.getStatoIscrizioneServiziRistorazione())
                || StatoIscrizioneServiziRistorazioneEnum.SOSPESA
                    .value()
                    .equalsIgnoreCase(iscritto.getStatoIscrizioneServiziRistorazione())) {
              return iscritto.getDataIscrizioneServiziRistorazione() == null
                  ? null
                  : iscritto.getDataIscrizioneServiziRistorazione();
            } else if (StatoIscrizioneServiziRistorazioneEnum.IN_ELABORAZIONE
                    .value()
                    .equalsIgnoreCase(iscritto.getStatoIscrizioneServiziRistorazione())
                || StatoIscrizioneServiziRistorazioneEnum.INVIATA
                    .value()
                    .equalsIgnoreCase(iscritto.getStatoIscrizioneServiziRistorazione())) {
              return iscritto.getDataPresentazioneDomandaIscrizioneServiziRistorazione() == null
                  ? null
                  : iscritto.getDataPresentazioneDomandaIscrizioneServiziRistorazione();
            } else {
              return null;
            }
          }

          private boolean isVisibileDataIscrizione(UtenteServiziRistorazione iscritto) {
            LocalDate dataDomanda = getDataDomandaDaVisualizzare(iscritto);
            if (dataDomanda == null) return false;
            else return true;
          }

          private boolean isVisibileLinkNuovaIscrizione(UtenteServiziRistorazione iscritto) {
            if (StatoIscrizioneServiziRistorazioneEnum.NON_ISCRITTO
                .value()
                .equalsIgnoreCase(iscritto.getStatoIscrizioneServiziRistorazione())) return true;
            else return false;
          }

          private boolean isVisibileLinkMensa(final UtenteServiziRistorazione iscritto) {
            log.debug("debut " + iscritto);
            return (iscritto
                    .getStatoIscrizioneServiziRistorazione()
                    .equalsIgnoreCase(
                        UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.ACCETTATA
                            .value())
                && iscritto.getBambinoMangia()
                && iscritto.getScuolaConMensa()
                && (!isStatale(iscritto)));
          }

          private boolean isStatoIscrizionePerLinkChiusura(
              final UtenteServiziRistorazione iscritto) {
            return (iscritto
                    .getStatoIscrizioneServiziRistorazione()
                    .equalsIgnoreCase(
                        UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.ACCETTATA
                            .value())
                || iscritto
                    .getStatoIscrizioneServiziRistorazione()
                    .equalsIgnoreCase(
                        UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.SOSPESA
                            .value()));
          }

          private IModel<?> getTextIscrizione(UtenteServiziRistorazione iscritto) {
            String testo = "";
            if (iscritto
                .getSesso()
                .equalsIgnoreCase(UtenteServiziRistorazione.SessoEnum.M.value())) {
              testo = getString("RiepilogoIscrizioniPanel.iscritto.m") + ":";
            } else {
              testo = getString("RiepilogoIscrizioniPanel.iscritto.f") + ":";
            }
            return new Model<>(testo);
          }

          private boolean isVisibileScuola(UtenteServiziRistorazione iscritto) {
            if (StatoIscrizioneServiziRistorazioneEnum.NON_ISCRITTO
                .value()
                .equalsIgnoreCase(iscritto.getStatoIscrizioneServiziRistorazione())) return false;
            else if (iscritto.getStrutturaScolastica() != null)
              return !iscritto.getStrutturaScolastica().equalsIgnoreCase("null");
            else return false;
          }

          private boolean isVisibileClasse(final UtenteServiziRistorazione iscritto) {
            if (iscritto.getClasse() != null) {
              if (iscritto.getClasse().equalsIgnoreCase("null")) {
                return false;
              } else if (iscritto.getClasse().equalsIgnoreCase("0")) {
                return false;
              } else {
                return true;
              }
            } else {
              return false;
            }
          }

          private boolean isVisibileSezione(final UtenteServiziRistorazione iscritto) {
            if (iscritto.getSezione() != null)
              return !iscritto.getSezione().equalsIgnoreCase("null");
            else return false;
          }

          private AttributeAppender getCssIconaMaschioFemmina(UtenteServiziRistorazione iscritto) {
            String css = "";
            if (iscritto
                .getSesso()
                .equalsIgnoreCase(UtenteServiziRistorazione.SessoEnum.M.value())) {
              css = ICON_BIMBO;
            } else {
              css = ICON_BIMBA;
            }
            return new AttributeAppender("class", " " + css);
          }

          private boolean isStatale(final UtenteServiziRistorazione iscritto) {

            String strutturaScolastica = iscritto.getCategoriaStrutturaScolastica();
            if (StringUtils.isNotBlank(strutturaScolastica)) {
              strutturaScolastica = strutturaScolastica.toUpperCase();
              return strutturaScolastica.contains(STATALE);
            } else {
              return false;
            }
          }

          //			private LaddaAjaxLink<Object> creaLinkNuovaIscrizione(UtenteServiziRistorazione
          // iscritto) {
          //				LaddaAjaxLink<Object> linkNuovaIscrizione = new
          // LaddaAjaxLink<Object>("nuovaIscrizione", Type.Link) {
          //
          //					private static final long serialVersionUID = -2685108981634950441L;
          //
          //					@Override
          //					public void onClick(AjaxRequestTarget target) {
          //						setResponsePage(new RichiestaIscrizionePage(iscritto));
          //					}
          //				};
          //
          //				String testoLinkIscrizione = getTestoLabelLinkIscrizioneMensa(iscritto);
          //				linkNuovaIscrizione.setLabel(Model.of(testoLinkIscrizione));
          //
          //				AttributeModifier attributeModifier = new AttributeModifier("class", "btn-link
          // ladda-button");
          //				linkNuovaIscrizione.add(attributeModifier);
          //				linkNuovaIscrizione.setSpinnerColor("#0066cb");
          //				linkNuovaIscrizione.setVisible(visibilitaLinkIscrizioneMensa(iscritto));
          //
          //				return linkNuovaIscrizione;
          //			}

          private LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> creaLinkNuovaIscrizionePNRR(
              UtenteServiziRistorazione iscritto) {
            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> nuovaIscrizione =
                new LinkDinamicoLaddaFunzione<>(
                    "nuovaIscrizionePNRR",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.nuovaIscrizione")
                        .setWicketClassName("DomandaIscrizioneMensaResidentePage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid",
                    getUtente().isResidente() && visibilitaLinkIscrizioneMensa(iscritto));

            return nuovaIscrizione;
          }

          private LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> creaLinkNuovaIscrizione(
              UtenteServiziRistorazione iscritto) {
            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> nuovaIscrizione =
                new LinkDinamicoLaddaFunzione<>(
                    "nuovaIscrizione",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.nuovaIscrizione")
                        .setWicketClassName("RichiestaIscrizionePage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid",
                    getUtente().isResidente() && visibilitaLinkIscrizioneMensa(iscritto));

            return nuovaIscrizione;
          }

          private LinkDinamicoLaddaFunzione<UtenteServiziRistorazione>
              creaLinkNuovaIscrizioneNonResidente(UtenteServiziRistorazione iscritto) {
            LinkDinamicoLaddaFunzione<UtenteServiziRistorazione> iscrizioneMensaNonResidente =
                new LinkDinamicoLaddaFunzione<>(
                    "iscrizioneMensaNonResidente",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("RiepilogoIscrizioniPanel.nuovaIscrizione")
                        .setWicketClassName("RichiestaIscrizioneMensaNonResidentePage")
                        .setLinkTitleAdditionalText(iscritto.getNome())
                        .build(),
                    iscritto,
                    RiepilogoIscrizioniPanel.this,
                    "color-blue-spid",
                    !getUtente().isResidente() && visibilitaLinkIscrizioneMensa(iscritto));

            return iscrizioneMensaNonResidente;
          }

          private String getTestoLabelLinkIscrizioneMensa(UtenteServiziRistorazione iscritto) {
            String testoLabel = "";

            if (iscritto.getStatoIscrizioneServiziRistorazione().equalsIgnoreCase("Non iscritto")) {
              testoLabel = getString("RiepilogoIscrizioniPanel.nuovaIscrizione");
            } else {
              if (iscritto.getCodiceFiscaleImpegnato() != null) {
                if (getUtente()
                    .getCodiceFiscaleOperatore()
                    .equalsIgnoreCase(iscritto.getCodiceFiscaleImpegnato())) {
                  testoLabel = getString("RiepilogoIscrizioniPanel.nuovaIscrizione");
                } else {
                  testoLabel = getString("RiepilogoIscrizioniPanel.iscrizioneAccettata");
                }
              } else {
                testoLabel = getString("RiepilogoIscrizioniPanel.iscrizioneAccettata");
              }
            }

            return testoLabel;
          }

          //			private boolean visibilitaLinkIscrizioneMensa(UtenteServiziRistorazione iscritto) {
          //				boolean visibileLink = false;
          //
          //				if (iscritto.getStatoIscrizioneServiziRistorazione().equalsIgnoreCase("Non
          // iscritto")) {
          //					visibileLink = true;
          //				} else {
          //					if (iscritto.getCodiceFiscaleImpegnato() != null) {
          //						if (getUtente().getCodiceFiscaleOperatore()
          //								.equalsIgnoreCase(iscritto.getCodiceFiscaleImpegnato())) {
          //							if (iscritto.getStatoIscrizioneServiziRistorazione().equalsIgnoreCase("Sospesa"))
          // {
          //								visibileLink = true;
          //							} else {
          //								visibileLink = false;
          //							}
          //						} else {
          //							visibileLink = true;
          //						}
          //					} else {
          //						visibileLink = true;
          //					}
          //				}
          //				return visibileLink;
          //			}

          private boolean visibilitaLinkIscrizioneMensa(UtenteServiziRistorazione iscritto) {
            boolean isStatoNonIscritto =
                iscritto.getStatoIscrizioneServiziRistorazione().equalsIgnoreCase("Non iscritto");

            boolean isImpegnatoAlPagamento =
                PageUtil.isStringValid(iscritto.getCodiceFiscaleImpegnato())
                    && getUtente()
                        .getCodiceFiscaleOperatore()
                        .equalsIgnoreCase(iscritto.getCodiceFiscaleImpegnato());
            boolean isStatoSospesa =
                iscritto.getStatoIscrizioneServiziRistorazione().equalsIgnoreCase("Sospesa");

            boolean isLinkVisibile =
                isStatoNonIscritto || (isImpegnatoAlPagamento && isStatoSospesa);

            log.debug("isStatoNonIscritto = " + isStatoNonIscritto);
            log.debug("isImpegnatoAlPagamento = " + isImpegnatoAlPagamento);
            log.debug("isStatoSospesa = " + isStatoSospesa);
            log.debug("isLinkVisibile = " + isLinkVisibile);

            boolean isRichiestaIscrizioneNonPossibile = isImpegnatoAlPagamento && !isStatoSospesa;
            log.debug("isRichiestaIscrizioneNonPossibile = " + isRichiestaIscrizioneNonPossibile);

            return !isRichiestaIscrizioneNonPossibile;
          }
        };

    addOrReplace(listView);
  }

  // TODO commento ma funziona, serve per gestione log persistenti JIRA FASCITT-1003
  //	@Override
  //	protected void onAfterRender() {
  //		log.debug("CP onAfterRender panel");
  //
  //		Response response = getPage().getResponse();
  //		log.debug("CP response html = " + response);
  //		bytes = response.toString().getBytes();
  //
  //		/*FileWriter fWriter = null;
  //		BufferedWriter writer = null;
  //		try {
  //		    fWriter = new FileWriter("BambiniAScuola.html");
  //		    writer = new BufferedWriter(fWriter);
  //		    writer.write(response.toString());
  //		    writer.close();
  //		} catch (Exception e) {
  //			log.error(
  //					"RiepilogoIscrizioniPanel -- creazione file html: errore:" + e.getMessage());
  //		}*/
  //
  //		super.onAfterRender();
  //	}
  //
  //	private void addDownloadLink() {
  //		final AJAXDownload download = new AJAXDownload() {
  //
  //			private static final long serialVersionUID = 4070594401840261669L;
  //
  //			@Override
  //			protected IResourceStream getResourceStream() {
  //
  //				byte[] pdf = null;
  //
  //				try {
  //					pdf = bytes;
  //
  //				} catch (final Exception e) {
  //					log.error(
  //							"[RiepilogoIscrizioniPanel] Errore durante scaricamento del file html creato: "
  //									+ e.getMessage(),
  //							e);
  //					error("Errore durante scaricamento del file html creato");
  //				}
  //				return PageUtil.createResourceStream(pdf);
  //			}
  //
  //			@Override
  //			protected String getFileName() {
  //				return "Provaaaa.html";
  //			}
  //		};
  //		add(download);
  //
  //		final AjaxLink<Page> linkDownload = new AjaxLink<Page>("btnDownload") {
  //
  //			@Override
  //			protected void onComponentTag(final ComponentTag tag) {
  //				super.onComponentTag(tag);
  //				tag.put("target", "_blank");
  //			}
  //
  //			@Override
  //			public void onClick(final AjaxRequestTarget target) {
  //				download.initiate(target);
  //			}
  //		};
  //		add(linkDownload);
  //	}

}
