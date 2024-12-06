package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgTCatComp
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:42:09.057
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatComp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgTCatCompDAO extends AbstractTableDAO {

  private CfgTCatComp cfgtcatcomp;

  public CfgTCatCompDAO(CfgTCatComp cfgtcatcomp) {
    super();
    this.cfgtcatcomp = cfgtcatcomp;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_CAT_COMP where ID_COMP=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtcatcomp.getIdComp());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_CAT_COMP where 1=1 ";
    if (cfgtcatcomp.getIdComp() != null) sql += " and ID_COMP  = ?";
    if (cfgtcatcomp.getIdSez() != null) sql += " and ID_SEZ  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatcomp.getIdComp() != null) st.setLong(index++, cfgtcatcomp.getIdComp());
    if (cfgtcatcomp.getIdSez() != null) st.setLong(index++, cfgtcatcomp.getIdSez());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTCatComp obj = new CfgTCatComp();

    obj.setIdComp(r.getLong("ID_COMP"));

    obj.setIdSez(r.getLong("ID_SEZ"));

    obj.setDenominazioneComp(r.getString("DENOMINAZIONE_COMP"));
    obj.setDescrizioneComp(r.getString("DESCRIZIONE_COMP"));
    obj.setUriComp(r.getString("URI_COMP"));
    obj.setDataCatalogazioneComp(
        r.getTimestamp("DATA_CATALOGAZIONE_COMP") != null
            ? r.getTimestamp("DATA_CATALOGAZIONE_COMP").toLocalDateTime()
            : null);
    obj.setOrdinamento(r.getLong("ORDINAMENTO"));

    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    obj.setIdStatoRec(r.getBoolean("ID_STATO_REC"));
    obj.setDataInvioMsg(
        r.getTimestamp("DATA_INVIO_MSG") != null
            ? r.getTimestamp("DATA_INVIO_MSG").toLocalDateTime()
            : null);
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_CAT_COMP where ID_COMP=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtcatcomp.getIdComp());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_CAT_COMP where 1=1 ";
    if (cfgtcatcomp.getIdComp() != null) sql += " and ID_COMP  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatcomp.getIdComp() != null) st.setLong(index++, cfgtcatcomp.getIdComp());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_COMP=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgtcatcomp.getIdComp());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtcatcomp.getIdComp() != null) sql += " and ID_COMP  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtcatcomp.getIdComp() != null) st.setLong(index++, cfgtcatcomp.getIdComp());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcatcomp.getIdComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatcomp.getIdComp());
    }
    if (cfgtcatcomp.getIdSez() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatcomp.getIdSez());
    }
    pst.setString(index++, cfgtcatcomp.getDenominazioneComp());
    pst.setString(index++, cfgtcatcomp.getDescrizioneComp());
    pst.setString(index++, cfgtcatcomp.getUriComp());
    if (cfgtcatcomp.getDataCatalogazioneComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcatcomp.getDataCatalogazioneComp()));
    }
    if (cfgtcatcomp.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatcomp.getOrdinamento());
    }
    if (cfgtcatcomp.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatcomp.getFlagAbilitazione());
    }
    if (cfgtcatcomp.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatcomp.getIdStatoRec());
    }
    if (cfgtcatcomp.getDataInvioMsg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcatcomp.getDataInvioMsg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_T_CAT_COMP set ID_COMP= ?  , ID_SEZ= ?  , DENOMINAZIONE_COMP= ?  , DESCRIZIONE_COMP= ?  , URI_COMP= ?  , DATA_CATALOGAZIONE_COMP= ?  , ORDINAMENTO= ?  , FLAG_ABILITAZIONE= ?  , ID_STATO_REC= ?  , DATA_INVIO_MSG= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_CAT_COMP ( ID_COMP,ID_SEZ,DENOMINAZIONE_COMP,DESCRIZIONE_COMP,URI_COMP,DATA_CATALOGAZIONE_COMP,ORDINAMENTO,FLAG_ABILITAZIONE,ID_STATO_REC,DATA_INVIO_MSG ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_T_CAT_COMP ( ID_SEZ,DENOMINAZIONE_COMP,DESCRIZIONE_COMP,URI_COMP,DATA_CATALOGAZIONE_COMP,ORDINAMENTO,FLAG_ABILITAZIONE,ID_STATO_REC,DATA_INVIO_MSG ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcatcomp.getIdSez() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatcomp.getIdSez());
    }
    pst.setString(index++, cfgtcatcomp.getDenominazioneComp());
    pst.setString(index++, cfgtcatcomp.getDescrizioneComp());
    pst.setString(index++, cfgtcatcomp.getUriComp());
    if (cfgtcatcomp.getDataCatalogazioneComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcatcomp.getDataCatalogazioneComp()));
    }
    if (cfgtcatcomp.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatcomp.getOrdinamento());
    }
    if (cfgtcatcomp.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatcomp.getFlagAbilitazione());
    }
    if (cfgtcatcomp.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatcomp.getIdStatoRec());
    }
    if (cfgtcatcomp.getDataInvioMsg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtcatcomp.getDataInvioMsg()));
    }
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_COMP"};
  }
}
