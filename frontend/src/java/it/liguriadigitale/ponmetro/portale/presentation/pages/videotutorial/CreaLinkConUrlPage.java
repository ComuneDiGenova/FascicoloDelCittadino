package it.liguriadigitale.ponmetro.portale.presentation.pages.videotutorial;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class CreaLinkConUrlPage extends WebPage {

  private static final long serialVersionUID = 1862882097906900962L;

  private static final Log log = LogFactory.getLog(CreaLinkConUrlPage.class);

  private String url;

  public CreaLinkConUrlPage(PageParameters p) {
    super();
    log.debug("URL da chiamare " + p.get("url"));
    url = p.get("url").toString();
    throw new RestartResponseAtInterceptPageException(new RedirectPage(url));
  }
}
