package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class FdCNumberDoubleField<T extends Component> extends Panel {

  NumberTextField<Double> doubleField;
  Label label;

  public NumberTextField<Double> getDoubleField() {
    return doubleField;
  }

  public void setDoubleField(NumberTextField<Double> doubleField) {
    this.doubleField = doubleField;
  }

  String wicketId;
  IModel<Double> modelDoubleField;

  T pannello;

  int i;

  public FdCNumberDoubleField(String wicketId, IModel<Double> modelDoubleField, T pannello, int i) {
    this(wicketId, modelDoubleField);
    this.pannello = pannello;
    this.i = i;
    doubleField.setMarkupId("doubleField" + wicketId + i);
    doubleField.add(new AttributeModifier("autocomplete", "off"));
    creaLabelEtichettaGenerandoResourceId(pannello, wicketId);
    addOrReplace(label);
  }

  public FdCNumberDoubleField(String wicketId, IModel<Double> modelDoubleField, T pannello) {

    this(wicketId, null, pannello, 0);
  }

  public FdCNumberDoubleField(String wicketId, IModel<Double> modelDoubleField) {
    super(wicketId);
    this.wicketId = wicketId;
    this.modelDoubleField = modelDoubleField;

    add(new AttributeModifier("class", "form-group"));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    doubleField = new NumberTextField<>("doubleField", modelDoubleField);
    doubleField.setMinimum(0.0);
    doubleField.add(new AttributeModifier("step", "any"));
    // doubleField.add(new CurrencyFormattingBehavior(" €"));

    doubleField.setMarkupId("doubleField" + wicketId + i);
    addOrReplace(doubleField);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
  }

  protected void creaLabelEtichettaGenerandoResourceId(T pannello, String wicketId) {
    String nomePannello = pannello.getClass().getSimpleName();
    String resourceId = nomePannello + "." + wicketId;
    creaLabelEtichetta(pannello, resourceId, wicketId);
  }

  protected void creaLabelEtichetta(T pannello, String resourceId, String wicketId) {
    String etichetta = getLocalizer().getString(resourceId, pannello);

    doubleField.setLabel(Model.of(etichetta));

    label = new NotEmptyLabel("label", etichetta);
    label.setOutputMarkupId(true);
    label.setOutputMarkupPlaceholderTag(true);

    label.add(new AttributeModifier("for", "doubleField" + wicketId + i));
    label.add(new AttributeModifier("class", "active"));

    addOrReplace(label);
  }
}
