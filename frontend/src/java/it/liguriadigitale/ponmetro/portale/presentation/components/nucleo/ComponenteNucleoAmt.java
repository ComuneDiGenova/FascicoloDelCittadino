package it.liguriadigitale.ponmetro.portale.presentation.components.nucleo;

import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import java.io.Serializable;

public class ComponenteNucleoAmt implements Serializable {

  private static final long serialVersionUID = 6878001428113447699L;

  ComponenteNucleo componenteNucleo;

  String tutti;

  public ComponenteNucleo getComponenteNucleo() {
    return componenteNucleo;
  }

  public void setComponenteNucleo(ComponenteNucleo componenteNucleo) {
    this.componenteNucleo = componenteNucleo;
  }

  public String getTutti() {
    return tutti;
  }

  public void setTutti(String tutti) {
    this.tutti = tutti;
  }

  @Override
  public String toString() {
    return "ComponenteNucleoAmt [componenteNucleo=" + componenteNucleo + ", tutti=" + tutti + "]";
  }
}
