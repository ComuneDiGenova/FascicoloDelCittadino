package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import org.apache.wicket.markup.html.form.Form;

public class ConfermaDatiForm extends AbstracFrameworkForm<VariazioniResidenza> {

  private static final long serialVersionUID = 7201104626323803183L;

  public Form<?> formUploadDocumenti;

  public ConfermaDatiForm(String id, VariazioniResidenza model) {
    super(id, model);

    addElementiForm();
  }

  @Override
  public void addElementiForm() {}
}
