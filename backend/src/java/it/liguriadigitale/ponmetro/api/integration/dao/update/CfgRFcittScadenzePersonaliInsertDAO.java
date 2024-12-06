package it.liguriadigitale.ponmetro.api.integration.dao.update;

/**
 * CfgRFcittScadenzePersonali
 *
 * <p>UPDATE
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.CfgRFcittScadenzePersonali;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang.NotImplementedException;

public class CfgRFcittScadenzePersonaliInsertDAO extends AbstractTableDAO {

  private CfgRFcittScadenzePersonali cfgrfcittscadenzepersonali;

  public CfgRFcittScadenzePersonaliInsertDAO(
      CfgRFcittScadenzePersonali cfgrfcittscadenzepersonali) {
    super();
    this.cfgrfcittscadenzepersonali = cfgrfcittscadenzepersonali;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    throw new NotImplementedException();
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    throw new NotImplementedException();
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  protected String getSqlDeleteByKey() {
    throw new NotImplementedException();
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  protected String getSqlDeleteByWhere() {
    throw new NotImplementedException();
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  protected String getSqlUpdateByKey() {
    throw new NotImplementedException();
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  protected String getSqlUpdateByWhere() {
    throw new NotImplementedException();
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    throw new NotImplementedException();
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
    if (cfgrfcittscadenzepersonali.getDataInvioMsg() == null) {
      pst.setNull(index++, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittscadenzepersonali.getDataInvioMsg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    throw new NotImplementedException();
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_R_FCITT_SCADENZE_PERSONALI ( ID_T_EVENTO,ID_STATO,ID_FCITT,SCADENZA,DATA_SCADENZA,ID_STATO_REC,UTENTE_INS,DATA_INS,OGGETTO,DATA_INVIO_MSG,ID_SCADENZA) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,?,?    )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgrfcittscadenzepersonali.getIdScadenza());
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    throw new NotImplementedException();
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_SCADENZA"};
  }
}
