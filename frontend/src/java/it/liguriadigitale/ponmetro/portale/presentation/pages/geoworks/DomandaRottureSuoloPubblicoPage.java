package it.liguriadigitale.ponmetro.portale.presentation.pages.geoworks;

import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import java.text.MessageFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class DomandaRottureSuoloPubblicoPage extends WebPage {

  private static final long serialVersionUID = -4966433531426003870L;

  private Log log = LogFactory.getLog(getClass());

  public DomandaRottureSuoloPubblicoPage(GeoworksServizi servizio) {
    super();

    log.debug("DomandaRottureSuoloPubblicoPage servizio = " + servizio);

    // String URL_RICHIESTA_SUOLO_PUBBLICO = BaseServiceImpl.API_GEOWORKS_DOMANDA +
    // servizio.getIdServizio();

    String url = BaseServiceImpl.API_GEOWORKS_DOMANDA;
    String urlDomanda = MessageFormat.format(url, servizio.getIdServizio());

    throw new RestartResponseAtInterceptPageException(new RedirectPage(urlDomanda));
  }
}
