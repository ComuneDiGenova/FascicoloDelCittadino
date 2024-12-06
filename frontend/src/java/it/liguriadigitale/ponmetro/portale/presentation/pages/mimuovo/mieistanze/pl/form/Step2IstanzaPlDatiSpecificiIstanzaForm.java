package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapRadioChoice;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.IstanzePlDatiMarcaVeicoloSummaryRender;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.YesNoRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.components.mimuovo.istanzepl.autocomplete.IstanzePLMarcaAutoComplete;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form.panel.NonProprietarioPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form.panel.TargaErrataPanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.AbstractChoice.LabelPosition;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class Step2IstanzaPlDatiSpecificiIstanzaForm
    extends AbstracFrameworkForm<DatiRichiestaIstanzaPl> {

  private static final long serialVersionUID = -7349674609304452360L;

  private FdCLocalDateTextfield furtoData;

  private FdCLocalDateTextfield ritrovatoData;

  // private WebMarkupContainer containerComune;

  public Step2IstanzaPlDatiSpecificiIstanzaForm(String id, DatiRichiestaIstanzaPl model) {
    super(id, model);

    addElementiForm();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addElementiForm() {
    DatiRichiestaIstanzaPl datiRichiestaIstanza = getModelObject();

    IstanzePLMarcaAutoComplete istanzePLMarcaAutoComplete =
        new IstanzePLMarcaAutoComplete(
            "autodichiarazioneMarca",
            new PropertyModel(getModelObject(), "autodichiarazioneMarca"),
            datiRichiestaIstanza);
    istanzePLMarcaAutoComplete.setModelObject(getMarcaAutoNelVerbale(datiRichiestaIstanza));
    istanzePLMarcaAutoComplete.add(
        new AjaxFormSubmitBehavior(this, "change") {

          private static final long serialVersionUID = -1L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {}

          @Override
          protected void onError(AjaxRequestTarget target) {}
        });
    istanzePLMarcaAutoComplete.setLabel(Model.of("Marca"));
    istanzePLMarcaAutoComplete.setRequired(true);
    // containerIstanzePLAutoComplete.addOrReplace(istanzePLAutoComplete);

    String codiceHermesMotivoSelezionato = datiRichiestaIstanza.getCodiceHermesMotivoSelezionato();
    setOutputMarkupPlaceholderTag(true);
    log.error(
        "codiceHermesMotivoSelezionatocodiceHermesMotivoSelezionatocodiceHermesMotivoSelezionato: "
            + codiceHermesMotivoSelezionato);
    Label labelNessunDatoRichiesto = new Label("labelNessunDatoRichiestoStep2", "");
    addOrReplace(labelNessunDatoRichiesto);

    Label labelVisualizzaCodice03 = new Label("labelVisualizzaCodice03", "");
    addOrReplace(labelVisualizzaCodice03);

    Label labelVisualizzaCodice05 = new Label("labelVisualizzaCodice05", "");
    addOrReplace(labelVisualizzaCodice05);

    Label labelVisualizzaCodice09 = new Label("labelVisualizzaCodice09", "");
    addOrReplace(labelVisualizzaCodice09);

    Label labelVisualizzaCodice13 = new Label("labelVisualizzaCodice13", "");
    addOrReplace(labelVisualizzaCodice13);

    NonProprietarioPanel nonProprietarioPanel =
        new NonProprietarioPanel("nonProprietarioPanel", datiRichiestaIstanza);
    addOrReplace(nonProprietarioPanel);

    TargaErrataPanel targaErrataPanel =
        new TargaErrataPanel("targaErrataPanel", datiRichiestaIstanza);
    addOrReplace(targaErrataPanel);

    Boolean modelAutodichiarazioneFurto = getModelObject().getAutodichiarazioneFurto();
    Boolean modelAutodichiarazioneFurtoRitrovamento =
        getModelObject().getAutodichiarazioneFurtoRitrovamento();
    Boolean modelAutodichiarazioneProprietarioETitolare =
        getModelObject().getAutodichiarazioneProprietarioETitolare();

    List<Boolean> choices = Arrays.asList(false, true);
    BootstrapRadioChoice<Boolean> radioAutodichiarazione =
        new BootstrapRadioChoice<Boolean>(
            "autodichiarazioneFurto",
            choices,
            new YesNoRenderer("radioAutodichiarazioneIstanzaPL"));
    aggiustaRadioPerBootstrapItalia(radioAutodichiarazione);
    radioAutodichiarazione.setOutputMarkupId(true);
    radioAutodichiarazione.setOutputMarkupPlaceholderTag(true);
    WebMarkupContainer divDatiAutodichiarazione =
        new WebMarkupContainer("divDatiAutodichiarazione");
    divDatiAutodichiarazione.setOutputMarkupId(true);
    divDatiAutodichiarazione.setOutputMarkupPlaceholderTag(true);
    addOrReplace(radioAutodichiarazione);

    addOrReplace(divDatiAutodichiarazione);

    divDatiAutodichiarazione.addOrReplace(istanzePLMarcaAutoComplete);

    TextField<String> autodichiarazioneTarga =
        new TextField<String>(
            "autodichiarazioneTarga",
            new PropertyModel<String>(getModelObject(), "autodichiarazioneTarga"));
    autodichiarazioneTarga.setModelObject(getTargaNelVerbale(datiRichiestaIstanza));
    autodichiarazioneTarga.setRequired(true);
    divDatiAutodichiarazione.addOrReplace(autodichiarazioneTarga);

    // TextField<String> autodichiarazioneMarca = new
    // TextField<String>("autodichiarazioneMarca",
    // new PropertyModel<String>(getModelObject(),
    // "autodichiarazioneMarca"));
    // autodichiarazioneMarca.setRequired(true);
    // divDatiAutodichiarazione.addOrReplace(autodichiarazioneMarca);

    TextField<String> autodichiarazioneModello =
        new TextField<String>(
            "autodichiarazioneModello",
            new PropertyModel<String>(getModelObject(), "autodichiarazioneModello"));
    autodichiarazioneModello.setModelObject(getModelloNelVerbale(datiRichiestaIstanza));
    autodichiarazioneModello.setRequired(true);
    divDatiAutodichiarazione.addOrReplace(autodichiarazioneModello);

    // TextField<String> autodichiarazioneTarga2 = new
    // TextField<String>("autodichiarazioneTarga2",
    // new PropertyModel<String>(getModelObject(),
    // "autodichiarazioneTarga2"));
    // autodichiarazioneTarga2.setRequired(true);
    // divDatiAutodichiarazione.addOrReplace(autodichiarazioneTarga2);

    divDatiAutodichiarazione.addOrReplace(createTextboxDataFurto());

    List<Boolean> choices2 = Arrays.asList(false, true);
    BootstrapRadioChoice<Boolean> radioAutodichiarazioneFurtoRitrovamento =
        new BootstrapRadioChoice<Boolean>(
            "autodichiarazioneFurtoRitrovamento",
            choices2,
            new YesNoRenderer("radioAutodichiarazioneFurtoRitrovamento"));
    aggiustaRadioPerBootstrapItalia(radioAutodichiarazioneFurtoRitrovamento);
    radioAutodichiarazioneFurtoRitrovamento.setOutputMarkupId(true);
    radioAutodichiarazioneFurtoRitrovamento.setOutputMarkupPlaceholderTag(true);
    WebMarkupContainer divDatiAutodichiarazioneRitrovamento =
        new WebMarkupContainer("divDatiAutodichiarazioneRitrovamento");
    divDatiAutodichiarazioneRitrovamento.setOutputMarkupId(true);
    divDatiAutodichiarazioneRitrovamento.setOutputMarkupPlaceholderTag(true);
    divDatiAutodichiarazione.addOrReplace(radioAutodichiarazioneFurtoRitrovamento);

    divDatiAutodichiarazione.addOrReplace(divDatiAutodichiarazioneRitrovamento);

    divDatiAutodichiarazioneRitrovamento.addOrReplace(createTextboxDataRitrovamento());

    TextField<String> riconsegnatoPolizia =
        new TextField<String>(
            "riconsegnatoPolizia",
            new PropertyModel<String>(getModelObject(), "riconsegnatoPolizia"));
    riconsegnatoPolizia.setRequired(true);
    divDatiAutodichiarazioneRitrovamento.addOrReplace(riconsegnatoPolizia);

    TextField<String> riconsegnatoPoliziaComune =
        new TextField<String>(
            "riconsegnatoPoliziaComune",
            new PropertyModel<String>(getModelObject(), "riconsegnatoPoliziaComune"));
    riconsegnatoPoliziaComune.setRequired(true);
    divDatiAutodichiarazioneRitrovamento.addOrReplace(riconsegnatoPoliziaComune);

    // codice 13
    List<Boolean> choices3 = Arrays.asList(false, true);
    BootstrapRadioChoice<Boolean> autodichiarazioneProprietarioETitolare =
        new BootstrapRadioChoice<Boolean>(
            "autodichiarazioneProprietarioETitolare",
            choices3,
            new YesNoRenderer("radioAutodichiarazioneProprietarioETitolareIstanzaPL"));
    aggiustaRadioPerBootstrapItalia(autodichiarazioneProprietarioETitolare);
    autodichiarazioneProprietarioETitolare.setOutputMarkupId(true);
    autodichiarazioneProprietarioETitolare.setOutputMarkupPlaceholderTag(true);
    WebMarkupContainer divSeNonProprietario = new WebMarkupContainer("divSeNonProprietario");
    divSeNonProprietario.setOutputMarkupId(true);
    divSeNonProprietario.setOutputMarkupPlaceholderTag(true);
    addOrReplace(autodichiarazioneProprietarioETitolare);

    addOrReplace(divSeNonProprietario);

    if (modelAutodichiarazioneFurto == null || !modelAutodichiarazioneFurto) {
      divDatiAutodichiarazione.setVisible(false);
    }
    if (modelAutodichiarazioneFurtoRitrovamento == null
        || !modelAutodichiarazioneFurtoRitrovamento) {
      divDatiAutodichiarazioneRitrovamento.setVisible(false);
    }
    if (modelAutodichiarazioneProprietarioETitolare == null
        || modelAutodichiarazioneProprietarioETitolare) {
      divSeNonProprietario.setVisible(false);
    }

    labelVisualizzaCodice03.setVisible(false);
    labelVisualizzaCodice05.setVisible(false);
    labelVisualizzaCodice09.setVisible(false);
    labelVisualizzaCodice13.setVisible(false);
    nonProprietarioPanel.setVisible(false);
    targaErrataPanel.setVisible(false);
    labelNessunDatoRichiesto.setVisible(false);

    if (StringUtils.equalsIgnoreCase(codiceHermesMotivoSelezionato, "01")) {
      labelNessunDatoRichiesto.setVisible(true);
    } else if (StringUtils.equalsIgnoreCase(codiceHermesMotivoSelezionato, "02")) {
      labelNessunDatoRichiesto.setVisible(true);
    } else if (StringUtils.equalsIgnoreCase(codiceHermesMotivoSelezionato, "03")) {
      // call here se furto
      // se furto carico le marche dei veicoli
      // addOrReplace(createAndGetTextearcMarche());

      labelVisualizzaCodice03.setVisible(true);
      radioAutodichiarazione.add(
          new AjaxFormChoiceComponentUpdatingBehavior() {

            private static final long serialVersionUID = -1L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
              if (radioAutodichiarazione.getInput().equalsIgnoreCase("true")) {
                divDatiAutodichiarazione.setVisible(true);
              } else {
                divDatiAutodichiarazione.setVisible(false);
              }
              clearInput();
              target.add(Step2IstanzaPlDatiSpecificiIstanzaForm.this);
            }

            @Override
            protected void onError(AjaxRequestTarget target, RuntimeException e) {
              super.onError(target, e);
            }
          });

      radioAutodichiarazioneFurtoRitrovamento.add(
          new AjaxFormChoiceComponentUpdatingBehavior() {

            private static final long serialVersionUID = -1L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
              if (radioAutodichiarazioneFurtoRitrovamento.getInput().equalsIgnoreCase("true")) {
                divDatiAutodichiarazioneRitrovamento.setVisible(true);
              } else {
                divDatiAutodichiarazioneRitrovamento.setVisible(false);
              }
              // clearInput();
              datiRichiestaIstanza.setRiconsegnatoData(null);
              datiRichiestaIstanza.setRiconsegnatoPolizia(null);
              datiRichiestaIstanza.setRiconsegnatoPoliziaComune(null);
              target.add(divDatiAutodichiarazioneRitrovamento);
            }

            @Override
            protected void onError(AjaxRequestTarget target, RuntimeException e) {
              super.onError(target, e);
            }
          });

    } else if (StringUtils.equalsIgnoreCase(codiceHermesMotivoSelezionato, "05")) {
      labelVisualizzaCodice05.setVisible(true);
    } else if (StringUtils.equalsIgnoreCase(codiceHermesMotivoSelezionato, "06")) {
      labelNessunDatoRichiesto.setVisible(true);
    } else if (StringUtils.equalsIgnoreCase(codiceHermesMotivoSelezionato, "07")) {
      labelNessunDatoRichiesto.setVisible(true);
    } else if (StringUtils.equalsIgnoreCase(codiceHermesMotivoSelezionato, "08")) {
      labelNessunDatoRichiesto.setVisible(true);
    } else if (StringUtils.equalsIgnoreCase(codiceHermesMotivoSelezionato, "09")) {
      labelVisualizzaCodice09.setVisible(true);
    } else if (StringUtils.equalsIgnoreCase(codiceHermesMotivoSelezionato, "13")) {
      labelVisualizzaCodice13.setVisible(true);

      autodichiarazioneProprietarioETitolare.add(
          new AjaxFormChoiceComponentUpdatingBehavior() {

            private static final long serialVersionUID = 2355719866780860686L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {

              if (autodichiarazioneProprietarioETitolare.getInput().equalsIgnoreCase("true")) {
                divSeNonProprietario.setVisible(false);
              } else {
                divSeNonProprietario.setVisible(true);
              }

              target.add(divSeNonProprietario);
            }
          });

    } else if (StringUtils.equalsIgnoreCase(codiceHermesMotivoSelezionato, "32")) {
      nonProprietarioPanel.setVisible(true);
      labelNessunDatoRichiesto.setVisible(false);

    } else if (StringUtils.equalsIgnoreCase(codiceHermesMotivoSelezionato, "36")) {
      targaErrataPanel.setVisible(true);
      labelNessunDatoRichiesto.setVisible(false);
    } else {
      labelNessunDatoRichiesto.setVisible(true);
    }
  }

  private Object getMarcaAutoNelVerbale(DatiRichiestaIstanzaPl datiRichiestaIstanza) {
    if (datiRichiestaIstanza.getDatiCompletiVerbale() != null
        && datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale() != null) {
      log.debug(
          "getDettaglioVerbale().getModello(): "
              + datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getModello());
      return datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getModello();
    } else return "";
  }

  private String getModelloNelVerbale(DatiRichiestaIstanzaPl datiRichiestaIstanza) {
    if (datiRichiestaIstanza.getDatiCompletiVerbale() != null
        && datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale() != null) {
      return datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getModello();
    } else return "";
  }

  private String getTargaNelVerbale(DatiRichiestaIstanzaPl datiRichiestaIstanza) {
    if (datiRichiestaIstanza.getDatiCompletiVerbale() != null
        && datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale() != null) {
      return datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getTarga();
    } else return "";
  }

  @SuppressWarnings("unused")
  private DropDownChoice<String> createAndGetComboMarche() {
    IstanzePlDatiMarcaVeicoloSummaryRender choiceRenderer =
        new IstanzePlDatiMarcaVeicoloSummaryRender();

    String marcaSelezionata = getModelObject().getAutodichiarazioneMarca();
    log.debug("createAndGetComboMarche() marcaSelezionata: " + marcaSelezionata);

    List<String> listMarche = this.getMarche();
    getModelObject().setListDatiMarcheVeicoloSelezionabili(listMarche);

    // comboMotivi = this.creaDropDownChoice(listDatiMotivoSummary,
    // "comboMotivi", choiceRenderer,
    // Model.of(datiMotivoSummary));

    // comboMotivi.setOutputMarkupId(true);
    // comboMotivi.add(createBehaviorComboMotivi());

    return null;
  }

  private List<String> getMarche() {

    List<String> listMarche = new ArrayList<>();

    try {
      listMarche = ServiceLocator.getInstance().getServiziMieiVerbali().getMarcheVeicoli();
    } catch (Exception e) {
      log.error("listaDatiMotivoSummary: ERROREEEE " + e.getMessage());
      log.debug("listaDatiMotivoSummary: ERROREEEE size " + listMarche.size());
    }
    log.error("listaDatiMotivoSummary: " + listMarche);
    return listMarche;
  }

  private void aggiustaRadioPerBootstrapItalia(BootstrapRadioChoice<Boolean> radio) {
    radio.setInline(true);
    radio.setLabelPosition(LabelPosition.AFTER);
    radio.setPrefix("<div class=\"form-check form-check-inline spazioPerRadio\">");
    radio.setSuffix("</div>");
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public FdCLocalDateTextfield createTextboxDataFurto() {
    // furtoData = new FdCLocalDateTextfield("furtoData",
    // Model.of(getModelObject().getFurtoData()));
    // furtoData.add(new OnChangeAjaxBehavior() {
    // private static final long serialVersionUID = -1L;
    //
    // @Override
    // protected void onUpdate(AjaxRequestTarget target) {
    // getModelObject().setFurtoData(furtoData.getModelObject());
    // }
    // });

    furtoData =
        new FdCLocalDateTextfield("furtoData", new PropertyModel(getModelObject(), "furtoData"));

    furtoData.setLabel(Model.of("Data furto"));
    furtoData.setRequired(true);

    return furtoData;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public FdCLocalDateTextfield createTextboxDataRitrovamento() {
    // ritrovatoData = new FdCLocalDateTextfield("ritrovamentoData",
    // Model.of(getModelObject().getRitrovamentoData()));
    // ritrovatoData.add(new OnChangeAjaxBehavior() {
    // private static final long serialVersionUID = -1L;
    //
    // @Override
    // protected void onUpdate(AjaxRequestTarget target) {
    // getModelObject().setRitrovamentoData(ritrovatoData.getModelObject());
    // }
    // });

    ritrovatoData =
        new FdCLocalDateTextfield(
            "ritrovamentoData", new PropertyModel(getModelObject(), "ritrovamentoData"));

    ritrovatoData.setLabel(Model.of("Data ritrovamento"));
    ritrovatoData.setRequired(true);

    return ritrovatoData;
  }
}
