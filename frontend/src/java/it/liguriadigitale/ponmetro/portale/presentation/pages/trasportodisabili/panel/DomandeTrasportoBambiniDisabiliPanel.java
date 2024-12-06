package it.liguriadigitale.ponmetro.portale.presentation.pages.trasportodisabili.panel;

import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.DomandeInviateRecordsInner;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class DomandeTrasportoBambiniDisabiliPanel extends BasePanel {

  private static final long serialVersionUID = 2992378006382767303L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public DomandeTrasportoBambiniDisabiliPanel(
      String id, List<DomandeInviateRecordsInner> trasporti) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    fillDati(trasporti);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<DomandeInviateRecordsInner> listaDomandeTrasportoBambiniDisabili =
        (List<DomandeInviateRecordsInner>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkTrasporti(listaDomandeTrasportoBambiniDisabili));
    addOrReplace(listaVuota);

    PageableListView<DomandeInviateRecordsInner> listView =
        new PageableListView<DomandeInviateRecordsInner>(
            "trasportodisabili", listaDomandeTrasportoBambiniDisabili, 4) {

          private static final long serialVersionUID = 5670876069947623574L;

          @Override
          protected void populateItem(ListItem<DomandeInviateRecordsInner> itemTrasporto) {
            final DomandeInviateRecordsInner trasporto = itemTrasporto.getModelObject();

            log.debug("CP trasporto = " + trasporto);
            itemTrasporto.addOrReplace(new NotEmptyLabel("name", trasporto.getName()));

            itemTrasporto.addOrReplace(
                new AmtCardLabel<>(
                        "id", trasporto.getId(), DomandeTrasportoBambiniDisabiliPanel.this)
                    .setVisible(false));
            itemTrasporto.addOrReplace(
                new AmtCardLabel<>(
                        "createdDate",
                        LocalDateUtil.getDataOraCorretteInItalia(trasporto.getCreatedDate()),
                        DomandeTrasportoBambiniDisabiliPanel.this)
                    .setVisible(false));
            itemTrasporto.addOrReplace(
                new AmtCardLabel<>(
                    "annoprotocollazionec",
                    trasporto.getAnnoProtocollazioneC(),
                    DomandeTrasportoBambiniDisabiliPanel.this));
            itemTrasporto.addOrReplace(
                new AmtCardLabel<>(
                    "codicefiscalebeneficiarioc",
                    trasporto.getCodiceFiscaleBeneficiarioC(),
                    DomandeTrasportoBambiniDisabiliPanel.this));
            itemTrasporto.addOrReplace(
                new AmtCardLabel<>(
                        "codicefiscalerichiedentec",
                        trasporto.getCodiceFiscaleRichiedenteC(),
                        DomandeTrasportoBambiniDisabiliPanel.this)
                    .setVisible(false));
            itemTrasporto.addOrReplace(
                new AmtCardLabel<>(
                    "datanascitabeneficiarioc",
                    LocalDateUtil.getDataFormatoEuropeo(trasporto.getDataNascitaBeneficiarioC()),
                    DomandeTrasportoBambiniDisabiliPanel.this));
            itemTrasporto.addOrReplace(
                new AmtCardLabel<>(
                    "dataprotocollazionec",
                    trasporto.getDataProtocollazioneC(),
                    DomandeTrasportoBambiniDisabiliPanel.this));
            itemTrasporto.addOrReplace(
                new AmtCardLabel<>(
                    "nomebeneficiarioc",
                    trasporto
                        .getNomeBeneficiarioC()
                        .concat(" ")
                        .concat(trasporto.getCognomeBeneficiarioC()),
                    DomandeTrasportoBambiniDisabiliPanel.this));
            itemTrasporto.addOrReplace(
                new AmtCardLabel<>(
                        "nomerichiedentec",
                        trasporto
                            .getNomeRichiedenteC()
                            .concat(" ")
                            .concat(trasporto.getCognomeRichiedenteC()),
                        DomandeTrasportoBambiniDisabiliPanel.this)
                    .setVisible(false));
            itemTrasporto.addOrReplace(
                new AmtCardLabel<>(
                    "numeroprotocolloc",
                    trasporto.getNumeroProtocolloC(),
                    DomandeTrasportoBambiniDisabiliPanel.this));
            itemTrasporto.addOrReplace(
                new AmtCardLabel<>(
                    "statopraticac",
                    trasporto.getStatoFrontendC(),
                    DomandeTrasportoBambiniDisabiliPanel.this));
            String pdfTrasporto = "";
            if (PageUtil.isStringValid(trasporto.getUrlPubblicoPdfC())) {
              pdfTrasporto = trasporto.getUrlPubblicoPdfC();
            }
            ExternalLink uRLPubblicoPdfC = new ExternalLink("urlpdfpubblico", pdfTrasporto);
            uRLPubblicoPdfC.setVisible(PageUtil.isStringValid(pdfTrasporto));
            itemTrasporto.addOrReplace(uRLPubblicoPdfC);
          }
        };

    listView.setVisible(checkTrasporti(listaDomandeTrasportoBambiniDisabili));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(
        checkTrasporti(listaDomandeTrasportoBambiniDisabili)
            && listaDomandeTrasportoBambiniDisabili.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkTrasporti(
      List<DomandeInviateRecordsInner> listaDomandeTrasportoBambiniDisabili) {
    return LabelFdCUtil.checkIfNotNull(listaDomandeTrasportoBambiniDisabili)
        && !LabelFdCUtil.checkEmptyList(listaDomandeTrasportoBambiniDisabili);
  }
}
