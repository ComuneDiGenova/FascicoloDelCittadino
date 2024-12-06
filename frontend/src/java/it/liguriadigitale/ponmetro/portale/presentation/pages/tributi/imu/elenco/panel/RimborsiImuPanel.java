package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.elenco.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.pojo.imu.StatoRimborsoEnum;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.dettaglio.RimborsoImuDettaglioPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.documenti.AllegaDocumentiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.documenti.form.RimborsoImuNotFoundException;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.elenco.RimborsiImuPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.RichiestaRimborsoImuPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.tributi.model.IstanzaRimborso;
import it.liguriadigitale.ponmetro.tributi.model.ProtocollazioneIstanzaRimborso;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class RimborsiImuPanel extends BasePanel {

  private static final long serialVersionUID = 146542132318976543L;

  private static final String ICONA_RIMBORSO = "icon-rimborsi-imu";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private List<RimborsoImu> listaRimborsiImu;
  PageableListView<RimborsoImu> listView;
  WebMarkupContainer containerList;
  WebMarkupContainer containerListaVuota;
  WebMarkupContainer containerPaginazione;

  StatoRimborsoEnum statoFilter;

  IModel<List<RimborsoImu>> listaModel;
  Label listaVuota;

  public RimborsiImuPanel(String id) {
    super(id);
    createFeedBackPanel();
    // TODO Auto-generated constructor stub
    this.fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {

    // TODO Auto-generated method stub
    containerList = new WebMarkupContainer("containerList");
    containerList.setOutputMarkupId(true);

    containerListaVuota = new WebMarkupContainer("containerListaVuota");
    containerListaVuota.setOutputMarkupId(true);

    containerPaginazione = new WebMarkupContainer("containerPaginazione");
    containerPaginazione.setOutputMarkupId(true);

    listaRimborsiImu = getRimborsiImu(getUtente().getCodiceFiscaleOperatore());

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(RimborsiImuPage.class).build();
    addOrReplace(boxMessaggi);

    listaModel =
        new LoadableDetachableModel<List<RimborsoImu>>() {
          private static final long serialVersionUID = 1L;

          @Override
          protected List<RimborsoImu> load() {
            // TODO Auto-generated method stub
            return getListaFilterRimborsoImu();
          }
        };

    addOrReplace(creaFilter("flagVerbaleTutti", "RimborsiImuPanel.tutti", null));
    addOrReplace(
        creaFilter(
            "flagVerbaleInCompilazione", "RimborsiImuPanel.inCompilazione", StatoRimborsoEnum.C));
    addOrReplace(
        creaFilter("flagVerbalePresentato", "RimborsiImuPanel.presentato", StatoRimborsoEnum.P));
    addOrReplace(creaFilter("flagVerbaleSospeso", "RimborsiImuPanel.sospeso", StatoRimborsoEnum.S));
    addOrReplace(creaFilter("flagVerbaleEvasa", "RimborsiImuPanel.evasa", StatoRimborsoEnum.E));
    addOrReplace(
        creaFilter("flagVerbaleAnnullata", "RimborsiImuPanel.annullata", StatoRimborsoEnum.A));

    listaVuota =
        new Label("listaVuota", new StringResourceModel("RimborsiImuPanel.listaVuota", this));
    listaVuota.setOutputMarkupId(true);
    listaVuota.setVisible(listaRimborsiImu.isEmpty());
    containerListaVuota.addOrReplace(listaVuota);

    addOrReplace(containerListaVuota);

    listView =
        new PageableListView<RimborsoImu>("box", listaModel, 4) {

          private static final long serialVersionUID = 1L;

          @Override
          protected void populateItem(ListItem<RimborsoImu> item) {

            final RimborsoImu rata = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaRimborso());
            icona.add(getColoreIconaRimborso(rata));
            item.addOrReplace(icona);

            // TODO Auto-generated method stub
            item.addOrReplace(
                new AmtCardLabel<>("stato", rata.getStato().toString(), RimborsiImuPanel.this));
            item.addOrReplace(
                new AmtCardLabel<>("numeroDoc", rata.getNumeroDocumento(), RimborsiImuPanel.this));
            item.addOrReplace(
                new AmtCardLabel<>("annoDoc", rata.getAnnoDocumento(), RimborsiImuPanel.this));
            item.addOrReplace(
                new AmtCardLabel<>("dataPrep", rata.getDataPresentazione(), RimborsiImuPanel.this));

            Form formInvia = new Form("formInvia");
            item.addOrReplace(creaBottoneDettagliPraticaIMU(rata));
            formInvia.addOrReplace(creaBottoneAnnullaPraticaRimborsoIMU(rata));
            formInvia.addOrReplace(creaBottoneInviaRimborsoIMU(rata));
            item.addOrReplace(formInvia);
            item.addOrReplace(creaBottoneAllegaDocumentiIMU(rata));
          }
        };

    listView.setOutputMarkupId(true);
    listView.setOutputMarkupPlaceholderTag(true);
    listView.setRenderBodyOnly(true);
    listView.setVisible(!listaRimborsiImu.isEmpty());
    containerList.addOrReplace(listView);

    addOrReplace(containerList);

    addOrReplace(creaBtnRichiestaRimborso());

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setOutputMarkupId(true);
    paginazioneFascicolo.setVisible(listaRimborsiImu.size() > 4);
    containerPaginazione.addOrReplace(paginazioneFascicolo);

    addOrReplace(containerPaginazione);
  }

  private AttributeModifier getColoreIconaRimborso(RimborsoImu rimborso) {
    AttributeModifier coloreIcona = new AttributeModifier("style", "color: #000000");
    log.debug("[Stato]" + rimborso.getStato());

    try {
      switch (rimborso.getStato()) {
        case P:
          coloreIcona = new AttributeModifier("style", "color: #008758");
          break;
        case S:
        case A:
          coloreIcona = new AttributeModifier("style", "color: #d9364f");
          break;
        case E:
          coloreIcona = new AttributeModifier("style", "color: #008758");
          break;
        default: // IN_COMPILAZIONE
          coloreIcona = new AttributeModifier("style", "color: #a66300");
          break;
      }
      return coloreIcona;
    } catch (Exception e) {
      return coloreIcona;
    }
  }

  private AttributeAppender getCssIconaRimborso() {
    return new AttributeAppender("class", " " + ICONA_RIMBORSO);
  }

  private LaddaAjaxLink<Object> creaBottoneAllegaDocumentiIMU(final RimborsoImu rata) {
    LaddaAjaxLink<Object> allegaFile =
        new LaddaAjaxLink<Object>("btnAllegaFile", Type.Success) {
          private static final long serialVersionUID = 18979654132131548L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            log.debug("[AllegaFile] Allega file rimborso Imu: " + rata);
            boolean isReadOnly =
                !rata.getStato().equals(StatoRimborsoEnum.C)
                    && !rata.getStato().equals(StatoRimborsoEnum.S);
            setResponsePage(new AllegaDocumentiPage(rata.getUriPratica(), isReadOnly));
          }
        };

    String key =
        rata.getStato().equals(StatoRimborsoEnum.C) || rata.getStato().equals(StatoRimborsoEnum.S)
            ? "RimborsiImuPanel.allegaFile"
            : "RimborsiImuPanel.vediAllegati";

    // allegaFile.setDefaultFormProcessing(true);
    allegaFile.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(key, RimborsiImuPanel.this)));

    /*allegaFile
    .setVisible(rata.getStato().equals(StatoRimborsoEnum.C) || rata.getStato().equals(StatoRimborsoEnum.S));*/
    return allegaFile;
  }

  private LaddaAjaxButton creaBottoneInviaRimborsoIMU(final RimborsoImu rata) {
    LaddaAjaxButton invia =
        new LaddaAjaxButton("btnInvia", Type.Primary) {
          private static final long serialVersionUID = 18979654132131548L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            try {
              inviaPraticaRimborso(rata);

              setResponsePage(new RimborsiImuPage());
            } catch (BusinessException
                | RimborsoImuNotFoundException
                | ApiException
                | IllegalArgumentException e) {
              log.debug("[Protocollazione] Errore nella protocollazione: " + e.getMessage());
              error(e.getMessage());
            }

            target.add(feedbackPanel);
          }

          private void inviaPraticaRimborso(RimborsoImu item)
              throws BusinessException, ApiException, RimborsoImuNotFoundException {

            RimborsoImu dettaglio =
                ServiceLocator.getInstance()
                    .getServiziImuEng()
                    .dettaglioPraticaRimborsoImu(item.getUriPratica());

            try {
              if (LabelFdCUtil.checkIfNull(dettaglio)) {
                throw new RimborsoImuNotFoundException(
                    "Impossibile recuperare l'istanza di rimborso");
              }

              // TODO Auto-generated method stub
              ProtocollazioneIstanzaRimborso protocollazione =
                  ServiceLocator.getInstance()
                      .getServiziImuEng()
                      .praticaRimborsoIMUProtocollazione(
                          dettaglio.getNome(),
                          dettaglio.getCognome(),
                          "1",
                          dettaglio.getCodiceFiscalePerAuriga(),
                          dettaglio.getCodiceSoggetoPerAuriga().toString(), // da
                          // caricare
                          "Pratica di Rimborso IMU",
                          Long.valueOf(dettaglio.getUriPratica()));

              if (LabelFdCUtil.checkIfNull(protocollazione)) {
                log.debug("[ProtocollazioneIMU] Impossibile Protocollare la richiesta di rimborso");
                throw new BusinessException(
                    getLocalizer()
                        .getString("RimborsiImuPanel.protocollazioneKo", RimborsiImuPanel.this));
              }
            } catch (BusinessException | ApiException e) {
              throw new BusinessException(
                  getLocalizer()
                      .getString("RimborsiImuPanel.protocollazioneKo", RimborsiImuPanel.this));
            }
          }
        };

    invia.setDefaultFormProcessing(true);
    invia.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RimborsiImuPanel.invia", RimborsiImuPanel.this)));

    invia.setVisible(rata.getStato().equals(StatoRimborsoEnum.C));
    return invia;
  }

  private LaddaAjaxLink<Object> creaBottoneDettagliPraticaIMU(final RimborsoImu rata) {
    LaddaAjaxLink<Object> dettagli =
        new LaddaAjaxLink<Object>("btnDettagli", Type.Primary) {
          private static final long serialVersionUID = 18979654132131548L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            log.debug("[AllegaFile] Allega file rimborso Imu: " + rata);
            setResponsePage(new RimborsoImuDettaglioPage(rata.getUriPratica()));
          }
        };

    dettagli.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RimborsiImuPanel.dettagli", RimborsiImuPanel.this)));
    return dettagli;
  }

  private LaddaAjaxButton creaBottoneAnnullaPraticaRimborsoIMU(final RimborsoImu rata) {
    LaddaAjaxButton annulla =
        new LaddaAjaxButton("btnAnnulla", Type.Danger) {
          private static final long serialVersionUID = 18979654132131549L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            try {

              IstanzaRimborso istanza =
                  ServiceLocator.getInstance()
                      .getServiziImuEng()
                      .praticaRimborsoImuAnnullamento(rata.getUriPratica().longValue());

              setResponsePage(new RimborsiImuPage());

            } catch (BusinessException | ApiException e) {
              // TODO Auto-generated catch block
              log.debug("[Protocollazione] Errore annullamento istanza: " + e.getMessage());
              error(e.getMessage());
            }
          }
        };

    annulla.setDefaultFormProcessing(false);
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RimborsiImuPanel.annulla", RimborsiImuPanel.this)));

    annulla.setVisible(rata.getStato().equals(StatoRimborsoEnum.C));
    return annulla;
  }

  private List<RimborsoImu> getRimborsiImu(String codiceFiscaleOperatore) {
    try {

      return ServiceLocator.getInstance()
          .getServiziImuEng()
          .getPraticheRimborsoIMU(getUtente().getCodiceFiscaleOperatore());
    } catch (BusinessException | ApiException e) {
      // TODO Auto-generated catch block
      log.debug("[getRimborsiImu] " + e);
      return new ArrayList<>();
    }
  }

  private LaddaAjaxLink<Object> creaBtnRichiestaRimborso() {
    LaddaAjaxLink<Object> btnRichiestaRimborsoImu =
        new LaddaAjaxLink<Object>("btnRichiestaRimborso", Type.Primary) {

          private static final long serialVersionUID = 1167746248084235400L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            setResponsePage(new RichiestaRimborsoImuPage());
          }
        };

    btnRichiestaRimborsoImu.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RimborsiImuPanel.btnRichiestaRimborso", RimborsiImuPanel.this)));

    IconType iconType =
        new IconType("btnRichiestaRimborso") {

          private static final long serialVersionUID = -5380436407601312614L;

          @Override
          public String cssClassName() {
            return "icon-rimborsi-imu";
          }
        };
    btnRichiestaRimborsoImu.setIconType(iconType);

    return btnRichiestaRimborsoImu;
  }

  private LaddaAjaxLink<Object> creaFilter(String wicketId, String label, StatoRimborsoEnum stato) {
    LaddaAjaxLink<Object> linkFlagVerbaleAperto =
        new LaddaAjaxLink<Object>(wicketId, Type.Link) {

          private static final long serialVersionUID = 1448579012199876827L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            statoFilter = stato;
            List<RimborsoImu> filter = getListaFilterRimborsoImu();
            listaModel.setObject(filter);
            paginazioneFascicolo.setVisible(listaModel.getObject().size() > 4);
            listaVuota.setVisible(listaModel.getObject().isEmpty());
            target.add(containerList, containerPaginazione, containerListaVuota);
          }
        };

    linkFlagVerbaleAperto.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(label, RimborsiImuPanel.this)));
    return linkFlagVerbaleAperto;
  }

  private List<RimborsoImu> getListaFilterRimborsoImu() {
    if (LabelFdCUtil.checkIfNull(statoFilter)) {
      log.debug("[Filter] Stato Tutti");
      return listaRimborsiImu;
    }

    return listaRimborsiImu.stream()
        .filter(x -> x.getStato().equals(statoFilter))
        .collect(Collectors.toList());
  }
}
