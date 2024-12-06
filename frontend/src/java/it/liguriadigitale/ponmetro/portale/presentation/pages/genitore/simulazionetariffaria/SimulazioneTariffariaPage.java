package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.simulazionetariffaria;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class SimulazioneTariffariaPage extends WebPage {

  private static final long serialVersionUID = -1592710267286302142L;
  private static final Log log = LogFactory.getLog(SimulazioneTariffariaPage.class);

  static final String URL_SIMULAZIONE =
      "https://www.applicazioni.comune.genova.it/tariffaristorazione/intro.asp";

  public SimulazioneTariffariaPage() {
    super();
    log.debug("SimulazioneTariffariaPage costruttore 1");
    throw new RestartResponseAtInterceptPageException(new RedirectPage(URL_SIMULAZIONE));
  }
}
