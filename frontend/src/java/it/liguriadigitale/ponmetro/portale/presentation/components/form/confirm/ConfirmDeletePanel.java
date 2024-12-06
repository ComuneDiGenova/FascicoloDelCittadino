package it.liguriadigitale.ponmetro.portale.presentation.components.form.confirm;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Pannello di conferma riutilizzabile.
 *
 * <p>Non usa una modale, ma va a sostituire un pannello temporaneo.
 *
 * <p>P.es utilizzare la classe EmptyPanel:
 *
 * <pre>
 *  ...
 *  Panel panelTemporaneo = new EmptyPanel("conferma");
 *  ...
 * </pre>
 *
 * la classe ConfirmDeletePanel ha il suo markup file associato e un file di properties contenente i
 * valori da usare per le etichette dei bottoni.
 *
 * <p>Instanziare la classe ConfirmDeletePanel all'interno del metodo onSubmit di un AjaxButton.
 * Implementare i metodi astratti, per personalizzare il comportamento.
 *
 * <p>Confirm:
 *
 * <pre>
 * &#64;Override
 * protected void onConfirm(AjaxRequestTarget target) {
 * this.replaceWith(panelTemporaneo);
 * ...
 * [implementare il comportamento per es. salvataggio su DB]
 * ...
 * target.add(form, bottoneValida, feedback);
 * }
 * </pre>
 *
 * Cancel:
 *
 * <pre>
 * &#64;Override
 * protected void onCancel(AjaxRequestTarget target) {
 * 	bottoneValida.setEnabled(true);
 * 	this.replaceWith(panelTemporaneo);
 * 	target.add(form, bottoneValida);
 * }
 * </pre>
 *
 * @author Matteo Scaldaferri da un'idea di Ed Eustace letta qui:
 *     https://cwiki.apache.org/confluence/display/WICKET/Getting+user+confirmation
 */
public abstract class ConfirmDeletePanel extends Panel {

  private static final long serialVersionUID = -1464908654074441530L;

  public ConfirmDeletePanel(String id, String message) {
    super(id);

    add(new Label("message", message));
    add(
        new AjaxLink<Void>("confirm") {
          /** */
          private static final long serialVersionUID = 168768943246173187L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            onConfirm(target);
          }
        });
    add(
        new AjaxLink<Void>("cancel") {
          /** */
          private static final long serialVersionUID = -155622043820849868L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            onCancel(target);
          }
        });
  }

  protected abstract void onCancel(AjaxRequestTarget target);

  protected abstract void onConfirm(AjaxRequestTarget target);
}
