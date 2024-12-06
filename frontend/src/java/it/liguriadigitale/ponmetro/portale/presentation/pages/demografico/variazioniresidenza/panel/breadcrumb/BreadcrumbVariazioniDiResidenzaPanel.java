package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.panel.breadcrumb;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;

public class BreadcrumbVariazioniDiResidenzaPanel extends BasePanel {

  private static final long serialVersionUID = 1334198713573467131L;

  public BreadcrumbVariazioniDiResidenzaPanel(String id) {
    super(id);

    Utente utente = getUtente();
    String io = getString("BreadcrumbVariazioniDiResidenzaPanel.io").concat(" ");
    Label ioUtente =
        new Label("ioUtente", io.concat(utente.getNome().concat(" ").concat(utente.getCognome())));
    addOrReplace(ioUtente);
  }

  @Override
  public void fillDati(Object dati) {}
}
