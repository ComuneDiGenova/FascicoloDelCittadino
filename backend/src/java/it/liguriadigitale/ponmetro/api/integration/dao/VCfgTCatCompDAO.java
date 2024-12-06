package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * VCfgTCatComp
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:40:05.201
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatComp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VCfgTCatCompDAO extends AbstractSearchDAO {

  private VCfgTCatComp vcfgtcatcomp;

  public VCfgTCatCompDAO(VCfgTCatComp vcfgtcatcomp) {
    super();
    this.vcfgtcatcomp = vcfgtcatcomp;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from V_CFG_T_CAT_COMP";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {}

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    VCfgTCatComp obj = new VCfgTCatComp();

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

    return obj;
  }
}
