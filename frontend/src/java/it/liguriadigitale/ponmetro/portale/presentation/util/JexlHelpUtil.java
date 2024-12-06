package it.liguriadigitale.ponmetro.portale.presentation.util;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;

public class JexlHelpUtil {

  private static final String JEXL_SYNTAX_URL =
      "http://commons.apache.org/jexl/reference/syntax.html";

  public static WebMarkupContainer getJexlHelpWebContainer(final String wicketId) {
    final WebMarkupContainer jexlHelp = new WebMarkupContainer(wicketId);
    jexlHelp.setVisible(false);
    jexlHelp.setOutputMarkupPlaceholderTag(true);
    jexlHelp.setOutputMarkupId(true);
    jexlHelp.add(new ExternalLink("jexlLink", JEXL_SYNTAX_URL));
    return jexlHelp;
  }

  public static AjaxLink<Void> getAjaxLink(final WebMarkupContainer wmc, final String wicketId) {
    AjaxLink<Void> questionMarkJexlHelp =
        new AjaxLink<Void>(wicketId) {

          boolean toogle = false;

          private static final long serialVersionUID = -7978723352517770644L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            toogle = !toogle;
            wmc.setVisible(toogle);
            target.add(wmc);
          }
        };
    return questionMarkJexlHelp;
  }
}
