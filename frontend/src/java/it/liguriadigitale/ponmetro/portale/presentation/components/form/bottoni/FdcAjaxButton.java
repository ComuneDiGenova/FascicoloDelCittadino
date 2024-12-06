package it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;

public class FdcAjaxButton extends AjaxButton {

  private static final long serialVersionUID = -1407109799344928739L;

  public FdcAjaxButton(String id) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    AttributeAppender style = new AttributeAppender("style", "min-width: 200px;");
    add(style);
  }
}
