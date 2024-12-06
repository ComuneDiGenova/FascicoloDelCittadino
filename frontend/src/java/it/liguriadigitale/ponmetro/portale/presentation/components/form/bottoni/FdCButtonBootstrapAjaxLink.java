package it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;

public class FdCButtonBootstrapAjaxLink<T> extends BootstrapAjaxLink<T> {

  private static final long serialVersionUID = -7652320708101208649L;

  public FdCButtonBootstrapAjaxLink(String id, Type type) {
    super(id, type);

    AttributeAppender style = new AttributeAppender("style", "min-width: 200px;");
    add(style);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  @Override
  public void onClick(AjaxRequestTarget arg0) {
    // TODO Auto-generated method stub

  }
}
