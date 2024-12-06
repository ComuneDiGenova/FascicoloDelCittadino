package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.nuova.common;

import java.time.LocalDate;
import org.apache.wicket.markup.html.panel.Panel;

public class AvvertenzaConScadenzaPanel extends Panel {

  private static final long serialVersionUID = -3707566625250821477L;
  private LocalDate dataScadenza = LocalDate.of(2023, 1, 1);

  public AvvertenzaConScadenzaPanel() {
    super("avvertenza");
  }

  @Override
  protected void onConfigure() {
    super.onConfigure();
    setVisible(LocalDate.now().isBefore(dataScadenza));
  }
}
