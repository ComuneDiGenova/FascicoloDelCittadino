package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.prenotazione.form;

import it.liguriadigitale.ponmetro.scuola.cedole.model.Cartolibreria;
import java.io.Serializable;

public class Prenotazione implements Serializable {

  private static final long serialVersionUID = -1556134721487175673L;

  private Cartolibreria comboCartolibreria;

  public Prenotazione() {
    super();
    comboCartolibreria = new Cartolibreria();
  }

  public Cartolibreria getComboCartolibreria() {
    return comboCartolibreria;
  }

  public void setComboCartolibreria(Cartolibreria comboCartolibreria) {
    this.comboCartolibreria = comboCartolibreria;
  }
}
