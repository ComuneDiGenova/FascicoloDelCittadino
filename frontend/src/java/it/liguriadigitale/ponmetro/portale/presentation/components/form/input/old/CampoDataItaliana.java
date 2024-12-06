package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.LocalDateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.LocalDateTextFieldConfig;
import it.liguriadigitale.framework.presentation.components.behavior.ComponentStyleBehavior;
import it.liguriadigitale.framework.presentation.components.form.components.interfaces.InvalidForceable;
import java.time.LocalDate;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.DateValidator;

public class CampoDataItaliana extends LocalDateTextField implements InvalidForceable {

  private static final long serialVersionUID = 273005386097043808L;

  private boolean invalidForced;

  protected Log log = LogFactory.getLog(this.getClass());

  private static String dataPattern = "dd/MM/yyyy";
  private static Short annoInizioData = 1900;

  public CampoDataItaliana(final String id) {
    super(id, dateConfig());
    this.setOutputMarkupId(true);
    this.setInvalidForced(false);
    this.add(new ComponentStyleBehavior());
    this.add(AttributeModifier.replace("autocomplete", "off"));
  }

  public CampoDataItaliana(final String id, Date dataInizio, Date dataFine) {
    this(id);
    this.add(new DateValidator(dataInizio, dataFine));
  }

  public CampoDataItaliana(final String id, final IModel<LocalDate> model) {
    super(id, model, dateConfig());
    this.setOutputMarkupId(true);
    this.setInvalidForced(false);
    this.add(new ComponentStyleBehavior());
  }

  private static LocalDateTextFieldConfig dateConfig() {
    return new LocalDateTextFieldConfig()
        .autoClose(true)
        .withLanguage("it")
        .withFormat(dataPattern)
        .highlightToday(true)
        .forceParse(true);
    // .withStartDate(new LocalDateTime(2000,1,1,0,0,0));

  }

  @Override
  public boolean isInvalidForced() {
    return invalidForced;
  }

  @Override
  public void setInvalidForced(boolean invalidForced) {
    this.invalidForced = invalidForced;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);

    StringBuilder sb = new StringBuilder();
    if (isRequired()) {
      sb.append(
          "$(document).ready(function() { \r\n"
              + "$('#"
              + getMarkupId()
              + "').rules( \"add\", { required: true}) ;});");
    }
    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }
}
