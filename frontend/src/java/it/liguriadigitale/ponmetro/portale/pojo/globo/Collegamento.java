package it.liguriadigitale.ponmetro.portale.pojo.globo;

import java.io.Serializable;

public class Collegamento implements Serializable {

  private static final long serialVersionUID = -7768693805757295938L;

  private String path;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  @Override
  public String toString() {
    return "Collegamento [path=" + path + "]";
  }
}
