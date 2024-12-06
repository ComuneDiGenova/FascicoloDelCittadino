package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class FdCEmailTextField<T extends Component> extends Panel {

  private static final long serialVersionUID = -5197932571565619850L;

  EmailTextField textField;
  Label label;
  String wicketId;
  IModel<String> modelTextField;

  public FdCEmailTextField(String wicketId, IModel<String> modelTextField, T pannello) {
    super(wicketId);
    this.wicketId = wicketId;
    this.modelTextField = modelTextField;

    add(new AttributeModifier("class", "form-group active"));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    textField = new EmailTextField("textField", modelTextField);
    textField.add(new AttributeModifier("id", "textField" + wicketId));
    textField.setMarkupId("textField" + wicketId);

    textField.add(
        new AttributeModifier("pattern", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));

    creaLabelEtichettaGenerandoResourceId(pannello, wicketId);

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
     * if(modelTextField.getObject() != null) { String script = "$('#" +
     * label.getMarkupId() + "').addClass('active');";
     * response.render(OnDomReadyHeaderItem.forScript(script)); }
     */

  }

  protected void creaLabelEtichettaGenerandoResourceId(T pannello, String wicketId) {
    String nomePannello = pannello.getClass().getSimpleName();
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
    label.add(new AttributeModifier("class", "active"));

    addOrReplace(label);
  }

  public EmailTextField getTextField() {
    return textField;
  }

  public void setTextField(EmailTextField textField) {
    this.textField = textField;
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    if (textField.isRequired()) textField.add(new AttributeModifier("required", "true"));
  }
}
