package it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati;

import java.io.Serializable;
import java.util.List;

public class DocumentiAllegati implements Serializable {

  private static final long serialVersionUID = -1615706336403043384L;

  List<AllegatoPermessiPersonalizzati> listaAllegatoPermessiPersonalizzati;

  public List<AllegatoPermessiPersonalizzati> getListaAllegatoPermessiPersonalizzati() {
    return listaAllegatoPermessiPersonalizzati;
  }

  public void setListaAllegatoPermessiPersonalizzati(
      List<AllegatoPermessiPersonalizzati> listaAllegatoPermessiPersonalizzati) {
    this.listaAllegatoPermessiPersonalizzati = listaAllegatoPermessiPersonalizzati;
  }
}
