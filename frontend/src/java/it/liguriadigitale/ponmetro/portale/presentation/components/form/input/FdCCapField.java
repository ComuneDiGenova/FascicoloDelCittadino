package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CAPValidator;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class FdCCapField<T extends Component> extends Panel {

  private static final long serialVersionUID = -5197932571565619850L;

  TextField<String> textField;
  Label label;
  String wicketId;
  IModel<String> modelTextField;
  T pannello;

  boolean required;

  boolean enabled;

  public FdCCapField(String wicketId, IModel<String> modelTextField, T pannello) {
    super(wicketId);
    this.wicketId = wicketId;
    this.modelTextField = modelTextField;
    this.pannello = pannello;

    add(new AttributeModifier("class", "form-group"));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    textField = new TextField<>("textField", modelTextField);
    textField.setMarkupId("textField" + wicketId);
    textField.add(
        new AttributeModifier(
            "oninput",
            "this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\\..*)\\./g, '$1');"));
    textField.add(new CAPValidator());

    creaLabelEtichettaGenerandoResourceId(pannello, wicketId);

    addOrReplace(textField);
    addOrReplace(label);
  }

  public FdCCapField(
      String wicketId, IModel<String> modelTextField, T pannello, String nomePannello) {
    // TODO Auto-generated constructor stub
    super(wicketId);
    this.wicketId = wicketId;
    this.modelTextField = modelTextField;
    this.pannello = pannello;

    add(new AttributeModifier("class", "form-group"));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    textField = new TextField<>("textField", modelTextField);
    textField.add(new AttributeModifier("id", "textField" + wicketId));
    textField.add(
        new AttributeModifier(
            "oninput",
            "this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\\..*)\\./g, '$1');"));
    textField.add(new CAPValidator());

    creaLabelEtichettaGenerandoResourceId(nomePannello, wicketId);

    addOrReplace(textField);
    addOrReplace(label);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);

    // if (modelTextField.getObject() == null && isEnabled()) {
    // String script = "$('#" + getMarkupId() + "').addClass('form-group');";
    // response.render(OnDomReadyHeaderItem.forScript(script));
    // }

    /*
    if(modelTextField.getObject() != null) {
    	String script = "$('#" + label.getMarkupId() + "').addClass('active');";
    	response.render(OnDomReadyHeaderItem.forScript(script));
    }*/

  }

  private void creaLabelEtichettaGenerandoResourceId(T pannello, String wicketId) {
    String nomePannello = pannello.getClass().getSimpleName();
    String resourceId = nomePannello + "." + wicketId;
    creaLabelEtichetta(pannello, resourceId, wicketId);
  }

  private void creaLabelEtichettaGenerandoResourceId(String nomePannello, String wicketId) {
    String resourceId = nomePannello + "." + wicketId;
    creaLabelEtichetta(pannello, resourceId, wicketId);
  }

  private void creaLabelEtichetta(T pannello, String resourceId, String wicketId) {
    String etichetta = getLocalizer().getString(resourceId, pannello);

    textField.setLabel(Model.of(etichetta));

    label = new NotEmptyLabel("label", etichetta);
    label.setOutputMarkupId(true);
    label.setOutputMarkupPlaceholderTag(true);

    label.add(new AttributeModifier("for", "textField" + wicketId));
    label.add(new AttributeModifier("class", "active"));

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

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    if (textField.isRequired()) textField.add(new AttributeModifier("required", "true"));
  }
}
