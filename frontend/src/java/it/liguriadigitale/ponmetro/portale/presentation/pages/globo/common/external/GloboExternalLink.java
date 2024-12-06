package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.external;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;

public class GloboExternalLink extends Panel {
  private static final long serialVersionUID = -6527814402202345257L;

  private String url;

  public GloboExternalLink(String id, String url, String label) {
    super(id);
    this.url = url;
    ExternalLink externalLink =
        new ExternalLink("link", url, label) {

          private static final long serialVersionUID = -9075202910150387437L;

          @Override
          protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
          }
        };
    externalLink.setVisible(!StringUtils.isEmpty(url));
    add(externalLink);
  }
}
