package it.liguriadigitale.ponmetro.portale.pojo.cedole;

import it.liguriadigitale.ponmetro.scuola.cedole.model.DomandaCedola;
import java.io.Serializable;

public class RichiestaCedola implements Serializable {

  private static final long serialVersionUID = 8499295283178397189L;

  private CedoleMinore cedolaMinore;

  private DomandaCedola domanda;

  public CedoleMinore getCedolaMinore() {
    return cedolaMinore;
  }

  public void setCedolaMinore(CedoleMinore cedolaMinore) {
    this.cedolaMinore = cedolaMinore;
  }

  public DomandaCedola getDomanda() {
    return domanda;
  }

  public void setDomanda(DomandaCedola domanda) {
    this.domanda = domanda;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("RichiestaCedola [cedolaMinore=");
    builder.append(cedolaMinore);
    builder.append("]");
    return builder.toString();
  }
}
