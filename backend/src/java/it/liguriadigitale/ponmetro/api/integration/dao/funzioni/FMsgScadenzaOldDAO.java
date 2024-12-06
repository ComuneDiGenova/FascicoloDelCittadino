package it.liguriadigitale.ponmetro.api.integration.dao.funzioni;

import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.ScadenzaPassata;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FMsgScadenzaOldDAO extends AbstractSearchDAO {

  private ScadenzaPassata scadenza;

  public FMsgScadenzaOldDAO(ScadenzaPassata scadenza) {
    super();
    this.scadenza = scadenza;
  }

  @Override
  protected Object getFromResultSet(ResultSet paramResultSet) throws SQLException {

    return paramResultSet.getLong(1);
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    return "select LDFDCADM.F_MSG_SCADENZA_OLD(?,?) from dual";
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement paramPreparedStatement)
      throws SQLException {
    paramPreparedStatement.setLong(1, scadenza.getIdFCitt());
    paramPreparedStatement.setLong(2, scadenza.getIdScadenza());
  }
}
