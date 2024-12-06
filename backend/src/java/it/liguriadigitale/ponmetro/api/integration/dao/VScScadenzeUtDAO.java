package it.liguriadigitale.ponmetro.api.integration.dao;

/** VScScadenzeUt */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.db.VScScadenzeUt;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VScScadenzeUtDAO extends AbstractSearchDAO {

  protected static final Log log = LogFactory.getLog(VScScadenzeUtDAO.class);

  private VScScadenzeUt vscscadenzeut;

  public VScScadenzeUtDAO(VScScadenzeUt vscscadenzeut) {
    super();
    this.vscscadenzeut = vscscadenzeut;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from V_SC_SCADENZE_UT S " + " WHERE 1=1 ";

    if (vscscadenzeut.getIdFcitt() != null) {
      sql += " AND (ID_FCITT = ? OR ID_FCITT IS NULL) ";
    } else {
      sql += " AND ID_FCITT IS NULL ";
    }

    if (vscscadenzeut.getDataScadenza() != null) {
      sql += " AND DATA_SCADENZA = ? ";
    }

    sql += " ORDER BY S.DATA_SCADENZA";

    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;

    if (vscscadenzeut.getIdFcitt() != null) {
      st.setBigDecimal(index, vscscadenzeut.getIdFcitt());
    }

    if (vscscadenzeut.getDataScadenza() != null) {
      st.setDate(index, Date.valueOf(vscscadenzeut.getDataScadenza().toLocalDate()));
    }
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    VScScadenzeUt obj = new VScScadenzeUt();
    obj.setIdScadenza(r.getLong("ID_SCADENZA"));

    obj.setIdStato(r.getLong("ID_STATO"));

    obj.setStatoScadenza(r.getString("STATO_SCADENZA"));
    obj.setIdComp(r.getLong("ID_COMP"));

    obj.setIdSez(r.getLong("ID_SEZ"));

    obj.setDenominazioneSez(r.getString("DENOMINAZIONE_SEZ"));
    obj.setDescrizioneSez(r.getString("DESCRIZIONE_SEZ"));
    obj.setUriSez(r.getString("URI_SEZ"));
    obj.setDenominazioneComp(r.getString("DENOMINAZIONE_COMP"));
    obj.setDescrizioneComp(r.getString("DESCRIZIONE_COMP"));
    obj.setUriComp(r.getString("URI_COMP"));
    obj.setDataCatalogazioneComp(
        r.getTimestamp("DATA_CATALOGAZIONE_COMP") != null
            ? r.getTimestamp("DATA_CATALOGAZIONE_COMP").toLocalDateTime()
            : null);
    obj.setOrdinamento(r.getLong("ORDINAMENTO"));

    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    obj.setScadenza(r.getString("SCADENZA"));
    obj.setDataScadenza(
        r.getTimestamp("DATA_SCADENZA") != null
            ? r.getTimestamp("DATA_SCADENZA").toLocalDateTime()
            : null);
    obj.setDataInvioMsg(
        r.getTimestamp("DATA_INVIO_MSG") != null
            ? r.getTimestamp("DATA_INVIO_MSG").toLocalDateTime()
            : null);
    obj.setIdFcitt(r.getBigDecimal("ID_FCITT"));
    obj.setPersonId(r.getBigDecimal("PERSON_ID"));
    obj.setIdTEvento(r.getBigDecimal("ID_T_EVENTO"));
    obj.setOggetto(r.getString("OGGETTO"));

    return obj;
  }
}
