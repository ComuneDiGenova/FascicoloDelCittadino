package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.form;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.configurazione.service.ConfigurazioneInterface;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.biblioteche.BibliotecheIscrizione;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.AJAXDownload;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.autocomplete.CittadinanzaBibliotecheAutocomplete;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.panel.BibliotecheIscrizionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.TelefonoFissoCellulareValidator;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaPaeseRecord;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.validation.validator.StringValidator;

public class BibliotecheIscrizioneForm extends AbstracFrameworkForm<BibliotecheIscrizione> {

  private static final long serialVersionUID = -35205887386699392L;

  private ComponenteNucleo componenteNucleo;

  private Utente utente;

  private String tipoUtente;

  private String provinciaResidenza;

  private String statoResidenza;

  private String email;

  private WebMarkupContainer containerPrivacy;

  private WebMarkupContainer containerMail;

  private String cellulare;

  private WebMarkupContainer containerDocumentoGenitore;

  private WebMarkupContainer datiDocumentoIdentitaContainer;

  private CompoundPropertyModel<BibliotecheIscrizione> compoundPropertyModel;

  private BibliotecheIscrizionePanel bibliotecheIscrizionePanel;

  public BibliotecheIscrizioneForm(
      String id, BibliotecheIscrizione model, ComponenteNucleo componenteNucleo, Utente utente) {
    super(id, model);

    this.setUtente(utente);
    this.setComponenteNucleo(componenteNucleo);

    addElementiForm(componenteNucleo, utente);
  }

  public BibliotecheIscrizioneForm(
      String id,
      BibliotecheIscrizione model,
      CompoundPropertyModel<BibliotecheIscrizione> compoundPropertyModel,
      ComponenteNucleo componenteNucleo,
      Utente utente,
      BibliotecheIscrizionePanel bibliotecheIscrizionePanel) {
    super(id, model);
    this.bibliotecheIscrizionePanel = bibliotecheIscrizionePanel;
    this.setUtente(utente);
    this.setComponenteNucleo(componenteNucleo);

    this.compoundPropertyModel = compoundPropertyModel;

    addElementiForm(componenteNucleo, utente);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public void addElementiForm(ComponenteNucleo componenteNucleo, Utente utente) {

    containerDocumentoGenitore = new WebMarkupContainer("containerDocumentoGenitore");
    containerDocumentoGenitore.setVisible(
        LabelFdCUtil.checkIfNotNull(componenteNucleo)
            && PageUtil.isStringValid(componenteNucleo.getCodiceFiscale())
            && !componenteNucleo
                .getCodiceFiscale()
                .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore()));
    containerDocumentoGenitore.setOutputMarkupId(true);
    addOrReplace(containerDocumentoGenitore);

    Residente datiUtente = componenteNucleo.getDatiCittadino();

    datiDocumentoIdentitaContainer = new WebMarkupContainer("datiDocumentoIdentitaContainer");
    datiDocumentoIdentitaContainer.setVisible(
        LabelFdCUtil.checkIfNotNull(datiUtente.getGenovaOntoIDCardIssueDate())
            && !datiUtente.getGenovaOntoIDCardIssueDate().isEqual(LocalDate.of(1800, 1, 1)));
    addOrReplace(datiDocumentoIdentitaContainer);

    NotEmptyLabel nome = new NotEmptyLabel("nome", datiUtente.getCpvGivenName());
    addOrReplace(nome);

    NotEmptyLabel cognome = new NotEmptyLabel("cognome", datiUtente.getCpvFamilyName());
    addOrReplace(cognome);

    NotEmptyLabel sesso = new NotEmptyLabel("sesso", datiUtente.getCpvHasSex());
    addOrReplace(sesso);

    NotEmptyLabel dataNascita =
        new NotEmptyLabel(
            "dataNascita", LocalDateUtil.getDataFormatoEuropeo(datiUtente.getCpvDateOfBirth()));
    addOrReplace(dataNascita);

    NotEmptyLabel luogoNascita =
        new NotEmptyLabel("luogoNascita", datiUtente.getCpvHasBirthPlace().getClvCity());
    addOrReplace(luogoNascita);

    NotEmptyLabel indirizzoResidenza =
        new NotEmptyLabel("indirizzoResidenza", datiUtente.getCpvHasAddress().getClvFullAddress());
    addOrReplace(indirizzoResidenza);

    NotEmptyLabel capResidenza =
        new NotEmptyLabel("capResidenza", datiUtente.getCpvHasAddress().getClvPostCode());
    addOrReplace(capResidenza);

    NotEmptyLabel cittaResidenza =
        new NotEmptyLabel("cittaResidenza", datiUtente.getCpvHasAddress().getClvCity());
    addOrReplace(cittaResidenza);

    String codiceProvinciaGenova = "GE";
    Label provinciaResidenza = new Label("provinciaResidenza", codiceProvinciaGenova);
    setProvinciaResidenza(codiceProvinciaGenova);
    addOrReplace(provinciaResidenza);

    String codiceStatoItalia = "IT";
    NotEmptyLabel statoResidenza = new NotEmptyLabel("statoResidenza", codiceStatoItalia);
    setStatoResidenza(codiceStatoItalia);
    addOrReplace(statoResidenza);

    NotEmptyLabel numeroCI = new NotEmptyLabel("numeroCI", datiUtente.getGenovaOntoIDCardNumber());
    datiDocumentoIdentitaContainer.addOrReplace(numeroCI);

    NotEmptyLabel ciRilasciataDa =
        new NotEmptyLabel("ciRilasciataDa", datiUtente.getGenovaOntoIDCardIssuingMunicipality());
    ciRilasciataDa.setVisible(
        LabelFdCUtil.checkIfNotNull(datiUtente.getGenovaOntoIDCardIssuingMunicipality())
            && !datiUtente.getGenovaOntoIDCardIssuingMunicipality().equalsIgnoreCase("-"));
    datiDocumentoIdentitaContainer.addOrReplace(ciRilasciataDa);

    LocalDateLabel ciValidaDal =
        new LocalDateLabel("ciValidaDal", datiUtente.getGenovaOntoIDCardIssueDate());
    ciValidaDal.setVisible(
        LabelFdCUtil.checkIfNotNull(datiUtente.getGenovaOntoIDCardIssueDate())
            && !datiUtente.getGenovaOntoIDCardIssueDate().isEqual(LocalDate.of(1800, 1, 1)));
    datiDocumentoIdentitaContainer.addOrReplace(ciValidaDal);

    LocalDateLabel ciValidaFinoAl =
        new LocalDateLabel("ciValidaFinoAl", datiUtente.getGenovaOntoIDCardValidUntilDate());
    ciValidaFinoAl.setVisible(
        LabelFdCUtil.checkIfNotNull(datiUtente.getGenovaOntoIDCardValidUntilDate()));
    datiDocumentoIdentitaContainer.addOrReplace(ciValidaFinoAl);

    Residente datiLoggato = getUtente().getDatiCittadinoResidente();

    NotEmptyLabel nomeGenitore = new NotEmptyLabel("nomeGenitore", datiLoggato.getCpvGivenName());
    containerDocumentoGenitore.addOrReplace(nomeGenitore);

    NotEmptyLabel cognomeGenitore =
        new NotEmptyLabel("cognomeGenitore", datiLoggato.getCpvFamilyName());
    containerDocumentoGenitore.addOrReplace(cognomeGenitore);

    NotEmptyLabel numeroCIGenitore =
        new NotEmptyLabel("numeroCIGenitore", datiLoggato.getGenovaOntoIDCardNumber());
    containerDocumentoGenitore.addOrReplace(numeroCIGenitore);

    NotEmptyLabel ciRilasciataDaGenitore =
        new NotEmptyLabel(
            "ciRilasciataDaGenitore", datiLoggato.getGenovaOntoIDCardIssuingMunicipality());
    ciRilasciataDaGenitore.setVisible(
        LabelFdCUtil.checkIfNotNull(datiLoggato.getGenovaOntoIDCardIssuingMunicipality())
            && datiLoggato.getGenovaOntoIDCardIssuingMunicipality().equalsIgnoreCase("-"));
    containerDocumentoGenitore.addOrReplace(ciRilasciataDaGenitore);

    LocalDateLabel ciValidaDalGenitore =
        new LocalDateLabel("ciValidaDalGenitore", datiLoggato.getGenovaOntoIDCardIssueDate());
    ciValidaDalGenitore.setVisible(
        LabelFdCUtil.checkIfNotNull(datiLoggato.getGenovaOntoIDCardIssueDate())
            && !datiLoggato.getGenovaOntoIDCardIssueDate().isEqual(LocalDate.of(1800, 1, 1)));
    containerDocumentoGenitore.addOrReplace(ciValidaDalGenitore);

    LocalDateLabel ciValidaFinoAlGenitore =
        new LocalDateLabel(
            "ciValidaFinoAlGenitore", datiLoggato.getGenovaOntoIDCardValidUntilDate());
    ciValidaFinoAlGenitore.setVisible(
        LabelFdCUtil.checkIfNotNull(datiLoggato.getGenovaOntoIDCardValidUntilDate()));
    containerDocumentoGenitore.addOrReplace(ciValidaFinoAlGenitore);

    if (utente.getMail() != null) {
      if (getModelObject().getEmail() != null
          && !utente.getMail().equalsIgnoreCase(getModelObject().getEmail())) {
        getModelObject().setEmail(getModelObject().getEmail());
      } else {
        getModelObject().setEmail(utente.getMail());
      }
    }
    EmailTextField email =
        new EmailTextField("email", new PropertyModel<String>(getModelObject(), "email"));
    email.setLabel(Model.of("Email"));
    email.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = 2768726675037761231L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setEmail(email.getValue());
            getModelObject().setEmail(email.getValue());
          }
        });
    getModelObject().setEmail(email.getValue());
    email.setRequired(true);
    addOrReplace(email);

    if (LabelFdCUtil.checkIfNotNull(utente.getMobile())) {
      if (LabelFdCUtil.checkIfNotNull(getModelObject().getCellulare())
          && !utente.getMobile().equalsIgnoreCase(getModelObject().getCellulare())) {
        getModelObject().setCellulare(getModelObject().getCellulare());
      } else {
        getModelObject().setCellulare(utente.getMobile());
      }
    }
    TextField cellulare =
        new TextField("cellulare", new PropertyModel(getModelObject(), "cellulare"));
    cellulare.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -6101754723605506289L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setCellulare(cellulare.getValue());
            getModelObject().setCellulare(cellulare.getValue());
          }
        });
    getModelObject().setCellulare(cellulare.getValue());
    cellulare.setRequired(true);
    cellulare.setLabel(Model.of("Cellulare"));
    cellulare.add(new TelefonoFissoCellulareValidator());
    cellulare.add(StringValidator.lengthBetween(9, 20));
    cellulare.add(StringValidator.minimumLength(9));
    cellulare.add(StringValidator.maximumLength(20));
    addOrReplace(cellulare);

    //		CountryComboSelect2 fdCSelect2 = new CountryComboSelect2("fdcSelect2",
    //				new PropertyModel(getModelObject(), "country"), this, bibliotecheIscrizionePanel);
    //		fdCSelect2.getCombo().setRequired(true);

    AbstractAutoCompleteTextRenderer<TabellaPaeseRecord> autocompleteRender =
        new AbstractAutoCompleteTextRenderer<TabellaPaeseRecord>() {

          @Override
          protected String getTextValue(TabellaPaeseRecord paese) {
            return paese.getDscr();
          }
        };

    AutoCompleteSettings settings = new AutoCompleteSettings();
    settings.setShowListOnEmptyInput(false);
    settings.setUseSmartPositioning(true);
    settings.setShowListOnFocusGain(true);
    settings.setMinInputLength(3);
    settings.setThrottleDelay(1000);

    CittadinanzaBibliotecheAutocomplete autocompleteCittadinanza =
        new CittadinanzaBibliotecheAutocomplete(
            "autocompleteCittadinanza",
            new PropertyModel<TabellaPaeseRecord>(getModelObject(), "autocompleteCittadinanza"),
            autocompleteRender,
            settings);
    autocompleteCittadinanza.setLabel(Model.of("Cittadinanza"));
    autocompleteCittadinanza.setRequired(true);

    List<TabellaPaeseRecord> listaNazionalita = new ArrayList<>();
    if (LabelFdCUtil.checkIfNotNull(componenteNucleo)
        && LabelFdCUtil.checkIfNotNull(componenteNucleo.getDatiCittadino())
        && LabelFdCUtil.checkIfNotNull(componenteNucleo.getDatiCittadino().getCpvHasCitizenship())
        && PageUtil.isStringValid(
            componenteNucleo.getDatiCittadino().getCpvHasCitizenship().getClvHasIdentifier())) {

      try {
        listaNazionalita =
            ServiceLocator.getInstance()
                .getServiziBiblioteche()
                .getPaeseNazionalita(
                    componenteNucleo
                        .getDatiCittadino()
                        .getCpvHasCitizenship()
                        .getClvHasIdentifier());

        if (listaNazionalita.size() == 1) {
          //	getModelObject().setCountry(listaNazionalita.get(0));
          getModelObject().setAutocompleteCittadinanza(listaNazionalita.get(0));

          autocompleteCittadinanza.setEnabled(false);
        }
      } catch (BusinessException | ApiException | IOException e) {
        log.error("Errore nazionalita paese biblioteche: " + e.getMessage(), e);
      }
    }

    //	fdCSelect2.getCombo().setChoices(listaNazionalita);

    //	addOrReplace(fdCSelect2);
    addOrReplace(autocompleteCittadinanza);

    addContainerPrivacy();
    addContainerMail();
    addDownloadPrivacy();
    addTogglePrivacy();
    addToggleMail();
  }

  @Override
  public void addElementiForm() {}

  public ComponenteNucleo getComponenteNucleo() {
    return componenteNucleo;
  }

  public void setComponenteNucleo(ComponenteNucleo componenteNucleo) {
    this.componenteNucleo = componenteNucleo;
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  public String getTipoUtente() {
    return tipoUtente;
  }

  public void setTipoUtente(String tipoUtente) {
    this.tipoUtente = tipoUtente;
  }

  public String getStatoResidenza() {
    return statoResidenza;
  }

  public void setStatoResidenza(String statoResidenza) {
    this.statoResidenza = statoResidenza;
  }

  public String getProvinciaResidenza() {
    return provinciaResidenza;
  }

  public void setProvinciaResidenza(String provinciaResidenza) {
    this.provinciaResidenza = provinciaResidenza;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCellulare() {
    return cellulare;
  }

  public void setCellulare(String cellulare) {
    this.cellulare = cellulare;
  }

  private void addContainerPrivacy() {

    log.debug("CP addContainerPrivacy");

    containerPrivacy = new WebMarkupContainer("containerPrivacy");
    containerPrivacy.setOutputMarkupId(true);
    boolean visibilitaContainerPrivacy = false;
    if (getModelObject().isAutorizzazioneTrattamentoDati()) {
      visibilitaContainerPrivacy = true;
    }
    containerPrivacy.setVisible(visibilitaContainerPrivacy);
    containerPrivacy.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerPrivacy);
  }

  private void addContainerMail() {

    log.debug("CP addTogglePrivacy");

    containerMail = new WebMarkupContainer("containerMail");
    containerMail.setOutputMarkupId(true);
    boolean visibilitaContainerMail = false;
    if (getModelObject().isAutorizzazioneTrattamentoDati()) {
      visibilitaContainerMail = true;
    }
    containerMail.setVisible(visibilitaContainerMail);
    containerMail.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerMail);
  }

  private void addTogglePrivacy() {

    log.debug("CP addTogglePrivacy");

    CheckBox autorizzazioneTrattamentoDati =
        new CheckBox(
            "autorizzazioneTrattamentoDati",
            new PropertyModel<Boolean>(getModelObject(), "autorizzazioneTrattamentoDati"));
    autorizzazioneTrattamentoDati.setRequired(true);
    autorizzazioneTrattamentoDati.setOutputMarkupId(true);
    autorizzazioneTrattamentoDati.setOutputMarkupPlaceholderTag(true);
    containerPrivacy.addOrReplace(autorizzazioneTrattamentoDati);
  }

  private void addToggleMail() {

    log.debug("CP addToggleMail");

    CheckBox autorizzazioneMail =
        new CheckBox(
            "autorizzazioneMail",
            new PropertyModel<Boolean>(getModelObject(), "autorizzazioneMail"));
    autorizzazioneMail.setRequired(false);
    autorizzazioneMail.setOutputMarkupId(true);
    autorizzazioneMail.setOutputMarkupPlaceholderTag(true);
    containerMail.addOrReplace(autorizzazioneMail);
  }

  private void addDownloadPrivacy() {

    log.debug("CP addDownloadPrivacy");

    final AJAXDownload download =
        new AJAXDownload() {

          private static final long serialVersionUID = -5868071643309482662L;

          @Override
          protected IResourceStream getResourceStream() {

            ConfigurazioneInterface stampa = null;
            byte[] pdf = null;

            try {
              stampa = ServiceLocator.getInstance().getServiziConfigurazione();
              pdf = stampa.getInformativaSebina(getUtente());
            } catch (final BusinessException e) {
              log.error(
                  "[BibliotecheIscrizioneForm] Errore durante scaricamento dell'informativa privacy Sebina: "
                      + e.getMessage(),
                  e);
              error("Errore durante scaricamento dell'informativa privacy Sebina");
            }
            return PageUtil.createResourceStream(pdf);
          }

          @Override
          protected String getFileName() {
            return getString("BibliotecheIscrizioneForm.nomeFile");
          }
        };
    add(download);

    final AjaxLink<Page> linkDownload =
        new AjaxLink<Page>("btnScaricaPrivacy") {

          private static final long serialVersionUID = 4503382426855985520L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
          }

          @Override
          public void onClick(final AjaxRequestTarget target) {
            target.add(containerPrivacy);
            target.add(containerMail);
            target.add(this);
            download.initiate(target);
            containerPrivacy.setVisible(true);
            containerMail.setVisible(true);

            setEnabled(false);
          }
        };

    add(linkDownload);
  }
}
