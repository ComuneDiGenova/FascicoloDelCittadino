package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.extend;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatSez;
import java.util.ArrayList;
import java.util.List;

public class CfgTCatSezEx extends CfgTCatSez {

  private static final long serialVersionUID = 2140467502059992515L;

  private List<CfgTCatComp> listaComparti;

  public CfgTCatSezEx() {
    super();
    listaComparti = new ArrayList<>();
  }

  public CfgTCatSezEx(List<CfgTCatComp> listaComparti) {
    super();
    this.listaComparti = listaComparti;
  }

  public List<CfgTCatComp> getListaComparti() {
    return listaComparti;
  }

  public void setListaComparti(List<CfgTCatComp> listaComparti) {
    this.listaComparti = listaComparti;
  }

  public List<CfgTCatComp> addToListaComparti(CfgTCatComp cfgTCatCompToAdd) {
    listaComparti.add(cfgTCatCompToAdd);
    return getListaComparti();
  }

  @Override
  public String toString() {
    return "CfgTCatSezEx [listaComparti="
        + listaComparti
        + ", toString()="
        + super.toString()
        + "]";
  }
}
