package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.CurrencyFormattingBehavior;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class FdCTextPercetualeField<T extends Component> extends Panel {

  private static final long serialVersionUID = 2954246600984858009L;

  TextField<Double> textField;
  Label label;
  String wicketId;
  IModel<Double> modelTextField;
  String idTextField;
  String _format;

  boolean required;

  boolean enabled;

  public FdCTextPercetualeField(
      String wicketId, IModel<Double> modelTextField, T pannello, final String format) {
    super(wicketId);
    this.wicketId = wicketId;
    this.modelTextField = modelTextField;
    _format = format;

    add(new AttributeModifier("class", "form-group"));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    textField = new TextField<>("textField", modelTextField);

    textField.setMarkupId("textField" + wicketId);
    textField.add(new CurrencyFormattingBehavior(" %"));

    creaLabelEtichettaGenerandoResourceId(pannello, wicketId);

    addOrReplace(textField);
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

  private void creaLabelEtichettaGenerandoResourceId(T pannello, String wicketId) {
    String nomePannello = pannello.getClass().getSimpleName();
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

    addOrReplace(label);
  }

  public TextField<Double> getTextField() {
    return textField;
  }

  public void setTextField(TextField<Double> textField) {
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
