package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.AvvisoPagamento;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbali;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbaliImportiPagabili;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbaliIuv0;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.PagamentoOnLinePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Rata;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class DettaglioPagamentiRatePanel extends BasePanel {

  private DettaglioVerbale _dettaglioVerbale;

  final String EMPTY = "";

  /** */
  private static final long serialVersionUID = 7230352860083311900L;

  public DettaglioPagamentiRatePanel(String id) {
    super(id);
    // TODO Auto-generated constructor stub

    setOutputMarkupId(true);
  }

  public DettaglioPagamentiRatePanel(String id, DettaglioVerbale dettaglioVerbale) {
    super(id);
    _dettaglioVerbale = dettaglioVerbale;

    setOutputMarkupId(true);
    fillDati(dettaglioVerbale);
  }

  @Override
  public void fillDati(Object dati) {

    // addOrReplace(creaBottoneProssimaRataDaPagare());
    addOrReplace(creaBottonePagamentoImportoResiduo());

    // creaScaricaAvvisoProssimaRata();
    creaScaricaAvvisoImportoResiduo();

    log.debug("[DettaglioPagamentiRatePanel] Recupero l'elenco delle prossime 3 rate da pagare");
    List<Rata> prossimeRate =
        LabelFdCUtil.checkIfNull(_dettaglioVerbale.getImportiPagabili())
            ? new ArrayList<Rata>()
            : getProssimeRate(_dettaglioVerbale.getImportiPagabili().getRate());
    log.debug("[DettaglioPagamentiRatePanel] Rate da pagare: " + prossimeRate);

    String codiceAvvisoProssimaRata = getCodiceAvvisoProssimaRata();
    log.debug(
        "[DettaglioPagamentiRatePanel] Codice IUV Prossima Rata da pagare: "
            + codiceAvvisoProssimaRata);

    PageableListView<Rata> listRate =
        new PageableListView<Rata>("rate", prossimeRate, 4) {

          /** */
          private static final long serialVersionUID = 7230352860083311878L;

          @Override
          protected void populateItem(ListItem<Rata> itemRata) {

            final Rata rata = itemRata.getModelObject();

            itemRata.addOrReplace(
                new AmtCardLabel<>("rata", rata.getNumero(), DettaglioPagamentiRatePanel.this));

            itemRata.addOrReplace(
                new AmtCardLabel<>(
                    "importo", rata.getImporto().floatValue(), DettaglioPagamentiRatePanel.this));

            itemRata.addOrReplace(
                new AmtCardLabel<>(
                    "dataEntro", rata.getDataEntro(), DettaglioPagamentiRatePanel.this));

            AmtCardLabel<Component> codiceAvviso =
                new AmtCardLabel<>(
                    "codiceAvviso", codiceAvvisoProssimaRata, DettaglioPagamentiRatePanel.this);
            codiceAvviso.setVisible(itemRata.getIndex() == 0);
            itemRata.addOrReplace(codiceAvviso);

            if (itemRata.getIndex() == 0) {
              creaScaricaAvvisoProssimaRata(itemRata);
              itemRata.addOrReplace(creaBottoneProssimaRataDaPagare(rata));
            }
          }
        };

    addOrReplace(listRate);
  }

  private void creaScaricaAvvisoImportoResiduo() {

    // TODO Auto-generated method stub
    if (UtilVerbali.checkIfNull(_dettaglioVerbale.getImportiPagabili())) {
      addOrReplace(new WebMarkupContainer("pdfAvvisoImportoResiduo").setVisible(false));
      return;
    }

    ResourceLink<?> pdfAvvisoPagamentoImportoResiduo = null;
    AvvisoPagamento avvisoPagamentoImportoResiduo =
        UtilVerbaliImportiPagabili.getAvvisoPagamento(
            getUtente(),
            _dettaglioVerbale.getImportiPagabili().getCodiceAvvisoImportoResiduoRate(),
            true,
            true);

    if (UtilVerbali.checkIfNotNull(avvisoPagamentoImportoResiduo)) {
      pdfAvvisoPagamentoImportoResiduo =
          UtilVerbaliImportiPagabili.downloadPdfAvviso(
              avvisoPagamentoImportoResiduo, "pdfAvvisoImportoResiduo");
      addOrReplace(pdfAvvisoPagamentoImportoResiduo);
    } else {
      addOrReplace(new WebMarkupContainer("pdfAvvisoImportoResiduo").setVisible(false));
    }
  }

  private void creaScaricaAvvisoProssimaRata(ListItem<Rata> itemRata) {

    // TODO Auto-generated method stub
    if (UtilVerbali.checkIfNull(_dettaglioVerbale.getImportiPagabili())
        && UtilVerbali.checkIfNull(
            _dettaglioVerbale.getImportiPagabili().getCodiceAvvisoRataPagabile())) {
      itemRata.addOrReplace(new WebMarkupContainer("pdfAvvisoRataInScadenza").setVisible(false));
      return;
    }

    ResourceLink<?> pdfProssimaRataPagabile = null;
    AvvisoPagamento avvisoRataPagabile =
        UtilVerbaliImportiPagabili.getAvvisoPagamento(
            getUtente(),
            _dettaglioVerbale.getImportiPagabili().getCodiceAvvisoRataPagabile(),
            true,
            false);

    if (UtilVerbali.checkIfNotNull(avvisoRataPagabile)) {
      pdfProssimaRataPagabile =
          UtilVerbaliImportiPagabili.downloadPdfAvviso(
              avvisoRataPagabile, "pdfAvvisoRataInScadenza");
      itemRata.addOrReplace(pdfProssimaRataPagabile);
    } else {
      itemRata.addOrReplace(new WebMarkupContainer("pdfAvvisoRataInScadenza").setVisible(false));
    }
  }

  private String getCodiceAvvisoProssimaRata() {

    if (LabelFdCUtil.checkIfNull(_dettaglioVerbale.getImportiPagabili())) {
      return EMPTY;
    }

    return LabelFdCUtil.checkIfNull(
            _dettaglioVerbale.getImportiPagabili().getCodiceAvvisoRataPagabile())
        ? EMPTY
        : _dettaglioVerbale.getImportiPagabili().getCodiceAvvisoRataPagabile();
  }

  private LaddaAjaxLink<Object> creaBottoneProssimaRataDaPagare(Rata nextRataPagabile) {
    return (LaddaAjaxLink<Object>)
        new LaddaAjaxLink<Object>("btnPagamentoRataInScadenza", Type.Default) {

          private static final long serialVersionUID = 1L;

          @Override
          public void onClick(AjaxRequestTarget arg0) {
            // TODO Auto-generated method stub

            // Rate nextRataPagabile = getNextRataPagabile();

            if (LabelFdCUtil.checkIfNotNull(nextRataPagabile)) {
              log.debug(
                  "[creaBottoneProssimaRataDaPagare] Procedo con il pagamento della prossima rata pagabile al MIP: "
                      + nextRataPagabile);

              MipData dataMip =
                  UtilVerbaliIuv0.creaMipDataRata(
                      getUtente(),
                      _dettaglioVerbale.getImportiPagabili().getCodiceAvvisoRataPagabile(),
                      nextRataPagabile.getImporto());

              log.debug("[creaBottoneProssimaRataDaPagare] Dati Mip: " + dataMip);

              PagamentoOnLinePage page = new PagamentoOnLinePage(dataMip);
              setResponsePage(page);
            }
          }
        }.setLabel(
            Model.of(
                Application.get()
                    .getResourceSettings()
                    .getLocalizer()
                    .getString(
                        "DettaglioPagamentiRatePanel.pagaRataInScadenza",
                        DettaglioPagamentiRatePanel.this)));
  }

  private LaddaAjaxLink<Object> creaBottonePagamentoImportoResiduo() {
    LaddaAjaxLink<Object> btnImportoResiduo =
        (LaddaAjaxLink<Object>)
            new LaddaAjaxLink<Object>("btnPagamentoImportoResiduo", Type.Primary) {

              private static final long serialVersionUID = 1L;

              @Override
              public void onClick(AjaxRequestTarget arg0) {

                MipData mipData =
                    UtilVerbaliIuv0.creaMipDataRata(
                        getUtente(),
                        _dettaglioVerbale.getImportiPagabili().getCodiceAvvisoImportoResiduoRate(),
                        _dettaglioVerbale.getImportiPagabili().getImportoResiduoRate());

                log.debug(
                    "[creaBottonePagamentoImportoResiduo] Creati dati Mip per Pagamento dell'importo residuo: "
                        + mipData);

                PagamentoOnLinePage page = new PagamentoOnLinePage(mipData);
                setResponsePage(page);
              }
            }.setLabel(
                Model.of(
                    Application.get()
                        .getResourceSettings()
                        .getLocalizer()
                        .getString(
                            "DettaglioPagamentiRatePanel.pagaImportoTotaleResiduo",
                            DettaglioPagamentiRatePanel.this)));

    return btnImportoResiduo;
  }

  private List<Rata> getProssimeRate(List<Rata> listaRate) {

    if (LabelFdCUtil.checkIfNull(listaRate)) {
      return new ArrayList<Rata>();
    }

    Comparator<Rata> comparator =
        Comparator.comparingLong(Rata::getNumero).thenComparing(Rata::getDataEntro);

    return listaRate.stream().sorted(comparator).limit(1).collect(Collectors.toList());
  }

  private String getImportoResiduoStr() {
    String importoResiduoStr = "";

    NumberFormat numberFormatEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);

    if (LabelFdCUtil.checkIfNotNull(_dettaglioVerbale.getImportiPagabili())
        && LabelFdCUtil.checkIfNotNull(
            _dettaglioVerbale.getImportiPagabili().getImportoResiduoRate())) {
      importoResiduoStr =
          String.valueOf(
              numberFormatEuro.format(
                  _dettaglioVerbale.getImportiPagabili().getImportoResiduoRate()));
    } else {
      importoResiduoStr = String.valueOf(numberFormatEuro.format(_dettaglioVerbale.getImporto()));
    }

    return importoResiduoStr;
  }
}
