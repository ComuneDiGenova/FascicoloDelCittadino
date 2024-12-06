package it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.LocalDateTextField;
import java.time.LocalDate;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.RangeValidator;

public class FdCLocalDateTextfield extends LocalDateTextField {

  /** */
  private static final long serialVersionUID = -3335483449183039155L;

  private static final String DATE_FORMAT = "yyyy-MM-dd";

  private boolean toRender = true;

  OnLoadHeaderItem localOnLoadHeaderItem = null;

  String markupId;

  public FdCLocalDateTextfield(String markupId, IModel<LocalDate> model) {
    super(markupId, model, DATE_FORMAT);
    this.markupId = markupId;

    add(AttributeModifier.replace("type", "date"));
  }

  public FdCLocalDateTextfield(String markupId) {
    super(markupId, DATE_FORMAT);
    this.markupId = markupId;

    add(AttributeModifier.replace("type", "date"));
  }

  @Override
  protected void onDisabled(ComponentTag tag) {
    super.onDisabled(tag);
  }

  @Override
  public void renderHead(IHeaderResponse response) {

    markupId = getMarkupId();
    String script = "";
    //        String script = "function aggiornaFormatoData"+ markupId+" () { \r\n"
    //        + "    if ($('#" + markupId + "').val() !='')  {"
    //        + "     var date = new Date($('#" + markupId + "').val());\r\n"
    //        +                 "var dd=date.getDate();\r\n"
    //        +                 "var mm=date.getMonth()+1;\r\n"
    //        +                 "var yy=date.getFullYear();\r\n"
    //        +                 "var newdate = dd +\"-\"+mm+\"-\"+yy;\r\n"
    //        +               "$('#" + markupId + "').val(newdate) \r\n"
    //        +          "} \r\n"
    //        + "         } \r\n"
    //        + "aggiornaFormatoData"+ markupId+"(); \r\n"
    //
    //
    //        + "$('#" + markupId + "').on('input', function(){ aggiornaFormatoData"+ markupId+"()
    // });";
    ////        String script = "$(document).ready(function() {"
    ////
    ////                + "    if(!$('#" + markupId + "').is(':disabled')  && !$('#" + markupId +
    // "').is('[readonly]')) {"
    ////
    ////                + "    $('#" + markupId + "').datepicker({" + "      inputFormat:
    // ['dd/MM/yyyy'],"
    ////                + "     outputFormat: 'dd/MM/yyyy',    }); "
    ////
    ////                + "    };"
    ////
    ////                + "}); /* inizializza DatePicker */";
    ////
    localOnLoadHeaderItem = new OnLoadHeaderItem(script);
    response.render(localOnLoadHeaderItem);
  }

  public boolean isToRender() {
    return toRender;
  }

  public void setToRender(boolean toRender) {
    this.toRender = toRender;
  }

  public void addNoFutureValidator() {
    RangeValidator<LocalDate> validator = new RangeValidator<LocalDate>(null, LocalDate.now());
    add(validator);
  }

  public void addNoPastValidator() {
    RangeValidator<LocalDate> validator = new RangeValidator<LocalDate>(LocalDate.now(), null);
    add(validator);
  }

  public void addNoFutureAndNoTodayValidator() {
    RangeValidator<LocalDate> validator =
        new RangeValidator<LocalDate>(null, LocalDate.now().minusDays(1));
    add(validator);
  }
}
