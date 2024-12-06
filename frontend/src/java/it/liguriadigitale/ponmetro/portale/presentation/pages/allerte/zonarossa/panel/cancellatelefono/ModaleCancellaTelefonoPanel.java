package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.cancellatelefono;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class ModaleCancellaTelefonoPanel<T> extends Modal<T> {

  private static final long serialVersionUID = -3738032977106181786L;

  protected Log log = LogFactory.getLog(this.getClass());

  protected Contatto contatto;

  WebMarkupContainer dialog = (WebMarkupContainer) get("dialog");

  public ModaleCancellaTelefonoPanel(String id) {
    super(id);
  }

  public ModaleCancellaTelefonoPanel(String id, Contatto contatto) {
    super(id);

    this.contatto = contatto;
    setHeaderVisible(false);
  }

  public MarkupContainer myAdd(Component... children) {
    return dialog.addOrReplace(children);
  }
}
