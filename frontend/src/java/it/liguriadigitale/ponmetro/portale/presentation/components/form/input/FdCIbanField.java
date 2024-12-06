package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import it.liguriadigitale.framework.presentation.components.form.components.validators.IbanValidator;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.IbanFdCValidator;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

public class FdCIbanField<T> extends FdCTextField<Component> {

  private static final long serialVersionUID = -5197932571565619850L;

  public FdCIbanField(String wicketId, IModel<String> modelTextField, Component pannello) {
    super(wicketId, modelTextField, pannello);

    textField.add(StringValidator.minimumLength(15));
    textField.add(StringValidator.maximumLength(31));

    textField.add(new IbanFdCValidator());
    textField.add(new IbanValidator());

    // textField.add(AttributeModifier.append("pattern", "^IT\\d{2}[A-Z]{1}\\d{10}[A-Z0-9]{12}$"));

    // textField.add(AttributeModifier.append("style", "text-transform:uppercase"));
    textField.add(AttributeModifier.append("autocomplete", "off"));
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
  }
}
