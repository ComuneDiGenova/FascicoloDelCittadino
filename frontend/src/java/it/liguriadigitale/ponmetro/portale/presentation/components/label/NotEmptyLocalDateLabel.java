package it.liguriadigitale.ponmetro.portale.presentation.components.label;

import java.time.LocalDate;
import org.apache.wicket.model.IModel;

public class NotEmptyLocalDateLabel extends LocalDateLabel {

  private static final long serialVersionUID = 5925329261165834873L;

  public NotEmptyLocalDateLabel(String id, IModel<LocalDate> date) {
    super(id, date);
  }

  public NotEmptyLocalDateLabel(String id, LocalDate date) {
    super(id, date);
  }

  @Override
  public boolean isVisible() {
    if (getDefaultModelObject() == null) return false;
    else if (getDefaultModelObjectAsString().isEmpty()) return false;
    else return super.isVisible();
  }
}
