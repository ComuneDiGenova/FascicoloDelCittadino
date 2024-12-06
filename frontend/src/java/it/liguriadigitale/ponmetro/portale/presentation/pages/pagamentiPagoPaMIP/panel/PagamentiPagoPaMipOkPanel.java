package it.liguriadigitale.ponmetro.portale.presentation.pages.pagamentiPagoPaMIP.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class PagamentiPagoPaMipOkPanel extends BasePanel {

  private static final long serialVersionUID = 7042899722197637434L;

  public PagamentiPagoPaMipOkPanel(String id) {
    super(id);

    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {

    WebMarkupContainer iconaMarkup = new WebMarkupContainer("icona");
    Image image = new Image("image", new ContextRelativeResource("/images/pagopa-logo.png"));
    iconaMarkup.add(image);
    add(iconaMarkup);
  }
}
