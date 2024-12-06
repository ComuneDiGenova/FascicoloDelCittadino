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

public class CfgRFcittScadenzePersonaliUpdateDAO extends AbstractTableDAO {

  private CfgRFcittScadenzePersonali cfgrfcittscadenzepersonali;

  public CfgRFcittScadenzePersonaliUpdateDAO(
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
    if (cfgrfcittscadenzepersonali.getIdFcitt() != null) sql += " and ID_FCITT   = ?";
    if (cfgrfcittscadenzepersonali.getIdTEvento() != null) sql += " and ID_T_EVENTO    = ?";
    if (cfgrfcittscadenzepersonali.getOggetto() != null) sql += " and OGGETTO    = ?";
    if (cfgrfcittscadenzepersonali.getIdStato() != null) sql += " and ID_STATO   <> ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgrfcittscadenzepersonali.getIdFcitt() != null)
      st.setLong(index++, cfgrfcittscadenzepersonali.getIdFcitt());
    if (cfgrfcittscadenzepersonali.getIdTEvento() != null)
      st.setLong(index++, cfgrfcittscadenzepersonali.getIdTEvento());
    if (cfgrfcittscadenzepersonali.getOggetto() != null)
      st.setString(index++, cfgrfcittscadenzepersonali.getOggetto());
    if (cfgrfcittscadenzepersonali.getIdStato() != null)
      st.setLong(index++, cfgrfcittscadenzepersonali.getIdStato());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;

    if (cfgrfcittscadenzepersonali.getIdStato() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgrfcittscadenzepersonali.getIdStato());
    }
    if (cfgrfcittscadenzepersonali.getUtenteAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setString(index++, cfgrfcittscadenzepersonali.getUtenteAgg());
    }
    if (cfgrfcittscadenzepersonali.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(cfgrfcittscadenzepersonali.getDataAgg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_R_FCITT_SCADENZE_PERSONALI set ID_STATO= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {
    throw new NotImplementedException();
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
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
    throw new NotImplementedException();
  }
}
