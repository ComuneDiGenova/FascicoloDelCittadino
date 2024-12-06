package it.liguriadigitale.ponmetro.api.integration.dao.update;

import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgRFcittComp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgRFcittCompGestisciDAO extends AbstractTableDAO {

  private VCfgRFcittComp vcfgRFcittComp;

  public CfgRFcittCompGestisciDAO(VCfgRFcittComp vcfgRFcittComp) {
    this.vcfgRFcittComp = vcfgRFcittComp;
  }

  private String getSqlUpdateDiChiusuraRecord() {
    if (vcfgRFcittComp.getIdFcitt() == null) {
      return null;
    }
    String sql = getUpdateFields() + " WHERE 1=1 ";
    sql += " AND ID_FCITT = ? ";
    if (vcfgRFcittComp.getIdComp() != null) {
      sql += " AND ID_COMP = ? ";
    }
    if (vcfgRFcittComp.getIdSez() != null) {
      sql +=
          " AND ID_COMP IN ( "
              + " SELECT DISTINCT ID_COMP "
              + " FROM CFG_T_CAT_COMP "
              + " WHERE 1 = 1 AND FLAG_ABILITAZIONE = 1 AND ID_SEZ = ? )";
    }
    sql += " AND DATA_REGISTRAZ_FCITT_COMP IS NOT NULL ";
    sql += " AND DATA_DEREGISTRAZ_FCITT_COMP IS NULL ";
    return sql;
  }

  @Override
  protected String getSqlUpdateByWhere() {
    String sql = getSqlUpdateDiChiusuraRecord();
    return sql;
  }

  protected String getUpdateFields() {
    String sql = " UPDATE CFG_R_FCITT_COMP SET DATA_DEREGISTRAZ_FCITT_COMP = SYSDATE ";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    if (vcfgRFcittComp.getIdFcitt() == null) {
      return;
    }
    int index = 1;
    st.setLong(index++, vcfgRFcittComp.getIdFcitt());
    if (vcfgRFcittComp.getIdComp() != null) {
      st.setLong(index++, vcfgRFcittComp.getIdComp());
    }
    if (vcfgRFcittComp.getIdSez() != null) {
      st.setLong(index++, vcfgRFcittComp.getIdSez());
    }
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    if (vcfgRFcittComp.getIdFcitt() == null) {
      return;
    }
    int index = 1;
    st.setLong(index++, vcfgRFcittComp.getIdFcitt());
    st.setLong(index++, vcfgRFcittComp.getIdFcitt());
    st.setLong(index++, vcfgRFcittComp.getIdComp());
    if (vcfgRFcittComp.getIdSez() != null) {
      st.setLong(index++, vcfgRFcittComp.getIdSez());
    }
    /*
     * if (vcfgRFcittComp.getIdSez() != null) { st.setLong(index++,
     * vcfgRFcittComp.getIdSez()); } st.setLong(index++,
     * vcfgRFcittComp.getIdFcitt()); if (vcfgRFcittComp.getIdComp() != null)
     * { st.setLong(index++, vcfgRFcittComp.getIdComp()); } if
     * (vcfgRFcittComp.getIdSez() != null) { st.setLong(index++,
     * vcfgRFcittComp.getIdSez()); }
     */
  }

  @Override
  protected String getSqlInsertObject() {
    if (vcfgRFcittComp.getIdFcitt() == null) {
      return null;
    }
    // String sql = " INSERT INTO CFG_R_FCITT_COMP ( ID_FCITT, ID_COMP )
    // VALUES( ?, ? ) ";
    // default:
    // DATA_REGISTRAZ_FCITT_COMP = sysdate
    // DATA_DEREGISTRAZ_FCITT_COMP = NULL
    // FLAG_ABILITAZIONE = 1

    String sql = " INSERT INTO CFG_R_FCITT_COMP ( ID_FCITT, ID_COMP ) ";
    sql +=
        "  SELECT DISTINCT ?, A.ID_COMP "
            + " FROM CFG_T_CAT_COMP A LEFT JOIN "
            + " CFG_R_FCITT_COMP B ON "
            + "  ( A.ID_COMP = B.ID_COMP AND B.ID_FCITT = ? AND "
            + "    B.DATA_DEREGISTRAZ_FCITT_COMP IS NULL ) "
            + " WHERE 1 = 1 AND "
            + " A.ID_COMP = ? AND ";
    if (vcfgRFcittComp.getIdSez() != null) {
      sql += "  A.ID_SEZ = ? AND ";
    }
    sql +=
        "  ( ( B.ID_FCITT = 18 AND B.ID_CFG_R_FCITT_COMP IS NULL ) "
            + "   OR B.ID_FCITT IS NULL ) ";
    return sql;
  }

  @Override
  protected String getSqlDeleteByKey() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected String getSqlUpdateByKey() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement arg0) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement arg0) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement arg0) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  protected Object getFromResultSet(ResultSet arg0) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected String getSqlDeleteByWhere() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement arg0) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement arg0) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  protected String[] getKeyColumnsNames() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement arg0) throws SQLException {
    // TODO Auto-generated method stub

  }
}
