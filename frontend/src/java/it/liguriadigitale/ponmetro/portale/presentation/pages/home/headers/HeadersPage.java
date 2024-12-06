package it.liguriadigitale.ponmetro.portale.presentation.pages.home.headers;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.WebPage;

public class HeadersPage extends WebPage {

  private static final long serialVersionUID = 4577958544484542488L;
  private static Log log = LogFactory.getLog(HeadersPage.class);

  public HeadersPage() {
    super();
    loggaHeaders();
  }

  private void loggaHeaders() {
    log.debug("[LayoutBasePage] >>>>> getHeaders:");
    final HttpServletRequest request = (HttpServletRequest) getRequest().getContainerRequest();
    final Enumeration<String> headerNames = request.getHeaderNames();

    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        final String elemento = headerNames.nextElement();
        log.debug("Header: " + elemento + "=" + request.getHeader(elemento));
      }
    }
  }
}
