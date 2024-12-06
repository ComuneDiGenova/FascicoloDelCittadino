package it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

public class LabelledTextField<T> extends FormComponent<T> {

  private static final long serialVersionUID = 3221813761639349389L;

  FormComponentLabel label;
  TextField<T> textField;

  public LabelledTextField(String id, IModel<T> model, IModel<String> labelModel) {
    super(id, model);
    setDefaultModel(model);
    setLabel(labelModel);

    WebMarkupContainer webMarkupContainer = new WebMarkupContainer("labelWrapper");

    textField = new TextField<>("idText");
    textField.setModel(model);

    label = new FormComponentLabel("idLabel", textField);
    Label somethingLabelSpan = new Label("somethingLabelSpan", getLabel());
    label.add(somethingLabelSpan);

    webMarkupContainer.add(textField);
    webMarkupContainer.add(label);
    add(webMarkupContainer);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    if (isRequired()) textField.setRequired(isRequired());
  }

  @Override
  protected void onModelChanged() {
    super.onModelChanged();
  }
}
