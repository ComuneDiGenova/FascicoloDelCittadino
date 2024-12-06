package it.liguriadigitale.ponmetro.portale.presentation.pages.segnalazioni.riepilogo;

import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo.MenuRiepilogoDinamicoPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class RiepilogoSegnalazioniPage extends MenuRiepilogoDinamicoPage {

  private static final String IO_SEGNALO = "ioSegnalo";
  private static final long serialVersionUID = -6074748446212822569L;

  private RiepilogoSegnalazioniPage(PageParameters parameters) {
    super(parameters);
  }

  public RiepilogoSegnalazioniPage() {
    this(creaParametri());
  }

  private static PageParameters creaParametri() {
    PageParameters parameters = new PageParameters();
    parameters.set(GloboPathParametersName.NOME_SEZIONE.name(), IO_SEGNALO);
    return parameters;
  }
}
