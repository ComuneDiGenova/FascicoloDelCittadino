package it.liguriadigitale.ponmetro.portale.pojo.globo;

import java.io.Serializable;
import java.util.List;

public class Tag implements Serializable {

  private static final long serialVersionUID = 4096711947017230570L;

  private String id;
  private String name;
  private List<Tag> child;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Tag> getChild() {
    return child;
  }

  public void setChild(List<Tag> child) {
    this.child = child;
  }

  @Override
  public String toString() {
    return "Tag [id=" + id + ", name=" + name + ", child=" + child + "]";
  }
}
