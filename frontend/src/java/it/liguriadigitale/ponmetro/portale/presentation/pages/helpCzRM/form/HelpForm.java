package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmSottoFascicoli;
import it.liguriadigitale.ponmetro.portale.pojo.richiestaassistenza.SottoCategoria;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.TelefonoFissoCellulareValidator;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

public class HelpForm extends AbstracFrameworkForm<Help> {

  private static final long serialVersionUID = -1465465464987896L;

  Help help;
  SottofascicoloDropDownChoice sottofascicolo;
  FeedbackPanel feedbackPanel;

  ServiziAssistenzaAutocomplete serviziAssistenzaAutocomplete;

  public HelpForm(String id, Help help, FeedbackPanel feedbackPanel) {
    super(id, help);
    this.help = help;
    this.feedbackPanel = feedbackPanel;

    setDefaultButton(getDefaultButton());
    addElementiForm();
  }

  @Override
  public void addElementiForm() {

    TextField<String> nome = new TextField<String>("nome", new PropertyModel<String>(help, "nome"));
    nome.setRequired(false);
    nome.setEnabled(false);

    TextField<String> cognome =
        new TextField<String>("cognome", new PropertyModel<String>(help, "cognome"));
    cognome.setRequired(false);
    cognome.setEnabled(false);

    TextField<String> cf = new TextField<String>("cf", new PropertyModel<String>(help, "cf"));
    cf.setRequired(false);
    cf.setEnabled(false);

    TextField<String> email =
        new TextField<String>("email", new PropertyModel<String>(help, "email"));
    email.setRequired(true);
    email.add(EmailAddressValidator.getInstance());

    TextField<String> telefono =
        new TextField<String>("telefono", new PropertyModel<String>(help, "telefono"));
    telefono.add(new TelefonoFissoCellulareValidator());
    telefono.add(new AttributeAppender("maxLength", "15"));

    sottofascicolo =
        new SottofascicoloDropDownChoice(
            "sottofascicolo", new PropertyModel<CzrmSottoFascicoli>(help, "sottofascicolo"));
    sottofascicolo.setRequired(true);
    sottofascicolo.setLabel(Model.of("Sotto Fascicolo"));
    sottofascicolo.add(
        new AjaxFormComponentUpdatingBehavior("change") {
          private static final long serialVersionUID = 1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            CzrmSottoFascicoli fascicolo = sottofascicolo.getModelObject();

            help.setServizioAssistenza(null);

            if (fascicolo.getSottoFascicoloValue() != null) {
              List<SottoCategoria> listaServizi =
                  popolaServiziDaGetListViews(fascicolo.getSottoFascicoloValue());

              serviziAssistenzaAutocomplete.setServizi(listaServizi);
            }

            target.add(sottofascicolo, serviziAssistenzaAutocomplete);
          }
        });

    AutoCompleteSettings settings = new AutoCompleteSettings();
    settings.setShowListOnEmptyInput(true);
    settings.setUseSmartPositioning(true);
    settings.setShowListOnFocusGain(true);

    AbstractAutoCompleteTextRenderer<SottoCategoria> autocompleteServiziAssistenzaRender =
        new AbstractAutoCompleteTextRenderer<SottoCategoria>() {

          private static final long serialVersionUID = -4120407055409927251L;

          @Override
          protected String getTextValue(SottoCategoria servizio) {
            return servizio.getServizio();
          }
        };

    serviziAssistenzaAutocomplete =
        new ServiziAssistenzaAutocomplete(
            "serviziAssistenzaAutocomplete",
            new PropertyModel<SottoCategoria>(help, "servizioAssistenza"),
            autocompleteServiziAssistenzaRender,
            new AutoCompleteSettings());
    serviziAssistenzaAutocomplete.setLabel(Model.of("Servizio"));
    serviziAssistenzaAutocomplete.setRequired(true);
    serviziAssistenzaAutocomplete.add(new AttributeModifier("class", "active"));
    serviziAssistenzaAutocomplete.add(new ServizioAssistenzaValidator());

    TextField<String> oggetto =
        new TextField<String>("oggetto", new PropertyModel<String>(help, "oggetto"));
    oggetto.setRequired(true);
    oggetto.add(new AttributeModifier("class", "active"));

    TextArea<String> descrizione =
        new TextArea<String>("descrizione", new PropertyModel<String>(help, "descrizione"));
    descrizione.setLabel(Model.of("Descrizione"));
    descrizione.setRequired(true);
    descrizione.add(new AttributeAppender("row", "4"));

    addOrReplace(nome);
    addOrReplace(cognome);
    addOrReplace(cf);
    addOrReplace(telefono);
    addOrReplace(email);
    addOrReplace(sottofascicolo);
    addOrReplace(serviziAssistenzaAutocomplete);
    addOrReplace(oggetto);
    addOrReplace(descrizione);
  }

  private List<SottoCategoria> popolaServiziDaGetListViews(String sottoFascicolo) {
    log.debug("HelpForm popolaServiziDaGetListViews = " + sottoFascicolo);

    List<SottoCategoria> lista =
        ServiceLocator.getInstance()
            .getRichiestaAssistenza()
            .getListaConSottoFascicolo(sottoFascicolo);

    return lista;
  }
}
