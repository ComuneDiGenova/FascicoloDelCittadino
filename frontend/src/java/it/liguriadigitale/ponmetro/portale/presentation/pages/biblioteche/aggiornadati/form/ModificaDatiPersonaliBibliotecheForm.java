package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.aggiornadati.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.TelefonoFissoCellulareValidator;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

public class ModificaDatiPersonaliBibliotecheForm extends AbstracFrameworkForm<Utente> {

  private static final long serialVersionUID = 7939768278925586733L;

  private String email;

  private String telefono;

  public ModificaDatiPersonaliBibliotecheForm(String id, Utente model) {
    super(id, model);

    addElementiForm();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void addElementiForm() {
    Label cf = new Label("cf", getModelObject().getCf());
    addOrReplace(cf);

    Label nominativo = new Label("nominativo", getModelObject().getDisplayName());
    addOrReplace(nominativo);

    if (LabelFdCUtil.checkIfNotNull(getModelObject().getMail())) {
      getModelObject().setMail(getModelObject().getMail());
    }
    EmailTextField email =
        new EmailTextField("email", new PropertyModel<String>(getModelObject(), "mail"));
    email.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -3709711666730574150L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setEmail(email.getValue());
            getModelObject().setMail(email.getValue());
          }
        });
    getModelObject().setMail(email.getValue());
    email.setRequired(true);
    email.setLabel(Model.of("Email"));
    addOrReplace(email);

    if (LabelFdCUtil.checkIfNotNull(getModelObject().getMobile())) {
      getModelObject().setMobile(getModelObject().getMobile());
    }

    TextField telefono =
        new TextField("telefono", new PropertyModel<String>(getModelObject(), "mobile"));
    telefono.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = 1924418731220180156L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setTelefono(telefono.getValue());
            getModelObject().setMobile(telefono.getValue());
          }
        });
    getModelObject().setMobile(telefono.getValue());
    telefono.setRequired(true);
    telefono.setLabel(Model.of("Telefono"));
    telefono.add(new TelefonoFissoCellulareValidator());
    telefono.add(StringValidator.lengthBetween(9, 20));
    telefono.add(StringValidator.minimumLength(9));
    telefono.add(StringValidator.maximumLength(20));
    addOrReplace(telefono);
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }
}
