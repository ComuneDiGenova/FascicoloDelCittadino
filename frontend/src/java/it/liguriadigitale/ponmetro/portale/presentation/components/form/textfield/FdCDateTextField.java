package it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield;

import java.util.Date;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.model.IModel;

/**
 * @deprecated usa FdCLocalDateTextfield al suo posto
 */
@Deprecated
public class FdCDateTextField extends DateTextField {

  private static final long serialVersionUID = -2368570484725412105L;

  private static String datePattern = "dd/MM/yyyy";

  /**
   * @deprecated
   */
  @Deprecated
  public FdCDateTextField(String id) {
    super(id, datePattern);
  }

  /**
   * @deprecated
   */
  @Deprecated
  public FdCDateTextField(String id, IModel<Date> model, String datePattern) {
    super(id, model, datePattern);
  }

  /**
   * @deprecated
   */
  @Deprecated
  public FdCDateTextField(String id, IModel<Date> model) {
    super(id, model, datePattern);
  }

  /**
   * @deprecated
   */
  @Deprecated
  public FdCDateTextField(String id, String datePattern) {
    super(id, datePattern);
  }
}
