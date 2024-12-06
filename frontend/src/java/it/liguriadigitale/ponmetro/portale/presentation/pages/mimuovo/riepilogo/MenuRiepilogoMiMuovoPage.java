package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.riepilogo;

import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo.MenuRiepilogoDinamicoPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class MenuRiepilogoMiMuovoPage extends MenuRiepilogoDinamicoPage {

  private static final long serialVersionUID = -3716531588435429409L;

  public MenuRiepilogoMiMuovoPage(PageParameters parameters) {
    super(parameters);
  }

  public MenuRiepilogoMiMuovoPage() {
    super("3");
    log.debug("MenuRiepilogoMiMuovoPage");
  }

  private static PageParameters creaParametri() {
    PageParameters parameters = new PageParameters();
    parameters.set(GloboPathParametersName.ID_SEZIONE.toString(), "3");
    return parameters;
  }
}
