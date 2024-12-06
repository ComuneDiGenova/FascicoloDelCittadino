package it.liguriadigitale.ponmetro.portale.presentation.components.label;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class SiNoLabel extends Label {

  private static final long serialVersionUID = 6557026198339607511L;

  public SiNoLabel(String id, IModel<Boolean> model) {
    super(id, formattaBool(model));
  }

  public SiNoLabel(String id, Boolean bool) {
    this(id, new Model<Boolean>(bool));
  }

  private static IModel<String> formattaBool(IModel<Boolean> model) {
    if (model.getObject()) return Model.of("Sì");
    else return Model.of("No");
  }
}
