package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mioisee.pojo;

import it.liguriadigitale.ponmetro.portale.pojo.enums.IndicatoreISEEEnum;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import java.io.Serializable;

public class DatiMioISEE implements Serializable {

  private static final long serialVersionUID = 1L;

  ComponenteNucleo componenteNucleo;
  IndicatoreISEEEnum indicatoreISEE;

  public ComponenteNucleo getComponenteNucleo() {
    return componenteNucleo;
  }

  public void setComponenteNucleo(ComponenteNucleo componenteNucleo) {
    this.componenteNucleo = componenteNucleo;
  }

  public IndicatoreISEEEnum getIndicatoreISEE() {
    return indicatoreISEE;
  }

  public void setIndicatoreISEE(IndicatoreISEEEnum indicatoreISEE) {
    this.indicatoreISEE = indicatoreISEE;
  }

  @Override
  public String toString() {
    return "DatiMioISEE [componenteNucleo="
        + componenteNucleo
        + ", indicatoreISEE="
        + indicatoreISEE
        + "]";
  }
}
