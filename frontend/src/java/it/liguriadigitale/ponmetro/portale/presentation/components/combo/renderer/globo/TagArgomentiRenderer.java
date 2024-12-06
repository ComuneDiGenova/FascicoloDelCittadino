package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.globo;

import it.liguriadigitale.ponmetro.portale.pojo.globo.Tag;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class TagArgomentiRenderer extends ChoiceRenderer<Tag> {

  private static final long serialVersionUID = -5519955183880060889L;

  @Override
  public Object getDisplayValue(Tag object) {
    return object.getName();
  }

  @Override
  public String getIdValue(Tag object, int index) {
    return object.getId();
  }
}
