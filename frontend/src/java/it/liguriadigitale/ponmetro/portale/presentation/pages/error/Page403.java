package it.liguriadigitale.ponmetro.portale.presentation.pages.error;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;

public class Page403 extends WebPage {

  private static final long serialVersionUID = 7359051219473706565L;

  public Page403() {
    super();
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
