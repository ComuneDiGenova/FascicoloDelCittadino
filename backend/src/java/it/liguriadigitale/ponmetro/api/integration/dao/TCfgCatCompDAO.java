package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * TCfgCatComp
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.config.db.TCfgCatComp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TCfgCatCompDAO extends AbstractTableDAO {

  private TCfgCatComp tcfgcatcomp;

  public TCfgCatCompDAO(TCfgCatComp tcfgcatcomp) {
    super();
    this.tcfgcatcomp = tcfgcatcomp;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from T_CFG_CAT_COMP where ID_COMP=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, tcfgcatcomp.getIdComp());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from T_CFG_CAT_COMP where 1=1 ";
    if (tcfgcatcomp.getIdComp() != null) sql += " and ID_COMP  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (tcfgcatcomp.getIdComp() != null) st.setLong(index++, tcfgcatcomp.getIdComp());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    TCfgCatComp obj = new TCfgCatComp();

    obj.setIdComp(r.getLong("ID_COMP"));

    obj.setIdSez(r.getLong("ID_SEZ"));

    obj.setDenominazioneComp(r.getString("DENOMINAZIONE_COMP"));
    obj.setDescrizioneComp(r.getString("DESCRIZIONE_COMP"));
    obj.setUriComp(r.getString("URI_COMP"));
    obj.setDataCatalogazioneComp(r.getTimestamp("DATA_CATALOGAZIONE_COMP").toLocalDateTime());
    obj.setOrdinamento(r.getLong("ORDINAMENTO"));

    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from T_CFG_CAT_COMP where ID_COMP=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, tcfgcatcomp.getIdComp());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from T_CFG_CAT_COMP where 1=1 ";
    if (tcfgcatcomp.getIdComp() != null) sql += " and ID_COMP  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (tcfgcatcomp.getIdComp() != null) st.setLong(index++, tcfgcatcomp.getIdComp());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_COMP=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, tcfgcatcomp.getIdComp());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (tcfgcatcomp.getIdComp() != null) sql += " and ID_COMP  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (tcfgcatcomp.getIdComp() != null) st.setLong(index++, tcfgcatcomp.getIdComp());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (tcfgcatcomp.getIdComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatcomp.getIdComp());
    }
    if (tcfgcatcomp.getIdSez() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatcomp.getIdSez());
    }
    pst.setString(index++, tcfgcatcomp.getDenominazioneComp());
    pst.setString(index++, tcfgcatcomp.getDescrizioneComp());
    pst.setString(index++, tcfgcatcomp.getUriComp());
    if (tcfgcatcomp.getDataCatalogazioneComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(tcfgcatcomp.getDataCatalogazioneComp()));
    }
    if (tcfgcatcomp.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatcomp.getOrdinamento());
    }
    if (tcfgcatcomp.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, tcfgcatcomp.getFlagAbilitazione());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE T_CFG_CAT_COMP set ID_COMP= ?  , ID_SEZ= ?  , DENOMINAZIONE_COMP= ?  , DESCRIZIONE_COMP= ?  , URI_COMP= ?  , DATA_CATALOGAZIONE_COMP= ?  , ORDINAMENTO= ?  , FLAG_ABILITAZIONE= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO T_CFG_CAT_COMP ( ID_COMP,ID_SEZ,DENOMINAZIONE_COMP,DESCRIZIONE_COMP,URI_COMP,DATA_CATALOGAZIONE_COMP,ORDINAMENTO,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO T_CFG_CAT_COMP ( ID_SEZ,DENOMINAZIONE_COMP,DESCRIZIONE_COMP,URI_COMP,DATA_CATALOGAZIONE_COMP,ORDINAMENTO,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (tcfgcatcomp.getIdSez() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatcomp.getIdSez());
    }
    pst.setString(index++, tcfgcatcomp.getDenominazioneComp());
    pst.setString(index++, tcfgcatcomp.getDescrizioneComp());
    pst.setString(index++, tcfgcatcomp.getUriComp());
    if (tcfgcatcomp.getDataCatalogazioneComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(tcfgcatcomp.getDataCatalogazioneComp()));
    }
    if (tcfgcatcomp.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatcomp.getOrdinamento());
    }
    if (tcfgcatcomp.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, tcfgcatcomp.getFlagAbilitazione());
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_COMP"};
  }
}
