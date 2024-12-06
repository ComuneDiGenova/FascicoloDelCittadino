package it.liguriadigitale.ponmetro.portale.presentation.pages.error;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.Icon;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconTypeBuilder;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconTypeBuilder.FontAwesomeGraphic;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconTypeBuilder.Size;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import org.apache.wicket.markup.html.WebPage;

public class ErrorPage extends WebPage {

  private static final long serialVersionUID = -1853474812121195908L;

  //	public ErrorPage() {
  //		super();
  //
  //		Link link = new Link("supportoTec") {
  //
  //			private static final long serialVersionUID = -8794445862774081974L;
  //
  //			@Override
  //			public void onClick() {
  //				HelpCzRMPage page = new HelpCzRMPage();
  //				setResponsePage(page);
  //			}
  //
  //			@Override
  //			public MarkupContainer setDefaultModel(IModel model) {
  //				return setDefaultModel(model);
  //			}
  //		};
  //
  //
  //
  //
  //		addOrReplace(link);
  //	}

  @SuppressWarnings("unchecked")
  public ErrorPage() {
    super();

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    this.generaIconeFooter();
  }

  private void generaIconeFooter() {
    this.add(
        new Icon(
            "twitter-fa",
            FontAwesomeIconTypeBuilder.on(FontAwesomeGraphic.twitter).size(Size.large).build()));
    this.add(
        new Icon(
            "facebook-fa",
            FontAwesomeIconTypeBuilder.on(FontAwesomeGraphic.facebook).size(Size.large).build()));
    this.add(
        new Icon(
            "youtube-fa",
            FontAwesomeIconTypeBuilder.on(FontAwesomeGraphic.youtube).size(Size.large).build()));
    this.add(
        new Icon(
            "instagram-fa",
            FontAwesomeIconTypeBuilder.on(FontAwesomeGraphic.instagram).size(Size.large).build()));
    this.add(
        new Icon(
            "flickr-fa",
            FontAwesomeIconTypeBuilder.on(FontAwesomeGraphic.flickr).size(Size.large).build()));
  }
}
