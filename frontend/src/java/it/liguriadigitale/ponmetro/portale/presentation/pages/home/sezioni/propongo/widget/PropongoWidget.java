package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.propongo.widget;

import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel.RichiestaRimborsoImuHelper;
import org.apache.wicket.markup.html.link.ExternalLink;

public class PropongoWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -3649206453761042059L;

  public PropongoWidget(POSIZIONE posizione) {
    super(posizione);

    fillDati("");
  }

  @Override
  protected void mostraTestoWidget() {
    String urlProponiti = RichiestaRimborsoImuHelper.getUrl("URL_IO_PROPONGO");
    ExternalLink linkImg = new ExternalLink("linkImg", urlProponiti);
    addOrReplace(linkImg);
  }

  @Override
  protected void mostraIcona() {}
}
