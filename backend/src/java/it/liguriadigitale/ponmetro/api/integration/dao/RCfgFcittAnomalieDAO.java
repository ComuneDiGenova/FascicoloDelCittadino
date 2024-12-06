package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * RCfgFcittAnomalie
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.config.db.RCfgFcittAnomalie;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RCfgFcittAnomalieDAO extends AbstractTableDAO {

  private RCfgFcittAnomalie rcfgfcittanomalie;

  public RCfgFcittAnomalieDAO(RCfgFcittAnomalie rcfgfcittanomalie) {
    super();
    this.rcfgfcittanomalie = rcfgfcittanomalie;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from R_CFG_FCITT_ANOMALIE where ID_FCITT_ANOMALIA=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, rcfgfcittanomalie.getIdFcittAnomalia());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from R_CFG_FCITT_ANOMALIE where 1=1 ";
    if (rcfgfcittanomalie.getIdFcittAnomalia() != null) sql += " and ID_FCITT_ANOMALIA  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (rcfgfcittanomalie.getIdFcittAnomalia() != null)
      st.setBigDecimal(index++, rcfgfcittanomalie.getIdFcittAnomalia());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    RCfgFcittAnomalie obj = new RCfgFcittAnomalie();

    obj.setIdFcittAnomalia(r.getBigDecimal("ID_FCITT_ANOMALIA"));
    obj.setIdTipoAnomalia(r.getLong("ID_TIPO_ANOMALIA"));

    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setDataSegnalazioneAnomalia(r.getTimestamp("DATA_SEGNALAZIONE_ANOMALIA").toLocalDateTime());
    obj.setTestoAnomalia(r.getString("TESTO_ANOMALIA"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from R_CFG_FCITT_ANOMALIE where ID_FCITT_ANOMALIA=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, rcfgfcittanomalie.getIdFcittAnomalia());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from R_CFG_FCITT_ANOMALIE where 1=1 ";
    if (rcfgfcittanomalie.getIdFcittAnomalia() != null) sql += " and ID_FCITT_ANOMALIA  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (rcfgfcittanomalie.getIdFcittAnomalia() != null)
      st.setBigDecimal(index++, rcfgfcittanomalie.getIdFcittAnomalia());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_FCITT_ANOMALIA=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setBigDecimal(index++, rcfgfcittanomalie.getIdFcittAnomalia());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (rcfgfcittanomalie.getIdFcittAnomalia() != null) sql += " and ID_FCITT_ANOMALIA  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (rcfgfcittanomalie.getIdFcittAnomalia() != null)
      st.setBigDecimal(index++, rcfgfcittanomalie.getIdFcittAnomalia());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, rcfgfcittanomalie.getIdFcittAnomalia());
    if (rcfgfcittanomalie.getIdTipoAnomalia() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittanomalie.getIdTipoAnomalia());
    }
    if (rcfgfcittanomalie.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittanomalie.getIdFcitt());
    }
    if (rcfgfcittanomalie.getDataSegnalazioneAnomalia() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(rcfgfcittanomalie.getDataSegnalazioneAnomalia()));
    }
    pst.setString(index++, rcfgfcittanomalie.getTestoAnomalia());
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE R_CFG_FCITT_ANOMALIE set ID_FCITT_ANOMALIA= ?  , ID_TIPO_ANOMALIA= ?  , ID_FCITT= ?  , DATA_SEGNALAZIONE_ANOMALIA= ?  , TESTO_ANOMALIA= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO R_CFG_FCITT_ANOMALIE ( ID_FCITT_ANOMALIA,ID_TIPO_ANOMALIA,ID_FCITT,DATA_SEGNALAZIONE_ANOMALIA,TESTO_ANOMALIA ) VALUES (? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO R_CFG_FCITT_ANOMALIE ( ID_TIPO_ANOMALIA,ID_FCITT,DATA_SEGNALAZIONE_ANOMALIA,TESTO_ANOMALIA ) VALUES (? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (rcfgfcittanomalie.getIdTipoAnomalia() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittanomalie.getIdTipoAnomalia());
    }
    if (rcfgfcittanomalie.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, rcfgfcittanomalie.getIdFcitt());
    }
    if (rcfgfcittanomalie.getDataSegnalazioneAnomalia() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(rcfgfcittanomalie.getDataSegnalazioneAnomalia()));
    }
    pst.setString(index++, rcfgfcittanomalie.getTestoAnomalia());
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_FCITT_ANOMALIA"};
  }
}
