package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaIstanza;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class RicercaIstanzaForm extends AbstracFrameworkForm<RicercaIstanza> {

  private static final long serialVersionUID = -2628008252302600356L;

  public RicercaIstanzaForm(String id, RicercaIstanza model, Panel panel) {
    super(id, model);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void addElementiForm() {

    TextField numeroProtocollo =
        new TextField("numeroProtocollo", new PropertyModel(getModelObject(), "numeroProtocollo"));

    /*
     * TextField numeroIstanza = new TextField("numeroIstanza", new
     * PropertyModel(getModelObject(), "numeroIstanza"));
     * log.debug("CP numero istanza " + numeroIstanza);
     * numeroIstanza.add(new OnChangeAjaxBehaviorSenzaIndicator() {
     *
     * private static final long serialVersionUID = -3191104071581235968L;
     *
     * @Override protected void onUpdate(AjaxRequestTarget target) { if
     * (!numeroIstanza.getValue().isEmpty() &&
     * numeroProtocollo.getValue().isEmpty()) {
     * numeroProtocollo.setRequired(true); } } });
     *
     * numeroProtocollo.setRequired(true); add(numeroIstanza);
     *
     */

    numeroProtocollo.setRequired(true);
    add(numeroProtocollo);
  }
}
