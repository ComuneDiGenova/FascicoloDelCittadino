package it.liguriadigitale.ponmetro.portale.presentation.pages.propongo.riepilogo;

import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo.MenuRiepilogoDinamicoPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class RiepilogoPropongoPage extends MenuRiepilogoDinamicoPage {

  private static final long serialVersionUID = -5564968059506189020L;

  private static final String IO_PROPONGO = "ioPropongo";

  private RiepilogoPropongoPage(PageParameters parameters) {
    super(parameters);
  }

  public RiepilogoPropongoPage() {
    this(creaParametri());
  }

  private static PageParameters creaParametri() {
    PageParameters parameters = new PageParameters();
    parameters.set(GloboPathParametersName.NOME_SEZIONE.name(), IO_PROPONGO);
    return parameters;
  }
}
