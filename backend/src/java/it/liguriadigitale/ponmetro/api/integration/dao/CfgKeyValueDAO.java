package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgKeyValue
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2020-01-23T11:08:10.686
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.CfgKeyValue;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgKeyValueDAO extends AbstractTableDAO {

  private CfgKeyValue cfgkeyvalue;

  public CfgKeyValueDAO(CfgKeyValue cfgkeyvalue) {
    super();
    this.cfgkeyvalue = cfgkeyvalue;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_KEY_VALUE where CFG_KEY=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setString(index++, cfgkeyvalue.getCfgKey());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_KEY_VALUE where 1=1 ";
    if (cfgkeyvalue.getCfgKey() != null) sql += " and CFG_KEY  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgkeyvalue.getCfgKey() != null) st.setString(index++, cfgkeyvalue.getCfgKey());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgKeyValue obj = new CfgKeyValue();

    obj.setCfgKey(r.getString("CFG_KEY"));
    obj.setCfgValue(r.getString("CFG_VALUE"));
    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(
        r.getTimestamp("DATA_INS") != null ? r.getTimestamp("DATA_INS").toLocalDateTime() : null);
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(
        r.getTimestamp("DATA_AGG") != null ? r.getTimestamp("DATA_AGG").toLocalDateTime() : null);
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_KEY_VALUE where CFG_KEY=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setString(index++, cfgkeyvalue.getCfgKey());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_KEY_VALUE where 1=1 ";
    if (cfgkeyvalue.getCfgKey() != null) sql += " and CFG_KEY  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgkeyvalue.getCfgKey() != null) st.setString(index++, cfgkeyvalue.getCfgKey());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where CFG_KEY=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setString(index++, cfgkeyvalue.getCfgKey());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgkeyvalue.getCfgKey() != null) sql += " and CFG_KEY  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgkeyvalue.getCfgKey() != null) st.setString(index++, cfgkeyvalue.getCfgKey());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, cfgkeyvalue.getCfgKey());
    pst.setString(index++, cfgkeyvalue.getCfgValue());
    pst.setString(index++, cfgkeyvalue.getUtenteIns());
    if (cfgkeyvalue.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgkeyvalue.getDataIns()));
    }
    pst.setString(index++, cfgkeyvalue.getUtenteAgg());
    if (cfgkeyvalue.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgkeyvalue.getDataAgg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_KEY_VALUE set CFG_KEY= ?  , CFG_VALUE= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_KEY_VALUE ( CFG_KEY,CFG_VALUE,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_KEY_VALUE ( CFG_VALUE,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, cfgkeyvalue.getCfgValue());
    pst.setString(index++, cfgkeyvalue.getUtenteIns());
    if (cfgkeyvalue.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgkeyvalue.getDataIns()));
    }
    pst.setString(index++, cfgkeyvalue.getUtenteAgg());
    if (cfgkeyvalue.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgkeyvalue.getDataAgg()));
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"CFG_KEY"};
  }
}
