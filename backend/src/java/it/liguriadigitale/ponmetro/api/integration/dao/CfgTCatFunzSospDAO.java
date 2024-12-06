package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgTCatFunzSosp
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:42:09.916
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunzSosp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgTCatFunzSospDAO extends AbstractTableDAO {

  private CfgTCatFunzSosp cfgtcatfunzsosp;

  public CfgTCatFunzSospDAO(CfgTCatFunzSosp cfgtcatfunzsosp) {
    super();
    this.cfgtcatfunzsosp = cfgtcatfunzsosp;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_CAT_FUNZ_SOSP where ID_FUNZ_SOSP=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtcatfunzsosp.getIdFunzSosp());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_CAT_FUNZ_SOSP where 1=1 ";
    if (cfgtcatfunzsosp.getIdFunzSosp() != null) sql += " and ID_FUNZ_SOSP  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatfunzsosp.getIdFunzSosp() != null)
      st.setLong(index++, cfgtcatfunzsosp.getIdFunzSosp());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTCatFunzSosp obj = new CfgTCatFunzSosp();

    obj.setIdFunzSosp(r.getLong("ID_FUNZ_SOSP"));

    obj.setIdFunz(r.getLong("ID_FUNZ"));

    obj.setDataInizioSosp(
        r.getTimestamp("DATA_INIZIO_SOSP") != null
            ? r.getTimestamp("DATA_INIZIO_SOSP").toLocalDateTime()
            : null);
    obj.setDataFineSosp(
        r.getTimestamp("DATA_FINE_SOSP") != null
            ? r.getTimestamp("DATA_FINE_SOSP").toLocalDateTime()
            : null);
    obj.setTipoSosp(r.getLong("TIPO_SOSP"));

    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_CAT_FUNZ_SOSP where ID_FUNZ_SOSP=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtcatfunzsosp.getIdFunzSosp());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_CAT_FUNZ_SOSP where 1=1 ";
    if (cfgtcatfunzsosp.getIdFunzSosp() != null) sql += " and ID_FUNZ_SOSP  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatfunzsosp.getIdFunzSosp() != null)
      st.setLong(index++, cfgtcatfunzsosp.getIdFunzSosp());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_FUNZ_SOSP=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgtcatfunzsosp.getIdFunzSosp());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtcatfunzsosp.getIdFunzSosp() != null) sql += " and ID_FUNZ_SOSP  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtcatfunzsosp.getIdFunzSosp() != null)
      st.setLong(index++, cfgtcatfunzsosp.getIdFunzSosp());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcatfunzsosp.getIdFunzSosp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatfunzsosp.getIdFunzSosp());
    }
    if (cfgtcatfunzsosp.getIdFunz() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatfunzsosp.getIdFunz());
    }
    if (cfgtcatfunzsosp.getDataInizioSosp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcatfunzsosp.getDataInizioSosp()));
    }
    if (cfgtcatfunzsosp.getDataFineSosp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcatfunzsosp.getDataFineSosp()));
    }
    if (cfgtcatfunzsosp.getTipoSosp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatfunzsosp.getTipoSosp());
    }
    if (cfgtcatfunzsosp.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatfunzsosp.getFlagAbilitazione());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_T_CAT_FUNZ_SOSP set ID_FUNZ_SOSP= ?  , ID_FUNZ= ?  , DATA_INIZIO_SOSP= ?  , DATA_FINE_SOSP= ?  , TIPO_SOSP= ?  , FLAG_ABILITAZIONE= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_CAT_FUNZ_SOSP ( ID_FUNZ_SOSP,ID_FUNZ,DATA_INIZIO_SOSP,DATA_FINE_SOSP,TIPO_SOSP,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_T_CAT_FUNZ_SOSP ( ID_FUNZ,DATA_INIZIO_SOSP,DATA_FINE_SOSP,TIPO_SOSP,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcatfunzsosp.getIdFunz() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatfunzsosp.getIdFunz());
    }
    if (cfgtcatfunzsosp.getDataInizioSosp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcatfunzsosp.getDataInizioSosp()));
    }
    if (cfgtcatfunzsosp.getDataFineSosp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcatfunzsosp.getDataFineSosp()));
    }
    if (cfgtcatfunzsosp.getTipoSosp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatfunzsosp.getTipoSosp());
    }
    if (cfgtcatfunzsosp.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatfunzsosp.getFlagAbilitazione());
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_FUNZ_SOSP"};
  }
}
