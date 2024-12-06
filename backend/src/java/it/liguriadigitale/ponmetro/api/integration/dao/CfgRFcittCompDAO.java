package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgRFcittComp
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:42:08.068
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgRFcittComp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgRFcittCompDAO extends AbstractTableDAO {

  protected CfgRFcittComp cfgrfcittcomp;

  public CfgRFcittCompDAO(CfgRFcittComp cfgrfcittcomp) {
    super();
    this.cfgrfcittcomp = cfgrfcittcomp;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_R_FCITT_COMP where ID_CFG_R_FCITT_COMP=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgrfcittcomp.getIdCfgRFcittComp());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_R_FCITT_COMP where 1=1 ";
    if (cfgrfcittcomp.getIdCfgRFcittComp() != null) sql += " and ID_CFG_R_FCITT_COMP  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgrfcittcomp.getIdCfgRFcittComp() != null)
      st.setLong(index++, cfgrfcittcomp.getIdCfgRFcittComp());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgRFcittComp obj = new CfgRFcittComp();

    obj.setIdCfgRFcittComp(r.getLong("ID_CFG_R_FCITT_COMP"));

    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setIdComp(r.getLong("ID_COMP"));

    obj.setDataRegistrazFcittComp(
        r.getTimestamp("DATA_REGISTRAZ_FCITT_COMP") != null
            ? r.getTimestamp("DATA_REGISTRAZ_FCITT_COMP").toLocalDateTime()
            : null);
    obj.setDataDeregistrazFcittComp(
        r.getTimestamp("DATA_DEREGISTRAZ_FCITT_COMP") != null
            ? r.getTimestamp("DATA_DEREGISTRAZ_FCITT_COMP").toLocalDateTime()
            : null);
    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_R_FCITT_COMP where ID_CFG_R_FCITT_COMP=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgrfcittcomp.getIdCfgRFcittComp());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_R_FCITT_COMP where 1=1 ";
    if (cfgrfcittcomp.getIdCfgRFcittComp() != null) sql += " and ID_CFG_R_FCITT_COMP  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgrfcittcomp.getIdCfgRFcittComp() != null)
      st.setLong(index++, cfgrfcittcomp.getIdCfgRFcittComp());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_CFG_R_FCITT_COMP=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgrfcittcomp.getIdCfgRFcittComp());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgrfcittcomp.getIdCfgRFcittComp() != null) sql += " and ID_CFG_R_FCITT_COMP  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgrfcittcomp.getIdCfgRFcittComp() != null)
      st.setLong(index++, cfgrfcittcomp.getIdCfgRFcittComp());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgrfcittcomp.getIdCfgRFcittComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittcomp.getIdCfgRFcittComp());
    }
    if (cfgrfcittcomp.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittcomp.getIdFcitt());
    }
    if (cfgrfcittcomp.getIdComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittcomp.getIdComp());
    }
    if (cfgrfcittcomp.getDataRegistrazFcittComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittcomp.getDataRegistrazFcittComp()));
    }
    if (cfgrfcittcomp.getDataDeregistrazFcittComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittcomp.getDataDeregistrazFcittComp()));
    }
    if (cfgrfcittcomp.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgrfcittcomp.getFlagAbilitazione());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_R_FCITT_COMP set ID_CFG_R_FCITT_COMP= ?  , ID_FCITT= ?  , ID_COMP= ?  , DATA_REGISTRAZ_FCITT_COMP= ?  , DATA_DEREGISTRAZ_FCITT_COMP= ?  , FLAG_ABILITAZIONE= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_R_FCITT_COMP ( ID_CFG_R_FCITT_COMP,ID_FCITT,ID_COMP,DATA_REGISTRAZ_FCITT_COMP,DATA_DEREGISTRAZ_FCITT_COMP,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_R_FCITT_COMP ( ID_FCITT,ID_COMP,DATA_REGISTRAZ_FCITT_COMP,DATA_DEREGISTRAZ_FCITT_COMP,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgrfcittcomp.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittcomp.getIdFcitt());
    }
    if (cfgrfcittcomp.getIdComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittcomp.getIdComp());
    }
    if (cfgrfcittcomp.getDataRegistrazFcittComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittcomp.getDataRegistrazFcittComp()));
    }
    if (cfgrfcittcomp.getDataDeregistrazFcittComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittcomp.getDataDeregistrazFcittComp()));
    }
    if (cfgrfcittcomp.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgrfcittcomp.getFlagAbilitazione());
    }
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_CFG_R_FCITT_COMP"};
  }
}
