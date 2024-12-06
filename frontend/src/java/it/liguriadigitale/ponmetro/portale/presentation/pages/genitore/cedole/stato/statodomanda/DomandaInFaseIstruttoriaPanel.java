package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.statodomanda;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import org.apache.wicket.markup.html.panel.Panel;

public class DomandaInFaseIstruttoriaPanel extends Panel {

  private static final long serialVersionUID = 1922276247258571694L;

  public DomandaInFaseIstruttoriaPanel(String id, Cedola cedola) {
    super(id);
    mostraElementi(cedola);
  }

  private void mostraElementi(Cedola cedola) {
    //
    add(new LocalDateLabel("data", cedola.getDataDomanda()));
  }
}
