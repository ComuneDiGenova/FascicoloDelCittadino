package it.liguriadigitale.ponmetro.portale.presentation.pages.geoworks;

import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;

public class DomandaSpecchiParaboliciDossiArtificialiPage extends WebPage {

  private static final long serialVersionUID = -543091800987985232L;

  public DomandaSpecchiParaboliciDossiArtificialiPage(GeoworksServizi servizio) {
    super();

    String URL_RICHIESTA_SPECCHI_DOSSI_ARTIFICIALI =
        BaseServiceImpl.API_GEOWORKS_DOMANDA
            + servizio.getIdServizio()
            + "&provider=SIRACCGeExternal";

    throw new RestartResponseAtInterceptPageException(
        new RedirectPage(URL_RICHIESTA_SPECCHI_DOSSI_ARTIFICIALI));
  }
}
