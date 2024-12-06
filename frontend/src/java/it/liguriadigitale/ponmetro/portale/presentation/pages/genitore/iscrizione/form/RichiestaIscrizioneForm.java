package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.Iscrizione;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class RichiestaIscrizioneForm extends AbstracFrameworkForm<Iscrizione> {

  private static final long serialVersionUID = 6237685019301972320L;

  @SuppressWarnings("unused")
  private Panel panel;

  @SuppressWarnings("unused")
  private Iscrizione model;

  // TODO da togliere 1.3.4
  /*
   * @SuppressWarnings("unused") private Boolean isAllergie = false;
   *
   * private Boolean isSanitaria = false;
   *
   * private Boolean isReligione = false;
   *
   * Boolean visibilitaDietaAllergie = false; Boolean visibilitaDietaSanitaria
   * = false; Boolean visibilitaDietaReligione = false;
   */

  public RichiestaIscrizioneForm(String id, Iscrizione model, Panel panel) {
    super(id, model);
    this.panel = panel;
    this.model = model;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void addElementiForm() {

    // TODO da togliere 1.3.4
    /*
     * List<Boolean> choices = Arrays.asList(false, true);
     * BootstrapRadioChoice<Boolean> radioDietaSanitari = new
     * BootstrapRadioChoice<>("dietaMotiviSanitari", choices, new
     * YesNoRenderer("radioDietaSanitari")); BootstrapRadioChoice<Boolean>
     * radioDietaReligiosi = new
     * BootstrapRadioChoice<>("dietaMotiviReligiosi", choices, new
     * YesNoRenderer("radioDietaReligiosi")); BootstrapRadioChoice<Boolean>
     * radioDietaAllergici = new
     * BootstrapRadioChoice<>("dietaEpisodiAllergici", choices, new
     * YesNoRenderer("radioDietaAllergici"));
     * aggiustaRadioPerBootstrapItalia(radioDietaSanitari,
     * radioDietaReligiosi, radioDietaAllergici);
     */

    // TODO da togliere 1.3.4
    /*
     * String urlComune = "https://smart.comune.genova.it/node/11590";
     * ExternalLink linkComune = new ExternalLink("linkComune", urlComune);
     */

    // TODO da togliere 1.3.4
    /*
     * Label messaggioDieta = new Label("messaggioDieta",
     * getString("RichiestaIscrizioneForm.messaggioDieta"));
     */

    // TODO da togliere 1.3.4
    /*
     * visibilitaDietaAllergie = getModelObject().isDietaEpisodiAllergici();
     * visibilitaDietaSanitaria = getModelObject().isDietaMotiviSanitari();
     * visibilitaDietaReligione = getModelObject().isDietaMotiviReligiosi();
     *
     * if (visibilitaDietaSanitaria) { radioDietaAllergici.setVisible(true);
     * } else { radioDietaAllergici.setVisible(false); } if
     * (visibilitaDietaSanitaria || visibilitaDietaAllergie ||
     * visibilitaDietaReligione) { linkComune.setVisible(true);
     * messaggioDieta.setVisible(true); } else {
     * linkComune.setVisible(false); messaggioDieta.setVisible(false); }
     *
     * radioDietaAllergici.setOutputMarkupId(true);
     * radioDietaReligiosi.setOutputMarkupId(true);
     * radioDietaSanitari.setOutputMarkupId(true);
     *
     * messaggioDieta.setOutputMarkupId(true);
     */

    // TODO da togliere 1.3.4
    // linkComune.setOutputMarkupId(true);

    // TODO da togliere 1.3.4
    /*
     * radioDietaSanitari.add(new AjaxFormChoiceComponentUpdatingBehavior()
     * {
     *
     * private static final long serialVersionUID = 1L;
     *
     * @Override protected void onUpdate(AjaxRequestTarget target) { if
     * (radioDietaSanitari.getInput().equalsIgnoreCase("true")) { if
     * (!linkComune.isVisible()) { linkComune.setVisible(true);
     * messaggioDieta.setVisible(true); }
     *
     * radioDietaAllergici.setVisible(true);
     *
     * isSanitaria = true;
     *
     * } else { radioDietaAllergici.setVisible(false);
     * radioDietaAllergici.setModelObject(false);
     * radioDietaAllergici.clearInput();
     *
     * if (isReligione || getModelObject().isDietaMotiviSanitari()) {
     * linkComune.setVisible(true); messaggioDieta.setVisible(true); } else
     * { linkComune.setVisible(false); messaggioDieta.setVisible(false); }
     *
     * isSanitaria = false;
     *
     * }
     *
     * target.add(RichiestaIscrizioneForm.this); } });
     *
     * radioDietaAllergici.add(new AjaxFormChoiceComponentUpdatingBehavior()
     * {
     *
     * private static final long serialVersionUID = 1L;
     *
     * @Override protected void onUpdate(AjaxRequestTarget target) {
     *
     * if (radioDietaAllergici.getInput().equalsIgnoreCase("true")) {
     * isAllergie = true; radioDietaAllergici.setVisible(true); } else {
     * isAllergie = false; }
     *
     * target.add(RichiestaIscrizioneForm.this); } });
     *
     * radioDietaReligiosi.add(new AjaxFormChoiceComponentUpdatingBehavior()
     * {
     *
     * private static final long serialVersionUID = 1L;
     *
     * @Override protected void onUpdate(AjaxRequestTarget target) { if
     * (radioDietaReligiosi.getInput().equalsIgnoreCase("true")) { if
     * (!linkComune.isVisible()) { linkComune.setVisible(true);
     * messaggioDieta.setVisible(true); }
     *
     * isReligione = true;
     *
     * } else { if (isSanitaria || getModelObject().isDietaMotiviSanitari())
     * { linkComune.setVisible(true); messaggioDieta.setVisible(true); }
     * else { linkComune.setVisible(false);
     * messaggioDieta.setVisible(false); }
     *
     * isReligione = false; }
     *
     * target.add(RichiestaIscrizioneForm.this); } });
     *
     * add(radioDietaSanitari); add(radioDietaReligiosi);
     * add(radioDietaAllergici);
     */

    // TODO da togliere 1.3.4
    // add(linkComune);

    // TODO da togliere 1.3.4
    // add(messaggioDieta);

    TextField indirizzo =
        new TextField("indirizzo", new PropertyModel(getModelObject(), "indirizzo"));
    TextField comune = new TextField("comune", new PropertyModel(getModelObject(), "comune"));
    TextField cap = new TextField("cap", new PropertyModel(getModelObject(), "cap"));

    add(indirizzo);
    add(comune);
    add(cap);

    EmailTextField email =
        new EmailTextField("emailContatto", new PropertyModel(getModelObject(), "emailContatto"));
    email.setRequired(true);
    email.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -7049158467606524337L;

          @Override
          protected void onUpdate(AjaxRequestTarget arg0) {

            log.debug("onUpdate email form: forza l'update");
          }
        });
    add(email);
  }

  // TODO da togliere 1.3.4
  /*
   * private void
   * aggiustaRadioPerBootstrapItalia(BootstrapRadioChoice<Boolean>
   * radioDietaSanitari, BootstrapRadioChoice<Boolean> radioDietaReligiosi,
   * BootstrapRadioChoice<Boolean> radioDietaAllergici) {
   * radioDietaSanitari.setInline(true); radioDietaReligiosi.setInline(true);
   * radioDietaAllergici.setInline(true);
   * radioDietaAllergici.setLabelPosition(LabelPosition.AFTER);
   * radioDietaSanitari.setLabelPosition(LabelPosition.AFTER);
   * radioDietaReligiosi.setLabelPosition(LabelPosition.AFTER);
   * radioDietaSanitari.
   * setPrefix("<div class=\"form-check form-check-inline\">");
   * radioDietaSanitari.setSuffix("</div>"); radioDietaReligiosi.
   * setPrefix("<div class=\"form-check form-check-inline\">");
   * radioDietaReligiosi.setSuffix("</div>"); radioDietaAllergici.
   * setPrefix("<div class=\"form-check form-check-inline\">");
   * radioDietaAllergici.setSuffix("</div>"); }
   */

}
