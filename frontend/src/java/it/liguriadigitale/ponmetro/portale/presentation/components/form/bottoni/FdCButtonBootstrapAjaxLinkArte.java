package it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;

public class FdCButtonBootstrapAjaxLinkArte<T> extends BootstrapAjaxLink<T> {

  private static final long serialVersionUID = 1L;

  public FdCButtonBootstrapAjaxLinkArte(String id, Type type) {
    super(id, type);

    AttributeAppender style = new AttributeAppender("style", "margin: 4px 6px;width: 132px;");
    add(style);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  @Override
  public void onClick(AjaxRequestTarget arg0) {
    // TODO Auto-generated method stub

  }
}
