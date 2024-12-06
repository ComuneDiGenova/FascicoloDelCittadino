package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgRFcittWidg
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:42:08.434
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgRFcittWidg;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgRFcittWidgDAO extends AbstractTableDAO {

  protected CfgRFcittWidg cfgrfcittwidg;

  public CfgRFcittWidgDAO(CfgRFcittWidg cfgrfcittwidg) {
    super();
    this.cfgrfcittwidg = cfgrfcittwidg;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_R_FCITT_WIDG where ID_CFG_R_FCITT_WIDG=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgrfcittwidg.getIdCfgRFcittWidg());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_R_FCITT_WIDG where 1=1 ";
    if (cfgrfcittwidg.getIdCfgRFcittWidg() != null) sql += " and ID_CFG_R_FCITT_WIDG  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgrfcittwidg.getIdCfgRFcittWidg() != null)
      st.setLong(index++, cfgrfcittwidg.getIdCfgRFcittWidg());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgRFcittWidg obj = new CfgRFcittWidg();

    obj.setIdCfgRFcittWidg(r.getLong("ID_CFG_R_FCITT_WIDG"));

    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setIdWidg(r.getLong("ID_WIDG"));

    obj.setDataAssociazioneFcittWidg(
        r.getTimestamp("DATA_ASSOCIAZIONE_FCITT_WIDG") != null
            ? r.getTimestamp("DATA_ASSOCIAZIONE_FCITT_WIDG").toLocalDateTime()
            : null);
    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    obj.setDataDeassociazioneFcittWidg(
        r.getTimestamp("DATA_DEASSOCIAZIONE_FCITT_WIDG") != null
            ? r.getTimestamp("DATA_DEASSOCIAZIONE_FCITT_WIDG").toLocalDateTime()
            : null);
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_R_FCITT_WIDG where ID_CFG_R_FCITT_WIDG=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgrfcittwidg.getIdCfgRFcittWidg());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_R_FCITT_WIDG where 1=1 ";
    if (cfgrfcittwidg.getIdCfgRFcittWidg() != null) sql += " and ID_CFG_R_FCITT_WIDG  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgrfcittwidg.getIdCfgRFcittWidg() != null)
      st.setLong(index++, cfgrfcittwidg.getIdCfgRFcittWidg());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_CFG_R_FCITT_WIDG=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgrfcittwidg.getIdCfgRFcittWidg());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgrfcittwidg.getIdCfgRFcittWidg() != null) sql += " and ID_CFG_R_FCITT_WIDG  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgrfcittwidg.getIdCfgRFcittWidg() != null)
      st.setLong(index++, cfgrfcittwidg.getIdCfgRFcittWidg());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgrfcittwidg.getIdCfgRFcittWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittwidg.getIdCfgRFcittWidg());
    }
    if (cfgrfcittwidg.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittwidg.getIdFcitt());
    }
    if (cfgrfcittwidg.getIdWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittwidg.getIdWidg());
    }
    if (cfgrfcittwidg.getDataAssociazioneFcittWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittwidg.getDataAssociazioneFcittWidg()));
    }
    if (cfgrfcittwidg.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgrfcittwidg.getFlagAbilitazione());
    }
    if (cfgrfcittwidg.getDataDeassociazioneFcittWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittwidg.getDataDeassociazioneFcittWidg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_R_FCITT_WIDG set ID_CFG_R_FCITT_WIDG= ?  , ID_FCITT= ?  , ID_WIDG= ?  , DATA_ASSOCIAZIONE_FCITT_WIDG= ?  , FLAG_ABILITAZIONE= ?  , DATA_DEASSOCIAZIONE_FCITT_WIDG= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_R_FCITT_WIDG ( ID_CFG_R_FCITT_WIDG,ID_FCITT,ID_WIDG,DATA_ASSOCIAZIONE_FCITT_WIDG,FLAG_ABILITAZIONE,DATA_DEASSOCIAZIONE_FCITT_WIDG ) VALUES (? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_R_FCITT_WIDG ( ID_FCITT,ID_WIDG,DATA_ASSOCIAZIONE_FCITT_WIDG,FLAG_ABILITAZIONE,DATA_DEASSOCIAZIONE_FCITT_WIDG ) VALUES (? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgrfcittwidg.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittwidg.getIdFcitt());
    }
    if (cfgrfcittwidg.getIdWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittwidg.getIdWidg());
    }
    if (cfgrfcittwidg.getDataAssociazioneFcittWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittwidg.getDataAssociazioneFcittWidg()));
    }
    if (cfgrfcittwidg.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgrfcittwidg.getFlagAbilitazione());
    }
    if (cfgrfcittwidg.getDataDeassociazioneFcittWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittwidg.getDataDeassociazioneFcittWidg()));
    }
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_CFG_R_FCITT_WIDG"};
  }
}
