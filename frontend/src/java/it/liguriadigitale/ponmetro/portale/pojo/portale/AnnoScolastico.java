package it.liguriadigitale.ponmetro.portale.pojo.portale;

import java.io.Serializable;

public class AnnoScolastico implements Serializable {

  private static final long serialVersionUID = -2069465961582399266L;

  private String value;
  private String description;

  public AnnoScolastico(final String value, final String description) {
    this.value = value;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
