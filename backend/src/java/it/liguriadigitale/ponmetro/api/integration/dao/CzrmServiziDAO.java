package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CzrmServizi
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2022-12-15T10:45:51.334
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmServizi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CzrmServiziDAO extends AbstractSearchDAO {

  private CzrmServizi czrmservizi;

  public CzrmServiziDAO(CzrmServizi czrmservizi) {
    super();
    this.czrmservizi = czrmservizi;
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CZRM_SERVIZI";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {}

  public Object getFromResultSet(ResultSet r) throws SQLException {
    CzrmServizi obj = new CzrmServizi();

    obj.setServizioValue(r.getString("SERVIZIO_VALUE"));
    obj.setServizioText(r.getString("SERVIZIO_TEXT"));
    obj.setSottoFascicoloValue(r.getString("SOTTO_FASCICOLO_VALUE"));
    obj.setSottoFascicoloId(r.getLong("SOTTO_FASCICOLO_ID"));
    if (r.wasNull()) {
      obj.setSottoFascicoloId(null);
    }

    return obj;
  }
}
