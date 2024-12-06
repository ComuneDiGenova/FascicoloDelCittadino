package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class FdCToggles<T extends Component> extends Panel {

  private static final long serialVersionUID = -5197932571565619850L;

  WebMarkupContainer wmcLabel;
  Label lblLabel;
  CheckBox checkbox;
  // Label label;
  String wicketId;
  IModel<Boolean> modelTextField;

  public FdCToggles(String wicketId, IModel<Boolean> modelTextField, T pannello) {
    super(wicketId);
    this.wicketId = wicketId;
    this.modelTextField = modelTextField;

    add(new AttributeModifier("class", "toggles"));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    checkbox = new CheckBox("checkbox", modelTextField);
    checkbox.setOutputMarkupId(true);
    checkbox.setOutputMarkupPlaceholderTag(true);

    creaLabelEtichettaGenerandoResourceId(pannello, wicketId);

    addOrReplace(wmcLabel);
    wmcLabel.addOrReplace(checkbox);
  }

  private void creaLabelEtichettaGenerandoResourceId(T pannello, String wicketId) {
    String nomePannello = pannello.getClass().getSimpleName();
    String resourceId = nomePannello + "." + wicketId;
    creaLabelEtichetta(pannello, resourceId, wicketId);
  }

  private void creaLabelEtichetta(T pannello, String resourceId, String wicketId) {
    String etichetta = getLocalizer().getString(resourceId, pannello);

    wmcLabel = new WebMarkupContainer("wmcLabel");
    wmcLabel.setOutputMarkupId(true);
    wmcLabel.setOutputMarkupPlaceholderTag(true);

    wmcLabel.add(new AttributeModifier("for", checkbox.getMarkupId()));

    wmcLabel.addOrReplace(lblLabel = new Label("lblLabel", etichetta));

    addOrReplace(wmcLabel);
  }

  public CheckBox getCheckbox() {
    return checkbox;
  }

  public void setCheckbox(CheckBox checkbox) {
    this.checkbox = checkbox;
  }
}
