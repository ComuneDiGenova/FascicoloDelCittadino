package it.liguriadigitale.ponmetro.api.integration.dao.extend;

import it.liguriadigitale.ponmetro.api.integration.dao.CfgTCatFunzDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunz;
import java.util.List;

public class CfgTCatFunzDAOEx extends CfgTCatFunzDAO {

  protected List<CfgTCatFunz> listCfgtcatfunz;

  public CfgTCatFunzDAOEx(CfgTCatFunz cfgtcatfunz) {
    super(cfgtcatfunz);
  }

  public CfgTCatFunzDAOEx(List<CfgTCatFunz> listCfgtcatfunz) {
    super(listCfgtcatfunz.get(0));
    this.listCfgtcatfunz = listCfgtcatfunz;
  }
}
