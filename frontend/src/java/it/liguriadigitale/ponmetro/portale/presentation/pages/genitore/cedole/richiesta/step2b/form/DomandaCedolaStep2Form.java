package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.richiesta.step2b.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.scuola.cedole.model.DomandaCedola;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class DomandaCedolaStep2Form extends AbstracFrameworkForm<DomandaCedola> {

  private static final long serialVersionUID = 7384393816597461769L;

  private WebMarkupContainer container;

  public DomandaCedolaStep2Form(String id, DomandaCedola model) {
    super(id, model);
  }

  @Override
  public void addElementiForm() {}
}
