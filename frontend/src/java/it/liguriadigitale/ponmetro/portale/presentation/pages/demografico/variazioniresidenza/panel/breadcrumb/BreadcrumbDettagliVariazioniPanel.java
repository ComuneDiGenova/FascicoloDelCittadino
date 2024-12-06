package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.panel.breadcrumb;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;

public class BreadcrumbDettagliVariazioniPanel extends BasePanel {

  private static final long serialVersionUID = 7864184449362115993L;

  public BreadcrumbDettagliVariazioniPanel(String id) {
    super(id);

    Utente utente = getUtente();
    String io = getString("BreadcrumbDettagliVariazioniPanel.io").concat(" ");
    Label ioUtente =
        new Label("ioUtente", io.concat(utente.getNome().concat(" ").concat(utente.getCognome())));
    addOrReplace(ioUtente);
  }

  @Override
  public void fillDati(Object dati) {}
}
