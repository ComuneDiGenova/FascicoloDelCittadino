package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.modal;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Immobile;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;

@SuppressWarnings("hiding")
public class ModalEliminaImmobili<Immobile> extends Modal<Immobile> {

  private static final long serialVersionUID = 1989876546136548979L;

  WebMarkupContainer dialog = (WebMarkupContainer) get("dialog");

  public ModalEliminaImmobili(String markupId) {
    super(markupId);
    // TODO Auto-generated constructor stub

    setHeaderVisible(false);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  public MarkupContainer myAdd(Component... children) {
    return dialog.addOrReplace(children);
  }
}
