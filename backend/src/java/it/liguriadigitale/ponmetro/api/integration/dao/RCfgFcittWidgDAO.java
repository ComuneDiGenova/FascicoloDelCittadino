package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * RCfgFcittWidg
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.config.db.RCfgFcittWidg;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RCfgFcittWidgDAO extends AbstractTableDAO {

  private RCfgFcittWidg rcfgfcittwidg;

  public RCfgFcittWidgDAO(RCfgFcittWidg rcfgfcittwidg) {
    super();
    this.rcfgfcittwidg = rcfgfcittwidg;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from R_CFG_FCITT_WIDG where ID_R_CFG_FCITT_WIDG=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, rcfgfcittwidg.getIdRCfgFcittWidg());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from R_CFG_FCITT_WIDG where 1=1 ";
    if (rcfgfcittwidg.getIdRCfgFcittWidg() != null) sql += " and ID_R_CFG_FCITT_WIDG  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (rcfgfcittwidg.getIdRCfgFcittWidg() != null)
      st.setLong(index++, rcfgfcittwidg.getIdRCfgFcittWidg());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    RCfgFcittWidg obj = new RCfgFcittWidg();

    obj.setIdRCfgFcittWidg(r.getLong("ID_R_CFG_FCITT_WIDG"));

    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setIdWidg(r.getLong("ID_WIDG"));

    obj.setDataAssociazioneFcittWidg(
        r.getTimestamp("DATA_ASSOCIAZIONE_FCITT_WIDG").toLocalDateTime());
    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from R_CFG_FCITT_WIDG where ID_R_CFG_FCITT_WIDG=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, rcfgfcittwidg.getIdRCfgFcittWidg());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from R_CFG_FCITT_WIDG where 1=1 ";
    if (rcfgfcittwidg.getIdRCfgFcittWidg() != null) sql += " and ID_R_CFG_FCITT_WIDG  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (rcfgfcittwidg.getIdRCfgFcittWidg() != null)
      st.setLong(index++, rcfgfcittwidg.getIdRCfgFcittWidg());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_R_CFG_FCITT_WIDG=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, rcfgfcittwidg.getIdRCfgFcittWidg());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (rcfgfcittwidg.getIdRCfgFcittWidg() != null) sql += " and ID_R_CFG_FCITT_WIDG  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (rcfgfcittwidg.getIdRCfgFcittWidg() != null)
      st.setLong(index++, rcfgfcittwidg.getIdRCfgFcittWidg());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (rcfgfcittwidg.getIdRCfgFcittWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittwidg.getIdRCfgFcittWidg());
    }
    if (rcfgfcittwidg.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittwidg.getIdFcitt());
    }
    if (rcfgfcittwidg.getIdWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittwidg.getIdWidg());
    }
    if (rcfgfcittwidg.getDataAssociazioneFcittWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(rcfgfcittwidg.getDataAssociazioneFcittWidg()));
    }
    if (rcfgfcittwidg.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, rcfgfcittwidg.getFlagAbilitazione());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE R_CFG_FCITT_WIDG set ID_R_CFG_FCITT_WIDG= ?  , ID_FCITT= ?  , ID_WIDG= ?  , DATA_ASSOCIAZIONE_FCITT_WIDG= ?  , FLAG_ABILITAZIONE= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO R_CFG_FCITT_WIDG ( ID_R_CFG_FCITT_WIDG,ID_FCITT,ID_WIDG,DATA_ASSOCIAZIONE_FCITT_WIDG,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO R_CFG_FCITT_WIDG ( ID_FCITT,ID_WIDG,DATA_ASSOCIAZIONE_FCITT_WIDG,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (rcfgfcittwidg.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittwidg.getIdFcitt());
    }
    if (rcfgfcittwidg.getIdWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittwidg.getIdWidg());
    }
    if (rcfgfcittwidg.getDataAssociazioneFcittWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(rcfgfcittwidg.getDataAssociazioneFcittWidg()));
    }
    if (rcfgfcittwidg.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, rcfgfcittwidg.getFlagAbilitazione());
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_R_CFG_FCITT_WIDG"};
  }
}
