package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.ponmetro.serviziristorazione.model.GiornoRientro.RientroEnum;
import java.io.Serializable;

public class Giorni implements Serializable {

  private RientroEnum rientro;

  private boolean selezionato;

  public boolean isSelezionato() {
    return selezionato;
  }

  public void setSelezionato(boolean selezionato) {
    this.selezionato = selezionato;
  }

  public RientroEnum getRientro() {
    return rientro;
  }

  public void setRientro(RientroEnum rientro) {
    this.rientro = rientro;
  }
}
