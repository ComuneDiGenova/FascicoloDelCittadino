package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgTCatSez
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:42:10.291
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatSez;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgTCatSezDAO extends AbstractTableDAO {

  private CfgTCatSez cfgtcatsez;

  public CfgTCatSezDAO(CfgTCatSez cfgtcatsez) {
    super();
    this.cfgtcatsez = cfgtcatsez;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_CAT_SEZ where ID_SEZ=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtcatsez.getIdSez());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_CAT_SEZ where 1=1 ";
    if (cfgtcatsez.getIdSez() != null) sql += " and ID_SEZ  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatsez.getIdSez() != null) st.setLong(index++, cfgtcatsez.getIdSez());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTCatSez obj = new CfgTCatSez();

    obj.setIdSez(r.getLong("ID_SEZ"));

    obj.setDenominazioneSez(r.getString("DENOMINAZIONE_SEZ"));
    obj.setDescrizioneSez(r.getString("DESCRIZIONE_SEZ"));
    obj.setDataCatalogazioneSez(
        r.getTimestamp("DATA_CATALOGAZIONE_SEZ") != null
            ? r.getTimestamp("DATA_CATALOGAZIONE_SEZ").toLocalDateTime()
            : null);
    obj.setUriSez(r.getString("URI_SEZ"));
    obj.setOrdinamento(r.getLong("ORDINAMENTO"));

    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    obj.setIdStatoRec(r.getBoolean("ID_STATO_REC"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_CAT_SEZ where ID_SEZ=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtcatsez.getIdSez());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_CAT_SEZ where 1=1 ";
    if (cfgtcatsez.getIdSez() != null) sql += " and ID_SEZ  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatsez.getIdSez() != null) st.setLong(index++, cfgtcatsez.getIdSez());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_SEZ=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgtcatsez.getIdSez());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtcatsez.getIdSez() != null) sql += " and ID_SEZ  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtcatsez.getIdSez() != null) st.setLong(index++, cfgtcatsez.getIdSez());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcatsez.getIdSez() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatsez.getIdSez());
    }
    pst.setString(index++, cfgtcatsez.getDenominazioneSez());
    pst.setString(index++, cfgtcatsez.getDescrizioneSez());
    if (cfgtcatsez.getDataCatalogazioneSez() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcatsez.getDataCatalogazioneSez()));
    }
    pst.setString(index++, cfgtcatsez.getUriSez());
    if (cfgtcatsez.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatsez.getOrdinamento());
    }
    if (cfgtcatsez.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatsez.getFlagAbilitazione());
    }
    if (cfgtcatsez.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatsez.getIdStatoRec());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_T_CAT_SEZ set ID_SEZ= ?  , DENOMINAZIONE_SEZ= ?  , DESCRIZIONE_SEZ= ?  , DATA_CATALOGAZIONE_SEZ= ?  , URI_SEZ= ?  , ORDINAMENTO= ?  , FLAG_ABILITAZIONE= ?  , ID_STATO_REC= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_CAT_SEZ ( ID_SEZ,DENOMINAZIONE_SEZ,DESCRIZIONE_SEZ,DATA_CATALOGAZIONE_SEZ,URI_SEZ,ORDINAMENTO,FLAG_ABILITAZIONE,ID_STATO_REC ) VALUES (? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_T_CAT_SEZ ( DENOMINAZIONE_SEZ,DESCRIZIONE_SEZ,DATA_CATALOGAZIONE_SEZ,URI_SEZ,ORDINAMENTO,FLAG_ABILITAZIONE,ID_STATO_REC ) VALUES (? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, cfgtcatsez.getDenominazioneSez());
    pst.setString(index++, cfgtcatsez.getDescrizioneSez());
    if (cfgtcatsez.getDataCatalogazioneSez() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcatsez.getDataCatalogazioneSez()));
    }
    pst.setString(index++, cfgtcatsez.getUriSez());
    if (cfgtcatsez.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatsez.getOrdinamento());
    }
    if (cfgtcatsez.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatsez.getFlagAbilitazione());
    }
    if (cfgtcatsez.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatsez.getIdStatoRec());
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_SEZ"};
  }
}
