package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.tarinetribe.model.Rata;
import java.time.format.DateTimeFormatter;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class RataTariChoiceRenderer extends ChoiceRenderer<Rata> {

  private static final long serialVersionUID = 5414980052197676047L;
  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  @Override
  public Object getDisplayValue(final Rata obj) {
    Rata c = obj;
    if (c.getNumRata() < 1) return "Unica soluzione";
    else return "Rata scadenza: " + c.getDataScadenzaRata().format(formatter);
  }

  @Override
  public String getIdValue(final Rata obj, final int index) {
    Rata c = obj;
    if ((c != null) && (c.getNumRata() != null)) return c.getNumRata().toString();
    else return "";
  }
}
