package it.liguriadigitale.ponmetro.portale.presentation.components.label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class LocalDateLabel extends Label {

  private static final long serialVersionUID = 5925329261165834873L;
  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private IModel<LocalDate> date;

  public LocalDateLabel(String id, IModel<LocalDate> date) {
    super(id, formattaData(date));
    this.date = date;
  }

  public LocalDateLabel(String id, LocalDate date) {
    this(id, new Model<LocalDate>(date));
  }

  private static String formattaData(IModel<LocalDate> date) {
    if (null != date && null != date.getObject()) return date.getObject().format(formatter);
    else return null;
  }

  @Override
  public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
    // Aggiunto TAG accessibilita
    if (null != date && null != date.getObject()) {
      replaceComponentTagBody(
          markupStream, openTag, "<time datetime='" + date.getObject().toString() + "'> ");
    }
    super.onComponentTagBody(markupStream, openTag);
  }
}
