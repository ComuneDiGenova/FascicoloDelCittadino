package it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni;

import java.io.Serializable;
import java.util.List;

public class ISEEMin implements Serializable {

  private static final long serialVersionUID = -7129998907870868316L;
  private List<ComponenteMinorenne> componenteMinorenne;

  public List<ComponenteMinorenne> getComponenteMinorenne() {
    return componenteMinorenne;
  }

  public void setComponenteMinorenne(List<ComponenteMinorenne> componenteMinorenne) {
    this.componenteMinorenne = componenteMinorenne;
  }

  @Override
  public String toString() {
    return "ISEEMin [componenteMinorenne=" + componenteMinorenne + "]";
  }
}
