package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgTCatWidg
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:42:10.640
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatWidg;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgTCatWidgDAO extends AbstractTableDAO {

  private CfgTCatWidg cfgtcatwidg;

  public CfgTCatWidgDAO(CfgTCatWidg cfgtcatwidg) {
    super();
    this.cfgtcatwidg = cfgtcatwidg;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_CAT_WIDG where ID_WIDG=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtcatwidg.getIdWidg());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_CAT_WIDG where 1=1 ";
    if (cfgtcatwidg.getIdWidg() != null) sql += " and ID_WIDG  = ?";
    if (cfgtcatwidg.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatwidg.getIdWidg() != null) st.setLong(index++, cfgtcatwidg.getIdWidg());
    if (cfgtcatwidg.getIdFunz() != null) st.setLong(index++, cfgtcatwidg.getIdFunz());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTCatWidg obj = new CfgTCatWidg();

    obj.setIdWidg(r.getLong("ID_WIDG"));

    obj.setIdFunz(r.getLong("ID_FUNZ"));

    obj.setDenominazioneWidg(r.getString("DENOMINAZIONE_WIDG"));
    obj.setDescrizioneWidg(r.getString("DESCRIZIONE_WIDG"));
    obj.setUriWidg(r.getString("URI_WIDG"));
    obj.setDataCatalogazioneWidg(
        r.getTimestamp("DATA_CATALOGAZIONE_WIDG") != null
            ? r.getTimestamp("DATA_CATALOGAZIONE_WIDG").toLocalDateTime()
            : null);
    obj.setOrdinamento(r.getLong("ORDINAMENTO"));

    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    obj.setFlagDefault(r.getBoolean("FLAG_DEFAULT"));
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_CAT_WIDG where ID_WIDG=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtcatwidg.getIdWidg());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_CAT_WIDG where 1=1 ";
    if (cfgtcatwidg.getIdWidg() != null) sql += " and ID_WIDG  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatwidg.getIdWidg() != null) st.setLong(index++, cfgtcatwidg.getIdWidg());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_WIDG=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgtcatwidg.getIdWidg());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtcatwidg.getIdWidg() != null) sql += " and ID_WIDG  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtcatwidg.getIdWidg() != null) st.setLong(index++, cfgtcatwidg.getIdWidg());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcatwidg.getIdWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatwidg.getIdWidg());
    }
    if (cfgtcatwidg.getIdFunz() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatwidg.getIdFunz());
    }
    pst.setString(index++, cfgtcatwidg.getDenominazioneWidg());
    pst.setString(index++, cfgtcatwidg.getDescrizioneWidg());
    pst.setString(index++, cfgtcatwidg.getUriWidg());
    if (cfgtcatwidg.getDataCatalogazioneWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcatwidg.getDataCatalogazioneWidg()));
    }
    if (cfgtcatwidg.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatwidg.getOrdinamento());
    }
    if (cfgtcatwidg.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatwidg.getFlagAbilitazione());
    }
    if (cfgtcatwidg.getFlagDefault() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatwidg.getFlagDefault());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_T_CAT_WIDG set ID_WIDG= ?  , ID_FUNZ= ?  , DENOMINAZIONE_WIDG= ?  , DESCRIZIONE_WIDG= ?  , URI_WIDG= ?  , DATA_CATALOGAZIONE_WIDG= ?  , ORDINAMENTO= ?  , FLAG_ABILITAZIONE= ?  , FLAG_DEFAULT= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_CAT_WIDG ( ID_WIDG,ID_FUNZ,DENOMINAZIONE_WIDG,DESCRIZIONE_WIDG,URI_WIDG,DATA_CATALOGAZIONE_WIDG,ORDINAMENTO,FLAG_ABILITAZIONE,FLAG_DEFAULT ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_T_CAT_WIDG ( ID_FUNZ,DENOMINAZIONE_WIDG,DESCRIZIONE_WIDG,URI_WIDG,DATA_CATALOGAZIONE_WIDG,ORDINAMENTO,FLAG_ABILITAZIONE,FLAG_DEFAULT ) VALUES (? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcatwidg.getIdFunz() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatwidg.getIdFunz());
    }
    pst.setString(index++, cfgtcatwidg.getDenominazioneWidg());
    pst.setString(index++, cfgtcatwidg.getDescrizioneWidg());
    pst.setString(index++, cfgtcatwidg.getUriWidg());
    if (cfgtcatwidg.getDataCatalogazioneWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcatwidg.getDataCatalogazioneWidg()));
    }
    if (cfgtcatwidg.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatwidg.getOrdinamento());
    }
    if (cfgtcatwidg.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatwidg.getFlagAbilitazione());
    }
    if (cfgtcatwidg.getFlagDefault() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatwidg.getFlagDefault());
    }
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_WIDG"};
  }
}
