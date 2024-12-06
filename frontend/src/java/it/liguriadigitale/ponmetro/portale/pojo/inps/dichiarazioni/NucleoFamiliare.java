package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;
import java.util.List;

public class NucleoFamiliare implements Serializable {

  private static final long serialVersionUID = -5126885722336826014L;

  private List<ComponenteNucleo> componenteNucleo;

  public List<ComponenteNucleo> getComponenteNucleo() {
    return componenteNucleo;
  }

  public void setComponenteNucleo(List<ComponenteNucleo> componenteNucleo) {
    this.componenteNucleo = componenteNucleo;
  }

  @Override
  public String toString() {
    return "NucleoFamiliare [componenteNucleo=" + componenteNucleo + "]";
  }
}
