package it.liguriadigitale.ponmetro.portale.presentation.pages.segnalazionedanni.panel;

import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.TipologiaRichiestaEnum;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.model.ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner;
import java.time.LocalDate;
import java.util.List;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.StringResourceModel;

public class DomandeSegnalazioneDanniBeniPrivatiPanel extends BasePanel {

  private static final long serialVersionUID = 1L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private TipologiaRichiestaEnum tipologia;

  private static final String ICON_FOGLI = "color-fc-pink col-3 icon-fogli";

  public TipologiaRichiestaEnum getTipologia() {
    return tipologia;
  }

  public void setTipologia(TipologiaRichiestaEnum tipologia) {
    this.tipologia = tipologia;
  }

  public DomandeSegnalazioneDanniBeniPrivatiPanel(
      String id,
      List<ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner> segnalazioni,
      TipologiaRichiestaEnum tipologia) {
    super(id);

    createFeedBackPanel();
    setOutputMarkupId(true);
    setTipologia(tipologia);
    fillDati(segnalazioni);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner>
        listaSegnalazioni =
            (List<ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    NotEmptyLabel messaggioListaVuota =
        new NotEmptyLabel(
            "messaggioListaVuota",
            new StringResourceModel("DomandeSegnalazioneDanniBeniPrivatiPanel.vuoto", this)
                .setParameters(TipologiaRichiestaEnum.SEGNALAZIONE_DANNI_BENI_PRIVATI));
    listaVuota.addOrReplace(messaggioListaVuota);
    listaVuota.setVisible(!checkSegnalazioni(listaSegnalazioni));
    addOrReplace(listaVuota);

    PageableListView<ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner>
        listView =
            new PageableListView<
                ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner>(
                "segnalazioniDanni", listaSegnalazioni, 4) {

              private static final long serialVersionUID = 1L;

              @Override
              protected void populateItem(
                  ListItem<ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner>
                      itemSegnalazione) {
                final ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner
                    segnalazione = itemSegnalazione.getModelObject();

                WebMarkupContainer icona = new WebMarkupContainer("icona");
                icona.add(getCssIcona());
                itemSegnalazione.addOrReplace(icona);

                itemSegnalazione.addOrReplace(new NotEmptyLabel("titolo", segnalazione.getName()));

                itemSegnalazione.addOrReplace(
                    new AmtCardLabel<>(
                        "id", segnalazione.getId(), DomandeSegnalazioneDanniBeniPrivatiPanel.this));

                itemSegnalazione.addOrReplace(
                    new AmtCardLabel<>(
                        "eventoCalamitoso",
                        segnalazione.getEventoCalamitosoC(),
                        DomandeSegnalazioneDanniBeniPrivatiPanel.this));

                itemSegnalazione.addOrReplace(
                    new AmtCardLabel<>(
                        "esito",
                        segnalazione.getEsitoVerificheC(),
                        DomandeSegnalazioneDanniBeniPrivatiPanel.this));

                LocalDate dataEvento = null;
                if (LabelFdCUtil.checkIfNotNull(segnalazione.getDataEventoCalamitosoC())) {
                  dataEvento =
                      LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(
                          segnalazione.getDataEventoCalamitosoC());
                }
                itemSegnalazione.addOrReplace(
                    new AmtCardLabel<>(
                        "dataEvento", dataEvento, DomandeSegnalazioneDanniBeniPrivatiPanel.this));

                itemSegnalazione.addOrReplace(
                    new AmtCardLabel<>(
                        "tipologiaRichiedente",
                        segnalazione.getTipologiaRichiedenteC(),
                        DomandeSegnalazioneDanniBeniPrivatiPanel.this));

                itemSegnalazione.addOrReplace(
                    new AmtCardLabel<>(
                        "stato",
                        segnalazione.getStatoFrontendC(),
                        DomandeSegnalazioneDanniBeniPrivatiPanel.this));

                String pdfMatrimonio = "";
                if (PageUtil.isStringValid(segnalazione.getUrlPubblicoPdfC())) {
                  pdfMatrimonio = segnalazione.getUrlPubblicoPdfC();
                }

                ExternalLink uRLPubblicoPdfC = new ExternalLink("uRLPubblicoPdfC", pdfMatrimonio);
                uRLPubblicoPdfC.setVisible(PageUtil.isStringValid(pdfMatrimonio));
                itemSegnalazione.addOrReplace(uRLPubblicoPdfC);
              }
            };

    listView.setVisible(checkSegnalazioni(listaSegnalazioni));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(
        checkSegnalazioni(listaSegnalazioni) && listaSegnalazioni.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkSegnalazioni(
      List<ServicesDataV580SobjectsAccountIdProcedimentiRGet200ResponseRecordsInner>
          listaSegnalazioni) {
    return LabelFdCUtil.checkIfNotNull(listaSegnalazioni)
        && !LabelFdCUtil.checkEmptyList(listaSegnalazioni);
  }

  private AttributeAppender getCssIcona() {

    return new AttributeAppender("class", " " + ICON_FOGLI);
  }
}
