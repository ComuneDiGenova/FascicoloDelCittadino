package it.liguriadigitale.ponmetro.portale.presentation.pages.home.landing;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

public class GetTokenPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1437341532196533213L;
  private static final String AUTHORIZATION_CODE = "authorizationCode";

  @SuppressWarnings("unused")
  public GetTokenPage(final PageParameters parameters) {
    super();

    final StringValue cfValue = parameters.get(AUTHORIZATION_CODE);
    // Pagina in cui si atterra dopo il login su SPID
    // Devo chiamare il servizio di WSO2 getToken e memorizzarlo in sessione
    // poi mando su LandingPage
  }
}
