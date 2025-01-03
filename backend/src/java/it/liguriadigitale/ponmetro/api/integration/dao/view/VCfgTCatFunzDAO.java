package it.liguriadigitale.ponmetro.api.integration.dao.view;

/**
 * VCfgTCatFunz
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2021-11-26T10:10:38.700
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatFunz;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VCfgTCatFunzDAO extends AbstractSearchDAO {

  private VCfgTCatFunz vcfgtcatfunz;

  public VCfgTCatFunzDAO(VCfgTCatFunz vcfgtcatfunz) {
    super();
    this.vcfgtcatfunz = vcfgtcatfunz;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from V_CFG_T_CAT_FUNZ";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {}

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    VCfgTCatFunz obj = new VCfgTCatFunz();

    obj.setIdSez(r.getLong("ID_SEZ"));
    if (r.wasNull()) {
      obj.setIdSez(null);
    }

    obj.setOrdinamentoS(r.getLong("ORDINAMENTO_S"));
    if (r.wasNull()) {
      obj.setOrdinamentoS(null);
    }

    obj.setOrdinamentoC(r.getLong("ORDINAMENTO_C"));
    if (r.wasNull()) {
      obj.setOrdinamentoC(null);
    }

    obj.setDenominazioneSez(r.getString("DENOMINAZIONE_SEZ"));
    obj.setDescrizioneSez(r.getString("DESCRIZIONE_SEZ"));
    obj.setDataCatalogazioneSez(
        r.getTimestamp("DATA_CATALOGAZIONE_SEZ") != null
            ? r.getTimestamp("DATA_CATALOGAZIONE_SEZ").toLocalDateTime()
            : null);
    obj.setUriSez(r.getString("URI_SEZ"));
    obj.setFlagAbilitazioneSez(r.getBoolean("FLAG_ABILITAZIONE_SEZ"));
    obj.setIdComp(r.getLong("ID_COMP"));
    if (r.wasNull()) {
      obj.setIdComp(null);
    }

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
    obj.setIdFunz(r.getLong("ID_FUNZ"));
    if (r.wasNull()) {
      obj.setIdFunz(null);
    }

    obj.setDenominazioneFunz(r.getString("DENOMINAZIONE_FUNZ"));
    obj.setDescrizioneFunz(r.getString("DESCRIZIONE_FUNZ"));
    obj.setWicketLabelIdStd(r.getString("WICKET_LABEL_ID_STD"));
    obj.setWicketLabelIdAlt(r.getString("WICKET_LABEL_ID_ALT"));
    obj.setClassePaginaStd(r.getString("CLASSE_PAGINA_STD"));
    obj.setClassePaginaAlt(r.getString("CLASSE_PAGINA_ALT"));
    obj.setWicketTitleStd(r.getString("WICKET_TITLE_STD"));
    obj.setWicketTitleAlt(r.getString("WICKET_TITLE_ALT"));
    obj.setIconaCss(r.getString("ICONA_CSS"));
    obj.setFlagAbilitazioneFunz(r.getBoolean("FLAG_ABILITAZIONE_FUNZ"));
    obj.setIdStatoRec(r.getBoolean("ID_STATO_REC"));
    obj.setFlagResidente(r.getBoolean("FLAG_RESIDENTE"));
    obj.setFlagNonResidente(r.getBoolean("FLAG_NON_RESIDENTE"));
    obj.setIdFunzPadre(r.getString("ID_FUNZ_PADRE"));
    return obj;
  }
}
