package it.liguriadigitale.ponmetro.portale.presentation.components.form.validator;

import it.liguriadigitale.framework.presentation.components.behavior.RedAsteriskBehavior;
import org.apache.wicket.markup.html.form.TextField;

public class GestoreTextField<T> extends TextField<T> {

  private static final long serialVersionUID = -637497405427567892L;

  public GestoreTextField(String id) {
    super(id);
    add(new RedAsteriskBehavior());
  }
}
