package it.liguriadigitale.ponmetro.portale.presentation.components.combo.dropdownchoice;

import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.CampoObbligatorioBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

public class ObbligatorioDropDownChoice<T> extends DropDownChoice<T> {

  private static final long serialVersionUID = -7950395075333861013L;

  private boolean asteriskRendered;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ObbligatorioDropDownChoice(String id, IModel choices) {
    super(id, choices);
    this.add(new CampoObbligatorioBehavior());
    this.asteriskRendered = false;
  }

  @SuppressWarnings({})
  public ObbligatorioDropDownChoice(String id) {
    super(id);
    this.add(new CampoObbligatorioBehavior());
    this.asteriskRendered = false;
  }

  public boolean isAsteriskRendered() {
    return asteriskRendered;
  }

  public void setAsteriskRendered(boolean asteriskRendered) {
    this.asteriskRendered = asteriskRendered;
  }
}
