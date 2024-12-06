package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgTFcitt
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.family.db.CfgTFcitt;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgTFcittDAO extends AbstractTableDAO {

  private CfgTFcitt cfgtfcitt;

  public CfgTFcittDAO(CfgTFcitt cfgtfcitt) {
    super();
    this.cfgtfcitt = cfgtfcitt;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_FCITT where ID_FCITT=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtfcitt.getIdFcitt());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_FCITT where 1=1 ";
    if (cfgtfcitt.getIdFcitt() != null) sql += " and ID_FCITT  = ?";
    if (cfgtfcitt.getPersonId() != null) sql += " and PERSON_ID  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtfcitt.getIdFcitt() != null) st.setLong(index++, cfgtfcitt.getIdFcitt());
    if (cfgtfcitt.getPersonId() != null) st.setLong(index++, cfgtfcitt.getPersonId());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTFcitt obj = new CfgTFcitt();

    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setPersonId(r.getLong("PERSON_ID"));

    obj.setTimeLastLogin(r.getTimestamp("TIME_LAST_LOGIN").toLocalDateTime());
    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(r.getTimestamp("DATA_INS").toLocalDateTime());
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(r.getTimestamp("DATA_AGG").toLocalDateTime());
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_FCITT where ID_FCITT=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtfcitt.getIdFcitt());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_FCITT where 1=1 ";
    if (cfgtfcitt.getIdFcitt() != null) sql += " and ID_FCITT  = ?";
    if (cfgtfcitt.getPersonId() != null) sql += " and PERSON_ID  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtfcitt.getIdFcitt() != null) st.setLong(index++, cfgtfcitt.getIdFcitt());
    if (cfgtfcitt.getPersonId() != null) st.setLong(index++, cfgtfcitt.getPersonId());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_FCITT=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgtfcitt.getIdFcitt());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtfcitt.getIdFcitt() != null) sql += " and ID_FCITT  = ?";
    if (cfgtfcitt.getPersonId() != null) sql += " and PERSON_ID  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtfcitt.getIdFcitt() != null) st.setLong(index++, cfgtfcitt.getIdFcitt());
    if (cfgtfcitt.getPersonId() != null) st.setLong(index++, cfgtfcitt.getPersonId());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtfcitt.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcitt.getIdFcitt());
    }
    if (cfgtfcitt.getPersonId() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcitt.getPersonId());
    }
    if (cfgtfcitt.getTimeLastLogin() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtfcitt.getTimeLastLogin()));
    }
    if (cfgtfcitt.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcitt.getIdStatoRec());
    }
    pst.setString(index++, cfgtfcitt.getUtenteIns());
    if (cfgtfcitt.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtfcitt.getDataIns()));
    }
    pst.setString(index++, cfgtfcitt.getUtenteAgg());
    if (cfgtfcitt.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtfcitt.getDataAgg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_T_FCITT set ID_FCITT= ?  , PERSON_ID= ?  , TIME_LAST_LOGIN= ?  , ID_STATO_REC= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_FCITT ( ID_FCITT,PERSON_ID,TIME_LAST_LOGIN,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_T_FCITT ( PERSON_ID,TIME_LAST_LOGIN,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtfcitt.getPersonId() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcitt.getPersonId());
    }
    if (cfgtfcitt.getTimeLastLogin() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtfcitt.getTimeLastLogin()));
    }
    if (cfgtfcitt.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcitt.getIdStatoRec());
    }
    pst.setString(index++, cfgtfcitt.getUtenteIns());
    if (cfgtfcitt.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtfcitt.getDataIns()));
    }
    pst.setString(index++, cfgtfcitt.getUtenteAgg());
    if (cfgtfcitt.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtfcitt.getDataAgg()));
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_FCITT"};
  }
}
