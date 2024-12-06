package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.abito;

import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo.MenuRiepilogoDinamicoPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class IoAbitoRiepilogoPage extends MenuRiepilogoDinamicoPage {

  private static final String IO_ABITO = "ioAbito";
  private static final long serialVersionUID = -6074748446212822569L;

  private IoAbitoRiepilogoPage(PageParameters parameters) {
    super(parameters);
  }

  public IoAbitoRiepilogoPage() {
    this(creaParametri());
  }

  private static PageParameters creaParametri() {
    PageParameters parameters = new PageParameters();
    parameters.set(GloboPathParametersName.NOME_SEZIONE.name(), IO_ABITO);
    return parameters;
  }
}
