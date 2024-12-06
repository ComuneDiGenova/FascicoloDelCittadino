package it.liguriadigitale.ponmetro.portale.presentation.components.label;

import java.io.Serializable;
import java.time.LocalDate;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class LabelConScadenza extends Label {

  private static final long serialVersionUID = -8105477200693054498L;

  private LocalDate dataScadenza = LocalDate.of(2021, 12, 31);

  public LabelConScadenza(String id, IModel<?> model) {
    super(id, model);
  }

  public LabelConScadenza(String id, Serializable label) {
    super(id, label);
  }

  public LabelConScadenza(String id) {
    super(id);
  }

  @Override
  protected void onConfigure() {
    super.onConfigure();
    setVisible(LocalDate.now().isBefore(dataScadenza));
  }
}
