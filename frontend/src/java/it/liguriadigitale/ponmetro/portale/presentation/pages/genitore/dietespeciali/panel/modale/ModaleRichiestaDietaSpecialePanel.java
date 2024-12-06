package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel.modale;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpecialeValida;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class ModaleRichiestaDietaSpecialePanel<T> extends Modal<T> {

  private static final long serialVersionUID = -4665052001193015582L;

  protected Log log = LogFactory.getLog(this.getClass());

  protected DatiDietaSpecialeValida dietaValida;

  WebMarkupContainer dialog = (WebMarkupContainer) get("dialog");

  public ModaleRichiestaDietaSpecialePanel(String id) {
    super(id);
  }

  public ModaleRichiestaDietaSpecialePanel(String id, DatiDietaSpecialeValida dietaValida) {
    super(id);

    this.dietaValida = dietaValida;
    setHeaderVisible(false);

    show(true);
    showImmediately();

    setFadeIn(false);

    setBackdrop(Backdrop.STATIC);

    setVisible(true);
  }

  public MarkupContainer myAdd(Component... children) {
    return dialog.addOrReplace(children);
  }
}
