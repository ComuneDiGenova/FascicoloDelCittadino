package it.liguriadigitale.ponmetro.portale.presentation.pages.messaggi.panel;

import it.liguriadigitale.ponmetro.messaggi.utente.model.DatiMessaggioUtente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;

public class SingoloMessaggioPanel extends BasePanel {

  private static final long serialVersionUID = -6835520584145959645L;

  public SingoloMessaggioPanel() {
    super("singoloMessaggioPanel");
  }

  @SuppressWarnings("unused")
  @Override
  public void fillDati(Object dati) {
    DatiMessaggioUtente messaggio = (DatiMessaggioUtente) dati;

    Label label =
        new Label(
            "testoStatico",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

    add(label);
  }
}
