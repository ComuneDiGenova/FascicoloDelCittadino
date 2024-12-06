package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * TCfgNotifiche
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.config.db.TCfgNotifiche;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TCfgNotificheDAO extends AbstractTableDAO {

  private TCfgNotifiche tcfgnotifiche;

  public TCfgNotificheDAO(TCfgNotifiche tcfgnotifiche) {
    super();
    this.tcfgnotifiche = tcfgnotifiche;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from T_CFG_NOTIFICHE where ID_NOTIFICA=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, tcfgnotifiche.getIdNotifica());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from T_CFG_NOTIFICHE where 1=1 ";
    if (tcfgnotifiche.getIdNotifica() != null) sql += " and ID_NOTIFICA  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (tcfgnotifiche.getIdNotifica() != null) st.setLong(index++, tcfgnotifiche.getIdNotifica());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    TCfgNotifiche obj = new TCfgNotifiche();

    obj.setIdNotifica(r.getLong("ID_NOTIFICA"));

    obj.setIdComp(r.getLong("ID_COMP"));

    obj.setIdTNotifica(r.getLong("ID_T_NOTIFICA"));

    obj.setTestoNotifica(r.getString("TESTO_NOTIFICA"));
    obj.setUriNotifica(r.getString("URI_NOTIFICA"));
    obj.setDataNotifica(r.getTimestamp("DATA_NOTIFICA").toLocalDateTime());
    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from T_CFG_NOTIFICHE where ID_NOTIFICA=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, tcfgnotifiche.getIdNotifica());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from T_CFG_NOTIFICHE where 1=1 ";
    if (tcfgnotifiche.getIdNotifica() != null) sql += " and ID_NOTIFICA  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (tcfgnotifiche.getIdNotifica() != null) st.setLong(index++, tcfgnotifiche.getIdNotifica());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_NOTIFICA=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, tcfgnotifiche.getIdNotifica());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (tcfgnotifiche.getIdNotifica() != null) sql += " and ID_NOTIFICA  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (tcfgnotifiche.getIdNotifica() != null) st.setLong(index++, tcfgnotifiche.getIdNotifica());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (tcfgnotifiche.getIdNotifica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgnotifiche.getIdNotifica());
    }
    if (tcfgnotifiche.getIdComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgnotifiche.getIdComp());
    }
    if (tcfgnotifiche.getIdTNotifica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgnotifiche.getIdTNotifica());
    }
    pst.setString(index++, tcfgnotifiche.getTestoNotifica());
    pst.setString(index++, tcfgnotifiche.getUriNotifica());
    if (tcfgnotifiche.getDataNotifica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(tcfgnotifiche.getDataNotifica()));
    }
    if (tcfgnotifiche.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, tcfgnotifiche.getFlagAbilitazione());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE T_CFG_NOTIFICHE set ID_NOTIFICA= ?  , ID_COMP= ?  , ID_T_NOTIFICA= ?  , TESTO_NOTIFICA= ?  , URI_NOTIFICA= ?  , DATA_NOTIFICA= ?  , FLAG_ABILITAZIONE= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO T_CFG_NOTIFICHE ( ID_NOTIFICA,ID_COMP,ID_T_NOTIFICA,TESTO_NOTIFICA,URI_NOTIFICA,DATA_NOTIFICA,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO T_CFG_NOTIFICHE ( ID_COMP,ID_T_NOTIFICA,TESTO_NOTIFICA,URI_NOTIFICA,DATA_NOTIFICA,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (tcfgnotifiche.getIdComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgnotifiche.getIdComp());
    }
    if (tcfgnotifiche.getIdTNotifica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgnotifiche.getIdTNotifica());
    }
    pst.setString(index++, tcfgnotifiche.getTestoNotifica());
    pst.setString(index++, tcfgnotifiche.getUriNotifica());
    if (tcfgnotifiche.getDataNotifica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(tcfgnotifiche.getDataNotifica()));
    }
    if (tcfgnotifiche.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, tcfgnotifiche.getFlagAbilitazione());
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_NOTIFICA"};
  }
}
