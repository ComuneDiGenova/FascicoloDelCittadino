package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.panel.modale;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegatiImmigrazione;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class ModaleCancellaIndividuoCaricatoPanel<T> extends Modal<T> {

  private static final long serialVersionUID = -752627024291505649L;

  protected Log log = LogFactory.getLog(this.getClass());

  protected IndividuiCollegatiImmigrazione individuo;

  WebMarkupContainer dialog = (WebMarkupContainer) get("dialog");

  public ModaleCancellaIndividuoCaricatoPanel(String id) {
    super(id);

    setHeaderVisible(false);
  }

  public ModaleCancellaIndividuoCaricatoPanel(String id, IndividuiCollegatiImmigrazione individuo) {
    super(id);

    this.individuo = individuo;

    setHeaderVisible(false);
  }

  public MarkupContainer myAdd(Component... children) {
    return dialog.addOrReplace(children);
  }
}
