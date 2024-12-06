package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.riepilogo;

import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo.MenuRiepilogoDinamicoPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class MenuRiepilogoTributiPage extends MenuRiepilogoDinamicoPage {

  private static final long serialVersionUID = -3716531588435429409L;

  public MenuRiepilogoTributiPage(PageParameters parameters) {
    super(parameters);
  }

  public MenuRiepilogoTributiPage() {
    super("4");
  }

  @SuppressWarnings("unused")
  private static PageParameters creaParametri() {
    PageParameters parameters = new PageParameters();
    parameters.set(GloboPathParametersName.ID_SEZIONE.toString(), "1");
    return parameters;
  }
}
