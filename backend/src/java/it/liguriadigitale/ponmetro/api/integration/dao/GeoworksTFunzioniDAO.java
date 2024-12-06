package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * GeoworksTFunzioni
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-10-24T10:39:22.250
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.geoworks.GeoworksTFunzioni;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeoworksTFunzioniDAO extends AbstractTableDAO {

  private GeoworksTFunzioni geoworkstfunzioni;

  public GeoworksTFunzioniDAO(GeoworksTFunzioni geoworkstfunzioni) {
    super();
    this.geoworkstfunzioni = geoworkstfunzioni;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from GEOWORKS_T_FUNZIONI where ID_FUNZ=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, geoworkstfunzioni.getIdFunz());
    st.setString(index++, geoworkstfunzioni.getTipoFunz());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from GEOWORKS_T_FUNZIONI where 1=1 ";
    if (geoworkstfunzioni.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    if (geoworkstfunzioni.getTipoFunz() != null) sql += " and TIPO_FUNZ  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (geoworkstfunzioni.getIdFunz() != null)
      st.setBigDecimal(index++, geoworkstfunzioni.getIdFunz());
    if (geoworkstfunzioni.getTipoFunz() != null)
      st.setString(index++, geoworkstfunzioni.getTipoFunz());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    GeoworksTFunzioni obj = new GeoworksTFunzioni();

    obj.setIdFunz(r.getBigDecimal("ID_FUNZ"));
    obj.setTipoFunz(r.getString("TIPO_FUNZ"));
    obj.setDescrizioneFunz(r.getString("DESCRIZIONE_FUNZ"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from GEOWORKS_T_FUNZIONI where ID_FUNZ=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, geoworkstfunzioni.getIdFunz());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from GEOWORKS_T_FUNZIONI where 1=1 ";
    if (geoworkstfunzioni.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (geoworkstfunzioni.getIdFunz() != null)
      st.setBigDecimal(index++, geoworkstfunzioni.getIdFunz());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_FUNZ=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setBigDecimal(index++, geoworkstfunzioni.getIdFunz());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (geoworkstfunzioni.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (geoworkstfunzioni.getIdFunz() != null)
      st.setBigDecimal(index++, geoworkstfunzioni.getIdFunz());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (geoworkstfunzioni.getIdFunz() != null) {
      pst.setBigDecimal(index++, geoworkstfunzioni.getIdFunz());
    }
    if (geoworkstfunzioni.getTipoFunz() != null) {
      pst.setString(index++, geoworkstfunzioni.getTipoFunz());
    }
    if (geoworkstfunzioni.getDescrizioneFunz() != null) {
      pst.setString(index++, geoworkstfunzioni.getDescrizioneFunz());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE GEOWORKS_T_FUNZIONI set ";

    if (geoworkstfunzioni.getIdFunz() != null) {
      sql += " ID_FUNZ= ? ";
      sql += " ,";
    }
    if (geoworkstfunzioni.getTipoFunz() != null) {
      sql += " TIPO_FUNZ= ? ";
      sql += " ,";
    }
    if (geoworkstfunzioni.getDescrizioneFunz() != null) {
      sql += " DESCRIZIONE_FUNZ= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO GEOWORKS_T_FUNZIONI ( ID_FUNZ,TIPO_FUNZ,DESCRIZIONE_FUNZ ) VALUES (? ,? ,?   )";
    return sql;
  }

  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, geoworkstfunzioni.getIdFunz());
    pst.setString(index++, geoworkstfunzioni.getTipoFunz());
    pst.setString(index++, geoworkstfunzioni.getDescrizioneFunz());
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql = "INSERT INTO GEOWORKS_T_FUNZIONI ( TIPO_FUNZ,DESCRIZIONE_FUNZ ) VALUES (? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, geoworkstfunzioni.getTipoFunz());
    pst.setString(index++, geoworkstfunzioni.getDescrizioneFunz());
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_FUNZ"};
  }
}
