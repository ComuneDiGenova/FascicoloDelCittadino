package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.riepilogo.panel.breadcrumb;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;

public class BreadcrumbRiepilogoMieiDatiPanel extends BasePanel {

  private static final long serialVersionUID = 989022914925451312L;

  public BreadcrumbRiepilogoMieiDatiPanel(String id) {
    super(id);
    Utente utente = getUtente();
    String io = "io ";
    Label ioUtente = new Label("ioUtente", io.concat(getNominativoCapoFamiglia(utente)));
    add(ioUtente);
  }

  @Override
  public void fillDati(Object dati) {}

  private String getNominativoCapoFamiglia(Utente capoFamiglia) {
    StringBuilder builder = new StringBuilder();
    return builder
        .append(capoFamiglia.getCognome())
        .append(" ")
        .append(capoFamiglia.getNome())
        .toString();
  }
}
