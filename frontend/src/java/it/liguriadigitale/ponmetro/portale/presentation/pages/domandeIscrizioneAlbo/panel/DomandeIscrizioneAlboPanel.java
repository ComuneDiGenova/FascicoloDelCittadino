package it.liguriadigitale.ponmetro.portale.presentation.pages.domandeIscrizioneAlbo.panel;

import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DomandeInviateRecordsInner;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.TipologiaRichiestaEnum;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.StringResourceModel;

public class DomandeIscrizioneAlboPanel extends BasePanel {

  private static final long serialVersionUID = 2992378006382767303L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private TipologiaRichiestaEnum tipologia;

  public TipologiaRichiestaEnum getTipologia() {
    return tipologia;
  }

  public void setTipologia(TipologiaRichiestaEnum tipologia) {
    this.tipologia = tipologia;
  }

  public DomandeIscrizioneAlboPanel(
      String id, List<DomandeInviateRecordsInner> iscrizioni, TipologiaRichiestaEnum tipologia) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    setTipologia(tipologia);

    fillDati(iscrizioni);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<DomandeInviateRecordsInner> listaIscrizioni = (List<DomandeInviateRecordsInner>) dati;

    String descrizioneFunzione = "";

    if (getTipologia().equals(TipologiaRichiestaEnum.PRESIDENTI)) {
      descrizioneFunzione = "Albo Presidenti";
    } else if (getTipologia().equals(TipologiaRichiestaEnum.SCRUTATORI)) {
      descrizioneFunzione = "Albo Scrutatori";
    }

    NotEmptyLabel titolo =
        new NotEmptyLabel(
            "titolo",
            new StringResourceModel("DomandeIscrizioneAlboPanel.titolo", this)
                .setParameters(descrizioneFunzione));
    addOrReplace(titolo);

    //		boolean x = mostraBottone(listaIscrizioni);
    //		NotEmptyLabel placeholder = new NotEmptyLabel("placeholder",
    //				new StringResourceModel("DomandeIscrizioneAlboPanel.placeholder", this).setParameters(x));
    //		addOrReplace(placeholder);

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    NotEmptyLabel messaggioListaVuota =
        new NotEmptyLabel(
            "messaggioListaVuota",
            new StringResourceModel("DomandeIscrizioneAlboPanel.vuoto", this)
                .setParameters(descrizioneFunzione));
    listaVuota.addOrReplace(messaggioListaVuota);
    listaVuota.setVisible(!checkRichieste(listaIscrizioni));
    addOrReplace(listaVuota);

    PageableListView<DomandeInviateRecordsInner> listView =
        new PageableListView<DomandeInviateRecordsInner>("iscrizioni", listaIscrizioni, 4) {

          private static final long serialVersionUID = 5670876069947623574L;

          @Override
          protected void populateItem(ListItem<DomandeInviateRecordsInner> itemIscrizione) {
            final DomandeInviateRecordsInner iscrizione = itemIscrizione.getModelObject();

            log.debug("record iscrizione = " + iscrizione);

            String titoloCard = "Pratica n." + iscrizione.getName();

            if (iscrizione.getTipologiaPraticaC().equalsIgnoreCase("Iscrizione Albo Elettorale")) {
              titoloCard = "Richiesta di iscrizione n." + iscrizione.getName();
            } else if (iscrizione
                .getTipologiaPraticaC()
                .equalsIgnoreCase("Rinnovo Disponibilità")) {
              titoloCard = "Segnalazione disponibilità n. " + iscrizione.getName();
            } else {
              titoloCard = "Richiesta di cancellazione n." + iscrizione.getName();
            }

            itemIscrizione.addOrReplace(new NotEmptyLabel("name", titoloCard));

            itemIscrizione.addOrReplace(
                new AmtCardLabel<>(
                    "statoPraticaC",
                    iscrizione.getStatoFrontendC(),
                    DomandeIscrizioneAlboPanel.this));

            String dataCreazione = "";
            if (LabelFdCUtil.checkIfNotNull(iscrizione.getCreatedDate())) {
              dataCreazione =
                  estraiGiornoDaStringaOffsetDateTimeAStringaFormatoEuropeo(
                      iscrizione.getCreatedDate());
            }
            itemIscrizione.addOrReplace(
                new AmtCardLabel<>("createdDate", dataCreazione, DomandeIscrizioneAlboPanel.this));

            //				String dataUltimaModifica = "";
            //				if(LabelFdCUtil.checkIfNotNull(iscrizione.getLastModifiedDate())) {
            //					dataUltimaModifica =
            // estraiGiornoDaStringaOffsetDateTimeAStringaFormatoEuropeo(iscrizione.getLastModifiedDate());
            //				}
            //				itemIscrizione.addOrReplace(
            //						new AmtCardLabel<>("lastModifiedDate",dataUltimaModifica,
            // DomandeIscrizioneAlboPanel.this));

            AmtCardLabel<Component> tornata =
                new AmtCardLabel<>(
                    "tornata",
                    iscrizione.getIdentificativoTornataElettoraleC(),
                    DomandeIscrizioneAlboPanel.this);
            tornata.setVisible(isRinnovo(iscrizione));
            itemIscrizione.addOrReplace(tornata);

            AmtCardLabel<Component> protocollo =
                new AmtCardLabel<>(
                    "numeroProtocolloC",
                    iscrizione.getNumeroProtocolloC(),
                    DomandeIscrizioneAlboPanel.this);
            protocollo.setVisible(!isRinnovo(iscrizione));
            itemIscrizione.addOrReplace(protocollo);

            //				itemIscrizione.addOrReplace(
            //						new AmtCardLabel<>("titoloStudio",iscrizione.getTitoloStudioC(),
            // DomandeIscrizioneAlboPanel.this));
            //
            //				itemIscrizione.addOrReplace(
            //						new
            // AmtCardLabel<>("titoloStudioPresso",iscrizione.getTitoloStudioConseguitoPressoC(),
            // DomandeIscrizioneAlboPanel.this));
            //
            //				itemIscrizione.addOrReplace(
            //						new
            // AmtCardLabel<>("titoloStudioComune",iscrizione.getComuneSedeIstitutoStudioC(),
            // DomandeIscrizioneAlboPanel.this));
            //
            //				itemIscrizione.addOrReplace(
            //						new
            // AmtCardLabel<>("titoloStudioAnno",iscrizione.getTitoloStudioConseguitoAnnoC(),
            // DomandeIscrizioneAlboPanel.this));
            //
            //				itemIscrizione.addOrReplace(
            //						new AmtCardLabel<>("professione",iscrizione.getProfessioneC(),
            // DomandeIscrizioneAlboPanel.this));
            //
            //				itemIscrizione.addOrReplace(
            //						new AmtCardLabel<>("luogoLavoro",iscrizione.getLuogoLavoroC(),
            // DomandeIscrizioneAlboPanel.this));
            //
            //				String funzioniSvolte = iscrizione.getFunzioniSvolteC();
            //				if (funzioniSvolte != null && funzioniSvolte.contains(";")) {
            //					funzioniSvolte = funzioniSvolte.replace(";", ", ");
            //				}
            //				itemIscrizione.addOrReplace(
            //						new AmtCardLabel<>("funzioniSvolte",funzioniSvolte,
            // DomandeIscrizioneAlboPanel.this));

            AmtCardLabel<Component> cardEmail =
                new AmtCardLabel<>(
                    "email", iscrizione.getRichiedente1EmailC(), DomandeIscrizioneAlboPanel.this);
            cardEmail.setVisible(!isRinnovo(iscrizione));
            itemIscrizione.addOrReplace(cardEmail);

            //				String pec = "";
            //				 if (LabelFdCUtil.checkIfNotNull(iscrizione.getPeCRichiedenteC())) {
            //					 pec = iscrizione.getPeCRichiedenteC();
            //				 }
            //				 iscrizione.getPeCRichiedenteC();
            //				itemIscrizione.addOrReplace(
            //						new AmtCardLabel<>("pec",iscrizione.getPeCRichiedenteC(),
            // DomandeIscrizioneAlboPanel.this));
            //
            //				itemIscrizione.addOrReplace(
            //						new AmtCardLabel<>("cittadinanza",iscrizione.getCittadinanzaC(),
            // DomandeIscrizioneAlboPanel.this));
            //
            //				itemIscrizione.addOrReplace(
            //						new
            // AmtCardLabel<>("indirizzo",iscrizione.getIndirizzoResidenzaRichiedenteStreetS(),
            // DomandeIscrizioneAlboPanel.this));

            String pdfMatrimonio = "";
            if (PageUtil.isStringValid(iscrizione.getUrlPubblicoPdfC())) {
              pdfMatrimonio = iscrizione.getUrlPubblicoPdfC();
            }
            ExternalLink uRLPubblicoPdfC = new ExternalLink("UrlPubblicoPdfC", pdfMatrimonio);
            uRLPubblicoPdfC.setVisible(PageUtil.isStringValid(pdfMatrimonio));
            itemIscrizione.addOrReplace(uRLPubblicoPdfC);
          }

          private boolean isRinnovo(DomandeInviateRecordsInner iscrizione) {
            return iscrizione.getTipoProcedimentoC().contains("Rinnovo");
          }
        };

    listView.setVisible(checkRichieste(listaIscrizioni));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkRichieste(listaIscrizioni) && listaIscrizioni.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkRichieste(List<DomandeInviateRecordsInner> listaIscrizioni) {
    return LabelFdCUtil.checkIfNotNull(listaIscrizioni)
        && !LabelFdCUtil.checkEmptyList(listaIscrizioni);
  }

  private String estraiGiornoDaStringaOffsetDateTimeAStringaFormatoEuropeo(String data) {
    try {
      if (data.contains(".000+0000")) data = data.replace(".000+0000", "");
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
      LocalDateTime ldt = LocalDateTime.parse(data, formatter);
      String toReturn = LocalDateUtil.getDataFormatoEuropeo(ldt.toLocalDate());
      log.warn("data modificata = " + toReturn);
      return toReturn;
    } catch (Exception e) {
      log.error(
          "Impossibile parsare la data ricevuta [" + data + "] a formato europeo, errore = " + e);
      return "";
    }
  }
}
