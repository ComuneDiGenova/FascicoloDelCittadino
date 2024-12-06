package it.liguriadigitale.ponmetro.api.integration.dao.teleriscaldamento;

/**
 * TrClienti
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2022-11-07T15:35:43.739
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.TrClienti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrClientiDAO extends AbstractTableDAO {

  private TrClienti trclienti;

  public TrClientiDAO(TrClienti trclienti) {
    super();
    this.trclienti = trclienti;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from TR_CLIENTI where NUMERO_CLIENTE=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setString(index++, trclienti.getNumeroCliente());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from TR_CLIENTI where 1=1 ";
    if (trclienti.getNumeroCliente() != null) sql += " and NUMERO_CLIENTE  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (trclienti.getNumeroCliente() != null) st.setString(index++, trclienti.getNumeroCliente());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    TrClienti obj = new TrClienti();

    obj.setNumeroCliente(r.getString("NUMERO_CLIENTE"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from TR_CLIENTI where NUMERO_CLIENTE=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setString(index++, trclienti.getNumeroCliente());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from TR_CLIENTI where 1=1 ";
    if (trclienti.getNumeroCliente() != null) sql += " and NUMERO_CLIENTE  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (trclienti.getNumeroCliente() != null) st.setString(index++, trclienti.getNumeroCliente());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where NUMERO_CLIENTE=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setString(index++, trclienti.getNumeroCliente());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (trclienti.getNumeroCliente() != null) sql += " and NUMERO_CLIENTE  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (trclienti.getNumeroCliente() != null) st.setString(index++, trclienti.getNumeroCliente());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (trclienti.getNumeroCliente() != null) {
      pst.setString(index++, trclienti.getNumeroCliente());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE TR_CLIENTI set ";

    if (trclienti.getNumeroCliente() != null) {
      sql += " NUMERO_CLIENTE= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql = "INSERT INTO TR_CLIENTI ( NUMERO_CLIENTE ) VALUES (?   )";
    return sql;
  }

  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, trclienti.getNumeroCliente());
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql = "INSERT INTO TR_CLIENTI (  ) VALUES (  )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"NUMERO_CLIENTE"};
  }
}
