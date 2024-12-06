package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.legend;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.basic.Label;

public class LegendBaseElement implements Serializable {

  private static final long serialVersionUID = 1L;

  protected Log log = LogFactory.getLog(this.getClass());

  private String identifier;
  private String value;
  private String cssClass;

  public LegendBaseElement(String identifier, String value, String cssClass) {
    super();
    this.identifier = identifier;
    this.value = value;
    this.cssClass = cssClass;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getCssClass() {
    return cssClass;
  }

  public void setCssClass(String cssClass) {
    this.cssClass = " " + cssClass + " ";
  }

  public Label getEntireLabel() {
    return getEntireLabel(false);
  }

  public Label getEntireLabel(Boolean appendClass) {
    Label label = new Label("testoElemento", getValue());

    Behavior behaviorForAttribute;
    if (appendClass) {
      behaviorForAttribute = new AttributeAppender("class", getCssClass());
    } else {
      behaviorForAttribute = new AttributeModifier("class", getCssClass());
    }
    label.add(behaviorForAttribute);
    return label;
  }
}
