package it.liguriadigitale.ponmetro.portale.pojo.dietespeciali;

import it.liguriadigitale.ponmetro.serviziristorazione.model.GiornoRientro.RientroEnum;
import java.io.Serializable;

public class GiorniRientroScuola implements Serializable {

  private static final long serialVersionUID = -2882454237376113090L;

  private RientroEnum giornoRientro;

  private boolean selezionato;

  public RientroEnum getGiornoRientro() {
    return giornoRientro;
  }

  public void setGiornoRientro(RientroEnum giornoRientro) {
    this.giornoRientro = giornoRientro;
  }

  public boolean isSelezionato() {
    return selezionato;
  }

  public void setSelezionato(boolean selezionato) {
    this.selezionato = selezionato;
  }

  @Override
  public String toString() {
    return "GiorniRientroScuola [giornoRientro="
        + giornoRientro
        + ", selezionato="
        + selezionato
        + "]";
  }
}
