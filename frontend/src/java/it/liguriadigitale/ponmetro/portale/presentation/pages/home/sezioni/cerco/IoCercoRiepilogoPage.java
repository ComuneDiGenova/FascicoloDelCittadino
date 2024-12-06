package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.cerco;

import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo.MenuRiepilogoDinamicoPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class IoCercoRiepilogoPage extends MenuRiepilogoDinamicoPage {

  private static final String IO_CERCO = "ioRichiedo";
  private static final long serialVersionUID = -6074748446212822569L;

  private IoCercoRiepilogoPage(PageParameters parameters) {
    super(parameters);
  }

  public IoCercoRiepilogoPage() {
    this(creaParametri());
  }

  private static PageParameters creaParametri() {
    PageParameters parameters = new PageParameters();
    parameters.set(GloboPathParametersName.NOME_SEZIONE.name(), IO_CERCO);
    return parameters;
  }
}
