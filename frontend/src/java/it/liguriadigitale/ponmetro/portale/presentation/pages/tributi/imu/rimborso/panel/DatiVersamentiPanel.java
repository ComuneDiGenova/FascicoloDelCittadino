package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel;

import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Versamento;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form.InsertVersamentoForm;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class DatiVersamentiPanel extends BasePanel {
  private static final long serialVersionUID = 798764321313136548L;

  private Versamento versamentoSingolo;

  WebMarkupContainer containerVersamento;

  FdcAjaxButton aggiungi;
  Label listaVuota;

  RimborsoImu rimborsoImu;
  PageableListView<Versamento> listView;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private static final String ICON_ACCETTATA = "color-fc-success col-3 icon-rimborsi-imu";

  public DatiVersamentiPanel(String id) {
    super(id);
    // TODO Auto-generated constructor stub
  }

  public DatiVersamentiPanel(String id, RimborsoImu rimborsoImu) {
    this(id);

    rimborsoImu.setVersamenti(new ArrayList<Versamento>());

    fillDati(rimborsoImu);
  }

  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub

    rimborsoImu = (RimborsoImu) dati;

    containerVersamento = new WebMarkupContainer("containerVersamento");
    containerVersamento.addOrReplace(insertimentoVersamento());
    containerVersamento.setVisible(true);
    containerVersamento.setOutputMarkupId(true);
    containerVersamento.setOutputMarkupPlaceholderTag(true);

    aggiungi =
        new FdcAjaxButton("btnAggiungi") {
          private static final long serialVersionUID = 19879865465218L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            containerVersamento.addOrReplace(insertimentoVersamento());
            containerVersamento.setVisible(true);
            aggiungi.setVisible(false);

            target.add(aggiungi, containerVersamento);
          }
        };

    aggiungi.setDefaultFormProcessing(false);

    aggiungi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DatiVersamentiPanel.aggiungi", DatiVersamentiPanel.this)));

    containerVersamento.setVisible(false);

    listaVuota =
        new Label("listaVuota", new StringResourceModel("DatiVersamentiPanel.listaVuota", this));
    listaVuota.setOutputMarkupId(true);
    listaVuota.setVisible(rimborsoImu.getVersamenti().isEmpty());
    addOrReplace(listaVuota);
    addOrReplace(aggiungi);
    addOrReplace(containerVersamento);

    listView =
        new PageableListView<Versamento>("listaVersamenti", rimborsoImu.getVersamenti(), 4) {

          private static final long serialVersionUID = 1986546311465487985L;

          @Override
          protected void populateItem(ListItem<Versamento> row) {
            // TODO Auto-generated method stub
            Versamento item = row.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            AttributeAppender attribute = new AttributeAppender("class", " " + ICON_ACCETTATA);
            icona.add(attribute);
            row.addOrReplace(icona);

            row.addOrReplace(new AmtCardLabel<>("annoV", item.getAnno(), DatiVersamentiPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>(
                    "quotaComuneV",
                    formatWithCurrency(item.getQuotaComune()),
                    DatiVersamentiPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>(
                    "quotaStatoV",
                    formatWithCurrency(item.getQuotaStato()),
                    DatiVersamentiPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>(
                    "totaleImportoV",
                    formatWithCurrency(item.getTotaleImporto()),
                    DatiVersamentiPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>(
                    "motivazioneV",
                    item.getMotivazioneVersamento().toString(),
                    DatiVersamentiPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("altroV", item.getAltro(), DatiVersamentiPanel.this));

            Button eliminaVersamento =
                new Button("btnEliminaVersamento") {
                  private static final long serialVersionUID = 18979654132131548L;

                  @Override
                  public void onSubmit() {
                    try {

                      if (!rimborsoImu.getVersamenti().isEmpty()) {
                        log.debug("[Remove - Versamento] Rimuovo il Versamento dalla Lista");
                        Versamento ver =
                            rimborsoImu.getVersamenti().stream()
                                .filter(x -> x.getId().equals(item.getId()))
                                .findFirst()
                                .get();

                        rimborsoImu.getVersamenti().remove(ver);
                        listaVuota.setVisible(rimborsoImu.getVersamenti().isEmpty());
                      }

                    } catch (Exception ex) {
                      log.debug("[Eliminare Versamento] Errore: " + ex);
                    }
                  }
                };

            eliminaVersamento.setDefaultFormProcessing(true);
            eliminaVersamento.setLabel(
                Model.of(
                    Application.get()
                        .getResourceSettings()
                        .getLocalizer()
                        .getString("DatiVersamentiPanel.elimina", DatiVersamentiPanel.this)));

            row.addOrReplace(eliminaVersamento);
          }
        };
    listView.setOutputMarkupId(true);
    listView.setOutputMarkupPlaceholderTag(true);

    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(
        LabelFdCUtil.checkIfNotNull(rimborsoImu.getVersamenti())
            && rimborsoImu.getVersamenti().size() > 4);
    paginazioneFascicolo.setOutputMarkupId(true);
    addOrReplace(paginazioneFascicolo);
  }

  private String formatWithCurrency(BigDecimal value) {
    return LabelFdCUtil.checkIfNull(value) ? "" : value.toString() + " €";
  }

  private InsertVersamentoForm insertimentoVersamento() {
    return new InsertVersamentoForm("insertVersamento", new Versamento()) {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onSubmit() {
        // TODO Auto-generated method stub
        if (checkIsAnnualitaPresente(getModelObject())) {
          error(
              getLocalizer()
                  .getString("DatiVersamentiPanel.annoDuplicato", DatiVersamentiPanel.this));
          return;
        }

        if (checkQuotaStatoComune(getModelObject())) {
          error(
              getLocalizer()
                  .getString("DatiVersamentiPanel.quotaObbligatoria", DatiVersamentiPanel.this));
          return;
        }

        log.debug(getModelObject());
        getModelObject().setId(UUID.randomUUID());
        rimborsoImu.getVersamenti().add(getModelObject());
        containerVersamento.setVisible(false);
        aggiungi.setVisible(true);
        listaVuota.setVisible(rimborsoImu.getVersamenti().isEmpty());

        paginazioneFascicolo.setVisible(
            LabelFdCUtil.checkIfNotNull(rimborsoImu.getVersamenti())
                && rimborsoImu.getVersamenti().size() > 4);
      }

      private boolean checkQuotaStatoComune(Versamento modelObject) {
        return modelObject.getQuotaComune() == null && modelObject.getQuotaStato() == null;
      }

      private boolean checkIsAnnualitaPresente(Versamento modelObject) {
        // TODO Auto-generated method stub
        return rimborsoImu.getVersamenti().stream()
            .anyMatch(x -> x.getAnno() == modelObject.getAnno());
      }

      @Override
      public void annulla(AjaxRequestTarget target) {
        // TODO Auto-generated method stub
        log.debug("[Annulla]");
        containerVersamento.setVisible(false);
        aggiungi.setVisible(true);

        listaVuota.setVisible(rimborsoImu.getVersamenti().isEmpty());

        paginazioneFascicolo.setVisible(
            LabelFdCUtil.checkIfNotNull(rimborsoImu.getVersamenti())
                && rimborsoImu.getVersamenti().size() > 4);

        target.add(containerVersamento, aggiungi, listaVuota, listaVuota, paginazioneFascicolo);
      }
    };
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    StringBuilder sb = new StringBuilder();

    sb.append(
        "$('html,body').animate({\r\n"
            + " scrollTop: $('#indicator').offset().top,\r\n"
            + " }, 650);");

    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }

  public void disableUI() {
    aggiungi.setVisible(false);
    listView.setEnabled(false);
  }

  public void enableUI() {
    aggiungi.setVisible(true);
    listView.setEnabled(true);
  }
}
