package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * RCfgFcittNotifiche
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.config.db.RCfgFcittNotifiche;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RCfgFcittNotificheDAO extends AbstractTableDAO {

  private RCfgFcittNotifiche rcfgfcittnotifiche;

  public RCfgFcittNotificheDAO(RCfgFcittNotifiche rcfgfcittnotifiche) {
    super();
    this.rcfgfcittnotifiche = rcfgfcittnotifiche;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from R_CFG_FCITT_NOTIFICHE where ID_FCITT_NOTIFICHE=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, rcfgfcittnotifiche.getIdFcittNotifiche());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from R_CFG_FCITT_NOTIFICHE where 1=1 ";
    if (rcfgfcittnotifiche.getIdFcittNotifiche() != null) sql += " and ID_FCITT_NOTIFICHE  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (rcfgfcittnotifiche.getIdFcittNotifiche() != null)
      st.setBigDecimal(index++, rcfgfcittnotifiche.getIdFcittNotifiche());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    RCfgFcittNotifiche obj = new RCfgFcittNotifiche();

    obj.setIdFcittNotifiche(r.getBigDecimal("ID_FCITT_NOTIFICHE"));
    obj.setIdNotifica(r.getLong("ID_NOTIFICA"));

    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setDataPresaVisione(r.getTimestamp("DATA_PRESA_VISIONE").toLocalDateTime());
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from R_CFG_FCITT_NOTIFICHE where ID_FCITT_NOTIFICHE=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, rcfgfcittnotifiche.getIdFcittNotifiche());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from R_CFG_FCITT_NOTIFICHE where 1=1 ";
    if (rcfgfcittnotifiche.getIdFcittNotifiche() != null) sql += " and ID_FCITT_NOTIFICHE  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (rcfgfcittnotifiche.getIdFcittNotifiche() != null)
      st.setBigDecimal(index++, rcfgfcittnotifiche.getIdFcittNotifiche());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_FCITT_NOTIFICHE=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setBigDecimal(index++, rcfgfcittnotifiche.getIdFcittNotifiche());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (rcfgfcittnotifiche.getIdFcittNotifiche() != null) sql += " and ID_FCITT_NOTIFICHE  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (rcfgfcittnotifiche.getIdFcittNotifiche() != null)
      st.setBigDecimal(index++, rcfgfcittnotifiche.getIdFcittNotifiche());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, rcfgfcittnotifiche.getIdFcittNotifiche());
    if (rcfgfcittnotifiche.getIdNotifica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittnotifiche.getIdNotifica());
    }
    if (rcfgfcittnotifiche.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittnotifiche.getIdFcitt());
    }
    if (rcfgfcittnotifiche.getDataPresaVisione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(rcfgfcittnotifiche.getDataPresaVisione()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE R_CFG_FCITT_NOTIFICHE set ID_FCITT_NOTIFICHE= ?  , ID_NOTIFICA= ?  , ID_FCITT= ?  , DATA_PRESA_VISIONE= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO R_CFG_FCITT_NOTIFICHE ( ID_FCITT_NOTIFICHE,ID_NOTIFICA,ID_FCITT,DATA_PRESA_VISIONE ) VALUES (? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO R_CFG_FCITT_NOTIFICHE ( ID_NOTIFICA,ID_FCITT,DATA_PRESA_VISIONE ) VALUES (? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (rcfgfcittnotifiche.getIdNotifica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittnotifiche.getIdNotifica());
    }
    if (rcfgfcittnotifiche.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittnotifiche.getIdFcitt());
    }
    if (rcfgfcittnotifiche.getDataPresaVisione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(rcfgfcittnotifiche.getDataPresaVisione()));
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_FCITT_NOTIFICHE"};
  }
}
