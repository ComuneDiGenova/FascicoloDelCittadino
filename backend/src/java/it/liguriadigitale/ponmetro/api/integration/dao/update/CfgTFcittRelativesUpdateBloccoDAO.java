package it.liguriadigitale.ponmetro.api.integration.dao.update;

import it.liguriadigitale.framework.integration.dao.AbstractUpdateTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.family.db.CfgTFcittRelatives;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.lang.NotImplementedException;

public class CfgTFcittRelativesUpdateBloccoDAO extends AbstractUpdateTableDAO {

  private CfgTFcittRelatives cfgtfcittrelatives;

  public CfgTFcittRelativesUpdateBloccoDAO(CfgTFcittRelatives cfgtfcittrelatives) {
    super();
    this.cfgtfcittrelatives = cfgtfcittrelatives;
  }

  @Override
  protected String getSqlUpdateByWhere() {
    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtfcittrelatives.getIdFcittRelatives() != null) sql += " and ID_FCITT_RELATIVES  = ?";
    if (cfgtfcittrelatives.getIdFcitt() != null) sql += " and ID_FCITT  = ?";
    if (cfgtfcittrelatives.getIdPersonParente() != null) sql += " and ID_PERSON_PARENTE  = ?";
    return sql;
  }

  private String getUpdateFields() {
    String sql =
        "UPDATE CFG_T_FCITT_RELATIVES set  UTENTE_AGG= ?  , DATA_AGG= ?  , BLOCCO_AUTODICHIARAZIONE= ?  ";
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
    pst.setString(index++, cfgtfcittrelatives.getUtenteAgg());
    if (cfgtfcittrelatives.getDataAgg() == null) {
      pst.setNull(index++, java.sql.Types.DATE);
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

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_FCITT_RELATIVES"};
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {
    throw new NotImplementedException();
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement arg0) throws SQLException {
    throw new NotImplementedException();
  }
}
