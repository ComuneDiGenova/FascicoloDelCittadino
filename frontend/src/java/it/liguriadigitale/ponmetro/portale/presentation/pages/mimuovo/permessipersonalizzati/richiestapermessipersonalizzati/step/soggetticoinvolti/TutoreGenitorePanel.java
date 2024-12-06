package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.soggetticoinvolti;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.combo.ComboTipoPatente;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CAPValidator;
import org.apache.wicket.Application;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class TutoreGenitorePanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  CheckBox tutoreCoincideConAccompagnatore;

  TextField<String> qualificaTutore;
  TextField<String> codiceFiscaleTutore;
  TextField<String> cognomeTutore;
  TextField<String> nomeTutore;
  FdCLocalDateTextfield dataNascitaTutore;
  TextField<String> luogoNascitaTutore;
  TextField<String> provinciaNascitaTutore;
  // --residenza
  TextField<String> indirizzoResidenzaTutore;
  TextField<String> capResidenzaTutore;
  TextField<String> cittaResidenzaTutore;
  TextField<String> provinciaResidenzaTutore;
  // --contatti
  TextField<String> telefonoTutore;
  TextField<String> cellulareTutore;
  EmailTextField emailTutore;
  EmailTextField pecTutore;

  // -- patente
  WebMarkupContainer wmkPatente;
  ComboTipoPatente tipoPatenteTutore;
  TextField<String> numeroPantenteTutore;
  FdCLocalDateTextfield dataRilascioPatenteTutore;
  FdCLocalDateTextfield dataScadenzaPatenteTutore;

  CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel;

  public TutoreGenitorePanel(
      String id,
      CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel) {
    super(id);
    this.richiestaPermessoPersonalizzatoModel = richiestaPermessoPersonalizzatoModel;
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {

    boolean isResidente = getUtente().isResidente();

    createFeedBackStep5Panel();

    addOrReplace(
        qualificaTutore =
            new TextField<String>(
                "qualificaTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.qualificaTutore")));
    qualificaTutore.setVisible(false);

    addOrReplace(
        codiceFiscaleTutore =
            new TextField<String>(
                "codiceFiscaleTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.codiceFiscaleTutore")));
    codiceFiscaleTutore.setEnabled(false);
    addOrReplace(
        cognomeTutore =
            new TextField<String>(
                "cognomeTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.cognomeTutore")));
    cognomeTutore.setEnabled(false);
    addOrReplace(
        nomeTutore =
            new TextField<String>(
                "nomeTutore",
                richiestaPermessoPersonalizzatoModel.bind("soggettiCoinvolti.tutore.nomeTutore")));
    nomeTutore.setEnabled(false);
    addOrReplace(
        dataNascitaTutore =
            new FdCLocalDateTextfield(
                "dataNascitaTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.dataNascitaTutore")));
    dataNascitaTutore.setEnabled(false);
    addOrReplace(
        luogoNascitaTutore =
            new TextField<String>(
                "luogoNascitaTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.luogoNascitaTutore")));
    luogoNascitaTutore.setEnabled(!isResidente);
    addOrReplace(
        provinciaNascitaTutore =
            new TextField<String>(
                "provinciaNascitaTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.provinciaNascitaTutore")));
    provinciaNascitaTutore.setEnabled(!isResidente);
    addOrReplace(
        indirizzoResidenzaTutore =
            new TextField<String>(
                "indirizzoResidenzaTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.indirizzoResidenzaTutore")));
    ;
    indirizzoResidenzaTutore.setEnabled(!isResidente);
    addOrReplace(
        capResidenzaTutore =
            new TextField<String>(
                "capResidenzaTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.capResidenzaTutore")));
    capResidenzaTutore.add(new CAPValidator());
    capResidenzaTutore.setEnabled(!isResidente);
    addOrReplace(
        cittaResidenzaTutore =
            new TextField<String>(
                "cittaResidenzaTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.cittaResidenzaTutore")));
    cittaResidenzaTutore.setEnabled(!isResidente);
    addOrReplace(
        provinciaResidenzaTutore =
            new TextField<String>(
                "provinciaResidenzaTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.provinciaResidenzaTutore")));
    provinciaResidenzaTutore.setEnabled(!isResidente);
    addOrReplace(
        telefonoTutore =
            new TextField<String>(
                "telefonoTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.telefonoTutore")));
    telefonoTutore.setEnabled(true);

    addOrReplace(
        cellulareTutore =
            new TextField<String>(
                "cellulareTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.cellulareTutore")));
    cellulareTutore.setEnabled(true);

    addOrReplace(
        emailTutore =
            new EmailTextField(
                "emailTutore",
                richiestaPermessoPersonalizzatoModel.bind("soggettiCoinvolti.tutore.emailTutore")));

    emailTutore.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("TutoreGenitorePanel.emailTutore", TutoreGenitorePanel.this)));

    emailTutore.setEnabled(true);
    addOrReplace(
        pecTutore =
            new EmailTextField(
                "pecTutore",
                richiestaPermessoPersonalizzatoModel.bind("soggettiCoinvolti.tutore.pecTutore")));

    pecTutore.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("TutoreGenitorePanel.pecTutore", TutoreGenitorePanel.this)));

    pecTutore.setEnabled(true);

    // -- pantente
    wmkPatente = new WebMarkupContainer("wmkPatente");

    wmkPatente.addOrReplace(
        tipoPatenteTutore =
            new ComboTipoPatente(
                "tipoPatenteTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.tipoPatenteTutore")));

    wmkPatente.addOrReplace(
        numeroPantenteTutore =
            new TextField<String>(
                "numeroPantenteTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.numeroPantenteTutore")));
    wmkPatente.addOrReplace(
        dataRilascioPatenteTutore =
            new FdCLocalDateTextfield(
                "dataRilascioPatenteTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.dataRilascioPatenteTutore")));

    dataRilascioPatenteTutore.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "TutoreGenitorePanel.dataRilascioPatenteTutore", TutoreGenitorePanel.this)));

    wmkPatente.addOrReplace(
        dataScadenzaPatenteTutore =
            new FdCLocalDateTextfield(
                "dataScadenzaPatenteTutore",
                richiestaPermessoPersonalizzatoModel.bind(
                    "soggettiCoinvolti.tutore.dataScadenzaPatenteTutore")));

    dataScadenzaPatenteTutore.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "TutoreGenitorePanel.dataScadenzaPatenteTutore", TutoreGenitorePanel.this)));

    wmkPatente.setVisible(
        richiestaPermessoPersonalizzatoModel
            .getObject()
            .getSoggettiCoinvolti()
            .isTutoreCoincideConAccompagnatore());
    addOrReplace(wmkPatente);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    wmkPatente.setVisible(
        richiestaPermessoPersonalizzatoModel
            .getObject()
            .getSoggettiCoinvolti()
            .isTutoreCoincideConAccompagnatore());
  }

  protected FeedbackPanel createFeedBackStep5Panel() {

    NotificationPanel feedback =
        new NotificationPanel("feedback5") {

          private static final long serialVersionUID = -8510097378330901001L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    feedback.setFilter(new ContainerFeedbackMessageFilter(TutoreGenitorePanel.this));
    feedback.setOutputMarkupId(true);
    this.addOrReplace(feedback);
    return feedback;
  }
}
