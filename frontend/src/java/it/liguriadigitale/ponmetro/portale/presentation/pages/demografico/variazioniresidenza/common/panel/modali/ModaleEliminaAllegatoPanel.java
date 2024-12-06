package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.common.panel.modali;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import it.liguriadigitale.ponmetro.servizianagrafici.model.DocumentoPratica;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class ModaleEliminaAllegatoPanel<T> extends Modal<T> {

  private static final long serialVersionUID = 5623019521049696182L;

  protected Log log = LogFactory.getLog(this.getClass());

  protected DocumentoPratica documento;

  WebMarkupContainer dialog = (WebMarkupContainer) get("dialog");

  public ModaleEliminaAllegatoPanel(String id) {
    super(id);
  }

  public ModaleEliminaAllegatoPanel(String id, DocumentoPratica documento) {
    super(id);

    this.documento = documento;
    setHeaderVisible(false);
  }

  public MarkupContainer myAdd(Component... children) {
    return dialog.addOrReplace(children);
  }
}
