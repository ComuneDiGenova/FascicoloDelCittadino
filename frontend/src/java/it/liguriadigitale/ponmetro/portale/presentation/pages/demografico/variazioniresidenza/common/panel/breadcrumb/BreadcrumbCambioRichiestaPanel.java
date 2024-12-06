package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.common.panel.breadcrumb;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;

public class BreadcrumbCambioRichiestaPanel extends BasePanel {

  private static final long serialVersionUID = 4662922100064967629L;

  public BreadcrumbCambioRichiestaPanel(String id, String nomePagina) {
    super(id);

    Utente utente = getUtente();
    String nome = "";
    String cognome = "";
    if (utente != null) {
      nome = utente.getNome();
      cognome = utente.getCognome();
    }

    StringBuilder builder = new StringBuilder();
    String inizioBreadCrumb = getString("BreadcrumbCambioRichiestaPanel.io");

    String labelUtente =
        builder
            .append(inizioBreadCrumb)
            .append(" ")
            .append(nome)
            .append(" ")
            .append(cognome)
            .toString();
    Label ioUtente = new Label("ioUtente", labelUtente);
    addOrReplace(ioUtente);

    Label currentPage = new Label("currentPage", nomePagina);
    addOrReplace(currentPage);
  }

  @Override
  public void fillDati(Object dati) {}
}
