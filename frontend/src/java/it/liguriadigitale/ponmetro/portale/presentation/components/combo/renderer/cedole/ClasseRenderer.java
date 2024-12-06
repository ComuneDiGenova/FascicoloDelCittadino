package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.cedole;

import it.liguriadigitale.ponmetro.scuola.cedole.model.Classe;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class ClasseRenderer extends ChoiceRenderer<Classe> {

  private static final long serialVersionUID = 8519661636463330168L;

  @Override
  public Object getDisplayValue(final Classe obj) {
    Classe c = obj;
    return c.getClasse() + " " + c.getSezione();
  }

  @Override
  public String getIdValue(final Classe obj, final int index) {
    Classe c = obj;
    if ((c != null) && (c.getId() != null)) return c.getId().toString();
    else return "";
  }
}
