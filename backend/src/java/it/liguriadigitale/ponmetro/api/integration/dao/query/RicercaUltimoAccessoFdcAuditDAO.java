package it.liguriadigitale.ponmetro.api.integration.dao.query;

import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.audit.FdcAudit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RicercaUltimoAccessoFdcAuditDAO extends AbstractSearchDAO {

  private FdcAudit ricerca;

  public RicercaUltimoAccessoFdcAuditDAO(FdcAudit ricerca) {
    super();
    this.ricerca = ricerca;
  }

  @Override
  protected Object getFromResultSet(ResultSet r) throws SQLException {
    FdcAudit obj = new FdcAudit();

    obj.setIdFcitt(r.getBigDecimal("ID_FCITT"));
    obj.setNomePagina(r.getString("NOME_PAGINA"));
    obj.setAmbiente(r.getString("AMBIENTE"));
    obj.setSessionId(r.getString("SESSION_ID"));
    obj.setAutorizzato(r.getBoolean("AUTORIZZATO"));
    obj.setTimeStamp(
        r.getTimestamp("TIME_STAMP") != null
            ? r.getTimestamp("TIME_STAMP").toLocalDateTime()
            : null);
    obj.setTipoUtente(r.getLong("TIPO_UTENTE"));

    return obj;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "select * from fdc_audit t " + " where 1=1" + " and t.time_stamp > sysdate -1 ";
    if (ricerca.getIdFcitt() != null) sql = sql + " and t.id_fcitt=?";
    if (ricerca.getNomePagina() != null) sql = sql + " and t.nome_pagina=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (ricerca.getIdFcitt() != null) st.setBigDecimal(index++, ricerca.getIdFcitt());
    if (ricerca.getNomePagina() != null) st.setString(index++, ricerca.getNomePagina());
  }
}
