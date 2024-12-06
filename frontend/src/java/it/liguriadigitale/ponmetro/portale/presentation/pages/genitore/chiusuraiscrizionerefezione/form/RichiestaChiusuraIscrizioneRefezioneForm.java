package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.chiusuraiscrizionerefezione.Chiusuraiscrizionerefezione;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class RichiestaChiusuraIscrizioneRefezioneForm
    extends AbstracFrameworkForm<Chiusuraiscrizionerefezione> {

  private static final long serialVersionUID = -6897593722524542583L;

  private Panel panel;

  private String email;

  private Utente utente;

  @SuppressWarnings("unused")
  private Chiusuraiscrizionerefezione model;

  public RichiestaChiusuraIscrizioneRefezioneForm(
      String id, Chiusuraiscrizionerefezione model, Panel panel, Utente utente) {
    super(id, model);
    this.setPanel(panel);
    this.setModel(model);
    this.setUtente(utente);

    addElementiForm(utente);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public void addElementiForm(Utente utente) {
    /*
     * EmailTextField email = new EmailTextField("emailContatto", new
     * PropertyModel(getModelObject(), "emailContatto"));
     * email.setRequired(true); email.add(new OnChangeAjaxBehaviorSenzaIndicator() {
     *
     * @Override protected void onUpdate(AjaxRequestTarget arg0) {
     * log.debug("onUpdate email disiscrizione form: forza l'update"); }
     *
     * }); add(email);
     */

    //
    if (utente.getMail() != null) {
      if (getModelObject().getEmailContatto() != null
          && !utente.getMail().equalsIgnoreCase(getModelObject().getEmailContatto())) {
        getModelObject().setEmailContatto(getModelObject().getEmailContatto());
      } else {
        getModelObject().setEmailContatto(utente.getMail());
      }
    }
    EmailTextField emailContatto =
        new EmailTextField(
            "emailContatto", new PropertyModel<String>(getModelObject(), "emailContatto"));
    emailContatto.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = 9116169759343033222L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setEmail(emailContatto.getValue());
            getModelObject().setEmailContatto(emailContatto.getValue());
          }
        });
    getModelObject().setEmailContatto(emailContatto.getValue());
    emailContatto.setRequired(true);
    addOrReplace(emailContatto);
    //

    TextField telefono =
        new TextField("telefonoContatto", new PropertyModel(getModelObject(), "telefonoContatto"));
    telefono.setRequired(true);
    telefono.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -7925109867892198823L;

          @Override
          protected void onUpdate(AjaxRequestTarget arg0) {
            log.debug("onUpdate telefono disiscrizione form: forza l'update");
          }
        });
    addOrReplace(telefono);

    TextField note = new TextField("note", new PropertyModel(getModelObject(), "note"));
    note.setRequired(true);
    note.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -2258714722149606961L;

          @Override
          protected void onUpdate(AjaxRequestTarget arg0) {
            log.debug("onUpdate note disiscrizione form: forza l'update");
          }
        });
    addOrReplace(note);
  }

  public Panel getPanel() {
    return panel;
  }

  public void setPanel(Panel panel) {
    this.panel = panel;
  }

  public void setModel(Chiusuraiscrizionerefezione model) {
    this.model = model;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  @Override
  public void addElementiForm() {
    // TODO Auto-generated method stub

  }
}
