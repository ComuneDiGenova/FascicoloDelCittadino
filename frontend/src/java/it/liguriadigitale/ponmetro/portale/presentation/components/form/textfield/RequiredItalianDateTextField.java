package it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield;

import it.liguriadigitale.framework.presentation.components.form.textfield.ItalianDateTextField;
import java.util.Date;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.PropertyModel;

public class RequiredItalianDateTextField extends ItalianDateTextField {

  private static final long serialVersionUID = -3110937569630122530L;

  public RequiredItalianDateTextField(String id, PropertyModel<Date> model) {
    super(id, model);
  }

  public RequiredItalianDateTextField(String id, Boolean isMasked) {
    super(id, isMasked);
  }

  public RequiredItalianDateTextField(String id, PropertyModel<Date> model, Boolean isMasked) {
    super(id, model, isMasked);
  }

  public RequiredItalianDateTextField(String id) {
    super(id);
  }

  @Override
  protected void onComponentTag(final ComponentTag tag) {
    if (isRequired()) {
      tag.put("required", true);
      super.onComponentTag(tag);
    }
  }
}
