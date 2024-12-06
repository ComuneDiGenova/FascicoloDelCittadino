package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto;

import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.components.nucleo.ComponenteNucleoAmt;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NucleoFamiglia implements Serializable {

  private static final long serialVersionUID = -1006203084768469078L;

  private ComponenteNucleoAmt comboNucleo;

  private List<ComponenteNucleo> listaCfNucleo;

  public ComponenteNucleoAmt getComboNucleo() {
    return comboNucleo;
  }

  public void setComboNucleo(ComponenteNucleoAmt comboNucleo) {
    this.comboNucleo = comboNucleo;
  }

  public List<ComponenteNucleo> getListaCfNucleo() {
    return listaCfNucleo;
  }

  public void setListaCfNucleo(List<ComponenteNucleo> listaCfNucleo) {
    this.listaCfNucleo = listaCfNucleo;
  }

  public void addListaCfNucleo(List<ComponenteNucleo> listaCfNucleo) {
    if (this.listaCfNucleo == null) {
      this.listaCfNucleo = new ArrayList<>();
    }
    this.listaCfNucleo.addAll(listaCfNucleo);
  }

  @Override
  public String toString() {
    return "NucleoFamiglia [comboNucleo=" + comboNucleo + ", listaCfNucleo=" + listaCfNucleo + "]";
  }
}
