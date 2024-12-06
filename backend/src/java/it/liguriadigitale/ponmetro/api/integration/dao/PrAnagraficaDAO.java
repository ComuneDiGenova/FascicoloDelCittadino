package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * PrAnagrafica
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.privacy.db.PrAnagrafica;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrAnagraficaDAO extends AbstractTableDAO {

  private PrAnagrafica pranagrafica;

  public PrAnagraficaDAO(PrAnagrafica pranagrafica) {
    super();
    this.pranagrafica = pranagrafica;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from PR_ANAGRAFICA where ID_ANAGRAFICA=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, pranagrafica.getIdAnagrafica());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from PR_ANAGRAFICA where 1=1 ";
    if (pranagrafica.getIdAnagrafica() != null) sql += " and ID_ANAGRAFICA  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (pranagrafica.getIdAnagrafica() != null) st.setLong(index++, pranagrafica.getIdAnagrafica());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    PrAnagrafica obj = new PrAnagrafica();

    obj.setIdAnagrafica(r.getLong("ID_ANAGRAFICA"));

    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(r.getTimestamp("DATA_INS").toLocalDateTime());
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(r.getTimestamp("DATA_AGG").toLocalDateTime());
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from PR_ANAGRAFICA where ID_ANAGRAFICA=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, pranagrafica.getIdAnagrafica());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from PR_ANAGRAFICA where 1=1 ";
    if (pranagrafica.getIdAnagrafica() != null) sql += " and ID_ANAGRAFICA  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (pranagrafica.getIdAnagrafica() != null) st.setLong(index++, pranagrafica.getIdAnagrafica());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_ANAGRAFICA=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, pranagrafica.getIdAnagrafica());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (pranagrafica.getIdAnagrafica() != null) sql += " and ID_ANAGRAFICA  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (pranagrafica.getIdAnagrafica() != null) st.setLong(index++, pranagrafica.getIdAnagrafica());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (pranagrafica.getIdAnagrafica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, pranagrafica.getIdAnagrafica());
    }
    if (pranagrafica.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, pranagrafica.getIdStatoRec());
    }
    pst.setString(index++, pranagrafica.getUtenteIns());
    if (pranagrafica.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(pranagrafica.getDataIns()));
    }
    pst.setString(index++, pranagrafica.getUtenteAgg());
    if (pranagrafica.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(pranagrafica.getDataAgg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE PR_ANAGRAFICA set ID_ANAGRAFICA= ?  , ID_STATO_REC= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO PR_ANAGRAFICA ( ID_ANAGRAFICA,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO PR_ANAGRAFICA ( ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (pranagrafica.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, pranagrafica.getIdStatoRec());
    }
    pst.setString(index++, pranagrafica.getUtenteIns());
    if (pranagrafica.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(pranagrafica.getDataIns()));
    }
    pst.setString(index++, pranagrafica.getUtenteAgg());
    if (pranagrafica.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(pranagrafica.getDataAgg()));
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_ANAGRAFICA"};
  }
}
