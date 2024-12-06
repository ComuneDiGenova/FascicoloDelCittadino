package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.avvisoBonario.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.AvvisoPagamento;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.DettaglioVerbaliPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbali;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbaliImportiPagabili;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbaliIuv0;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.PagamentoOnLinePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AvvisoBonario;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale.StatoEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.stream.Collectors;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class DettaglioAvvisoBonarioPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 198786546543218L;
  protected PaginazioneFascicoloPanel paginazioneFascicolo;
  DettaglioVerbale dettaglioVerbale;

  private static final String ICON_AUTOVEICOLO = "color-cyan col-3 icon-autoveicolo";
  private static final String ICON_MOTOVEICOLO = "color-cyan col-3 icon-motoveicolo";
  private static final String ICON_MOTOCICLO = "color-cyan col-3 icon-ciclomotore";
  private static final String ICON_VEICOLI = "color-cyan col-3 icon-mezzi";

  private static final String ICONA_AVVISOBONARIO = "icon-rimborsi-imu";

  public DettaglioAvvisoBonarioPanel(
      String id, AvvisoBonario avvisoBonario, DettaglioVerbale dettaglioVerbale) {
    // TODO Auto-generated constructor stub
    super(id, avvisoBonario);

    this.dettaglioVerbale = dettaglioVerbale;

    this.fillDati(avvisoBonario);
  }

  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub
    super.fillDati(dati);

    AvvisoBonario avvisoBonario = (AvvisoBonario) dati;

    WebMarkupContainer icona = new WebMarkupContainer("iconaAvviso");
    icona.add(new AttributeAppender("class", " " + ICONA_AVVISOBONARIO));
    icona.add(new AttributeModifier("style", "color: #008758"));
    form.addOrReplace(icona);

    creaScaricaAvvisoBonario("pdfAvvisoIuv", avvisoBonario);
    creaScaricaAvvisoBonario("pdfAvvisoIuvDue", avvisoBonario);

    form.addOrReplace(creaButtonPaga("paga", avvisoBonario));
    form.addOrReplace(creaButtonPaga("pagaDue", avvisoBonario));

    form.addOrReplace(
        new AmtCardLabel<>(
            "numero", dettaglioVerbale.getAvvisoBonario(), DettaglioAvvisoBonarioPanel.this));
    form.addOrReplace(
        new AmtCardLabel<>("stato", avvisoBonario.getStato(), DettaglioAvvisoBonarioPanel.this));
    form.addOrReplace(
        new AmtCardLabel<>(
            "importo", avvisoBonario.getImporto() + " €", DettaglioAvvisoBonarioPanel.this));
    form.addOrReplace(
        new AmtCardLabel<>("data", avvisoBonario.getData(), DettaglioAvvisoBonarioPanel.this));

    PageableListView<Verbale> lista =
        new PageableListView<Verbale>("lista", avvisoBonario.getVerbali(), 4) {
          private static final long serialVersionUID = 1546465487865321L;

          @Override
          protected void populateItem(ListItem<Verbale> row) {
            // TODO Auto-generated method stub
            Verbale item = (Verbale) row.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaVerbale(item.getTarga()));
            icona.add(
                getCssIconColor(
                    it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale
                        .StatoEnum.fromValue(item.getStato())));

            row.addOrReplace(icona);
            row.addOrReplace(
                new AmtCardLabel<>("stato", item.getStato(), DettaglioAvvisoBonarioPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>(
                    "numeroAvviso", item.getNumeroAvviso(), DettaglioAvvisoBonarioPanel.this));
            // row.addOrReplace(new AmtCardLabel<>("importo", item.getImporto() + " €",
            // DettaglioAvvisoBonarioPanel.this));
            String importoDaPagare = getImportoDaPagare(item);
            Label importo = new Label("importo", importoDaPagare);
            importo.add(getColoreImporto(item.getStato()));
            importo.setVisible(item.getImporto() != null);
            row.addOrReplace(importo);

            row.addOrReplace(
                new AmtCardLabel<>(
                    "dataAccertamento",
                    item.getDataAccertamento(),
                    DettaglioAvvisoBonarioPanel.this));

            row.addOrReplace(
                new AmtCardLabel<>(
                    "localita", item.getLocalita(), DettaglioAvvisoBonarioPanel.this));

            row.addOrReplace(
                new AmtCardLabel<>(
                    "articoliViolati",
                    item.getArticoliViolati().stream()
                        .map(
                            x -> {
                              return x.getArticoloDaVisualizzare();
                            })
                        .collect(Collectors.joining("\n")),
                    DettaglioAvvisoBonarioPanel.this));

            row.addOrReplace(creaLinkDettagliVerbale(item));

            row.addOrReplace(
                new AmtCardLabel<>(
                    "numeroProtocollo",
                    item.getNumeroProtocollo(),
                    DettaglioAvvisoBonarioPanel.this));
            row.addOrReplace(new Label("targa", item.getTarga()));
          }

          private String getImportoDaPagare(Verbale item) {
            String importoDaPagare;
            int compareToImporto = item.getImporto().compareTo(BigDecimal.ZERO);
            if (item.getStato().equalsIgnoreCase(StatoEnum.ARCHIVIATO.value())) {
              importoDaPagare = getString("DettaglioAvvisoBonarioPanel.nienteDaPagare");
            } else {
              if (compareToImporto == 0 || compareToImporto == -1) {
                importoDaPagare = getString("DettaglioAvvisoBonarioPanel.nienteDaPagare");
              } else {
                NumberFormat numberFormatEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
                importoDaPagare = numberFormatEuro.format(item.getImporto());
              }
            }
            return importoDaPagare;
          }

          private AttributeModifier getColoreImporto(String statoStr) {

            it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale.StatoEnum
                stato =
                    it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale
                        .StatoEnum.fromValue(statoStr);
            AttributeModifier attribute = null;

            switch (stato) {
              case ARCHIVIATO:
                attribute =
                    new AttributeModifier("style", "color: white;background-color: #008758");
                break;
              case IN_ATTESA_DI_VERIFICA:
                attribute =
                    new AttributeModifier("style", "color: white;background-color: #a66300");
                break;

              case INVIO_AD_AUTORITA_COMPETENTE:
              case ISCRITTO_A_RUOLO:
              case OGGETTO_DI_RICORSO:
                attribute = new AttributeModifier("style", "color: white;background-color: #06c");
                break;
              default:
                attribute =
                    new AttributeModifier("style", "color: white;background-color: #d9364f");
                break;
            }

            return attribute;
          }

          private AttributeModifier getCssIconColor(
              it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale.StatoEnum
                  stato) {
            // TODO Auto-generated method stub
            AttributeModifier attribute = null;

            switch (stato) {
              case APERTO:
                attribute = new AttributeModifier("style", "color: #d9364f");
                break;
              case ARCHIVIATO:
                attribute = new AttributeModifier("style", "color: #008758");
                break;
              case IN_ATTESA_DI_VERIFICA:
                attribute = new AttributeModifier("style", "color: #a66300");
                break;

              case INVIO_AD_AUTORITA_COMPETENTE:
              case ISCRITTO_A_RUOLO:
              case OGGETTO_DI_RICORSO:
                attribute = new AttributeModifier("style", "color: #06c");
                break;
            }

            return attribute;
          }

          private AttributeAppender getCssIconaVerbale(String targaVerbale) {
            String css = "";
            String regex = "[a-zA-Z]+";

            if (targaVerbale.length() == 8) {
              if (targaVerbale.substring(0, 3).matches(regex)) {
                css = ICON_AUTOVEICOLO;
              } else {
                css = ICON_MOTOVEICOLO;
              }
            } else if (targaVerbale.length() == 7) {
              if (targaVerbale.substring(5, 6).matches(regex)) {
                css = ICON_AUTOVEICOLO;
              } else {
                css = ICON_MOTOVEICOLO;
              }
            } else if (targaVerbale.length() == 5 || targaVerbale.length() == 6) {
              css = ICON_MOTOCICLO;
            } else {
              css = ICON_VEICOLI;
            }

            return new AttributeAppender("class", " " + css);
          }
        };

    form.addOrReplace(lista);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", lista);
    paginazioneFascicolo.setVisible(
        LabelFdCUtil.checkIfNotNull(avvisoBonario.getVerbali())
            && avvisoBonario.getVerbali().size() > 2);
    form.addOrReplace(paginazioneFascicolo);

    form.addOrReplace(creaButtonPaga("paga", avvisoBonario));
    form.addOrReplace(creaButtonAnnulla());
  }

  private void creaScaricaAvvisoBonario(String id, AvvisoBonario avvisoBonario) {

    ResourceLink<?> pdfAvvisoBonarioPagamento = null;
    AvvisoPagamento avvisoBonarioPagamento =
        UtilVerbaliImportiPagabili.getAvvisoPagamento(
            getUtente(), avvisoBonario.getIuv(), true, true);

    if (UtilVerbali.checkIfNotNull(avvisoBonarioPagamento)) {
      pdfAvvisoBonarioPagamento =
          UtilVerbaliImportiPagabili.downloadPdfAvviso(avvisoBonarioPagamento, id);
      form.addOrReplace(pdfAvvisoBonarioPagamento);
    } else {
      form.addOrReplace(new WebMarkupContainer(id).setVisible(false));
    }
  }

  private FdCButtonBootstrapAjaxLink<Object> creaButtonAnnulla() {
    FdCButtonBootstrapAjaxLink<Object> annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {
          private static final long serialVersionUID = 1L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DettaglioAvvisoBonarioPanel.this);
            form.clearInput();
            setResponsePage(new DettaglioVerbaliPage(dettaglioVerbale));
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DettaglioAvvisoBonarioPanel.annulla", DettaglioAvvisoBonarioPanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaButtonPaga(String id, AvvisoBonario avviso) {
    FdCButtonBootstrapAjaxLink<Object> paga =
        new FdCButtonBootstrapAjaxLink<Object>(id, Type.Primary) {
          private static final long serialVersionUID = 1L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            MipData mipData =
                UtilVerbaliIuv0.creaMipDataRata(getUtente(), avviso.getIuv(), avviso.getImporto());

            log.debug("[creaButtonPaga] Creati dati Mip per Pagamento dell'importo: " + mipData);

            PagamentoOnLinePage page = new PagamentoOnLinePage(mipData);
            setResponsePage(page);
          }
        };

    paga.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DettaglioAvvisoBonarioPanel.paga", DettaglioAvvisoBonarioPanel.this)));

    return paga;
  }

  private LaddaAjaxLink<Object> creaLinkDettagliVerbale(Verbale verbale) {
    LaddaAjaxLink<Object> linkDettagliVerbale =
        new LaddaAjaxLink<Object>("btnDettagliVerbale", Type.Primary) {

          private static final long serialVersionUID = 4181405244105128930L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DettaglioAvvisoBonarioPanel.this);
            setResponsePage(new DettaglioVerbaliPage(verbale));
          }
        };
    linkDettagliVerbale.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DettaglioAvvisoBonarioPanel.dettagliVerbale",
                    DettaglioAvvisoBonarioPanel.this)));

    return linkDettagliVerbale;
  }
}
