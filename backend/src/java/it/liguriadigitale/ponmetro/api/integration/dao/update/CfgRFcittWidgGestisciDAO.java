package it.liguriadigitale.ponmetro.api.integration.dao.update;

import it.liguriadigitale.ponmetro.api.integration.dao.CfgRFcittWidgDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgRFcittWidg;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CfgRFcittWidgGestisciDAO extends CfgRFcittWidgDAO {

  public CfgRFcittWidgGestisciDAO(CfgRFcittWidg cfgrfcittwidg) {
    super(cfgrfcittwidg);
  }

  private String getSqlUpdateDiChiusuraRecord() {
    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgrfcittwidg.getIdFcitt() != null) sql += " and ID_FCITT  = ? ";
    if (cfgrfcittwidg.getIdWidg() != null) sql += " and ID_WIDG  = ? ";
    sql += " and DATA_ASSOCIAZIONE_FCITT_WIDG IS NOT NULL ";
    sql += " and DATA_DEASSOCIAZIONE_FCITT_WIDG IS NULL ";
    return sql;
  }

  @Override
  protected String getSqlUpdateByWhere() {
    String sql = getSqlUpdateDiChiusuraRecord();
    return sql;
  }

  @Override
  protected String getUpdateFields() {
    String sql = " UPDATE CFG_R_FCITT_WIDG set DATA_DEASSOCIAZIONE_FCITT_WIDG = SYSDATE ";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgrfcittwidg.getIdFcitt());
    st.setLong(index++, cfgrfcittwidg.getIdWidg());
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgrfcittwidg.getIdFcitt());
    st.setLong(index++, cfgrfcittwidg.getIdWidg());
    st.setLong(index++, cfgrfcittwidg.getIdFcitt());
    st.setLong(index++, cfgrfcittwidg.getIdWidg());
  }

  @Override
  protected String getSqlInsertObject() {
    // Inserisco solo se ci sono record chiusi e un solo record aperto
    return "  INSERT INTO CFG_R_FCITT_WIDG ( ID_FCITT, ID_WIDG ) "
        + " ( "
        + "    SELECT DISTINCT ?, A.ID_WIDG "
        + "    FROM CFG_T_CAT_WIDG A "
        + "    WHERE A.ID_WIDG = ? AND "
        +
        // " --non deve avere record aperti " +
        "	 0 = ( "
        + "	    SELECT COUNT(*) "
        + "	    FROM  CFG_R_FCITT_WIDG "
        + "	    WHERE ID_FCITT = ? AND "
        + "	    ID_WIDG = ? AND "
        + "	    CFG_R_FCITT_WIDG.DATA_ASSOCIAZIONE_FCITT_WIDG IS NOT NULL AND "
        + "	    CFG_R_FCITT_WIDG.DATA_DEASSOCIAZIONE_FCITT_WIDG IS NULL ) "
        + " ) ";
    // default:
    // DATA_ASSOCIAZIONE_FCITT_WIDG = sysdate
    // DATA_DEASSOCIAZIONE_FCITT_WIDG = NULL
    // FLAG_ABILITAZIONE = 1
  }
}
