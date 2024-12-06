package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * GeoworksTServizi
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-10-24T12:30:37.243
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.geoworks.GeoworksTServizi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeoworksTServiziDAO extends AbstractTableDAO {

  private GeoworksTServizi geoworkstservizi;

  public GeoworksTServiziDAO(GeoworksTServizi geoworkstservizi) {
    super();
    this.geoworkstservizi = geoworkstservizi;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from GEOWORKS_T_SERVIZI where ID_SERVIZIO=? and ID_FUNZ=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, geoworkstservizi.getIdServizio());
    st.setBigDecimal(index++, geoworkstservizi.getIdFunz());
    st.setString(index++, geoworkstservizi.getTipoFunz());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from GEOWORKS_T_SERVIZI where 1=1 ";
    if (geoworkstservizi.getIdServizio() != null) sql += " and ID_SERVIZIO  = ?";
    if (geoworkstservizi.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    if (geoworkstservizi.getTipoFunz() != null) sql += " and TIPO_FUNZ  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (geoworkstservizi.getIdServizio() != null)
      st.setBigDecimal(index++, geoworkstservizi.getIdServizio());
    if (geoworkstservizi.getIdFunz() != null)
      st.setBigDecimal(index++, geoworkstservizi.getIdFunz());
    if (geoworkstservizi.getTipoFunz() != null)
      st.setString(index++, geoworkstservizi.getTipoFunz());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    GeoworksTServizi obj = new GeoworksTServizi();

    obj.setIdServizio(r.getBigDecimal("ID_SERVIZIO"));
    obj.setIdFunz(r.getBigDecimal("ID_FUNZ"));
    obj.setDescrizioneServizio(r.getString("DESCRIZIONE_SERVIZIO"));
    obj.setTipoFunz(r.getString("TIPO_FUNZ"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from GEOWORKS_T_SERVIZI where ID_SERVIZIO=? and ID_FUNZ=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, geoworkstservizi.getIdServizio());
    st.setBigDecimal(index++, geoworkstservizi.getIdFunz());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from GEOWORKS_T_SERVIZI where 1=1 ";
    if (geoworkstservizi.getIdServizio() != null) sql += " and ID_SERVIZIO  = ?";
    if (geoworkstservizi.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (geoworkstservizi.getIdServizio() != null)
      st.setBigDecimal(index++, geoworkstservizi.getIdServizio());
    if (geoworkstservizi.getIdFunz() != null)
      st.setBigDecimal(index++, geoworkstservizi.getIdFunz());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_SERVIZIO=? and ID_FUNZ=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setBigDecimal(index++, geoworkstservizi.getIdServizio());
    st.setBigDecimal(index++, geoworkstservizi.getIdFunz());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (geoworkstservizi.getIdServizio() != null) sql += " and ID_SERVIZIO  = ?";
    if (geoworkstservizi.getIdFunz() != null) sql += " and ID_FUNZ  = ?";

    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (geoworkstservizi.getIdServizio() != null)
      st.setBigDecimal(index++, geoworkstservizi.getIdServizio());
    if (geoworkstservizi.getIdFunz() != null)
      st.setBigDecimal(index++, geoworkstservizi.getIdFunz());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (geoworkstservizi.getIdServizio() != null) {
      pst.setBigDecimal(index++, geoworkstservizi.getIdServizio());
    }
    if (geoworkstservizi.getIdFunz() != null) {
      pst.setBigDecimal(index++, geoworkstservizi.getIdFunz());
    }
    if (geoworkstservizi.getDescrizioneServizio() != null) {
      pst.setString(index++, geoworkstservizi.getDescrizioneServizio());
    }
    if (geoworkstservizi.getTipoFunz() != null) {
      pst.setString(index++, geoworkstservizi.getTipoFunz());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE GEOWORKS_T_SERVIZI set ";

    if (geoworkstservizi.getIdServizio() != null) {
      sql += " ID_SERVIZIO= ? ";
      sql += " ,";
    }
    if (geoworkstservizi.getIdFunz() != null) {
      sql += " ID_FUNZ= ? ";
      sql += " ,";
    }
    if (geoworkstservizi.getDescrizioneServizio() != null) {
      sql += " DESCRIZIONE_SERVIZIO= ? ";
      sql += " ,";
    }
    if (geoworkstservizi.getTipoFunz() != null) {
      sql += " TIPO_FUNZ= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO GEOWORKS_T_SERVIZI ( ID_SERVIZIO,ID_FUNZ,DESCRIZIONE_SERVIZIO,TIPO_FUNZ ) VALUES (? ,? ,? ,?   )";
    return sql;
  }

  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, geoworkstservizi.getIdServizio());
    pst.setBigDecimal(index++, geoworkstservizi.getIdFunz());
    pst.setString(index++, geoworkstservizi.getDescrizioneServizio());
    pst.setString(index++, geoworkstservizi.getTipoFunz());
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO GEOWORKS_T_SERVIZI ( DESCRIZIONE_SERVIZIO,TIPO_FUNZ ) VALUES (? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, geoworkstservizi.getDescrizioneServizio());
    pst.setString(index++, geoworkstservizi.getTipoFunz());
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_SERVIZIO", "ID_FUNZ"};
  }
}
