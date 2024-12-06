package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.RevocaDietaSpeciale;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpeciale;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class RevocaDietaSpecialeForm extends AbstracFrameworkForm<RevocaDietaSpeciale> {

  private static final long serialVersionUID = -1124887853505370541L;

  private Utente utente;

  private String email;

  private DatiDietaSpeciale datiDietaSpeciale;

  public RevocaDietaSpecialeForm(
      String id, RevocaDietaSpeciale model, Utente utente, DatiDietaSpeciale datiDietaSpeciale) {
    super(id, model);

    this.setUtente(utente);
    this.datiDietaSpeciale = datiDietaSpeciale;

    addElementiForm(utente);
  }

  public void addElementiForm(Utente utente) {

    log.debug("CP dieta = " + datiDietaSpeciale);

    String cognomeNomeRichiedente =
        getUtente().getCognome().concat(" ").concat(getUtente().getNome());
    Label io = new Label("io", cognomeNomeRichiedente);
    io.setVisible(PageUtil.isStringValid(cognomeNomeRichiedente));
    addOrReplace(io);

    Label id = new Label("id", datiDietaSpeciale.getIdentificativo());
    id.setVisible(PageUtil.isStringValid(datiDietaSpeciale.getIdentificativo()));
    addOrReplace(id);

    String cognomeNomeBambino =
        datiDietaSpeciale
            .getCognomeIscritto()
            .concat(" ")
            .concat(datiDietaSpeciale.getNomeIscritto());
    Label bambino = new Label("bambino", cognomeNomeBambino);
    bambino.setVisible(PageUtil.isStringValid(cognomeNomeBambino));
    addOrReplace(bambino);

    if (LabelFdCUtil.checkIfNotNull(utente.getMail())) {
      if (LabelFdCUtil.checkIfNotNull(getModelObject().getEmailRichiedenteRevoca())
          && !utente.getMail().equalsIgnoreCase(getModelObject().getEmailRichiedenteRevoca())) {
        getModelObject().setEmailRichiedenteRevoca(getModelObject().getEmailRichiedenteRevoca());
      } else {
        getModelObject().setEmailRichiedenteRevoca(utente.getMail());
      }
    }
    EmailTextField emailRichiedenteRevoca =
        new EmailTextField(
            "emailRichiedenteRevoca",
            new PropertyModel<String>(getModelObject(), "emailRichiedenteRevoca"));
    emailRichiedenteRevoca.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = 5674246653864565463L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            setEmail(emailRichiedenteRevoca.getValue());
            getModelObject().setEmailRichiedenteRevoca(emailRichiedenteRevoca.getValue());
          }
        });
    getModelObject().setEmailRichiedenteRevoca(emailRichiedenteRevoca.getValue());
    emailRichiedenteRevoca.setRequired(true);
    emailRichiedenteRevoca.setLabel(Model.of("Email"));
    addOrReplace(emailRichiedenteRevoca);
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public void addElementiForm() {
    // TODO Auto-generated method stub

  }
}
