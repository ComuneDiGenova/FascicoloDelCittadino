package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CzrmSottoFascicoli
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2022-12-15T10:45:51.353
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmSottoFascicoli;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CzrmSottoFascicoliDAO extends AbstractSearchDAO {

  private CzrmSottoFascicoli czrmsottofascicoli;

  public CzrmSottoFascicoliDAO(CzrmSottoFascicoli czrmsottofascicoli) {
    super();
    this.czrmsottofascicoli = czrmsottofascicoli;
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CZRM_SOTTO_FASCICOLI";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {}

  public Object getFromResultSet(ResultSet r) throws SQLException {
    CzrmSottoFascicoli obj = new CzrmSottoFascicoli();

    obj.setSottoFascicoloValue(r.getString("SOTTO_FASCICOLO_VALUE"));
    obj.setSottoFascicoloText(r.getString("SOTTO_FASCICOLO_TEXT"));
    return obj;
  }
}
