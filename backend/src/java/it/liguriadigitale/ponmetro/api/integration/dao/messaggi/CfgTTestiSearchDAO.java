package it.liguriadigitale.ponmetro.api.integration.dao.messaggi;

import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.CfgTTesti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgTTestiSearchDAO extends AbstractSearchDAO {

  private CfgTTesti cfgttesti;

  public CfgTTestiSearchDAO(CfgTTesti cfgttesti) {
    this.cfgttesti = cfgttesti;
  }

  @Override
  protected Object getFromResultSet(ResultSet r) throws SQLException {

    CfgTTesti obj = new CfgTTesti();

    obj.setIdCfgTesti(r.getLong("ID_CFG_TESTI"));
    if (r.wasNull()) {
      obj.setIdCfgTesti(null);
    }

    obj.setTipoTesto(r.getString("TIPO_TESTO"));
    obj.setCfgValue(r.getString("CFG_VALUE"));
    obj.setClasseJava(r.getString("CLASSE_JAVA"));
    obj.setOrdine(r.getLong("ORDINE"));
    if (r.wasNull()) {
      obj.setOrdine(null);
    }

    obj.setDataValInizio(
        r.getTimestamp("DATA_VAL_INIZIO") != null
            ? r.getTimestamp("DATA_VAL_INIZIO").toLocalDateTime()
            : null);
    obj.setDataValFine(
        r.getTimestamp("DATA_VAL_FINE") != null
            ? r.getTimestamp("DATA_VAL_FINE").toLocalDateTime()
            : null);
    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(
        r.getTimestamp("DATA_INS") != null ? r.getTimestamp("DATA_INS").toLocalDateTime() : null);
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(
        r.getTimestamp("DATA_AGG") != null ? r.getTimestamp("DATA_AGG").toLocalDateTime() : null);
    return obj;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_TESTI where 1=1 ";
    if (cfgttesti.getClasseJava() != null) sql += " and CLASSE_JAVA  = ?";
    if (cfgttesti.getDataValFine() != null) {
      sql += " and (DATA_VAL_INIZIO IS NULL OR TRUNC(DATA_VAL_INIZIO) <= TRUNC(?))";
      sql += " and (DATA_VAL_FINE IS NULL OR TRUNC(DATA_VAL_FINE) >= TRUNC(?))";
    }
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgttesti.getClasseJava() != null) pst.setString(index++, cfgttesti.getClasseJava());
    if (cfgttesti.getDataValFine() != null) {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataValFine()));
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgttesti.getDataValFine()));
    }
  }
}
