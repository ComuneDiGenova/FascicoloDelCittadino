package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgRFcittScadenzePersonali
 *
 * <p>WARNING!
 *
 * <p>MODIFICATO a mano, NON RIGENERARE
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.CfgRFcittScadenzePersonali;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgRFcittScadenzePersonaliDAO extends AbstractTableDAO {

  private CfgRFcittScadenzePersonali cfgrfcittscadenzepersonali;

  public CfgRFcittScadenzePersonaliDAO(CfgRFcittScadenzePersonali cfgrfcittscadenzepersonali) {
    super();
    this.cfgrfcittscadenzepersonali = cfgrfcittscadenzepersonali;
    log.debug("pojo=" + cfgrfcittscadenzepersonali);
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_R_FCITT_SCADENZE_PERSONALI where ID_SCADENZA=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgrfcittscadenzepersonali.getIdScadenza());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_R_FCITT_SCADENZE_PERSONALI where 1=1 ";
    if (cfgrfcittscadenzepersonali.getIdFcitt() != null) sql += " and ID_FCITT  = ?";
    if (cfgrfcittscadenzepersonali.getIdTEvento() != null) sql += " and ID_T_EVENTO  = ?";
    if (cfgrfcittscadenzepersonali.getDataScadenza() != null) sql += " and DATA_SCADENZA  = ?";
    if (cfgrfcittscadenzepersonali.getOggetto() != null) sql += " and OGGETTO  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgrfcittscadenzepersonali.getIdFcitt() != null)
      st.setLong(index++, cfgrfcittscadenzepersonali.getIdFcitt());
    if (cfgrfcittscadenzepersonali.getIdTEvento() != null)
      st.setLong(index++, cfgrfcittscadenzepersonali.getIdTEvento());
    if (cfgrfcittscadenzepersonali.getDataScadenza() != null)
      st.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittscadenzepersonali.getDataScadenza()));
    if (cfgrfcittscadenzepersonali.getOggetto() != null)
      st.setString(index++, cfgrfcittscadenzepersonali.getOggetto());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgRFcittScadenzePersonali obj = new CfgRFcittScadenzePersonali();

    obj.setIdScadenza(r.getLong("ID_SCADENZA"));

    obj.setIdTEvento(r.getLong("ID_T_EVENTO"));

    obj.setIdStato(r.getLong("ID_STATO"));

    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setScadenza(r.getString("SCADENZA"));
    obj.setDataScadenza(
        r.getTimestamp("DATA_SCADENZA") != null
            ? r.getTimestamp("DATA_SCADENZA").toLocalDateTime()
            : null);
    obj.setDataInvioMsg(
        r.getTimestamp("DATA_INVIO_MSG") != null
            ? r.getTimestamp("DATA_INVIO_MSG").toLocalDateTime()
            : null);
    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(
        r.getTimestamp("DATA_INS") != null ? r.getTimestamp("DATA_INS").toLocalDateTime() : null);
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(
        r.getTimestamp("DATA_AGG") != null ? r.getTimestamp("DATA_AGG").toLocalDateTime() : null);
    obj.setOggetto(r.getString("OGGETTO"));
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_R_FCITT_SCADENZE_PERSONALI where ID_SCADENZA=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgrfcittscadenzepersonali.getIdScadenza());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_R_FCITT_SCADENZE_PERSONALI where 1=1 ";
    if (cfgrfcittscadenzepersonali.getIdScadenza() != null) sql += " and ID_SCADENZA  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgrfcittscadenzepersonali.getIdScadenza() != null)
      st.setLong(index++, cfgrfcittscadenzepersonali.getIdScadenza());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_SCADENZA=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgrfcittscadenzepersonali.getIdScadenza());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgrfcittscadenzepersonali.getIdScadenza() != null) sql += " and ID_SCADENZA  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgrfcittscadenzepersonali.getIdScadenza());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgrfcittscadenzepersonali.getIdTEvento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittscadenzepersonali.getIdTEvento());
    }
    if (cfgrfcittscadenzepersonali.getIdStato() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittscadenzepersonali.getIdStato());
    }
    if (cfgrfcittscadenzepersonali.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittscadenzepersonali.getIdFcitt());
    }
    pst.setString(index++, cfgrfcittscadenzepersonali.getScadenza());
    if (cfgrfcittscadenzepersonali.getDataScadenza() == null) {
      pst.setNull(index++, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittscadenzepersonali.getDataScadenza()));
    }
    if (cfgrfcittscadenzepersonali.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittscadenzepersonali.getIdStatoRec());
    }
    pst.setString(index++, cfgrfcittscadenzepersonali.getUtenteIns());
    if (cfgrfcittscadenzepersonali.getDataIns() == null) {
      pst.setNull(index++, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittscadenzepersonali.getDataIns()));
    }
    pst.setString(index++, cfgrfcittscadenzepersonali.getOggetto());
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_R_FCITT_SCADENZE_PERSONALI set ID_SCADENZA= ?  , ID_T_EVENTO= ?  , ID_STATO= ?  , ID_FCITT= ?  , SCADENZA= ?  , DATA_SCADENZA= ?  , DATA_INVIO_MSG= ?  , ID_STATO_REC= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  , OGGETTO= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_R_FCITT_SCADENZE_PERSONALI ( ID_T_EVENTO,ID_STATO,ID_FCITT,SCADENZA,DATA_SCADENZA,ID_STATO_REC,UTENTE_INS,DATA_INS,OGGETTO,ID_SCADENZA ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,?    )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgrfcittscadenzepersonali.getIdScadenza());
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_R_FCITT_SCADENZE_PERSONALI ( ID_T_EVENTO,ID_STATO,ID_FCITT,SCADENZA,DATA_SCADENZA,DATA_INVIO_MSG,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG,OGGETTO ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgrfcittscadenzepersonali.getIdScadenza() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittscadenzepersonali.getIdScadenza());
    }
    if (cfgrfcittscadenzepersonali.getIdTEvento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittscadenzepersonali.getIdTEvento());
    }
    if (cfgrfcittscadenzepersonali.getIdStato() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittscadenzepersonali.getIdStato());
    }
    if (cfgrfcittscadenzepersonali.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittscadenzepersonali.getIdFcitt());
    }
    pst.setString(index++, cfgrfcittscadenzepersonali.getScadenza());
    if (cfgrfcittscadenzepersonali.getDataScadenza() == null) {
      pst.setNull(index++, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittscadenzepersonali.getDataScadenza()));
    }
    if (cfgrfcittscadenzepersonali.getDataInvioMsg() == null) {
      pst.setNull(index++, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittscadenzepersonali.getDataInvioMsg()));
    }
    if (cfgrfcittscadenzepersonali.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittscadenzepersonali.getIdStatoRec());
    }
    pst.setString(index++, cfgrfcittscadenzepersonali.getUtenteIns());
    if (cfgrfcittscadenzepersonali.getDataIns() == null) {
      pst.setNull(index++, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittscadenzepersonali.getDataIns()));
    }
    pst.setString(index++, cfgrfcittscadenzepersonali.getUtenteAgg());
    if (cfgrfcittscadenzepersonali.getDataAgg() == null) {
      pst.setNull(index++, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittscadenzepersonali.getDataAgg()));
    }
    pst.setString(index++, cfgrfcittscadenzepersonali.getOggetto());
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_SCADENZA"};
  }
}
