package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.soggetticoinvolti;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.Disabile;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.EnumTipoDomandaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.combo.ComboNucleoFamigliare;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.combo.ComboPermessoDisabile;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.combo.ComboTipoPatente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.panel.MessagePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.validator.ValidatorUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CAPValidator;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.TelefonoFissoCellulareValidator;
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

public class DisabilePanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  WebMarkupContainer wmkDatiDisabile;

  TextField<String> codiceFiscaleDisabile;
  TextField<String> cognomeDisabile;
  TextField<String> nomeDisabile;
  FdCLocalDateTextfield dataNascitaDisabile;
  TextField<String> luogoNascitaDisabile;
  TextField<String> provinciaNascitaDisabile;

  // Residenza
  WebMarkupContainer wmkResidenza;
  TextField<String> indirizzoResidenzaDisabile;
  TextField<String> capResidenzaDisabile;
  TextField<String> cittaResidenzaDisabile;
  TextField<String> provinciaResidenzaDisabile;

  WebMarkupContainer wmkContattiPatenteDisabile;
  // -- contatti
  TextField<String> telefonoDisabile;
  TextField<String> cellulareDisabile;
  EmailTextField emailDisabile;
  EmailTextField pecDisabile;
  // -- patente
  WebMarkupContainer wmkPantenteDisabile;
  ComboTipoPatente tipoPatenteDisabile;

  TextField<String> numeroPantenteDisabile;
  FdCLocalDateTextfield dataRilascioPatenteDisabile;
  FdCLocalDateTextfield dataScadenzaPatenteDisabile;

  WebMarkupContainer wmkDatiPermessoDisabile;
  TextField<String> numeroContrassegnoH;
  FdCLocalDateTextfield dataRilascioContrassegnoH;
  FdCLocalDateTextfield dataScadenzaContrassegnoH;

  WebMarkupContainer wmkComboPermessoDisabile;
  WebMarkupContainer wmkCudeManuale;
  ComboPermessoDisabile comboPermessoDisabile;

  private CheckBox faParteDelNucleoFamigliareDisabile;
  private DropDownChoice<ComponenteNucleo> comboComponentiNucleoFamigliare;
  private WebMarkupContainer wmkFaParteDelNucleoFamigliareDisabile;
  private boolean richiestaFattaDalTutore;

  private WebMarkupContainer wmkRicercaDisabilePerCodiceFiscalePatente;
  private TextField<String> codiceFiscalePerRicercaDisabile;
  // private TextField<String> cartaIdentitaPerRicercaDisabile;

  CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel;

  private ArrayList<String> listaMessaggiDisabile;
  private MessagePanel messagePanelDisabile;

  private boolean attivo;

  public DisabilePanel(
      String id,
      CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel,
      boolean attivo) {
    super(id);
    this.attivo = attivo;
    this.richiestaPermessoPersonalizzatoModel = richiestaPermessoPersonalizzatoModel;

    listaMessaggiDisabile = new ArrayList<String>();
    messagePanelDisabile = new MessagePanel("messagePanelDisabile", listaMessaggiDisabile);
    addOrReplace(messagePanelDisabile);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {

    createFeedBackStep3Panel();

    boolean isResidente = getUtente().isResidente();

    richiestaFattaDalTutore =
        richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda() != null
            && (richiestaPermessoPersonalizzatoModel
                    .getObject()
                    .getTipoDomanda()
                    .getCodice()
                    .equalsIgnoreCase(
                        EnumTipoDomandaPermessoPersonalizzato.DISABILE_MINORE.toString())
                || richiestaPermessoPersonalizzatoModel
                    .getObject()
                    .getTipoDomanda()
                    .getCodice()
                    .equalsIgnoreCase(
                        EnumTipoDomandaPermessoPersonalizzato.DISABILE_TUTORE.toString()));

    if (richiestaFattaDalTutore)
      richiestaPermessoPersonalizzatoModel
          .getObject()
          .getSoggettiCoinvolti()
          .getDisabile()
          .setResidenteInGenova(true);
    else
      richiestaPermessoPersonalizzatoModel
          .getObject()
          .getSoggettiCoinvolti()
          .getDisabile()
          .setResidenteInGenova(isResidente);

    addOrReplace(
        wmkFaParteDelNucleoFamigliareDisabile =
            new WebMarkupContainer("wmkFaParteDelNucleoFamigliareDisabile"));

    wmkFaParteDelNucleoFamigliareDisabile.addOrReplace(
        faParteDelNucleoFamigliareDisabile = creaToggleFaParteDelNucleoFamigliareDisabile());

    comboComponentiNucleoFamigliare =
        new ComboNucleoFamigliare(
            "comboComponentiNucleoFamigliareDisabile",
            getUtente(),
            richiestaPermessoPersonalizzatoModel.bind(
                "soggettiCoinvolti.disabile.componenteNucleoFamigliare"));
    comboComponentiNucleoFamigliare.setOutputMarkupId(true);
    comboComponentiNucleoFamigliare.setOutputMarkupPlaceholderTag(true);
    comboComponentiNucleoFamigliare.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 4567199981239849845L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            ComponenteNucleo componenteNucleo = comboComponentiNucleoFamigliare.getModelObject();

            Disabile disabile =
                richiestaPermessoPersonalizzatoModel
                    .getObject()
                    .getSoggettiCoinvolti()
                    .getDisabile();

            disabile.setCapResidenzaDisabile(
                componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvPostCode());
            disabile.setCittaResidenzaDisabile(
                componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvCity());
            disabile.setCodiceFiscaleDisabile(componenteNucleo.getDatiCittadino().getCpvTaxCode());
            disabile.setCodiceIndividuoDisabile(
                componenteNucleo.getDatiCittadino().getCpvPersonID());

            disabile.setCognomeDisabile(componenteNucleo.getDatiCittadino().getCpvFamilyName());
            disabile.setDataNascitaDisabile(componenteNucleo.getDataNascita());

            try {
              disabile.setCodCivicoResidenzaDisabile(
                  Integer.parseInt(
                      componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvStreetNumber()));
            } catch (Exception e) {
              log.error("Errore impostazione CodCivicoResidenzaDisabile");
            }
            try {
              disabile.setCodInternoResidenzaDisabile(
                  Integer.parseInt(
                      componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvFlatNumber()));
            } catch (Exception e) {
              log.error("Errore impostazione CodInternoResidenzaDisabile");
            }
            disabile.setLuogoResidenzaDisabile(
                componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvCity());

            disabile.setIndirizzoResidenzaDisabile(
                componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvFullAddress());
            disabile.setLuogoNascitaDisabile(
                componenteNucleo.getDatiCittadino().getCpvHasBirthPlace().getClvCity());
            disabile.setNomeDisabile(componenteNucleo.getDatiCittadino().getCpvGivenName());
            disabile.setProvinciaNascitaDisabile(
                componenteNucleo.getDatiCittadino().getCpvHasBirthPlace().getClvProvince());
            disabile.setProvinciaResidenzaDisabile("GE");
            richiestaPermessoPersonalizzatoModel
                .getObject()
                .getSoggettiCoinvolti()
                .setDisabile(disabile);

            FeaturesGeoserver featuresGeoserver = new FeaturesGeoserver();
            featuresGeoserver.setMACHINE_LAST_UPD(
                componenteNucleo.getDatiCittadino().getCpvHasAddress().getClvFullAddress());

            try {
              featuresGeoserver.setCODICE_INDIRIZZO(
                  Integer.parseInt(
                      componenteNucleo
                          .getDatiCittadino()
                          .getCpvHasAddress()
                          .getGenovaOntoStreetNumberCode()));

            } catch (Exception e) {
              log.error(
                  "Richiesta PermessoPersonalizzatoConverter:errore nel parsing del codice indirizzo");
            }

            getSession().removeAttribute("listaPermessiDisabili");
            comboPermessoDisabile.inizializza(codiceFiscaleDisabile.getModelObject());

            richiestaPermessoPersonalizzatoModel
                .getObject()
                .setGeoserverUbicazioneParcheggio(featuresGeoserver);

            wmkDatiDisabile.setVisible(true);
            wmkResidenza.setVisible(true);
            target.add(DisabilePanel.this);
          }
        });

    comboComponentiNucleoFamigliare.setVisible(
        faParteDelNucleoFamigliareDisabile.isVisible()
            && richiestaPermessoPersonalizzatoModel
                .getObject()
                .getSoggettiCoinvolti()
                .getDisabile()
                .isFaParteDelNucleoFamigliare()
            && attivo);

    addOrReplace(comboComponentiNucleoFamigliare);

    wmkFaParteDelNucleoFamigliareDisabile.setVisible(
        !comboComponentiNucleoFamigliare.getChoices().isEmpty()
            && richiestaFattaDalTutore
            && attivo);

    addOrReplace(
        wmkRicercaDisabilePerCodiceFiscalePatente =
            new WebMarkupContainer("wmkRicercaDisabilePerCodiceFiscalePatente"));

    wmkRicercaDisabilePerCodiceFiscalePatente.setOutputMarkupId(true);
    wmkRicercaDisabilePerCodiceFiscalePatente.setOutputMarkupPlaceholderTag(true);

    wmkRicercaDisabilePerCodiceFiscalePatente.addOrReplace(
        codiceFiscalePerRicercaDisabile =
            new TextField<String>(
                "codiceFiscalePerRicercaDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.codiceFiscalePerRicercaDisabile")));

    wmkRicercaDisabilePerCodiceFiscalePatente.addOrReplace(creaBottoneCercaPerCodiceFiscale());

    wmkRicercaDisabilePerCodiceFiscalePatente.setVisible(
        wmkRicercaDisabilePerCodiceFiscaleVisibile(isResidente));

    addOrReplace(wmkDatiDisabile = new WebMarkupContainer("wmkDatiDisabile"));
    wmkDatiDisabile.setOutputMarkupId(true);
    wmkDatiDisabile.setOutputMarkupPlaceholderTag(true);

    wmkDatiDisabile.addOrReplace(
        codiceFiscaleDisabile =
            new TextField<String>(
                "codiceFiscaleDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.codiceFiscaleDisabile")));

    codiceFiscaleDisabile.setEnabled(false);

    wmkDatiDisabile.addOrReplace(
        cognomeDisabile =
            new TextField<String>(
                "cognomeDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.cognomeDisabile")));
    cognomeDisabile.setEnabled(false);

    wmkDatiDisabile.addOrReplace(
        nomeDisabile =
            new TextField<String>(
                "nomeDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.nomeDisabile")));
    nomeDisabile.setEnabled(false);

    wmkDatiDisabile.addOrReplace(
        dataNascitaDisabile =
            new FdCLocalDateTextfield(
                "dataNascitaDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.dataNascitaDisabile")));
    dataNascitaDisabile.setEnabled(false);

    wmkDatiDisabile.addOrReplace(
        luogoNascitaDisabile =
            new TextField<String>(
                "luogoNascitaDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.luogoNascitaDisabile")));
    luogoNascitaDisabile.setEnabled(
        !isResidente
            && richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda() != null
            && !(EnumTipoDomandaPermessoPersonalizzato.getById(
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
                    .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_TUTORE)));

    wmkDatiDisabile.addOrReplace(
        provinciaNascitaDisabile =
            new TextField<String>(
                "provinciaNascitaDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.provinciaNascitaDisabile")));
    provinciaNascitaDisabile.setEnabled(
        !isResidente
            && richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda() != null
            && !(EnumTipoDomandaPermessoPersonalizzato.getById(
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
                    .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_TUTORE)));

    // -- residenza
    wmkDatiDisabile.addOrReplace(wmkResidenza = new WebMarkupContainer("wmkResidenza"));

    wmkResidenza.addOrReplace(
        indirizzoResidenzaDisabile =
            new TextField<String>(
                "indirizzoResidenzaDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.indirizzoResidenzaDisabile")));
    indirizzoResidenzaDisabile.setEnabled(!isResidente);

    wmkResidenza.addOrReplace(
        capResidenzaDisabile =
            new TextField<String>(
                "capResidenzaDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.capResidenzaDisabile")));
    capResidenzaDisabile.add(new CAPValidator());

    capResidenzaDisabile.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DisabilePanel.capResidenzaDisabile", DisabilePanel.this)));

    capResidenzaDisabile.setEnabled(!isResidente);

    wmkResidenza.addOrReplace(
        cittaResidenzaDisabile =
            new TextField<String>(
                "cittaResidenzaDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.cittaResidenzaDisabile")));
    cittaResidenzaDisabile.setEnabled(!isResidente);

    wmkResidenza.addOrReplace(
        provinciaResidenzaDisabile =
            new TextField<String>(
                "provinciaResidenzaDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.provinciaResidenzaDisabile")));
    provinciaResidenzaDisabile.setEnabled(!isResidente);

    wmkContattiPatenteDisabile = new WebMarkupContainer("wmkContattiPatenteDisabile");

    // -- contatti
    wmkContattiPatenteDisabile.addOrReplace(
        telefonoDisabile =
            new TextField<String>(
                "telefonoDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.telefonoDisabile")));
    telefonoDisabile.setEnabled(true);
    telefonoDisabile.add(new TelefonoFissoCellulareValidator());
    wmkContattiPatenteDisabile.addOrReplace(
        cellulareDisabile =
            new TextField<String>(
                "cellulareDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.cellulareDisabile")));
    cellulareDisabile.setEnabled(true);
    cellulareDisabile.add(new TelefonoFissoCellulareValidator());

    wmkContattiPatenteDisabile.addOrReplace(
        emailDisabile =
            new EmailTextField(
                "emailDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.emailDisabile")));

    emailDisabile.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DisabilePanel.emailDisabile", DisabilePanel.this)));

    emailDisabile.setEnabled(true);
    wmkContattiPatenteDisabile.addOrReplace(
        pecDisabile =
            new EmailTextField(
                "pecDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.pecDisabile")));
    pecDisabile.setEnabled(true);
    pecDisabile.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DisabilePanel.pecDisabile", DisabilePanel.this)));
    // -- pantente
    wmkPantenteDisabile = new WebMarkupContainer("wmkPantenteDisabile");

    tipoPatenteDisabile =
        new ComboTipoPatente(
            "tipoPatenteDisabile",
            richiestaPermessoPersonalizzatoModel.bind(
                "soggettiCoinvolti.disabile.tipoPatenteDisabile"));
    wmkPantenteDisabile.addOrReplace(tipoPatenteDisabile);

    wmkPantenteDisabile.addOrReplace(
        numeroPantenteDisabile =
            new TextField<String>(
                "numeroPantenteDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.numeroPantenteDisabile")));
    wmkPantenteDisabile.addOrReplace(
        dataRilascioPatenteDisabile =
            new FdCLocalDateTextfield(
                "dataRilascioPatenteDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.dataRilascioPatenteDisabile")));

    dataRilascioPatenteDisabile.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DisabilePanel.dataRilascioPatenteDisabile", DisabilePanel.this)));

    wmkPantenteDisabile.addOrReplace(
        dataScadenzaPatenteDisabile =
            new FdCLocalDateTextfield(
                "dataScadenzaPatenteDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.dataScadenzaPatenteDisabile")));

    dataScadenzaPatenteDisabile.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DisabilePanel.dataScadenzaPatenteDisabile", DisabilePanel.this)));

    if (richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda() != null)
      wmkPantenteDisabile.setVisible(
          EnumTipoDomandaPermessoPersonalizzato.getById(
                      richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda().getCodice())
                  .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_GUIDATORE_RESIDENZA)
              || EnumTipoDomandaPermessoPersonalizzato.getById(
                      richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda().getCodice())
                  .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_GUIDATORE_LAVORO));

    wmkContattiPatenteDisabile.add(wmkPantenteDisabile);
    wmkDatiDisabile.addOrReplace(wmkContattiPatenteDisabile);

    wmkDatiDisabile.setVisible(visibilitaWmkDatiDisabile());

    wmkDatiDisabile.addOrReplace(
        wmkComboPermessoDisabile = new WebMarkupContainer("wmkComboPermessoDisabile"));

    String codiceFiscale = null;
    if (richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda() != null
        && !(EnumTipoDomandaPermessoPersonalizzato.getById(
                    richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda().getCodice())
                .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_MINORE)
            || EnumTipoDomandaPermessoPersonalizzato.getById(
                    richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda().getCodice())
                .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_TUTORE))) {
      codiceFiscale = getUtente().getCodiceFiscaleOperatore();
    }

    wmkComboPermessoDisabile.addOrReplace(
        comboPermessoDisabile =
            new ComboPermessoDisabile(
                "comboPermessoDisabile",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.datiPermessoDisabile"),
                codiceFiscale));

    wmkComboPermessoDisabile.setVisible(
        richiestaPermessoPersonalizzatoModel
            .getObject()
            .getSoggettiCoinvolti()
            .getDisabile()
            .isResidenteInGenova());

    wmkDatiDisabile.addOrReplace(wmkCudeManuale = new WebMarkupContainer("wmkCudeManuale"));

    wmkCudeManuale.addOrReplace(
        numeroContrassegnoH =
            new TextField<String>(
                "numeroContrassegnoH",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.numeroContrassegnoH")));

    numeroContrassegnoH.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DisabilePanel.numeroContrassegnoH", DisabilePanel.this)));

    // numeroContrassegnoH.setLabel(Model.of("Classe"));
    numeroContrassegnoH.setEnabled(true);

    wmkCudeManuale.addOrReplace(
        dataRilascioContrassegnoH =
            new FdCLocalDateTextfield(
                "dataRilascioContrassegnoH",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.dataRilascioContrassegnoH")));

    dataRilascioContrassegnoH.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DisabilePanel.dataRilascioContrassegnoH", DisabilePanel.this)));

    dataRilascioContrassegnoH.setEnabled(true);

    wmkCudeManuale.addOrReplace(
        dataScadenzaContrassegnoH =
            new FdCLocalDateTextfield(
                "dataScadenzaContrassegnoH",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.disabile.dataScadenzaContrassegnoH")));

    dataScadenzaContrassegnoH.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DisabilePanel.dataScadenzaContrassegnoH", DisabilePanel.this)));

    dataScadenzaContrassegnoH.setEnabled(true);

    wmkCudeManuale.setVisible(
        !richiestaPermessoPersonalizzatoModel
            .getObject()
            .getSoggettiCoinvolti()
            .getDisabile()
            .isResidenteInGenova());
  }

  private boolean wmkRicercaDisabilePerCodiceFiscaleVisibile(boolean isResidente) {
    boolean visibile = false;
    if (richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda() != null) {
      visibile =
          ((EnumTipoDomandaPermessoPersonalizzato.getById(
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
                          .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_TUTORE)))
                  && !faParteDelNucleoFamigliareDisabile.getModelObject()
                  && isResidente
              || (!isResidente
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
                          .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_TUTORE)));
    }
    return visibile;
  }

  private boolean visibilitaWmkDatiDisabile() {
    return (richiestaPermessoPersonalizzatoModel
                    .getObject()
                    .getSoggettiCoinvolti()
                    .getDisabile()
                    .getCodiceFiscaleDisabile()
                != null
            && !richiestaPermessoPersonalizzatoModel
                .getObject()
                .getSoggettiCoinvolti()
                .getDisabile()
                .getCodiceFiscaleDisabile()
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
                        .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_TUTORE))));
  }

  private CheckBox creaToggleFaParteDelNucleoFamigliareDisabile() {
    faParteDelNucleoFamigliareDisabile =
        new CheckBox(
            "faParteDelNucleoFamigliareDisabile",
            richiestaPermessoPersonalizzatoModel.bind(
                "soggettiCoinvolti.disabile.faParteDelNucleoFamigliare"));
    faParteDelNucleoFamigliareDisabile.setRequired(true);
    faParteDelNucleoFamigliareDisabile.setOutputMarkupId(true);
    faParteDelNucleoFamigliareDisabile.setOutputMarkupPlaceholderTag(true);
    faParteDelNucleoFamigliareDisabile.setVisible(richiestaFattaDalTutore);

    faParteDelNucleoFamigliareDisabile.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = 1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            comboComponentiNucleoFamigliare.setVisible(
                faParteDelNucleoFamigliareDisabile.getModelObject());
            wmkRicercaDisabilePerCodiceFiscalePatente.setVisible(
                !faParteDelNucleoFamigliareDisabile.getModelObject());
            wmkDatiDisabile.setVisible(false);

            comboComponentiNucleoFamigliare.setModelObject(null);
            target.add(DisabilePanel.this);
          }
        });

    return faParteDelNucleoFamigliareDisabile;
  }

  private LaddaAjaxButton creaBottoneCercaPerCodiceFiscale() {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("cercaPerCodiceFiscale", Type.Primary) {

          private static final long serialVersionUID = 6018267445510360466L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            List<String> lm = messagePanelDisabile.getListaMessaggi();
            lm.clear();
            try {

              if (codiceFiscalePerRicercaDisabile.getModelObject() != null
                  && !codiceFiscalePerRicercaDisabile.getModelObject().equalsIgnoreCase("")) {

                if (ValidatorUtil.codiceFiscaleValido(
                    codiceFiscalePerRicercaDisabile.getModelObject())) {

                  Residente datiResidenteCoabitante =
                      ServiceLocator.getInstance()
                          .getServizioDemografico()
                          .getDatiResidente(codiceFiscalePerRicercaDisabile.getModelObject());

                  Disabile disabile =
                      richiestaPermessoPersonalizzatoModel
                          .getObject()
                          .getSoggettiCoinvolti()
                          .getDisabile();
                  disabile.setCapResidenzaDisabile(
                      datiResidenteCoabitante.getCpvHasAddress().getClvPostCode());

                  disabile.setCittaResidenzaDisabile(
                      datiResidenteCoabitante.getCpvHasAddress().getClvCity());
                  disabile.setCodiceFiscaleDisabile(datiResidenteCoabitante.getCpvTaxCode());
                  disabile.setCodiceIndividuoDisabile(datiResidenteCoabitante.getCpvPersonID());
                  disabile.setCognomeDisabile(datiResidenteCoabitante.getCpvFamilyName());
                  disabile.setDataNascitaDisabile(datiResidenteCoabitante.getCpvDateOfBirth());

                  try {
                    disabile.setCodCivicoResidenzaDisabile(
                        Integer.parseInt(
                            datiResidenteCoabitante.getCpvHasAddress().getClvStreetNumber()));
                  } catch (Exception e) {
                    log.error(
                        "Richiesta PermessoPersonalizzatoConverter:errore nel parsing del codice civico residenza disabile");
                  }

                  try {
                    disabile.setCodInternoResidenzaDisabile(
                        Integer.parseInt(
                            datiResidenteCoabitante.getCpvHasAddress().getClvFlatNumber()));

                  } catch (Exception e) {
                    log.error(
                        "Richiesta PermessoPersonalizzatoConverter:errore nel parsing del codice interno residenza disabile");
                  }

                  disabile.setLuogoResidenzaDisabile(
                      datiResidenteCoabitante.getCpvHasAddress().getClvCity());

                  disabile.setIndirizzoResidenzaDisabile(
                      datiResidenteCoabitante.getCpvHasAddress().getClvFullAddress());
                  disabile.setLuogoNascitaDisabile(
                      datiResidenteCoabitante.getCpvHasBirthPlace().getClvCity());
                  disabile.setNomeDisabile(datiResidenteCoabitante.getCpvGivenName());
                  // disabile.setNumeroPantenteDisabile(null);
                  // disabile.setPecDisabile(null);
                  disabile.setProvinciaNascitaDisabile(
                      datiResidenteCoabitante.getCpvHasBirthPlace().getClvProvince());
                  disabile.setProvinciaResidenzaDisabile("GE");
                  // disabile.setTelefonoDisabile(null);
                  // disabile.setTipoPatenteDisabile(null);
                  richiestaPermessoPersonalizzatoModel
                      .getObject()
                      .getSoggettiCoinvolti()
                      .setDisabile(disabile);

                  FeaturesGeoserver featuresGeoserver = new FeaturesGeoserver();
                  featuresGeoserver.setMACHINE_LAST_UPD(
                      datiResidenteCoabitante.getCpvHasAddress().getClvFullAddress());

                  try {
                    featuresGeoserver.setCODICE_INDIRIZZO(
                        Integer.parseInt(
                            datiResidenteCoabitante
                                .getCpvHasAddress()
                                .getGenovaOntoStreetNumberCode()));

                  } catch (Exception e) {
                    log.error(
                        "Richiesta PermessoPersonalizzatoConverter:errore nel parsing del codice indirizzo");
                  }

                  richiestaPermessoPersonalizzatoModel
                      .getObject()
                      .setGeoserverUbicazioneParcheggio(featuresGeoserver);

                  wmkDatiDisabile.setVisible(
                      richiestaPermessoPersonalizzatoModel
                                  .getObject()
                                  .getSoggettiCoinvolti()
                                  .getDisabile()
                                  .getCodiceFiscaleDisabile()
                              != null
                          && !richiestaPermessoPersonalizzatoModel
                              .getObject()
                              .getSoggettiCoinvolti()
                              .getDisabile()
                              .getCodiceFiscaleDisabile()
                              .equalsIgnoreCase(""));
                  wmkResidenza.setVisible(false);
                } else {
                  lm.add("Codice fiscale non valido.");
                  messagePanelDisabile.setVisible(true);
                }
              } else if (codiceFiscalePerRicercaDisabile.getModelObject() == null
                  || codiceFiscalePerRicercaDisabile.getModelObject().equalsIgnoreCase("")) {
                lm.add("Inserire il codice fiscale.");
                messagePanelDisabile.setVisible(true);
              }

            } catch (BusinessException | ApiException e) {
              lm.add(
                  "Il codice fiscale deve appartenere ad una persona residente nel comune di Genova.");
              messagePanelDisabile.setVisible(true);
              log.error("Errore errore nella ricerca per codice fiscale " + e.getMessage());
            }

            getSession().removeAttribute("listaPermessiDisabili");
            comboPermessoDisabile.inizializza(codiceFiscaleDisabile.getModelObject());

            target.add(DisabilePanel.this);
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
                .getString("DisabilePanel.cercaPerCodiceFiscale", DisabilePanel.this)));

    return avanti;
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    wmkDatiDisabile.setVisible(visibilitaWmkDatiDisabile());
  }

  protected FeedbackPanel createFeedBackStep3Panel() {

    NotificationPanel feedback =
        new NotificationPanel("feedback3") {

          private static final long serialVersionUID = -8510097378330901001L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };

    feedback.setFilter(new ContainerFeedbackMessageFilter(DisabilePanel.this));
    feedback.setOutputMarkupId(true);
    this.addOrReplace(feedback);
    return feedback;
  }

  public ComboPermessoDisabile getComboPermessoDisabile() {
    return comboPermessoDisabile;
  }
}
