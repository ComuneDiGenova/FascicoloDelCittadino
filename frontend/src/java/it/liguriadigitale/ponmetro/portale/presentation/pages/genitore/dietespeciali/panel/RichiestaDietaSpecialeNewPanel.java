package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.DietaSpeciale;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.GiorniRientroScuola;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.DieteSpecialiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form.AnnoScolasticoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form.CategoriaScuolaEnum;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form.DirezioneTerritorialeDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form.MenuMotiviReligiosiDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form.RichiestaDietaSpecialeHelper;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form.ScuolaDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form.TipoDietaDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadDieteValidator;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AnnoScolastico;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpeciale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDirezioneTerritoriale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaRichiestaDietaSpeciale.TipoDietaSpecialeEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIstituto;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DietaMotiviEticoReligiosi;
import it.liguriadigitale.ponmetro.serviziristorazione.model.GiornoRientro.RientroEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.AbstractChoice.LabelPosition;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.Bytes;

public class RichiestaDietaSpecialeNewPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 1688038611118788617L;

  private enum Step {
    Home,
    InvioDati,
    Riepilogo,
    Esito
  };

  private StepPanel _stepPanel;
  private int _index = 1;

  private FdcAjaxButton _avanti;
  private FdcAjaxButton _indietro;
  private FdcAjaxButton _invia;
  private FdCButtonBootstrapAjaxLink<Object> _tornaListaDiete;
  private FdCButtonBootstrapAjaxLink<Object> _annulla;

  WebMarkupContainer container;
  WebMarkupContainer containerDietaMotiviSalute;
  WebMarkupContainer containerDietaMotiviReligiosi;
  WebMarkupContainer containerNoteIntegrative;
  WebMarkupContainer containerEsito;
  WebMarkupContainer containerFile;

  DietaSpeciale dietaSpeciale;

  AjaxButton eliminaFile;
  private Form<?> formUploadFile;
  private FileUploadField fileAttestazioneMedicaUpload;
  private String nomeFileAttestazioneMedica;
  private byte[] byteFileAttestazioneMedica;
  private String dimensioneFileAttestazioneMedica;

  TipoDietaDropDownChoise tipoDieta;
  MenuMotiviReligiosiDropDownChoise dietaMotiviEticoReligiosi;

  ScuolaDropDownChoise scuolaDropDownChoise;
  // DirezioneTerritorialeAutocomplete direzioneTerritoriale;
  DirezioneTerritorialeDropDownChoise direzioneTerritorialeDropDownChoise;

  AnnoScolasticoDropDownChoice annoScolasticoDropDownChoise;

  UtenteServiziRistorazione _iscrizione;

  CheckGroup<GiorniRientroScuola> selectGiorniRientro;
  NumberTextField<Integer> classe;
  FdCTextField<Component> sezione;

  CheckBoxMultipleChoice<RientroEnum> giornirientro;
  List<DatiDietaSpeciale> listaDieteSpecialiInviate;

  public RichiestaDietaSpecialeNewPanel(
      String id, DietaSpeciale dieta, UtenteServiziRistorazione iscrizione) {
    super(id, dieta);

    this._iscrizione = iscrizione;

    listaDieteSpecialiInviate = getListaDieteInviate(this._iscrizione.getCodiceFiscale());

    log.debug("[RichiestaDietaSpecialeNewPanel] Dieta: " + dieta);
    this.fillDati(dieta);
  }

  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    dietaSpeciale = (DietaSpeciale) dati;

    _stepPanel = new StepPanel("stepPanel", _index, getListaStep());

    form.getRootForm().setMultiPart(true);
    form.addOrReplace(_stepPanel);

    container = new WebMarkupContainer("container");
    container.setOutputMarkupId(true);
    container.setOutputMarkupPlaceholderTag(true);

    containerNoteIntegrative = new WebMarkupContainer("containerNoteIntegrative");
    containerNoteIntegrative.setOutputMarkupId(true);
    containerNoteIntegrative.setOutputMarkupPlaceholderTag(true);

    container.addOrReplace(
        RichiestaDietaSpecialeHelper.createFdCTextField(
            "nome",
            new PropertyModel<String>(dietaSpeciale, "nomeRichiedente"),
            false,
            false,
            RichiestaDietaSpecialeNewPanel.this));

    container.addOrReplace(
        RichiestaDietaSpecialeHelper.createFdCTextField(
            "cognome",
            new PropertyModel<String>(dietaSpeciale, "cognomeRichiedente"),
            false,
            false,
            RichiestaDietaSpecialeNewPanel.this));
    container.addOrReplace(
        RichiestaDietaSpecialeHelper.createFdCTextField(
            "cf",
            new PropertyModel<String>(dietaSpeciale, "cfRichiedente"),
            false,
            false,
            RichiestaDietaSpecialeNewPanel.this));

    FdCEmailTextField<Component> email =
        RichiestaDietaSpecialeHelper.createFdCEmailTextField(
            "email",
            new PropertyModel<String>(dietaSpeciale, "emailRichiedente"),
            true,
            true,
            RichiestaDietaSpecialeNewPanel.this);
    email.getTextField().setLabel(Model.of("Email"));
    container.addOrReplace(email);

    container.addOrReplace(
        RichiestaDietaSpecialeHelper.createFdCTextField(
            "nomeIscritto",
            new PropertyModel<String>(dietaSpeciale, "nomeIscritto"),
            false,
            false,
            RichiestaDietaSpecialeNewPanel.this));
    container.addOrReplace(
        RichiestaDietaSpecialeHelper.createFdCTextField(
            "cognomeIscritto",
            new PropertyModel<String>(dietaSpeciale, "cognomeIscritto"),
            false,
            false,
            RichiestaDietaSpecialeNewPanel.this));
    container.addOrReplace(
        RichiestaDietaSpecialeHelper.createFdCTextField(
            "cfIscritto",
            new PropertyModel<String>(dietaSpeciale, "cfIscritto"),
            false,
            false,
            RichiestaDietaSpecialeNewPanel.this));

    direzioneTerritorialeDropDownChoise =
        RichiestaDietaSpecialeHelper.creaDirezioneTerritorialeDropDownChoise(
            "direzioneTerritoriale",
            new PropertyModel<DatiDirezioneTerritoriale>(dietaSpeciale, "direzioneTerritoriale"));
    direzioneTerritorialeDropDownChoise.add(addDirezioneTerritorialeBehaviorOnChange());
    container.addOrReplace(direzioneTerritorialeDropDownChoise);

    container.addOrReplace(direzioneTerritorialeDropDownChoise);

    scuolaDropDownChoise =
        RichiestaDietaSpecialeHelper.creaScuolaDropDownChoise(
            "scuola", new PropertyModel<DatiIstituto>(dietaSpeciale, "datiIstituto"));
    scuolaDropDownChoise.setLabel(Model.of("Scuola"));
    scuolaDropDownChoise.add(addScuolaOnChange());
    container.addOrReplace(scuolaDropDownChoise);

    annoScolasticoDropDownChoise =
        RichiestaDietaSpecialeHelper.creaAnnoScolasticoDropDownChoise(
            "annoScolastico", new PropertyModel<AnnoScolastico>(dietaSpeciale, "annoScolastico"));
    container.addOrReplace(annoScolasticoDropDownChoise);

    /* Classe */
    classe =
        new NumberTextField<Integer>("classe", new PropertyModel<Integer>(dietaSpeciale, "classe"));
    classe.setMinimum(1);
    classe.setMaximum(5);
    classe.setRequired(true);
    classe.add(addOnChangeAjaxBehavior());
    classe.setLabel(Model.of("Classe"));

    Label label = new NotEmptyLabel("classeLabel", "Classe");
    label.setOutputMarkupId(true);
    label.setOutputMarkupPlaceholderTag(true);

    label.add(new AttributeModifier("for", classe.getMarkupId()));
    label.add(new AttributeModifier("class", "active"));

    container.add(label);
    container.add(classe);

    sezione =
        RichiestaDietaSpecialeHelper.createFdCTextField(
            "sezione",
            new PropertyModel<String>(dietaSpeciale, "sezione"),
            true,
            false,
            RichiestaDietaSpecialeNewPanel.this);
    container.addOrReplace(sezione);

    tipoDieta =
        RichiestaDietaSpecialeHelper.createFdcTipoDietaDropDown(
            "tipoDieta", new PropertyModel<TipoDietaSpecialeEnum>(dietaSpeciale, "tipoDieta"));
    tipoDieta.setLabel(Model.of("Tipo Dieta"));
    tipoDieta.add(addTipoDietaBehaviorOnChange());

    container.addOrReplace(tipoDieta);
    form.addOrReplace(container);

    containerFile = new WebMarkupContainer("containerFile");
    containerDietaMotiviSalute = new WebMarkupContainer("containerDietaMotiviSalute");
    containerDietaMotiviSalute.setOutputMarkupId(true);
    containerDietaMotiviSalute.setOutputMarkupPlaceholderTag(true);

    containerDietaMotiviReligiosi = new WebMarkupContainer("containerDietaMotiviReligiosi");
    containerDietaMotiviReligiosi.setOutputMarkupId(true);
    containerDietaMotiviReligiosi.setOutputMarkupPlaceholderTag(true);

    containerFile.setVisible(false);
    containerFile.setOutputMarkupId(true);

    Label nomeFile = new Label("nomeFile", "");
    Label dimensioneFile = new Label("dimensioneFile", "");

    eliminaFile =
        new AjaxButton("eliminaFile") {

          private static final long serialVersionUID = 6421871465837323595L;

          @Override
          protected void onSubmit(AjaxRequestTarget targetElimina) {

            containerFile.setVisible(false);

            setNomeFileAttestazioneMedica(null);
            setByteFileAttestazioneMedica(null);

            clearAttestazioneMedica();

            targetElimina.add(containerFile);
          }
          ;
        };
    eliminaFile.setDefaultFormProcessing(false);

    containerFile.addOrReplace(eliminaFile);

    containerFile.addOrReplace(nomeFile);
    containerFile.addOrReplace(dimensioneFile);

    formUploadFile =
        new Form<Void>("formUploadFile") {

          private static final long serialVersionUID = 5637460239713955952L;

          @Override
          protected void onSubmit() {

            final FileUpload uploadedFile = fileAttestazioneMedicaUpload.getFileUpload();
            if (uploadedFile != null) {}
          }
        };
    formUploadFile.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = 5911456237189742864L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {
            final FileUpload uploadedFile = fileAttestazioneMedicaUpload.getFileUpload();

            if (uploadedFile != null) {

              setFileAttestazioneMedicaUpload(fileAttestazioneMedicaUpload);
              setNomeFileAttestazioneMedica(uploadedFile.getClientFileName());
              setByteFileAttestazioneMedica(uploadedFile.getBytes());

              containerFile.setVisible(true);

              Label nomeFile = new Label("nomeFile", getNomeFileAttestazioneMedica());
              containerFile.addOrReplace(nomeFile);

              Label dimensioneFile =
                  new Label("dimensioneFile", FileFdCUtil.getSizeFile(uploadedFile.getSize()));
              containerFile.addOrReplace(dimensioneFile);

              target.add(formUploadFile, feedbackPanel);
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            super.onError(target);
            target.add(feedbackPanel);
          }
        });

    formUploadFile.setMultiPart(true);
    formUploadFile.setFileMaxSize(Bytes.kilobytes(2000));
    formUploadFile.addOrReplace(containerFile);
    formUploadFile.addOrReplace(
        fileAttestazioneMedicaUpload = new FileUploadField("fileAttestazioneMedicaUpload"));
    fileAttestazioneMedicaUpload.setLabel(Model.of("Upload attestazione medica"));
    fileAttestazioneMedicaUpload.add(new FileUploadDieteValidator());
    fileAttestazioneMedicaUpload.setOutputMarkupId(true);
    fileAttestazioneMedicaUpload.setOutputMarkupPlaceholderTag(true);

    containerDietaMotiviSalute.addOrReplace(formUploadFile);

    CheckBox anafilassi =
        new CheckBox("anafilassi", new PropertyModel<Boolean>(dietaSpeciale, "anafilassi"));
    anafilassi.setRequired(true);
    anafilassi.setOutputMarkupId(true);
    anafilassi.setOutputMarkupPlaceholderTag(true);
    anafilassi.setLabel(Model.of("Episodi di anafilassi"));
    containerDietaMotiviSalute.addOrReplace(anafilassi);

    containerDietaMotiviSalute.setVisible(false);

    dietaMotiviEticoReligiosi =
        RichiestaDietaSpecialeHelper.createMenuMotiviReligiosiDropDownChoise(
            "comboMenu", new PropertyModel<DietaMotiviEticoReligiosi>(dietaSpeciale, "comboMenu"));
    containerDietaMotiviReligiosi.addOrReplace(dietaMotiviEticoReligiosi);
    containerDietaMotiviReligiosi.setVisible(false);

    form.addOrReplace(containerDietaMotiviSalute);
    form.addOrReplace(containerDietaMotiviReligiosi);

    containerNoteIntegrative.addOrReplace(
        RichiestaDietaSpecialeHelper.createFdCTextField(
            "noteIntegrative",
            new PropertyModel<String>(dietaSpeciale, "noteIntegrative"),
            true,
            false,
            RichiestaDietaSpecialeNewPanel.this));

    giornirientro =
        new CheckBoxMultipleChoice<RientroEnum>(
            "listaGiorniRientri",
            Arrays.asList(RientroEnum.values()),
            new IChoiceRenderer<RientroEnum>() {
              private static final long serialVersionUID = 1L;

              @Override
              public Object getDisplayValue(RientroEnum obj) {
                RientroEnum item = obj;
                return item.toString();
              }

              @Override
              public String getIdValue(RientroEnum obj, int arg1) {
                RientroEnum item = obj;
                return item.toString();
              }

              @Override
              public RientroEnum getObject(
                  String arg0, IModel<? extends List<? extends RientroEnum>> arg1) {
                // TODO Auto-generated method stub
                return null;
              }
            });

    giornirientro.setPrefix("<div class=\"link-list\">");
    giornirientro.setSuffix("</div>");
    giornirientro.setLabelPosition(LabelPosition.AFTER);
    giornirientro.setRequired(true);
    giornirientro.setOutputMarkupPlaceholderTag(true);
    giornirientro.setModel(PropertyModel.of(dietaSpeciale, "listaGiorniRientriSelezionati"));
    giornirientro.setLabel(Model.of("Giorni di rientro"));

    container.addOrReplace(giornirientro);

    containerEsito = new WebMarkupContainer("containerEsito");
    creaBottoneTornaIndietro();
    Label lblEsito = new Label("labelEsito", "Richiesta dieta speciale inviata correttamente");
    containerEsito.addOrReplace(lblEsito);
    containerEsito.addOrReplace(_tornaListaDiete);
    form.addOrReplace(containerEsito);

    form.addOrReplace(container);
    form.addOrReplace(containerNoteIntegrative);
    creaBottoneProsegui();
    creaBottoneIndietro();
    creaBottoneAnnulla();
    creaBottoneInvia();

    form.addOrReplace(_avanti);
    form.addOrReplace(_annulla);
    form.addOrReplace(_invia);
    form.addOrReplace(_indietro);

    stepDatiPersonali();
  }

  /* Drop Down List OnChange */
  private AjaxFormComponentUpdatingBehavior addDirezioneTerritorialeBehaviorOnChange() {
    AjaxFormComponentUpdatingBehavior behavior =
        new AjaxFormComponentUpdatingBehavior("change") {
          private static final long serialVersionUID = -6241925736326802635L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // TODO Auto-generated method stub

            log.debug(
                "[DirezioneTerritoriale - Autocomplete] Direzione Territoriale: "
                    + direzioneTerritorialeDropDownChoise.getModelObject());

            if (direzioneTerritorialeDropDownChoise.getModelObject() != null) {
              log.debug(
                  "[DirezioneTerritoriale - Autocomplete] Cerco per Scuola: "
                      + direzioneTerritorialeDropDownChoise.getModelObject().getCodDirezTerr());

              scuolaDropDownChoise.clearInput();
              scuolaDropDownChoise.setListaScuola(
                  direzioneTerritorialeDropDownChoise.getModelObject().getCodDirezTerr());
            }

            target.add(direzioneTerritorialeDropDownChoise, scuolaDropDownChoise);
          }
        };

    return behavior;
  }

  private AjaxFormComponentUpdatingBehavior addTipoDietaBehaviorOnChange() {
    AjaxFormComponentUpdatingBehavior behavior =
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = -6241925736326802631L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            try {
              log.debug("[onUpdate - Select Tipo Dieta] value: " + tipoDieta.getModelObject());

              if (tipoDieta.getModelObject().equals(TipoDietaSpecialeEnum.ETICO_RELIGIOSI)) {

                log.debug("[onUpdate - Select Tipo Dieta - containerDietaMotiviReligiosi]");

                containerDietaMotiviReligiosi.setVisible(true);
                containerDietaMotiviSalute.setVisible(false);

                dietaMotiviEticoReligiosi.clearInput();
                dietaSpeciale.setComboMenu(null);
                dietaMotiviEticoReligiosi.setRequired(true);

                clearAttestazioneMedica();
                dietaSpeciale.setAnafilassi(false);

                containerFile.setVisible(false);

                setByteFileAttestazioneMedica(null);
                setNomeFileAttestazioneMedica(null);
                setDimensioneFileAttestazioneMedica(null);

              } else if (tipoDieta.getModelObject().equals(TipoDietaSpecialeEnum.DI_SALUTE)) {
                log.debug("[onUpdate - Select Tipo Dieta - containerDietaMotiviSalute]");

                containerDietaMotiviReligiosi.setVisible(false);
                containerDietaMotiviSalute.setVisible(true);

                dietaMotiviEticoReligiosi.clearInput();
                dietaMotiviEticoReligiosi.setRequired(false);
                dietaSpeciale.setComboMenu(null);
              }
            } catch (NullPointerException e) {
              log.debug("[onUpdate - Exception] " + e);
              containerDietaMotiviReligiosi.setVisible(false);
              containerDietaMotiviSalute.setVisible(false);
            }

            target.add(
                direzioneTerritorialeDropDownChoise,
                scuolaDropDownChoise,
                tipoDieta,
                containerDietaMotiviSalute,
                containerDietaMotiviReligiosi);
          }
        };

    return behavior;
  }

  private AjaxFormComponentUpdatingBehavior addOnChangeAjaxBehavior() {
    return new AjaxFormComponentUpdatingBehavior("change") {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onUpdate(AjaxRequestTarget target) {
        // TODO Auto-generated method stub
        if (LabelFdCUtil.checkIfNotNull(dietaSpeciale.getClasse())) {
          log.debug("[addOnChangeAjaxBehavior - Classe] Classe: " + dietaSpeciale.getClasse());
          sezione.getTextField().setRequired(isSezioneRequired());
          target.add(classe, sezione);
        }
      }

      @Override
      protected void onError(AjaxRequestTarget target, RuntimeException e) {
        log.debug("[addOnChangeAjaxBehavior - Classe] Change Classe Errore: " + e);
        target.add(classe, sezione);
      }
    };
  }

  private AjaxFormComponentUpdatingBehavior addScuolaOnChange() {
    AjaxFormComponentUpdatingBehavior behavior =
        new AjaxFormComponentUpdatingBehavior("change") {
          private static final long serialVersionUID = -6241925736326802636L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            sezione.getTextField().setRequired(isSezioneRequired());
            target.add(direzioneTerritorialeDropDownChoise, scuolaDropDownChoise, sezione);
          }

          @Override
          protected void onError(AjaxRequestTarget target, RuntimeException e) {
            log.debug("[addOnChangeAjaxBehavior - Classe] Change Classe Errore: " + e);
            target.add(direzioneTerritorialeDropDownChoise, scuolaDropDownChoise, sezione);
          }
        };

    return behavior;
  }

  /* Buttons */
  /* Button Events */
  private void creaBottoneInvia() {
    _invia =
        new FdcAjaxButton("invia") {

          /** */
          private static final long serialVersionUID = 1L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {

            try {

              ServiceLocator.getInstance()
                  .getServiziRistorazione()
                  .richiediDietaSpeciale(dietaSpeciale);

              _index = _index + 1;

              success("Richiesta dieta speciale inviata correttamente.");

            } catch (ApiException e) {

              String messaggioAttenzione = "Attenzione, errore nella richiesta di dieta speciale: ";

              log.error("Errore durante richiesta dieta speciale: " + e);
              String myMessage = e.getMyMessage();
              String eccezione = "javax.ws.rs.WebApplicationException:";
              String messaggioRicevuto = myMessage;
              if (myMessage.contains(eccezione)) {
                messaggioRicevuto = myMessage.substring(eccezione.length(), myMessage.length());
              }
              log.debug(
                  "Errore gestito durante la chiamata delle API Ristorazione dieta speciale: "
                      + myMessage,
                  e);

              Integer indexOf = messaggioRicevuto.indexOf(":");
              String messaggioDaVisualizzare =
                  messaggioRicevuto.substring(indexOf + 1, messaggioRicevuto.length());

              messaggioDaVisualizzare = messaggioAttenzione.concat(messaggioDaVisualizzare);

              log.debug("CP messaggio dieta errore = " + messaggioDaVisualizzare);

              error(messaggioDaVisualizzare);

            } catch (BusinessException e) {
              log.error(
                  "BusinessException gestito durante la chiamata delle API Ristorazione dieta speciale: ",
                  e);
              error(
                  "Attenzione, errore nella richiesta di dieta speciale. Si prega di riprovare più tardi. Grazie.");
            }

            getTargetPanel(target, dietaSpeciale);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(RichiestaDietaSpecialeNewPanel.this);

            log.error("[FdcAjaxButton - creaBottoneProsegui] Error Target");
          }
        };
  }

  private void creaBottoneProsegui() {
    _avanti =
        new FdcAjaxButton("prosegui") {

          /** */
          private static final long serialVersionUID = 1L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {

            log.debug("[Avanti] Target: " + target);

            log.debug("[Prosegui] Dieta da Inviare: " + dietaSpeciale);

            setAttestazioneMedicaInDieta();

            if (dietaSpeciale.getTipoDieta().equals(TipoDietaSpecialeEnum.DI_SALUTE)
                && !checkIfAllegatoAlmenoUnFile()) {

              error(
                  Application.get()
                      .getResourceSettings()
                      .getLocalizer()
                      .getString(
                          "RichiestaDietaSpecialeNewPanel.alertFile",
                          RichiestaDietaSpecialeNewPanel.this));
            } else if (dietaSpeciale.getTipoDieta().equals(TipoDietaSpecialeEnum.ETICO_RELIGIOSI)
                && checkMenuEticoReligiosoInviata(dietaSpeciale.getComboMenu())) {
              error(
                  Application.get()
                      .getResourceSettings()
                      .getLocalizer()
                      .getString(
                          "RichiestaDietaSpecialeNewPanel.alertDietaSpecialeDuplicata",
                          RichiestaDietaSpecialeNewPanel.this));
            } else {
              _index = _index + 1;
            }

            getTargetPanel(target, dietaSpeciale);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(RichiestaDietaSpecialeNewPanel.this);

            log.error("[FdcAjaxButton - creaBottoneProsegui] Error Target");
          }
        };
  }

  private void creaBottoneAnnulla() {
    _annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          /** */
          private static final long serialVersionUID = 1L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaDietaSpecialeNewPanel.this);
            form.clearInput();
            setResponsePage(new DieteSpecialiPage(_iscrizione));
          }
        };

    _annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaDietaSpecialeNewPanel.annulla",
                    RichiestaDietaSpecialeNewPanel.this)));
  }

  private void creaBottoneIndietro() {
    _indietro =
        new FdcAjaxButton("indietro") {

          /** */
          private static final long serialVersionUID = 1L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            _index = _index - 1;
            getTargetPanel(target, dietaSpeciale);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(RichiestaDietaSpecialeNewPanel.this);

            log.error("[FdcAjaxButton - creaBottoneProsegui] Error Target");
          }
        };
  }

  private void creaBottoneTornaIndietro() {
    _tornaListaDiete =
        new FdCButtonBootstrapAjaxLink<Object>("tornaIndietro", Type.Default) {

          /** */
          private static final long serialVersionUID = 1L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaDietaSpecialeNewPanel.this);
            form.clearInput();
            setResponsePage(new DieteSpecialiPage(_iscrizione));
          }
        };

    _tornaListaDiete.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaDietaSpecialeNewPanel.tornaIndietro",
                    RichiestaDietaSpecialeNewPanel.this)));
  }

  private AjaxRequestTarget getTargetPanel(AjaxRequestTarget target, DietaSpeciale dati) {
    this._stepPanel.setIndexStep(_index);
    target.add(_stepPanel, feedbackPanel, _indietro, _avanti, _annulla, _invia, giornirientro);
    log.debug("[getTargetPanel] Next or Previous Step: " + Step.values()[_index]);

    switch (Step.values()[_index]) {
        // DatiPersonali
      case InvioDati:
        stepDatiPersonali();
        target.add(this);
        break;
      case Riepilogo:
        stepRiepilogo();
        target.add(this);
        break;
      case Esito:
        stepEsito();
        target.add(this);
        break;
      default:
        break;
    }

    return target;
  }

  private void setAttestazioneMedicaInDieta() {
    if (!dietaSpeciale.getTipoDieta().equals(TipoDietaSpecialeEnum.DI_SALUTE)) {
      return;
    }

    // TODO Auto-generated method stub
    if (LabelFdCUtil.checkIfNotNull(getFileAttestazioneMedicaUpload())
        && LabelFdCUtil.checkIfNotNull(getByteFileAttestazioneMedica())) {

      dietaSpeciale.setByteFileAttestazioneMedica(getByteFileAttestazioneMedica());
      dietaSpeciale.setNomeFileAttestazioneMedica(getNomeFileAttestazioneMedica());
      dietaSpeciale.setDimensioneFileAttestazioneMedica(getDimensioneFileAttestazioneMedica());
      if (LabelFdCUtil.checkIfNotNull(getByteFileAttestazioneMedica())) {
        try {
          dietaSpeciale.setEstensioneFileAttestazioneMedica(
              FileFdCUtil.getEstensionFileUploadAllegato(getByteFileAttestazioneMedica()));

          dietaSpeciale.setMimeTypeFileAttestazioneMedica(
              FileFdCUtil.getMimeTypeFileUploadAllegato(getByteFileAttestazioneMedica()));

        } catch (BusinessException | MagicMatchNotFoundException e) {
          log.error("Errore durante mimetype o estensione attestazione medica = " + e.getMessage());
        }
      }

      log.debug(
          "[setAttestazioneMedicaInDieta] Compilo Dieta "
              + dietaSpeciale.getByteFileAttestazioneMedica().toString());
    }
  }

  private void clearAttestazioneMedica() {

    if (LabelFdCUtil.checkIfNotNull(dietaSpeciale)) {
      dietaSpeciale.setFileAttestazioneMedicaUpload(null);
      dietaSpeciale.setByteFileAttestazioneMedica(null);
      dietaSpeciale.setNomeFileAttestazioneMedica(null);
      dietaSpeciale.setEstensioneFileAttestazioneMedica(null);
      dietaSpeciale.setMimeTypeFileAttestazioneMedica(null);
    }
  }

  private void stepEsito() {
    // TODO Auto-generated method stub
    container.setVisible(false);
    container.setEnabled(false);
    containerNoteIntegrative.setEnabled(false);

    containerDietaMotiviReligiosi.setVisible(false);
    containerDietaMotiviSalute.setVisible(false);
    containerNoteIntegrative.setVisible(false);
    containerEsito.setVisible(true);

    _indietro.setVisible(false);
    _avanti.setVisible(false);
    _invia.setVisible(false);
    _annulla.setVisible(false);
    _tornaListaDiete.setVisible(false);
  }

  private void stepRiepilogo() {
    // TODO Auto-generated method stub
    container.setVisible(true);
    container.setEnabled(false);
    containerNoteIntegrative.setEnabled(false);
    containerDietaMotiviReligiosi.setEnabled(false);
    containerDietaMotiviSalute.setEnabled(false);
    containerEsito.setVisible(false);

    _indietro.setVisible(true);
    _invia.setVisible(true);
    _avanti.setVisible(false);
    _tornaListaDiete.setVisible(false);
  }

  private void stepDatiPersonali() {
    // TODO Auto-generated method stub
    container.setVisible(true);
    container.setEnabled(true);

    containerNoteIntegrative.setEnabled(true);
    containerDietaMotiviReligiosi.setEnabled(true);
    containerDietaMotiviSalute.setEnabled(true);
    containerEsito.setVisible(false);

    _invia.setVisible(false);
    _indietro.setVisible(false);
    _avanti.setVisible(true);
    _tornaListaDiete.setVisible(false);
  }

  public List<StepFdC> getListaStep() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Dati", 1));
    listaStep.add(new StepFdC("Riepilogo", 2));
    listaStep.add(new StepFdC("Esito", 3));

    return listaStep;
  }

  private boolean isSezioneRequired() {
    List<CategoriaScuolaEnum> categorie = Arrays.asList(CategoriaScuolaEnum.values());

    log.debug(
        "[ScuolaOnChange] Cod Categoria selezionata: "
            + dietaSpeciale.getDatiIstituto().getCodCategoria());
    log.debug("[ScuolaOnChange] Classe: " + dietaSpeciale.getClasse());
    log.debug("[ScuolaOnChange] Dieta Speciale: " + dietaSpeciale);

    boolean isRequired =
        LabelFdCUtil.checkIfNotNull(dietaSpeciale.getDatiIstituto())
            && categorie.contains(
                CategoriaScuolaEnum.getById(dietaSpeciale.getDatiIstituto().getCodCategoria()))
            && (LabelFdCUtil.checkIfNotNull(dietaSpeciale.getClasse())
                && dietaSpeciale.getClasse() >= 2);

    log.debug(
        "[ScuolaOnChange] Categoria Enum: "
            + CategoriaScuolaEnum.getById(dietaSpeciale.getDatiIstituto().getCodCategoria()));
    log.debug("[ScuolaOnChange] Sezione obbligatoria: " + isRequired);

    return isRequired;
  }

  /* Upload File */
  public FileUploadField getFileAttestazioneMedicaUpload() {
    return fileAttestazioneMedicaUpload;
  }

  public void setFileAttestazioneMedicaUpload(FileUploadField fileAttestazioneMedicaUpload) {
    this.fileAttestazioneMedicaUpload = fileAttestazioneMedicaUpload;
  }

  public String getNomeFileAttestazioneMedica() {
    return nomeFileAttestazioneMedica;
  }

  public void setNomeFileAttestazioneMedica(String nomeFileAttestazioneMedica) {
    this.nomeFileAttestazioneMedica = nomeFileAttestazioneMedica;
  }

  public byte[] getByteFileAttestazioneMedica() {
    return byteFileAttestazioneMedica;
  }

  public void setByteFileAttestazioneMedica(byte[] byteFileAttestazioneMedica) {
    this.byteFileAttestazioneMedica = byteFileAttestazioneMedica;
  }

  public String getDimensioneFileAttestazioneMedica() {
    return dimensioneFileAttestazioneMedica;
  }

  public void setDimensioneFileAttestazioneMedica(String dimensioneFileAttestazioneMedica) {
    this.dimensioneFileAttestazioneMedica = dimensioneFileAttestazioneMedica;
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

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    if (LabelFdCUtil.checkIfNotNull(eliminaFile)) {
      eliminaFile.setVisible(isEnabled());
    }

    if (LabelFdCUtil.checkIfNotNull(dietaSpeciale)) {
      fileAttestazioneMedicaUpload.setVisible(true);
    } else {
      fileAttestazioneMedicaUpload.setVisible(false);
    }
  }

  /* private Validator */
  // Vero se l'utente ha caricato il file
  private boolean checkIfAllegatoAlmenoUnFile() {
    log.debug(
        "[checkIfAllegatoAlmenoUnFile] checkIfAllegatoAlmenoUnFile: "
            + dietaSpeciale.getByteFileAttestazioneMedica());
    log.debug(
        "[checkIfAllegatoAlmenoUnFile] checkIfAllegatoAlmenoUnFile: "
            + dietaSpeciale.getNomeFileAttestazioneMedica());

    return LabelFdCUtil.checkIfNotNull(dietaSpeciale.getByteFileAttestazioneMedica());
  }

  private boolean checkMenuEticoReligiosoInviata(
      DietaMotiviEticoReligiosi dietaMotiviEticoReligiosi) {
    return this.listaDieteSpecialiInviate.stream()
        .anyMatch(
            x ->
                x.getTipoDieta().equals(TipoDietaSpecialeEnum.ETICO_RELIGIOSI.toString())
                    && (x.getStato().toUpperCase().equals("APERTA")
                        || x.getStato().toUpperCase().equals("ACCETTATA"))
                    && x.getDietaMotiviReligiosi()
                        .getCodice()
                        .equals(dietaMotiviEticoReligiosi.getCodice()));
  }

  private List<DatiDietaSpeciale> getListaDieteInviate(String cfIscritto) {
    try {
      return ServiceLocator.getInstance().getServiziRistorazione().getDieteSpeciali(cfIscritto);

    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      // throw new
      // RestartResponseAtInterceptPageException(ErrorPage.class);
      return new ArrayList<DatiDietaSpeciale>();
    }
  }
}
