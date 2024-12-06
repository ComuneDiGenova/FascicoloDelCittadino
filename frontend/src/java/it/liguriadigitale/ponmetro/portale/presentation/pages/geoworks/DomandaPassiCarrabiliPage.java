package it.liguriadigitale.ponmetro.portale.presentation.pages.geoworks;

import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import java.text.MessageFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class DomandaPassiCarrabiliPage extends WebPage {

  private static final long serialVersionUID = -7667409792675991329L;

  private static Log log = LogFactory.getLog(DomandaPassiCarrabiliPage.class);

  public DomandaPassiCarrabiliPage(GeoworksServizi servizio) {
    super();

    // String URL_RICHIESTA_PASSI_CARRABILI = BaseServiceImpl.API_GEOWORKS_DOMANDA +
    // servizio.getIdServizio() + "&provider=SIRACCGeExternal";

    String url = BaseServiceImpl.API_GEOWORKS_DOMANDA;
    String urlDomanda = MessageFormat.format(url, servizio.getIdServizio());

    throw new RestartResponseAtInterceptPageException(new RedirectPage(urlDomanda));
  }
}
