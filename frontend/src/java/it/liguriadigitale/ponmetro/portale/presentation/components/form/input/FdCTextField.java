package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class FdCTextField<T extends Component> extends Panel {

  private static final long serialVersionUID = -5197932571565619850L;

  TextField<String> textField;
  Label label;
  String wicketId;
  IModel<String> modelTextField;
  T pannello;

  protected Log log = LogFactory.getLog(getClass());

  boolean required;

  boolean enabled;

  public FdCTextField(String wicketId, IModel<String> modelTextField) {
    super(wicketId);
    this.wicketId = wicketId;
    this.modelTextField = modelTextField;

    add(new AttributeModifier("class", "form-group"));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    textField = new TextField<>("textField", modelTextField);

    textField.setMarkupId("textField" + wicketId);
    addOrReplace(textField);
  }

  public FdCTextField(String wicketId, IModel<String> modelTextField, String etichetta) {
    super(wicketId);
    this.wicketId = wicketId;
    this.modelTextField = modelTextField;

    add(new AttributeModifier("class", "form-group"));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    textField = new TextField<>("textField", modelTextField);

    textField.setMarkupId("textField" + wicketId);
    addOrReplace(textField);

    textField.setLabel(Model.of(etichetta));

    label = new NotEmptyLabel("label", etichetta);
    label.setOutputMarkupId(true);
    label.setOutputMarkupPlaceholderTag(true);

    label.add(new AttributeModifier("for", "textField" + wicketId));

    addOrReplace(label);
  }

  public FdCTextField(String wicketId, IModel<String> modelTextField, T pannello) {

    this(wicketId, modelTextField);
    this.pannello = pannello;
    creaLabelEtichettaGenerandoResourceId(pannello, wicketId);
    addOrReplace(label);
  }

  public FdCTextField(
      String wicketId, PropertyModel<String> modelTextField, T pannello, String nomePannello) {
    this(wicketId, modelTextField);

    this.pannello = pannello;
    creaLabelEtichettaGenerandoResourceId(nomePannello, wicketId);
    addOrReplace(label);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    if (textField.isRequired()) {
      textField.add(new AttributeModifier("required", "true"));
    } else {
      textField.add(AttributeModifier.remove("required"));
    }

    if (textField.isEnabled()) {
      label.add(new AttributeModifier("class", "active"));
    }
  }

  protected void creaLabelEtichettaGenerandoResourceId(T pannello, String wicketId) {

    log.debug("[creaLabelEtichettaGenerandoResourceId] Pannello: " + pannello + " Id: " + wicketId);
    String nomePannello = pannello.getClass().getSimpleName();
    String resourceId = nomePannello + "." + wicketId;
    creaLabelEtichetta(pannello, resourceId, wicketId);
  }

  protected void creaLabelEtichettaGenerandoResourceId(String nomePannello, String wicketId) {
    String resourceId = nomePannello + "." + wicketId;
    creaLabelEtichetta(pannello, resourceId, wicketId);
  }

  protected void creaLabelEtichetta(T pannello, String resourceId, String wicketId) {
    String etichetta = getLocalizer().getString(resourceId, pannello);

    textField.setLabel(Model.of(etichetta));

    label = new NotEmptyLabel("label", etichetta);
    label.setOutputMarkupId(true);
    label.setOutputMarkupPlaceholderTag(true);

    label.add(new AttributeModifier("for", "textField" + wicketId));

    addOrReplace(label);
  }

  public TextField<String> getTextField() {
    return textField;
  }

  public void setTextField(TextField<String> textField) {
    this.textField = textField;
  }

  public boolean isRequired() {
    return textField.isRequired();
  }

  public void setRequired(boolean required) {
    this.required = required;
    this.textField.setRequired(required);
  }
}
