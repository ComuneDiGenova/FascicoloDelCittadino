package it.liguriadigitale.ponmetro.api.integration.dao.query;

import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunzioniInRealizzazioneDAO extends AbstractSearchDAO {

  private ChiaveValore chiaveValore;

  public FunzioniInRealizzazioneDAO(ChiaveValore chiaveValore) {
    super();
    this.chiaveValore = chiaveValore;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT CKV.* " + "FROM CFG_KEY_VALUE CKV " + "WHERE 1=1 ";
    if (chiaveValore != null && chiaveValore.getChiave() != null) {
      sql += " AND CKV.CFG_KEY LIKE ? ";
    }

    log.debug("CP sql query in realizzazione = " + sql);

    return sql;
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    ChiaveValore obj = new ChiaveValore();
    obj.setChiave(r.getString("CFG_KEY"));
    obj.setValore(r.getString("CFG_VALUE"));
    return obj;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (chiaveValore != null && chiaveValore.getChiave() != null) {
      st.setString(index++, chiaveValore.getChiave());
    }
  }
}
