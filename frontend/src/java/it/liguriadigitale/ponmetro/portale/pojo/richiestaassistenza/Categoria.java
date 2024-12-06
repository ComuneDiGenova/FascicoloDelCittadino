package it.liguriadigitale.ponmetro.portale.pojo.richiestaassistenza;

import java.io.Serializable;

public class Categoria implements Serializable {

  private static final long serialVersionUID = -5618167304768972724L;

  private String label;

  private String value;

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "Categoria [label=" + label + ", value=" + value + "]";
  }
}
