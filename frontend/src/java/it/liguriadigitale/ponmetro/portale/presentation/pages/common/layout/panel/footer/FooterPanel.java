package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.footer;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.Icon;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconTypeBuilder;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconTypeBuilder.FontAwesomeGraphic;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconTypeBuilder.Size;
import it.liguriadigitale.framework.presentation.components.panel.FrameworkWebPanel;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;

public class FooterPanel extends FrameworkWebPanel {

  private static final long serialVersionUID = -1264296706185857837L;

  public FooterPanel(String id) {
    super(id);
    this.generaIconeFooter();
    add(new HeaderResponseContainer("footer-container", "footer-container"));
    add(new AboveFooter("above-footer"));
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
