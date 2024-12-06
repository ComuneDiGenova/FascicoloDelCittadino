package it.liguriadigitale.ponmetro.portale.presentation.pages.error;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;

public class CittadinoMinoreDiEtaPage extends WebPage {

  private static final long serialVersionUID = 7439301135262467147L;

  public CittadinoMinoreDiEtaPage() {
    super();

    /*
     * Integer minimo = Integer.parseInt(BaseServiceImpl.MINORE_DI); String
     * minore = String.valueOf(minimo + 1); Label minoreDi = new
     * Label("minoreDi", new
     * StringResourceModel("CittadinoMinoreDiEtaPage.minore", this)
     * .setParameters(minore)); addOrReplace(minoreDi);
     */

  }

  @Override
  protected void configureResponse(WebResponse arg0) {
    super.configureResponse(arg0);
  }

  @Override
  public boolean isVersioned() {
    return false;
  }

  @Override
  public boolean isErrorPage() {
    return true;
  }
}
