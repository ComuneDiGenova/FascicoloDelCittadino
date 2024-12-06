package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import it.liguriadigitale.ponmetro.portale.presentation.util.validator.TelefonoFissoCellulareValidator;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;

public class FdCPhoneNumberField<T> extends FdCTextField<Component> {

  private static final long serialVersionUID = -5197932571565619850L;

  public FdCPhoneNumberField(String wicketId, IModel<String> modelTextField, Component pannello) {
    super(wicketId, modelTextField, pannello);

    textField.add(
        new AttributeModifier(
            "oninput", "this.value.replace(/[^0-9.]/g, '').replace(/(\\..*)\\./g, '$1');"));
    textField.add(new TelefonoFissoCellulareValidator());

    label.add(new AttributeModifier("class", "active"));
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
  }
}
