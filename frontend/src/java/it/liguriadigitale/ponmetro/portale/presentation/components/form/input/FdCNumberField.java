package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class FdCNumberField<T extends Component> extends Panel {

  private static final long serialVersionUID = -6265571845521614761L;

  NumberTextField<Integer> numberField;
  Label label;
  String wicketId;
  IModel<Integer> modelNumberField;
  T pannello;

  boolean required;

  protected static Log log = LogFactory.getLog(FdCNumberField.class);

  public FdCNumberField(String wicketId, IModel<Integer> modelNumberField) {
    super(wicketId);

    add(new AttributeModifier("class", "form-group"));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    numberField = new NumberTextField<>("numberField", modelNumberField);
    numberField.setMarkupId("numberField" + wicketId);

    addOrReplace(numberField);
  }

  public FdCNumberField(String wicketId, IModel<Integer> modelNumberField, T pannello) {
    this(wicketId, modelNumberField);
    creaLabelEtichettaGenerandoResourceId(pannello, wicketId);
    addOrReplace(label);
  }

  public FdCNumberField(
      String wicketId, IModel<Integer> modelTextField, T pannello, String nomePannello) {
    // TODO Auto-generated constructor stub
    this(wicketId, modelTextField);

    this.pannello = pannello;
    creaLabelEtichettaGenerandoResourceId(nomePannello, wicketId);
    addOrReplace(label);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    if (numberField.isRequired()) {
      numberField.add(new AttributeModifier("required", "true"));
    }
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

  private void creaLabelEtichettaGenerandoResourceId(String nomePannello, String wicketId) {
    String resourceId = nomePannello + "." + wicketId;
    creaLabelEtichetta(pannello, resourceId, wicketId);
  }

  private void creaLabelEtichetta(T pannello, String resourceId, String wicketId) {
    String etichetta = getLocalizer().getString(resourceId, pannello);

    numberField.setLabel(Model.of(etichetta));

    label = new NotEmptyLabel("label", etichetta);
    label.setOutputMarkupId(true);
    label.setOutputMarkupPlaceholderTag(true);

    label.add(new AttributeModifier("for", "numberField" + wicketId));
    label.add(new AttributeModifier("class", "active"));

    addOrReplace(label);
  }

  public NumberTextField<Integer> getNumberField() {
    return numberField;
  }

  public void setNumberField(NumberTextField<Integer> numberField) {
    this.numberField = numberField;
  }

  public boolean isRequired() {
    return numberField.isRequired();
  }

  public void setRequired(boolean required) {
    this.required = required;
    this.numberField.setRequired(required);
  }
}
