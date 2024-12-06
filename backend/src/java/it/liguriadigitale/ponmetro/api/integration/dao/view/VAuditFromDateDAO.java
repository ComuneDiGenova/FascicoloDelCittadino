package it.liguriadigitale.ponmetro.api.integration.dao.view;

/**
 * VFdcAudit
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2021-02-17T10:01:33.092
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.audit.VFdcAudit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VAuditFromDateDAO extends AbstractSearchDAO {

  private VFdcAudit vfdcaudit;

  public VAuditFromDateDAO(VFdcAudit vfdcaudit) {
    super();
    this.vfdcaudit = vfdcaudit;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from V_FDC_AUDIT a WHERE 1=1 ";
    if (vfdcaudit.getTimeStamp() != null) {
      sql = sql + "AND TRUNC(time_stamp) > ?";
    }

    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    if (vfdcaudit.getTimeStamp() != null) {
      st.setDate(1, java.sql.Date.valueOf(vfdcaudit.getTimeStamp().toLocalDate()));
    }
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    VFdcAudit obj = new VFdcAudit();

    obj.setIdFcitt(r.getBigDecimal("ID_FCITT"));
    obj.setNomePagina(r.getString("NOME_PAGINA"));
    obj.setAmbiente(r.getString("AMBIENTE"));
    obj.setSessionId(r.getString("SESSION_ID"));
    obj.setAutorizzato(r.getBoolean("AUTORIZZATO"));
    obj.setTimeStamp(
        r.getTimestamp("TIME_STAMP") != null
            ? r.getTimestamp("TIME_STAMP").toLocalDateTime()
            : null);
    obj.setTipoUtente(r.getBigDecimal("TIPO_UTENTE"));
    obj.setDescrizioneTipoUtente(r.getString("DESCRIZIONE_TIPO_UTENTE"));
    obj.setDescrizioneFunz(r.getString("DESCRIZIONE_FUNZ"));
    obj.setIdFunz(r.getBigDecimal("ID_FUNZ"));
    obj.setDenominazioneComp(r.getString("DENOMINAZIONE_COMP"));
    obj.setIdComp(r.getBigDecimal("ID_COMP"));
    obj.setDescrizioneSez(r.getString("DESCRIZIONE_SEZ"));
    obj.setIdSez(r.getBigDecimal("ID_SEZ"));
    return obj;
  }
}
