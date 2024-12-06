package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * TCfgCatSez
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.config.db.TCfgCatSez;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TCfgCatSezDAO extends AbstractTableDAO {

  private TCfgCatSez tcfgcatsez;

  public TCfgCatSezDAO(TCfgCatSez tcfgcatsez) {
    super();
    this.tcfgcatsez = tcfgcatsez;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from T_CFG_CAT_SEZ where ID_SEZ=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, tcfgcatsez.getIdSez());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from T_CFG_CAT_SEZ where 1=1 ";
    if (tcfgcatsez.getIdSez() != null) sql += " and ID_SEZ  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (tcfgcatsez.getIdSez() != null) st.setLong(index++, tcfgcatsez.getIdSez());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    TCfgCatSez obj = new TCfgCatSez();

    obj.setIdSez(r.getLong("ID_SEZ"));

    obj.setDenominazioneSez(r.getString("DENOMINAZIONE_SEZ"));
    obj.setDescrizioneSez(r.getString("DESCRIZIONE_SEZ"));
    obj.setDataCatalogazioneSez(r.getTimestamp("DATA_CATALOGAZIONE_SEZ").toLocalDateTime());
    obj.setUriSez(r.getString("URI_SEZ"));
    obj.setOrdinamento(r.getLong("ORDINAMENTO"));

    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from T_CFG_CAT_SEZ where ID_SEZ=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, tcfgcatsez.getIdSez());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from T_CFG_CAT_SEZ where 1=1 ";
    if (tcfgcatsez.getIdSez() != null) sql += " and ID_SEZ  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (tcfgcatsez.getIdSez() != null) st.setLong(index++, tcfgcatsez.getIdSez());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_SEZ=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, tcfgcatsez.getIdSez());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (tcfgcatsez.getIdSez() != null) sql += " and ID_SEZ  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (tcfgcatsez.getIdSez() != null) st.setLong(index++, tcfgcatsez.getIdSez());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (tcfgcatsez.getIdSez() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatsez.getIdSez());
    }
    pst.setString(index++, tcfgcatsez.getDenominazioneSez());
    pst.setString(index++, tcfgcatsez.getDescrizioneSez());
    if (tcfgcatsez.getDataCatalogazioneSez() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(tcfgcatsez.getDataCatalogazioneSez()));
    }
    pst.setString(index++, tcfgcatsez.getUriSez());
    if (tcfgcatsez.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatsez.getOrdinamento());
    }
    if (tcfgcatsez.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, tcfgcatsez.getFlagAbilitazione());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE T_CFG_CAT_SEZ set ID_SEZ= ?  , DENOMINAZIONE_SEZ= ?  , DESCRIZIONE_SEZ= ?  , DATA_CATALOGAZIONE_SEZ= ?  , URI_SEZ= ?  , ORDINAMENTO= ?  , FLAG_ABILITAZIONE= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO T_CFG_CAT_SEZ ( ID_SEZ,DENOMINAZIONE_SEZ,DESCRIZIONE_SEZ,DATA_CATALOGAZIONE_SEZ,URI_SEZ,ORDINAMENTO,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO T_CFG_CAT_SEZ ( DENOMINAZIONE_SEZ,DESCRIZIONE_SEZ,DATA_CATALOGAZIONE_SEZ,URI_SEZ,ORDINAMENTO,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, tcfgcatsez.getDenominazioneSez());
    pst.setString(index++, tcfgcatsez.getDescrizioneSez());
    if (tcfgcatsez.getDataCatalogazioneSez() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(tcfgcatsez.getDataCatalogazioneSez()));
    }
    pst.setString(index++, tcfgcatsez.getUriSez());
    if (tcfgcatsez.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatsez.getOrdinamento());
    }
    if (tcfgcatsez.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, tcfgcatsez.getFlagAbilitazione());
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_SEZ"};
  }
}
