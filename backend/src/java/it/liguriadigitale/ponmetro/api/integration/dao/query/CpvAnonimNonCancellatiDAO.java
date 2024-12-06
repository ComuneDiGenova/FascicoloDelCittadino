package it.liguriadigitale.ponmetro.api.integration.dao.query;

import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.db.CpvAnonim;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CpvAnonimNonCancellatiDAO extends AbstractSearchDAO {

  private CpvAnonim cpvanonim;

  public CpvAnonimNonCancellatiDAO(CpvAnonim cpvAnonim) {
    super();
    this.cpvanonim = cpvAnonim;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CPV_ANONIM where 1=1 and ID_STATO_REC=1 ";
    if (cpvanonim.getPersonId() != null) sql += " and PERSON_ID  = ?";
    if (cpvanonim.getCodiceFiscale() != null) sql += " and CODICE_FISCALE  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cpvanonim.getPersonId() != null) st.setLong(index++, cpvanonim.getPersonId());
    if (cpvanonim.getCodiceFiscale() != null) st.setString(index++, cpvanonim.getCodiceFiscale());
  }

  @Override
  protected Object getFromResultSet(ResultSet r) throws SQLException {
    CpvAnonim obj = new CpvAnonim();

    obj.setPersonId(r.getLong("PERSON_ID"));

    obj.setCodiceFiscale(r.getString("CODICE_FISCALE"));
    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(
        r.getTimestamp("DATA_INS") != null ? r.getTimestamp("DATA_INS").toLocalDateTime() : null);
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(
        r.getTimestamp("DATA_AGG") != null ? r.getTimestamp("DATA_AGG").toLocalDateTime() : null);
    obj.setNome(r.getString("NOME"));
    obj.setCognome(r.getString("COGNOME"));
    obj.setDataNascita(
        r.getTimestamp("DATA_NASCITA") != null
            ? r.getTimestamp("DATA_NASCITA").toLocalDateTime()
            : null);
    obj.setSesso(r.getString("SESSO"));
    obj.setCodiceBelfioreNascita(r.getString("CODICE_BELFIORE_NASCITA"));
    return obj;
  }
}
