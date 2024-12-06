package it.liguriadigitale.ponmetro.portale.pojo.componentifascicolo;

import java.io.Serializable;

public class LabelValue implements Serializable {

  private static final long serialVersionUID = 1L;

  String id;
  String desc;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public String toString() {
    return "LabelValue [id=" + id + ", desc=" + desc + "]";
  }
}
