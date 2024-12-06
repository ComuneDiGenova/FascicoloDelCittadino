package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.linkdinamico.redirect;

import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.linkdinamico.link.GloboLaddaLink;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class GloboRedirectPage extends WebPage {

  private static final long serialVersionUID = 5941445567574246672L;
  private static final Log log = LogFactory.getLog(GloboLaddaLink.class);
  private String url;

  public GloboRedirectPage(String url) {
    super();
    this.url = url;
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    log.debug("Redirect URL arrivata: " + url);
    throw new RestartResponseAtInterceptPageException(new RedirectPage(url));
  }
}
