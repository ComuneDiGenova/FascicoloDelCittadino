package it.liguriadigitale.ponmetro.api.integration.dao.query;

import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.PrivacyRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMaxVersionePrivacyDAO extends AbstractSearchDAO {

  private PrivacyRequest request;

  public GetMaxVersionePrivacyDAO(PrivacyRequest request) {
    super();
    this.request = request;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql =
        "select max(pv.id_privacy_ver) valore from pr_privacy_ver pv "
            + "inner join pr_servizi s on pv.id_privacy = s.id_privacy "
            + "where 1=1 ";
    if (request.getCodApplicazione() != null) sql += "and s.cod_servizio = ?";
    sql += " group by pv.id_privacy";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    if (request.getCodApplicazione() != null) st.setString(1, request.getCodApplicazione());
  }

  @Override
  protected Object getFromResultSet(ResultSet rs) throws SQLException {
    return rs.getLong("VALORE");
  }
}
