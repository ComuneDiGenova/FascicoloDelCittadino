package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.common.panel.modali;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class ModaleEliminaPraticaPanel<T> extends Modal<T> {

  private static final long serialVersionUID = -8055170148573264591L;

  protected Log log = LogFactory.getLog(this.getClass());

  protected VariazioniResidenza variazioniResidenza;

  WebMarkupContainer dialog = (WebMarkupContainer) get("dialog");

  public ModaleEliminaPraticaPanel(String id) {
    super(id);

    setHeaderVisible(false);
  }

  public ModaleEliminaPraticaPanel(String id, VariazioniResidenza variazioniResidenza) {
    super(id);

    this.variazioniResidenza = variazioniResidenza;

    setHeaderVisible(false);
  }

  public MarkupContainer myAdd(Component... children) {
    return dialog.addOrReplace(children);
  }
}
