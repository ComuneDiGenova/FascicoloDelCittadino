package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.mobilita.widget;

import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;

public class BottomWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -1603617571589424627L;

  public BottomWidget(POSIZIONE posizione) {
    super(posizione);
    fillDati("");
  }

  @Override
  protected void mostraTestoWidget() {}

  @Override
  protected void mostraIcona() {}
}
