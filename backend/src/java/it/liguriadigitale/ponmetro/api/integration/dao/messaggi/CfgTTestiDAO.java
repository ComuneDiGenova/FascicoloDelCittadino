package it.liguriadigitale.ponmetro.api.integration.dao.messaggi;

/**
 * CfgTTesti
 *
 * <p>WARNING! Automatically generated file with TableGen 2.0.7! Do not edit! created:
 * 2022-06-06T17:11:08.444
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.CfgTTesti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgTTestiDAO extends AbstractTableDAO {

  private CfgTTesti cfgttesti;

  public CfgTTestiDAO(CfgTTesti cfgttesti) {
    super();
    this.cfgttesti = cfgttesti;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_TESTI where ID_CFG_TESTI=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgttesti.getIdCfgTesti());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_TESTI where 1=1 ";
    if (cfgttesti.getIdCfgTesti() != null) sql += " and ID_CFG_TESTI  = ?";
    if (cfgttesti.getTipoTesto() != null) sql += " and TIPO_TESTO  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgttesti.getIdCfgTesti() != null) st.setLong(index++, cfgttesti.getIdCfgTesti());
    if (cfgttesti.getTipoTesto() != null) st.setString(index++, cfgttesti.getTipoTesto());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTTesti obj = new CfgTTesti();

    obj.setIdCfgTesti(r.getLong("ID_CFG_TESTI"));
    if (r.wasNull()) {
      obj.setIdCfgTesti(null);
    }

    obj.setTipoTesto(r.getString("TIPO_TESTO"));
    obj.setCfgValue(r.getString("CFG_VALUE"));
    obj.setClasseJava(r.getString("CLASSE_JAVA"));
    obj.setOrdine(r.getLong("ORDINE"));
    if (r.wasNull()) {
      obj.setOrdine(null);
    }

    obj.setDataValInizio(
        r.getTimestamp("DATA_VAL_INIZIO") != null
            ? r.getTimestamp("DATA_VAL_INIZIO").toLocalDateTime()
            : null);
    obj.setDataValFine(
        r.getTimestamp("DATA_VAL_FINE") != null
            ? r.getTimestamp("DATA_VAL_FINE").toLocalDateTime()
            : null);
    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(
        r.getTimestamp("DATA_INS") != null ? r.getTimestamp("DATA_INS").toLocalDateTime() : null);
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(
        r.getTimestamp("DATA_AGG") != null ? r.getTimestamp("DATA_AGG").toLocalDateTime() : null);
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_TESTI where ID_CFG_TESTI=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgttesti.getIdCfgTesti());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_TESTI where 1=1 ";
    if (cfgttesti.getIdCfgTesti() != null) sql += " and ID_CFG_TESTI  = ?";
    if (cfgttesti.getTipoTesto() != null) sql += " and TIPO_TESTO  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgttesti.getIdCfgTesti() != null) st.setLong(index++, cfgttesti.getIdCfgTesti());
    if (cfgttesti.getTipoTesto() != null) st.setString(index++, cfgttesti.getTipoTesto());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_CFG_TESTI=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgttesti.getIdCfgTesti());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgttesti.getIdCfgTesti() != null) sql += " and ID_CFG_TESTI  = ?";
    if (cfgttesti.getTipoTesto() != null) sql += " and TIPO_TESTO  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgttesti.getIdCfgTesti() != null) st.setLong(index++, cfgttesti.getIdCfgTesti());
    if (cfgttesti.getTipoTesto() != null) st.setString(index++, cfgttesti.getTipoTesto());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgttesti.getIdCfgTesti() != null) {
      pst.setLong(index++, cfgttesti.getIdCfgTesti());
    }
    if (cfgttesti.getTipoTesto() != null) {
      pst.setString(index++, cfgttesti.getTipoTesto());
    }
    if (cfgttesti.getCfgValue() != null) {
      pst.setString(index++, cfgttesti.getCfgValue());
    }
    if (cfgttesti.getClasseJava() != null) {
      pst.setString(index++, cfgttesti.getClasseJava());
    }
    if (cfgttesti.getOrdine() != null) {
      pst.setLong(index++, cfgttesti.getOrdine());
    }
    if (cfgttesti.getDataValInizio() != null) {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataValInizio()));
    }
    if (cfgttesti.getDataValFine() != null) {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataValFine()));
    }
    if (cfgttesti.getUtenteIns() != null) {
      pst.setString(index++, cfgttesti.getUtenteIns());
    }
    if (cfgttesti.getDataIns() != null) {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataIns()));
    }
    if (cfgttesti.getUtenteAgg() != null) {
      pst.setString(index++, cfgttesti.getUtenteAgg());
    }
    if (cfgttesti.getDataAgg() != null) {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataAgg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE CFG_T_TESTI set ";

    if (cfgttesti.getIdCfgTesti() != null) {
      sql += " ID_CFG_TESTI= ? ";
      sql += " ,";
    }
    if (cfgttesti.getTipoTesto() != null) {
      sql += " TIPO_TESTO= ? ";
      sql += " ,";
    }
    if (cfgttesti.getCfgValue() != null) {
      sql += " CFG_VALUE= ? ";
      sql += " ,";
    }
    if (cfgttesti.getClasseJava() != null) {
      sql += " CLASSE_JAVA= ? ";
      sql += " ,";
    }
    if (cfgttesti.getOrdine() != null) {
      sql += " ORDINE= ? ";
      sql += " ,";
    }
    if (cfgttesti.getDataValInizio() != null) {
      sql += " DATA_VAL_INIZIO= ? ";
      sql += " ,";
    }
    if (cfgttesti.getDataValFine() != null) {
      sql += " DATA_VAL_FINE= ? ";
      sql += " ,";
    }
    if (cfgttesti.getUtenteIns() != null) {
      sql += " UTENTE_INS= ? ";
      sql += " ,";
    }
    if (cfgttesti.getDataIns() != null) {
      sql += " DATA_INS= ? ";
      sql += " ,";
    }
    if (cfgttesti.getUtenteAgg() != null) {
      sql += " UTENTE_AGG= ? ";
      sql += " ,";
    }
    if (cfgttesti.getDataAgg() != null) {
      sql += " DATA_AGG= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_TESTI ( ID_CFG_TESTI,TIPO_TESTO,CFG_VALUE,CLASSE_JAVA,ORDINE,DATA_VAL_INIZIO,DATA_VAL_FINE,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgttesti.getIdCfgTesti() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, cfgttesti.getIdCfgTesti());
    }
    pst.setString(index++, cfgttesti.getTipoTesto());
    pst.setString(index++, cfgttesti.getCfgValue());
    pst.setString(index++, cfgttesti.getClasseJava());
    if (cfgttesti.getOrdine() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, cfgttesti.getOrdine());
    }
    if (cfgttesti.getDataValInizio() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataValInizio()));
    }
    if (cfgttesti.getDataValFine() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataValFine()));
    }
    pst.setString(index++, cfgttesti.getUtenteIns());
    if (cfgttesti.getDataIns() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataIns()));
    }
    pst.setString(index++, cfgttesti.getUtenteAgg());
    if (cfgttesti.getDataAgg() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataAgg()));
    }
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_T_TESTI ( TIPO_TESTO,CFG_VALUE,CLASSE_JAVA,ORDINE,DATA_VAL_INIZIO,DATA_VAL_FINE,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, cfgttesti.getTipoTesto());
    pst.setString(index++, cfgttesti.getCfgValue());
    pst.setString(index++, cfgttesti.getClasseJava());
    if (cfgttesti.getOrdine() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, cfgttesti.getOrdine());
    }
    if (cfgttesti.getDataValInizio() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataValInizio()));
    }
    if (cfgttesti.getDataValFine() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataValFine()));
    }
    pst.setString(index++, cfgttesti.getUtenteIns());
    if (cfgttesti.getDataIns() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataIns()));
    }
    pst.setString(index++, cfgttesti.getUtenteAgg());
    if (cfgttesti.getDataAgg() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataAgg()));
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_CFG_TESTI"};
  }
}
