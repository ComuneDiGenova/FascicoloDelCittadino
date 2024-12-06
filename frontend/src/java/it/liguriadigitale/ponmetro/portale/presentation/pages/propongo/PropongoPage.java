package it.liguriadigitale.ponmetro.portale.presentation.pages.propongo;

import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel.RichiestaRimborsoImuHelper;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class PropongoPage extends WebPage {

  private static final long serialVersionUID = 1461569784785571378L;

  public PropongoPage() {
    super();

    String urlProponiti = RichiestaRimborsoImuHelper.getUrl("URL_IO_PROPONGO");

    throw new RestartResponseAtInterceptPageException(new RedirectPage(urlProponiti));
  }
}
