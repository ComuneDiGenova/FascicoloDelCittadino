package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * VCfgRFcittComp
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:40:04.483
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgRFcittComp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VCfgRFcittCompDAO extends AbstractSearchDAO {

  protected VCfgRFcittComp vcfgrfcittcomp;

  public VCfgRFcittCompDAO(VCfgRFcittComp vcfgrfcittcomp) {
    super();
    this.vcfgrfcittcomp = vcfgrfcittcomp;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from V_CFG_R_FCITT_COMP WHERE 1 = 1 ";
    if (vcfgrfcittcomp.getIdFcitt() != null) sql += " and ID_FCITT = ? ";
    if (vcfgrfcittcomp.getIdSez() != null) sql += " and ID_SEZ = ? ";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (vcfgrfcittcomp.getIdFcitt() != null) st.setLong(index++, vcfgrfcittcomp.getIdFcitt());
    if (vcfgrfcittcomp.getIdSez() != null) st.setLong(index++, vcfgrfcittcomp.getIdSez());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    VCfgRFcittComp obj = new VCfgRFcittComp();

    obj.setIdSez(r.getLong("ID_SEZ"));

    obj.setOrdinamentoS(r.getLong("ORDINAMENTO_S"));

    obj.setOrdinamentoC(r.getLong("ORDINAMENTO_C"));

    obj.setDenominazioneSez(r.getString("DENOMINAZIONE_SEZ"));
    obj.setDescrizioneSez(r.getString("DESCRIZIONE_SEZ"));
    obj.setDataCatalogazioneSez(
        r.getTimestamp("DATA_CATALOGAZIONE_SEZ") != null
            ? r.getTimestamp("DATA_CATALOGAZIONE_SEZ").toLocalDateTime()
            : null);
    obj.setUriSez(r.getString("URI_SEZ"));
    obj.setFlagAbilitazioneSez(r.getBoolean("FLAG_ABILITAZIONE_SEZ"));
    obj.setIdComp(r.getLong("ID_COMP"));

    obj.setDenominazioneComp(r.getString("DENOMINAZIONE_COMP"));
    obj.setDescrizioneComp(r.getString("DESCRIZIONE_COMP"));
    obj.setUriComp(r.getString("URI_COMP"));
    obj.setDataCatalogazioneComp(
        r.getTimestamp("DATA_CATALOGAZIONE_COMP") != null
            ? r.getTimestamp("DATA_CATALOGAZIONE_COMP").toLocalDateTime()
            : null);
    obj.setDataInvioMsg(
        r.getTimestamp("DATA_INVIO_MSG") != null
            ? r.getTimestamp("DATA_INVIO_MSG").toLocalDateTime()
            : null);
    obj.setFlagAbilitazioneComp(r.getBoolean("FLAG_ABILITAZIONE_COMP"));
    obj.setIdCfgRFcittComp(r.getLong("ID_CFG_R_FCITT_COMP"));
    obj.setIdFcitt(r.getLong("ID_FCITT"));
    obj.setDataRegistrazFcittComp(
        r.getTimestamp("DATA_REGISTRAZ_FCITT_COMP") != null
            ? r.getTimestamp("DATA_REGISTRAZ_FCITT_COMP").toLocalDateTime()
            : null);
    obj.setDataDeregistrazFcittComp(
        r.getTimestamp("DATA_DEREGISTRAZ_FCITT_COMP") != null
            ? r.getTimestamp("DATA_DEREGISTRAZ_FCITT_COMP").toLocalDateTime()
            : null);
    obj.setFlagAbilitazioneCittComp(r.getBoolean("FLAG_ABILITAZIONE_CITT_COMP"));
    obj.setPersonId(r.getLong("PERSON_ID"));

    return obj;
  }
}
