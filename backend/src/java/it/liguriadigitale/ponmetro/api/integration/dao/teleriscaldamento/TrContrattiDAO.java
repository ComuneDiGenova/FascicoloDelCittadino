package it.liguriadigitale.ponmetro.api.integration.dao.teleriscaldamento;

/**
 * TrContratti
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2022-11-07T15:37:26.398
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.TrContratti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrContrattiDAO extends AbstractTableDAO {

  private TrContratti trcontratti;

  public TrContrattiDAO(TrContratti trcontratti) {
    super();
    this.trcontratti = trcontratti;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from TR_CONTRATTI where NUM_CONTRATTO=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setString(index++, trcontratti.getNumContratto());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from TR_CONTRATTI where 1=1 ";
    if (trcontratti.getNumContratto() != null) sql += " and NUM_CONTRATTO  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (trcontratti.getNumContratto() != null) st.setString(index++, trcontratti.getNumContratto());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    TrContratti obj = new TrContratti();

    obj.setNumContratto(r.getString("NUM_CONTRATTO"));
    obj.setNumeroCliente(r.getString("NUMERO_CLIENTE"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from TR_CONTRATTI where NUM_CONTRATTO=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setString(index++, trcontratti.getNumContratto());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from TR_CONTRATTI where 1=1 ";
    if (trcontratti.getNumContratto() != null) sql += " and NUM_CONTRATTO  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (trcontratti.getNumContratto() != null) st.setString(index++, trcontratti.getNumContratto());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where NUM_CONTRATTO=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setString(index++, trcontratti.getNumContratto());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (trcontratti.getNumContratto() != null) sql += " and NUM_CONTRATTO  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (trcontratti.getNumContratto() != null) st.setString(index++, trcontratti.getNumContratto());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (trcontratti.getNumContratto() != null) {
      pst.setString(index++, trcontratti.getNumContratto());
    }
    if (trcontratti.getNumeroCliente() != null) {
      pst.setString(index++, trcontratti.getNumeroCliente());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE TR_CONTRATTI set ";

    if (trcontratti.getNumContratto() != null) {
      sql += " NUM_CONTRATTO= ? ";
      sql += " ,";
    }
    if (trcontratti.getNumeroCliente() != null) {
      sql += " NUMERO_CLIENTE= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql = "INSERT INTO TR_CONTRATTI ( NUM_CONTRATTO,NUMERO_CLIENTE ) VALUES (? ,?   )";
    return sql;
  }

  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, trcontratti.getNumContratto());
    pst.setString(index++, trcontratti.getNumeroCliente());
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql = "INSERT INTO TR_CONTRATTI ( NUMERO_CLIENTE ) VALUES (?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, trcontratti.getNumeroCliente());
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"NUM_CONTRATTO"};
  }
}
