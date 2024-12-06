package it.liguriadigitale.ponmetro.api.integration.dao.query;

import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.SottopagineDisponibili;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ElencoCfgTCatFunzPagineDAO extends AbstractSearchDAO {

  @Override
  protected Object getFromResultSet(ResultSet r) throws SQLException {
    SottopagineDisponibili obj = new SottopagineDisponibili();
    obj.setIdFunzPadre(r.getLong("ID_FUNZ_PADRE"));
    obj.setClassePaginaStd(r.getString("CLASSE_PAGINA_STD"));
    obj.setClassePaginaStdPadre(r.getString("CLASSE_PADRE"));
    obj.setIdFunzPadre(r.getLong("ID_FUNZ_PADRE"));
    obj.setIdPagina(r.getLong("ID"));
    obj.setNomePaginaNelPath(r.getString("ID_PAGINA"));
    return obj;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql =
        "select f.classe_pagina_std CLASSE_PADRE,f.id_pagina mount_padre, t.classe_pagina_std, t.id_pagina, t.id, t.id_funz ID_FUNZ_PADRE"
            + " from cfg_t_cat_funz_pagine t"
            + " inner join cfg_t_cat_funz f on t.id_funz=f.id_funz";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement arg0) throws SQLException {}
}
