package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgTCatFunz
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:42:09.533
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunz;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgTCatFunzDAO extends AbstractTableDAO {

  private CfgTCatFunz cfgtcatfunz;

  public CfgTCatFunzDAO(CfgTCatFunz cfgtcatfunz) {
    super();
    this.cfgtcatfunz = cfgtcatfunz;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_CAT_FUNZ where ID_FUNZ=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtcatfunz.getIdFunz());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_CAT_FUNZ where 1=1 ";
    if (cfgtcatfunz.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    if (cfgtcatfunz.getIdComp() != null) sql += " and ID_COMP  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatfunz.getIdFunz() != null) st.setLong(index++, cfgtcatfunz.getIdFunz());
    if (cfgtcatfunz.getIdComp() != null) st.setLong(index++, cfgtcatfunz.getIdComp());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTCatFunz obj = new CfgTCatFunz();

    obj.setIdFunz(r.getLong("ID_FUNZ"));

    obj.setIdComp(r.getLong("ID_COMP"));

    obj.setDenominazioneFunz(r.getString("DENOMINAZIONE_FUNZ"));
    obj.setDescrizioneFunz(r.getString("DESCRIZIONE_FUNZ"));
    obj.setWicketLabelIdStd(r.getString("WICKET_LABEL_ID_STD"));
    obj.setWicketLabelIdAlt(r.getString("WICKET_LABEL_ID_ALT"));
    obj.setClassePaginaStd(r.getString("CLASSE_PAGINA_STD"));
    obj.setClassePaginaAlt(r.getString("CLASSE_PAGINA_ALT"));
    obj.setWicketTitleStd(r.getString("WICKET_TITLE_STD"));
    obj.setWicketTitleAlt(r.getString("WICKET_TITLE_ALT"));
    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    obj.setIdStatoRec(r.getBoolean("ID_STATO_REC"));
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_CAT_FUNZ where ID_FUNZ=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtcatfunz.getIdFunz());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_CAT_FUNZ where 1=1 ";
    if (cfgtcatfunz.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatfunz.getIdFunz() != null) st.setLong(index++, cfgtcatfunz.getIdFunz());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_FUNZ=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgtcatfunz.getIdFunz());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtcatfunz.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtcatfunz.getIdFunz() != null) st.setLong(index++, cfgtcatfunz.getIdFunz());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcatfunz.getIdFunz() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatfunz.getIdFunz());
    }
    if (cfgtcatfunz.getIdComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatfunz.getIdComp());
    }
    pst.setString(index++, cfgtcatfunz.getDenominazioneFunz());
    pst.setString(index++, cfgtcatfunz.getDescrizioneFunz());
    pst.setString(index++, cfgtcatfunz.getWicketLabelIdStd());
    pst.setString(index++, cfgtcatfunz.getWicketLabelIdAlt());
    pst.setString(index++, cfgtcatfunz.getClassePaginaStd());
    pst.setString(index++, cfgtcatfunz.getClassePaginaAlt());
    pst.setString(index++, cfgtcatfunz.getWicketTitleStd());
    pst.setString(index++, cfgtcatfunz.getWicketTitleAlt());
    if (cfgtcatfunz.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatfunz.getFlagAbilitazione());
    }
    if (cfgtcatfunz.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatfunz.getIdStatoRec());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_T_CAT_FUNZ set ID_FUNZ= ?  , ID_COMP= ?  , DENOMINAZIONE_FUNZ= ?  , DESCRIZIONE_FUNZ= ?  , WICKET_LABEL_ID_STD= ?  , WICKET_LABEL_ID_ALT= ?  , CLASSE_PAGINA_STD= ?  , CLASSE_PAGINA_ALT= ?  , WICKET_TITLE_STD= ?  , WICKET_TITLE_ALT= ?  , FLAG_ABILITAZIONE= ?  , ID_STATO_REC= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_CAT_FUNZ ( ID_FUNZ,ID_COMP,DENOMINAZIONE_FUNZ,DESCRIZIONE_FUNZ,WICKET_LABEL_ID_STD,WICKET_LABEL_ID_ALT,CLASSE_PAGINA_STD,CLASSE_PAGINA_ALT,WICKET_TITLE_STD,WICKET_TITLE_ALT,FLAG_ABILITAZIONE,ID_STATO_REC ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_T_CAT_FUNZ ( ID_COMP,DENOMINAZIONE_FUNZ,DESCRIZIONE_FUNZ,WICKET_LABEL_ID_STD,WICKET_LABEL_ID_ALT,CLASSE_PAGINA_STD,CLASSE_PAGINA_ALT,WICKET_TITLE_STD,WICKET_TITLE_ALT,FLAG_ABILITAZIONE,ID_STATO_REC ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcatfunz.getIdComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatfunz.getIdComp());
    }
    pst.setString(index++, cfgtcatfunz.getDenominazioneFunz());
    pst.setString(index++, cfgtcatfunz.getDescrizioneFunz());
    pst.setString(index++, cfgtcatfunz.getWicketLabelIdStd());
    pst.setString(index++, cfgtcatfunz.getWicketLabelIdAlt());
    pst.setString(index++, cfgtcatfunz.getClassePaginaStd());
    pst.setString(index++, cfgtcatfunz.getClassePaginaAlt());
    pst.setString(index++, cfgtcatfunz.getWicketTitleStd());
    pst.setString(index++, cfgtcatfunz.getWicketTitleAlt());
    if (cfgtcatfunz.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatfunz.getFlagAbilitazione());
    }
    if (cfgtcatfunz.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtcatfunz.getIdStatoRec());
    }
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_FUNZ"};
  }
}
