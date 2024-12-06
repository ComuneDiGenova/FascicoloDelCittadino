package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.breadcrumb;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;

public class BreadcrumbCertificatiAnagrafePanel extends BasePanel {

  private static final long serialVersionUID = -9087352375630473019L;

  public BreadcrumbCertificatiAnagrafePanel(String id) {
    super(id);

    Utente utente = getUtente();
    String io = getString("BreadcrumbCertificatiAnagrafePanel.io").concat(" ");
    Label ioUtente =
        new Label("ioUtente", io.concat(utente.getNome().concat(" ").concat(utente.getCognome())));
    addOrReplace(ioUtente);
  }

  @Override
  public void fillDati(Object dati) {}
}
