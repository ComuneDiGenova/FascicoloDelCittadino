package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Immobile;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.pojo.imu.TipoImmobileEnum;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta.ProprietaUtenzeExt;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.modal.ModalEliminaImmobili;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.joda.time.LocalDate;

public class DatiImmobiliPanel extends BasePanel {

  private static final long serialVersionUID = 187987653115L;

  FdcAjaxButton aggiungi;
  WebMarkupContainer listaVuota;
  WebMarkupContainer containerImmobile;
  WebMarkupContainer containerLista;
  WebMarkupContainer containerAggiungi;
  WebMarkupContainer containerPaginazione;

  RimborsoImu rimborso;
  ModalEliminaImmobili<Object> modalViewPanel;
  PageableListView<Immobile> listView;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;
  private static final String ICON_HOME = "color-green col-3 icon-casa";

  public DatiImmobiliPanel(String id) {
    super(id);
    // createFeedBackPanel();
    // TODO Auto-generated constructor stub
  }

  public DatiImmobiliPanel(String id, RimborsoImu rimborso) {
    super(id);
    // createFeedBackPanel();
    // TODO Auto-generated constructor stub
    this.fillDati(rimborso);
  }

  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub

    rimborso = (RimborsoImu) dati;
    preCompilaProprieta();

    /*
     * containerAggiungi = new WebMarkupContainer("containerAggiungi");
     * containerAggiungi.setOutputMarkupId(true);
     * containerAggiungi.setVisible(true);
     */

    containerLista = new WebMarkupContainer("containerLista");
    containerLista.setOutputMarkupId(true);

    containerImmobile = new WebMarkupContainer("containerImmobile");
    containerImmobile.setOutputMarkupId(true);
    containerImmobile.setOutputMarkupPlaceholderTag(true);
    containerImmobile.setVisible(false);

    aggiungi =
        new FdcAjaxButton("btnAggiungi") {
          private static final long serialVersionUID = 19879865465218L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            containerImmobile.setVisible(true);
            containerImmobile.addOrReplace(createInserimentoImmobilePanel(new Immobile(), true));
            aggiungi.setVisible(false);

            target.add(aggiungi, containerImmobile);
            target.appendJavaScript(
                "$('html,body').animate({\r\n"
                    + " scrollTop: $('#indicator').offset().top,\r\n"
                    + " }, 650);");
          }
        };

    aggiungi.setDefaultFormProcessing(false);
    aggiungi.setOutputMarkupId(true);

    aggiungi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DatiImmobiliPanel.aggiungi", DatiImmobiliPanel.this)));

    // containerAggiungi.addOrReplace(aggiungi);
    addOrReplace(containerImmobile);
    addOrReplace(aggiungi);

    listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(rimborso.getImmobili().isEmpty());
    listaVuota.setOutputMarkupId(true);
    listaVuota.setOutputMarkupPlaceholderTag(true);
    addOrReplace(listaVuota);

    listView =
        new PageableListView<Immobile>("listaImmobili", rimborso.getImmobili(), 2) {
          private static final long serialVersionUID = 1456465483131L;

          @SuppressWarnings("unused")
          @Override
          protected void populateItem(ListItem<Immobile> row) {
            // TODO Auto-generated method stub
            Immobile item = row.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            AttributeAppender attribute = new AttributeAppender("class", " " + ICON_HOME);
            icona.add(attribute);
            row.addOrReplace(icona);

            String tipo =
                LabelFdCUtil.checkIfNull(item.getTipo()) ? null : item.getTipo().toString();
            String categoria =
                LabelFdCUtil.checkIfNull(item.getCategoria())
                    ? null
                    : item.getCategoria().getCodice() + "-" + item.getCategoria().getDescrizione();
            String percentualePoss =
                LabelFdCUtil.checkIfNull(item.getPercentualePossesso())
                    ? null
                    : item.getPercentualePossesso() + "%";

            row.addOrReplace(new AmtCardLabel<>("tipo", tipo, DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("sezione", item.getSezione(), DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("foglio", item.getFoglio(), DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("particella", item.getParticella(), DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("subalterno", item.getSubalterno(), DatiImmobiliPanel.this));
            row.addOrReplace(new AmtCardLabel<>("categoria", categoria, DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("provincia", item.getProvincia(), DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("comune", item.getComune(), DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("indirizzo", item.getIndirizzo(), DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("civico", item.getCivico(), DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("esponente", item.getEsponente(), DatiImmobiliPanel.this));
            row.addOrReplace(new AmtCardLabel<>("scala", item.getScala(), DatiImmobiliPanel.this));
            row.addOrReplace(new AmtCardLabel<>("piano", item.getPiano(), DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("interno", item.getInterno(), DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("colore", item.getColore(), DatiImmobiliPanel.this));
            row.addOrReplace(new AmtCardLabel<>("cap", item.getCap(), DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("classe", item.getClasse(), DatiImmobiliPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("percentualePossesso", percentualePoss, DatiImmobiliPanel.this));

            String utilizzo = item.getUtilizzo() != null ? item.getUtilizzo().toString() : "";

            row.addOrReplace(new AmtCardLabel<>("utilizzo", utilizzo, DatiImmobiliPanel.this));
            row.addOrReplace(new AmtCardLabel<>("altro", item.getAltro(), DatiImmobiliPanel.this));

            WebMarkupContainer alert = new WebMarkupContainer("alert");
            alert.setVisible(
                LabelFdCUtil.checkIfNull(item.getPercentualePossesso())
                    || LabelFdCUtil.checkIfNull(item.getUtilizzo()));
            row.addOrReplace(alert);

            @SuppressWarnings("rawtypes")
            Form<?> immobiliAction = new Form("immobiliAction");

            ModalEliminaImmobili<Immobile> modalViewPanel =
                new ModalEliminaImmobili<Immobile>("modalViewPanel") {
                  private static final long serialVersionUID = 154652118879875321L;
                };

            modalViewPanel.addOrReplace(createButtonSi(modalViewPanel, item));
            modalViewPanel.addOrReplace(createButtonNo(modalViewPanel));

            immobiliAction.addOrReplace(modalViewPanel);

            AjaxButton eliminaVersamento =
                new AjaxButton("btnEliminaVersamento") {
                  private static final long serialVersionUID = 18979654132131548L;

                  @Override
                  public void onSubmit(AjaxRequestTarget target) {
                    modalViewPanel.show(target);
                  }
                };

            eliminaVersamento.setDefaultFormProcessing(false);
            eliminaVersamento.setLabel(
                Model.of(
                    Application.get()
                        .getResourceSettings()
                        .getLocalizer()
                        .getString("DatiImmobiliPanel.elimina", DatiImmobiliPanel.this)));

            immobiliAction.addOrReplace(eliminaVersamento);

            AjaxButton modifica =
                new AjaxButton("btnEdit") {
                  private static final long serialVersionUID = 18979654132131548L;

                  @Override
                  public void onSubmit(AjaxRequestTarget target) {
                    // TODO Auto-generated method stub
                    try {

                      if (!rimborso.getImmobili().isEmpty()) {
                        log.debug("[Remove - Versamento] Rimuovo il Versamento dalla Lista");
                        Immobile im =
                            rimborso.getImmobili().stream()
                                .filter(x -> x.getId().equals(item.getId()))
                                .findFirst()
                                .get();

                        containerImmobile.setVisible(true);
                        containerImmobile.addOrReplace(createInserimentoImmobilePanel(im, false));
                        aggiungi.setVisible(false);
                      }

                    } catch (Exception ex) {
                      log.debug("[Eliminare Versamento] Errore: " + ex);
                    }

                    target.add(containerImmobile, aggiungi);
                  }
                };

            modifica.setDefaultFormProcessing(false);

            modifica.setLabel(
                Model.of(
                    Application.get()
                        .getResourceSettings()
                        .getLocalizer()
                        .getString("DatiImmobiliPanel.modifica", DatiImmobiliPanel.this)));

            modifica.setVisible(item.isPrecompilato());
            immobiliAction.addOrReplace(modifica);

            row.addOrReplace(immobiliAction);
          }
        };

    listView.setOutputMarkupId(true);
    listView.setOutputMarkupPlaceholderTag(true);

    containerLista.addOrReplace(listView);
    addOrReplace(containerLista);

    containerPaginazione = new WebMarkupContainer("containerPaginazione");
    containerPaginazione.setOutputMarkupId(true);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setOutputMarkupId(true);
    paginazioneFascicolo.setVisible(
        LabelFdCUtil.checkIfNotNull(rimborso.getImmobili()) && rimborso.getImmobili().size() > 2);
    containerPaginazione.addOrReplace(paginazioneFascicolo);
    addOrReplace(containerPaginazione);
  }

  private AjaxButton createButtonNo(ModalEliminaImmobili<Immobile> modal) {
    // TODO Auto-generated method stub
    AjaxButton no =
        new AjaxButton("btnNo") {
          private static final long serialVersionUID = 1323565488721315L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            modal.close(target);
          }
        };

    return no;
  }

  private AjaxButton createButtonSi(ModalEliminaImmobili<Immobile> modal, Immobile item) {
    // TODO Auto-generated method stub
    AjaxButton yes =
        new AjaxButton("btnSi") {
          private static final long serialVersionUID = 146432132165498L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            log.debug("[Remove - Immobile] Rimuovo il Immobile dalla Lista");
            try {

              if (!rimborso.getImmobili().isEmpty()) {
                log.debug("[Remove - Immobile] Rimuovo il Immobile dalla Lista");
                Immobile im =
                    rimborso.getImmobili().stream()
                        .filter(x -> x.getId().equals(item.getId()))
                        .findFirst()
                        .get();

                rimborso.getImmobili().remove(im);
              }

            } catch (Exception ex) {
              log.debug("[Eliminare Versamento] Errore: " + ex);
            }

            listaVuota.setVisible(rimborso.getImmobili().isEmpty());
            containerLista.setVisible(!rimborso.getImmobili().isEmpty());
            paginazioneFascicolo.setVisible(
                LabelFdCUtil.checkIfNotNull(rimborso.getImmobili())
                    && rimborso.getImmobili().size() > 2);

            target.add(containerLista, listaVuota, containerPaginazione);
            modal.close(target);
          }
        };

    return yes;
  }

  private void preCompilaProprieta() {
    List<ProprietaUtenzeExt> lista =
        getProprietaUtenteExt(String.valueOf(LocalDate.now().getYear()));

    lista.forEach(
        prop -> {
          try {
            Immobile im = new Immobile();
            TipoImmobileEnum t =
                prop.isAbitazione()
                    ? TipoImmobileEnum.F
                    : prop.isTerreno() ? TipoImmobileEnum.T : TipoImmobileEnum.F;

            im.setId(UUID.randomUUID());
            im.setTipo(t);
            im.setClasse(prop.getClasse().trim());
            im.setCategoria(
                RichiestaRimborsoImuHelper.getCategoriaCatastaleByCodice(prop.getCategoria()));
            im.setCivico(Long.valueOf(prop.getNumeroCivico()));
            im.setEsponente(prop.getEsponenteCivico().trim());
            im.setIndirizzo(prop.getVia().trim());
            im.setPiano(LabelFdCUtil.defaultString(prop.getPiano().trim()));
            im.setScala(LabelFdCUtil.defaultString(prop.getScala().trim()));
            im.setInterno(LabelFdCUtil.defaultString(prop.getInterno().trim()));

            if (!prop.isTerreno() && LabelFdCUtil.checkIfNotNull(prop.getInformazioniCatastali())) {
              List<String> catastali = Arrays.asList(prop.getInformazioniCatastali().split("/"));
              im.setSezione(getDatiCatastali(catastali, 0));
              im.setFoglio(formatLong(getDatiCatastali(catastali, 1)));
              im.setParticella(formatLong(getDatiCatastali(catastali, 2)));
              im.setSubalterno(formatLong(getDatiCatastali(catastali, 3)));
            }

            im.setPrecompilato(true);

            FeaturesGeoserver geo = new FeaturesGeoserver();
            geo.setDESVIA(prop.getIndirizzoCompleto());

            im.setGeoServerFeature(geo);

            rimborso.getImmobili().add(im);

          } catch (IllegalArgumentException e) {
            log.debug("[Crea Immobile]" + e);
          }
        });
  }

  private Long formatLong(String value) {
    // TODO Auto-generated method stub
    try {
      return Long.valueOf(value);
    } catch (NumberFormatException nex) {
      log.debug("[formatLong] Format String to Long" + nex);
      return null;
    }
  }

  private String getDatiCatastali(List<String> catastali, int i) {
    // TODO Auto-generated method stub
    try {
      return catastali.get(i);
    } catch (IndexOutOfBoundsException ex) {
      return "";
    }
  }

  private List<ProprietaUtenzeExt> getProprietaUtenteExt(final String annoRiferimento) {
    try {
      return ServiceLocator.getInstance()
          .getServiziQuadroTributario()
          .getProprietaUtenzeExt(getUtente(), annoRiferimento);
    } catch (ApiException | BusinessException e) {
      return new ArrayList<>();
    }
  }

  private InserimentoImmobilePanel createInserimentoImmobilePanel(
      Immobile immobile, boolean isNotEditMode) {
    InserimentoImmobilePanel inserimentoImmobilePanel =
        new InserimentoImmobilePanel("inserimentoImmobilePanel", immobile, isNotEditMode) {
          private static final long serialVersionUID = 1L;

          @Override
          public void annulla(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            containerImmobile.setVisible(false);
            aggiungi.setVisible(true);
            target.add(containerImmobile, aggiungi);
          }

          @Override
          public void salva(AjaxRequestTarget target, Immobile immobile) {

            if (!rimborso.getImmobili().stream()
                .anyMatch(x -> x.getId().equals(immobile.getId()))) {
              log.debug("[Salva Immobile] Immobile: " + immobile);
              immobile.setId(UUID.randomUUID());
              immobile.setPrecompilato(false);
              rimborso.getImmobili().add(immobile);
            }

            aggiungi.setVisible(true);
            containerImmobile.setVisible(false);
            containerLista.setVisible(!rimborso.getImmobili().isEmpty());
            listaVuota.setVisible(rimborso.getImmobili().isEmpty());
            paginazioneFascicolo.setVisible(
                LabelFdCUtil.checkIfNotNull(rimborso.getImmobili())
                    && rimborso.getImmobili().size() > 2);

            target.add(
                feedbackPanel,
                containerImmobile,
                aggiungi,
                containerLista,
                listaVuota,
                containerPaginazione);
          }
        };

    return inserimentoImmobilePanel;
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
