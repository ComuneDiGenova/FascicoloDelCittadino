package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgRFcittNotifiche
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.messaggiutente.CfgRFcittNotifiche;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgRFcittNotificheDAO extends AbstractTableDAO {

  private CfgRFcittNotifiche cfgrfcittnotifiche;

  public CfgRFcittNotificheDAO(CfgRFcittNotifiche cfgrfcittnotifiche) {
    super();
    this.cfgrfcittnotifiche = cfgrfcittnotifiche;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_R_FCITT_NOTIFICHE where ID_FCITT_NOTIFICHE=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, cfgrfcittnotifiche.getIdFcittNotifiche());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_R_FCITT_NOTIFICHE where 1=1 ";
    if (cfgrfcittnotifiche.getIdFcittNotifiche() != null) sql += " and ID_FCITT_NOTIFICHE  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgrfcittnotifiche.getIdFcittNotifiche() != null)
      st.setBigDecimal(index++, cfgrfcittnotifiche.getIdFcittNotifiche());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgRFcittNotifiche obj = new CfgRFcittNotifiche();

    obj.setIdFcittNotifiche(r.getBigDecimal("ID_FCITT_NOTIFICHE"));
    obj.setIdNotifica(r.getLong("ID_NOTIFICA"));

    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setDataPresaVisione(
        r.getTimestamp("DATA_PRESA_VISIONE") != null
            ? r.getTimestamp("DATA_PRESA_VISIONE").toLocalDateTime()
            : null);
    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(
        r.getTimestamp("DATA_INS") != null ? r.getTimestamp("DATA_INS").toLocalDateTime() : null);
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(
        r.getTimestamp("DATA_AGG") != null ? r.getTimestamp("DATA_AGG").toLocalDateTime() : null);
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_R_FCITT_NOTIFICHE where ID_FCITT_NOTIFICHE=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, cfgrfcittnotifiche.getIdFcittNotifiche());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_R_FCITT_NOTIFICHE where 1=1 ";
    if (cfgrfcittnotifiche.getIdFcittNotifiche() != null) sql += " and ID_FCITT_NOTIFICHE  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgrfcittnotifiche.getIdFcittNotifiche() != null)
      st.setBigDecimal(index++, cfgrfcittnotifiche.getIdFcittNotifiche());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_FCITT_NOTIFICHE=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setBigDecimal(index++, cfgrfcittnotifiche.getIdFcittNotifiche());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgrfcittnotifiche.getIdFcittNotifiche() != null) sql += " and ID_FCITT_NOTIFICHE  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgrfcittnotifiche.getIdFcittNotifiche() != null)
      st.setBigDecimal(index++, cfgrfcittnotifiche.getIdFcittNotifiche());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, cfgrfcittnotifiche.getIdFcittNotifiche());
    if (cfgrfcittnotifiche.getIdNotifica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittnotifiche.getIdNotifica());
    }
    if (cfgrfcittnotifiche.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittnotifiche.getIdFcitt());
    }
    if (cfgrfcittnotifiche.getDataPresaVisione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittnotifiche.getDataPresaVisione()));
    }
    if (cfgrfcittnotifiche.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittnotifiche.getIdStatoRec());
    }
    pst.setString(index++, cfgrfcittnotifiche.getUtenteIns());
    if (cfgrfcittnotifiche.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgrfcittnotifiche.getDataIns()));
    }
    pst.setString(index++, cfgrfcittnotifiche.getUtenteAgg());
    if (cfgrfcittnotifiche.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgrfcittnotifiche.getDataAgg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_R_FCITT_NOTIFICHE set ID_FCITT_NOTIFICHE= ?  , ID_NOTIFICA= ?  , ID_FCITT= ?  , DATA_PRESA_VISIONE= ?  , ID_STATO_REC= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_R_FCITT_NOTIFICHE ( ID_FCITT_NOTIFICHE,ID_NOTIFICA,ID_FCITT,DATA_PRESA_VISIONE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_R_FCITT_NOTIFICHE ( ID_NOTIFICA,ID_FCITT,DATA_PRESA_VISIONE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgrfcittnotifiche.getIdNotifica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittnotifiche.getIdNotifica());
    }
    if (cfgrfcittnotifiche.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittnotifiche.getIdFcitt());
    }
    if (cfgrfcittnotifiche.getDataPresaVisione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittnotifiche.getDataPresaVisione()));
    }
    if (cfgrfcittnotifiche.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittnotifiche.getIdStatoRec());
    }
    pst.setString(index++, cfgrfcittnotifiche.getUtenteIns());
    if (cfgrfcittnotifiche.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgrfcittnotifiche.getDataIns()));
    }
    pst.setString(index++, cfgrfcittnotifiche.getUtenteAgg());
    if (cfgrfcittnotifiche.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgrfcittnotifiche.getDataAgg()));
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_FCITT_NOTIFICHE"};
  }
}
