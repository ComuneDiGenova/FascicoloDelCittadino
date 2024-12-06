package it.liguriadigitale.ponmetro.portale.presentation.components.label;

import java.io.Serializable;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class NotEmptyLabel extends Label {

  private static final long serialVersionUID = 3256069642073161783L;

  public NotEmptyLabel(String id, Serializable label) {
    super(id, label);
  }

  public NotEmptyLabel(final String id, IModel<?> model) {
    super(id, model);
  }

  @Override
  public boolean isVisible() {
    if (getDefaultModelObject() == null) return false;
    else if (getDefaultModelObjectAsString().isEmpty()) return false;
    else return super.isVisible();
  }
}
