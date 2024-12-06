package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep1;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep2;
import it.liguriadigitale.ponmetro.portale.pojo.portale.AgevolazioneTariffariaRistorazione;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.form.pojo.MembroNucleoChePercepivaReddito;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.TelefonoFissoCellulareValidator;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffaria.AccettazioneNucleoIseeAnagraficoEnum;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.validation.validator.StringValidator;

public class RichiestaAgevolazioneStep2Form extends AbstracFrameworkForm<AgevolazioneStep2> {

  private static final long serialVersionUID = -658798504729052572L;

  private Component pannello;

  private WebMarkupContainer containerDisoccupati;

  public RichiestaAgevolazioneStep2Form(String id, AgevolazioneStep2 model, Component pannello) {
    super(id, model);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setPannello(pannello);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void addElementiForm() {

    AgevolazioneStep2 step2 = getModelObject();

    WebMarkupContainer containerDatiForm = new WebMarkupContainer("containerDatiForm");
    containerDatiForm.setVisible(checkContainerDatiFormVisible(step2.getStep1()));
    addOrReplace(containerDatiForm);

    NumberTextField<Integer> numeroMinoriACarico =
        new NumberTextField<Integer>(
            "numeroMinoriACarico",
            new PropertyModel<Integer>(getModelObject(), "numeroMinoriACarico"));
    numeroMinoriACarico.setRequired(true);
    numeroMinoriACarico.setMinimum(0);
    numeroMinoriACarico.setLabel(Model.of("Numero minori a carico"));
    containerDatiForm.addOrReplace(numeroMinoriACarico);

    Label numeroMinoriACaricoLabel =
        new NotEmptyLabel("numeroMinoriACaricoLabel", "Numero minori a carico");
    numeroMinoriACaricoLabel.setOutputMarkupId(true);
    numeroMinoriACaricoLabel.setOutputMarkupPlaceholderTag(true);
    numeroMinoriACaricoLabel.add(new AttributeModifier("for", numeroMinoriACarico.getMarkupId()));
    containerDatiForm.addOrReplace(numeroMinoriACaricoLabel);

    EmailTextField emailContatto =
        new EmailTextField(
            "emailContatto", new PropertyModel<String>(getModelObject(), "emailContatto"));
    emailContatto.setRequired(true);
    emailContatto.setLabel(Model.of("Email"));
    emailContatto.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -270871948846254935L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            getModelObject().setEmailContatto(emailContatto.getValue());
          }
        });
    containerDatiForm.addOrReplace(emailContatto);

    TextField telefonoContatto =
        new TextField(
            "telefonoContatto", new PropertyModel<String>(getModelObject(), "telefonoContatto"));
    telefonoContatto.setRequired(true);
    telefonoContatto.setLabel(Model.of("Telefono"));
    telefonoContatto.add(new TelefonoFissoCellulareValidator());
    telefonoContatto.add(StringValidator.maximumLength(13));
    telefonoContatto.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -4332012520040413222L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            getModelObject().setTelefonoContatto(telefonoContatto.getValue());
          }
        });
    containerDatiForm.addOrReplace(telefonoContatto);

    containerDatiForm.addOrReplace(creaListaNomiSelezionati(step2.getStep1()));
    containerDatiForm.addOrReplace(creaLabelAnnoScolasticoSelezionato(step2.getStep1()));

    Double iseeOrd = 0.0;
    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
    String moneyString = "";
    String dataPresentazioneIsee = "";

    if (LabelFdCUtil.checkIfNotNull(step2.getStep1())) {

      if (LabelFdCUtil.checkIfNotNull(step2.getStep1().getAttestazioneIseeModi())) {

        iseeOrd =
            Double.parseDouble(
                step2
                    .getStep1()
                    .getAttestazioneIseeModi()
                    .getConsultazioneAttestazioneResponse()
                    .getConsultazioneAttestazioneResult()
                    .getXmlEsitoAttestazioneDecoded()
                    .getEsitoAttestazione()
                    .getAttestazione()
                    .getOrdinario()
                    .getIsEEOrdinario()
                    .getValori()
                    .getISEE());

        LocalDate dataPresentazioneIseeLocalDate =
            step2
                .getStep1()
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione()
                .getAttestazione()
                .getDataPresentazione();

        if (LabelFdCUtil.checkIfNotNull(dataPresentazioneIseeLocalDate)) {
          dataPresentazioneIsee =
              LocalDateUtil.getDataFormatoEuropeo(dataPresentazioneIseeLocalDate);
        }
      }
    }

    BigDecimal iseeOrdinario = BigDecimal.valueOf(iseeOrd);
    moneyString = formatter.format(iseeOrdinario);

    Label dichiaroPuntoA =
        new Label(
            "dichiaroPuntoA",
            new StringResourceModel("RichiestaAgevolazionePanel.dichiaroPuntoA", this)
                .setParameters(moneyString, dataPresentazioneIsee));
    dichiaroPuntoA.setEscapeModelStrings(false);
    containerDatiForm.addOrReplace(dichiaroPuntoA);

    containerDisoccupati = new WebMarkupContainer("containerDisoccupati");
    containerDisoccupati.setOutputMarkupId(true);
    containerDisoccupati.setOutputMarkupPlaceholderTag(true);
    containerDisoccupati.setVisible(
        LabelFdCUtil.checkIfNotNull(getModelObject().getComboSiNoPuntoA())
            && !getModelObject().getComboSiNoPuntoA().equalsIgnoreCase("No"));
    containerDatiForm.addOrReplace(containerDisoccupati);

    SiNoDropDownChoice comboSiNoPuntoA =
        new SiNoDropDownChoice(
            "comboSiNoPuntoA", new PropertyModel<String>(getModelObject(), "comboSiNoPuntoA"));
    comboSiNoPuntoA.setLabel(Model.of("Sono intervenute disoccupazioni?"));
    comboSiNoPuntoA.setOutputMarkupId(true);
    comboSiNoPuntoA.setOutputMarkupPlaceholderTag(true);

    comboSiNoPuntoA.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 7305198839712402367L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            log.debug("CP click scelta comboSiNoPuntoA = " + comboSiNoPuntoA.getValue());

            if (comboSiNoPuntoA.getValue().equalsIgnoreCase("0")) {
              containerDisoccupati.setVisible(true);
            } else {
              containerDisoccupati.setVisible(false);
            }

            target.add(containerDisoccupati);
          }
        });

    containerDatiForm.addOrReplace(comboSiNoPuntoA);

    ListView<MembroNucleoChePercepivaReddito> boxMembriNucleoCheAvevanoReddito =
        new ListView<MembroNucleoChePercepivaReddito>(
            "boxMembriNucleoCheAvevanoReddito", getListaMembriNucleo(step2)) {

          private static final long serialVersionUID = 5529675413637189114L;

          @Override
          protected void populateItem(ListItem<MembroNucleoChePercepivaReddito> itemNucleo) {
            final MembroNucleoChePercepivaReddito membroNucleo = itemNucleo.getModelObject();

            String nomeCognome = "";
            if (LabelFdCUtil.checkIfNotNull(membroNucleo)
                && LabelFdCUtil.checkIfNotNull(membroNucleo.getDatiMembroNucleo())) {
              nomeCognome =
                  membroNucleo
                      .getDatiMembroNucleo()
                      .getNome()
                      .concat(" ")
                      .concat(membroNucleo.getDatiMembroNucleo().getCognome());
            }

            NotEmptyLabel nomeMembroComboSiNo =
                new NotEmptyLabel(
                    "nomeMembroComboSiNo",
                    new StringResourceModel("RichiestaAgevolazionePanel.nomeMembroComboSiNo", this)
                        .setParameters(nomeCognome));
            itemNucleo.addOrReplace(nomeMembroComboSiNo);

            if (membroNucleo != null && membroNucleo.getComboSiNoMembroNucleo() == null) {
              membroNucleo.setComboSiNoMembroNucleo("No");
            }

            SiNoDropDownChoice comboSiNoMembroNucleo =
                new SiNoDropDownChoice(
                    "comboSiNoMembroNucleo",
                    new PropertyModel<String>(membroNucleo, "comboSiNoMembroNucleo"));
            comboSiNoMembroNucleo.setLabel(
                Model.of(nomeCognome.concat(" sei stato disoccupato negli ultimi due anni?")));
            comboSiNoMembroNucleo.setOutputMarkupId(true);
            comboSiNoMembroNucleo.setOutputMarkupPlaceholderTag(true);

            WebMarkupContainer wmkDataDisoccupazione =
                new WebMarkupContainer("wmkDataDisoccupazione");

            FdCLocalDateTextfield dataDisoccupazioneMembroNucleo =
                new FdCLocalDateTextfield(
                    "dataDisoccupazioneMembroNucleo",
                    new PropertyModel(membroNucleo, "dataDisoccupazioneMembroNucleo"));
            dataDisoccupazioneMembroNucleo.setLabel(
                Model.of("Data di inizio disoccupazione di ".concat(nomeCognome)));
            dataDisoccupazioneMembroNucleo.setOutputMarkupId(true);
            dataDisoccupazioneMembroNucleo.setOutputMarkupPlaceholderTag(true);
            dataDisoccupazioneMembroNucleo.setRequired(false);
            dataDisoccupazioneMembroNucleo.add(AttributeModifier.remove("required"));
            dataDisoccupazioneMembroNucleo.setMarkupId(
                "dataDisoccupazioneMembroNucleo" + itemNucleo.getIndex());

            wmkDataDisoccupazione.addOrReplace(dataDisoccupazioneMembroNucleo);
            wmkDataDisoccupazione.setOutputMarkupPlaceholderTag(true);
            wmkDataDisoccupazione.setOutputMarkupPlaceholderTag(true);

            wmkDataDisoccupazione.setVisible(
                membroNucleo.getComboSiNoMembroNucleo() != null
                    && !membroNucleo.getComboSiNoMembroNucleo().equalsIgnoreCase("No"));

            Label labelData = new Label("labelData", "Data inizio disoccupazione");
            labelData.setOutputMarkupId(true);
            labelData.setOutputMarkupPlaceholderTag(true);
            labelData.setMarkupId("labelData" + itemNucleo.getIndex());
            labelData.add(
                new AttributeModifier(
                    "for", "dataDisoccupazioneMembroNucleo" + itemNucleo.getIndex()));
            wmkDataDisoccupazione.addOrReplace(labelData);

            itemNucleo.addOrReplace(wmkDataDisoccupazione);

            comboSiNoMembroNucleo.add(
                new AjaxFormComponentUpdatingBehavior("change") {

                  private static final long serialVersionUID = 8521826766549997191L;

                  @Override
                  protected void onUpdate(AjaxRequestTarget target) {
                    log.debug(
                        "CP click scelta comboSiNoMembro = " + comboSiNoMembroNucleo.getValue());

                    if (comboSiNoMembroNucleo.getValue().equalsIgnoreCase("0")) {

                      AttributeModifier attributeModifierRequired =
                          new AttributeModifier("required", "");
                      dataDisoccupazioneMembroNucleo.add(attributeModifierRequired);
                      dataDisoccupazioneMembroNucleo.setRequired(true);

                      wmkDataDisoccupazione.setVisible(true);
                    } else {

                      dataDisoccupazioneMembroNucleo.setRequired(false);
                      dataDisoccupazioneMembroNucleo.add(AttributeModifier.remove("required"));

                      wmkDataDisoccupazione.setVisible(false);

                      membroNucleo.setDataDisoccupazioneMembroNucleo(null);
                    }

                    target.add(wmkDataDisoccupazione);
                  }
                });

            itemNucleo.addOrReplace(comboSiNoMembroNucleo);
          }
        };

    boxMembriNucleoCheAvevanoReddito.setOutputMarkupId(true);
    boxMembriNucleoCheAvevanoReddito.setOutputMarkupPlaceholderTag(true);
    containerDisoccupati.addOrReplace(boxMembriNucleoCheAvevanoReddito);
  }

  private List<NucleoFamiliareComponenteNucleoInner> getListaPortatoriDiRedditoConIseeModi(
      AgevolazioneStep2 step2) {
    return ServiceLocator.getInstance()
        .getServiziRistorazione()
        .getListaPortatoriDiRedditoConIseeModi(step2);
  }

  private List<MembroNucleoChePercepivaReddito> getListaMembriNucleo(AgevolazioneStep2 step2) {
    List<MembroNucleoChePercepivaReddito> listaMembri =
        new ArrayList<MembroNucleoChePercepivaReddito>();

    List<NucleoFamiliareComponenteNucleoInner> listaIseeModi =
        getListaPortatoriDiRedditoConIseeModi(step2);
    if (LabelFdCUtil.checkIfNotNull(listaIseeModi)) {
      for (NucleoFamiliareComponenteNucleoInner elem : listaIseeModi) {
        MembroNucleoChePercepivaReddito membro = new MembroNucleoChePercepivaReddito();
        membro.setDatiMembroNucleo(elem);
        listaMembri.add(membro);
      }
    }

    step2.setListaMembriNucleo(listaMembri);

    return listaMembri;
  }

  private Label creaLabelAnnoScolasticoSelezionato(AgevolazioneStep1 step1) {
    Integer anno = step1.getAnnoScolastico();
    String testo = anno + "/" + (++anno);
    return new Label("annoScolasticoSelezionato", testo);
  }

  private RepeatingView creaListaNomiSelezionati(AgevolazioneStep1 step1) {

    RepeatingView listItems = new RepeatingView("listaNomiIscritti");
    String dummy = "vuoto";
    String lastChildId = dummy;
    for (AgevolazioneTariffariaRistorazione figlio : step1.getListaFigliPerRichiesta()) {
      if (figlio.isSelezionato()) {
        String nome =
            new StringBuilder()
                .append(figlio.getNome())
                .append(" ")
                .append(figlio.getCognome())
                .toString();
        listItems.add(new Label(listItems.newChildId(), nome));
        lastChildId = listItems.newChildId();
        listItems.add(new Label(lastChildId, ", "));
      }
    }
    if (!lastChildId.equalsIgnoreCase(dummy)) listItems.get(lastChildId).setVisible(false);

    return listItems;
  }

  public Component getPannello() {
    return pannello;
  }

  public void setPannello(Component pannello) {
    this.pannello = pannello;
  }

  private boolean checkContainerDatiFormVisible(AgevolazioneStep1 step1) {
    return LabelFdCUtil.checkIfNotNull(step1)
        && LabelFdCUtil.checkIfNotNull(step1.getAccettazioneNuclei())
        && (step1.getAccettazioneNuclei().equals(AccettazioneNucleoIseeAnagraficoEnum.ACCETTA)
            || step1
                .getAccettazioneNuclei()
                .equals(AccettazioneNucleoIseeAnagraficoEnum.NUCLEI_COINCIDONO));
  }
}
