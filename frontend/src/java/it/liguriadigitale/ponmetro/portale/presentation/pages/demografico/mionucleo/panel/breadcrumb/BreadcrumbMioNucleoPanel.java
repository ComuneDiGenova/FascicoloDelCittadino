package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mionucleo.panel.breadcrumb;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;

public class BreadcrumbMioNucleoPanel extends BasePanel {

  private static final long serialVersionUID = -6673669877816552415L;

  public BreadcrumbMioNucleoPanel(String id) {
    super(id);

    Utente utente = getUtente();
    String io = "io ";
    Label ioUtente =
        new Label("ioUtente", io.concat(utente.getNome().concat(" ").concat(utente.getCognome())));
    add(ioUtente);
  }

  @Override
  public void fillDati(Object dati) {}
}
