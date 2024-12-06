package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import java.time.LocalDate;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class FdCLocalDateField<T extends Component> extends Panel {

  private static final long serialVersionUID = -5197932571565619850L;

  FdCLocalDateTextfield textField;
  Label label;
  String wicketId;
  IModel<LocalDate> modelTextField;
  String idTextField;

  public FdCLocalDateField(String wicketId, IModel<LocalDate> modelTextField, T pannello) {
    super(wicketId);
    this.wicketId = wicketId;
    this.modelTextField = modelTextField;

    add(new AttributeModifier("class", "form-group"));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    textField = new FdCLocalDateTextfield("textField", modelTextField);
    idTextField = "textField" + wicketId;
    textField.setMarkupId(idTextField);

    // textField.add(new AttributeModifier("id", "textField" + wicketId));

    textField.setOutputMarkupId(true);
    textField.setOutputMarkupPlaceholderTag(true);

    creaLabelEtichettaGenerandoResourceId(pannello, wicketId);

    addOrReplace(textField);
    addOrReplace(label);
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

  @Override
  public void renderHead(IHeaderResponse response) {

    String script =
        "$(document).ready(function() {"
            + "	if(!$('#"
            + idTextField
            + "').is(':disabled')  && !$('#"
            + idTextField
            + "').is('[readonly]')) {"
            + "    $('#"
            + idTextField
            + "').datepicker({"
            + "      inputFormat: ['dd/MM/yyyy'],"
            + "     outputFormat: 'dd/MM/yyyy',    }); "
            + "	};"
            + "}); /* inizializza DatePicker */";

    OnLoadHeaderItem localOnLoadHeaderItem = new OnLoadHeaderItem(script);

    response.render(localOnLoadHeaderItem);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    if (textField.isRequired()) textField.add(new AttributeModifier("required", "true"));
  }

  public FdCLocalDateTextfield getTextField() {
    return textField;
  }
}
