package it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.api.pojo.enums.AutocertificazioneTipoMinoreEnum;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.portale.MinoreConvivente;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.TipoMinoreRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.FdcLaddaAjaxLinkAutodichiarazione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.LoadData;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.landing.BaseLandingPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.Wizard2AutocertificazionePage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class AutodichiarazioneGenitorePanel extends BasePanel {

  private static final long serialVersionUID = -1642481424336076636L;

  private HashMap<String, Boolean> mappaMinori;

  private AggiungiFiglioPanel aggiungiFiglioPanel = new AggiungiFiglioPanel("aggiungiFiglioPanel");

  @SuppressWarnings("unused")
  private boolean aggiungiFiglioVisibile = false;

  @SuppressWarnings("unused")
  private String testoBottoneAggiungi = getString("AutodichiarazioneGenitorePanel.aggiungiFiglio");

  private ListView<MinoreConvivente> listView;

  private List<MinoreConvivente> listaMinori;

  private long idCatRelatives;

  public AutodichiarazioneGenitorePanel(String id) {
    super(id);

    Residente residente = LoadData.caricaMieiDatiResidente(getSession());
    fillDati(residente);
    createFeedBackPanel();
    setOutputMarkupId(true);
    addOrReplace(creaBtnHome());
    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  @Override
  public void fillDati(Object dati) {

    List<ComponenteNucleo> listaMaggiorenniOConviventi = new ArrayList<>();
    listaMinori = new ArrayList<>();
    try {
      inizializzaListaMinori();
      listaMaggiorenniOConviventi =
          ServiceLocator.getInstance()
              .getServizioDemografico()
              .getFigliNonPerAutodichiarazione(getUtente());
    } catch (BusinessException | ApiException e) {
      log.error("Errore API:", e);
    }
    log.debug("AutodichiarazioneGenitorePanel -- listaMinori: " + listaMinori);
    log.debug(
        "AutodichiarazioneGenitorePanel -- listaMaggiorenniOConviventi: "
            + listaMaggiorenniOConviventi);

    listView =
        new ListView<MinoreConvivente>("listaConviventi", listaMinori) {

          private static final long serialVersionUID = -675270110514582192L;

          @Override
          protected void populateItem(ListItem<MinoreConvivente> item) {

            MinoreConvivente minore = item.getModelObject();
            log.debug("AutodichiarazioneGenitorePanel -- minore: " + minore);
            AbstracFrameworkForm<MinoreConvivente> form =
                new AbstracFrameworkForm<MinoreConvivente>("form", minore) {

                  private static final long serialVersionUID = 1811478912485612738L;

                  @Override
                  public void addElementiForm() {
                    PropertyModel<MinoreConvivente> model =
                        new PropertyModel<MinoreConvivente>(getModel(), "tipoParentela");
                    DropDownChoice<?> dropDownChoice =
                        creaDropDownChoice(getLista(), "combo", new TipoMinoreRenderer(), model);
                    dropDownChoice.setEscapeModelStrings(true);
                    dropDownChoice.setRequired(true);
                    add(dropDownChoice);
                  }

                  private List<AutocertificazioneTipoMinoreEnum> getLista() {
                    List<AutocertificazioneTipoMinoreEnum> listaParentele =
                        Arrays.asList(AutocertificazioneTipoMinoreEnum.values());

                    if (getUtente().isResidente()) {
                      return listaParentele;
                    } else {
                      List<AutocertificazioneTipoMinoreEnum> listaParenteleSenzaMinoreConvivente =
                          new ArrayList<AutocertificazioneTipoMinoreEnum>();

                      for (AutocertificazioneTipoMinoreEnum elem : listaParentele) {
                        if (!elem.getDescription()
                                .equalsIgnoreCase(
                                    AutocertificazioneTipoMinoreEnum.MC.getDescription())
                            && !elem.getDescription()
                                .equalsIgnoreCase(
                                    AutocertificazioneTipoMinoreEnum.NN.getDescription())) {
                          listaParenteleSenzaMinoreConvivente.add(elem);
                        }
                      }
                      return listaParenteleSenzaMinoreConvivente;
                    }
                  }
                };
            item.add(form);

            LaddaAjaxButton laddaAjaxButton =
                new LaddaAjaxButton("salva", form, Type.Primary) {

                  private static final long serialVersionUID = 1302165883054146653L;

                  @Override
                  protected void onSubmit(AjaxRequestTarget target) {
                    target.add(getPage());
                    MinoreConvivente minore = form.getModelObject();

                    try {
                      ServiceLocator.getInstance()
                          .getServiziConfigurazione()
                          .updateMinoreConviventePerDichiazioneGenitore(minore, getUtente());
                      TimeUnit.SECONDS.sleep(1);
                      getUtente().aggiornaStatoFigli();
                      log.debug("Modifiche salvate");
                      AutodichiarazioneGenitorePanel.this.success("Modifiche salvate");
                    } catch (BusinessException | InterruptedException e) {
                      AutodichiarazioneGenitorePanel.this.error("Errore durante il salvataggio");
                      log.error("Errore:", e);
                    }
                    updateMappa(minore);
                  }
                };
            laddaAjaxButton.setLabel(Model.of("Salva autodichiarazione"));
            form.add(laddaAjaxButton);

            Label nomeCognome = new Label("nome", minore.getNome() + " " + minore.getCognome());
            Label codiceFiscale = new Label("cf", minore.getCodiceFiscale());
            form.add(nomeCognome);
            form.add(codiceFiscale);
            item.setVisible(isVisibile(minore));
            item.setEnabled(isModificabile(minore));

            /************************************************************************************************************/
            LaddaAjaxButton deleteAjaxButton =
                new LaddaAjaxButton("cancella", form, Type.Primary) {

                  private static final long serialVersionUID = 2086164563333141819L;

                  @Override
                  protected void onSubmit(AjaxRequestTarget target) {
                    MinoreConvivente minore = form.getModelObject();

                    try {
                      ServiceLocator.getInstance()
                          .getServiziConfigurazione()
                          .cancellaMinoreConviventePerDichiazioneGenitore(minore, getUtente());
                      TimeUnit.SECONDS.sleep(1);
                      getUtente().aggiornaStatoFigli();
                      getUtente().resetNucleoFamiliareAllargato();
                      inizializzaListaMinori();
                      listView.setList(listaMinori);
                      log.debug("Autodichiarazione cancellata");

                      AutodichiarazioneGenitorePanel.this.success("Autodichiarazione cancellata");
                    } catch (BusinessException | InterruptedException | ApiException e) {
                      AutodichiarazioneGenitorePanel.this.error("Errore durante la cancellazione");
                      log.error("Errore:", e);
                    }
                    updateMappa(minore);
                    target.add(getPage());
                  }
                };
            deleteAjaxButton.setLabel(Model.of("Cancella autodichiarazione"));
            form.add(deleteAjaxButton);

            form.add(nomeCognome);
            form.add(codiceFiscale);

            if (getUtente().isResidente()) {
              deleteAjaxButton.setVisible(checkTipoParentelaResidente(minore));
            } else {
              deleteAjaxButton.setVisible(true);
            }
          }

          private boolean checkTipoParentelaResidente(MinoreConvivente minore) {
            return !minore
                    .getTipoParentela()
                    .toString()
                    .equalsIgnoreCase(AutocertificazioneTipoMinoreEnum.FG.value())
                && !minore
                    .getTipoParentela()
                    .toString()
                    .equalsIgnoreCase(AutocertificazioneTipoMinoreEnum.NN.value());
          }

          /************************************************************************************************************/
          private boolean isModificabile(MinoreConvivente minore) {
            return !minore.isBloccoAutodichiarazione();
          }

          private boolean isVisibile(MinoreConvivente minore) {
            return !minore.isFiglioStatoCivile();
          }
        };

    listView.setRenderBodyOnly(true);
    boolean autocertificazioneNonNecessaria = isAutocertificazioneNonNecessaria(listaMinori);
    listView.setVisible(!autocertificazioneNonNecessaria);
    messaggioAutocertificazioneNonNecessaria(autocertificazioneNonNecessaria);
    addOrReplace(listView);

    ListView<ComponenteNucleo> listViewFigliMaggiorenniOConviventi =
        creaListViewMaggiorenniOrConvinventi(listaMaggiorenniOConviventi);
    listViewFigliMaggiorenniOConviventi.setRenderBodyOnly(true);
    add(listViewFigliMaggiorenniOConviventi);

    WebMarkupContainer autodichiarazioneNonConviventi =
        new WebMarkupContainer("autodichiarazioneNonResidenti") {

          private static final long serialVersionUID = 86150554392799228L;

          @Override
          public boolean isVisible() {
            log.debug(
                "autodichiarazioneNonConviventi -- visibile se non residente="
                    + !getUtente().isResidente());
            return !getUtente().isResidente();
          }
        };
    add(autodichiarazioneNonConviventi);
    autodichiarazioneNonConviventi.addOrReplace(aggiungiFiglioPanel);
  }

  private void inizializzaListaMinori() throws BusinessException, ApiException {
    listaMinori =
        ServiceLocator.getInstance()
            .getServizioDemografico()
            .getFigliPerAutodichiarazione(getUtente());
    creaMappa(listaMinori);
  }

  private ListView<ComponenteNucleo> creaListViewMaggiorenniOrConvinventi(
      List<ComponenteNucleo> listaMaggiorenniOConviventi) {
    return new ListView<ComponenteNucleo>(
        "listaMaggiorenniOConviventi", listaMaggiorenniOConviventi) {

      private static final long serialVersionUID = -675270110514582192L;

      @Override
      protected void populateItem(ListItem<ComponenteNucleo> item) {
        ComponenteNucleo componenteNucleo = item.getModelObject();

        log.debug("AutodichiarazioneGenitorePanel -- componenteNucleo: " + componenteNucleo);

        AbstracFrameworkForm<ComponenteNucleo> form =
            new AbstracFrameworkForm<ComponenteNucleo>(
                "formMaggiorenniOConviventi", componenteNucleo) {

              private static final long serialVersionUID = 1811478912485612738L;

              @Override
              public void addElementiForm() {}
            };
        item.add(form);

        String nomeCognomeDaDatiCittadino = "";
        String codiceFiscaleDaDatiCittadino = "";

        if (componenteNucleo.getDatiCittadino() != null) {
          nomeCognomeDaDatiCittadino =
              componenteNucleo.getDatiCittadino().getCpvGivenName()
                  + " "
                  + componenteNucleo.getDatiCittadino().getCpvFamilyName();

          codiceFiscaleDaDatiCittadino = componenteNucleo.getDatiCittadino().getCpvTaxCode();
        }

        if (codiceFiscaleDaDatiCittadino == "" && componenteNucleo.getRelazione() != null) {
          codiceFiscaleDaDatiCittadino = componenteNucleo.getRelazione().getCpvComponentTaxCode();
        }

        Label nomeCognome = new Label("nome", nomeCognomeDaDatiCittadino);
        Label codiceFiscale = new Label("cf", codiceFiscaleDaDatiCittadino);

        nomeCognome.setOutputMarkupId(true);
        nomeCognome.setVisible(nomeCognomeDaDatiCittadino != "");

        form.add(nomeCognome);
        form.add(codiceFiscale);
        item.setVisible(true);
        item.setEnabled(false);
      }
    };
  }

  @SuppressWarnings("rawtypes")
  private FdcLaddaAjaxLinkAutodichiarazione creaBtnHome() {

    FdcLaddaAjaxLinkAutodichiarazione buttonHome =
        new FdcLaddaAjaxLinkAutodichiarazione<String>(
            "homeBtn", Model.of("Vai alla Home"), Buttons.Type.Primary, Model.of("Vai alla Home")) {

          private static final long serialVersionUID = 3910631421237359469L;

          @Override
          public void onClick(AjaxRequestTarget arg0) {
            setResponsePage(BaseLandingPage.class);
          }

          @Override
          protected void onConfigure() {
            super.onConfigure();
            Boolean isPaginaWizard = Wizard2AutocertificazionePage.class.isInstance(getPage());
            setVisibilityAllowed(!checkMappa() && isPaginaWizard);
          }
        };
    return buttonHome;
  }

  public boolean checkMappa() {

    if (mappaMinori != null) {
      for (Boolean valore : mappaMinori.values()) {
        if (valore) {
          log.debug("Trovato:" + valore);
          return true;
        }
      }
    }
    return false;
  }

  private void messaggioAutocertificazioneNonNecessaria(boolean autocertificazioneNonNecessaria) {
    if (autocertificazioneNonNecessaria) {
      this.success(getString("AutodichiarazioneGenitorePanel.nonNecessaria"));
    }
  }

  private boolean isAutocertificazioneNonNecessaria(List<MinoreConvivente> listaMinori) {
    log.debug(
        "AutodichiarazioneGenitorePanel -- isAutocertificazioneNonNecessaria: "
            + listaMinori.isEmpty());
    return listaMinori.isEmpty() && getUtente().isResidente();
  }

  private void creaMappa(List<MinoreConvivente> listaMinori) {
    mappaMinori = new HashMap<>();
    for (MinoreConvivente minore : listaMinori) {
      log.debug("minore&parentela:" + minore.getCodiceFiscale() + "-" + minore.getTipoParentela());
      mappaMinori.put(
          minore.getCodiceFiscale(),
          minore.getTipoParentela().value().equals(AutocertificazioneTipoMinoreEnum.NN.value()));
    }
  }

  private void updateMappa(MinoreConvivente minore) {
    log.debug("minore:" + minore.getCodiceFiscale());
    mappaMinori.put(minore.getCodiceFiscale(), false);
  }

  @SuppressWarnings("unused")
  private void setIdCatRelatives(long l) {
    this.idCatRelatives = l;
  }

  private Long getIdCatRelatives() {
    return this.idCatRelatives;
  }

  @SuppressWarnings("unused")
  private String checkAzione(MinoreConvivente minore) {
    if (!minore.getTipoParentela().toString().equalsIgnoreCase(getValueIdCatRelatives())) {
      if (minore
          .getTipoParentela()
          .toString()
          .equalsIgnoreCase(AutocertificazioneTipoMinoreEnum.NN.value())) {
        return "annulla";
      } else return "update";
    } else return "cancella";
  }

  private String getValueIdCatRelatives() {
    Long idCatRel = getIdCatRelatives();
    if (idCatRel.equals(0L)) {
      return AutocertificazioneTipoMinoreEnum.NN.value();
    } else if (idCatRel.equals(1L)) {
      return AutocertificazioneTipoMinoreEnum.MC.value();
    } else if (idCatRel.equals(2L)) {
      return AutocertificazioneTipoMinoreEnum.FP.value();
    } else if (idCatRel.equals(3L)) {
      return AutocertificazioneTipoMinoreEnum.FG.value();
    } else return AutocertificazioneTipoMinoreEnum.NN.value();
  }
}
