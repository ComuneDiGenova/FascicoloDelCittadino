package it.liguriadigitale.ponmetro.api.integration.dao.contatti;

/**
 * CfgTContatti
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-08-31T18:12:06.726
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.contatti.CfgTContatti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgTContattiDAO extends AbstractTableDAO {

  private CfgTContatti cfgtcontatti;

  public CfgTContattiDAO(CfgTContatti cfgtcontatti) {
    super();
    this.cfgtcontatti = cfgtcontatti;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_CONTATTI where TIPO=? and ID_FCITT=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setString(index++, cfgtcontatti.getTipo());
    st.setLong(index++, cfgtcontatti.getIdFcitt());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_CONTATTI where 1=1 ";
    if (cfgtcontatti.getIdFcitt() != null) sql += " and ID_FCITT  = ?";
    if (cfgtcontatti.getTipo() != null) sql += " and TIPO  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcontatti.getIdFcitt() != null) st.setLong(index++, cfgtcontatti.getIdFcitt());
    if (cfgtcontatti.getTipo() != null) st.setString(index++, cfgtcontatti.getTipo());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTContatti obj = new CfgTContatti();

    obj.setIdFcitt(r.getLong("ID_FCITT"));
    if (r.wasNull()) {
      obj.setIdFcitt(null);
    }

    obj.setContatto(r.getString("CONTATTO"));
    obj.setDataIns(
        r.getTimestamp("DATA_INS") != null ? r.getTimestamp("DATA_INS").toLocalDateTime() : null);
    obj.setDataAgg(
        r.getTimestamp("DATA_AGG") != null ? r.getTimestamp("DATA_AGG").toLocalDateTime() : null);
    obj.setTipo(r.getString("TIPO"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_CONTATTI where TIPO=? and ID_FCITT=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setString(index++, cfgtcontatti.getTipo());
    st.setLong(index++, cfgtcontatti.getIdFcitt());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_CONTATTI where 1=1 ";
    if (cfgtcontatti.getIdFcitt() != null) sql += " and ID_FCITT  = ?";
    if (cfgtcontatti.getTipo() != null) sql += " and TIPO  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcontatti.getIdFcitt() != null) st.setLong(index++, cfgtcontatti.getIdFcitt());
    if (cfgtcontatti.getTipo() != null) st.setString(index++, cfgtcontatti.getTipo());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where TIPO=? and ID_FCITT=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setString(index++, cfgtcontatti.getTipo());
    st.setLong(index++, cfgtcontatti.getIdFcitt());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtcontatti.getIdFcitt() != null) sql += " and ID_FCITT  = ?";
    if (cfgtcontatti.getTipo() != null) sql += " and TIPO  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtcontatti.getIdFcitt() != null) st.setLong(index++, cfgtcontatti.getIdFcitt());
    if (cfgtcontatti.getTipo() != null) st.setString(index++, cfgtcontatti.getTipo());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcontatti.getIdFcitt() != null) {
      pst.setLong(index++, cfgtcontatti.getIdFcitt());
    }
    if (cfgtcontatti.getContatto() != null) {
      pst.setString(index++, cfgtcontatti.getContatto());
    }
    if (cfgtcontatti.getDataIns() != null) {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcontatti.getDataIns()));
    }
    if (cfgtcontatti.getDataAgg() != null) {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcontatti.getDataAgg()));
    }
    if (cfgtcontatti.getTipo() != null) {
      pst.setString(index++, cfgtcontatti.getTipo());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE CFG_T_CONTATTI set ";

    if (cfgtcontatti.getIdFcitt() != null) {
      sql += " ID_FCITT= ? ";
      sql += " ,";
    }
    if (cfgtcontatti.getContatto() != null) {
      sql += " CONTATTO= ? ";
      sql += " ,";
    }
    if (cfgtcontatti.getDataIns() != null) {
      sql += " DATA_INS= ? ";
      sql += " ,";
    }
    if (cfgtcontatti.getDataAgg() != null) {
      sql += " DATA_AGG= ? ";
      sql += " ,";
    }
    if (cfgtcontatti.getTipo() != null) {
      sql += " TIPO= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_CONTATTI ( ID_FCITT,CONTATTO,DATA_INS,DATA_AGG,TIPO ) VALUES (? ,? ,? ,? ,?   )";
    return sql;
  }

  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcontatti.getIdFcitt() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, cfgtcontatti.getIdFcitt());
    }
    pst.setString(index++, cfgtcontatti.getContatto());
    if (cfgtcontatti.getDataIns() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcontatti.getDataIns()));
    }
    if (cfgtcontatti.getDataAgg() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcontatti.getDataAgg()));
    }
    pst.setString(index++, cfgtcontatti.getTipo());
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql = "INSERT INTO CFG_T_CONTATTI ( CONTATTO,DATA_INS,DATA_AGG ) VALUES (? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, cfgtcontatti.getContatto());
    if (cfgtcontatti.getDataIns() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcontatti.getDataIns()));
    }
    if (cfgtcontatti.getDataAgg() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcontatti.getDataAgg()));
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"TIPO", "ID_FCITT"};
  }
}
