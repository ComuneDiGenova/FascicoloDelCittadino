package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgTCatRelatives
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.family.db.CfgTCatRelatives;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgTCatRelativesDAO extends AbstractTableDAO {

  private CfgTCatRelatives cfgtcatrelatives;

  public CfgTCatRelativesDAO(CfgTCatRelatives cfgtcatrelatives) {
    super();
    this.cfgtcatrelatives = cfgtcatrelatives;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_CAT_RELATIVES where ID_CAT_RELATIVES=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtcatrelatives.getIdCatRelatives());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_CAT_RELATIVES where 1=1 ";
    if (cfgtcatrelatives.getIdCatRelatives() != null) sql += " and ID_CAT_RELATIVES  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatrelatives.getIdCatRelatives() != null)
      st.setLong(index++, cfgtcatrelatives.getIdCatRelatives());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTCatRelatives obj = new CfgTCatRelatives();

    obj.setIdCatRelatives(r.getLong("ID_CAT_RELATIVES"));

    obj.setDenominazione(r.getString("DENOMINAZIONE"));
    obj.setDescrizione(r.getString("DESCRIZIONE"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_CAT_RELATIVES where ID_CAT_RELATIVES=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtcatrelatives.getIdCatRelatives());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_CAT_RELATIVES where 1=1 ";
    if (cfgtcatrelatives.getIdCatRelatives() != null) sql += " and ID_CAT_RELATIVES  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatrelatives.getIdCatRelatives() != null)
      st.setLong(index++, cfgtcatrelatives.getIdCatRelatives());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_CAT_RELATIVES=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgtcatrelatives.getIdCatRelatives());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtcatrelatives.getIdCatRelatives() != null) sql += " and ID_CAT_RELATIVES  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtcatrelatives.getIdCatRelatives() != null)
      st.setLong(index++, cfgtcatrelatives.getIdCatRelatives());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcatrelatives.getIdCatRelatives() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtcatrelatives.getIdCatRelatives());
    }
    pst.setString(index++, cfgtcatrelatives.getDenominazione());
    pst.setString(index++, cfgtcatrelatives.getDescrizione());
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_T_CAT_RELATIVES set ID_CAT_RELATIVES= ?  , DENOMINAZIONE= ?  , DESCRIZIONE= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_CAT_RELATIVES ( ID_CAT_RELATIVES,DENOMINAZIONE,DESCRIZIONE ) VALUES (? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql = "INSERT INTO CFG_T_CAT_RELATIVES ( DENOMINAZIONE,DESCRIZIONE ) VALUES (? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, cfgtcatrelatives.getDenominazione());
    pst.setString(index++, cfgtcatrelatives.getDescrizione());
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_CAT_RELATIVES"};
  }
}
