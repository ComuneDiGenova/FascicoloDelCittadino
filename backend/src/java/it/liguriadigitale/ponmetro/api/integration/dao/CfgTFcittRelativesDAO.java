package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgTFcittRelatives
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.family.db.CfgTFcittRelatives;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgTFcittRelativesDAO extends AbstractTableDAO {

  private CfgTFcittRelatives cfgtfcittrelatives;

  public CfgTFcittRelativesDAO(CfgTFcittRelatives cfgtfcittrelatives) {
    super();
    this.cfgtfcittrelatives = cfgtfcittrelatives;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_FCITT_RELATIVES where ID_FCITT_RELATIVES=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtfcittrelatives.getIdFcittRelatives());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_FCITT_RELATIVES where 1=1 AND ID_STATO_REC=1 ";
    if (cfgtfcittrelatives.getIdFcittRelatives() != null) sql += " and ID_FCITT_RELATIVES  = ?";
    if (cfgtfcittrelatives.getIdFcitt() != null) sql += " and ID_FCITT  = ?";
    if (cfgtfcittrelatives.getIdPersonParente() != null) sql += " and ID_PERSON_PARENTE  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtfcittrelatives.getIdFcittRelatives() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdFcittRelatives());
    if (cfgtfcittrelatives.getIdFcitt() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdFcitt());
    if (cfgtfcittrelatives.getIdPersonParente() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdPersonParente());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTFcittRelatives obj = new CfgTFcittRelatives();

    obj.setIdFcittRelatives(r.getLong("ID_FCITT_RELATIVES"));

    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setIdCatRelatives(r.getLong("ID_CAT_RELATIVES"));

    obj.setIdPersonParente(r.getLong("ID_PERSON_PARENTE"));

    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(r.getTimestamp("DATA_INS").toLocalDateTime());
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(r.getTimestamp("DATA_AGG").toLocalDateTime());
    obj.setBloccoAutodichiarazione(r.getBoolean("BLOCCO_AUTODICHIARAZIONE"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_FCITT_RELATIVES where ID_FCITT_RELATIVES=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtfcittrelatives.getIdFcittRelatives());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_FCITT_RELATIVES where 1=1 ";
    if (cfgtfcittrelatives.getIdFcittRelatives() != null) sql += " and ID_FCITT_RELATIVES  = ?";
    if (cfgtfcittrelatives.getIdFcitt() != null) sql += " and ID_FCITT  = ?";
    if (cfgtfcittrelatives.getIdPersonParente() != null) sql += " and ID_PERSON_PARENTE  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtfcittrelatives.getIdFcittRelatives() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdFcittRelatives());
    if (cfgtfcittrelatives.getIdFcitt() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdFcitt());
    if (cfgtfcittrelatives.getIdPersonParente() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdPersonParente());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_FCITT_RELATIVES=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgtfcittrelatives.getIdFcittRelatives());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtfcittrelatives.getIdFcittRelatives() != null) sql += " and ID_FCITT_RELATIVES  = ?";
    if (cfgtfcittrelatives.getIdFcitt() != null) sql += " and ID_FCITT  = ?";
    if (cfgtfcittrelatives.getIdPersonParente() != null) sql += " and ID_PERSON_PARENTE  = ?";
    if (cfgtfcittrelatives.getIdCatRelatives() != null) sql += " and ID_CAT_RELATIVES  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtfcittrelatives.getIdFcittRelatives() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdFcittRelatives());
    if (cfgtfcittrelatives.getIdFcitt() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdFcitt());
    if (cfgtfcittrelatives.getIdPersonParente() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdPersonParente());
    if (cfgtfcittrelatives.getIdCatRelatives() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdCatRelatives());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtfcittrelatives.getIdFcittRelatives() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcittrelatives.getIdFcittRelatives());
    }
    if (cfgtfcittrelatives.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcittrelatives.getIdFcitt());
    }
    if (cfgtfcittrelatives.getIdCatRelatives() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcittrelatives.getIdCatRelatives());
    }
    if (cfgtfcittrelatives.getIdPersonParente() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcittrelatives.getIdPersonParente());
    }
    if (cfgtfcittrelatives.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcittrelatives.getIdStatoRec());
    }
    pst.setString(index++, cfgtfcittrelatives.getUtenteIns());
    if (cfgtfcittrelatives.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtfcittrelatives.getDataIns()));
    }
    pst.setString(index++, cfgtfcittrelatives.getUtenteAgg());
    if (cfgtfcittrelatives.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtfcittrelatives.getDataAgg()));
    }
    if (cfgtfcittrelatives.getBloccoAutodichiarazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtfcittrelatives.getBloccoAutodichiarazione());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_T_FCITT_RELATIVES set ID_FCITT_RELATIVES= ?  , ID_FCITT= ?  , ID_CAT_RELATIVES= ?  , ID_PERSON_PARENTE= ?  , ID_STATO_REC= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  , BLOCCO_AUTODICHIARAZIONE= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_FCITT_RELATIVES ( ID_FCITT_RELATIVES,ID_FCITT,ID_CAT_RELATIVES,ID_PERSON_PARENTE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG,BLOCCO_AUTODICHIARAZIONE ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_T_FCITT_RELATIVES ( ID_FCITT,ID_CAT_RELATIVES,ID_PERSON_PARENTE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG,BLOCCO_AUTODICHIARAZIONE ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtfcittrelatives.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcittrelatives.getIdFcitt());
    }
    if (cfgtfcittrelatives.getIdCatRelatives() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcittrelatives.getIdCatRelatives());
    }
    if (cfgtfcittrelatives.getIdPersonParente() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcittrelatives.getIdPersonParente());
    }
    if (cfgtfcittrelatives.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtfcittrelatives.getIdStatoRec());
    }
    pst.setString(index++, cfgtfcittrelatives.getUtenteIns());
    if (cfgtfcittrelatives.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtfcittrelatives.getDataIns()));
    }
    pst.setString(index++, cfgtfcittrelatives.getUtenteAgg());
    if (cfgtfcittrelatives.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtfcittrelatives.getDataAgg()));
    }
    if (cfgtfcittrelatives.getBloccoAutodichiarazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, cfgtfcittrelatives.getBloccoAutodichiarazione());
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_FCITT_RELATIVES"};
  }
}
