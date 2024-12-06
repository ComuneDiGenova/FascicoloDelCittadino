package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.sanzioniamt.panel;

import it.liguriadigitale.ponmetro.portale.pojo.amt.SanzioniAmtEsteso;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.ImportoEuroLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.List;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class SanzioniAMTPanel extends BasePanel {

  private static final long serialVersionUID = -8984009932245572044L;

  private static final String ICON_SANZIONE_AMT = "color-orange-amt col-3 icon-sanzione_amt";
  private static final String ICON_SANZIONE_AMT_DA_PAGARE =
      "color-fc-danger col-3 icon-sanzione_amt";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public SanzioniAMTPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<SanzioniAmtEsteso> listaSanzioniEsteso = (List<SanzioniAmtEsteso>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setOutputMarkupId(true);
    listaVuota.setOutputMarkupPlaceholderTag(true);
    listaVuota.setVisible(!checkPresenzaSanzioni(listaSanzioniEsteso));
    addOrReplace(listaVuota);

    PageableListView<SanzioniAmtEsteso> listView =
        new PageableListView<SanzioniAmtEsteso>("box", listaSanzioniEsteso, 4) {

          private static final long serialVersionUID = 1132608775678802978L;

          @Override
          protected void populateItem(ListItem<SanzioniAmtEsteso> item) {
            final SanzioniAmtEsteso sanzioniEsteso = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaSanzione(sanzioniEsteso));
            item.addOrReplace(icona);

            item.addOrReplace(new NotEmptyLabel("nominativo", sanzioniEsteso.getNominativo()));

            String serieNumero = "Verbale: ";
            if (PageUtil.isStringValid(sanzioniEsteso.getSERIE())) {
              serieNumero = serieNumero.concat(sanzioniEsteso.getSERIE());
            }
            if (PageUtil.isStringValid(sanzioniEsteso.getNUMERO())) {
              serieNumero = serieNumero.concat(sanzioniEsteso.getNUMERO());
            }
            Label serieNumeroLabel = new Label("serieNumero", serieNumero);
            serieNumeroLabel.setVisible(
                PageUtil.isStringValid(sanzioniEsteso.getSERIE())
                    || PageUtil.isStringValid(sanzioniEsteso.getNUMERO()));
            item.addOrReplace(serieNumeroLabel);

            item.addOrReplace(
                new AmtCardLabel<>("cfSanzione", sanzioniEsteso.getCf(), SanzioniAMTPanel.this));

            String dataOraVerbaleValue = "";
            if (LabelFdCUtil.checkIfNotNull(sanzioniEsteso.getDATAORAVERB())) {
              dataOraVerbaleValue =
                  LocalDateUtil.getStringOffsetDateTime(sanzioniEsteso.getDATAORAVERB());
            }
            item.addOrReplace(
                new AmtCardLabel<>("dataOraVerbale", dataOraVerbaleValue, SanzioniAMTPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>("linea", sanzioniEsteso.getLINEA(), SanzioniAMTPanel.this));

            ImportoEuroLabel totaleSanzione =
                new ImportoEuroLabel("totaleSanzione", sanzioniEsteso.getTOTALESANZIONE());
            if (PageUtil.isStringValid(sanzioniEsteso.getURLPAGAMENTO())) {
              AttributeAppender attributeAppenderBadgeChips =
                  new AttributeAppender("class", "badge rounded-pill bg-danger");
              totaleSanzione.add(attributeAppenderBadgeChips);
            }
            totaleSanzione.setVisible(
                LabelFdCUtil.checkIfNotNull(sanzioniEsteso.getTOTALESANZIONE()));
            item.addOrReplace(totaleSanzione);

            item.addOrReplace(
                new AmtCardLabel<>("pagato", sanzioniEsteso.getPAGATO(), SanzioniAMTPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>("stato", sanzioniEsteso.getSTATO(), SanzioniAMTPanel.this));

            ExternalLink urlPagamento =
                new ExternalLink("urlPagamento", sanzioniEsteso.getURLPAGAMENTO());
            urlPagamento.setVisible(PageUtil.isStringValid(sanzioniEsteso.getURLPAGAMENTO()));
            item.addOrReplace(urlPagamento);
          }
        };

    listView.setRenderBodyOnly(true);
    listView.setVisible(checkPresenzaSanzioni(listaSanzioniEsteso));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationSanzioni", listView);
    paginazioneFascicolo.setVisible(
        checkPresenzaSanzioni(listaSanzioniEsteso) && listaSanzioniEsteso.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkPresenzaSanzioni(List<SanzioniAmtEsteso> listaSanzioniEsteso) {
    return LabelFdCUtil.checkIfNotNull(listaSanzioniEsteso)
        && !LabelFdCUtil.checkEmptyList(listaSanzioniEsteso);
  }

  private AttributeAppender getCssIconaSanzione(SanzioniAmtEsteso sanzioneAmtEsteso) {
    String css = "";

    if (LabelFdCUtil.checkIfNotNull(sanzioneAmtEsteso)
        && PageUtil.isStringValid(sanzioneAmtEsteso.getURLPAGAMENTO())) {
      css = ICON_SANZIONE_AMT_DA_PAGARE;
    } else {
      css = ICON_SANZIONE_AMT;
    }

    return new AttributeAppender("class", " " + css);
  }
}
