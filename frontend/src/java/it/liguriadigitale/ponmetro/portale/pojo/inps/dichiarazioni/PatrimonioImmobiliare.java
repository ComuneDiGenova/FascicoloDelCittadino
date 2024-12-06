package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;
import java.util.List;

public class PatrimonioImmobiliare implements Serializable {

  private static final long serialVersionUID = 5643598348371346584L;
  private List<Patrimonio> Patrimonio;

  public List<Patrimonio> getPatrimonio() {
    return Patrimonio;
  }

  public void setPatrimonio(List<Patrimonio> patrimonio) {
    Patrimonio = patrimonio;
  }

  @Override
  public String toString() {
    return "PatrimonioImmobiliare [Patrimonio=" + Patrimonio + "]";
  }
}
