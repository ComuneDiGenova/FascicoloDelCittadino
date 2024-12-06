package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.mobilita.widget;

import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;

public class TopWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -8811645327993474295L;

  public TopWidget(POSIZIONE posizione) {
    super(posizione);
    fillDati("");
  }

  @Override
  protected void mostraTestoWidget() {}

  @Override
  protected void mostraIcona() {}
}
