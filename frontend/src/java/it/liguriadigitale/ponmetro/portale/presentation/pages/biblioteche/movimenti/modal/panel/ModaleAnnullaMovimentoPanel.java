package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.modal.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Movimento;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class ModaleAnnullaMovimentoPanel<T> extends Modal<T> {

  private static final long serialVersionUID = -2618272352095692062L;

  protected Log log = LogFactory.getLog(this.getClass());

  protected Movimento movimento;

  WebMarkupContainer dialog = (WebMarkupContainer) get("dialog");

  public ModaleAnnullaMovimentoPanel(String id) {
    super(id);
  }

  public ModaleAnnullaMovimentoPanel(String id, Movimento movimento) {
    super(id);

    this.movimento = movimento;
    setHeaderVisible(false);
  }

  public MarkupContainer myAdd(Component... children) {
    return dialog.addOrReplace(children);
  }
}
