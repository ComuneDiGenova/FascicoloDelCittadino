package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.segnalazioni.widget;

import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel.RichiestaRimborsoImuHelper;
import org.apache.wicket.markup.html.link.ExternalLink;

public class MieSegnalazioniWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 1564385765948746138L;

  public MieSegnalazioniWidget(POSIZIONE posizione) {
    super(posizione);

    fillDati("");
  }

  @Override
  protected void mostraTestoWidget() {
    // String urlSegnalaci = "https://segnalazioni.comune.genova.it/";
    String urlSegnalaci = RichiestaRimborsoImuHelper.getUrl("URL_SENSOR_CIVICO");
    ExternalLink linkImg = new ExternalLink("linkImg", urlSegnalaci);
    addOrReplace(linkImg);
  }

  @Override
  protected void mostraIcona() {}
}
