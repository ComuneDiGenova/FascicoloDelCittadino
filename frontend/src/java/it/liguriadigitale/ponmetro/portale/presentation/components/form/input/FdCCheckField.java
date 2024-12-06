package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class FdCCheckField<T extends Component> extends Panel {

  private static final long serialVersionUID = -5197932571565619850L;

  CheckBox checkBox;
  Label label;
  String wicketId;
  IModel<Boolean> modelCheckField;

  public FdCCheckField(String wicketId, IModel<Boolean> modelTextField, T pannello) {
    super(wicketId);
    this.wicketId = wicketId;
    this.modelCheckField = modelTextField;

    // add(new AttributeModifier("class", "form-group"));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    checkBox = new CheckBox("checkBox", modelCheckField);
    checkBox.setMarkupId("checkField" + getMarkupId());

    creaLabelEtichettaGenerandoResourceId(pannello, wicketId);

    addOrReplace(checkBox);
    addOrReplace(label);
  }

  private void creaLabelEtichettaGenerandoResourceId(T pannello, String wicketId) {
    String nomePannello = pannello.getClass().getSimpleName();
    String resourceId = nomePannello + "." + wicketId;
    creaLabelEtichetta(pannello, resourceId, wicketId);
  }

  private void creaLabelEtichetta(T pannello, String resourceId, String wicketId) {
    String etichetta = getLocalizer().getString(resourceId, pannello);

    checkBox.setLabel(Model.of(etichetta));

    label = new NotEmptyLabel("label", etichetta);
    label.setOutputMarkupId(true);
    label.setOutputMarkupPlaceholderTag(true);

    label.add(new AttributeModifier("for", "checkField" + getMarkupId()));
    // label.add(new AttributeModifier("class", "active"));

    addOrReplace(label);
  }

  public CheckBox getCheckBox() {
    return checkBox;
  }

  public void setCheckBox(CheckBox checkBox) {
    this.checkBox = checkBox;
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    if (checkBox.isRequired()) checkBox.add(new AttributeModifier("required", "true"));

    if (checkBox.isEnabled()) {
      checkBox.add(
          AttributeModifier.remove("disabled")); // new AttributeModifier("disabled", "false"));
      label.add(new AttributeModifier("class", "active"));
    } else {
      checkBox.add(new AttributeModifier("disabled", "true"));
      label.add(new AttributeModifier("class", "disabled"));
    }
  }
}
