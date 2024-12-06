package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class RicercaVerbaleForm extends AbstracFrameworkForm<Verbale> {

  private static final long serialVersionUID = -2628008252302600356L;

  public RicercaVerbaleForm(String id, Verbale model, Panel panel) {
    super(id, model);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void addElementiForm() {
    TextField numeroAvviso =
        new TextField("numeroAvviso", new PropertyModel(getModelObject(), "numeroAvviso"));
    numeroAvviso.setRequired(true);
    add(numeroAvviso);
  }
}
