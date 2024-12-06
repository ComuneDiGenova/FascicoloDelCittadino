package it.liguriadigitale.ponmetro.portale.presentation.components.panel;

public abstract class PortaleInnerPanel extends PortalePanel {

  private static final long serialVersionUID = -6833857256064971422L;

  public PortaleInnerPanel(final String id) {
    super(id);
  }

  public abstract void fillDati(final Object dati);
}
