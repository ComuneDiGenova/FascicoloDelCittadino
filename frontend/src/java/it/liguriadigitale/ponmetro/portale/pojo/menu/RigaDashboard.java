package it.liguriadigitale.ponmetro.portale.pojo.menu;

import java.util.List;
import org.apache.wicket.util.io.IClusterable;

public class RigaDashboard implements IClusterable {

  private static final long serialVersionUID = 1L;

  private List<Dashboard> listaDashboard;

  public List<Dashboard> getListaDashboard() {
    return listaDashboard;
  }

  public void setListaDashboard(List<Dashboard> listaDashboard) {
    this.listaDashboard = listaDashboard;
  }
}
