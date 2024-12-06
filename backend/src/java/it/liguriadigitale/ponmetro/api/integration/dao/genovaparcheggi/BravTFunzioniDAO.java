package it.liguriadigitale.ponmetro.api.integration.dao.genovaparcheggi;

/**
 * BravTFunzioni
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-11-14T12:14:51.224
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi.BravTFunzioni;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BravTFunzioniDAO extends AbstractTableDAO {

  private BravTFunzioni bravtfunzioni;

  public BravTFunzioniDAO(BravTFunzioni bravtfunzioni) {
    super();
    this.bravtfunzioni = bravtfunzioni;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from BRAV_T_FUNZIONI where ID_FUNZ=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, bravtfunzioni.getIdFunz());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from BRAV_T_FUNZIONI where 1=1 ";
    if (bravtfunzioni.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    if (bravtfunzioni.getTipoFunz() != null) sql += " and TIPO_FUNZ  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (bravtfunzioni.getIdFunz() != null) st.setBigDecimal(index++, bravtfunzioni.getIdFunz());
    if (bravtfunzioni.getTipoFunz() != null) st.setString(index++, bravtfunzioni.getTipoFunz());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    BravTFunzioni obj = new BravTFunzioni();

    obj.setIdFunz(r.getBigDecimal("ID_FUNZ"));
    obj.setTipoFunz(r.getString("TIPO_FUNZ"));
    obj.setDescrizioneFunz(r.getString("DESCRIZIONE_FUNZ"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from BRAV_T_FUNZIONI where ID_FUNZ=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, bravtfunzioni.getIdFunz());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from BRAV_T_FUNZIONI where 1=1 ";
    if (bravtfunzioni.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (bravtfunzioni.getIdFunz() != null) st.setBigDecimal(index++, bravtfunzioni.getIdFunz());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_FUNZ=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setBigDecimal(index++, bravtfunzioni.getIdFunz());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (bravtfunzioni.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (bravtfunzioni.getIdFunz() != null) st.setBigDecimal(index++, bravtfunzioni.getIdFunz());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (bravtfunzioni.getIdFunz() != null) {
      pst.setBigDecimal(index++, bravtfunzioni.getIdFunz());
    }
    if (bravtfunzioni.getTipoFunz() != null) {
      pst.setString(index++, bravtfunzioni.getTipoFunz());
    }
    if (bravtfunzioni.getDescrizioneFunz() != null) {
      pst.setString(index++, bravtfunzioni.getDescrizioneFunz());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE BRAV_T_FUNZIONI set ";

    if (bravtfunzioni.getIdFunz() != null) {
      sql += " ID_FUNZ= ? ";
      sql += " ,";
    }
    if (bravtfunzioni.getTipoFunz() != null) {
      sql += " TIPO_FUNZ= ? ";
      sql += " ,";
    }
    if (bravtfunzioni.getDescrizioneFunz() != null) {
      sql += " DESCRIZIONE_FUNZ= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO BRAV_T_FUNZIONI ( ID_FUNZ,TIPO_FUNZ,DESCRIZIONE_FUNZ ) VALUES (? ,? ,?   )";
    return sql;
  }

  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, bravtfunzioni.getIdFunz());
    pst.setString(index++, bravtfunzioni.getTipoFunz());
    pst.setString(index++, bravtfunzioni.getDescrizioneFunz());
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql = "INSERT INTO BRAV_T_FUNZIONI ( TIPO_FUNZ,DESCRIZIONE_FUNZ ) VALUES (? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, bravtfunzioni.getTipoFunz());
    pst.setString(index++, bravtfunzioni.getDescrizioneFunz());
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_FUNZ"};
  }
}
