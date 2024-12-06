package it.liguriadigitale.ponmetro.portale.presentation.pages.segnalazioni;

import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel.RichiestaRimborsoImuHelper;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class SegnalazioniPage extends WebPage {

  private static final long serialVersionUID = -6118829283414148945L;

  public SegnalazioniPage() {
    super();

    String urlSegnalaci = RichiestaRimborsoImuHelper.getUrl("URL_SENSOR_CIVICO");

    // "https://segnalazioni.comune.genova.it/";
    throw new RestartResponseAtInterceptPageException(new RedirectPage(urlSegnalaci));
  }
}
