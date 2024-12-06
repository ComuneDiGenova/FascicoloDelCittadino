package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.soggetticoinvolti;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.Accompagnatore;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.EnumTipoDomandaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.combo.ComboNucleoFamigliare;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.combo.ComboTipoPatente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.panel.MessagePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.validator.ValidatorUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class AccompagnatorePanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  WebMarkupContainer wmkDatiAccompagnatore;

  TextField<String> codiceFiscaleAccompagnatore;
  TextField<String> cognomeAccompagnatore;
  TextField<String> nomeAccompagnatore;
  FdCLocalDateTextfield dataNascitaAccompagnatore;
  TextField<String> luogoNascitaAccompagnatore;
  TextField<String> provinciaNascitaAccompagnatore;
  // -- residenza
  WebMarkupContainer wmkResidenza;
  TextField<String> indirizzoResidenzaAccompagnatore;
  TextField<String> capResidenzaAccompagnatore;
  TextField<String> cittaResidenzaAccompagnatore;
  TextField<String> provinciaResidenzaAccompagnatore;
  // -- contatti
  TextField<String> telefonoAccompagnatore;
  TextField<String> cellulareAccompagnatore;
  EmailTextField emailAccompagnatore;
  EmailTextField pecAccompagnatore;
  // -- patente
  ComboTipoPatente tipoPatenteAccompagnatore;
  TextField<String> numeroPantenteAccompagnatore;
  FdCLocalDateTextfield dataRilascioPatenteAccompagnatore;
  FdCLocalDateTextfield dataScadenzaPatenteAccompagnatore;

  CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel;

  private CheckBox faParteDelNucleoFamigliare;
  private DropDownChoice<ComponenteNucleo> comboComponentiNucleoFamigliare;
  private WebMarkupContainer wmkFaParteDelNucleoFamigliare;
  private boolean checkFaParteDelNucleoFamigliareVisible;

  private WebMarkupContainer wmkRicercaAcccompagnatorePerCodiceFiscalePatente;
  private TextField<String> codiceFiscalePerRicercaAccompagnatore;
  // private TextField<String> cartaIdentitaPerRicercaAccompagnatore;

  private ArrayList<String> listaMessaggiAccompagnatore;
  private MessagePanel messagePanelAccompagnatore;

  private boolean attivo;

  public AccompagnatorePanel(
      String id,
      CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel,
      boolean attivo) {
    super(id);
    this.attivo = attivo;
    this.richiestaPermessoPersonalizzatoModel = richiestaPermessoPersonalizzatoModel;
    listaMessaggiAccompagnatore = new ArrayList<String>();
    messagePanelAccompagnatore =
        new MessagePanel("messagePanelAccompagnatore", listaMessaggiAccompagnatore);
    addOrReplace(messagePanelAccompagnatore);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {

    boolean isResidente = getUtente().isResidente();

    createFeedBackStep4Panel();

    checkFaParteDelNucleoFamigliareVisible = true;

    addOrReplace(
        wmkFaParteDelNucleoFamigliare = new WebMarkupContainer("wmkFaParteDelNucleoFamigliare"));

    wmkFaParteDelNucleoFamigliare.addOrReplace(
        faParteDelNucleoFamigliare = creaToggleFaParteDelNucleoFamigliare());

    addOrReplace(
        comboComponentiNucleoFamigliare =
            new ComboNucleoFamigliare(
                "comboComponentiNucleoFamigliare",
                getUtente(),
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.componenteNucleoFamigliare")));
    comboComponentiNucleoFamigliare.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 4567199981239849845L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            log.error("aggiornamento pannello accompagnatore.");

            ComponenteNucleo componenteNucleo = comboComponentiNucleoFamigliare.getModelObject();

            Accompagnatore accompagnatore =
                richiestaPermessoPersonalizzatoModel
                    .getObject()
                    .getSoggettiCoinvolti()
                    .getAccompagnatore();
            accompagnatore.setCapResidenzaAccompagnatore(
                componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvPostCode());
            // accompagnatore.setCellulareAccompagnatore("");
            accompagnatore.setCittaResidenzaAccompagnatore(
                componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvCity());
            accompagnatore.setCodiceFiscaleAccompagnatore(
                componenteNucleo.getDatiCittadino().getCpvTaxCode());
            accompagnatore.setCodiceIndividuoAccompagnatore(
                componenteNucleo.getDatiCittadino().getCpvPersonID());
            accompagnatore.setCognomeAccompagnatore(
                componenteNucleo.getDatiCittadino().getCpvFamilyName());
            accompagnatore.setDataNascitaAccompagnatore(componenteNucleo.getDataNascita());
            // accompagnatore.setDataRilascioPatenteAccompagnatore(null);
            // accompagnatore.setDataScadenzaPatenteAccompagnatore(null);
            // accompagnatore.setEmailAccompagnatore(null);

            try {
              accompagnatore.setCodCivicoResidenzaAccompagnatore(
                  Integer.parseInt(
                      componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvStreetNumber()));
            } catch (Exception e) {
              log.error("Errore impostazione CodCivicoResidenzaAccompagnatore");
            }

            try {
              accompagnatore.setCodInternoResidenzaAccompagnatore(
                  Integer.parseInt(
                      componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvFlatNumber()));
            } catch (Exception e) {
              log.error("Errore impostazione CodInternoResidenzaAccompagnatore");
            }

            accompagnatore.setLuogoResidenzaAccompagnatore(
                componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvCity());

            accompagnatore.setIndirizzoResidenzaAccompagnatore(
                componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvFullAddress());
            accompagnatore.setLuogoNascitaAccompagnatore(
                componenteNucleo.getDatiCittadino().getCpvHasBirthPlace().getClvCity());
            accompagnatore.setNomeAccompagnatore(
                componenteNucleo.getDatiCittadino().getCpvGivenName());
            // accompagnatore.setNumeroPantenteAccompagnatore(null);
            // accompagnatore.setPecAccompagnatore(null);
            accompagnatore.setProvinciaNascitaAccompagnatore(
                componenteNucleo.getDatiCittadino().getCpvHasBirthPlace().getClvProvince());
            accompagnatore.setProvinciaResidenzaAccompagnatore("GE");
            // accompagnatore.setTelefonoAccompagnatore(null);
            // accompagnatore.setTipoPatenteAccompagnatore(null);
            richiestaPermessoPersonalizzatoModel
                .getObject()
                .getSoggettiCoinvolti()
                .setAccompagnatore(accompagnatore);

            wmkDatiAccompagnatore.setVisible(
                richiestaPermessoPersonalizzatoModel
                            .getObject()
                            .getSoggettiCoinvolti()
                            .getAccompagnatore()
                            .getCodiceFiscaleAccompagnatore()
                        != null
                    && !richiestaPermessoPersonalizzatoModel
                        .getObject()
                        .getSoggettiCoinvolti()
                        .getAccompagnatore()
                        .getCodiceFiscaleAccompagnatore()
                        .equalsIgnoreCase(""));
            wmkResidenza.setVisible(true);
            target.add(AccompagnatorePanel.this);
          }
        });

    comboComponentiNucleoFamigliare.setOutputMarkupId(true);
    comboComponentiNucleoFamigliare.setOutputMarkupPlaceholderTag(true);
    /*
     * comboComponentiNucleoFamigliare.setVisible(!comboComponentiNucleoFamigliare.
     * getChoices().isEmpty() && richiestaPermessoPersonalizzatoModel.getObject()
     * .getSoggettiCoinvolti().getAccompagnatore().isFaParteDelNucleoFamigliare());
     */
    wmkFaParteDelNucleoFamigliare.setVisible(
        !comboComponentiNucleoFamigliare.getChoices().isEmpty()
            && checkFaParteDelNucleoFamigliareVisible
            && attivo);

    addOrReplace(
        wmkRicercaAcccompagnatorePerCodiceFiscalePatente =
            new WebMarkupContainer("wmkRicercaAcccompagnatorePerCodiceFiscalePatente"));
    wmkRicercaAcccompagnatorePerCodiceFiscalePatente.addOrReplace(
        codiceFiscalePerRicercaAccompagnatore =
            new TextField<String>(
                "codiceFiscalePerRicercaAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.codiceFiscalePerRicercaAccompagnatore")));
    //		wmkRicercaAcccompagnatorePerCodiceFiscalePatente.addOrReplace(
    //				cartaIdentitaPerRicercaAccompagnatore = new
    // TextField<String>("cartaIdentitaPerRicercaAccompagnatore",
    //						richiestaPermessoPersonalizzatoModel
    //								.bind("soggettiCoinvolti.accompagnatore.cartaIdentitaPerRicercaAccompagnatore")));
    wmkRicercaAcccompagnatorePerCodiceFiscalePatente.addOrReplace(
        creaBottoneCercaPerCodiceFiscale());

    comboComponentiNucleoFamigliare.setVisible(
        !comboComponentiNucleoFamigliare.getChoices().isEmpty()
            && faParteDelNucleoFamigliare.getModelObject()
            && attivo);
    wmkRicercaAcccompagnatorePerCodiceFiscalePatente.setVisible(
        isResidente && !faParteDelNucleoFamigliare.getModelObject()
            || (richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda() != null
                && !isResidente
                && (EnumTipoDomandaPermessoPersonalizzato.getById(
                            richiestaPermessoPersonalizzatoModel
                                .getObject()
                                .getTipoDomanda()
                                .getCodice())
                        .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_MINORE)
                    || EnumTipoDomandaPermessoPersonalizzato.getById(
                            richiestaPermessoPersonalizzatoModel
                                .getObject()
                                .getTipoDomanda()
                                .getCodice())
                        .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_TUTORE))));

    addOrReplace(wmkDatiAccompagnatore = new WebMarkupContainer("wmkDatiAccompagnatore"));
    wmkDatiAccompagnatore.setOutputMarkupId(true);
    wmkDatiAccompagnatore.setOutputMarkupPlaceholderTag(true);

    wmkDatiAccompagnatore.addOrReplace(
        codiceFiscaleAccompagnatore =
            new TextField<String>(
                "codiceFiscaleAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.codiceFiscaleAccompagnatore")));
    codiceFiscaleAccompagnatore.setEnabled(!checkFaParteDelNucleoFamigliareVisible);
    wmkDatiAccompagnatore.addOrReplace(
        cognomeAccompagnatore =
            new TextField<String>(
                "cognomeAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.cognomeAccompagnatore")));
    cognomeAccompagnatore.setEnabled(!checkFaParteDelNucleoFamigliareVisible);
    wmkDatiAccompagnatore.addOrReplace(
        nomeAccompagnatore =
            new TextField<String>(
                "nomeAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.nomeAccompagnatore")));
    nomeAccompagnatore.setEnabled(!checkFaParteDelNucleoFamigliareVisible);
    wmkDatiAccompagnatore.addOrReplace(
        dataNascitaAccompagnatore =
            new FdCLocalDateTextfield(
                "dataNascitaAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.dataNascitaAccompagnatore")));
    dataNascitaAccompagnatore.setEnabled(!checkFaParteDelNucleoFamigliareVisible);
    wmkDatiAccompagnatore.addOrReplace(
        luogoNascitaAccompagnatore =
            new TextField<String>(
                "luogoNascitaAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.luogoNascitaAccompagnatore")));
    luogoNascitaAccompagnatore.setEnabled(!checkFaParteDelNucleoFamigliareVisible);
    wmkDatiAccompagnatore.addOrReplace(
        provinciaNascitaAccompagnatore =
            new TextField<String>(
                "provinciaNascitaAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.provinciaNascitaAccompagnatore")));
    provinciaNascitaAccompagnatore.setEnabled(!checkFaParteDelNucleoFamigliareVisible);
    // -- residenza
    wmkDatiAccompagnatore.addOrReplace(wmkResidenza = new WebMarkupContainer("wmkResidenza"));

    wmkResidenza.addOrReplace(
        indirizzoResidenzaAccompagnatore =
            new TextField<String>(
                "indirizzoResidenzaAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.indirizzoResidenzaAccompagnatore")));
    indirizzoResidenzaAccompagnatore.setEnabled(!checkFaParteDelNucleoFamigliareVisible);
    wmkResidenza.addOrReplace(
        capResidenzaAccompagnatore =
            new TextField<String>(
                "capResidenzaAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.capResidenzaAccompagnatore")));
    capResidenzaAccompagnatore.setEnabled(!checkFaParteDelNucleoFamigliareVisible);
    wmkResidenza.addOrReplace(
        cittaResidenzaAccompagnatore =
            new TextField<String>(
                "cittaResidenzaAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.cittaResidenzaAccompagnatore")));
    cittaResidenzaAccompagnatore.setEnabled(!checkFaParteDelNucleoFamigliareVisible);
    wmkResidenza.addOrReplace(
        provinciaResidenzaAccompagnatore =
            new TextField<String>(
                "provinciaResidenzaAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.provinciaResidenzaAccompagnatore")));
    provinciaResidenzaAccompagnatore.setEnabled(!checkFaParteDelNucleoFamigliareVisible);
    // -- contatti
    wmkDatiAccompagnatore.addOrReplace(
        telefonoAccompagnatore =
            new TextField<String>(
                "telefonoAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.telefonoAccompagnatore")));
    telefonoAccompagnatore.setEnabled(true);
    wmkDatiAccompagnatore.addOrReplace(
        cellulareAccompagnatore =
            new TextField<String>(
                "cellulareAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.cellulareAccompagnatore")));
    cellulareAccompagnatore.setEnabled(true);
    wmkDatiAccompagnatore.addOrReplace(
        emailAccompagnatore =
            new EmailTextField(
                "emailAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.emailAccompagnatore")));

    emailAccompagnatore.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AccompagnatorePanel.emailAccompagnatore", AccompagnatorePanel.this)));

    emailAccompagnatore.setEnabled(true);
    wmkDatiAccompagnatore.addOrReplace(
        pecAccompagnatore =
            new EmailTextField(
                "pecAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.pecAccompagnatore")));

    pecAccompagnatore.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AccompagnatorePanel.pecAccompagnatore", AccompagnatorePanel.this)));

    pecAccompagnatore.setEnabled(true);
    // -- pantente
    wmkDatiAccompagnatore.addOrReplace(
        tipoPatenteAccompagnatore =
            new ComboTipoPatente(
                "tipoPatenteAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.tipoPatenteAccompagnatore")));

    wmkDatiAccompagnatore.addOrReplace(
        numeroPantenteAccompagnatore =
            new TextField<String>(
                "numeroPantenteAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.numeroPantenteAccompagnatore")));
    wmkDatiAccompagnatore.addOrReplace(
        dataRilascioPatenteAccompagnatore =
            new FdCLocalDateTextfield(
                "dataRilascioPatenteAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.dataRilascioPatenteAccompagnatore")));

    dataRilascioPatenteAccompagnatore.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "AccompagnatorePanel.dataRilascioPatenteAccompagnatore",
                    AccompagnatorePanel.this)));

    wmkDatiAccompagnatore.addOrReplace(
        dataScadenzaPatenteAccompagnatore =
            new FdCLocalDateTextfield(
                "dataScadenzaPatenteAccompagnatore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.accompagnatore.dataScadenzaPatenteAccompagnatore")));

    dataScadenzaPatenteAccompagnatore.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "AccompagnatorePanel.dataScadenzaPatenteAccompagnatore",
                    AccompagnatorePanel.this)));

    wmkDatiAccompagnatore.setVisible(
        (richiestaPermessoPersonalizzatoModel
                        .getObject()
                        .getSoggettiCoinvolti()
                        .getAccompagnatore()
                        .getCodiceFiscaleAccompagnatore()
                    != null
                && !richiestaPermessoPersonalizzatoModel
                    .getObject()
                    .getSoggettiCoinvolti()
                    .getAccompagnatore()
                    .getCodiceFiscaleAccompagnatore()
                    .equalsIgnoreCase(""))
            || (!getUtente().isResidente()
                && !(richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda() != null
                    && (EnumTipoDomandaPermessoPersonalizzato.getById(
                                richiestaPermessoPersonalizzatoModel
                                    .getObject()
                                    .getTipoDomanda()
                                    .getCodice())
                            .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_MINORE)
                        || EnumTipoDomandaPermessoPersonalizzato.getById(
                                richiestaPermessoPersonalizzatoModel
                                    .getObject()
                                    .getTipoDomanda()
                                    .getCodice())
                            .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_TUTORE)))));
  }

  private CheckBox creaToggleFaParteDelNucleoFamigliare() {
    faParteDelNucleoFamigliare =
        new CheckBox(
            "faParteDelNucleoFamigliare",
            richiestaPermessoPersonalizzatoModel.bind(
                "soggettiCoinvolti.accompagnatore.faParteDelNucleoFamigliare"));
    faParteDelNucleoFamigliare.setRequired(true);
    faParteDelNucleoFamigliare.setOutputMarkupId(true);
    faParteDelNucleoFamigliare.setOutputMarkupPlaceholderTag(true);
    faParteDelNucleoFamigliare.setVisible(checkFaParteDelNucleoFamigliareVisible);

    faParteDelNucleoFamigliare.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = 1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            comboComponentiNucleoFamigliare.setVisible(faParteDelNucleoFamigliare.getModelObject());
            wmkRicercaAcccompagnatorePerCodiceFiscalePatente.setVisible(
                !faParteDelNucleoFamigliare.getModelObject());
            wmkDatiAccompagnatore.setVisible(false);

            comboComponentiNucleoFamigliare.setModelObject(null);
            target.add(AccompagnatorePanel.this);
          }
        });

    return faParteDelNucleoFamigliare;
  }

  private LaddaAjaxButton creaBottoneCercaPerCodiceFiscale() {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("cercaPerCodiceFiscale", Type.Primary) {

          private static final long serialVersionUID = 6018267445510360466L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            List<String> lm = messagePanelAccompagnatore.getListaMessaggi();
            lm.clear();
            try {

              if (codiceFiscalePerRicercaAccompagnatore.getModelObject() != null
                  && !codiceFiscalePerRicercaAccompagnatore.getModelObject().equalsIgnoreCase("")) {
                //							cartaIdentitaPerRicercaAccompagnatore.getModelObject() != null &&
                // !cartaIdentitaPerRicercaAccompagnatore.getModelObject().equalsIgnoreCase(""))

                if (ValidatorUtil.codiceFiscaleValido(
                    codiceFiscalePerRicercaAccompagnatore.getModelObject())) {

                  Residente datiResidenteCoabitante =
                      ServiceLocator.getInstance()
                          .getServizioDemografico()
                          .getDatiResidente(codiceFiscalePerRicercaAccompagnatore.getModelObject());

                  //					if (datiResidenteCoabitante.getGenovaOntoIDCardNumber()
                  //
                  //	.equalsIgnoreCase(cartaIdentitaPerRicercaAccompagnatore.getModelObject())) {

                  Accompagnatore accompagnatore =
                      richiestaPermessoPersonalizzatoModel
                          .getObject()
                          .getSoggettiCoinvolti()
                          .getAccompagnatore();
                  accompagnatore.setCapResidenzaAccompagnatore(
                      datiResidenteCoabitante.getCpvHasAddress().getClvPostCode());
                  // accompagnatore.setCellulareAccompagnatore("");
                  accompagnatore.setCittaResidenzaAccompagnatore(
                      datiResidenteCoabitante.getCpvHasAddress().getClvCity());
                  accompagnatore.setCodiceFiscaleAccompagnatore(
                      datiResidenteCoabitante.getCpvTaxCode());
                  accompagnatore.setCodiceIndividuoAccompagnatore(
                      datiResidenteCoabitante.getCpvPersonID());
                  accompagnatore.setCognomeAccompagnatore(
                      datiResidenteCoabitante.getCpvFamilyName());
                  accompagnatore.setDataNascitaAccompagnatore(
                      datiResidenteCoabitante.getCpvDateOfBirth());

                  try {
                    accompagnatore.setCodCivicoResidenzaAccompagnatore(
                        Integer.parseInt(
                            datiResidenteCoabitante.getCpvHasAddress().getClvStreetNumber()));
                  } catch (Exception e) {
                    log.error("Errore impostazione CodCivicoResidenzaAccompagnatore");
                  }

                  try {
                    accompagnatore.setCodInternoResidenzaAccompagnatore(
                        Integer.parseInt(
                            datiResidenteCoabitante.getCpvHasAddress().getClvFlatNumber()));

                  } catch (Exception e) {
                    log.error("Errore impostazione CodInternoResidenzaAccompagnatore");
                  }

                  accompagnatore.setLuogoResidenzaAccompagnatore(
                      datiResidenteCoabitante.getCpvHasAddress().getClvCity());
                  // accompagnatore.setDataRilascioPatenteAccompagnatore(null);
                  // accompagnatore.setDataScadenzaPatenteAccompagnatore(null);
                  // accompagnatore.setEmailAccompagnatore(null);
                  accompagnatore.setIndirizzoResidenzaAccompagnatore(
                      datiResidenteCoabitante.getCpvHasAddress().getClvFullAddress());
                  accompagnatore.setLuogoNascitaAccompagnatore(
                      datiResidenteCoabitante.getCpvHasBirthPlace().getClvCity());
                  accompagnatore.setNomeAccompagnatore(datiResidenteCoabitante.getCpvGivenName());
                  // accompagnatore.setNumeroPantenteAccompagnatore(null);
                  // accompagnatore.setPecAccompagnatore(null);
                  accompagnatore.setProvinciaNascitaAccompagnatore(
                      datiResidenteCoabitante.getCpvHasBirthPlace().getClvProvince());
                  accompagnatore.setProvinciaResidenzaAccompagnatore("GE");
                  // accompagnatore.setTelefonoAccompagnatore(null);
                  // accompagnatore.setTipoPatenteAccompagnatore(null);
                  richiestaPermessoPersonalizzatoModel
                      .getObject()
                      .getSoggettiCoinvolti()
                      .setAccompagnatore(accompagnatore);

                  wmkDatiAccompagnatore.setVisible(
                      richiestaPermessoPersonalizzatoModel
                                  .getObject()
                                  .getSoggettiCoinvolti()
                                  .getAccompagnatore()
                                  .getCodiceFiscaleAccompagnatore()
                              != null
                          && !richiestaPermessoPersonalizzatoModel
                              .getObject()
                              .getSoggettiCoinvolti()
                              .getAccompagnatore()
                              .getCodiceFiscaleAccompagnatore()
                              .equalsIgnoreCase(""));
                  wmkResidenza.setVisible(false);
                } else {
                  lm.add("Codice fiscale non valido.");
                  messagePanelAccompagnatore.setVisible(true);
                }
                //					} else {
                //
                //						lm.add("Il numero della carta di identit&agrave; non &eacute; corretto.");
                //						messagePanelAccompagnatore.setVisible(true);
                //
                //					}
              } else if (codiceFiscalePerRicercaAccompagnatore.getModelObject() == null
                  || codiceFiscalePerRicercaAccompagnatore.getModelObject().equalsIgnoreCase("")) {
                lm.add("Inserire il codice fiscale.");
                messagePanelAccompagnatore.setVisible(true);
              }
              //					else if(cartaIdentitaPerRicercaAccompagnatore.getModelObject() == null ||
              // cartaIdentitaPerRicercaAccompagnatore.getModelObject().equalsIgnoreCase("")) {
              //						lm.add("Inserire la carta di identit&agrave;.");
              //						messagePanelAccompagnatore.setVisible(true);
              //					}
            } catch (BusinessException | ApiException e) {
              lm.add(
                  "Il codice fiscale deve appartenere ad una persona residente nel comune di Genova.");
              messagePanelAccompagnatore.setVisible(true);
              log.error("Errore errore nella ricerca per codice fiscale " + e.getMessage());
            }

            target.add(AccompagnatorePanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            //				target.add(RichiestaPermessiPersonalizzatiPage.this);

            // log.error("CP errore step 2 cambio indirizzo: " + getFeedbackMessages());
          }
        };

    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AccompagnatorePanel.cercaPerCodiceFiscale", AccompagnatorePanel.this)));

    return avanti;
  }

  //	private DropDownChoice<ComponenteNucleo> creaComboComponentiNucleoFamigliare() {
  //
  //
  //		DropDownChoice<ComponenteNucleo> dropDownChoice = new
  // DropDownChoice<>("comboComponentiNucleoFamigliare");
  //		dropDownChoice.setChoiceRenderer(new ComponenteNucleoFamigliareRenderer());
  //		dropDownChoice.setChoices(getUtente().getNucleoFamiliareAllargato());
  //
  ////		DropDownChoice<ComponenteNucleo> dropDownChoice = new DropDownChoice<ComponenteNucleo>(
  ////				"comboComponentiNucleoFamigliare",
  ////
  //	Model.of(richiestaPermessoPersonalizzato.getSoggettiCoinvolti().getAccompagnatore().getComponenteNucleoFamigliare()),
  ////				new ComponenteNucleoFamigliareRenderer()
  ////				);
  //		dropDownChoice.setNullValid(true);
  //		dropDownChoice.setOutputMarkupId(true);
  //		dropDownChoice.add(new AjaxFormComponentUpdatingBehavior("change") {
  //
  //			private static final long serialVersionUID = 4567199981239849845L;
  //
  //			@Override
  //			protected void onUpdate(AjaxRequestTarget target) {
  ////				log.info("comboDisoccupato1=" + dropDownChoice.getModelObject());
  ////				if (dropDownChoice.getModelObject() != null) {
  ////					// dataDisoccupazione1.setEnabled(true);
  ////					dataDisoccupazione1.setRequired(true);
  ////					comboDisoccupato2.setEnabled(true);
  ////				} else {
  ////					// dataDisoccupazione1.setEnabled(false);
  ////					dataDisoccupazione1.setRequired(false);
  ////					comboDisoccupato2.setEnabled(false);
  ////					// dataDisoccupazione2.setEnabled(false);
  ////					dataDisoccupazione2.setRequired(false);
  ////				}
  ////
  ////				List<ComponenteNucleo> listaAdulti = getListaPortatoriDiReddito(getModelObject());
  ////				listaAdulti.remove(dropDownChoice.getModelObject());
  ////				comboDisoccupato2.setChoices(listaAdulti);
  ////
  ////				target.add(/* dataDisoccupazione1, dataDisoccupazione2, */comboDisoccupato2);
  //			}
  //		});
  //		//add(dropDownChoice);
  //		return dropDownChoice;
  //	}

  protected FeedbackPanel createFeedBackStep4Panel() {

    NotificationPanel feedback =
        new NotificationPanel("feedback4") {

          private static final long serialVersionUID = -8510097378330901001L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    feedback.setFilter(new ContainerFeedbackMessageFilter(AccompagnatorePanel.this));
    feedback.setOutputMarkupId(true);
    this.addOrReplace(feedback);
    return feedback;
  }
}
