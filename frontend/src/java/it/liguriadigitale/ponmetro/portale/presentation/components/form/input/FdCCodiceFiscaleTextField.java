package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class FdCCodiceFiscaleTextField<T extends Component> extends Panel {

  TextField textField;
  Label label;
  String wicketId;
  IModel<String> modelTextField;

  public FdCCodiceFiscaleTextField(String wicketId, IModel<String> modelTextField, T pannello) {
    super(wicketId);
    this.wicketId = wicketId;
    this.modelTextField = modelTextField;

    add(new AttributeModifier("class", "form-group"));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    textField = new TextField("textField", modelTextField);
    textField.setMarkupId("textField" + wicketId);

    textField.add(new CodiceFiscaleValidatorUtil());

    creaLabelEtichettaGenerandoResourceId(pannello, wicketId);

    addOrReplace(textField);
    addOrReplace(label);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
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
    label.add(new AttributeModifier("class", "active"));

    addOrReplace(label);
  }

  public TextField getTextField() {
    return textField;
  }

  public void setTextField(EmailTextField textField) {
    this.textField = textField;
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    if (textField.isRequired()) {
      textField.add(new AttributeModifier("required", "true"));
    } else {
      textField.add(AttributeModifier.remove("required"));
    }
  }
}
