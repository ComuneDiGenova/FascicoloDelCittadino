package it.liguriadigitale.ponmetro.api.integration.dao.genovaparcheggi;

/**
 * BravTPermessi
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-11-14T12:14:51.506
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi.BravTPermessi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BravTPermessiDAO extends AbstractTableDAO {

  private BravTPermessi bravtpermessi;

  public BravTPermessiDAO(BravTPermessi bravtpermessi) {
    super();
    this.bravtpermessi = bravtpermessi;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from BRAV_T_PERMESSI where ID_PERMESSO=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, bravtpermessi.getIdPermesso());
    st.setString(index++, bravtpermessi.getTipoFunz());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from BRAV_T_PERMESSI where 1=1 ";
    if (bravtpermessi.getIdPermesso() != null) sql += " and ID_PERMESSO  = ?";
    if (bravtpermessi.getTipoFunz() != null) sql += " and TIPO_FUNZ  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (bravtpermessi.getIdPermesso() != null)
      st.setBigDecimal(index++, bravtpermessi.getIdPermesso());
    if (bravtpermessi.getTipoFunz() != null) st.setString(index++, bravtpermessi.getTipoFunz());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    BravTPermessi obj = new BravTPermessi();

    obj.setIdPermesso(r.getBigDecimal("ID_PERMESSO"));
    obj.setIdFunz(r.getBigDecimal("ID_FUNZ"));
    obj.setTipoFunz(r.getString("TIPO_FUNZ"));
    obj.setDescrizioneFunz(r.getString("DESCRIZIONE_FUNZ"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from BRAV_T_PERMESSI where ID_PERMESSO=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, bravtpermessi.getIdPermesso());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from BRAV_T_PERMESSI where 1=1 ";
    if (bravtpermessi.getIdPermesso() != null) sql += " and ID_PERMESSO  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (bravtpermessi.getIdPermesso() != null)
      st.setBigDecimal(index++, bravtpermessi.getIdPermesso());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_PERMESSO=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setBigDecimal(index++, bravtpermessi.getIdPermesso());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (bravtpermessi.getIdPermesso() != null) sql += " and ID_PERMESSO  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (bravtpermessi.getIdPermesso() != null)
      st.setBigDecimal(index++, bravtpermessi.getIdPermesso());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (bravtpermessi.getIdPermesso() != null) {
      pst.setBigDecimal(index++, bravtpermessi.getIdPermesso());
    }
    if (bravtpermessi.getIdFunz() != null) {
      pst.setBigDecimal(index++, bravtpermessi.getIdFunz());
    }
    if (bravtpermessi.getTipoFunz() != null) {
      pst.setString(index++, bravtpermessi.getTipoFunz());
    }
    if (bravtpermessi.getDescrizioneFunz() != null) {
      pst.setString(index++, bravtpermessi.getDescrizioneFunz());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE BRAV_T_PERMESSI set ";

    if (bravtpermessi.getIdPermesso() != null) {
      sql += " ID_PERMESSO= ? ";
      sql += " ,";
    }
    if (bravtpermessi.getIdFunz() != null) {
      sql += " ID_FUNZ= ? ";
      sql += " ,";
    }
    if (bravtpermessi.getTipoFunz() != null) {
      sql += " TIPO_FUNZ= ? ";
      sql += " ,";
    }
    if (bravtpermessi.getDescrizioneFunz() != null) {
      sql += " DESCRIZIONE_FUNZ= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO BRAV_T_PERMESSI ( ID_PERMESSO,ID_FUNZ,TIPO_FUNZ,DESCRIZIONE_FUNZ ) VALUES (? ,? ,? ,?   )";
    return sql;
  }

  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, bravtpermessi.getIdPermesso());
    pst.setBigDecimal(index++, bravtpermessi.getIdFunz());
    pst.setString(index++, bravtpermessi.getTipoFunz());
    pst.setString(index++, bravtpermessi.getDescrizioneFunz());
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO BRAV_T_PERMESSI ( ID_FUNZ,TIPO_FUNZ,DESCRIZIONE_FUNZ ) VALUES (? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, bravtpermessi.getIdFunz());
    pst.setString(index++, bravtpermessi.getTipoFunz());
    pst.setString(index++, bravtpermessi.getDescrizioneFunz());
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_PERMESSO"};
  }
}
