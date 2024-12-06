package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * RCfgFcittComp
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.config.db.RCfgFcittComp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RCfgFcittCompDAO extends AbstractTableDAO {

  private RCfgFcittComp rcfgfcittcomp;

  public RCfgFcittCompDAO(RCfgFcittComp rcfgfcittcomp) {
    super();
    this.rcfgfcittcomp = rcfgfcittcomp;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from R_CFG_FCITT_COMP where ID_R_CFG_FCITT_COMP=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, rcfgfcittcomp.getIdRCfgFcittComp());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from R_CFG_FCITT_COMP where 1=1 ";
    if (rcfgfcittcomp.getIdRCfgFcittComp() != null) sql += " and ID_R_CFG_FCITT_COMP  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (rcfgfcittcomp.getIdRCfgFcittComp() != null)
      st.setLong(index++, rcfgfcittcomp.getIdRCfgFcittComp());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    RCfgFcittComp obj = new RCfgFcittComp();

    obj.setIdRCfgFcittComp(r.getLong("ID_R_CFG_FCITT_COMP"));

    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setIdComp(r.getLong("ID_COMP"));

    obj.setDataRegistrazioneFcittComp(
        r.getTimestamp("DATA_REGISTRAZIONE_FCITT_COMP").toLocalDateTime());
    obj.setDataDegistrazioneFcittComp(
        r.getTimestamp("DATA_DEGISTRAZIONE_FCITT_COMP").toLocalDateTime());
    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from R_CFG_FCITT_COMP where ID_R_CFG_FCITT_COMP=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, rcfgfcittcomp.getIdRCfgFcittComp());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from R_CFG_FCITT_COMP where 1=1 ";
    if (rcfgfcittcomp.getIdRCfgFcittComp() != null) sql += " and ID_R_CFG_FCITT_COMP  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (rcfgfcittcomp.getIdRCfgFcittComp() != null)
      st.setLong(index++, rcfgfcittcomp.getIdRCfgFcittComp());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_R_CFG_FCITT_COMP=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, rcfgfcittcomp.getIdRCfgFcittComp());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (rcfgfcittcomp.getIdRCfgFcittComp() != null) sql += " and ID_R_CFG_FCITT_COMP  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (rcfgfcittcomp.getIdRCfgFcittComp() != null)
      st.setLong(index++, rcfgfcittcomp.getIdRCfgFcittComp());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (rcfgfcittcomp.getIdRCfgFcittComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittcomp.getIdRCfgFcittComp());
    }
    if (rcfgfcittcomp.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittcomp.getIdFcitt());
    }
    if (rcfgfcittcomp.getIdComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittcomp.getIdComp());
    }
    if (rcfgfcittcomp.getDataRegistrazioneFcittComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(rcfgfcittcomp.getDataRegistrazioneFcittComp()));
    }
    if (rcfgfcittcomp.getDataDegistrazioneFcittComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(rcfgfcittcomp.getDataDegistrazioneFcittComp()));
    }
    if (rcfgfcittcomp.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, rcfgfcittcomp.getFlagAbilitazione());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE R_CFG_FCITT_COMP set ID_R_CFG_FCITT_COMP= ?  , ID_FCITT= ?  , ID_COMP= ?  , DATA_REGISTRAZIONE_FCITT_COMP= ?  , DATA_DEGISTRAZIONE_FCITT_COMP= ?  , FLAG_ABILITAZIONE= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO R_CFG_FCITT_COMP ( ID_R_CFG_FCITT_COMP,ID_FCITT,ID_COMP,DATA_REGISTRAZIONE_FCITT_COMP,DATA_DEGISTRAZIONE_FCITT_COMP,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO R_CFG_FCITT_COMP ( ID_FCITT,ID_COMP,DATA_REGISTRAZIONE_FCITT_COMP,DATA_DEGISTRAZIONE_FCITT_COMP,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (rcfgfcittcomp.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittcomp.getIdFcitt());
    }
    if (rcfgfcittcomp.getIdComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittcomp.getIdComp());
    }
    if (rcfgfcittcomp.getDataRegistrazioneFcittComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(rcfgfcittcomp.getDataRegistrazioneFcittComp()));
    }
    if (rcfgfcittcomp.getDataDegistrazioneFcittComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(rcfgfcittcomp.getDataDegistrazioneFcittComp()));
    }
    if (rcfgfcittcomp.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, rcfgfcittcomp.getFlagAbilitazione());
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_R_CFG_FCITT_COMP"};
  }
}
