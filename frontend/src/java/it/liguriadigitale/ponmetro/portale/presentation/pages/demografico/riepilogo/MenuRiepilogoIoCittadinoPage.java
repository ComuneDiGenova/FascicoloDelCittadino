package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.riepilogo;

import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo.MenuRiepilogoDinamicoPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class MenuRiepilogoIoCittadinoPage extends MenuRiepilogoDinamicoPage {

  private static final long serialVersionUID = -3716531588435429409L;

  public MenuRiepilogoIoCittadinoPage(PageParameters parameters) {
    super(parameters);
  }

  public MenuRiepilogoIoCittadinoPage() {
    super("1");
    log.debug("MenuRiepilogoIoCittadinoPage1");
  }

  private static PageParameters creaParametri() {
    PageParameters parameters = new PageParameters();
    parameters.set(GloboPathParametersName.ID_SEZIONE.toString(), "1");
    return parameters;
  }
}
