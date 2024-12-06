package it.liguriadigitale.ponmetro.api.integration.dao.update;

/**
 * CfgTFcittRelatives
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractUpdateTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.family.db.CfgTFcittRelatives;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CfgTFcittRelativesUpdateDAO extends AbstractUpdateTableDAO {

  private CfgTFcittRelatives cfgtfcittrelatives;

  public CfgTFcittRelativesUpdateDAO(CfgTFcittRelatives cfgtfcittrelatives) {
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

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_FCITT_RELATIVES where ID_FCITT_RELATIVES=?";
    return sql;
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_FCITT_RELATIVES=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgtfcittrelatives.getIdFcittRelatives());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtfcittrelatives.getIdFcittRelatives() != null) sql += " and ID_FCITT_RELATIVES  = ?";
    if (cfgtfcittrelatives.getIdFcitt() != null) sql += " and ID_FCITT  = ?";
    if (cfgtfcittrelatives.getIdPersonParente() != null) sql += " and ID_PERSON_PARENTE  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtfcittrelatives.getIdFcittRelatives() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdFcittRelatives());
    if (cfgtfcittrelatives.getIdFcitt() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdFcitt());
    if (cfgtfcittrelatives.getIdPersonParente() != null)
      st.setLong(index++, cfgtfcittrelatives.getIdPersonParente());
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
    pst.setString(index++, cfgtfcittrelatives.getUtenteAgg());
    if (cfgtfcittrelatives.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtfcittrelatives.getDataAgg()));
    }
    if (cfgtfcittrelatives.getBloccoAutodichiarazione() == null) {
      pst.setNull(index++, java.sql.Types.INTEGER);
    } else {
      pst.setBoolean(index++, cfgtfcittrelatives.getBloccoAutodichiarazione());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_T_FCITT_RELATIVES set ID_FCITT_RELATIVES= ?  , ID_FCITT= ?  , ID_CAT_RELATIVES= ?  , ID_PERSON_PARENTE= ?  , ID_STATO_REC= ?  , UTENTE_AGG= ?  , DATA_AGG= ? , BLOCCO_AUTODICHIARAZIONE= ? ";
    return sql;
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_T_FCITT_RELATIVES ( ID_FCITT,ID_CAT_RELATIVES,ID_PERSON_PARENTE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
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

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_FCITT_RELATIVES"};
  }
}
