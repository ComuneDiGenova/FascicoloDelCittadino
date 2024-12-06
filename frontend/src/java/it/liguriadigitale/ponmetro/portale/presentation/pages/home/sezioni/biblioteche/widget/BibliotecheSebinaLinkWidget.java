package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.biblioteche.widget;

import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class BibliotecheSebinaLinkWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -6657797272694979129L;

  static final String URL_SEBINA = "https://bibliometroge.sebina.it/opac/.do";

  public BibliotecheSebinaLinkWidget(POSIZIONE posizione) {
    super(posizione);

    setOutputMarkupId(true);
    setRenderBodyOnly(false);

    fillDati("");

    setVisible(true);
  }

  @Override
  protected void mostraTestoWidget() {
    ExternalLink linkSebina = new ExternalLink("linkImg", URL_SEBINA);

    WebMarkupContainer iconaMarkup = new WebMarkupContainer("icona");
    Image image = new Image("image", new ContextRelativeResource("/images/bigmet.jpg"));
    iconaMarkup.add(image);
    linkSebina.add(iconaMarkup);

    add(linkSebina);
  }

  @Override
  protected void mostraIcona() {
    // TODO Auto-generated method stub

  }
}
